package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProjectSupportedMapperTest {

    private ProjectSupportedMapper projectSupportedMapper;

    @BeforeEach
    public void setUp() {
        projectSupportedMapper = new ProjectSupportedMapperImpl();
    }
}
