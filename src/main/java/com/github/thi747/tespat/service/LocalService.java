package com.github.thi747.tespat.service;

import com.github.thi747.tespat.domain.Local;
import com.github.thi747.tespat.repository.LocalRepository;
import com.github.thi747.tespat.service.dto.LocalDTO;
import com.github.thi747.tespat.service.mapper.LocalMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.github.thi747.tespat.domain.Local}.
 */
@Service
@Transactional
public class LocalService {

    private final Logger log = LoggerFactory.getLogger(LocalService.class);

    private final LocalRepository localRepository;

    private final LocalMapper localMapper;

    public LocalService(LocalRepository localRepository, LocalMapper localMapper) {
        this.localRepository = localRepository;
        this.localMapper = localMapper;
    }

    /**
     * Save a local.
     *
     * @param localDTO the entity to save.
     * @return the persisted entity.
     */
    public LocalDTO save(LocalDTO localDTO) {
        log.debug("Request to save Local : {}", localDTO);
        Local local = localMapper.toEntity(localDTO);
        local = localRepository.save(local);
        return localMapper.toDto(local);
    }

    /**
     * Update a local.
     *
     * @param localDTO the entity to save.
     * @return the persisted entity.
     */
    public LocalDTO update(LocalDTO localDTO) {
        log.debug("Request to update Local : {}", localDTO);
        Local local = localMapper.toEntity(localDTO);
        local = localRepository.save(local);
        return localMapper.toDto(local);
    }

    /**
     * Partially update a local.
     *
     * @param localDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LocalDTO> partialUpdate(LocalDTO localDTO) {
        log.debug("Request to partially update Local : {}", localDTO);

        return localRepository
            .findById(localDTO.getId())
            .map(existingLocal -> {
                localMapper.partialUpdate(existingLocal, localDTO);

                return existingLocal;
            })
            .map(localRepository::save)
            .map(localMapper::toDto);
    }

    /**
     * Get all the locais.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LocalDTO> findAll() {
        log.debug("Request to get all Locais");
        return localRepository.findAll().stream().map(localMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one local by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LocalDTO> findOne(Long id) {
        log.debug("Request to get Local : {}", id);
        return localRepository.findById(id).map(localMapper::toDto);
    }

    /**
     * Delete the local by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Local : {}", id);
        localRepository.deleteById(id);
    }
}
