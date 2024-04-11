package com.github.thi747.tespat.web.rest;

import com.github.thi747.tespat.repository.FornecedorRepository;
import com.github.thi747.tespat.service.FornecedorService;
import com.github.thi747.tespat.service.dto.FornecedorDTO;
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
 * REST controller for managing {@link com.github.thi747.tespat.domain.Fornecedor}.
 */
@RestController
@RequestMapping("/api/fornecedores")
public class FornecedorResource {

    private final Logger log = LoggerFactory.getLogger(FornecedorResource.class);

    private static final String ENTITY_NAME = "fornecedor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FornecedorService fornecedorService;

    private final FornecedorRepository fornecedorRepository;

    public FornecedorResource(FornecedorService fornecedorService, FornecedorRepository fornecedorRepository) {
        this.fornecedorService = fornecedorService;
        this.fornecedorRepository = fornecedorRepository;
    }

    /**
     * {@code POST  /fornecedores} : Create a new fornecedor.
     *
     * @param fornecedorDTO the fornecedorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fornecedorDTO, or with status {@code 400 (Bad Request)} if the fornecedor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FornecedorDTO> createFornecedor(@Valid @RequestBody FornecedorDTO fornecedorDTO) throws URISyntaxException {
        log.debug("REST request to save Fornecedor : {}", fornecedorDTO);
        if (fornecedorDTO.getId() != null) {
            throw new BadRequestAlertException("A new fornecedor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        fornecedorDTO = fornecedorService.save(fornecedorDTO);
        return ResponseEntity.created(new URI("/api/fornecedores/" + fornecedorDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, fornecedorDTO.getId().toString()))
            .body(fornecedorDTO);
    }

    /**
     * {@code PUT  /fornecedores/:id} : Updates an existing fornecedor.
     *
     * @param id the id of the fornecedorDTO to save.
     * @param fornecedorDTO the fornecedorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fornecedorDTO,
     * or with status {@code 400 (Bad Request)} if the fornecedorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fornecedorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FornecedorDTO> updateFornecedor(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FornecedorDTO fornecedorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Fornecedor : {}, {}", id, fornecedorDTO);
        if (fornecedorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fornecedorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fornecedorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        fornecedorDTO = fornecedorService.update(fornecedorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fornecedorDTO.getId().toString()))
            .body(fornecedorDTO);
    }

    /**
     * {@code PATCH  /fornecedores/:id} : Partial updates given fields of an existing fornecedor, field will ignore if it is null
     *
     * @param id the id of the fornecedorDTO to save.
     * @param fornecedorDTO the fornecedorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fornecedorDTO,
     * or with status {@code 400 (Bad Request)} if the fornecedorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fornecedorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fornecedorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FornecedorDTO> partialUpdateFornecedor(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FornecedorDTO fornecedorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Fornecedor partially : {}, {}", id, fornecedorDTO);
        if (fornecedorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fornecedorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fornecedorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FornecedorDTO> result = fornecedorService.partialUpdate(fornecedorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fornecedorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /fornecedores} : get all the fornecedores.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fornecedores in body.
     */
    @GetMapping("")
    public List<FornecedorDTO> getAllFornecedores() {
        log.debug("REST request to get all Fornecedores");
        return fornecedorService.findAll();
    }

    /**
     * {@code GET  /fornecedores/:id} : get the "id" fornecedor.
     *
     * @param id the id of the fornecedorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fornecedorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FornecedorDTO> getFornecedor(@PathVariable("id") Long id) {
        log.debug("REST request to get Fornecedor : {}", id);
        Optional<FornecedorDTO> fornecedorDTO = fornecedorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fornecedorDTO);
    }

    /**
     * {@code DELETE  /fornecedores/:id} : delete the "id" fornecedor.
     *
     * @param id the id of the fornecedorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFornecedor(@PathVariable("id") Long id) {
        log.debug("REST request to delete Fornecedor : {}", id);
        fornecedorService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
