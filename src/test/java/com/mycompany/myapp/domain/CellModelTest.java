package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CellModelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CellModel.class);
        CellModel cellModel1 = new CellModel();
        cellModel1.setId("id1");
        CellModel cellModel2 = new CellModel();
        cellModel2.setId(cellModel1.getId());
        assertThat(cellModel1).isEqualTo(cellModel2);
        cellModel2.setId("id2");
        assertThat(cellModel1).isNotEqualTo(cellModel2);
        cellModel1.setId(null);
        assertThat(cellModel1).isNotEqualTo(cellModel2);
    }
}
