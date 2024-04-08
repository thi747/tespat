package com.github.thi747.tespat.service.mapper;

import com.github.thi747.tespat.domain.Categoria;
import com.github.thi747.tespat.service.dto.CategoriaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Categoria} and its DTO {@link CategoriaDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategoriaMapper extends EntityMapper<CategoriaDTO, Categoria> {}
