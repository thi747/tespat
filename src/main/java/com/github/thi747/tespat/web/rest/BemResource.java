package com.github.thi747.tespat.web.rest;

import com.github.thi747.tespat.repository.BemRepository;
import com.github.thi747.tespat.service.BemService;
import com.github.thi747.tespat.service.dto.BemDTO;
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
 * REST controller for managing {@link com.github.thi747.tespat.domain.Bem}.
 */
@RestController
@RequestMapping("/api/bens")
public class BemResource {

    private final Logger log = LoggerFactory.getLogger(BemResource.class);

    private static final String ENTITY_NAME = "bem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BemService bemService;

    private final BemRepository bemRepository;

    public BemResource(BemService bemService, BemRepository bemRepository) {
        this.bemService = bemService;
        this.bemRepository = bemRepository;
    }

    /**
     * {@code POST  /bens} : Create a new bem.
     *
     * @param bemDTO the bemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bemDTO, or with status {@code 400 (Bad Request)} if the bem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BemDTO> createBem(@Valid @RequestBody BemDTO bemDTO) throws URISyntaxException {
        log.debug("REST request to save Bem : {}", bemDTO);
        if (bemDTO.getId() != null) {
            throw new BadRequestAlertException("A new bem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bemDTO = bemService.save(bemDTO);
        return ResponseEntity.created(new URI("/api/bens/" + bemDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, bemDTO.getId().toString()))
            .body(bemDTO);
    }

    /**
     * {@code PUT  /bens/:id} : Updates an existing bem.
     *
     * @param id the id of the bemDTO to save.
     * @param bemDTO the bemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bemDTO,
     * or with status {@code 400 (Bad Request)} if the bemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BemDTO> updateBem(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody BemDTO bemDTO)
        throws URISyntaxException {
        log.debug("REST request to update Bem : {}, {}", id, bemDTO);
        if (bemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bemDTO = bemService.update(bemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bemDTO.getId().toString()))
            .body(bemDTO);
    }

    /**
     * {@code PATCH  /bens/:id} : Partial updates given fields of an existing bem, field will ignore if it is null
     *
     * @param id the id of the bemDTO to save.
     * @param bemDTO the bemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bemDTO,
     * or with status {@code 400 (Bad Request)} if the bemDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bemDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BemDTO> partialUpdateBem(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BemDTO bemDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bem partially : {}, {}", id, bemDTO);
        if (bemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BemDTO> result = bemService.partialUpdate(bemDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bemDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bens} : get all the bens.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bens in body.
     */
    @GetMapping("")
    public List<BemDTO> getAllBens(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all Bens");
        return bemService.findAll();
    }

    /**
     * {@code GET  /bens/:id} : get the "id" bem.
     *
     * @param id the id of the bemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BemDTO> getBem(@PathVariable("id") Long id) {
        log.debug("REST request to get Bem : {}", id);
        Optional<BemDTO> bemDTO = bemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bemDTO);
    }

    /**
     * {@code DELETE  /bens/:id} : delete the "id" bem.
     *
     * @param id the id of the bemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBem(@PathVariable("id") Long id) {
        log.debug("REST request to delete Bem : {}", id);
        bemService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
