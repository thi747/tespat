package com.github.thi747.tespat.service.mapper;

import com.github.thi747.tespat.domain.Pessoa;
import com.github.thi747.tespat.service.dto.PessoaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pessoa} and its DTO {@link PessoaDTO}.
 */
@Mapper(componentModel = "spring")
public interface PessoaMapper extends EntityMapper<PessoaDTO, Pessoa> {}
