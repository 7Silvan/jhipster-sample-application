package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectSupportedTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectSupported.class);
        ProjectSupported projectSupported1 = new ProjectSupported();
        projectSupported1.setId(1L);
        ProjectSupported projectSupported2 = new ProjectSupported();
        projectSupported2.setId(projectSupported1.getId());
        assertThat(projectSupported1).isEqualTo(projectSupported2);
        projectSupported2.setId(2L);
        assertThat(projectSupported1).isNotEqualTo(projectSupported2);
        projectSupported1.setId(null);
        assertThat(projectSupported1).isNotEqualTo(projectSupported2);
    }
}
