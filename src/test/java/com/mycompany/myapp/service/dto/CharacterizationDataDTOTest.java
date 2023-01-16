package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CharacterizationDataDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CharacterizationDataDTO.class);
        CharacterizationDataDTO characterizationDataDTO1 = new CharacterizationDataDTO();
        characterizationDataDTO1.setId(1L);
        CharacterizationDataDTO characterizationDataDTO2 = new CharacterizationDataDTO();
        assertThat(characterizationDataDTO1).isNotEqualTo(characterizationDataDTO2);
        characterizationDataDTO2.setId(characterizationDataDTO1.getId());
        assertThat(characterizationDataDTO1).isEqualTo(characterizationDataDTO2);
        characterizationDataDTO2.setId(2L);
        assertThat(characterizationDataDTO1).isNotEqualTo(characterizationDataDTO2);
        characterizationDataDTO1.setId(null);
        assertThat(characterizationDataDTO1).isNotEqualTo(characterizationDataDTO2);
    }
}
