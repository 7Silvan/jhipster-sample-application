package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CharacterizationDataMapperTest {

    private CharacterizationDataMapper characterizationDataMapper;

    @BeforeEach
    public void setUp() {
        characterizationDataMapper = new CharacterizationDataMapperImpl();
    }
}
