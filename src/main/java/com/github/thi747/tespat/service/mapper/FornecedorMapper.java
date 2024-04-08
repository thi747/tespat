package com.github.thi747.tespat.service.mapper;

import com.github.thi747.tespat.domain.Fornecedor;
import com.github.thi747.tespat.service.dto.FornecedorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Fornecedor} and its DTO {@link FornecedorDTO}.
 */
@Mapper(componentModel = "spring")
public interface FornecedorMapper extends EntityMapper<FornecedorDTO, Fornecedor> {}
