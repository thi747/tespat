package com.github.thi747.tespat.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.thi747.tespat.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MovimentacaoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovimentacaoDTO.class);
        MovimentacaoDTO movimentacaoDTO1 = new MovimentacaoDTO();
        movimentacaoDTO1.setId(1L);
        MovimentacaoDTO movimentacaoDTO2 = new MovimentacaoDTO();
        assertThat(movimentacaoDTO1).isNotEqualTo(movimentacaoDTO2);
        movimentacaoDTO2.setId(movimentacaoDTO1.getId());
        assertThat(movimentacaoDTO1).isEqualTo(movimentacaoDTO2);
        movimentacaoDTO2.setId(2L);
        assertThat(movimentacaoDTO1).isNotEqualTo(movimentacaoDTO2);
        movimentacaoDTO1.setId(null);
        assertThat(movimentacaoDTO1).isNotEqualTo(movimentacaoDTO2);
    }
}
