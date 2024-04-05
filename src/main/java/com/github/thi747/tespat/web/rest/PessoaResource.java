package com.github.thi747.tespat.web.rest;

import com.github.thi747.tespat.domain.Pessoa;
import com.github.thi747.tespat.repository.PessoaRepository;
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
 * REST controller for managing {@link com.github.thi747.tespat.domain.Pessoa}.
 */
@RestController
@RequestMapping("/api/pessoas")
@Transactional
public class PessoaResource {

    private final Logger log = LoggerFactory.getLogger(PessoaResource.class);

    private static final String ENTITY_NAME = "pessoa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PessoaRepository pessoaRepository;

    public PessoaResource(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    /**
     * {@code POST  /pessoas} : Create a new pessoa.
     *
     * @param pessoa the pessoa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pessoa, or with status {@code 400 (Bad Request)} if the pessoa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Pessoa> createPessoa(@Valid @RequestBody Pessoa pessoa) throws URISyntaxException {
        log.debug("REST request to save Pessoa : {}", pessoa);
        if (pessoaRepository.existsById(pessoa.getUsuario())) {
            throw new BadRequestAlertException("pessoa already exists", ENTITY_NAME, "idexists");
        }
        pessoa = pessoaRepository.save(pessoa);
        return ResponseEntity.created(new URI("/api/pessoas/" + pessoa.getUsuario()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, pessoa.getUsuario()))
            .body(pessoa);
    }

    /**
     * {@code PUT  /pessoas/:usuario} : Updates an existing pessoa.
     *
     * @param usuario the id of the pessoa to save.
     * @param pessoa the pessoa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pessoa,
     * or with status {@code 400 (Bad Request)} if the pessoa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pessoa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{usuario}")
    public ResponseEntity<Pessoa> updatePessoa(
        @PathVariable(value = "usuario", required = false) final String usuario,
        @Valid @RequestBody Pessoa pessoa
    ) throws URISyntaxException {
        log.debug("REST request to update Pessoa : {}, {}", usuario, pessoa);
        if (pessoa.getUsuario() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(usuario, pessoa.getUsuario())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pessoaRepository.existsById(usuario)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        pessoa.setIsPersisted();
        pessoa = pessoaRepository.save(pessoa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pessoa.getUsuario()))
            .body(pessoa);
    }

    /**
     * {@code PATCH  /pessoas/:usuario} : Partial updates given fields of an existing pessoa, field will ignore if it is null
     *
     * @param usuario the id of the pessoa to save.
     * @param pessoa the pessoa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pessoa,
     * or with status {@code 400 (Bad Request)} if the pessoa is not valid,
     * or with status {@code 404 (Not Found)} if the pessoa is not found,
     * or with status {@code 500 (Internal Server Error)} if the pessoa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{usuario}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Pessoa> partialUpdatePessoa(
        @PathVariable(value = "usuario", required = false) final String usuario,
        @NotNull @RequestBody Pessoa pessoa
    ) throws URISyntaxException {
        log.debug("REST request to partial update Pessoa partially : {}, {}", usuario, pessoa);
        if (pessoa.getUsuario() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(usuario, pessoa.getUsuario())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pessoaRepository.existsById(usuario)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Pessoa> result = pessoaRepository
            .findById(pessoa.getUsuario())
            .map(existingPessoa -> {
                if (pessoa.getNome() != null) {
                    existingPessoa.setNome(pessoa.getNome());
                }
                if (pessoa.getCpf() != null) {
                    existingPessoa.setCpf(pessoa.getCpf());
                }
                if (pessoa.getEmail() != null) {
                    existingPessoa.setEmail(pessoa.getEmail());
                }
                if (pessoa.getAtivo() != null) {
                    existingPessoa.setAtivo(pessoa.getAtivo());
                }
                if (pessoa.getEndereco() != null) {
                    existingPessoa.setEndereco(pessoa.getEndereco());
                }
                if (pessoa.getCidade() != null) {
                    existingPessoa.setCidade(pessoa.getCidade());
                }
                if (pessoa.getEstado() != null) {
                    existingPessoa.setEstado(pessoa.getEstado());
                }

                return existingPessoa;
            })
            .map(pessoaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pessoa.getUsuario())
        );
    }

    /**
     * {@code GET  /pessoas} : get all the pessoas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pessoas in body.
     */
    @GetMapping("")
    public List<Pessoa> getAllPessoas() {
        log.debug("REST request to get all Pessoas");
        return pessoaRepository.findAll();
    }

    /**
     * {@code GET  /pessoas/:id} : get the "id" pessoa.
     *
     * @param id the id of the pessoa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pessoa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> getPessoa(@PathVariable("id") String id) {
        log.debug("REST request to get Pessoa : {}", id);
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pessoa);
    }

    /**
     * {@code DELETE  /pessoas/:id} : delete the "id" pessoa.
     *
     * @param id the id of the pessoa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePessoa(@PathVariable("id") String id) {
        log.debug("REST request to delete Pessoa : {}", id);
        pessoaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
