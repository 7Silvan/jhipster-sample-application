package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CellType;
import com.mycompany.myapp.repository.CellTypeRepository;
import com.mycompany.myapp.service.dto.CellTypeDTO;
import com.mycompany.myapp.service.mapper.CellTypeMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CellTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CellTypeResourceIT {

    private static final String DEFAULT_CELL_SOURCE_INFO = "AAAAAAAAAA";
    private static final String UPDATED_CELL_SOURCE_INFO = "BBBBBBBBBB";

    private static final String DEFAULT_CELL_ARCHITECTURE = "AAAAAAAAAA";
    private static final String UPDATED_CELL_ARCHITECTURE = "BBBBBBBBBB";

    private static final String DEFAULT_VENDOR = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cell-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CellTypeRepository cellTypeRepository;

    @Autowired
    private CellTypeMapper cellTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCellTypeMockMvc;

    private CellType cellType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CellType createEntity(EntityManager em) {
        CellType cellType = new CellType()
            .cellSourceInfo(DEFAULT_CELL_SOURCE_INFO)
            .cellArchitecture(DEFAULT_CELL_ARCHITECTURE)
            .vendor(DEFAULT_VENDOR);
        return cellType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CellType createUpdatedEntity(EntityManager em) {
        CellType cellType = new CellType()
            .cellSourceInfo(UPDATED_CELL_SOURCE_INFO)
            .cellArchitecture(UPDATED_CELL_ARCHITECTURE)
            .vendor(UPDATED_VENDOR);
        return cellType;
    }

    @BeforeEach
    public void initTest() {
        cellType = createEntity(em);
    }

    @Test
    @Transactional
    void createCellType() throws Exception {
        int databaseSizeBeforeCreate = cellTypeRepository.findAll().size();
        // Create the CellType
        CellTypeDTO cellTypeDTO = cellTypeMapper.toDto(cellType);
        restCellTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cellTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the CellType in the database
        List<CellType> cellTypeList = cellTypeRepository.findAll();
        assertThat(cellTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CellType testCellType = cellTypeList.get(cellTypeList.size() - 1);
        assertThat(testCellType.getCellSourceInfo()).isEqualTo(DEFAULT_CELL_SOURCE_INFO);
        assertThat(testCellType.getCellArchitecture()).isEqualTo(DEFAULT_CELL_ARCHITECTURE);
        assertThat(testCellType.getVendor()).isEqualTo(DEFAULT_VENDOR);
    }

    @Test
    @Transactional
    void createCellTypeWithExistingId() throws Exception {
        // Create the CellType with an existing ID
        cellType.setId(1L);
        CellTypeDTO cellTypeDTO = cellTypeMapper.toDto(cellType);

        int databaseSizeBeforeCreate = cellTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCellTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cellTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CellType in the database
        List<CellType> cellTypeList = cellTypeRepository.findAll();
        assertThat(cellTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCellSourceInfoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cellTypeRepository.findAll().size();
        // set the field null
        cellType.setCellSourceInfo(null);

        // Create the CellType, which fails.
        CellTypeDTO cellTypeDTO = cellTypeMapper.toDto(cellType);

        restCellTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cellTypeDTO)))
            .andExpect(status().isBadRequest());

        List<CellType> cellTypeList = cellTypeRepository.findAll();
        assertThat(cellTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCellArchitectureIsRequired() throws Exception {
        int databaseSizeBeforeTest = cellTypeRepository.findAll().size();
        // set the field null
        cellType.setCellArchitecture(null);

        // Create the CellType, which fails.
        CellTypeDTO cellTypeDTO = cellTypeMapper.toDto(cellType);

        restCellTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cellTypeDTO)))
            .andExpect(status().isBadRequest());

        List<CellType> cellTypeList = cellTypeRepository.findAll();
        assertThat(cellTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVendorIsRequired() throws Exception {
        int databaseSizeBeforeTest = cellTypeRepository.findAll().size();
        // set the field null
        cellType.setVendor(null);

        // Create the CellType, which fails.
        CellTypeDTO cellTypeDTO = cellTypeMapper.toDto(cellType);

        restCellTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cellTypeDTO)))
            .andExpect(status().isBadRequest());

        List<CellType> cellTypeList = cellTypeRepository.findAll();
        assertThat(cellTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCellTypes() throws Exception {
        // Initialize the database
        cellTypeRepository.saveAndFlush(cellType);

        // Get all the cellTypeList
        restCellTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cellType.getId().intValue())))
            .andExpect(jsonPath("$.[*].cellSourceInfo").value(hasItem(DEFAULT_CELL_SOURCE_INFO)))
            .andExpect(jsonPath("$.[*].cellArchitecture").value(hasItem(DEFAULT_CELL_ARCHITECTURE)))
            .andExpect(jsonPath("$.[*].vendor").value(hasItem(DEFAULT_VENDOR)));
    }

    @Test
    @Transactional
    void getCellType() throws Exception {
        // Initialize the database
        cellTypeRepository.saveAndFlush(cellType);

        // Get the cellType
        restCellTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, cellType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cellType.getId().intValue()))
            .andExpect(jsonPath("$.cellSourceInfo").value(DEFAULT_CELL_SOURCE_INFO))
            .andExpect(jsonPath("$.cellArchitecture").value(DEFAULT_CELL_ARCHITECTURE))
            .andExpect(jsonPath("$.vendor").value(DEFAULT_VENDOR));
    }

    @Test
    @Transactional
    void getNonExistingCellType() throws Exception {
        // Get the cellType
        restCellTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCellType() throws Exception {
        // Initialize the database
        cellTypeRepository.saveAndFlush(cellType);

        int databaseSizeBeforeUpdate = cellTypeRepository.findAll().size();

        // Update the cellType
        CellType updatedCellType = cellTypeRepository.findById(cellType.getId()).get();
        // Disconnect from session so that the updates on updatedCellType are not directly saved in db
        em.detach(updatedCellType);
        updatedCellType.cellSourceInfo(UPDATED_CELL_SOURCE_INFO).cellArchitecture(UPDATED_CELL_ARCHITECTURE).vendor(UPDATED_VENDOR);
        CellTypeDTO cellTypeDTO = cellTypeMapper.toDto(updatedCellType);

        restCellTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cellTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cellTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the CellType in the database
        List<CellType> cellTypeList = cellTypeRepository.findAll();
        assertThat(cellTypeList).hasSize(databaseSizeBeforeUpdate);
        CellType testCellType = cellTypeList.get(cellTypeList.size() - 1);
        assertThat(testCellType.getCellSourceInfo()).isEqualTo(UPDATED_CELL_SOURCE_INFO);
        assertThat(testCellType.getCellArchitecture()).isEqualTo(UPDATED_CELL_ARCHITECTURE);
        assertThat(testCellType.getVendor()).isEqualTo(UPDATED_VENDOR);
    }

    @Test
    @Transactional
    void putNonExistingCellType() throws Exception {
        int databaseSizeBeforeUpdate = cellTypeRepository.findAll().size();
        cellType.setId(count.incrementAndGet());

        // Create the CellType
        CellTypeDTO cellTypeDTO = cellTypeMapper.toDto(cellType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCellTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cellTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cellTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CellType in the database
        List<CellType> cellTypeList = cellTypeRepository.findAll();
        assertThat(cellTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCellType() throws Exception {
        int databaseSizeBeforeUpdate = cellTypeRepository.findAll().size();
        cellType.setId(count.incrementAndGet());

        // Create the CellType
        CellTypeDTO cellTypeDTO = cellTypeMapper.toDto(cellType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCellTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cellTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CellType in the database
        List<CellType> cellTypeList = cellTypeRepository.findAll();
        assertThat(cellTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCellType() throws Exception {
        int databaseSizeBeforeUpdate = cellTypeRepository.findAll().size();
        cellType.setId(count.incrementAndGet());

        // Create the CellType
        CellTypeDTO cellTypeDTO = cellTypeMapper.toDto(cellType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCellTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cellTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CellType in the database
        List<CellType> cellTypeList = cellTypeRepository.findAll();
        assertThat(cellTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCellTypeWithPatch() throws Exception {
        // Initialize the database
        cellTypeRepository.saveAndFlush(cellType);

        int databaseSizeBeforeUpdate = cellTypeRepository.findAll().size();

        // Update the cellType using partial update
        CellType partialUpdatedCellType = new CellType();
        partialUpdatedCellType.setId(cellType.getId());

        partialUpdatedCellType.cellArchitecture(UPDATED_CELL_ARCHITECTURE).vendor(UPDATED_VENDOR);

        restCellTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCellType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCellType))
            )
            .andExpect(status().isOk());

        // Validate the CellType in the database
        List<CellType> cellTypeList = cellTypeRepository.findAll();
        assertThat(cellTypeList).hasSize(databaseSizeBeforeUpdate);
        CellType testCellType = cellTypeList.get(cellTypeList.size() - 1);
        assertThat(testCellType.getCellSourceInfo()).isEqualTo(DEFAULT_CELL_SOURCE_INFO);
        assertThat(testCellType.getCellArchitecture()).isEqualTo(UPDATED_CELL_ARCHITECTURE);
        assertThat(testCellType.getVendor()).isEqualTo(UPDATED_VENDOR);
    }

    @Test
    @Transactional
    void fullUpdateCellTypeWithPatch() throws Exception {
        // Initialize the database
        cellTypeRepository.saveAndFlush(cellType);

        int databaseSizeBeforeUpdate = cellTypeRepository.findAll().size();

        // Update the cellType using partial update
        CellType partialUpdatedCellType = new CellType();
        partialUpdatedCellType.setId(cellType.getId());

        partialUpdatedCellType.cellSourceInfo(UPDATED_CELL_SOURCE_INFO).cellArchitecture(UPDATED_CELL_ARCHITECTURE).vendor(UPDATED_VENDOR);

        restCellTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCellType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCellType))
            )
            .andExpect(status().isOk());

        // Validate the CellType in the database
        List<CellType> cellTypeList = cellTypeRepository.findAll();
        assertThat(cellTypeList).hasSize(databaseSizeBeforeUpdate);
        CellType testCellType = cellTypeList.get(cellTypeList.size() - 1);
        assertThat(testCellType.getCellSourceInfo()).isEqualTo(UPDATED_CELL_SOURCE_INFO);
        assertThat(testCellType.getCellArchitecture()).isEqualTo(UPDATED_CELL_ARCHITECTURE);
        assertThat(testCellType.getVendor()).isEqualTo(UPDATED_VENDOR);
    }

    @Test
    @Transactional
    void patchNonExistingCellType() throws Exception {
        int databaseSizeBeforeUpdate = cellTypeRepository.findAll().size();
        cellType.setId(count.incrementAndGet());

        // Create the CellType
        CellTypeDTO cellTypeDTO = cellTypeMapper.toDto(cellType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCellTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cellTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cellTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CellType in the database
        List<CellType> cellTypeList = cellTypeRepository.findAll();
        assertThat(cellTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCellType() throws Exception {
        int databaseSizeBeforeUpdate = cellTypeRepository.findAll().size();
        cellType.setId(count.incrementAndGet());

        // Create the CellType
        CellTypeDTO cellTypeDTO = cellTypeMapper.toDto(cellType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCellTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cellTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CellType in the database
        List<CellType> cellTypeList = cellTypeRepository.findAll();
        assertThat(cellTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCellType() throws Exception {
        int databaseSizeBeforeUpdate = cellTypeRepository.findAll().size();
        cellType.setId(count.incrementAndGet());

        // Create the CellType
        CellTypeDTO cellTypeDTO = cellTypeMapper.toDto(cellType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCellTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cellTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CellType in the database
        List<CellType> cellTypeList = cellTypeRepository.findAll();
        assertThat(cellTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCellType() throws Exception {
        // Initialize the database
        cellTypeRepository.saveAndFlush(cellType);

        int databaseSizeBeforeDelete = cellTypeRepository.findAll().size();

        // Delete the cellType
        restCellTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, cellType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CellType> cellTypeList = cellTypeRepository.findAll();
        assertThat(cellTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
