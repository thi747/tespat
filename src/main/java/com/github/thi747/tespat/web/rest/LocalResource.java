package com.github.thi747.tespat.web.rest;

import com.github.thi747.tespat.domain.Local;
import com.github.thi747.tespat.repository.LocalRepository;
import com.github.thi747.tespat.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
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
 * REST controller for managing {@link com.github.thi747.tespat.domain.Local}.
 */
@RestController
@RequestMapping("/api/locals")
@Transactional
public class LocalResource {

    private final Logger log = LoggerFactory.getLogger(LocalResource.class);

    private static final String ENTITY_NAME = "local";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocalRepository localRepository;

    public LocalResource(LocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    /**
     * {@code POST  /locals} : Create a new local.
     *
     * @param local the local to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new local, or with status {@code 400 (Bad Request)} if the local has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Local> createLocal(@Valid @RequestBody Local local) throws URISyntaxException {
        log.debug("REST request to save Local : {}", local);
        if (localRepository.existsById(local.getNome())) {
            throw new BadRequestAlertException("local already exists", ENTITY_NAME, "idexists");
        }
        local = localRepository.save(local);
        return ResponseEntity.created(new URI("/api/locals/" + local.getNome()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, local.getNome()))
            .body(local);
    }

    /**
     * {@code PUT  /locals/:nome} : Updates an existing local.
     *
     * @param nome the id of the local to save.
     * @param local the local to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated local,
     * or with status {@code 400 (Bad Request)} if the local is not valid,
     * or with status {@code 500 (Internal Server Error)} if the local couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{nome}")
    public ResponseEntity<Local> updateLocal(
        @PathVariable(value = "nome", required = false) final String nome,
        @Valid @RequestBody Local local
    ) throws URISyntaxException {
        log.debug("REST request to update Local : {}, {}", nome, local);
        if (local.getNome() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(nome, local.getNome())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!localRepository.existsById(nome)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        local.setIsPersisted();
        local = localRepository.save(local);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, local.getNome()))
            .body(local);
    }

    /**
     * {@code PATCH  /locals/:nome} : Partial updates given fields of an existing local, field will ignore if it is null
     *
     * @param nome the id of the local to save.
     * @param local the local to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated local,
     * or with status {@code 400 (Bad Request)} if the local is not valid,
     * or with status {@code 404 (Not Found)} if the local is not found,
     * or with status {@code 500 (Internal Server Error)} if the local couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{nome}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Local> partialUpdateLocal(
        @PathVariable(value = "nome", required = false) final String nome,
        @NotNull @RequestBody Local local
    ) throws URISyntaxException {
        log.debug("REST request to partial update Local partially : {}, {}", nome, local);
        if (local.getNome() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(nome, local.getNome())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!localRepository.existsById(nome)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Local> result = localRepository
            .findById(local.getNome())
            .map(existingLocal -> {
                if (local.getDescricao() != null) {
                    existingLocal.setDescricao(local.getDescricao());
                }
                if (local.getSala() != null) {
                    existingLocal.setSala(local.getSala());
                }

                return existingLocal;
            })
            .map(localRepository::save);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, local.getNome()));
    }

    /**
     * {@code GET  /locals} : get all the locals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of locals in body.
     */
    @GetMapping("")
    public List<Local> getAllLocals() {
        log.debug("REST request to get all Locals");
        return localRepository.findAll();
    }

    /**
     * {@code GET  /locals/:id} : get the "id" local.
     *
     * @param id the id of the local to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the local, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Local> getLocal(@PathVariable("id") String id) {
        log.debug("REST request to get Local : {}", id);
        Optional<Local> local = localRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(local);
    }

    /**
     * {@code DELETE  /locals/:id} : delete the "id" local.
     *
     * @param id the id of the local to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocal(@PathVariable("id") String id) {
        log.debug("REST request to delete Local : {}", id);
        localRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
