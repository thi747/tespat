package com.github.thi747.tespat.service.mapper;

import com.github.thi747.tespat.domain.Bem;
import com.github.thi747.tespat.domain.Movimentacao;
import com.github.thi747.tespat.domain.Pessoa;
import com.github.thi747.tespat.service.dto.BemDTO;
import com.github.thi747.tespat.service.dto.MovimentacaoDTO;
import com.github.thi747.tespat.service.dto.PessoaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Movimentacao} and its DTO {@link MovimentacaoDTO}.
 */
@Mapper(componentModel = "spring")
public interface MovimentacaoMapper extends EntityMapper<MovimentacaoDTO, Movimentacao> {
    @Mapping(target = "bem", source = "bem", qualifiedByName = "bemPatrimonio")
    @Mapping(target = "pessoa", source = "pessoa", qualifiedByName = "pessoaUsuario")
    MovimentacaoDTO toDto(Movimentacao s);

    @Named("bemPatrimonio")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "patrimonio", source = "patrimonio")
    BemDTO toDtoBemPatrimonio(Bem bem);

    @Named("pessoaUsuario")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "usuario", source = "usuario")
    PessoaDTO toDtoPessoaUsuario(Pessoa pessoa);
}
