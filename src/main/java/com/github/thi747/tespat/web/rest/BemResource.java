package com.github.thi747.tespat.web.rest;

import com.github.thi747.tespat.domain.Bem;
import com.github.thi747.tespat.repository.BemRepository;
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
 * REST controller for managing {@link com.github.thi747.tespat.domain.Bem}.
 */
@RestController
@RequestMapping("/api/bems")
@Transactional
public class BemResource {

    private final Logger log = LoggerFactory.getLogger(BemResource.class);

    private static final String ENTITY_NAME = "bem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BemRepository bemRepository;

    public BemResource(BemRepository bemRepository) {
        this.bemRepository = bemRepository;
    }

    /**
     * {@code POST  /bems} : Create a new bem.
     *
     * @param bem the bem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bem, or with status {@code 400 (Bad Request)} if the bem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Bem> createBem(@Valid @RequestBody Bem bem) throws URISyntaxException {
        log.debug("REST request to save Bem : {}", bem);
        if (bem.getPatrimonio() != null) {
            throw new BadRequestAlertException("A new bem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bem = bemRepository.save(bem);
        return ResponseEntity.created(new URI("/api/bems/" + bem.getPatrimonio()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, bem.getPatrimonio().toString()))
            .body(bem);
    }

    /**
     * {@code PUT  /bems/:patrimonio} : Updates an existing bem.
     *
     * @param patrimonio the id of the bem to save.
     * @param bem the bem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bem,
     * or with status {@code 400 (Bad Request)} if the bem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{patrimonio}")
    public ResponseEntity<Bem> updateBem(
        @PathVariable(value = "patrimonio", required = false) final Long patrimonio,
        @Valid @RequestBody Bem bem
    ) throws URISyntaxException {
        log.debug("REST request to update Bem : {}, {}", patrimonio, bem);
        if (bem.getPatrimonio() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(patrimonio, bem.getPatrimonio())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bemRepository.existsById(patrimonio)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bem = bemRepository.save(bem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bem.getPatrimonio().toString()))
            .body(bem);
    }

    /**
     * {@code PATCH  /bems/:patrimonio} : Partial updates given fields of an existing bem, field will ignore if it is null
     *
     * @param patrimonio the id of the bem to save.
     * @param bem the bem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bem,
     * or with status {@code 400 (Bad Request)} if the bem is not valid,
     * or with status {@code 404 (Not Found)} if the bem is not found,
     * or with status {@code 500 (Internal Server Error)} if the bem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{patrimonio}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bem> partialUpdateBem(
        @PathVariable(value = "patrimonio", required = false) final Long patrimonio,
        @NotNull @RequestBody Bem bem
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bem partially : {}, {}", patrimonio, bem);
        if (bem.getPatrimonio() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(patrimonio, bem.getPatrimonio())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bemRepository.existsById(patrimonio)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bem> result = bemRepository
            .findById(bem.getPatrimonio())
            .map(existingBem -> {
                if (bem.getNome() != null) {
                    existingBem.setNome(bem.getNome());
                }
                if (bem.getDescricao() != null) {
                    existingBem.setDescricao(bem.getDescricao());
                }
                if (bem.getObservacoes() != null) {
                    existingBem.setObservacoes(bem.getObservacoes());
                }
                if (bem.getNumeroDeSerie() != null) {
                    existingBem.setNumeroDeSerie(bem.getNumeroDeSerie());
                }
                if (bem.getDataAquisicao() != null) {
                    existingBem.setDataAquisicao(bem.getDataAquisicao());
                }
                if (bem.getValorCompra() != null) {
                    existingBem.setValorCompra(bem.getValorCompra());
                }
                if (bem.getValorAtual() != null) {
                    existingBem.setValorAtual(bem.getValorAtual());
                }
                if (bem.getEstado() != null) {
                    existingBem.setEstado(bem.getEstado());
                }
                if (bem.getStatus() != null) {
                    existingBem.setStatus(bem.getStatus());
                }

                return existingBem;
            })
            .map(bemRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bem.getPatrimonio().toString())
        );
    }

    /**
     * {@code GET  /bems} : get all the bems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bems in body.
     */
    @GetMapping("")
    public List<Bem> getAllBems() {
        log.debug("REST request to get all Bems");
        return bemRepository.findAll();
    }

    /**
     * {@code GET  /bems/:id} : get the "id" bem.
     *
     * @param id the id of the bem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Bem> getBem(@PathVariable("id") Long id) {
        log.debug("REST request to get Bem : {}", id);
        Optional<Bem> bem = bemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bem);
    }

    /**
     * {@code DELETE  /bems/:id} : delete the "id" bem.
     *
     * @param id the id of the bem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBem(@PathVariable("id") Long id) {
        log.debug("REST request to delete Bem : {}", id);
        bemRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
