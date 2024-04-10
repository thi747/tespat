package com.github.thi747.tespat.web.rest;

import static com.github.thi747.tespat.domain.MovimentacaoAsserts.*;
import static com.github.thi747.tespat.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.thi747.tespat.IntegrationTest;
import com.github.thi747.tespat.domain.Bem;
import com.github.thi747.tespat.domain.Local;
import com.github.thi747.tespat.domain.Movimentacao;
import com.github.thi747.tespat.domain.enumeration.TipoMovimentacao;
import com.github.thi747.tespat.repository.MovimentacaoRepository;
import com.github.thi747.tespat.service.MovimentacaoService;
import com.github.thi747.tespat.service.dto.MovimentacaoDTO;
import com.github.thi747.tespat.service.mapper.MovimentacaoMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MovimentacaoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class MovimentacaoResourceIT {

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final TipoMovimentacao DEFAULT_TIPO = TipoMovimentacao.ENTRADA;
    private static final TipoMovimentacao UPDATED_TIPO = TipoMovimentacao.SAIDA;

    private static final String ENTITY_API_URL = "/api/movimentacaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Mock
    private MovimentacaoRepository movimentacaoRepositoryMock;

    @Autowired
    private MovimentacaoMapper movimentacaoMapper;

    @Mock
    private MovimentacaoService movimentacaoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMovimentacaoMockMvc;

    private Movimentacao movimentacao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Movimentacao createEntity(EntityManager em) {
        Movimentacao movimentacao = new Movimentacao().data(DEFAULT_DATA).descricao(DEFAULT_DESCRICAO).tipo(DEFAULT_TIPO);
        // Add required entity
        Bem bem;
        if (TestUtil.findAll(em, Bem.class).isEmpty()) {
            bem = BemResourceIT.createEntity(em);
            em.persist(bem);
            em.flush();
        } else {
            bem = TestUtil.findAll(em, Bem.class).get(0);
        }
        movimentacao.setBem(bem);
        // Add required entity
        Local local;
        if (TestUtil.findAll(em, Local.class).isEmpty()) {
            local = LocalResourceIT.createEntity(em);
            em.persist(local);
            em.flush();
        } else {
            local = TestUtil.findAll(em, Local.class).get(0);
        }
        movimentacao.setLocal(local);
        return movimentacao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Movimentacao createUpdatedEntity(EntityManager em) {
        Movimentacao movimentacao = new Movimentacao().data(UPDATED_DATA).descricao(UPDATED_DESCRICAO).tipo(UPDATED_TIPO);
        // Add required entity
        Bem bem;
        if (TestUtil.findAll(em, Bem.class).isEmpty()) {
            bem = BemResourceIT.createUpdatedEntity(em);
            em.persist(bem);
            em.flush();
        } else {
            bem = TestUtil.findAll(em, Bem.class).get(0);
        }
        movimentacao.setBem(bem);
        // Add required entity
        Local local;
        if (TestUtil.findAll(em, Local.class).isEmpty()) {
            local = LocalResourceIT.createUpdatedEntity(em);
            em.persist(local);
            em.flush();
        } else {
            local = TestUtil.findAll(em, Local.class).get(0);
        }
        movimentacao.setLocal(local);
        return movimentacao;
    }

    @BeforeEach
    public void initTest() {
        movimentacao = createEntity(em);
    }

    @Test
    @Transactional
    void createMovimentacao() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Movimentacao
        MovimentacaoDTO movimentacaoDTO = movimentacaoMapper.toDto(movimentacao);
        var returnedMovimentacaoDTO = om.readValue(
            restMovimentacaoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(movimentacaoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MovimentacaoDTO.class
        );

        // Validate the Movimentacao in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMovimentacao = movimentacaoMapper.toEntity(returnedMovimentacaoDTO);
        assertMovimentacaoUpdatableFieldsEquals(returnedMovimentacao, getPersistedMovimentacao(returnedMovimentacao));
    }

    @Test
    @Transactional
    void createMovimentacaoWithExistingId() throws Exception {
        // Create the Movimentacao with an existing ID
        movimentacao.setId(1L);
        MovimentacaoDTO movimentacaoDTO = movimentacaoMapper.toDto(movimentacao);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovimentacaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(movimentacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Movimentacao in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTipoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        movimentacao.setTipo(null);

        // Create the Movimentacao, which fails.
        MovimentacaoDTO movimentacaoDTO = movimentacaoMapper.toDto(movimentacao);

        restMovimentacaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(movimentacaoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMovimentacaos() throws Exception {
        // Initialize the database
        movimentacaoRepository.saveAndFlush(movimentacao);

        // Get all the movimentacaoList
        restMovimentacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movimentacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMovimentacaosWithEagerRelationshipsIsEnabled() throws Exception {
        when(movimentacaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMovimentacaoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(movimentacaoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMovimentacaosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(movimentacaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMovimentacaoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(movimentacaoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getMovimentacao() throws Exception {
        // Initialize the database
        movimentacaoRepository.saveAndFlush(movimentacao);

        // Get the movimentacao
        restMovimentacaoMockMvc
            .perform(get(ENTITY_API_URL_ID, movimentacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(movimentacao.getId().intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMovimentacao() throws Exception {
        // Get the movimentacao
        restMovimentacaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMovimentacao() throws Exception {
        // Initialize the database
        movimentacaoRepository.saveAndFlush(movimentacao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the movimentacao
        Movimentacao updatedMovimentacao = movimentacaoRepository.findById(movimentacao.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMovimentacao are not directly saved in db
        em.detach(updatedMovimentacao);
        updatedMovimentacao.data(UPDATED_DATA).descricao(UPDATED_DESCRICAO).tipo(UPDATED_TIPO);
        MovimentacaoDTO movimentacaoDTO = movimentacaoMapper.toDto(updatedMovimentacao);

        restMovimentacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, movimentacaoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(movimentacaoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Movimentacao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMovimentacaoToMatchAllProperties(updatedMovimentacao);
    }

    @Test
    @Transactional
    void putNonExistingMovimentacao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        movimentacao.setId(longCount.incrementAndGet());

        // Create the Movimentacao
        MovimentacaoDTO movimentacaoDTO = movimentacaoMapper.toDto(movimentacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovimentacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, movimentacaoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(movimentacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Movimentacao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMovimentacao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        movimentacao.setId(longCount.incrementAndGet());

        // Create the Movimentacao
        MovimentacaoDTO movimentacaoDTO = movimentacaoMapper.toDto(movimentacao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMovimentacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(movimentacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Movimentacao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMovimentacao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        movimentacao.setId(longCount.incrementAndGet());

        // Create the Movimentacao
        MovimentacaoDTO movimentacaoDTO = movimentacaoMapper.toDto(movimentacao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMovimentacaoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(movimentacaoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Movimentacao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMovimentacaoWithPatch() throws Exception {
        // Initialize the database
        movimentacaoRepository.saveAndFlush(movimentacao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the movimentacao using partial update
        Movimentacao partialUpdatedMovimentacao = new Movimentacao();
        partialUpdatedMovimentacao.setId(movimentacao.getId());

        partialUpdatedMovimentacao.data(UPDATED_DATA).descricao(UPDATED_DESCRICAO).tipo(UPDATED_TIPO);

        restMovimentacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMovimentacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMovimentacao))
            )
            .andExpect(status().isOk());

        // Validate the Movimentacao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMovimentacaoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMovimentacao, movimentacao),
            getPersistedMovimentacao(movimentacao)
        );
    }

    @Test
    @Transactional
    void fullUpdateMovimentacaoWithPatch() throws Exception {
        // Initialize the database
        movimentacaoRepository.saveAndFlush(movimentacao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the movimentacao using partial update
        Movimentacao partialUpdatedMovimentacao = new Movimentacao();
        partialUpdatedMovimentacao.setId(movimentacao.getId());

        partialUpdatedMovimentacao.data(UPDATED_DATA).descricao(UPDATED_DESCRICAO).tipo(UPDATED_TIPO);

        restMovimentacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMovimentacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMovimentacao))
            )
            .andExpect(status().isOk());

        // Validate the Movimentacao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMovimentacaoUpdatableFieldsEquals(partialUpdatedMovimentacao, getPersistedMovimentacao(partialUpdatedMovimentacao));
    }

    @Test
    @Transactional
    void patchNonExistingMovimentacao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        movimentacao.setId(longCount.incrementAndGet());

        // Create the Movimentacao
        MovimentacaoDTO movimentacaoDTO = movimentacaoMapper.toDto(movimentacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovimentacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, movimentacaoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(movimentacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Movimentacao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMovimentacao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        movimentacao.setId(longCount.incrementAndGet());

        // Create the Movimentacao
        MovimentacaoDTO movimentacaoDTO = movimentacaoMapper.toDto(movimentacao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMovimentacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(movimentacaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Movimentacao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMovimentacao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        movimentacao.setId(longCount.incrementAndGet());

        // Create the Movimentacao
        MovimentacaoDTO movimentacaoDTO = movimentacaoMapper.toDto(movimentacao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMovimentacaoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(movimentacaoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Movimentacao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMovimentacao() throws Exception {
        // Initialize the database
        movimentacaoRepository.saveAndFlush(movimentacao);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the movimentacao
        restMovimentacaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, movimentacao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return movimentacaoRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Movimentacao getPersistedMovimentacao(Movimentacao movimentacao) {
        return movimentacaoRepository.findById(movimentacao.getId()).orElseThrow();
    }

    protected void assertPersistedMovimentacaoToMatchAllProperties(Movimentacao expectedMovimentacao) {
        assertMovimentacaoAllPropertiesEquals(expectedMovimentacao, getPersistedMovimentacao(expectedMovimentacao));
    }

    protected void assertPersistedMovimentacaoToMatchUpdatableProperties(Movimentacao expectedMovimentacao) {
        assertMovimentacaoAllUpdatablePropertiesEquals(expectedMovimentacao, getPersistedMovimentacao(expectedMovimentacao));
    }
}
