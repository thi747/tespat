package com.github.thi747.tespat.web.rest;

import static com.github.thi747.tespat.domain.FornecedorAsserts.*;
import static com.github.thi747.tespat.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.thi747.tespat.IntegrationTest;
import com.github.thi747.tespat.domain.Fornecedor;
import com.github.thi747.tespat.repository.FornecedorRepository;
import com.github.thi747.tespat.service.dto.FornecedorDTO;
import com.github.thi747.tespat.service.mapper.FornecedorMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link FornecedorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FornecedorResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_CPF_OU_CNPJ = "AAAAAAAAAAA";
    private static final String UPDATED_CPF_OU_CNPJ = "BBBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final String DEFAULT_ENDERECO = "AAAAAAAAAA";
    private static final String UPDATED_ENDERECO = "BBBBBBBBBB";

    private static final String DEFAULT_MUNICIPIO = "AAAAAAAAAA";
    private static final String UPDATED_MUNICIPIO = "BBBBBBBBBB";

    private static final String DEFAULT_UF = "AA";
    private static final String UPDATED_UF = "BB";

    private static final String ENTITY_API_URL = "/api/fornecedores";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private FornecedorMapper fornecedorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFornecedorMockMvc;

    private Fornecedor fornecedor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fornecedor createEntity(EntityManager em) {
        Fornecedor fornecedor = new Fornecedor()
            .nome(DEFAULT_NOME)
            .cpfOuCnpj(DEFAULT_CPF_OU_CNPJ)
            .email(DEFAULT_EMAIL)
            .descricao(DEFAULT_DESCRICAO)
            .telefone(DEFAULT_TELEFONE)
            .endereco(DEFAULT_ENDERECO)
            .municipio(DEFAULT_MUNICIPIO)
            .uf(DEFAULT_UF);
        return fornecedor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fornecedor createUpdatedEntity(EntityManager em) {
        Fornecedor fornecedor = new Fornecedor()
            .nome(UPDATED_NOME)
            .cpfOuCnpj(UPDATED_CPF_OU_CNPJ)
            .email(UPDATED_EMAIL)
            .descricao(UPDATED_DESCRICAO)
            .telefone(UPDATED_TELEFONE)
            .endereco(UPDATED_ENDERECO)
            .municipio(UPDATED_MUNICIPIO)
            .uf(UPDATED_UF);
        return fornecedor;
    }

    @BeforeEach
    public void initTest() {
        fornecedor = createEntity(em);
    }

    @Test
    @Transactional
    void createFornecedor() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Fornecedor
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);
        var returnedFornecedorDTO = om.readValue(
            restFornecedorMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fornecedorDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FornecedorDTO.class
        );

        // Validate the Fornecedor in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedFornecedor = fornecedorMapper.toEntity(returnedFornecedorDTO);
        assertFornecedorUpdatableFieldsEquals(returnedFornecedor, getPersistedFornecedor(returnedFornecedor));
    }

    @Test
    @Transactional
    void createFornecedorWithExistingId() throws Exception {
        // Create the Fornecedor with an existing ID
        fornecedor.setId(1L);
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFornecedorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fornecedorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fornecedor in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        fornecedor.setNome(null);

        // Create the Fornecedor, which fails.
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        restFornecedorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fornecedorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCpfOuCnpjIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        fornecedor.setCpfOuCnpj(null);

        // Create the Fornecedor, which fails.
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        restFornecedorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fornecedorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFornecedores() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList
        restFornecedorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fornecedor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].cpfOuCnpj").value(hasItem(DEFAULT_CPF_OU_CNPJ)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO)))
            .andExpect(jsonPath("$.[*].municipio").value(hasItem(DEFAULT_MUNICIPIO)))
            .andExpect(jsonPath("$.[*].uf").value(hasItem(DEFAULT_UF)));
    }

    @Test
    @Transactional
    void getFornecedor() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get the fornecedor
        restFornecedorMockMvc
            .perform(get(ENTITY_API_URL_ID, fornecedor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fornecedor.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.cpfOuCnpj").value(DEFAULT_CPF_OU_CNPJ))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE))
            .andExpect(jsonPath("$.endereco").value(DEFAULT_ENDERECO))
            .andExpect(jsonPath("$.municipio").value(DEFAULT_MUNICIPIO))
            .andExpect(jsonPath("$.uf").value(DEFAULT_UF));
    }

    @Test
    @Transactional
    void getNonExistingFornecedor() throws Exception {
        // Get the fornecedor
        restFornecedorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFornecedor() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fornecedor
        Fornecedor updatedFornecedor = fornecedorRepository.findById(fornecedor.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFornecedor are not directly saved in db
        em.detach(updatedFornecedor);
        updatedFornecedor
            .nome(UPDATED_NOME)
            .cpfOuCnpj(UPDATED_CPF_OU_CNPJ)
            .email(UPDATED_EMAIL)
            .descricao(UPDATED_DESCRICAO)
            .telefone(UPDATED_TELEFONE)
            .endereco(UPDATED_ENDERECO)
            .municipio(UPDATED_MUNICIPIO)
            .uf(UPDATED_UF);
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(updatedFornecedor);

        restFornecedorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fornecedorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fornecedorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Fornecedor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFornecedorToMatchAllProperties(updatedFornecedor);
    }

    @Test
    @Transactional
    void putNonExistingFornecedor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fornecedor.setId(longCount.incrementAndGet());

        // Create the Fornecedor
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFornecedorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fornecedorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fornecedorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fornecedor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFornecedor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fornecedor.setId(longCount.incrementAndGet());

        // Create the Fornecedor
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFornecedorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fornecedorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fornecedor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFornecedor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fornecedor.setId(longCount.incrementAndGet());

        // Create the Fornecedor
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFornecedorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fornecedorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fornecedor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFornecedorWithPatch() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fornecedor using partial update
        Fornecedor partialUpdatedFornecedor = new Fornecedor();
        partialUpdatedFornecedor.setId(fornecedor.getId());

        partialUpdatedFornecedor
            .descricao(UPDATED_DESCRICAO)
            .telefone(UPDATED_TELEFONE)
            .endereco(UPDATED_ENDERECO)
            .municipio(UPDATED_MUNICIPIO)
            .uf(UPDATED_UF);

        restFornecedorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFornecedor.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFornecedor))
            )
            .andExpect(status().isOk());

        // Validate the Fornecedor in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFornecedorUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFornecedor, fornecedor),
            getPersistedFornecedor(fornecedor)
        );
    }

    @Test
    @Transactional
    void fullUpdateFornecedorWithPatch() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fornecedor using partial update
        Fornecedor partialUpdatedFornecedor = new Fornecedor();
        partialUpdatedFornecedor.setId(fornecedor.getId());

        partialUpdatedFornecedor
            .nome(UPDATED_NOME)
            .cpfOuCnpj(UPDATED_CPF_OU_CNPJ)
            .email(UPDATED_EMAIL)
            .descricao(UPDATED_DESCRICAO)
            .telefone(UPDATED_TELEFONE)
            .endereco(UPDATED_ENDERECO)
            .municipio(UPDATED_MUNICIPIO)
            .uf(UPDATED_UF);

        restFornecedorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFornecedor.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFornecedor))
            )
            .andExpect(status().isOk());

        // Validate the Fornecedor in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFornecedorUpdatableFieldsEquals(partialUpdatedFornecedor, getPersistedFornecedor(partialUpdatedFornecedor));
    }

    @Test
    @Transactional
    void patchNonExistingFornecedor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fornecedor.setId(longCount.incrementAndGet());

        // Create the Fornecedor
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFornecedorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fornecedorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fornecedorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fornecedor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFornecedor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fornecedor.setId(longCount.incrementAndGet());

        // Create the Fornecedor
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFornecedorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fornecedorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fornecedor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFornecedor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fornecedor.setId(longCount.incrementAndGet());

        // Create the Fornecedor
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFornecedorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(fornecedorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fornecedor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFornecedor() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the fornecedor
        restFornecedorMockMvc
            .perform(delete(ENTITY_API_URL_ID, fornecedor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return fornecedorRepository.count();
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

    protected Fornecedor getPersistedFornecedor(Fornecedor fornecedor) {
        return fornecedorRepository.findById(fornecedor.getId()).orElseThrow();
    }

    protected void assertPersistedFornecedorToMatchAllProperties(Fornecedor expectedFornecedor) {
        assertFornecedorAllPropertiesEquals(expectedFornecedor, getPersistedFornecedor(expectedFornecedor));
    }

    protected void assertPersistedFornecedorToMatchUpdatableProperties(Fornecedor expectedFornecedor) {
        assertFornecedorAllUpdatablePropertiesEquals(expectedFornecedor, getPersistedFornecedor(expectedFornecedor));
    }
}
