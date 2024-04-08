package com.github.thi747.tespat.web.rest;

import com.github.thi747.tespat.domain.Fornecedor;
import com.github.thi747.tespat.repository.FornecedorRepository;
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
 * REST controller for managing {@link com.github.thi747.tespat.domain.Fornecedor}.
 */
@RestController
@RequestMapping("/api/fornecedors")
@Transactional
public class FornecedorResource {

    private final Logger log = LoggerFactory.getLogger(FornecedorResource.class);

    private static final String ENTITY_NAME = "fornecedor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FornecedorRepository fornecedorRepository;

    public FornecedorResource(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    /**
     * {@code POST  /fornecedors} : Create a new fornecedor.
     *
     * @param fornecedor the fornecedor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fornecedor, or with status {@code 400 (Bad Request)} if the fornecedor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Fornecedor> createFornecedor(@Valid @RequestBody Fornecedor fornecedor) throws URISyntaxException {
        log.debug("REST request to save Fornecedor : {}", fornecedor);
        if (fornecedorRepository.existsById(fornecedor.getNome())) {
            throw new BadRequestAlertException("fornecedor already exists", ENTITY_NAME, "idexists");
        }
        fornecedor = fornecedorRepository.save(fornecedor);
        return ResponseEntity.created(new URI("/api/fornecedors/" + fornecedor.getNome()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, fornecedor.getNome()))
            .body(fornecedor);
    }

    /**
     * {@code PUT  /fornecedors/:nome} : Updates an existing fornecedor.
     *
     * @param nome the id of the fornecedor to save.
     * @param fornecedor the fornecedor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fornecedor,
     * or with status {@code 400 (Bad Request)} if the fornecedor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fornecedor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{nome}")
    public ResponseEntity<Fornecedor> updateFornecedor(
        @PathVariable(value = "nome", required = false) final String nome,
        @Valid @RequestBody Fornecedor fornecedor
    ) throws URISyntaxException {
        log.debug("REST request to update Fornecedor : {}, {}", nome, fornecedor);
        if (fornecedor.getNome() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(nome, fornecedor.getNome())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fornecedorRepository.existsById(nome)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        fornecedor.setIsPersisted();
        fornecedor = fornecedorRepository.save(fornecedor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fornecedor.getNome()))
            .body(fornecedor);
    }

    /**
     * {@code PATCH  /fornecedors/:nome} : Partial updates given fields of an existing fornecedor, field will ignore if it is null
     *
     * @param nome the id of the fornecedor to save.
     * @param fornecedor the fornecedor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fornecedor,
     * or with status {@code 400 (Bad Request)} if the fornecedor is not valid,
     * or with status {@code 404 (Not Found)} if the fornecedor is not found,
     * or with status {@code 500 (Internal Server Error)} if the fornecedor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{nome}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Fornecedor> partialUpdateFornecedor(
        @PathVariable(value = "nome", required = false) final String nome,
        @NotNull @RequestBody Fornecedor fornecedor
    ) throws URISyntaxException {
        log.debug("REST request to partial update Fornecedor partially : {}, {}", nome, fornecedor);
        if (fornecedor.getNome() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(nome, fornecedor.getNome())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fornecedorRepository.existsById(nome)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Fornecedor> result = fornecedorRepository
            .findById(fornecedor.getNome())
            .map(existingFornecedor -> {
                if (fornecedor.getDescricao() != null) {
                    existingFornecedor.setDescricao(fornecedor.getDescricao());
                }
                if (fornecedor.getCpfOuCnpj() != null) {
                    existingFornecedor.setCpfOuCnpj(fornecedor.getCpfOuCnpj());
                }
                if (fornecedor.getEmail() != null) {
                    existingFornecedor.setEmail(fornecedor.getEmail());
                }
                if (fornecedor.getTelefone() != null) {
                    existingFornecedor.setTelefone(fornecedor.getTelefone());
                }
                if (fornecedor.getEndereco() != null) {
                    existingFornecedor.setEndereco(fornecedor.getEndereco());
                }
                if (fornecedor.getCidade() != null) {
                    existingFornecedor.setCidade(fornecedor.getCidade());
                }
                if (fornecedor.getEstado() != null) {
                    existingFornecedor.setEstado(fornecedor.getEstado());
                }

                return existingFornecedor;
            })
            .map(fornecedorRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fornecedor.getNome())
        );
    }

    /**
     * {@code GET  /fornecedors} : get all the fornecedors.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fornecedors in body.
     */
    @GetMapping("")
    public List<Fornecedor> getAllFornecedors() {
        log.debug("REST request to get all Fornecedors");
        return fornecedorRepository.findAll();
    }

    /**
     * {@code GET  /fornecedors/:id} : get the "id" fornecedor.
     *
     * @param id the id of the fornecedor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fornecedor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Fornecedor> getFornecedor(@PathVariable("id") String id) {
        log.debug("REST request to get Fornecedor : {}", id);
        Optional<Fornecedor> fornecedor = fornecedorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(fornecedor);
    }

    /**
     * {@code DELETE  /fornecedors/:id} : delete the "id" fornecedor.
     *
     * @param id the id of the fornecedor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFornecedor(@PathVariable("id") String id) {
        log.debug("REST request to delete Fornecedor : {}", id);
        fornecedorRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
