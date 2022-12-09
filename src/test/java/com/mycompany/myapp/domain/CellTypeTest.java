package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CellTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CellType.class);
        CellType cellType1 = new CellType();
        cellType1.setId(1L);
        CellType cellType2 = new CellType();
        cellType2.setId(cellType1.getId());
        assertThat(cellType1).isEqualTo(cellType2);
        cellType2.setId(2L);
        assertThat(cellType1).isNotEqualTo(cellType2);
        cellType1.setId(null);
        assertThat(cellType1).isNotEqualTo(cellType2);
    }
}
