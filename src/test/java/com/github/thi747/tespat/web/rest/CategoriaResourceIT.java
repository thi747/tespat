package com.github.thi747.tespat.web.rest;

import static com.github.thi747.tespat.domain.CategoriaAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.thi747.tespat.IntegrationTest;
import com.github.thi747.tespat.domain.Categoria;
import com.github.thi747.tespat.repository.CategoriaRepository;
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
 * Integration tests for the {@link CategoriaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CategoriaResourceIT {

    private static final String ENTITY_API_URL = "/api/categorias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{nome}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoriaMockMvc;

    private Categoria categoria;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Categoria createEntity(EntityManager em) {
        Categoria categoria = new Categoria().nome(UUID.randomUUID().toString());
        return categoria;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Categoria createUpdatedEntity(EntityManager em) {
        Categoria categoria = new Categoria().nome(UUID.randomUUID().toString());
        return categoria;
    }

    @BeforeEach
    public void initTest() {
        categoria = createEntity(em);
    }

    @Test
    @Transactional
    void createCategoria() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Categoria
        var returnedCategoria = om.readValue(
            restCategoriaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(categoria)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Categoria.class
        );

        // Validate the Categoria in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCategoriaUpdatableFieldsEquals(returnedCategoria, getPersistedCategoria(returnedCategoria));
    }

    @Test
    @Transactional
    void createCategoriaWithExistingId() throws Exception {
        // Create the Categoria with an existing ID
        categoriaRepository.saveAndFlush(categoria);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoriaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(categoria)))
            .andExpect(status().isBadRequest());

        // Validate the Categoria in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCategorias() throws Exception {
        // Initialize the database
        categoria.setNome(UUID.randomUUID().toString());
        categoriaRepository.saveAndFlush(categoria);

        // Get all the categoriaList
        restCategoriaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=nome,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(categoria.getNome())));
    }

    @Test
    @Transactional
    void getCategoria() throws Exception {
        // Initialize the database
        categoria.setNome(UUID.randomUUID().toString());
        categoriaRepository.saveAndFlush(categoria);

        // Get the categoria
        restCategoriaMockMvc
            .perform(get(ENTITY_API_URL_ID, categoria.getNome()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.nome").value(categoria.getNome()));
    }

    @Test
    @Transactional
    void getNonExistingCategoria() throws Exception {
        // Get the categoria
        restCategoriaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteCategoria() throws Exception {
        // Initialize the database
        categoria.setNome(UUID.randomUUID().toString());
        categoriaRepository.saveAndFlush(categoria);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the categoria
        restCategoriaMockMvc
            .perform(delete(ENTITY_API_URL_ID, categoria.getNome()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return categoriaRepository.count();
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

    protected Categoria getPersistedCategoria(Categoria categoria) {
        return categoriaRepository.findById(categoria.getNome()).orElseThrow();
    }

    protected void assertPersistedCategoriaToMatchAllProperties(Categoria expectedCategoria) {
        assertCategoriaAllPropertiesEquals(expectedCategoria, getPersistedCategoria(expectedCategoria));
    }

    protected void assertPersistedCategoriaToMatchUpdatableProperties(Categoria expectedCategoria) {
        assertCategoriaAllUpdatablePropertiesEquals(expectedCategoria, getPersistedCategoria(expectedCategoria));
    }
}
