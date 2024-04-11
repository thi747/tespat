package com.github.thi747.tespat.service;

import com.github.thi747.tespat.domain.Movimentacao;
import com.github.thi747.tespat.repository.MovimentacaoRepository;
import com.github.thi747.tespat.service.dto.MovimentacaoDTO;
import com.github.thi747.tespat.service.mapper.MovimentacaoMapper;
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
 * Service Implementation for managing {@link com.github.thi747.tespat.domain.Movimentacao}.
 */
@Service
@Transactional
public class MovimentacaoService {

    private final Logger log = LoggerFactory.getLogger(MovimentacaoService.class);

    private final MovimentacaoRepository movimentacaoRepository;

    private final MovimentacaoMapper movimentacaoMapper;

    public MovimentacaoService(MovimentacaoRepository movimentacaoRepository, MovimentacaoMapper movimentacaoMapper) {
        this.movimentacaoRepository = movimentacaoRepository;
        this.movimentacaoMapper = movimentacaoMapper;
    }

    /**
     * Save a movimentacao.
     *
     * @param movimentacaoDTO the entity to save.
     * @return the persisted entity.
     */
    public MovimentacaoDTO save(MovimentacaoDTO movimentacaoDTO) {
        log.debug("Request to save Movimentacao : {}", movimentacaoDTO);
        Movimentacao movimentacao = movimentacaoMapper.toEntity(movimentacaoDTO);
        movimentacao = movimentacaoRepository.save(movimentacao);
        return movimentacaoMapper.toDto(movimentacao);
    }

    /**
     * Update a movimentacao.
     *
     * @param movimentacaoDTO the entity to save.
     * @return the persisted entity.
     */
    public MovimentacaoDTO update(MovimentacaoDTO movimentacaoDTO) {
        log.debug("Request to update Movimentacao : {}", movimentacaoDTO);
        Movimentacao movimentacao = movimentacaoMapper.toEntity(movimentacaoDTO);
        movimentacao = movimentacaoRepository.save(movimentacao);
        return movimentacaoMapper.toDto(movimentacao);
    }

    /**
     * Partially update a movimentacao.
     *
     * @param movimentacaoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MovimentacaoDTO> partialUpdate(MovimentacaoDTO movimentacaoDTO) {
        log.debug("Request to partially update Movimentacao : {}", movimentacaoDTO);

        return movimentacaoRepository
            .findById(movimentacaoDTO.getId())
            .map(existingMovimentacao -> {
                movimentacaoMapper.partialUpdate(existingMovimentacao, movimentacaoDTO);

                return existingMovimentacao;
            })
            .map(movimentacaoRepository::save)
            .map(movimentacaoMapper::toDto);
    }

    /**
     * Get all the movimentacoes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MovimentacaoDTO> findAll() {
        log.debug("Request to get all Movimentacoes");
        return movimentacaoRepository.findAll().stream().map(movimentacaoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the movimentacoes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<MovimentacaoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return movimentacaoRepository.findAllWithEagerRelationships(pageable).map(movimentacaoMapper::toDto);
    }

    /**
     * Get one movimentacao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MovimentacaoDTO> findOne(Long id) {
        log.debug("Request to get Movimentacao : {}", id);
        return movimentacaoRepository.findOneWithEagerRelationships(id).map(movimentacaoMapper::toDto);
    }

    /**
     * Delete the movimentacao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Movimentacao : {}", id);
        movimentacaoRepository.deleteById(id);
    }
}
