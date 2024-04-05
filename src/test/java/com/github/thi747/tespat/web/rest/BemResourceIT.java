package com.github.thi747.tespat.web.rest;

import static com.github.thi747.tespat.domain.BemAsserts.*;
import static com.github.thi747.tespat.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.thi747.tespat.IntegrationTest;
import com.github.thi747.tespat.domain.Bem;
import com.github.thi747.tespat.domain.enumeration.TipoConservacao;
import com.github.thi747.tespat.domain.enumeration.TipoStatus;
import com.github.thi747.tespat.repository.BemRepository;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BemResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACOES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACOES = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_DE_SERIE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_DE_SERIE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_AQUISICAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_AQUISICAO = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_VALOR_COMPRA = 1D;
    private static final Double UPDATED_VALOR_COMPRA = 2D;

    private static final Double DEFAULT_VALOR_ATUAL = 1D;
    private static final Double UPDATED_VALOR_ATUAL = 2D;

    private static final TipoConservacao DEFAULT_ESTADO = TipoConservacao.NOVO;
    private static final TipoConservacao UPDATED_ESTADO = TipoConservacao.BOM;

    private static final TipoStatus DEFAULT_STATUS = TipoStatus.USO;
    private static final TipoStatus UPDATED_STATUS = TipoStatus.ESTOQUE;

    private static final String ENTITY_API_URL = "/api/bems";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{patrimonio}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BemRepository bemRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBemMockMvc;

    private Bem bem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bem createEntity(EntityManager em) {
        Bem bem = new Bem()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .observacoes(DEFAULT_OBSERVACOES)
            .numeroDeSerie(DEFAULT_NUMERO_DE_SERIE)
            .dataAquisicao(DEFAULT_DATA_AQUISICAO)
            .valorCompra(DEFAULT_VALOR_COMPRA)
            .valorAtual(DEFAULT_VALOR_ATUAL)
            .estado(DEFAULT_ESTADO)
            .status(DEFAULT_STATUS);
        return bem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bem createUpdatedEntity(EntityManager em) {
        Bem bem = new Bem()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .observacoes(UPDATED_OBSERVACOES)
            .numeroDeSerie(UPDATED_NUMERO_DE_SERIE)
            .dataAquisicao(UPDATED_DATA_AQUISICAO)
            .valorCompra(UPDATED_VALOR_COMPRA)
            .valorAtual(UPDATED_VALOR_ATUAL)
            .estado(UPDATED_ESTADO)
            .status(UPDATED_STATUS);
        return bem;
    }

    @BeforeEach
    public void initTest() {
        bem = createEntity(em);
    }

    @Test
    @Transactional
    void createBem() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Bem
        var returnedBem = om.readValue(
            restBemMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bem)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Bem.class
        );

        // Validate the Bem in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBemUpdatableFieldsEquals(returnedBem, getPersistedBem(returnedBem));
    }

    @Test
    @Transactional
    void createBemWithExistingId() throws Exception {
        // Create the Bem with an existing ID
        bem.setPatrimonio(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bem)))
            .andExpect(status().isBadRequest());

        // Validate the Bem in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        bem.setNome(null);

        // Create the Bem, which fails.

        restBemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bem)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBems() throws Exception {
        // Initialize the database
        bemRepository.saveAndFlush(bem);

        // Get all the bemList
        restBemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=patrimonio,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].patrimonio").value(hasItem(bem.getPatrimonio().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].observacoes").value(hasItem(DEFAULT_OBSERVACOES)))
            .andExpect(jsonPath("$.[*].numeroDeSerie").value(hasItem(DEFAULT_NUMERO_DE_SERIE)))
            .andExpect(jsonPath("$.[*].dataAquisicao").value(hasItem(DEFAULT_DATA_AQUISICAO.toString())))
            .andExpect(jsonPath("$.[*].valorCompra").value(hasItem(DEFAULT_VALOR_COMPRA.doubleValue())))
            .andExpect(jsonPath("$.[*].valorAtual").value(hasItem(DEFAULT_VALOR_ATUAL.doubleValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getBem() throws Exception {
        // Initialize the database
        bemRepository.saveAndFlush(bem);

        // Get the bem
        restBemMockMvc
            .perform(get(ENTITY_API_URL_ID, bem.getPatrimonio()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.patrimonio").value(bem.getPatrimonio().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.observacoes").value(DEFAULT_OBSERVACOES))
            .andExpect(jsonPath("$.numeroDeSerie").value(DEFAULT_NUMERO_DE_SERIE))
            .andExpect(jsonPath("$.dataAquisicao").value(DEFAULT_DATA_AQUISICAO.toString()))
            .andExpect(jsonPath("$.valorCompra").value(DEFAULT_VALOR_COMPRA.doubleValue()))
            .andExpect(jsonPath("$.valorAtual").value(DEFAULT_VALOR_ATUAL.doubleValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingBem() throws Exception {
        // Get the bem
        restBemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBem() throws Exception {
        // Initialize the database
        bemRepository.saveAndFlush(bem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bem
        Bem updatedBem = bemRepository.findById(bem.getPatrimonio()).orElseThrow();
        // Disconnect from session so that the updates on updatedBem are not directly saved in db
        em.detach(updatedBem);
        updatedBem
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .observacoes(UPDATED_OBSERVACOES)
            .numeroDeSerie(UPDATED_NUMERO_DE_SERIE)
            .dataAquisicao(UPDATED_DATA_AQUISICAO)
            .valorCompra(UPDATED_VALOR_COMPRA)
            .valorAtual(UPDATED_VALOR_ATUAL)
            .estado(UPDATED_ESTADO)
            .status(UPDATED_STATUS);

        restBemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBem.getPatrimonio())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBem))
            )
            .andExpect(status().isOk());

        // Validate the Bem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBemToMatchAllProperties(updatedBem);
    }

    @Test
    @Transactional
    void putNonExistingBem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bem.setPatrimonio(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBemMockMvc
            .perform(put(ENTITY_API_URL_ID, bem.getPatrimonio()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bem)))
            .andExpect(status().isBadRequest());

        // Validate the Bem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bem.setPatrimonio(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bem))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bem.setPatrimonio(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bem)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBemWithPatch() throws Exception {
        // Initialize the database
        bemRepository.saveAndFlush(bem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bem using partial update
        Bem partialUpdatedBem = new Bem();
        partialUpdatedBem.setPatrimonio(bem.getPatrimonio());

        partialUpdatedBem
            .numeroDeSerie(UPDATED_NUMERO_DE_SERIE)
            .valorCompra(UPDATED_VALOR_COMPRA)
            .valorAtual(UPDATED_VALOR_ATUAL)
            .status(UPDATED_STATUS);

        restBemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBem.getPatrimonio())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBem))
            )
            .andExpect(status().isOk());

        // Validate the Bem in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBemUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedBem, bem), getPersistedBem(bem));
    }

    @Test
    @Transactional
    void fullUpdateBemWithPatch() throws Exception {
        // Initialize the database
        bemRepository.saveAndFlush(bem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bem using partial update
        Bem partialUpdatedBem = new Bem();
        partialUpdatedBem.setPatrimonio(bem.getPatrimonio());

        partialUpdatedBem
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .observacoes(UPDATED_OBSERVACOES)
            .numeroDeSerie(UPDATED_NUMERO_DE_SERIE)
            .dataAquisicao(UPDATED_DATA_AQUISICAO)
            .valorCompra(UPDATED_VALOR_COMPRA)
            .valorAtual(UPDATED_VALOR_ATUAL)
            .estado(UPDATED_ESTADO)
            .status(UPDATED_STATUS);

        restBemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBem.getPatrimonio())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBem))
            )
            .andExpect(status().isOk());

        // Validate the Bem in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBemUpdatableFieldsEquals(partialUpdatedBem, getPersistedBem(partialUpdatedBem));
    }

    @Test
    @Transactional
    void patchNonExistingBem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bem.setPatrimonio(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bem.getPatrimonio()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bem))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bem.setPatrimonio(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bem))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bem.setPatrimonio(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBemMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bem)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBem() throws Exception {
        // Initialize the database
        bemRepository.saveAndFlush(bem);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bem
        restBemMockMvc
            .perform(delete(ENTITY_API_URL_ID, bem.getPatrimonio()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bemRepository.count();
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

    protected Bem getPersistedBem(Bem bem) {
        return bemRepository.findById(bem.getPatrimonio()).orElseThrow();
    }

    protected void assertPersistedBemToMatchAllProperties(Bem expectedBem) {
        assertBemAllPropertiesEquals(expectedBem, getPersistedBem(expectedBem));
    }

    protected void assertPersistedBemToMatchUpdatableProperties(Bem expectedBem) {
        assertBemAllUpdatablePropertiesEquals(expectedBem, getPersistedBem(expectedBem));
    }
}
