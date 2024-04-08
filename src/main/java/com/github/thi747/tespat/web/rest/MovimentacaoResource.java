package com.github.thi747.tespat.web.rest;

import com.github.thi747.tespat.repository.MovimentacaoRepository;
import com.github.thi747.tespat.service.MovimentacaoService;
import com.github.thi747.tespat.service.dto.MovimentacaoDTO;
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
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.github.thi747.tespat.domain.Movimentacao}.
 */
@RestController
@RequestMapping("/api/movimentacaos")
public class MovimentacaoResource {

    private final Logger log = LoggerFactory.getLogger(MovimentacaoResource.class);

    private static final String ENTITY_NAME = "movimentacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MovimentacaoService movimentacaoService;

    private final MovimentacaoRepository movimentacaoRepository;

    public MovimentacaoResource(MovimentacaoService movimentacaoService, MovimentacaoRepository movimentacaoRepository) {
        this.movimentacaoService = movimentacaoService;
        this.movimentacaoRepository = movimentacaoRepository;
    }

    /**
     * {@code POST  /movimentacaos} : Create a new movimentacao.
     *
     * @param movimentacaoDTO the movimentacaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new movimentacaoDTO, or with status {@code 400 (Bad Request)} if the movimentacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MovimentacaoDTO> createMovimentacao(@Valid @RequestBody MovimentacaoDTO movimentacaoDTO)
        throws URISyntaxException {
        log.debug("REST request to save Movimentacao : {}", movimentacaoDTO);
        if (movimentacaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new movimentacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        movimentacaoDTO = movimentacaoService.save(movimentacaoDTO);
        return ResponseEntity.created(new URI("/api/movimentacaos/" + movimentacaoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, movimentacaoDTO.getId().toString()))
            .body(movimentacaoDTO);
    }

    /**
     * {@code PUT  /movimentacaos/:id} : Updates an existing movimentacao.
     *
     * @param id the id of the movimentacaoDTO to save.
     * @param movimentacaoDTO the movimentacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated movimentacaoDTO,
     * or with status {@code 400 (Bad Request)} if the movimentacaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the movimentacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MovimentacaoDTO> updateMovimentacao(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MovimentacaoDTO movimentacaoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Movimentacao : {}, {}", id, movimentacaoDTO);
        if (movimentacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, movimentacaoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!movimentacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        movimentacaoDTO = movimentacaoService.update(movimentacaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, movimentacaoDTO.getId().toString()))
            .body(movimentacaoDTO);
    }

    /**
     * {@code PATCH  /movimentacaos/:id} : Partial updates given fields of an existing movimentacao, field will ignore if it is null
     *
     * @param id the id of the movimentacaoDTO to save.
     * @param movimentacaoDTO the movimentacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated movimentacaoDTO,
     * or with status {@code 400 (Bad Request)} if the movimentacaoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the movimentacaoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the movimentacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MovimentacaoDTO> partialUpdateMovimentacao(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MovimentacaoDTO movimentacaoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Movimentacao partially : {}, {}", id, movimentacaoDTO);
        if (movimentacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, movimentacaoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!movimentacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MovimentacaoDTO> result = movimentacaoService.partialUpdate(movimentacaoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, movimentacaoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /movimentacaos} : get all the movimentacaos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of movimentacaos in body.
     */
    @GetMapping("")
    public List<MovimentacaoDTO> getAllMovimentacaos(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all Movimentacaos");
        return movimentacaoService.findAll();
    }

    /**
     * {@code GET  /movimentacaos/:id} : get the "id" movimentacao.
     *
     * @param id the id of the movimentacaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the movimentacaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MovimentacaoDTO> getMovimentacao(@PathVariable("id") Long id) {
        log.debug("REST request to get Movimentacao : {}", id);
        Optional<MovimentacaoDTO> movimentacaoDTO = movimentacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(movimentacaoDTO);
    }

    /**
     * {@code DELETE  /movimentacaos/:id} : delete the "id" movimentacao.
     *
     * @param id the id of the movimentacaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovimentacao(@PathVariable("id") Long id) {
        log.debug("REST request to delete Movimentacao : {}", id);
        movimentacaoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
