package com.github.thi747.tespat.service;

import com.github.thi747.tespat.domain.Pessoa;
import com.github.thi747.tespat.repository.PessoaRepository;
import com.github.thi747.tespat.service.dto.PessoaDTO;
import com.github.thi747.tespat.service.mapper.PessoaMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.github.thi747.tespat.domain.Pessoa}.
 */
@Service
@Transactional
public class PessoaService {

    private final Logger log = LoggerFactory.getLogger(PessoaService.class);

    private final PessoaRepository pessoaRepository;

    private final PessoaMapper pessoaMapper;

    public PessoaService(PessoaRepository pessoaRepository, PessoaMapper pessoaMapper) {
        this.pessoaRepository = pessoaRepository;
        this.pessoaMapper = pessoaMapper;
    }

    /**
     * Save a pessoa.
     *
     * @param pessoaDTO the entity to save.
     * @return the persisted entity.
     */
    public PessoaDTO save(PessoaDTO pessoaDTO) {
        log.debug("Request to save Pessoa : {}", pessoaDTO);
        Pessoa pessoa = pessoaMapper.toEntity(pessoaDTO);
        pessoa = pessoaRepository.save(pessoa);
        return pessoaMapper.toDto(pessoa);
    }

    /**
     * Update a pessoa.
     *
     * @param pessoaDTO the entity to save.
     * @return the persisted entity.
     */
    public PessoaDTO update(PessoaDTO pessoaDTO) {
        log.debug("Request to update Pessoa : {}", pessoaDTO);
        Pessoa pessoa = pessoaMapper.toEntity(pessoaDTO);
        pessoa = pessoaRepository.save(pessoa);
        return pessoaMapper.toDto(pessoa);
    }

    /**
     * Partially update a pessoa.
     *
     * @param pessoaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PessoaDTO> partialUpdate(PessoaDTO pessoaDTO) {
        log.debug("Request to partially update Pessoa : {}", pessoaDTO);

        return pessoaRepository
            .findById(pessoaDTO.getId())
            .map(existingPessoa -> {
                pessoaMapper.partialUpdate(existingPessoa, pessoaDTO);

                return existingPessoa;
            })
            .map(pessoaRepository::save)
            .map(pessoaMapper::toDto);
    }

    /**
     * Get all the pessoas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PessoaDTO> findAll() {
        log.debug("Request to get all Pessoas");
        return pessoaRepository.findAll().stream().map(pessoaMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one pessoa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PessoaDTO> findOne(Long id) {
        log.debug("Request to get Pessoa : {}", id);
        return pessoaRepository.findById(id).map(pessoaMapper::toDto);
    }

    /**
     * Delete the pessoa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Pessoa : {}", id);
        pessoaRepository.deleteById(id);
    }
}
