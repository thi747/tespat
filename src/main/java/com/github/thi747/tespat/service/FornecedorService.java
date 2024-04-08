package com.github.thi747.tespat.service;

import com.github.thi747.tespat.domain.Fornecedor;
import com.github.thi747.tespat.repository.FornecedorRepository;
import com.github.thi747.tespat.service.dto.FornecedorDTO;
import com.github.thi747.tespat.service.mapper.FornecedorMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.github.thi747.tespat.domain.Fornecedor}.
 */
@Service
@Transactional
public class FornecedorService {

    private final Logger log = LoggerFactory.getLogger(FornecedorService.class);

    private final FornecedorRepository fornecedorRepository;

    private final FornecedorMapper fornecedorMapper;

    public FornecedorService(FornecedorRepository fornecedorRepository, FornecedorMapper fornecedorMapper) {
        this.fornecedorRepository = fornecedorRepository;
        this.fornecedorMapper = fornecedorMapper;
    }

    /**
     * Save a fornecedor.
     *
     * @param fornecedorDTO the entity to save.
     * @return the persisted entity.
     */
    public FornecedorDTO save(FornecedorDTO fornecedorDTO) {
        log.debug("Request to save Fornecedor : {}", fornecedorDTO);
        Fornecedor fornecedor = fornecedorMapper.toEntity(fornecedorDTO);
        fornecedor = fornecedorRepository.save(fornecedor);
        return fornecedorMapper.toDto(fornecedor);
    }

    /**
     * Update a fornecedor.
     *
     * @param fornecedorDTO the entity to save.
     * @return the persisted entity.
     */
    public FornecedorDTO update(FornecedorDTO fornecedorDTO) {
        log.debug("Request to update Fornecedor : {}", fornecedorDTO);
        Fornecedor fornecedor = fornecedorMapper.toEntity(fornecedorDTO);
        fornecedor = fornecedorRepository.save(fornecedor);
        return fornecedorMapper.toDto(fornecedor);
    }

    /**
     * Partially update a fornecedor.
     *
     * @param fornecedorDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FornecedorDTO> partialUpdate(FornecedorDTO fornecedorDTO) {
        log.debug("Request to partially update Fornecedor : {}", fornecedorDTO);

        return fornecedorRepository
            .findById(fornecedorDTO.getId())
            .map(existingFornecedor -> {
                fornecedorMapper.partialUpdate(existingFornecedor, fornecedorDTO);

                return existingFornecedor;
            })
            .map(fornecedorRepository::save)
            .map(fornecedorMapper::toDto);
    }

    /**
     * Get all the fornecedors.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FornecedorDTO> findAll() {
        log.debug("Request to get all Fornecedors");
        return fornecedorRepository.findAll().stream().map(fornecedorMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one fornecedor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FornecedorDTO> findOne(Long id) {
        log.debug("Request to get Fornecedor : {}", id);
        return fornecedorRepository.findById(id).map(fornecedorMapper::toDto);
    }

    /**
     * Delete the fornecedor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Fornecedor : {}", id);
        fornecedorRepository.deleteById(id);
    }
}
