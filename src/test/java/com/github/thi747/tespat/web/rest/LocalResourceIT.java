package com.github.thi747.tespat.web.rest;

import static com.github.thi747.tespat.domain.LocalAsserts.*;
import static com.github.thi747.tespat.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.thi747.tespat.IntegrationTest;
import com.github.thi747.tespat.domain.Local;
import com.github.thi747.tespat.repository.LocalRepository;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LocalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LocalResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_SALA = "AAAAAAAAAA";
    private static final String UPDATED_SALA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/locals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{nome}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LocalRepository localRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLocalMockMvc;

    private Local local;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Local createEntity(EntityManager em) {
        Local local = new Local().nome(UUID.randomUUID().toString()).descricao(DEFAULT_DESCRICAO).sala(DEFAULT_SALA);
        return local;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Local createUpdatedEntity(EntityManager em) {
        Local local = new Local().nome(UUID.randomUUID().toString()).descricao(UPDATED_DESCRICAO).sala(UPDATED_SALA);
        return local;
    }

    @BeforeEach
    public void initTest() {
        local = createEntity(em);
    }

    @Test
    @Transactional
    void createLocal() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Local
        var returnedLocal = om.readValue(
            restLocalMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(local)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Local.class
        );

        // Validate the Local in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLocalUpdatableFieldsEquals(returnedLocal, getPersistedLocal(returnedLocal));
    }

    @Test
    @Transactional
    void createLocalWithExistingId() throws Exception {
        // Create the Local with an existing ID
        localRepository.saveAndFlush(local);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(local)))
            .andExpect(status().isBadRequest());

        // Validate the Local in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLocals() throws Exception {
        // Initialize the database
        local.setNome(UUID.randomUUID().toString());
        localRepository.saveAndFlush(local);

        // Get all the localList
        restLocalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=nome,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(local.getNome())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].sala").value(hasItem(DEFAULT_SALA)));
    }

    @Test
    @Transactional
    void getLocal() throws Exception {
        // Initialize the database
        local.setNome(UUID.randomUUID().toString());
        localRepository.saveAndFlush(local);

        // Get the local
        restLocalMockMvc
            .perform(get(ENTITY_API_URL_ID, local.getNome()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.nome").value(local.getNome()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.sala").value(DEFAULT_SALA));
    }

    @Test
    @Transactional
    void getNonExistingLocal() throws Exception {
        // Get the local
        restLocalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLocal() throws Exception {
        // Initialize the database
        local.setNome(UUID.randomUUID().toString());
        localRepository.saveAndFlush(local);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the local
        Local updatedLocal = localRepository.findById(local.getNome()).orElseThrow();
        // Disconnect from session so that the updates on updatedLocal are not directly saved in db
        em.detach(updatedLocal);
        updatedLocal.descricao(UPDATED_DESCRICAO).sala(UPDATED_SALA);

        restLocalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLocal.getNome())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedLocal))
            )
            .andExpect(status().isOk());

        // Validate the Local in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLocalToMatchAllProperties(updatedLocal);
    }

    @Test
    @Transactional
    void putNonExistingLocal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        local.setNome(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocalMockMvc
            .perform(put(ENTITY_API_URL_ID, local.getNome()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(local)))
            .andExpect(status().isBadRequest());

        // Validate the Local in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLocal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        local.setNome(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(local))
            )
            .andExpect(status().isBadRequest());

        // Validate the Local in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLocal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        local.setNome(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocalMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(local)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Local in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLocalWithPatch() throws Exception {
        // Initialize the database
        local.setNome(UUID.randomUUID().toString());
        localRepository.saveAndFlush(local);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the local using partial update
        Local partialUpdatedLocal = new Local();
        partialUpdatedLocal.setNome(local.getNome());

        partialUpdatedLocal.descricao(UPDATED_DESCRICAO).sala(UPDATED_SALA);

        restLocalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocal.getNome())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLocal))
            )
            .andExpect(status().isOk());

        // Validate the Local in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLocalUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedLocal, local), getPersistedLocal(local));
    }

    @Test
    @Transactional
    void fullUpdateLocalWithPatch() throws Exception {
        // Initialize the database
        local.setNome(UUID.randomUUID().toString());
        localRepository.saveAndFlush(local);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the local using partial update
        Local partialUpdatedLocal = new Local();
        partialUpdatedLocal.setNome(local.getNome());

        partialUpdatedLocal.descricao(UPDATED_DESCRICAO).sala(UPDATED_SALA);

        restLocalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocal.getNome())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLocal))
            )
            .andExpect(status().isOk());

        // Validate the Local in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLocalUpdatableFieldsEquals(partialUpdatedLocal, getPersistedLocal(partialUpdatedLocal));
    }

    @Test
    @Transactional
    void patchNonExistingLocal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        local.setNome(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, local.getNome()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(local))
            )
            .andExpect(status().isBadRequest());

        // Validate the Local in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLocal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        local.setNome(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(local))
            )
            .andExpect(status().isBadRequest());

        // Validate the Local in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLocal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        local.setNome(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocalMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(local)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Local in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLocal() throws Exception {
        // Initialize the database
        local.setNome(UUID.randomUUID().toString());
        localRepository.saveAndFlush(local);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the local
        restLocalMockMvc
            .perform(delete(ENTITY_API_URL_ID, local.getNome()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return localRepository.count();
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

    protected Local getPersistedLocal(Local local) {
        return localRepository.findById(local.getNome()).orElseThrow();
    }

    protected void assertPersistedLocalToMatchAllProperties(Local expectedLocal) {
        assertLocalAllPropertiesEquals(expectedLocal, getPersistedLocal(expectedLocal));
    }

    protected void assertPersistedLocalToMatchUpdatableProperties(Local expectedLocal) {
        assertLocalAllUpdatablePropertiesEquals(expectedLocal, getPersistedLocal(expectedLocal));
    }
}
