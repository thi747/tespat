package com.github.thi747.tespat.web.rest;

import com.github.thi747.tespat.repository.PessoaRepository;
import com.github.thi747.tespat.service.PessoaService;
import com.github.thi747.tespat.service.dto.PessoaDTO;
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
 * REST controller for managing {@link com.github.thi747.tespat.domain.Pessoa}.
 */
@RestController
@RequestMapping("/api/pessoas")
public class PessoaResource {

    private final Logger log = LoggerFactory.getLogger(PessoaResource.class);

    private static final String ENTITY_NAME = "pessoa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PessoaService pessoaService;

    private final PessoaRepository pessoaRepository;

    public PessoaResource(PessoaService pessoaService, PessoaRepository pessoaRepository) {
        this.pessoaService = pessoaService;
        this.pessoaRepository = pessoaRepository;
    }

    /**
     * {@code POST  /pessoas} : Create a new pessoa.
     *
     * @param pessoaDTO the pessoaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pessoaDTO, or with status {@code 400 (Bad Request)} if the pessoa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PessoaDTO> createPessoa(@Valid @RequestBody PessoaDTO pessoaDTO) throws URISyntaxException {
        log.debug("REST request to save Pessoa : {}", pessoaDTO);
        if (pessoaDTO.getId() != null) {
            throw new BadRequestAlertException("A new pessoa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        pessoaDTO = pessoaService.save(pessoaDTO);
        return ResponseEntity.created(new URI("/api/pessoas/" + pessoaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, pessoaDTO.getId().toString()))
            .body(pessoaDTO);
    }

    /**
     * {@code PUT  /pessoas/:id} : Updates an existing pessoa.
     *
     * @param id the id of the pessoaDTO to save.
     * @param pessoaDTO the pessoaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pessoaDTO,
     * or with status {@code 400 (Bad Request)} if the pessoaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pessoaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PessoaDTO> updatePessoa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PessoaDTO pessoaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Pessoa : {}, {}", id, pessoaDTO);
        if (pessoaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pessoaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pessoaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        pessoaDTO = pessoaService.update(pessoaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pessoaDTO.getId().toString()))
            .body(pessoaDTO);
    }

    /**
     * {@code PATCH  /pessoas/:id} : Partial updates given fields of an existing pessoa, field will ignore if it is null
     *
     * @param id the id of the pessoaDTO to save.
     * @param pessoaDTO the pessoaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pessoaDTO,
     * or with status {@code 400 (Bad Request)} if the pessoaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the pessoaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the pessoaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PessoaDTO> partialUpdatePessoa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PessoaDTO pessoaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Pessoa partially : {}, {}", id, pessoaDTO);
        if (pessoaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pessoaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pessoaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PessoaDTO> result = pessoaService.partialUpdate(pessoaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pessoaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /pessoas} : get all the pessoas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pessoas in body.
     */
    @GetMapping("")
    public List<PessoaDTO> getAllPessoas() {
        log.debug("REST request to get all Pessoas");
        return pessoaService.findAll();
    }

    /**
     * {@code GET  /pessoas/:id} : get the "id" pessoa.
     *
     * @param id the id of the pessoaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pessoaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> getPessoa(@PathVariable("id") Long id) {
        log.debug("REST request to get Pessoa : {}", id);
        Optional<PessoaDTO> pessoaDTO = pessoaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pessoaDTO);
    }

    /**
     * {@code DELETE  /pessoas/:id} : delete the "id" pessoa.
     *
     * @param id the id of the pessoaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePessoa(@PathVariable("id") Long id) {
        log.debug("REST request to delete Pessoa : {}", id);
        pessoaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
