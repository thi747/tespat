package com.github.thi747.tespat.service.mapper;

import com.github.thi747.tespat.domain.Local;
import com.github.thi747.tespat.service.dto.LocalDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Local} and its DTO {@link LocalDTO}.
 */
@Mapper(componentModel = "spring")
public interface LocalMapper extends EntityMapper<LocalDTO, Local> {}
