package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CellTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CellTypeDTO.class);
        CellTypeDTO cellTypeDTO1 = new CellTypeDTO();
        cellTypeDTO1.setId(1L);
        CellTypeDTO cellTypeDTO2 = new CellTypeDTO();
        assertThat(cellTypeDTO1).isNotEqualTo(cellTypeDTO2);
        cellTypeDTO2.setId(cellTypeDTO1.getId());
        assertThat(cellTypeDTO1).isEqualTo(cellTypeDTO2);
        cellTypeDTO2.setId(2L);
        assertThat(cellTypeDTO1).isNotEqualTo(cellTypeDTO2);
        cellTypeDTO1.setId(null);
        assertThat(cellTypeDTO1).isNotEqualTo(cellTypeDTO2);
    }
}
