package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CharacterizationDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CharacterizationData.class);
        CharacterizationData characterizationData1 = new CharacterizationData();
        characterizationData1.setId(1L);
        CharacterizationData characterizationData2 = new CharacterizationData();
        characterizationData2.setId(characterizationData1.getId());
        assertThat(characterizationData1).isEqualTo(characterizationData2);
        characterizationData2.setId(2L);
        assertThat(characterizationData1).isNotEqualTo(characterizationData2);
        characterizationData1.setId(null);
        assertThat(characterizationData1).isNotEqualTo(characterizationData2);
    }
}
