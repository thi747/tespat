package com.github.thi747.tespat.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.thi747.tespat.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BemDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BemDTO.class);
        BemDTO bemDTO1 = new BemDTO();
        bemDTO1.setId(1L);
        BemDTO bemDTO2 = new BemDTO();
        assertThat(bemDTO1).isNotEqualTo(bemDTO2);
        bemDTO2.setId(bemDTO1.getId());
        assertThat(bemDTO1).isEqualTo(bemDTO2);
        bemDTO2.setId(2L);
        assertThat(bemDTO1).isNotEqualTo(bemDTO2);
        bemDTO1.setId(null);
        assertThat(bemDTO1).isNotEqualTo(bemDTO2);
    }
}
