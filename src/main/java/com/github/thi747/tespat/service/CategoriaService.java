package com.github.thi747.tespat.service;

import com.github.thi747.tespat.domain.Categoria;
import com.github.thi747.tespat.repository.CategoriaRepository;
import com.github.thi747.tespat.service.dto.CategoriaDTO;
import com.github.thi747.tespat.service.mapper.CategoriaMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.github.thi747.tespat.domain.Categoria}.
 */
@Service
@Transactional
public class CategoriaService {

    private final Logger log = LoggerFactory.getLogger(CategoriaService.class);

    private final CategoriaRepository categoriaRepository;

    private final CategoriaMapper categoriaMapper;

    public CategoriaService(CategoriaRepository categoriaRepository, CategoriaMapper categoriaMapper) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
    }

    /**
     * Save a categoria.
     *
     * @param categoriaDTO the entity to save.
     * @return the persisted entity.
     */
    public CategoriaDTO save(CategoriaDTO categoriaDTO) {
        log.debug("Request to save Categoria : {}", categoriaDTO);
        Categoria categoria = categoriaMapper.toEntity(categoriaDTO);
        categoria = categoriaRepository.save(categoria);
        return categoriaMapper.toDto(categoria);
    }

    /**
     * Update a categoria.
     *
     * @param categoriaDTO the entity to save.
     * @return the persisted entity.
     */
    public CategoriaDTO update(CategoriaDTO categoriaDTO) {
        log.debug("Request to update Categoria : {}", categoriaDTO);
        Categoria categoria = categoriaMapper.toEntity(categoriaDTO);
        categoria = categoriaRepository.save(categoria);
        return categoriaMapper.toDto(categoria);
    }

    /**
     * Partially update a categoria.
     *
     * @param categoriaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CategoriaDTO> partialUpdate(CategoriaDTO categoriaDTO) {
        log.debug("Request to partially update Categoria : {}", categoriaDTO);

        return categoriaRepository
            .findById(categoriaDTO.getId())
            .map(existingCategoria -> {
                categoriaMapper.partialUpdate(existingCategoria, categoriaDTO);

                return existingCategoria;
            })
            .map(categoriaRepository::save)
            .map(categoriaMapper::toDto);
    }

    /**
     * Get all the categorias.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CategoriaDTO> findAll() {
        log.debug("Request to get all Categorias");
        return categoriaRepository.findAll().stream().map(categoriaMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one categoria by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CategoriaDTO> findOne(Long id) {
        log.debug("Request to get Categoria : {}", id);
        return categoriaRepository.findById(id).map(categoriaMapper::toDto);
    }

    /**
     * Delete the categoria by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Categoria : {}", id);
        categoriaRepository.deleteById(id);
    }
}
