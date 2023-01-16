package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CellModelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CellModelDTO.class);
        CellModelDTO cellModelDTO1 = new CellModelDTO();
        cellModelDTO1.setId("id1");
        CellModelDTO cellModelDTO2 = new CellModelDTO();
        assertThat(cellModelDTO1).isNotEqualTo(cellModelDTO2);
        cellModelDTO2.setId(cellModelDTO1.getId());
        assertThat(cellModelDTO1).isEqualTo(cellModelDTO2);
        cellModelDTO2.setId("id2");
        assertThat(cellModelDTO1).isNotEqualTo(cellModelDTO2);
        cellModelDTO1.setId(null);
        assertThat(cellModelDTO1).isNotEqualTo(cellModelDTO2);
    }
}
