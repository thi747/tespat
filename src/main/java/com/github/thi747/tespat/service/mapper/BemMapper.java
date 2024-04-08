package com.github.thi747.tespat.service.mapper;

import com.github.thi747.tespat.domain.Bem;
import com.github.thi747.tespat.domain.Categoria;
import com.github.thi747.tespat.domain.Fornecedor;
import com.github.thi747.tespat.domain.Local;
import com.github.thi747.tespat.service.dto.BemDTO;
import com.github.thi747.tespat.service.dto.CategoriaDTO;
import com.github.thi747.tespat.service.dto.FornecedorDTO;
import com.github.thi747.tespat.service.dto.LocalDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Bem} and its DTO {@link BemDTO}.
 */
@Mapper(componentModel = "spring")
public interface BemMapper extends EntityMapper<BemDTO, Bem> {
    @Mapping(target = "categoria", source = "categoria", qualifiedByName = "categoriaNome")
    @Mapping(target = "fornecedor", source = "fornecedor", qualifiedByName = "fornecedorNome")
    @Mapping(target = "local", source = "local", qualifiedByName = "localNome")
    BemDTO toDto(Bem s);

    @Named("categoriaNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    CategoriaDTO toDtoCategoriaNome(Categoria categoria);

    @Named("fornecedorNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    FornecedorDTO toDtoFornecedorNome(Fornecedor fornecedor);

    @Named("localNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    LocalDTO toDtoLocalNome(Local local);
}
