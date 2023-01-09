package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CellModelMapperTest {

    private CellModelMapper cellModelMapper;

    @BeforeEach
    public void setUp() {
        cellModelMapper = new CellModelMapperImpl();
    }
}
