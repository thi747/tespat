package com.github.thi747.tespat.service;

import com.github.thi747.tespat.domain.Bem;
import com.github.thi747.tespat.repository.BemRepository;
import com.github.thi747.tespat.service.dto.BemDTO;
import com.github.thi747.tespat.service.mapper.BemMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.github.thi747.tespat.domain.Bem}.
 */
@Service
@Transactional
public class BemService {

    private final Logger log = LoggerFactory.getLogger(BemService.class);

    private final BemRepository bemRepository;

    private final BemMapper bemMapper;

    public BemService(BemRepository bemRepository, BemMapper bemMapper) {
        this.bemRepository = bemRepository;
        this.bemMapper = bemMapper;
    }

    /**
     * Save a bem.
     *
     * @param bemDTO the entity to save.
     * @return the persisted entity.
     */
    public BemDTO save(BemDTO bemDTO) {
        log.debug("Request to save Bem : {}", bemDTO);
        Bem bem = bemMapper.toEntity(bemDTO);
        bem = bemRepository.save(bem);
        return bemMapper.toDto(bem);
    }

    /**
     * Update a bem.
     *
     * @param bemDTO the entity to save.
     * @return the persisted entity.
     */
    public BemDTO update(BemDTO bemDTO) {
        log.debug("Request to update Bem : {}", bemDTO);
        Bem bem = bemMapper.toEntity(bemDTO);
        bem = bemRepository.save(bem);
        return bemMapper.toDto(bem);
    }

    /**
     * Partially update a bem.
     *
     * @param bemDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BemDTO> partialUpdate(BemDTO bemDTO) {
        log.debug("Request to partially update Bem : {}", bemDTO);

        return bemRepository
            .findById(bemDTO.getId())
            .map(existingBem -> {
                bemMapper.partialUpdate(existingBem, bemDTO);

                return existingBem;
            })
            .map(bemRepository::save)
            .map(bemMapper::toDto);
    }

    /**
     * Get all the bens.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BemDTO> findAll() {
        log.debug("Request to get all Bens");
        return bemRepository.findAll().stream().map(bemMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the bens with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<BemDTO> findAllWithEagerRelationships(Pageable pageable) {
        return bemRepository.findAllWithEagerRelationships(pageable).map(bemMapper::toDto);
    }

    /**
     * Get one bem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BemDTO> findOne(Long id) {
        log.debug("Request to get Bem : {}", id);
        return bemRepository.findOneWithEagerRelationships(id).map(bemMapper::toDto);
    }

    /**
     * Delete the bem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Bem : {}", id);
        bemRepository.deleteById(id);
    }
}
