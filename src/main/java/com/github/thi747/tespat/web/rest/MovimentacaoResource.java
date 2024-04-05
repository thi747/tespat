package com.github.thi747.tespat.web.rest;

import com.github.thi747.tespat.domain.Movimentacao;
import com.github.thi747.tespat.repository.MovimentacaoRepository;
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
 * REST controller for managing {@link com.github.thi747.tespat.domain.Movimentacao}.
 */
@RestController
@RequestMapping("/api/movimentacaos")
@Transactional
public class MovimentacaoResource {

    private final Logger log = LoggerFactory.getLogger(MovimentacaoResource.class);

    private static final String ENTITY_NAME = "movimentacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MovimentacaoRepository movimentacaoRepository;

    public MovimentacaoResource(MovimentacaoRepository movimentacaoRepository) {
        this.movimentacaoRepository = movimentacaoRepository;
    }

    /**
     * {@code POST  /movimentacaos} : Create a new movimentacao.
     *
     * @param movimentacao the movimentacao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new movimentacao, or with status {@code 400 (Bad Request)} if the movimentacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Movimentacao> createMovimentacao(@Valid @RequestBody Movimentacao movimentacao) throws URISyntaxException {
        log.debug("REST request to save Movimentacao : {}", movimentacao);
        if (movimentacao.getId() != null) {
            throw new BadRequestAlertException("A new movimentacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        movimentacao = movimentacaoRepository.save(movimentacao);
        return ResponseEntity.created(new URI("/api/movimentacaos/" + movimentacao.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, movimentacao.getId().toString()))
            .body(movimentacao);
    }

    /**
     * {@code PUT  /movimentacaos/:id} : Updates an existing movimentacao.
     *
     * @param id the id of the movimentacao to save.
     * @param movimentacao the movimentacao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated movimentacao,
     * or with status {@code 400 (Bad Request)} if the movimentacao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the movimentacao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Movimentacao> updateMovimentacao(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Movimentacao movimentacao
    ) throws URISyntaxException {
        log.debug("REST request to update Movimentacao : {}, {}", id, movimentacao);
        if (movimentacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, movimentacao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!movimentacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        movimentacao = movimentacaoRepository.save(movimentacao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, movimentacao.getId().toString()))
            .body(movimentacao);
    }

    /**
     * {@code PATCH  /movimentacaos/:id} : Partial updates given fields of an existing movimentacao, field will ignore if it is null
     *
     * @param id the id of the movimentacao to save.
     * @param movimentacao the movimentacao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated movimentacao,
     * or with status {@code 400 (Bad Request)} if the movimentacao is not valid,
     * or with status {@code 404 (Not Found)} if the movimentacao is not found,
     * or with status {@code 500 (Internal Server Error)} if the movimentacao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Movimentacao> partialUpdateMovimentacao(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Movimentacao movimentacao
    ) throws URISyntaxException {
        log.debug("REST request to partial update Movimentacao partially : {}, {}", id, movimentacao);
        if (movimentacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, movimentacao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!movimentacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Movimentacao> result = movimentacaoRepository
            .findById(movimentacao.getId())
            .map(existingMovimentacao -> {
                if (movimentacao.getDescricao() != null) {
                    existingMovimentacao.setDescricao(movimentacao.getDescricao());
                }
                if (movimentacao.getData() != null) {
                    existingMovimentacao.setData(movimentacao.getData());
                }
                if (movimentacao.getTipo() != null) {
                    existingMovimentacao.setTipo(movimentacao.getTipo());
                }

                return existingMovimentacao;
            })
            .map(movimentacaoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, movimentacao.getId().toString())
        );
    }

    /**
     * {@code GET  /movimentacaos} : get all the movimentacaos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of movimentacaos in body.
     */
    @GetMapping("")
    public List<Movimentacao> getAllMovimentacaos() {
        log.debug("REST request to get all Movimentacaos");
        return movimentacaoRepository.findAll();
    }

    /**
     * {@code GET  /movimentacaos/:id} : get the "id" movimentacao.
     *
     * @param id the id of the movimentacao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the movimentacao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Movimentacao> getMovimentacao(@PathVariable("id") Long id) {
        log.debug("REST request to get Movimentacao : {}", id);
        Optional<Movimentacao> movimentacao = movimentacaoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(movimentacao);
    }

    /**
     * {@code DELETE  /movimentacaos/:id} : delete the "id" movimentacao.
     *
     * @param id the id of the movimentacao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovimentacao(@PathVariable("id") Long id) {
        log.debug("REST request to delete Movimentacao : {}", id);
        movimentacaoRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
