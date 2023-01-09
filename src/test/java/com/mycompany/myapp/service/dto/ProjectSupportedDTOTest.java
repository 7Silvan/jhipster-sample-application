package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectSupportedDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectSupportedDTO.class);
        ProjectSupportedDTO projectSupportedDTO1 = new ProjectSupportedDTO();
        projectSupportedDTO1.setId(1L);
        ProjectSupportedDTO projectSupportedDTO2 = new ProjectSupportedDTO();
        assertThat(projectSupportedDTO1).isNotEqualTo(projectSupportedDTO2);
        projectSupportedDTO2.setId(projectSupportedDTO1.getId());
        assertThat(projectSupportedDTO1).isEqualTo(projectSupportedDTO2);
        projectSupportedDTO2.setId(2L);
        assertThat(projectSupportedDTO1).isNotEqualTo(projectSupportedDTO2);
        projectSupportedDTO1.setId(null);
        assertThat(projectSupportedDTO1).isNotEqualTo(projectSupportedDTO2);
    }
}
