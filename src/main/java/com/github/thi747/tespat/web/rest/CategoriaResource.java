package com.github.thi747.tespat.web.rest;

import com.github.thi747.tespat.domain.Categoria;
import com.github.thi747.tespat.repository.CategoriaRepository;
import com.github.thi747.tespat.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.github.thi747.tespat.domain.Categoria}.
 */
@RestController
@RequestMapping("/api/categorias")
@Transactional
public class CategoriaResource {

    private final Logger log = LoggerFactory.getLogger(CategoriaResource.class);

    private static final String ENTITY_NAME = "categoria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoriaRepository categoriaRepository;

    public CategoriaResource(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    /**
     * {@code POST  /categorias} : Create a new categoria.
     *
     * @param categoria the categoria to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoria, or with status {@code 400 (Bad Request)} if the categoria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Categoria> createCategoria(@Valid @RequestBody Categoria categoria) throws URISyntaxException {
        log.debug("REST request to save Categoria : {}", categoria);
        if (categoriaRepository.existsById(categoria.getNome())) {
            throw new BadRequestAlertException("categoria already exists", ENTITY_NAME, "idexists");
        }
        categoria = categoriaRepository.save(categoria);
        return ResponseEntity.created(new URI("/api/categorias/" + categoria.getNome()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, categoria.getNome()))
            .body(categoria);
    }

    /**
     * {@code GET  /categorias} : get all the categorias.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categorias in body.
     */
    @GetMapping("")
    public List<Categoria> getAllCategorias() {
        log.debug("REST request to get all Categorias");
        return categoriaRepository.findAll();
    }

    /**
     * {@code GET  /categorias/:id} : get the "id" categoria.
     *
     * @param id the id of the categoria to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoria, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getCategoria(@PathVariable("id") String id) {
        log.debug("REST request to get Categoria : {}", id);
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(categoria);
    }

    /**
     * {@code DELETE  /categorias/:id} : delete the "id" categoria.
     *
     * @param id the id of the categoria to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable("id") String id) {
        log.debug("REST request to delete Categoria : {}", id);
        categoriaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
