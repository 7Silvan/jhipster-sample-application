package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CellModel;
import com.mycompany.myapp.domain.CellType;
import com.mycompany.myapp.domain.CharacterizationData;
import com.mycompany.myapp.domain.Comment;
import com.mycompany.myapp.domain.ProjectSupported;
import com.mycompany.myapp.domain.Publication;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.CellModelRepository;
import com.mycompany.myapp.service.criteria.CellModelCriteria;
import com.mycompany.myapp.service.dto.CellModelDTO;
import com.mycompany.myapp.service.mapper.CellModelMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
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
 * Integration tests for the {@link CellModelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CellModelResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_ADDED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_ADDED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATE_ADDED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_DATE_UPDATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_UPDATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATE_UPDATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_SPECIES = "AAAAAAAAAA";
    private static final String UPDATED_SPECIES = "BBBBBBBBBB";

    private static final String DEFAULT_TARGET_ORGAN = "AAAAAAAAAA";
    private static final String UPDATED_TARGET_ORGAN = "BBBBBBBBBB";

    private static final String DEFAULT_TARGET_TISSUE = "AAAAAAAAAA";
    private static final String UPDATED_TARGET_TISSUE = "BBBBBBBBBB";

    private static final String DEFAULT_VESSEL_INFO = "AAAAAAAAAA";
    private static final String UPDATED_VESSEL_INFO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MATRIX = false;
    private static final Boolean UPDATED_MATRIX = true;

    private static final String DEFAULT_PROTOCOL = "AAAAAAAAAA";
    private static final String UPDATED_PROTOCOL = "BBBBBBBBBB";

    private static final String DEFAULT_MANUFACTURER = "AAAAAAAAAA";
    private static final String UPDATED_MANUFACTURER = "BBBBBBBBBB";

    private static final String DEFAULT_MATRIX_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_MATRIX_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_VESSEL_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_VESSEL_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INHOUSE = false;
    private static final Boolean UPDATED_INHOUSE = true;

    private static final Integer DEFAULT_RATING_CHARACTERIZATION = 1;
    private static final Integer UPDATED_RATING_CHARACTERIZATION = 2;
    private static final Integer SMALLER_RATING_CHARACTERIZATION = 1 - 1;

    private static final Integer DEFAULT_RATING_COMPLEXITY = 1;
    private static final Integer UPDATED_RATING_COMPLEXITY = 2;
    private static final Integer SMALLER_RATING_COMPLEXITY = 1 - 1;

    private static final Integer DEFAULT_RATING_MODEL_COST = 1;
    private static final Integer UPDATED_RATING_MODEL_COST = 2;
    private static final Integer SMALLER_RATING_MODEL_COST = 1 - 1;

    private static final Integer DEFAULT_RATING_THROUGH_PUT_CAPACITY = 1;
    private static final Integer UPDATED_RATING_THROUGH_PUT_CAPACITY = 2;
    private static final Integer SMALLER_RATING_THROUGH_PUT_CAPACITY = 1 - 1;

    private static final Integer DEFAULT_RATING_TURN_AROUND_TIME = 1;
    private static final Integer UPDATED_RATING_TURN_AROUND_TIME = 2;
    private static final Integer SMALLER_RATING_TURN_AROUND_TIME = 1 - 1;

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTMENT = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTMENT = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL_STATE = "AAAAAAAAAA";
    private static final String UPDATED_MODEL_STATE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_APPLICATION_ADME = false;
    private static final Boolean UPDATED_APPLICATION_ADME = true;

    private static final Boolean DEFAULT_APPLICATION_EFFICACY = false;
    private static final Boolean UPDATED_APPLICATION_EFFICACY = true;

    private static final Boolean DEFAULT_APPLICATION_NONE = false;
    private static final Boolean UPDATED_APPLICATION_NONE = true;

    private static final Boolean DEFAULT_APPLICATION_SAFETY = false;
    private static final Boolean UPDATED_APPLICATION_SAFETY = true;

    private static final String DEFAULT_ANIMAL_ASSAY_REPLACED = "AAAAAAAAAA";
    private static final String UPDATED_ANIMAL_ASSAY_REPLACED = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_EXPERTS = "AAAAAAAAAA";
    private static final String UPDATED_EXPERTS = "BBBBBBBBBB";

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_LIMITATION_PERCIEVED = "AAAAAAAAAA";
    private static final String UPDATED_LIMITATION_PERCIEVED = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cell-models";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private CellModelRepository cellModelRepository;

    @Autowired
    private CellModelMapper cellModelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCellModelMockMvc;

    private CellModel cellModel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CellModel createEntity(EntityManager em) {
        CellModel cellModel = new CellModel()
            .name(DEFAULT_NAME)
            .dateAdded(DEFAULT_DATE_ADDED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .species(DEFAULT_SPECIES)
            .targetOrgan(DEFAULT_TARGET_ORGAN)
            .targetTissue(DEFAULT_TARGET_TISSUE)
            .vesselInfo(DEFAULT_VESSEL_INFO)
            .matrix(DEFAULT_MATRIX)
            .protocol(DEFAULT_PROTOCOL)
            .manufacturer(DEFAULT_MANUFACTURER)
            .matrixType(DEFAULT_MATRIX_TYPE)
            .vesselType(DEFAULT_VESSEL_TYPE)
            .inhouse(DEFAULT_INHOUSE)
            .ratingCharacterization(DEFAULT_RATING_CHARACTERIZATION)
            .ratingComplexity(DEFAULT_RATING_COMPLEXITY)
            .ratingModelCost(DEFAULT_RATING_MODEL_COST)
            .ratingThroughPutCapacity(DEFAULT_RATING_THROUGH_PUT_CAPACITY)
            .ratingTurnAroundTime(DEFAULT_RATING_TURN_AROUND_TIME)
            .imageUrl(DEFAULT_IMAGE_URL)
            .department(DEFAULT_DEPARTMENT)
            .modelState(DEFAULT_MODEL_STATE)
            .applicationADME(DEFAULT_APPLICATION_ADME)
            .applicationEfficacy(DEFAULT_APPLICATION_EFFICACY)
            .applicationNone(DEFAULT_APPLICATION_NONE)
            .applicationSafety(DEFAULT_APPLICATION_SAFETY)
            .animalAssayReplaced(DEFAULT_ANIMAL_ASSAY_REPLACED)
            .comment(DEFAULT_COMMENT)
            .experts(DEFAULT_EXPERTS)
            .link(DEFAULT_LINK)
            .limitationPercieved(DEFAULT_LIMITATION_PERCIEVED);
        return cellModel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CellModel createUpdatedEntity(EntityManager em) {
        CellModel cellModel = new CellModel()
            .name(UPDATED_NAME)
            .dateAdded(UPDATED_DATE_ADDED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .species(UPDATED_SPECIES)
            .targetOrgan(UPDATED_TARGET_ORGAN)
            .targetTissue(UPDATED_TARGET_TISSUE)
            .vesselInfo(UPDATED_VESSEL_INFO)
            .matrix(UPDATED_MATRIX)
            .protocol(UPDATED_PROTOCOL)
            .manufacturer(UPDATED_MANUFACTURER)
            .matrixType(UPDATED_MATRIX_TYPE)
            .vesselType(UPDATED_VESSEL_TYPE)
            .inhouse(UPDATED_INHOUSE)
            .ratingCharacterization(UPDATED_RATING_CHARACTERIZATION)
            .ratingComplexity(UPDATED_RATING_COMPLEXITY)
            .ratingModelCost(UPDATED_RATING_MODEL_COST)
            .ratingThroughPutCapacity(UPDATED_RATING_THROUGH_PUT_CAPACITY)
            .ratingTurnAroundTime(UPDATED_RATING_TURN_AROUND_TIME)
            .imageUrl(UPDATED_IMAGE_URL)
            .department(UPDATED_DEPARTMENT)
            .modelState(UPDATED_MODEL_STATE)
            .applicationADME(UPDATED_APPLICATION_ADME)
            .applicationEfficacy(UPDATED_APPLICATION_EFFICACY)
            .applicationNone(UPDATED_APPLICATION_NONE)
            .applicationSafety(UPDATED_APPLICATION_SAFETY)
            .animalAssayReplaced(UPDATED_ANIMAL_ASSAY_REPLACED)
            .comment(UPDATED_COMMENT)
            .experts(UPDATED_EXPERTS)
            .link(UPDATED_LINK)
            .limitationPercieved(UPDATED_LIMITATION_PERCIEVED);
        return cellModel;
    }

    @BeforeEach
    public void initTest() {
        cellModel = createEntity(em);
    }

    @Test
    @Transactional
    void createCellModel() throws Exception {
        int databaseSizeBeforeCreate = cellModelRepository.findAll().size();
        // Create the CellModel
        CellModelDTO cellModelDTO = cellModelMapper.toDto(cellModel);
        restCellModelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cellModelDTO)))
            .andExpect(status().isCreated());

        // Validate the CellModel in the database
        List<CellModel> cellModelList = cellModelRepository.findAll();
        assertThat(cellModelList).hasSize(databaseSizeBeforeCreate + 1);
        CellModel testCellModel = cellModelList.get(cellModelList.size() - 1);
        assertThat(testCellModel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCellModel.getDateAdded()).isEqualTo(DEFAULT_DATE_ADDED);
        assertThat(testCellModel.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testCellModel.getSpecies()).isEqualTo(DEFAULT_SPECIES);
        assertThat(testCellModel.getTargetOrgan()).isEqualTo(DEFAULT_TARGET_ORGAN);
        assertThat(testCellModel.getTargetTissue()).isEqualTo(DEFAULT_TARGET_TISSUE);
        assertThat(testCellModel.getVesselInfo()).isEqualTo(DEFAULT_VESSEL_INFO);
        assertThat(testCellModel.getMatrix()).isEqualTo(DEFAULT_MATRIX);
        assertThat(testCellModel.getProtocol()).isEqualTo(DEFAULT_PROTOCOL);
        assertThat(testCellModel.getManufacturer()).isEqualTo(DEFAULT_MANUFACTURER);
        assertThat(testCellModel.getMatrixType()).isEqualTo(DEFAULT_MATRIX_TYPE);
        assertThat(testCellModel.getVesselType()).isEqualTo(DEFAULT_VESSEL_TYPE);
        assertThat(testCellModel.getInhouse()).isEqualTo(DEFAULT_INHOUSE);
        assertThat(testCellModel.getRatingCharacterization()).isEqualTo(DEFAULT_RATING_CHARACTERIZATION);
        assertThat(testCellModel.getRatingComplexity()).isEqualTo(DEFAULT_RATING_COMPLEXITY);
        assertThat(testCellModel.getRatingModelCost()).isEqualTo(DEFAULT_RATING_MODEL_COST);
        assertThat(testCellModel.getRatingThroughPutCapacity()).isEqualTo(DEFAULT_RATING_THROUGH_PUT_CAPACITY);
        assertThat(testCellModel.getRatingTurnAroundTime()).isEqualTo(DEFAULT_RATING_TURN_AROUND_TIME);
        assertThat(testCellModel.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testCellModel.getDepartment()).isEqualTo(DEFAULT_DEPARTMENT);
        assertThat(testCellModel.getModelState()).isEqualTo(DEFAULT_MODEL_STATE);
        assertThat(testCellModel.getApplicationADME()).isEqualTo(DEFAULT_APPLICATION_ADME);
        assertThat(testCellModel.getApplicationEfficacy()).isEqualTo(DEFAULT_APPLICATION_EFFICACY);
        assertThat(testCellModel.getApplicationNone()).isEqualTo(DEFAULT_APPLICATION_NONE);
        assertThat(testCellModel.getApplicationSafety()).isEqualTo(DEFAULT_APPLICATION_SAFETY);
        assertThat(testCellModel.getAnimalAssayReplaced()).isEqualTo(DEFAULT_ANIMAL_ASSAY_REPLACED);
        assertThat(testCellModel.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testCellModel.getExperts()).isEqualTo(DEFAULT_EXPERTS);
        assertThat(testCellModel.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testCellModel.getLimitationPercieved()).isEqualTo(DEFAULT_LIMITATION_PERCIEVED);
    }

    @Test
    @Transactional
    void createCellModelWithExistingId() throws Exception {
        // Create the CellModel with an existing ID
        cellModel.setId("existing_id");
        CellModelDTO cellModelDTO = cellModelMapper.toDto(cellModel);

        int databaseSizeBeforeCreate = cellModelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCellModelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cellModelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CellModel in the database
        List<CellModel> cellModelList = cellModelRepository.findAll();
        assertThat(cellModelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSpeciesIsRequired() throws Exception {
        int databaseSizeBeforeTest = cellModelRepository.findAll().size();
        // set the field null
        cellModel.setSpecies(null);

        // Create the CellModel, which fails.
        CellModelDTO cellModelDTO = cellModelMapper.toDto(cellModel);

        restCellModelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cellModelDTO)))
            .andExpect(status().isBadRequest());

        List<CellModel> cellModelList = cellModelRepository.findAll();
        assertThat(cellModelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTargetOrganIsRequired() throws Exception {
        int databaseSizeBeforeTest = cellModelRepository.findAll().size();
        // set the field null
        cellModel.setTargetOrgan(null);

        // Create the CellModel, which fails.
        CellModelDTO cellModelDTO = cellModelMapper.toDto(cellModel);

        restCellModelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cellModelDTO)))
            .andExpect(status().isBadRequest());

        List<CellModel> cellModelList = cellModelRepository.findAll();
        assertThat(cellModelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTargetTissueIsRequired() throws Exception {
        int databaseSizeBeforeTest = cellModelRepository.findAll().size();
        // set the field null
        cellModel.setTargetTissue(null);

        // Create the CellModel, which fails.
        CellModelDTO cellModelDTO = cellModelMapper.toDto(cellModel);

        restCellModelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cellModelDTO)))
            .andExpect(status().isBadRequest());

        List<CellModel> cellModelList = cellModelRepository.findAll();
        assertThat(cellModelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVesselInfoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cellModelRepository.findAll().size();
        // set the field null
        cellModel.setVesselInfo(null);

        // Create the CellModel, which fails.
        CellModelDTO cellModelDTO = cellModelMapper.toDto(cellModel);

        restCellModelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cellModelDTO)))
            .andExpect(status().isBadRequest());

        List<CellModel> cellModelList = cellModelRepository.findAll();
        assertThat(cellModelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRatingCharacterizationIsRequired() throws Exception {
        int databaseSizeBeforeTest = cellModelRepository.findAll().size();
        // set the field null
        cellModel.setRatingCharacterization(null);

        // Create the CellModel, which fails.
        CellModelDTO cellModelDTO = cellModelMapper.toDto(cellModel);

        restCellModelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cellModelDTO)))
            .andExpect(status().isBadRequest());

        List<CellModel> cellModelList = cellModelRepository.findAll();
        assertThat(cellModelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRatingComplexityIsRequired() throws Exception {
        int databaseSizeBeforeTest = cellModelRepository.findAll().size();
        // set the field null
        cellModel.setRatingComplexity(null);

        // Create the CellModel, which fails.
        CellModelDTO cellModelDTO = cellModelMapper.toDto(cellModel);

        restCellModelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cellModelDTO)))
            .andExpect(status().isBadRequest());

        List<CellModel> cellModelList = cellModelRepository.findAll();
        assertThat(cellModelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRatingModelCostIsRequired() throws Exception {
        int databaseSizeBeforeTest = cellModelRepository.findAll().size();
        // set the field null
        cellModel.setRatingModelCost(null);

        // Create the CellModel, which fails.
        CellModelDTO cellModelDTO = cellModelMapper.toDto(cellModel);

        restCellModelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cellModelDTO)))
            .andExpect(status().isBadRequest());

        List<CellModel> cellModelList = cellModelRepository.findAll();
        assertThat(cellModelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRatingThroughPutCapacityIsRequired() throws Exception {
        int databaseSizeBeforeTest = cellModelRepository.findAll().size();
        // set the field null
        cellModel.setRatingThroughPutCapacity(null);

        // Create the CellModel, which fails.
        CellModelDTO cellModelDTO = cellModelMapper.toDto(cellModel);

        restCellModelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cellModelDTO)))
            .andExpect(status().isBadRequest());

        List<CellModel> cellModelList = cellModelRepository.findAll();
        assertThat(cellModelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRatingTurnAroundTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cellModelRepository.findAll().size();
        // set the field null
        cellModel.setRatingTurnAroundTime(null);

        // Create the CellModel, which fails.
        CellModelDTO cellModelDTO = cellModelMapper.toDto(cellModel);

        restCellModelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cellModelDTO)))
            .andExpect(status().isBadRequest());

        List<CellModel> cellModelList = cellModelRepository.findAll();
        assertThat(cellModelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDepartmentIsRequired() throws Exception {
        int databaseSizeBeforeTest = cellModelRepository.findAll().size();
        // set the field null
        cellModel.setDepartment(null);

        // Create the CellModel, which fails.
        CellModelDTO cellModelDTO = cellModelMapper.toDto(cellModel);

        restCellModelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cellModelDTO)))
            .andExpect(status().isBadRequest());

        List<CellModel> cellModelList = cellModelRepository.findAll();
        assertThat(cellModelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkModelStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = cellModelRepository.findAll().size();
        // set the field null
        cellModel.setModelState(null);

        // Create the CellModel, which fails.
        CellModelDTO cellModelDTO = cellModelMapper.toDto(cellModel);

        restCellModelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cellModelDTO)))
            .andExpect(status().isBadRequest());

        List<CellModel> cellModelList = cellModelRepository.findAll();
        assertThat(cellModelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkApplicationADMEIsRequired() throws Exception {
        int databaseSizeBeforeTest = cellModelRepository.findAll().size();
        // set the field null
        cellModel.setApplicationADME(null);

        // Create the CellModel, which fails.
        CellModelDTO cellModelDTO = cellModelMapper.toDto(cellModel);

        restCellModelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cellModelDTO)))
            .andExpect(status().isBadRequest());

        List<CellModel> cellModelList = cellModelRepository.findAll();
        assertThat(cellModelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkApplicationEfficacyIsRequired() throws Exception {
        int databaseSizeBeforeTest = cellModelRepository.findAll().size();
        // set the field null
        cellModel.setApplicationEfficacy(null);

        // Create the CellModel, which fails.
        CellModelDTO cellModelDTO = cellModelMapper.toDto(cellModel);

        restCellModelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cellModelDTO)))
            .andExpect(status().isBadRequest());

        List<CellModel> cellModelList = cellModelRepository.findAll();
        assertThat(cellModelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkApplicationNoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = cellModelRepository.findAll().size();
        // set the field null
        cellModel.setApplicationNone(null);

        // Create the CellModel, which fails.
        CellModelDTO cellModelDTO = cellModelMapper.toDto(cellModel);

        restCellModelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cellModelDTO)))
            .andExpect(status().isBadRequest());

        List<CellModel> cellModelList = cellModelRepository.findAll();
        assertThat(cellModelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkApplicationSafetyIsRequired() throws Exception {
        int databaseSizeBeforeTest = cellModelRepository.findAll().size();
        // set the field null
        cellModel.setApplicationSafety(null);

        // Create the CellModel, which fails.
        CellModelDTO cellModelDTO = cellModelMapper.toDto(cellModel);

        restCellModelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cellModelDTO)))
            .andExpect(status().isBadRequest());

        List<CellModel> cellModelList = cellModelRepository.findAll();
        assertThat(cellModelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCellModels() throws Exception {
        // Initialize the database
        cellModel.setId(UUID.randomUUID().toString());
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList
        restCellModelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cellModel.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].dateAdded").value(hasItem(sameInstant(DEFAULT_DATE_ADDED))))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(sameInstant(DEFAULT_DATE_UPDATED))))
            .andExpect(jsonPath("$.[*].species").value(hasItem(DEFAULT_SPECIES)))
            .andExpect(jsonPath("$.[*].targetOrgan").value(hasItem(DEFAULT_TARGET_ORGAN)))
            .andExpect(jsonPath("$.[*].targetTissue").value(hasItem(DEFAULT_TARGET_TISSUE)))
            .andExpect(jsonPath("$.[*].vesselInfo").value(hasItem(DEFAULT_VESSEL_INFO)))
            .andExpect(jsonPath("$.[*].matrix").value(hasItem(DEFAULT_MATRIX.booleanValue())))
            .andExpect(jsonPath("$.[*].protocol").value(hasItem(DEFAULT_PROTOCOL)))
            .andExpect(jsonPath("$.[*].manufacturer").value(hasItem(DEFAULT_MANUFACTURER)))
            .andExpect(jsonPath("$.[*].matrixType").value(hasItem(DEFAULT_MATRIX_TYPE)))
            .andExpect(jsonPath("$.[*].vesselType").value(hasItem(DEFAULT_VESSEL_TYPE)))
            .andExpect(jsonPath("$.[*].inhouse").value(hasItem(DEFAULT_INHOUSE.booleanValue())))
            .andExpect(jsonPath("$.[*].ratingCharacterization").value(hasItem(DEFAULT_RATING_CHARACTERIZATION)))
            .andExpect(jsonPath("$.[*].ratingComplexity").value(hasItem(DEFAULT_RATING_COMPLEXITY)))
            .andExpect(jsonPath("$.[*].ratingModelCost").value(hasItem(DEFAULT_RATING_MODEL_COST)))
            .andExpect(jsonPath("$.[*].ratingThroughPutCapacity").value(hasItem(DEFAULT_RATING_THROUGH_PUT_CAPACITY)))
            .andExpect(jsonPath("$.[*].ratingTurnAroundTime").value(hasItem(DEFAULT_RATING_TURN_AROUND_TIME)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT)))
            .andExpect(jsonPath("$.[*].modelState").value(hasItem(DEFAULT_MODEL_STATE)))
            .andExpect(jsonPath("$.[*].applicationADME").value(hasItem(DEFAULT_APPLICATION_ADME.booleanValue())))
            .andExpect(jsonPath("$.[*].applicationEfficacy").value(hasItem(DEFAULT_APPLICATION_EFFICACY.booleanValue())))
            .andExpect(jsonPath("$.[*].applicationNone").value(hasItem(DEFAULT_APPLICATION_NONE.booleanValue())))
            .andExpect(jsonPath("$.[*].applicationSafety").value(hasItem(DEFAULT_APPLICATION_SAFETY.booleanValue())))
            .andExpect(jsonPath("$.[*].animalAssayReplaced").value(hasItem(DEFAULT_ANIMAL_ASSAY_REPLACED)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].experts").value(hasItem(DEFAULT_EXPERTS)))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)))
            .andExpect(jsonPath("$.[*].limitationPercieved").value(hasItem(DEFAULT_LIMITATION_PERCIEVED)));
    }

    @Test
    @Transactional
    void getCellModel() throws Exception {
        // Initialize the database
        cellModel.setId(UUID.randomUUID().toString());
        cellModelRepository.saveAndFlush(cellModel);

        // Get the cellModel
        restCellModelMockMvc
            .perform(get(ENTITY_API_URL_ID, cellModel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cellModel.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.dateAdded").value(sameInstant(DEFAULT_DATE_ADDED)))
            .andExpect(jsonPath("$.dateUpdated").value(sameInstant(DEFAULT_DATE_UPDATED)))
            .andExpect(jsonPath("$.species").value(DEFAULT_SPECIES))
            .andExpect(jsonPath("$.targetOrgan").value(DEFAULT_TARGET_ORGAN))
            .andExpect(jsonPath("$.targetTissue").value(DEFAULT_TARGET_TISSUE))
            .andExpect(jsonPath("$.vesselInfo").value(DEFAULT_VESSEL_INFO))
            .andExpect(jsonPath("$.matrix").value(DEFAULT_MATRIX.booleanValue()))
            .andExpect(jsonPath("$.protocol").value(DEFAULT_PROTOCOL))
            .andExpect(jsonPath("$.manufacturer").value(DEFAULT_MANUFACTURER))
            .andExpect(jsonPath("$.matrixType").value(DEFAULT_MATRIX_TYPE))
            .andExpect(jsonPath("$.vesselType").value(DEFAULT_VESSEL_TYPE))
            .andExpect(jsonPath("$.inhouse").value(DEFAULT_INHOUSE.booleanValue()))
            .andExpect(jsonPath("$.ratingCharacterization").value(DEFAULT_RATING_CHARACTERIZATION))
            .andExpect(jsonPath("$.ratingComplexity").value(DEFAULT_RATING_COMPLEXITY))
            .andExpect(jsonPath("$.ratingModelCost").value(DEFAULT_RATING_MODEL_COST))
            .andExpect(jsonPath("$.ratingThroughPutCapacity").value(DEFAULT_RATING_THROUGH_PUT_CAPACITY))
            .andExpect(jsonPath("$.ratingTurnAroundTime").value(DEFAULT_RATING_TURN_AROUND_TIME))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.department").value(DEFAULT_DEPARTMENT))
            .andExpect(jsonPath("$.modelState").value(DEFAULT_MODEL_STATE))
            .andExpect(jsonPath("$.applicationADME").value(DEFAULT_APPLICATION_ADME.booleanValue()))
            .andExpect(jsonPath("$.applicationEfficacy").value(DEFAULT_APPLICATION_EFFICACY.booleanValue()))
            .andExpect(jsonPath("$.applicationNone").value(DEFAULT_APPLICATION_NONE.booleanValue()))
            .andExpect(jsonPath("$.applicationSafety").value(DEFAULT_APPLICATION_SAFETY.booleanValue()))
            .andExpect(jsonPath("$.animalAssayReplaced").value(DEFAULT_ANIMAL_ASSAY_REPLACED))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.experts").value(DEFAULT_EXPERTS))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK))
            .andExpect(jsonPath("$.limitationPercieved").value(DEFAULT_LIMITATION_PERCIEVED));
    }

    @Test
    @Transactional
    void getCellModelsByIdFiltering() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        String id = cellModel.getId();

        defaultCellModelShouldBeFound("id.equals=" + id);
        defaultCellModelShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllCellModelsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where name equals to DEFAULT_NAME
        defaultCellModelShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cellModelList where name equals to UPDATED_NAME
        defaultCellModelShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCellModelsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCellModelShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cellModelList where name equals to UPDATED_NAME
        defaultCellModelShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCellModelsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where name is not null
        defaultCellModelShouldBeFound("name.specified=true");

        // Get all the cellModelList where name is null
        defaultCellModelShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllCellModelsByNameContainsSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where name contains DEFAULT_NAME
        defaultCellModelShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cellModelList where name contains UPDATED_NAME
        defaultCellModelShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCellModelsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where name does not contain DEFAULT_NAME
        defaultCellModelShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cellModelList where name does not contain UPDATED_NAME
        defaultCellModelShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCellModelsByDateAddedIsEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where dateAdded equals to DEFAULT_DATE_ADDED
        defaultCellModelShouldBeFound("dateAdded.equals=" + DEFAULT_DATE_ADDED);

        // Get all the cellModelList where dateAdded equals to UPDATED_DATE_ADDED
        defaultCellModelShouldNotBeFound("dateAdded.equals=" + UPDATED_DATE_ADDED);
    }

    @Test
    @Transactional
    void getAllCellModelsByDateAddedIsInShouldWork() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where dateAdded in DEFAULT_DATE_ADDED or UPDATED_DATE_ADDED
        defaultCellModelShouldBeFound("dateAdded.in=" + DEFAULT_DATE_ADDED + "," + UPDATED_DATE_ADDED);

        // Get all the cellModelList where dateAdded equals to UPDATED_DATE_ADDED
        defaultCellModelShouldNotBeFound("dateAdded.in=" + UPDATED_DATE_ADDED);
    }

    @Test
    @Transactional
    void getAllCellModelsByDateAddedIsNullOrNotNull() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where dateAdded is not null
        defaultCellModelShouldBeFound("dateAdded.specified=true");

        // Get all the cellModelList where dateAdded is null
        defaultCellModelShouldNotBeFound("dateAdded.specified=false");
    }

    @Test
    @Transactional
    void getAllCellModelsByDateAddedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where dateAdded is greater than or equal to DEFAULT_DATE_ADDED
        defaultCellModelShouldBeFound("dateAdded.greaterThanOrEqual=" + DEFAULT_DATE_ADDED);

        // Get all the cellModelList where dateAdded is greater than or equal to UPDATED_DATE_ADDED
        defaultCellModelShouldNotBeFound("dateAdded.greaterThanOrEqual=" + UPDATED_DATE_ADDED);
    }

    @Test
    @Transactional
    void getAllCellModelsByDateAddedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where dateAdded is less than or equal to DEFAULT_DATE_ADDED
        defaultCellModelShouldBeFound("dateAdded.lessThanOrEqual=" + DEFAULT_DATE_ADDED);

        // Get all the cellModelList where dateAdded is less than or equal to SMALLER_DATE_ADDED
        defaultCellModelShouldNotBeFound("dateAdded.lessThanOrEqual=" + SMALLER_DATE_ADDED);
    }

    @Test
    @Transactional
    void getAllCellModelsByDateAddedIsLessThanSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where dateAdded is less than DEFAULT_DATE_ADDED
        defaultCellModelShouldNotBeFound("dateAdded.lessThan=" + DEFAULT_DATE_ADDED);

        // Get all the cellModelList where dateAdded is less than UPDATED_DATE_ADDED
        defaultCellModelShouldBeFound("dateAdded.lessThan=" + UPDATED_DATE_ADDED);
    }

    @Test
    @Transactional
    void getAllCellModelsByDateAddedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where dateAdded is greater than DEFAULT_DATE_ADDED
        defaultCellModelShouldNotBeFound("dateAdded.greaterThan=" + DEFAULT_DATE_ADDED);

        // Get all the cellModelList where dateAdded is greater than SMALLER_DATE_ADDED
        defaultCellModelShouldBeFound("dateAdded.greaterThan=" + SMALLER_DATE_ADDED);
    }

    @Test
    @Transactional
    void getAllCellModelsByDateUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where dateUpdated equals to DEFAULT_DATE_UPDATED
        defaultCellModelShouldBeFound("dateUpdated.equals=" + DEFAULT_DATE_UPDATED);

        // Get all the cellModelList where dateUpdated equals to UPDATED_DATE_UPDATED
        defaultCellModelShouldNotBeFound("dateUpdated.equals=" + UPDATED_DATE_UPDATED);
    }

    @Test
    @Transactional
    void getAllCellModelsByDateUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where dateUpdated in DEFAULT_DATE_UPDATED or UPDATED_DATE_UPDATED
        defaultCellModelShouldBeFound("dateUpdated.in=" + DEFAULT_DATE_UPDATED + "," + UPDATED_DATE_UPDATED);

        // Get all the cellModelList where dateUpdated equals to UPDATED_DATE_UPDATED
        defaultCellModelShouldNotBeFound("dateUpdated.in=" + UPDATED_DATE_UPDATED);
    }

    @Test
    @Transactional
    void getAllCellModelsByDateUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where dateUpdated is not null
        defaultCellModelShouldBeFound("dateUpdated.specified=true");

        // Get all the cellModelList where dateUpdated is null
        defaultCellModelShouldNotBeFound("dateUpdated.specified=false");
    }

    @Test
    @Transactional
    void getAllCellModelsByDateUpdatedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where dateUpdated is greater than or equal to DEFAULT_DATE_UPDATED
        defaultCellModelShouldBeFound("dateUpdated.greaterThanOrEqual=" + DEFAULT_DATE_UPDATED);

        // Get all the cellModelList where dateUpdated is greater than or equal to UPDATED_DATE_UPDATED
        defaultCellModelShouldNotBeFound("dateUpdated.greaterThanOrEqual=" + UPDATED_DATE_UPDATED);
    }

    @Test
    @Transactional
    void getAllCellModelsByDateUpdatedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where dateUpdated is less than or equal to DEFAULT_DATE_UPDATED
        defaultCellModelShouldBeFound("dateUpdated.lessThanOrEqual=" + DEFAULT_DATE_UPDATED);

        // Get all the cellModelList where dateUpdated is less than or equal to SMALLER_DATE_UPDATED
        defaultCellModelShouldNotBeFound("dateUpdated.lessThanOrEqual=" + SMALLER_DATE_UPDATED);
    }

    @Test
    @Transactional
    void getAllCellModelsByDateUpdatedIsLessThanSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where dateUpdated is less than DEFAULT_DATE_UPDATED
        defaultCellModelShouldNotBeFound("dateUpdated.lessThan=" + DEFAULT_DATE_UPDATED);

        // Get all the cellModelList where dateUpdated is less than UPDATED_DATE_UPDATED
        defaultCellModelShouldBeFound("dateUpdated.lessThan=" + UPDATED_DATE_UPDATED);
    }

    @Test
    @Transactional
    void getAllCellModelsByDateUpdatedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where dateUpdated is greater than DEFAULT_DATE_UPDATED
        defaultCellModelShouldNotBeFound("dateUpdated.greaterThan=" + DEFAULT_DATE_UPDATED);

        // Get all the cellModelList where dateUpdated is greater than SMALLER_DATE_UPDATED
        defaultCellModelShouldBeFound("dateUpdated.greaterThan=" + SMALLER_DATE_UPDATED);
    }

    @Test
    @Transactional
    void getAllCellModelsBySpeciesIsEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where species equals to DEFAULT_SPECIES
        defaultCellModelShouldBeFound("species.equals=" + DEFAULT_SPECIES);

        // Get all the cellModelList where species equals to UPDATED_SPECIES
        defaultCellModelShouldNotBeFound("species.equals=" + UPDATED_SPECIES);
    }

    @Test
    @Transactional
    void getAllCellModelsBySpeciesIsInShouldWork() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where species in DEFAULT_SPECIES or UPDATED_SPECIES
        defaultCellModelShouldBeFound("species.in=" + DEFAULT_SPECIES + "," + UPDATED_SPECIES);

        // Get all the cellModelList where species equals to UPDATED_SPECIES
        defaultCellModelShouldNotBeFound("species.in=" + UPDATED_SPECIES);
    }

    @Test
    @Transactional
    void getAllCellModelsBySpeciesIsNullOrNotNull() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where species is not null
        defaultCellModelShouldBeFound("species.specified=true");

        // Get all the cellModelList where species is null
        defaultCellModelShouldNotBeFound("species.specified=false");
    }

    @Test
    @Transactional
    void getAllCellModelsBySpeciesContainsSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where species contains DEFAULT_SPECIES
        defaultCellModelShouldBeFound("species.contains=" + DEFAULT_SPECIES);

        // Get all the cellModelList where species contains UPDATED_SPECIES
        defaultCellModelShouldNotBeFound("species.contains=" + UPDATED_SPECIES);
    }

    @Test
    @Transactional
    void getAllCellModelsBySpeciesNotContainsSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where species does not contain DEFAULT_SPECIES
        defaultCellModelShouldNotBeFound("species.doesNotContain=" + DEFAULT_SPECIES);

        // Get all the cellModelList where species does not contain UPDATED_SPECIES
        defaultCellModelShouldBeFound("species.doesNotContain=" + UPDATED_SPECIES);
    }

    @Test
    @Transactional
    void getAllCellModelsByTargetOrganIsEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where targetOrgan equals to DEFAULT_TARGET_ORGAN
        defaultCellModelShouldBeFound("targetOrgan.equals=" + DEFAULT_TARGET_ORGAN);

        // Get all the cellModelList where targetOrgan equals to UPDATED_TARGET_ORGAN
        defaultCellModelShouldNotBeFound("targetOrgan.equals=" + UPDATED_TARGET_ORGAN);
    }

    @Test
    @Transactional
    void getAllCellModelsByTargetOrganIsInShouldWork() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where targetOrgan in DEFAULT_TARGET_ORGAN or UPDATED_TARGET_ORGAN
        defaultCellModelShouldBeFound("targetOrgan.in=" + DEFAULT_TARGET_ORGAN + "," + UPDATED_TARGET_ORGAN);

        // Get all the cellModelList where targetOrgan equals to UPDATED_TARGET_ORGAN
        defaultCellModelShouldNotBeFound("targetOrgan.in=" + UPDATED_TARGET_ORGAN);
    }

    @Test
    @Transactional
    void getAllCellModelsByTargetOrganIsNullOrNotNull() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where targetOrgan is not null
        defaultCellModelShouldBeFound("targetOrgan.specified=true");

        // Get all the cellModelList where targetOrgan is null
        defaultCellModelShouldNotBeFound("targetOrgan.specified=false");
    }

    @Test
    @Transactional
    void getAllCellModelsByTargetOrganContainsSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where targetOrgan contains DEFAULT_TARGET_ORGAN
        defaultCellModelShouldBeFound("targetOrgan.contains=" + DEFAULT_TARGET_ORGAN);

        // Get all the cellModelList where targetOrgan contains UPDATED_TARGET_ORGAN
        defaultCellModelShouldNotBeFound("targetOrgan.contains=" + UPDATED_TARGET_ORGAN);
    }

    @Test
    @Transactional
    void getAllCellModelsByTargetOrganNotContainsSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where targetOrgan does not contain DEFAULT_TARGET_ORGAN
        defaultCellModelShouldNotBeFound("targetOrgan.doesNotContain=" + DEFAULT_TARGET_ORGAN);

        // Get all the cellModelList where targetOrgan does not contain UPDATED_TARGET_ORGAN
        defaultCellModelShouldBeFound("targetOrgan.doesNotContain=" + UPDATED_TARGET_ORGAN);
    }

    @Test
    @Transactional
    void getAllCellModelsByTargetTissueIsEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where targetTissue equals to DEFAULT_TARGET_TISSUE
        defaultCellModelShouldBeFound("targetTissue.equals=" + DEFAULT_TARGET_TISSUE);

        // Get all the cellModelList where targetTissue equals to UPDATED_TARGET_TISSUE
        defaultCellModelShouldNotBeFound("targetTissue.equals=" + UPDATED_TARGET_TISSUE);
    }

    @Test
    @Transactional
    void getAllCellModelsByTargetTissueIsInShouldWork() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where targetTissue in DEFAULT_TARGET_TISSUE or UPDATED_TARGET_TISSUE
        defaultCellModelShouldBeFound("targetTissue.in=" + DEFAULT_TARGET_TISSUE + "," + UPDATED_TARGET_TISSUE);

        // Get all the cellModelList where targetTissue equals to UPDATED_TARGET_TISSUE
        defaultCellModelShouldNotBeFound("targetTissue.in=" + UPDATED_TARGET_TISSUE);
    }

    @Test
    @Transactional
    void getAllCellModelsByTargetTissueIsNullOrNotNull() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where targetTissue is not null
        defaultCellModelShouldBeFound("targetTissue.specified=true");

        // Get all the cellModelList where targetTissue is null
        defaultCellModelShouldNotBeFound("targetTissue.specified=false");
    }

    @Test
    @Transactional
    void getAllCellModelsByTargetTissueContainsSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where targetTissue contains DEFAULT_TARGET_TISSUE
        defaultCellModelShouldBeFound("targetTissue.contains=" + DEFAULT_TARGET_TISSUE);

        // Get all the cellModelList where targetTissue contains UPDATED_TARGET_TISSUE
        defaultCellModelShouldNotBeFound("targetTissue.contains=" + UPDATED_TARGET_TISSUE);
    }

    @Test
    @Transactional
    void getAllCellModelsByTargetTissueNotContainsSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where targetTissue does not contain DEFAULT_TARGET_TISSUE
        defaultCellModelShouldNotBeFound("targetTissue.doesNotContain=" + DEFAULT_TARGET_TISSUE);

        // Get all the cellModelList where targetTissue does not contain UPDATED_TARGET_TISSUE
        defaultCellModelShouldBeFound("targetTissue.doesNotContain=" + UPDATED_TARGET_TISSUE);
    }

    @Test
    @Transactional
    void getAllCellModelsByVesselInfoIsEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where vesselInfo equals to DEFAULT_VESSEL_INFO
        defaultCellModelShouldBeFound("vesselInfo.equals=" + DEFAULT_VESSEL_INFO);

        // Get all the cellModelList where vesselInfo equals to UPDATED_VESSEL_INFO
        defaultCellModelShouldNotBeFound("vesselInfo.equals=" + UPDATED_VESSEL_INFO);
    }

    @Test
    @Transactional
    void getAllCellModelsByVesselInfoIsInShouldWork() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where vesselInfo in DEFAULT_VESSEL_INFO or UPDATED_VESSEL_INFO
        defaultCellModelShouldBeFound("vesselInfo.in=" + DEFAULT_VESSEL_INFO + "," + UPDATED_VESSEL_INFO);

        // Get all the cellModelList where vesselInfo equals to UPDATED_VESSEL_INFO
        defaultCellModelShouldNotBeFound("vesselInfo.in=" + UPDATED_VESSEL_INFO);
    }

    @Test
    @Transactional
    void getAllCellModelsByVesselInfoIsNullOrNotNull() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where vesselInfo is not null
        defaultCellModelShouldBeFound("vesselInfo.specified=true");

        // Get all the cellModelList where vesselInfo is null
        defaultCellModelShouldNotBeFound("vesselInfo.specified=false");
    }

    @Test
    @Transactional
    void getAllCellModelsByVesselInfoContainsSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where vesselInfo contains DEFAULT_VESSEL_INFO
        defaultCellModelShouldBeFound("vesselInfo.contains=" + DEFAULT_VESSEL_INFO);

        // Get all the cellModelList where vesselInfo contains UPDATED_VESSEL_INFO
        defaultCellModelShouldNotBeFound("vesselInfo.contains=" + UPDATED_VESSEL_INFO);
    }

    @Test
    @Transactional
    void getAllCellModelsByVesselInfoNotContainsSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where vesselInfo does not contain DEFAULT_VESSEL_INFO
        defaultCellModelShouldNotBeFound("vesselInfo.doesNotContain=" + DEFAULT_VESSEL_INFO);

        // Get all the cellModelList where vesselInfo does not contain UPDATED_VESSEL_INFO
        defaultCellModelShouldBeFound("vesselInfo.doesNotContain=" + UPDATED_VESSEL_INFO);
    }

    @Test
    @Transactional
    void getAllCellModelsByMatrixIsEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where matrix equals to DEFAULT_MATRIX
        defaultCellModelShouldBeFound("matrix.equals=" + DEFAULT_MATRIX);

        // Get all the cellModelList where matrix equals to UPDATED_MATRIX
        defaultCellModelShouldNotBeFound("matrix.equals=" + UPDATED_MATRIX);
    }

    @Test
    @Transactional
    void getAllCellModelsByMatrixIsInShouldWork() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where matrix in DEFAULT_MATRIX or UPDATED_MATRIX
        defaultCellModelShouldBeFound("matrix.in=" + DEFAULT_MATRIX + "," + UPDATED_MATRIX);

        // Get all the cellModelList where matrix equals to UPDATED_MATRIX
        defaultCellModelShouldNotBeFound("matrix.in=" + UPDATED_MATRIX);
    }

    @Test
    @Transactional
    void getAllCellModelsByMatrixIsNullOrNotNull() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where matrix is not null
        defaultCellModelShouldBeFound("matrix.specified=true");

        // Get all the cellModelList where matrix is null
        defaultCellModelShouldNotBeFound("matrix.specified=false");
    }

    @Test
    @Transactional
    void getAllCellModelsByProtocolIsEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where protocol equals to DEFAULT_PROTOCOL
        defaultCellModelShouldBeFound("protocol.equals=" + DEFAULT_PROTOCOL);

        // Get all the cellModelList where protocol equals to UPDATED_PROTOCOL
        defaultCellModelShouldNotBeFound("protocol.equals=" + UPDATED_PROTOCOL);
    }

    @Test
    @Transactional
    void getAllCellModelsByProtocolIsInShouldWork() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where protocol in DEFAULT_PROTOCOL or UPDATED_PROTOCOL
        defaultCellModelShouldBeFound("protocol.in=" + DEFAULT_PROTOCOL + "," + UPDATED_PROTOCOL);

        // Get all the cellModelList where protocol equals to UPDATED_PROTOCOL
        defaultCellModelShouldNotBeFound("protocol.in=" + UPDATED_PROTOCOL);
    }

    @Test
    @Transactional
    void getAllCellModelsByProtocolIsNullOrNotNull() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where protocol is not null
        defaultCellModelShouldBeFound("protocol.specified=true");

        // Get all the cellModelList where protocol is null
        defaultCellModelShouldNotBeFound("protocol.specified=false");
    }

    @Test
    @Transactional
    void getAllCellModelsByProtocolContainsSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where protocol contains DEFAULT_PROTOCOL
        defaultCellModelShouldBeFound("protocol.contains=" + DEFAULT_PROTOCOL);

        // Get all the cellModelList where protocol contains UPDATED_PROTOCOL
        defaultCellModelShouldNotBeFound("protocol.contains=" + UPDATED_PROTOCOL);
    }

    @Test
    @Transactional
    void getAllCellModelsByProtocolNotContainsSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where protocol does not contain DEFAULT_PROTOCOL
        defaultCellModelShouldNotBeFound("protocol.doesNotContain=" + DEFAULT_PROTOCOL);

        // Get all the cellModelList where protocol does not contain UPDATED_PROTOCOL
        defaultCellModelShouldBeFound("protocol.doesNotContain=" + UPDATED_PROTOCOL);
    }

    @Test
    @Transactional
    void getAllCellModelsByManufacturerIsEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where manufacturer equals to DEFAULT_MANUFACTURER
        defaultCellModelShouldBeFound("manufacturer.equals=" + DEFAULT_MANUFACTURER);

        // Get all the cellModelList where manufacturer equals to UPDATED_MANUFACTURER
        defaultCellModelShouldNotBeFound("manufacturer.equals=" + UPDATED_MANUFACTURER);
    }

    @Test
    @Transactional
    void getAllCellModelsByManufacturerIsInShouldWork() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where manufacturer in DEFAULT_MANUFACTURER or UPDATED_MANUFACTURER
        defaultCellModelShouldBeFound("manufacturer.in=" + DEFAULT_MANUFACTURER + "," + UPDATED_MANUFACTURER);

        // Get all the cellModelList where manufacturer equals to UPDATED_MANUFACTURER
        defaultCellModelShouldNotBeFound("manufacturer.in=" + UPDATED_MANUFACTURER);
    }

    @Test
    @Transactional
    void getAllCellModelsByManufacturerIsNullOrNotNull() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where manufacturer is not null
        defaultCellModelShouldBeFound("manufacturer.specified=true");

        // Get all the cellModelList where manufacturer is null
        defaultCellModelShouldNotBeFound("manufacturer.specified=false");
    }

    @Test
    @Transactional
    void getAllCellModelsByManufacturerContainsSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where manufacturer contains DEFAULT_MANUFACTURER
        defaultCellModelShouldBeFound("manufacturer.contains=" + DEFAULT_MANUFACTURER);

        // Get all the cellModelList where manufacturer contains UPDATED_MANUFACTURER
        defaultCellModelShouldNotBeFound("manufacturer.contains=" + UPDATED_MANUFACTURER);
    }

    @Test
    @Transactional
    void getAllCellModelsByManufacturerNotContainsSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where manufacturer does not contain DEFAULT_MANUFACTURER
        defaultCellModelShouldNotBeFound("manufacturer.doesNotContain=" + DEFAULT_MANUFACTURER);

        // Get all the cellModelList where manufacturer does not contain UPDATED_MANUFACTURER
        defaultCellModelShouldBeFound("manufacturer.doesNotContain=" + UPDATED_MANUFACTURER);
    }

    @Test
    @Transactional
    void getAllCellModelsByMatrixTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where matrixType equals to DEFAULT_MATRIX_TYPE
        defaultCellModelShouldBeFound("matrixType.equals=" + DEFAULT_MATRIX_TYPE);

        // Get all the cellModelList where matrixType equals to UPDATED_MATRIX_TYPE
        defaultCellModelShouldNotBeFound("matrixType.equals=" + UPDATED_MATRIX_TYPE);
    }

    @Test
    @Transactional
    void getAllCellModelsByMatrixTypeIsInShouldWork() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where matrixType in DEFAULT_MATRIX_TYPE or UPDATED_MATRIX_TYPE
        defaultCellModelShouldBeFound("matrixType.in=" + DEFAULT_MATRIX_TYPE + "," + UPDATED_MATRIX_TYPE);

        // Get all the cellModelList where matrixType equals to UPDATED_MATRIX_TYPE
        defaultCellModelShouldNotBeFound("matrixType.in=" + UPDATED_MATRIX_TYPE);
    }

    @Test
    @Transactional
    void getAllCellModelsByMatrixTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where matrixType is not null
        defaultCellModelShouldBeFound("matrixType.specified=true");

        // Get all the cellModelList where matrixType is null
        defaultCellModelShouldNotBeFound("matrixType.specified=false");
    }

    @Test
    @Transactional
    void getAllCellModelsByMatrixTypeContainsSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where matrixType contains DEFAULT_MATRIX_TYPE
        defaultCellModelShouldBeFound("matrixType.contains=" + DEFAULT_MATRIX_TYPE);

        // Get all the cellModelList where matrixType contains UPDATED_MATRIX_TYPE
        defaultCellModelShouldNotBeFound("matrixType.contains=" + UPDATED_MATRIX_TYPE);
    }

    @Test
    @Transactional
    void getAllCellModelsByMatrixTypeNotContainsSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where matrixType does not contain DEFAULT_MATRIX_TYPE
        defaultCellModelShouldNotBeFound("matrixType.doesNotContain=" + DEFAULT_MATRIX_TYPE);

        // Get all the cellModelList where matrixType does not contain UPDATED_MATRIX_TYPE
        defaultCellModelShouldBeFound("matrixType.doesNotContain=" + UPDATED_MATRIX_TYPE);
    }

    @Test
    @Transactional
    void getAllCellModelsByVesselTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where vesselType equals to DEFAULT_VESSEL_TYPE
        defaultCellModelShouldBeFound("vesselType.equals=" + DEFAULT_VESSEL_TYPE);

        // Get all the cellModelList where vesselType equals to UPDATED_VESSEL_TYPE
        defaultCellModelShouldNotBeFound("vesselType.equals=" + UPDATED_VESSEL_TYPE);
    }

    @Test
    @Transactional
    void getAllCellModelsByVesselTypeIsInShouldWork() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where vesselType in DEFAULT_VESSEL_TYPE or UPDATED_VESSEL_TYPE
        defaultCellModelShouldBeFound("vesselType.in=" + DEFAULT_VESSEL_TYPE + "," + UPDATED_VESSEL_TYPE);

        // Get all the cellModelList where vesselType equals to UPDATED_VESSEL_TYPE
        defaultCellModelShouldNotBeFound("vesselType.in=" + UPDATED_VESSEL_TYPE);
    }

    @Test
    @Transactional
    void getAllCellModelsByVesselTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where vesselType is not null
        defaultCellModelShouldBeFound("vesselType.specified=true");

        // Get all the cellModelList where vesselType is null
        defaultCellModelShouldNotBeFound("vesselType.specified=false");
    }

    @Test
    @Transactional
    void getAllCellModelsByVesselTypeContainsSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where vesselType contains DEFAULT_VESSEL_TYPE
        defaultCellModelShouldBeFound("vesselType.contains=" + DEFAULT_VESSEL_TYPE);

        // Get all the cellModelList where vesselType contains UPDATED_VESSEL_TYPE
        defaultCellModelShouldNotBeFound("vesselType.contains=" + UPDATED_VESSEL_TYPE);
    }

    @Test
    @Transactional
    void getAllCellModelsByVesselTypeNotContainsSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where vesselType does not contain DEFAULT_VESSEL_TYPE
        defaultCellModelShouldNotBeFound("vesselType.doesNotContain=" + DEFAULT_VESSEL_TYPE);

        // Get all the cellModelList where vesselType does not contain UPDATED_VESSEL_TYPE
        defaultCellModelShouldBeFound("vesselType.doesNotContain=" + UPDATED_VESSEL_TYPE);
    }

    @Test
    @Transactional
    void getAllCellModelsByInhouseIsEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where inhouse equals to DEFAULT_INHOUSE
        defaultCellModelShouldBeFound("inhouse.equals=" + DEFAULT_INHOUSE);

        // Get all the cellModelList where inhouse equals to UPDATED_INHOUSE
        defaultCellModelShouldNotBeFound("inhouse.equals=" + UPDATED_INHOUSE);
    }

    @Test
    @Transactional
    void getAllCellModelsByInhouseIsInShouldWork() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where inhouse in DEFAULT_INHOUSE or UPDATED_INHOUSE
        defaultCellModelShouldBeFound("inhouse.in=" + DEFAULT_INHOUSE + "," + UPDATED_INHOUSE);

        // Get all the cellModelList where inhouse equals to UPDATED_INHOUSE
        defaultCellModelShouldNotBeFound("inhouse.in=" + UPDATED_INHOUSE);
    }

    @Test
    @Transactional
    void getAllCellModelsByInhouseIsNullOrNotNull() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where inhouse is not null
        defaultCellModelShouldBeFound("inhouse.specified=true");

        // Get all the cellModelList where inhouse is null
        defaultCellModelShouldNotBeFound("inhouse.specified=false");
    }

    @Test
    @Transactional
    void getAllCellModelsByRatingCharacterizationIsEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where ratingCharacterization equals to DEFAULT_RATING_CHARACTERIZATION
        defaultCellModelShouldBeFound("ratingCharacterization.equals=" + DEFAULT_RATING_CHARACTERIZATION);

        // Get all the cellModelList where ratingCharacterization equals to UPDATED_RATING_CHARACTERIZATION
        defaultCellModelShouldNotBeFound("ratingCharacterization.equals=" + UPDATED_RATING_CHARACTERIZATION);
    }

    @Test
    @Transactional
    void getAllCellModelsByRatingCharacterizationIsInShouldWork() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where ratingCharacterization in DEFAULT_RATING_CHARACTERIZATION or UPDATED_RATING_CHARACTERIZATION
        defaultCellModelShouldBeFound(
            "ratingCharacterization.in=" + DEFAULT_RATING_CHARACTERIZATION + "," + UPDATED_RATING_CHARACTERIZATION
        );

        // Get all the cellModelList where ratingCharacterization equals to UPDATED_RATING_CHARACTERIZATION
        defaultCellModelShouldNotBeFound("ratingCharacterization.in=" + UPDATED_RATING_CHARACTERIZATION);
    }

    @Test
    @Transactional
    void getAllCellModelsByRatingCharacterizationIsNullOrNotNull() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where ratingCharacterization is not null
        defaultCellModelShouldBeFound("ratingCharacterization.specified=true");

        // Get all the cellModelList where ratingCharacterization is null
        defaultCellModelShouldNotBeFound("ratingCharacterization.specified=false");
    }

    @Test
    @Transactional
    void getAllCellModelsByRatingCharacterizationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where ratingCharacterization is greater than or equal to DEFAULT_RATING_CHARACTERIZATION
        defaultCellModelShouldBeFound("ratingCharacterization.greaterThanOrEqual=" + DEFAULT_RATING_CHARACTERIZATION);

        // Get all the cellModelList where ratingCharacterization is greater than or equal to UPDATED_RATING_CHARACTERIZATION
        defaultCellModelShouldNotBeFound("ratingCharacterization.greaterThanOrEqual=" + UPDATED_RATING_CHARACTERIZATION);
    }

    @Test
    @Transactional
    void getAllCellModelsByRatingCharacterizationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where ratingCharacterization is less than or equal to DEFAULT_RATING_CHARACTERIZATION
        defaultCellModelShouldBeFound("ratingCharacterization.lessThanOrEqual=" + DEFAULT_RATING_CHARACTERIZATION);

        // Get all the cellModelList where ratingCharacterization is less than or equal to SMALLER_RATING_CHARACTERIZATION
        defaultCellModelShouldNotBeFound("ratingCharacterization.lessThanOrEqual=" + SMALLER_RATING_CHARACTERIZATION);
    }

    @Test
    @Transactional
    void getAllCellModelsByRatingCharacterizationIsLessThanSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where ratingCharacterization is less than DEFAULT_RATING_CHARACTERIZATION
        defaultCellModelShouldNotBeFound("ratingCharacterization.lessThan=" + DEFAULT_RATING_CHARACTERIZATION);

        // Get all the cellModelList where ratingCharacterization is less than UPDATED_RATING_CHARACTERIZATION
        defaultCellModelShouldBeFound("ratingCharacterization.lessThan=" + UPDATED_RATING_CHARACTERIZATION);
    }

    @Test
    @Transactional
    void getAllCellModelsByRatingCharacterizationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where ratingCharacterization is greater than DEFAULT_RATING_CHARACTERIZATION
        defaultCellModelShouldNotBeFound("ratingCharacterization.greaterThan=" + DEFAULT_RATING_CHARACTERIZATION);

        // Get all the cellModelList where ratingCharacterization is greater than SMALLER_RATING_CHARACTERIZATION
        defaultCellModelShouldBeFound("ratingCharacterization.greaterThan=" + SMALLER_RATING_CHARACTERIZATION);
    }

    @Test
    @Transactional
    void getAllCellModelsByRatingComplexityIsEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where ratingComplexity equals to DEFAULT_RATING_COMPLEXITY
        defaultCellModelShouldBeFound("ratingComplexity.equals=" + DEFAULT_RATING_COMPLEXITY);

        // Get all the cellModelList where ratingComplexity equals to UPDATED_RATING_COMPLEXITY
        defaultCellModelShouldNotBeFound("ratingComplexity.equals=" + UPDATED_RATING_COMPLEXITY);
    }

    @Test
    @Transactional
    void getAllCellModelsByRatingComplexityIsInShouldWork() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where ratingComplexity in DEFAULT_RATING_COMPLEXITY or UPDATED_RATING_COMPLEXITY
        defaultCellModelShouldBeFound("ratingComplexity.in=" + DEFAULT_RATING_COMPLEXITY + "," + UPDATED_RATING_COMPLEXITY);

        // Get all the cellModelList where ratingComplexity equals to UPDATED_RATING_COMPLEXITY
        defaultCellModelShouldNotBeFound("ratingComplexity.in=" + UPDATED_RATING_COMPLEXITY);
    }

    @Test
    @Transactional
    void getAllCellModelsByRatingComplexityIsNullOrNotNull() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where ratingComplexity is not null
        defaultCellModelShouldBeFound("ratingComplexity.specified=true");

        // Get all the cellModelList where ratingComplexity is null
        defaultCellModelShouldNotBeFound("ratingComplexity.specified=false");
    }

    @Test
    @Transactional
    void getAllCellModelsByRatingComplexityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where ratingComplexity is greater than or equal to DEFAULT_RATING_COMPLEXITY
        defaultCellModelShouldBeFound("ratingComplexity.greaterThanOrEqual=" + DEFAULT_RATING_COMPLEXITY);

        // Get all the cellModelList where ratingComplexity is greater than or equal to UPDATED_RATING_COMPLEXITY
        defaultCellModelShouldNotBeFound("ratingComplexity.greaterThanOrEqual=" + UPDATED_RATING_COMPLEXITY);
    }

    @Test
    @Transactional
    void getAllCellModelsByRatingComplexityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where ratingComplexity is less than or equal to DEFAULT_RATING_COMPLEXITY
        defaultCellModelShouldBeFound("ratingComplexity.lessThanOrEqual=" + DEFAULT_RATING_COMPLEXITY);

        // Get all the cellModelList where ratingComplexity is less than or equal to SMALLER_RATING_COMPLEXITY
        defaultCellModelShouldNotBeFound("ratingComplexity.lessThanOrEqual=" + SMALLER_RATING_COMPLEXITY);
    }

    @Test
    @Transactional
    void getAllCellModelsByRatingComplexityIsLessThanSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where ratingComplexity is less than DEFAULT_RATING_COMPLEXITY
        defaultCellModelShouldNotBeFound("ratingComplexity.lessThan=" + DEFAULT_RATING_COMPLEXITY);

        // Get all the cellModelList where ratingComplexity is less than UPDATED_RATING_COMPLEXITY
        defaultCellModelShouldBeFound("ratingComplexity.lessThan=" + UPDATED_RATING_COMPLEXITY);
    }

    @Test
    @Transactional
    void getAllCellModelsByRatingComplexityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where ratingComplexity is greater than DEFAULT_RATING_COMPLEXITY
        defaultCellModelShouldNotBeFound("ratingComplexity.greaterThan=" + DEFAULT_RATING_COMPLEXITY);

        // Get all the cellModelList where ratingComplexity is greater than SMALLER_RATING_COMPLEXITY
        defaultCellModelShouldBeFound("ratingComplexity.greaterThan=" + SMALLER_RATING_COMPLEXITY);
    }

    @Test
    @Transactional
    void getAllCellModelsByRatingModelCostIsEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where ratingModelCost equals to DEFAULT_RATING_MODEL_COST
        defaultCellModelShouldBeFound("ratingModelCost.equals=" + DEFAULT_RATING_MODEL_COST);

        // Get all the cellModelList where ratingModelCost equals to UPDATED_RATING_MODEL_COST
        defaultCellModelShouldNotBeFound("ratingModelCost.equals=" + UPDATED_RATING_MODEL_COST);
    }

    @Test
    @Transactional
    void getAllCellModelsByRatingModelCostIsInShouldWork() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where ratingModelCost in DEFAULT_RATING_MODEL_COST or UPDATED_RATING_MODEL_COST
        defaultCellModelShouldBeFound("ratingModelCost.in=" + DEFAULT_RATING_MODEL_COST + "," + UPDATED_RATING_MODEL_COST);

        // Get all the cellModelList where ratingModelCost equals to UPDATED_RATING_MODEL_COST
        defaultCellModelShouldNotBeFound("ratingModelCost.in=" + UPDATED_RATING_MODEL_COST);
    }

    @Test
    @Transactional
    void getAllCellModelsByRatingModelCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where ratingModelCost is not null
        defaultCellModelShouldBeFound("ratingModelCost.specified=true");

        // Get all the cellModelList where ratingModelCost is null
        defaultCellModelShouldNotBeFound("ratingModelCost.specified=false");
    }

    @Test
    @Transactional
    void getAllCellModelsByRatingModelCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where ratingModelCost is greater than or equal to DEFAULT_RATING_MODEL_COST
        defaultCellModelShouldBeFound("ratingModelCost.greaterThanOrEqual=" + DEFAULT_RATING_MODEL_COST);

        // Get all the cellModelList where ratingModelCost is greater than or equal to UPDATED_RATING_MODEL_COST
        defaultCellModelShouldNotBeFound("ratingModelCost.greaterThanOrEqual=" + UPDATED_RATING_MODEL_COST);
    }

    @Test
    @Transactional
    void getAllCellModelsByRatingModelCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where ratingModelCost is less than or equal to DEFAULT_RATING_MODEL_COST
        defaultCellModelShouldBeFound("ratingModelCost.lessThanOrEqual=" + DEFAULT_RATING_MODEL_COST);

        // Get all the cellModelList where ratingModelCost is less than or equal to SMALLER_RATING_MODEL_COST
        defaultCellModelShouldNotBeFound("ratingModelCost.lessThanOrEqual=" + SMALLER_RATING_MODEL_COST);
    }

    @Test
    @Transactional
    void getAllCellModelsByRatingModelCostIsLessThanSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where ratingModelCost is less than DEFAULT_RATING_MODEL_COST
        defaultCellModelShouldNotBeFound("ratingModelCost.lessThan=" + DEFAULT_RATING_MODEL_COST);

        // Get all the cellModelList where ratingModelCost is less than UPDATED_RATING_MODEL_COST
        defaultCellModelShouldBeFound("ratingModelCost.lessThan=" + UPDATED_RATING_MODEL_COST);
    }

    @Test
    @Transactional
    void getAllCellModelsByRatingModelCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where ratingModelCost is greater than DEFAULT_RATING_MODEL_COST
        defaultCellModelShouldNotBeFound("ratingModelCost.greaterThan=" + DEFAULT_RATING_MODEL_COST);

        // Get all the cellModelList where ratingModelCost is greater than SMALLER_RATING_MODEL_COST
        defaultCellModelShouldBeFound("ratingModelCost.greaterThan=" + SMALLER_RATING_MODEL_COST);
    }

    @Test
    @Transactional
    void getAllCellModelsByRatingThroughPutCapacityIsEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where ratingThroughPutCapacity equals to DEFAULT_RATING_THROUGH_PUT_CAPACITY
        defaultCellModelShouldBeFound("ratingThroughPutCapacity.equals=" + DEFAULT_RATING_THROUGH_PUT_CAPACITY);

        // Get all the cellModelList where ratingThroughPutCapacity equals to UPDATED_RATING_THROUGH_PUT_CAPACITY
        defaultCellModelShouldNotBeFound("ratingThroughPutCapacity.equals=" + UPDATED_RATING_THROUGH_PUT_CAPACITY);
    }

    @Test
    @Transactional
    void getAllCellModelsByRatingThroughPutCapacityIsInShouldWork() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where ratingThroughPutCapacity in DEFAULT_RATING_THROUGH_PUT_CAPACITY or UPDATED_RATING_THROUGH_PUT_CAPACITY
        defaultCellModelShouldBeFound(
            "ratingThroughPutCapacity.in=" + DEFAULT_RATING_THROUGH_PUT_CAPACITY + "," + UPDATED_RATING_THROUGH_PUT_CAPACITY
        );

        // Get all the cellModelList where ratingThroughPutCapacity equals to UPDATED_RATING_THROUGH_PUT_CAPACITY
        defaultCellModelShouldNotBeFound("ratingThroughPutCapacity.in=" + UPDATED_RATING_THROUGH_PUT_CAPACITY);
    }

    @Test
    @Transactional
    void getAllCellModelsByRatingThroughPutCapacityIsNullOrNotNull() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where ratingThroughPutCapacity is not null
        defaultCellModelShouldBeFound("ratingThroughPutCapacity.specified=true");

        // Get all the cellModelList where ratingThroughPutCapacity is null
        defaultCellModelShouldNotBeFound("ratingThroughPutCapacity.specified=false");
    }

    @Test
    @Transactional
    void getAllCellModelsByRatingThroughPutCapacityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where ratingThroughPutCapacity is greater than or equal to DEFAULT_RATING_THROUGH_PUT_CAPACITY
        defaultCellModelShouldBeFound("ratingThroughPutCapacity.greaterThanOrEqual=" + DEFAULT_RATING_THROUGH_PUT_CAPACITY);

        // Get all the cellModelList where ratingThroughPutCapacity is greater than or equal to UPDATED_RATING_THROUGH_PUT_CAPACITY
        defaultCellModelShouldNotBeFound("ratingThroughPutCapacity.greaterThanOrEqual=" + UPDATED_RATING_THROUGH_PUT_CAPACITY);
    }

    @Test
    @Transactional
    void getAllCellModelsByRatingThroughPutCapacityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where ratingThroughPutCapacity is less than or equal to DEFAULT_RATING_THROUGH_PUT_CAPACITY
        defaultCellModelShouldBeFound("ratingThroughPutCapacity.lessThanOrEqual=" + DEFAULT_RATING_THROUGH_PUT_CAPACITY);

        // Get all the cellModelList where ratingThroughPutCapacity is less than or equal to SMALLER_RATING_THROUGH_PUT_CAPACITY
        defaultCellModelShouldNotBeFound("ratingThroughPutCapacity.lessThanOrEqual=" + SMALLER_RATING_THROUGH_PUT_CAPACITY);
    }

    @Test
    @Transactional
    void getAllCellModelsByRatingThroughPutCapacityIsLessThanSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where ratingThroughPutCapacity is less than DEFAULT_RATING_THROUGH_PUT_CAPACITY
        defaultCellModelShouldNotBeFound("ratingThroughPutCapacity.lessThan=" + DEFAULT_RATING_THROUGH_PUT_CAPACITY);

        // Get all the cellModelList where ratingThroughPutCapacity is less than UPDATED_RATING_THROUGH_PUT_CAPACITY
        defaultCellModelShouldBeFound("ratingThroughPutCapacity.lessThan=" + UPDATED_RATING_THROUGH_PUT_CAPACITY);
    }

    @Test
    @Transactional
    void getAllCellModelsByRatingThroughPutCapacityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where ratingThroughPutCapacity is greater than DEFAULT_RATING_THROUGH_PUT_CAPACITY
        defaultCellModelShouldNotBeFound("ratingThroughPutCapacity.greaterThan=" + DEFAULT_RATING_THROUGH_PUT_CAPACITY);

        // Get all the cellModelList where ratingThroughPutCapacity is greater than SMALLER_RATING_THROUGH_PUT_CAPACITY
        defaultCellModelShouldBeFound("ratingThroughPutCapacity.greaterThan=" + SMALLER_RATING_THROUGH_PUT_CAPACITY);
    }

    @Test
    @Transactional
    void getAllCellModelsByRatingTurnAroundTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where ratingTurnAroundTime equals to DEFAULT_RATING_TURN_AROUND_TIME
        defaultCellModelShouldBeFound("ratingTurnAroundTime.equals=" + DEFAULT_RATING_TURN_AROUND_TIME);

        // Get all the cellModelList where ratingTurnAroundTime equals to UPDATED_RATING_TURN_AROUND_TIME
        defaultCellModelShouldNotBeFound("ratingTurnAroundTime.equals=" + UPDATED_RATING_TURN_AROUND_TIME);
    }

    @Test
    @Transactional
    void getAllCellModelsByRatingTurnAroundTimeIsInShouldWork() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where ratingTurnAroundTime in DEFAULT_RATING_TURN_AROUND_TIME or UPDATED_RATING_TURN_AROUND_TIME
        defaultCellModelShouldBeFound("ratingTurnAroundTime.in=" + DEFAULT_RATING_TURN_AROUND_TIME + "," + UPDATED_RATING_TURN_AROUND_TIME);

        // Get all the cellModelList where ratingTurnAroundTime equals to UPDATED_RATING_TURN_AROUND_TIME
        defaultCellModelShouldNotBeFound("ratingTurnAroundTime.in=" + UPDATED_RATING_TURN_AROUND_TIME);
    }

    @Test
    @Transactional
    void getAllCellModelsByRatingTurnAroundTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where ratingTurnAroundTime is not null
        defaultCellModelShouldBeFound("ratingTurnAroundTime.specified=true");

        // Get all the cellModelList where ratingTurnAroundTime is null
        defaultCellModelShouldNotBeFound("ratingTurnAroundTime.specified=false");
    }

    @Test
    @Transactional
    void getAllCellModelsByRatingTurnAroundTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where ratingTurnAroundTime is greater than or equal to DEFAULT_RATING_TURN_AROUND_TIME
        defaultCellModelShouldBeFound("ratingTurnAroundTime.greaterThanOrEqual=" + DEFAULT_RATING_TURN_AROUND_TIME);

        // Get all the cellModelList where ratingTurnAroundTime is greater than or equal to UPDATED_RATING_TURN_AROUND_TIME
        defaultCellModelShouldNotBeFound("ratingTurnAroundTime.greaterThanOrEqual=" + UPDATED_RATING_TURN_AROUND_TIME);
    }

    @Test
    @Transactional
    void getAllCellModelsByRatingTurnAroundTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where ratingTurnAroundTime is less than or equal to DEFAULT_RATING_TURN_AROUND_TIME
        defaultCellModelShouldBeFound("ratingTurnAroundTime.lessThanOrEqual=" + DEFAULT_RATING_TURN_AROUND_TIME);

        // Get all the cellModelList where ratingTurnAroundTime is less than or equal to SMALLER_RATING_TURN_AROUND_TIME
        defaultCellModelShouldNotBeFound("ratingTurnAroundTime.lessThanOrEqual=" + SMALLER_RATING_TURN_AROUND_TIME);
    }

    @Test
    @Transactional
    void getAllCellModelsByRatingTurnAroundTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where ratingTurnAroundTime is less than DEFAULT_RATING_TURN_AROUND_TIME
        defaultCellModelShouldNotBeFound("ratingTurnAroundTime.lessThan=" + DEFAULT_RATING_TURN_AROUND_TIME);

        // Get all the cellModelList where ratingTurnAroundTime is less than UPDATED_RATING_TURN_AROUND_TIME
        defaultCellModelShouldBeFound("ratingTurnAroundTime.lessThan=" + UPDATED_RATING_TURN_AROUND_TIME);
    }

    @Test
    @Transactional
    void getAllCellModelsByRatingTurnAroundTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where ratingTurnAroundTime is greater than DEFAULT_RATING_TURN_AROUND_TIME
        defaultCellModelShouldNotBeFound("ratingTurnAroundTime.greaterThan=" + DEFAULT_RATING_TURN_AROUND_TIME);

        // Get all the cellModelList where ratingTurnAroundTime is greater than SMALLER_RATING_TURN_AROUND_TIME
        defaultCellModelShouldBeFound("ratingTurnAroundTime.greaterThan=" + SMALLER_RATING_TURN_AROUND_TIME);
    }

    @Test
    @Transactional
    void getAllCellModelsByImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where imageUrl equals to DEFAULT_IMAGE_URL
        defaultCellModelShouldBeFound("imageUrl.equals=" + DEFAULT_IMAGE_URL);

        // Get all the cellModelList where imageUrl equals to UPDATED_IMAGE_URL
        defaultCellModelShouldNotBeFound("imageUrl.equals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllCellModelsByImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where imageUrl in DEFAULT_IMAGE_URL or UPDATED_IMAGE_URL
        defaultCellModelShouldBeFound("imageUrl.in=" + DEFAULT_IMAGE_URL + "," + UPDATED_IMAGE_URL);

        // Get all the cellModelList where imageUrl equals to UPDATED_IMAGE_URL
        defaultCellModelShouldNotBeFound("imageUrl.in=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllCellModelsByImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where imageUrl is not null
        defaultCellModelShouldBeFound("imageUrl.specified=true");

        // Get all the cellModelList where imageUrl is null
        defaultCellModelShouldNotBeFound("imageUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllCellModelsByImageUrlContainsSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where imageUrl contains DEFAULT_IMAGE_URL
        defaultCellModelShouldBeFound("imageUrl.contains=" + DEFAULT_IMAGE_URL);

        // Get all the cellModelList where imageUrl contains UPDATED_IMAGE_URL
        defaultCellModelShouldNotBeFound("imageUrl.contains=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllCellModelsByImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where imageUrl does not contain DEFAULT_IMAGE_URL
        defaultCellModelShouldNotBeFound("imageUrl.doesNotContain=" + DEFAULT_IMAGE_URL);

        // Get all the cellModelList where imageUrl does not contain UPDATED_IMAGE_URL
        defaultCellModelShouldBeFound("imageUrl.doesNotContain=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllCellModelsByDepartmentIsEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where department equals to DEFAULT_DEPARTMENT
        defaultCellModelShouldBeFound("department.equals=" + DEFAULT_DEPARTMENT);

        // Get all the cellModelList where department equals to UPDATED_DEPARTMENT
        defaultCellModelShouldNotBeFound("department.equals=" + UPDATED_DEPARTMENT);
    }

    @Test
    @Transactional
    void getAllCellModelsByDepartmentIsInShouldWork() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where department in DEFAULT_DEPARTMENT or UPDATED_DEPARTMENT
        defaultCellModelShouldBeFound("department.in=" + DEFAULT_DEPARTMENT + "," + UPDATED_DEPARTMENT);

        // Get all the cellModelList where department equals to UPDATED_DEPARTMENT
        defaultCellModelShouldNotBeFound("department.in=" + UPDATED_DEPARTMENT);
    }

    @Test
    @Transactional
    void getAllCellModelsByDepartmentIsNullOrNotNull() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where department is not null
        defaultCellModelShouldBeFound("department.specified=true");

        // Get all the cellModelList where department is null
        defaultCellModelShouldNotBeFound("department.specified=false");
    }

    @Test
    @Transactional
    void getAllCellModelsByDepartmentContainsSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where department contains DEFAULT_DEPARTMENT
        defaultCellModelShouldBeFound("department.contains=" + DEFAULT_DEPARTMENT);

        // Get all the cellModelList where department contains UPDATED_DEPARTMENT
        defaultCellModelShouldNotBeFound("department.contains=" + UPDATED_DEPARTMENT);
    }

    @Test
    @Transactional
    void getAllCellModelsByDepartmentNotContainsSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where department does not contain DEFAULT_DEPARTMENT
        defaultCellModelShouldNotBeFound("department.doesNotContain=" + DEFAULT_DEPARTMENT);

        // Get all the cellModelList where department does not contain UPDATED_DEPARTMENT
        defaultCellModelShouldBeFound("department.doesNotContain=" + UPDATED_DEPARTMENT);
    }

    @Test
    @Transactional
    void getAllCellModelsByModelStateIsEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where modelState equals to DEFAULT_MODEL_STATE
        defaultCellModelShouldBeFound("modelState.equals=" + DEFAULT_MODEL_STATE);

        // Get all the cellModelList where modelState equals to UPDATED_MODEL_STATE
        defaultCellModelShouldNotBeFound("modelState.equals=" + UPDATED_MODEL_STATE);
    }

    @Test
    @Transactional
    void getAllCellModelsByModelStateIsInShouldWork() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where modelState in DEFAULT_MODEL_STATE or UPDATED_MODEL_STATE
        defaultCellModelShouldBeFound("modelState.in=" + DEFAULT_MODEL_STATE + "," + UPDATED_MODEL_STATE);

        // Get all the cellModelList where modelState equals to UPDATED_MODEL_STATE
        defaultCellModelShouldNotBeFound("modelState.in=" + UPDATED_MODEL_STATE);
    }

    @Test
    @Transactional
    void getAllCellModelsByModelStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where modelState is not null
        defaultCellModelShouldBeFound("modelState.specified=true");

        // Get all the cellModelList where modelState is null
        defaultCellModelShouldNotBeFound("modelState.specified=false");
    }

    @Test
    @Transactional
    void getAllCellModelsByModelStateContainsSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where modelState contains DEFAULT_MODEL_STATE
        defaultCellModelShouldBeFound("modelState.contains=" + DEFAULT_MODEL_STATE);

        // Get all the cellModelList where modelState contains UPDATED_MODEL_STATE
        defaultCellModelShouldNotBeFound("modelState.contains=" + UPDATED_MODEL_STATE);
    }

    @Test
    @Transactional
    void getAllCellModelsByModelStateNotContainsSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where modelState does not contain DEFAULT_MODEL_STATE
        defaultCellModelShouldNotBeFound("modelState.doesNotContain=" + DEFAULT_MODEL_STATE);

        // Get all the cellModelList where modelState does not contain UPDATED_MODEL_STATE
        defaultCellModelShouldBeFound("modelState.doesNotContain=" + UPDATED_MODEL_STATE);
    }

    @Test
    @Transactional
    void getAllCellModelsByApplicationADMEIsEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where applicationADME equals to DEFAULT_APPLICATION_ADME
        defaultCellModelShouldBeFound("applicationADME.equals=" + DEFAULT_APPLICATION_ADME);

        // Get all the cellModelList where applicationADME equals to UPDATED_APPLICATION_ADME
        defaultCellModelShouldNotBeFound("applicationADME.equals=" + UPDATED_APPLICATION_ADME);
    }

    @Test
    @Transactional
    void getAllCellModelsByApplicationADMEIsInShouldWork() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where applicationADME in DEFAULT_APPLICATION_ADME or UPDATED_APPLICATION_ADME
        defaultCellModelShouldBeFound("applicationADME.in=" + DEFAULT_APPLICATION_ADME + "," + UPDATED_APPLICATION_ADME);

        // Get all the cellModelList where applicationADME equals to UPDATED_APPLICATION_ADME
        defaultCellModelShouldNotBeFound("applicationADME.in=" + UPDATED_APPLICATION_ADME);
    }

    @Test
    @Transactional
    void getAllCellModelsByApplicationADMEIsNullOrNotNull() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where applicationADME is not null
        defaultCellModelShouldBeFound("applicationADME.specified=true");

        // Get all the cellModelList where applicationADME is null
        defaultCellModelShouldNotBeFound("applicationADME.specified=false");
    }

    @Test
    @Transactional
    void getAllCellModelsByApplicationEfficacyIsEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where applicationEfficacy equals to DEFAULT_APPLICATION_EFFICACY
        defaultCellModelShouldBeFound("applicationEfficacy.equals=" + DEFAULT_APPLICATION_EFFICACY);

        // Get all the cellModelList where applicationEfficacy equals to UPDATED_APPLICATION_EFFICACY
        defaultCellModelShouldNotBeFound("applicationEfficacy.equals=" + UPDATED_APPLICATION_EFFICACY);
    }

    @Test
    @Transactional
    void getAllCellModelsByApplicationEfficacyIsInShouldWork() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where applicationEfficacy in DEFAULT_APPLICATION_EFFICACY or UPDATED_APPLICATION_EFFICACY
        defaultCellModelShouldBeFound("applicationEfficacy.in=" + DEFAULT_APPLICATION_EFFICACY + "," + UPDATED_APPLICATION_EFFICACY);

        // Get all the cellModelList where applicationEfficacy equals to UPDATED_APPLICATION_EFFICACY
        defaultCellModelShouldNotBeFound("applicationEfficacy.in=" + UPDATED_APPLICATION_EFFICACY);
    }

    @Test
    @Transactional
    void getAllCellModelsByApplicationEfficacyIsNullOrNotNull() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where applicationEfficacy is not null
        defaultCellModelShouldBeFound("applicationEfficacy.specified=true");

        // Get all the cellModelList where applicationEfficacy is null
        defaultCellModelShouldNotBeFound("applicationEfficacy.specified=false");
    }

    @Test
    @Transactional
    void getAllCellModelsByApplicationNoneIsEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where applicationNone equals to DEFAULT_APPLICATION_NONE
        defaultCellModelShouldBeFound("applicationNone.equals=" + DEFAULT_APPLICATION_NONE);

        // Get all the cellModelList where applicationNone equals to UPDATED_APPLICATION_NONE
        defaultCellModelShouldNotBeFound("applicationNone.equals=" + UPDATED_APPLICATION_NONE);
    }

    @Test
    @Transactional
    void getAllCellModelsByApplicationNoneIsInShouldWork() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where applicationNone in DEFAULT_APPLICATION_NONE or UPDATED_APPLICATION_NONE
        defaultCellModelShouldBeFound("applicationNone.in=" + DEFAULT_APPLICATION_NONE + "," + UPDATED_APPLICATION_NONE);

        // Get all the cellModelList where applicationNone equals to UPDATED_APPLICATION_NONE
        defaultCellModelShouldNotBeFound("applicationNone.in=" + UPDATED_APPLICATION_NONE);
    }

    @Test
    @Transactional
    void getAllCellModelsByApplicationNoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where applicationNone is not null
        defaultCellModelShouldBeFound("applicationNone.specified=true");

        // Get all the cellModelList where applicationNone is null
        defaultCellModelShouldNotBeFound("applicationNone.specified=false");
    }

    @Test
    @Transactional
    void getAllCellModelsByApplicationSafetyIsEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where applicationSafety equals to DEFAULT_APPLICATION_SAFETY
        defaultCellModelShouldBeFound("applicationSafety.equals=" + DEFAULT_APPLICATION_SAFETY);

        // Get all the cellModelList where applicationSafety equals to UPDATED_APPLICATION_SAFETY
        defaultCellModelShouldNotBeFound("applicationSafety.equals=" + UPDATED_APPLICATION_SAFETY);
    }

    @Test
    @Transactional
    void getAllCellModelsByApplicationSafetyIsInShouldWork() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where applicationSafety in DEFAULT_APPLICATION_SAFETY or UPDATED_APPLICATION_SAFETY
        defaultCellModelShouldBeFound("applicationSafety.in=" + DEFAULT_APPLICATION_SAFETY + "," + UPDATED_APPLICATION_SAFETY);

        // Get all the cellModelList where applicationSafety equals to UPDATED_APPLICATION_SAFETY
        defaultCellModelShouldNotBeFound("applicationSafety.in=" + UPDATED_APPLICATION_SAFETY);
    }

    @Test
    @Transactional
    void getAllCellModelsByApplicationSafetyIsNullOrNotNull() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where applicationSafety is not null
        defaultCellModelShouldBeFound("applicationSafety.specified=true");

        // Get all the cellModelList where applicationSafety is null
        defaultCellModelShouldNotBeFound("applicationSafety.specified=false");
    }

    @Test
    @Transactional
    void getAllCellModelsByAnimalAssayReplacedIsEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where animalAssayReplaced equals to DEFAULT_ANIMAL_ASSAY_REPLACED
        defaultCellModelShouldBeFound("animalAssayReplaced.equals=" + DEFAULT_ANIMAL_ASSAY_REPLACED);

        // Get all the cellModelList where animalAssayReplaced equals to UPDATED_ANIMAL_ASSAY_REPLACED
        defaultCellModelShouldNotBeFound("animalAssayReplaced.equals=" + UPDATED_ANIMAL_ASSAY_REPLACED);
    }

    @Test
    @Transactional
    void getAllCellModelsByAnimalAssayReplacedIsInShouldWork() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where animalAssayReplaced in DEFAULT_ANIMAL_ASSAY_REPLACED or UPDATED_ANIMAL_ASSAY_REPLACED
        defaultCellModelShouldBeFound("animalAssayReplaced.in=" + DEFAULT_ANIMAL_ASSAY_REPLACED + "," + UPDATED_ANIMAL_ASSAY_REPLACED);

        // Get all the cellModelList where animalAssayReplaced equals to UPDATED_ANIMAL_ASSAY_REPLACED
        defaultCellModelShouldNotBeFound("animalAssayReplaced.in=" + UPDATED_ANIMAL_ASSAY_REPLACED);
    }

    @Test
    @Transactional
    void getAllCellModelsByAnimalAssayReplacedIsNullOrNotNull() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where animalAssayReplaced is not null
        defaultCellModelShouldBeFound("animalAssayReplaced.specified=true");

        // Get all the cellModelList where animalAssayReplaced is null
        defaultCellModelShouldNotBeFound("animalAssayReplaced.specified=false");
    }

    @Test
    @Transactional
    void getAllCellModelsByAnimalAssayReplacedContainsSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where animalAssayReplaced contains DEFAULT_ANIMAL_ASSAY_REPLACED
        defaultCellModelShouldBeFound("animalAssayReplaced.contains=" + DEFAULT_ANIMAL_ASSAY_REPLACED);

        // Get all the cellModelList where animalAssayReplaced contains UPDATED_ANIMAL_ASSAY_REPLACED
        defaultCellModelShouldNotBeFound("animalAssayReplaced.contains=" + UPDATED_ANIMAL_ASSAY_REPLACED);
    }

    @Test
    @Transactional
    void getAllCellModelsByAnimalAssayReplacedNotContainsSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where animalAssayReplaced does not contain DEFAULT_ANIMAL_ASSAY_REPLACED
        defaultCellModelShouldNotBeFound("animalAssayReplaced.doesNotContain=" + DEFAULT_ANIMAL_ASSAY_REPLACED);

        // Get all the cellModelList where animalAssayReplaced does not contain UPDATED_ANIMAL_ASSAY_REPLACED
        defaultCellModelShouldBeFound("animalAssayReplaced.doesNotContain=" + UPDATED_ANIMAL_ASSAY_REPLACED);
    }

    @Test
    @Transactional
    void getAllCellModelsByCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where comment equals to DEFAULT_COMMENT
        defaultCellModelShouldBeFound("comment.equals=" + DEFAULT_COMMENT);

        // Get all the cellModelList where comment equals to UPDATED_COMMENT
        defaultCellModelShouldNotBeFound("comment.equals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllCellModelsByCommentIsInShouldWork() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where comment in DEFAULT_COMMENT or UPDATED_COMMENT
        defaultCellModelShouldBeFound("comment.in=" + DEFAULT_COMMENT + "," + UPDATED_COMMENT);

        // Get all the cellModelList where comment equals to UPDATED_COMMENT
        defaultCellModelShouldNotBeFound("comment.in=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllCellModelsByCommentIsNullOrNotNull() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where comment is not null
        defaultCellModelShouldBeFound("comment.specified=true");

        // Get all the cellModelList where comment is null
        defaultCellModelShouldNotBeFound("comment.specified=false");
    }

    @Test
    @Transactional
    void getAllCellModelsByCommentContainsSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where comment contains DEFAULT_COMMENT
        defaultCellModelShouldBeFound("comment.contains=" + DEFAULT_COMMENT);

        // Get all the cellModelList where comment contains UPDATED_COMMENT
        defaultCellModelShouldNotBeFound("comment.contains=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllCellModelsByCommentNotContainsSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where comment does not contain DEFAULT_COMMENT
        defaultCellModelShouldNotBeFound("comment.doesNotContain=" + DEFAULT_COMMENT);

        // Get all the cellModelList where comment does not contain UPDATED_COMMENT
        defaultCellModelShouldBeFound("comment.doesNotContain=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllCellModelsByExpertsIsEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where experts equals to DEFAULT_EXPERTS
        defaultCellModelShouldBeFound("experts.equals=" + DEFAULT_EXPERTS);

        // Get all the cellModelList where experts equals to UPDATED_EXPERTS
        defaultCellModelShouldNotBeFound("experts.equals=" + UPDATED_EXPERTS);
    }

    @Test
    @Transactional
    void getAllCellModelsByExpertsIsInShouldWork() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where experts in DEFAULT_EXPERTS or UPDATED_EXPERTS
        defaultCellModelShouldBeFound("experts.in=" + DEFAULT_EXPERTS + "," + UPDATED_EXPERTS);

        // Get all the cellModelList where experts equals to UPDATED_EXPERTS
        defaultCellModelShouldNotBeFound("experts.in=" + UPDATED_EXPERTS);
    }

    @Test
    @Transactional
    void getAllCellModelsByExpertsIsNullOrNotNull() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where experts is not null
        defaultCellModelShouldBeFound("experts.specified=true");

        // Get all the cellModelList where experts is null
        defaultCellModelShouldNotBeFound("experts.specified=false");
    }

    @Test
    @Transactional
    void getAllCellModelsByExpertsContainsSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where experts contains DEFAULT_EXPERTS
        defaultCellModelShouldBeFound("experts.contains=" + DEFAULT_EXPERTS);

        // Get all the cellModelList where experts contains UPDATED_EXPERTS
        defaultCellModelShouldNotBeFound("experts.contains=" + UPDATED_EXPERTS);
    }

    @Test
    @Transactional
    void getAllCellModelsByExpertsNotContainsSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where experts does not contain DEFAULT_EXPERTS
        defaultCellModelShouldNotBeFound("experts.doesNotContain=" + DEFAULT_EXPERTS);

        // Get all the cellModelList where experts does not contain UPDATED_EXPERTS
        defaultCellModelShouldBeFound("experts.doesNotContain=" + UPDATED_EXPERTS);
    }

    @Test
    @Transactional
    void getAllCellModelsByLinkIsEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where link equals to DEFAULT_LINK
        defaultCellModelShouldBeFound("link.equals=" + DEFAULT_LINK);

        // Get all the cellModelList where link equals to UPDATED_LINK
        defaultCellModelShouldNotBeFound("link.equals=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    void getAllCellModelsByLinkIsInShouldWork() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where link in DEFAULT_LINK or UPDATED_LINK
        defaultCellModelShouldBeFound("link.in=" + DEFAULT_LINK + "," + UPDATED_LINK);

        // Get all the cellModelList where link equals to UPDATED_LINK
        defaultCellModelShouldNotBeFound("link.in=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    void getAllCellModelsByLinkIsNullOrNotNull() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where link is not null
        defaultCellModelShouldBeFound("link.specified=true");

        // Get all the cellModelList where link is null
        defaultCellModelShouldNotBeFound("link.specified=false");
    }

    @Test
    @Transactional
    void getAllCellModelsByLinkContainsSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where link contains DEFAULT_LINK
        defaultCellModelShouldBeFound("link.contains=" + DEFAULT_LINK);

        // Get all the cellModelList where link contains UPDATED_LINK
        defaultCellModelShouldNotBeFound("link.contains=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    void getAllCellModelsByLinkNotContainsSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where link does not contain DEFAULT_LINK
        defaultCellModelShouldNotBeFound("link.doesNotContain=" + DEFAULT_LINK);

        // Get all the cellModelList where link does not contain UPDATED_LINK
        defaultCellModelShouldBeFound("link.doesNotContain=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    void getAllCellModelsByLimitationPercievedIsEqualToSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where limitationPercieved equals to DEFAULT_LIMITATION_PERCIEVED
        defaultCellModelShouldBeFound("limitationPercieved.equals=" + DEFAULT_LIMITATION_PERCIEVED);

        // Get all the cellModelList where limitationPercieved equals to UPDATED_LIMITATION_PERCIEVED
        defaultCellModelShouldNotBeFound("limitationPercieved.equals=" + UPDATED_LIMITATION_PERCIEVED);
    }

    @Test
    @Transactional
    void getAllCellModelsByLimitationPercievedIsInShouldWork() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where limitationPercieved in DEFAULT_LIMITATION_PERCIEVED or UPDATED_LIMITATION_PERCIEVED
        defaultCellModelShouldBeFound("limitationPercieved.in=" + DEFAULT_LIMITATION_PERCIEVED + "," + UPDATED_LIMITATION_PERCIEVED);

        // Get all the cellModelList where limitationPercieved equals to UPDATED_LIMITATION_PERCIEVED
        defaultCellModelShouldNotBeFound("limitationPercieved.in=" + UPDATED_LIMITATION_PERCIEVED);
    }

    @Test
    @Transactional
    void getAllCellModelsByLimitationPercievedIsNullOrNotNull() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where limitationPercieved is not null
        defaultCellModelShouldBeFound("limitationPercieved.specified=true");

        // Get all the cellModelList where limitationPercieved is null
        defaultCellModelShouldNotBeFound("limitationPercieved.specified=false");
    }

    @Test
    @Transactional
    void getAllCellModelsByLimitationPercievedContainsSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where limitationPercieved contains DEFAULT_LIMITATION_PERCIEVED
        defaultCellModelShouldBeFound("limitationPercieved.contains=" + DEFAULT_LIMITATION_PERCIEVED);

        // Get all the cellModelList where limitationPercieved contains UPDATED_LIMITATION_PERCIEVED
        defaultCellModelShouldNotBeFound("limitationPercieved.contains=" + UPDATED_LIMITATION_PERCIEVED);
    }

    @Test
    @Transactional
    void getAllCellModelsByLimitationPercievedNotContainsSomething() throws Exception {
        // Initialize the database
        cellModelRepository.saveAndFlush(cellModel);

        // Get all the cellModelList where limitationPercieved does not contain DEFAULT_LIMITATION_PERCIEVED
        defaultCellModelShouldNotBeFound("limitationPercieved.doesNotContain=" + DEFAULT_LIMITATION_PERCIEVED);

        // Get all the cellModelList where limitationPercieved does not contain UPDATED_LIMITATION_PERCIEVED
        defaultCellModelShouldBeFound("limitationPercieved.doesNotContain=" + UPDATED_LIMITATION_PERCIEVED);
    }

    @Test
    @Transactional
    void getAllCellModelsByUserIsEqualToSomething() throws Exception {
        User user;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            cellModelRepository.saveAndFlush(cellModel);
            user = UserResourceIT.createEntity(em);
        } else {
            user = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(user);
        em.flush();
        cellModel.setUser(user);
        cellModelRepository.saveAndFlush(cellModel);
        Long userId = user.getId();

        // Get all the cellModelList where user equals to userId
        defaultCellModelShouldBeFound("userId.equals=" + userId);

        // Get all the cellModelList where user equals to (userId + 1)
        defaultCellModelShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    @Test
    @Transactional
    void getAllCellModelsByProjectSupportedIsEqualToSomething() throws Exception {
        ProjectSupported projectSupported;
        if (TestUtil.findAll(em, ProjectSupported.class).isEmpty()) {
            cellModelRepository.saveAndFlush(cellModel);
            projectSupported = ProjectSupportedResourceIT.createEntity(em);
        } else {
            projectSupported = TestUtil.findAll(em, ProjectSupported.class).get(0);
        }
        em.persist(projectSupported);
        em.flush();
        cellModel.addProjectSupported(projectSupported);
        cellModelRepository.saveAndFlush(cellModel);
        Long projectSupportedId = projectSupported.getId();

        // Get all the cellModelList where projectSupported equals to projectSupportedId
        defaultCellModelShouldBeFound("projectSupportedId.equals=" + projectSupportedId);

        // Get all the cellModelList where projectSupported equals to (projectSupportedId + 1)
        defaultCellModelShouldNotBeFound("projectSupportedId.equals=" + (projectSupportedId + 1));
    }

    @Test
    @Transactional
    void getAllCellModelsByCellTypeIsEqualToSomething() throws Exception {
        CellType cellType;
        if (TestUtil.findAll(em, CellType.class).isEmpty()) {
            cellModelRepository.saveAndFlush(cellModel);
            cellType = CellTypeResourceIT.createEntity(em);
        } else {
            cellType = TestUtil.findAll(em, CellType.class).get(0);
        }
        em.persist(cellType);
        em.flush();
        cellModel.addCellType(cellType);
        cellModelRepository.saveAndFlush(cellModel);
        Long cellTypeId = cellType.getId();

        // Get all the cellModelList where cellType equals to cellTypeId
        defaultCellModelShouldBeFound("cellTypeId.equals=" + cellTypeId);

        // Get all the cellModelList where cellType equals to (cellTypeId + 1)
        defaultCellModelShouldNotBeFound("cellTypeId.equals=" + (cellTypeId + 1));
    }

    @Test
    @Transactional
    void getAllCellModelsByCommentIsEqualToSomething() throws Exception {
        Comment comment;
        if (TestUtil.findAll(em, Comment.class).isEmpty()) {
            cellModelRepository.saveAndFlush(cellModel);
            comment = CommentResourceIT.createEntity(em);
        } else {
            comment = TestUtil.findAll(em, Comment.class).get(0);
        }
        em.persist(comment);
        em.flush();
        cellModel.addComment(comment);
        cellModelRepository.saveAndFlush(cellModel);
        Long commentId = comment.getId();

        // Get all the cellModelList where comment equals to commentId
        defaultCellModelShouldBeFound("commentId.equals=" + commentId);

        // Get all the cellModelList where comment equals to (commentId + 1)
        defaultCellModelShouldNotBeFound("commentId.equals=" + (commentId + 1));
    }

    @Test
    @Transactional
    void getAllCellModelsByCharacterizationDataIsEqualToSomething() throws Exception {
        CharacterizationData characterizationData;
        if (TestUtil.findAll(em, CharacterizationData.class).isEmpty()) {
            cellModelRepository.saveAndFlush(cellModel);
            characterizationData = CharacterizationDataResourceIT.createEntity(em);
        } else {
            characterizationData = TestUtil.findAll(em, CharacterizationData.class).get(0);
        }
        em.persist(characterizationData);
        em.flush();
        cellModel.addCharacterizationData(characterizationData);
        cellModelRepository.saveAndFlush(cellModel);
        Long characterizationDataId = characterizationData.getId();

        // Get all the cellModelList where characterizationData equals to characterizationDataId
        defaultCellModelShouldBeFound("characterizationDataId.equals=" + characterizationDataId);

        // Get all the cellModelList where characterizationData equals to (characterizationDataId + 1)
        defaultCellModelShouldNotBeFound("characterizationDataId.equals=" + (characterizationDataId + 1));
    }

    @Test
    @Transactional
    void getAllCellModelsByPublicationIsEqualToSomething() throws Exception {
        Publication publication;
        if (TestUtil.findAll(em, Publication.class).isEmpty()) {
            cellModelRepository.saveAndFlush(cellModel);
            publication = PublicationResourceIT.createEntity(em);
        } else {
            publication = TestUtil.findAll(em, Publication.class).get(0);
        }
        em.persist(publication);
        em.flush();
        cellModel.addPublication(publication);
        cellModelRepository.saveAndFlush(cellModel);
        Long publicationId = publication.getId();

        // Get all the cellModelList where publication equals to publicationId
        defaultCellModelShouldBeFound("publicationId.equals=" + publicationId);

        // Get all the cellModelList where publication equals to (publicationId + 1)
        defaultCellModelShouldNotBeFound("publicationId.equals=" + (publicationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCellModelShouldBeFound(String filter) throws Exception {
        restCellModelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cellModel.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].dateAdded").value(hasItem(sameInstant(DEFAULT_DATE_ADDED))))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(sameInstant(DEFAULT_DATE_UPDATED))))
            .andExpect(jsonPath("$.[*].species").value(hasItem(DEFAULT_SPECIES)))
            .andExpect(jsonPath("$.[*].targetOrgan").value(hasItem(DEFAULT_TARGET_ORGAN)))
            .andExpect(jsonPath("$.[*].targetTissue").value(hasItem(DEFAULT_TARGET_TISSUE)))
            .andExpect(jsonPath("$.[*].vesselInfo").value(hasItem(DEFAULT_VESSEL_INFO)))
            .andExpect(jsonPath("$.[*].matrix").value(hasItem(DEFAULT_MATRIX.booleanValue())))
            .andExpect(jsonPath("$.[*].protocol").value(hasItem(DEFAULT_PROTOCOL)))
            .andExpect(jsonPath("$.[*].manufacturer").value(hasItem(DEFAULT_MANUFACTURER)))
            .andExpect(jsonPath("$.[*].matrixType").value(hasItem(DEFAULT_MATRIX_TYPE)))
            .andExpect(jsonPath("$.[*].vesselType").value(hasItem(DEFAULT_VESSEL_TYPE)))
            .andExpect(jsonPath("$.[*].inhouse").value(hasItem(DEFAULT_INHOUSE.booleanValue())))
            .andExpect(jsonPath("$.[*].ratingCharacterization").value(hasItem(DEFAULT_RATING_CHARACTERIZATION)))
            .andExpect(jsonPath("$.[*].ratingComplexity").value(hasItem(DEFAULT_RATING_COMPLEXITY)))
            .andExpect(jsonPath("$.[*].ratingModelCost").value(hasItem(DEFAULT_RATING_MODEL_COST)))
            .andExpect(jsonPath("$.[*].ratingThroughPutCapacity").value(hasItem(DEFAULT_RATING_THROUGH_PUT_CAPACITY)))
            .andExpect(jsonPath("$.[*].ratingTurnAroundTime").value(hasItem(DEFAULT_RATING_TURN_AROUND_TIME)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT)))
            .andExpect(jsonPath("$.[*].modelState").value(hasItem(DEFAULT_MODEL_STATE)))
            .andExpect(jsonPath("$.[*].applicationADME").value(hasItem(DEFAULT_APPLICATION_ADME.booleanValue())))
            .andExpect(jsonPath("$.[*].applicationEfficacy").value(hasItem(DEFAULT_APPLICATION_EFFICACY.booleanValue())))
            .andExpect(jsonPath("$.[*].applicationNone").value(hasItem(DEFAULT_APPLICATION_NONE.booleanValue())))
            .andExpect(jsonPath("$.[*].applicationSafety").value(hasItem(DEFAULT_APPLICATION_SAFETY.booleanValue())))
            .andExpect(jsonPath("$.[*].animalAssayReplaced").value(hasItem(DEFAULT_ANIMAL_ASSAY_REPLACED)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].experts").value(hasItem(DEFAULT_EXPERTS)))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)))
            .andExpect(jsonPath("$.[*].limitationPercieved").value(hasItem(DEFAULT_LIMITATION_PERCIEVED)));

        // Check, that the count call also returns 1
        restCellModelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCellModelShouldNotBeFound(String filter) throws Exception {
        restCellModelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCellModelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCellModel() throws Exception {
        // Get the cellModel
        restCellModelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCellModel() throws Exception {
        // Initialize the database
        cellModel.setId(UUID.randomUUID().toString());
        cellModelRepository.saveAndFlush(cellModel);

        int databaseSizeBeforeUpdate = cellModelRepository.findAll().size();

        // Update the cellModel
        CellModel updatedCellModel = cellModelRepository.findById(cellModel.getId()).get();
        // Disconnect from session so that the updates on updatedCellModel are not directly saved in db
        em.detach(updatedCellModel);
        updatedCellModel
            .name(UPDATED_NAME)
            .dateAdded(UPDATED_DATE_ADDED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .species(UPDATED_SPECIES)
            .targetOrgan(UPDATED_TARGET_ORGAN)
            .targetTissue(UPDATED_TARGET_TISSUE)
            .vesselInfo(UPDATED_VESSEL_INFO)
            .matrix(UPDATED_MATRIX)
            .protocol(UPDATED_PROTOCOL)
            .manufacturer(UPDATED_MANUFACTURER)
            .matrixType(UPDATED_MATRIX_TYPE)
            .vesselType(UPDATED_VESSEL_TYPE)
            .inhouse(UPDATED_INHOUSE)
            .ratingCharacterization(UPDATED_RATING_CHARACTERIZATION)
            .ratingComplexity(UPDATED_RATING_COMPLEXITY)
            .ratingModelCost(UPDATED_RATING_MODEL_COST)
            .ratingThroughPutCapacity(UPDATED_RATING_THROUGH_PUT_CAPACITY)
            .ratingTurnAroundTime(UPDATED_RATING_TURN_AROUND_TIME)
            .imageUrl(UPDATED_IMAGE_URL)
            .department(UPDATED_DEPARTMENT)
            .modelState(UPDATED_MODEL_STATE)
            .applicationADME(UPDATED_APPLICATION_ADME)
            .applicationEfficacy(UPDATED_APPLICATION_EFFICACY)
            .applicationNone(UPDATED_APPLICATION_NONE)
            .applicationSafety(UPDATED_APPLICATION_SAFETY)
            .animalAssayReplaced(UPDATED_ANIMAL_ASSAY_REPLACED)
            .comment(UPDATED_COMMENT)
            .experts(UPDATED_EXPERTS)
            .link(UPDATED_LINK)
            .limitationPercieved(UPDATED_LIMITATION_PERCIEVED);
        CellModelDTO cellModelDTO = cellModelMapper.toDto(updatedCellModel);

        restCellModelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cellModelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cellModelDTO))
            )
            .andExpect(status().isOk());

        // Validate the CellModel in the database
        List<CellModel> cellModelList = cellModelRepository.findAll();
        assertThat(cellModelList).hasSize(databaseSizeBeforeUpdate);
        CellModel testCellModel = cellModelList.get(cellModelList.size() - 1);
        assertThat(testCellModel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCellModel.getDateAdded()).isEqualTo(UPDATED_DATE_ADDED);
        assertThat(testCellModel.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testCellModel.getSpecies()).isEqualTo(UPDATED_SPECIES);
        assertThat(testCellModel.getTargetOrgan()).isEqualTo(UPDATED_TARGET_ORGAN);
        assertThat(testCellModel.getTargetTissue()).isEqualTo(UPDATED_TARGET_TISSUE);
        assertThat(testCellModel.getVesselInfo()).isEqualTo(UPDATED_VESSEL_INFO);
        assertThat(testCellModel.getMatrix()).isEqualTo(UPDATED_MATRIX);
        assertThat(testCellModel.getProtocol()).isEqualTo(UPDATED_PROTOCOL);
        assertThat(testCellModel.getManufacturer()).isEqualTo(UPDATED_MANUFACTURER);
        assertThat(testCellModel.getMatrixType()).isEqualTo(UPDATED_MATRIX_TYPE);
        assertThat(testCellModel.getVesselType()).isEqualTo(UPDATED_VESSEL_TYPE);
        assertThat(testCellModel.getInhouse()).isEqualTo(UPDATED_INHOUSE);
        assertThat(testCellModel.getRatingCharacterization()).isEqualTo(UPDATED_RATING_CHARACTERIZATION);
        assertThat(testCellModel.getRatingComplexity()).isEqualTo(UPDATED_RATING_COMPLEXITY);
        assertThat(testCellModel.getRatingModelCost()).isEqualTo(UPDATED_RATING_MODEL_COST);
        assertThat(testCellModel.getRatingThroughPutCapacity()).isEqualTo(UPDATED_RATING_THROUGH_PUT_CAPACITY);
        assertThat(testCellModel.getRatingTurnAroundTime()).isEqualTo(UPDATED_RATING_TURN_AROUND_TIME);
        assertThat(testCellModel.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testCellModel.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testCellModel.getModelState()).isEqualTo(UPDATED_MODEL_STATE);
        assertThat(testCellModel.getApplicationADME()).isEqualTo(UPDATED_APPLICATION_ADME);
        assertThat(testCellModel.getApplicationEfficacy()).isEqualTo(UPDATED_APPLICATION_EFFICACY);
        assertThat(testCellModel.getApplicationNone()).isEqualTo(UPDATED_APPLICATION_NONE);
        assertThat(testCellModel.getApplicationSafety()).isEqualTo(UPDATED_APPLICATION_SAFETY);
        assertThat(testCellModel.getAnimalAssayReplaced()).isEqualTo(UPDATED_ANIMAL_ASSAY_REPLACED);
        assertThat(testCellModel.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testCellModel.getExperts()).isEqualTo(UPDATED_EXPERTS);
        assertThat(testCellModel.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testCellModel.getLimitationPercieved()).isEqualTo(UPDATED_LIMITATION_PERCIEVED);
    }

    @Test
    @Transactional
    void putNonExistingCellModel() throws Exception {
        int databaseSizeBeforeUpdate = cellModelRepository.findAll().size();
        cellModel.setId(UUID.randomUUID().toString());

        // Create the CellModel
        CellModelDTO cellModelDTO = cellModelMapper.toDto(cellModel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCellModelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cellModelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cellModelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CellModel in the database
        List<CellModel> cellModelList = cellModelRepository.findAll();
        assertThat(cellModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCellModel() throws Exception {
        int databaseSizeBeforeUpdate = cellModelRepository.findAll().size();
        cellModel.setId(UUID.randomUUID().toString());

        // Create the CellModel
        CellModelDTO cellModelDTO = cellModelMapper.toDto(cellModel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCellModelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cellModelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CellModel in the database
        List<CellModel> cellModelList = cellModelRepository.findAll();
        assertThat(cellModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCellModel() throws Exception {
        int databaseSizeBeforeUpdate = cellModelRepository.findAll().size();
        cellModel.setId(UUID.randomUUID().toString());

        // Create the CellModel
        CellModelDTO cellModelDTO = cellModelMapper.toDto(cellModel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCellModelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cellModelDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CellModel in the database
        List<CellModel> cellModelList = cellModelRepository.findAll();
        assertThat(cellModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCellModelWithPatch() throws Exception {
        // Initialize the database
        cellModel.setId(UUID.randomUUID().toString());
        cellModelRepository.saveAndFlush(cellModel);

        int databaseSizeBeforeUpdate = cellModelRepository.findAll().size();

        // Update the cellModel using partial update
        CellModel partialUpdatedCellModel = new CellModel();
        partialUpdatedCellModel.setId(cellModel.getId());

        partialUpdatedCellModel
            .name(UPDATED_NAME)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .protocol(UPDATED_PROTOCOL)
            .manufacturer(UPDATED_MANUFACTURER)
            .matrixType(UPDATED_MATRIX_TYPE)
            .ratingModelCost(UPDATED_RATING_MODEL_COST)
            .modelState(UPDATED_MODEL_STATE)
            .applicationADME(UPDATED_APPLICATION_ADME)
            .applicationEfficacy(UPDATED_APPLICATION_EFFICACY)
            .applicationNone(UPDATED_APPLICATION_NONE)
            .applicationSafety(UPDATED_APPLICATION_SAFETY)
            .comment(UPDATED_COMMENT)
            .link(UPDATED_LINK)
            .limitationPercieved(UPDATED_LIMITATION_PERCIEVED);

        restCellModelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCellModel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCellModel))
            )
            .andExpect(status().isOk());

        // Validate the CellModel in the database
        List<CellModel> cellModelList = cellModelRepository.findAll();
        assertThat(cellModelList).hasSize(databaseSizeBeforeUpdate);
        CellModel testCellModel = cellModelList.get(cellModelList.size() - 1);
        assertThat(testCellModel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCellModel.getDateAdded()).isEqualTo(DEFAULT_DATE_ADDED);
        assertThat(testCellModel.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testCellModel.getSpecies()).isEqualTo(DEFAULT_SPECIES);
        assertThat(testCellModel.getTargetOrgan()).isEqualTo(DEFAULT_TARGET_ORGAN);
        assertThat(testCellModel.getTargetTissue()).isEqualTo(DEFAULT_TARGET_TISSUE);
        assertThat(testCellModel.getVesselInfo()).isEqualTo(DEFAULT_VESSEL_INFO);
        assertThat(testCellModel.getMatrix()).isEqualTo(DEFAULT_MATRIX);
        assertThat(testCellModel.getProtocol()).isEqualTo(UPDATED_PROTOCOL);
        assertThat(testCellModel.getManufacturer()).isEqualTo(UPDATED_MANUFACTURER);
        assertThat(testCellModel.getMatrixType()).isEqualTo(UPDATED_MATRIX_TYPE);
        assertThat(testCellModel.getVesselType()).isEqualTo(DEFAULT_VESSEL_TYPE);
        assertThat(testCellModel.getInhouse()).isEqualTo(DEFAULT_INHOUSE);
        assertThat(testCellModel.getRatingCharacterization()).isEqualTo(DEFAULT_RATING_CHARACTERIZATION);
        assertThat(testCellModel.getRatingComplexity()).isEqualTo(DEFAULT_RATING_COMPLEXITY);
        assertThat(testCellModel.getRatingModelCost()).isEqualTo(UPDATED_RATING_MODEL_COST);
        assertThat(testCellModel.getRatingThroughPutCapacity()).isEqualTo(DEFAULT_RATING_THROUGH_PUT_CAPACITY);
        assertThat(testCellModel.getRatingTurnAroundTime()).isEqualTo(DEFAULT_RATING_TURN_AROUND_TIME);
        assertThat(testCellModel.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testCellModel.getDepartment()).isEqualTo(DEFAULT_DEPARTMENT);
        assertThat(testCellModel.getModelState()).isEqualTo(UPDATED_MODEL_STATE);
        assertThat(testCellModel.getApplicationADME()).isEqualTo(UPDATED_APPLICATION_ADME);
        assertThat(testCellModel.getApplicationEfficacy()).isEqualTo(UPDATED_APPLICATION_EFFICACY);
        assertThat(testCellModel.getApplicationNone()).isEqualTo(UPDATED_APPLICATION_NONE);
        assertThat(testCellModel.getApplicationSafety()).isEqualTo(UPDATED_APPLICATION_SAFETY);
        assertThat(testCellModel.getAnimalAssayReplaced()).isEqualTo(DEFAULT_ANIMAL_ASSAY_REPLACED);
        assertThat(testCellModel.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testCellModel.getExperts()).isEqualTo(DEFAULT_EXPERTS);
        assertThat(testCellModel.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testCellModel.getLimitationPercieved()).isEqualTo(UPDATED_LIMITATION_PERCIEVED);
    }

    @Test
    @Transactional
    void fullUpdateCellModelWithPatch() throws Exception {
        // Initialize the database
        cellModel.setId(UUID.randomUUID().toString());
        cellModelRepository.saveAndFlush(cellModel);

        int databaseSizeBeforeUpdate = cellModelRepository.findAll().size();

        // Update the cellModel using partial update
        CellModel partialUpdatedCellModel = new CellModel();
        partialUpdatedCellModel.setId(cellModel.getId());

        partialUpdatedCellModel
            .name(UPDATED_NAME)
            .dateAdded(UPDATED_DATE_ADDED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .species(UPDATED_SPECIES)
            .targetOrgan(UPDATED_TARGET_ORGAN)
            .targetTissue(UPDATED_TARGET_TISSUE)
            .vesselInfo(UPDATED_VESSEL_INFO)
            .matrix(UPDATED_MATRIX)
            .protocol(UPDATED_PROTOCOL)
            .manufacturer(UPDATED_MANUFACTURER)
            .matrixType(UPDATED_MATRIX_TYPE)
            .vesselType(UPDATED_VESSEL_TYPE)
            .inhouse(UPDATED_INHOUSE)
            .ratingCharacterization(UPDATED_RATING_CHARACTERIZATION)
            .ratingComplexity(UPDATED_RATING_COMPLEXITY)
            .ratingModelCost(UPDATED_RATING_MODEL_COST)
            .ratingThroughPutCapacity(UPDATED_RATING_THROUGH_PUT_CAPACITY)
            .ratingTurnAroundTime(UPDATED_RATING_TURN_AROUND_TIME)
            .imageUrl(UPDATED_IMAGE_URL)
            .department(UPDATED_DEPARTMENT)
            .modelState(UPDATED_MODEL_STATE)
            .applicationADME(UPDATED_APPLICATION_ADME)
            .applicationEfficacy(UPDATED_APPLICATION_EFFICACY)
            .applicationNone(UPDATED_APPLICATION_NONE)
            .applicationSafety(UPDATED_APPLICATION_SAFETY)
            .animalAssayReplaced(UPDATED_ANIMAL_ASSAY_REPLACED)
            .comment(UPDATED_COMMENT)
            .experts(UPDATED_EXPERTS)
            .link(UPDATED_LINK)
            .limitationPercieved(UPDATED_LIMITATION_PERCIEVED);

        restCellModelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCellModel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCellModel))
            )
            .andExpect(status().isOk());

        // Validate the CellModel in the database
        List<CellModel> cellModelList = cellModelRepository.findAll();
        assertThat(cellModelList).hasSize(databaseSizeBeforeUpdate);
        CellModel testCellModel = cellModelList.get(cellModelList.size() - 1);
        assertThat(testCellModel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCellModel.getDateAdded()).isEqualTo(UPDATED_DATE_ADDED);
        assertThat(testCellModel.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testCellModel.getSpecies()).isEqualTo(UPDATED_SPECIES);
        assertThat(testCellModel.getTargetOrgan()).isEqualTo(UPDATED_TARGET_ORGAN);
        assertThat(testCellModel.getTargetTissue()).isEqualTo(UPDATED_TARGET_TISSUE);
        assertThat(testCellModel.getVesselInfo()).isEqualTo(UPDATED_VESSEL_INFO);
        assertThat(testCellModel.getMatrix()).isEqualTo(UPDATED_MATRIX);
        assertThat(testCellModel.getProtocol()).isEqualTo(UPDATED_PROTOCOL);
        assertThat(testCellModel.getManufacturer()).isEqualTo(UPDATED_MANUFACTURER);
        assertThat(testCellModel.getMatrixType()).isEqualTo(UPDATED_MATRIX_TYPE);
        assertThat(testCellModel.getVesselType()).isEqualTo(UPDATED_VESSEL_TYPE);
        assertThat(testCellModel.getInhouse()).isEqualTo(UPDATED_INHOUSE);
        assertThat(testCellModel.getRatingCharacterization()).isEqualTo(UPDATED_RATING_CHARACTERIZATION);
        assertThat(testCellModel.getRatingComplexity()).isEqualTo(UPDATED_RATING_COMPLEXITY);
        assertThat(testCellModel.getRatingModelCost()).isEqualTo(UPDATED_RATING_MODEL_COST);
        assertThat(testCellModel.getRatingThroughPutCapacity()).isEqualTo(UPDATED_RATING_THROUGH_PUT_CAPACITY);
        assertThat(testCellModel.getRatingTurnAroundTime()).isEqualTo(UPDATED_RATING_TURN_AROUND_TIME);
        assertThat(testCellModel.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testCellModel.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testCellModel.getModelState()).isEqualTo(UPDATED_MODEL_STATE);
        assertThat(testCellModel.getApplicationADME()).isEqualTo(UPDATED_APPLICATION_ADME);
        assertThat(testCellModel.getApplicationEfficacy()).isEqualTo(UPDATED_APPLICATION_EFFICACY);
        assertThat(testCellModel.getApplicationNone()).isEqualTo(UPDATED_APPLICATION_NONE);
        assertThat(testCellModel.getApplicationSafety()).isEqualTo(UPDATED_APPLICATION_SAFETY);
        assertThat(testCellModel.getAnimalAssayReplaced()).isEqualTo(UPDATED_ANIMAL_ASSAY_REPLACED);
        assertThat(testCellModel.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testCellModel.getExperts()).isEqualTo(UPDATED_EXPERTS);
        assertThat(testCellModel.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testCellModel.getLimitationPercieved()).isEqualTo(UPDATED_LIMITATION_PERCIEVED);
    }

    @Test
    @Transactional
    void patchNonExistingCellModel() throws Exception {
        int databaseSizeBeforeUpdate = cellModelRepository.findAll().size();
        cellModel.setId(UUID.randomUUID().toString());

        // Create the CellModel
        CellModelDTO cellModelDTO = cellModelMapper.toDto(cellModel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCellModelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cellModelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cellModelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CellModel in the database
        List<CellModel> cellModelList = cellModelRepository.findAll();
        assertThat(cellModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCellModel() throws Exception {
        int databaseSizeBeforeUpdate = cellModelRepository.findAll().size();
        cellModel.setId(UUID.randomUUID().toString());

        // Create the CellModel
        CellModelDTO cellModelDTO = cellModelMapper.toDto(cellModel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCellModelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cellModelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CellModel in the database
        List<CellModel> cellModelList = cellModelRepository.findAll();
        assertThat(cellModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCellModel() throws Exception {
        int databaseSizeBeforeUpdate = cellModelRepository.findAll().size();
        cellModel.setId(UUID.randomUUID().toString());

        // Create the CellModel
        CellModelDTO cellModelDTO = cellModelMapper.toDto(cellModel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCellModelMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cellModelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CellModel in the database
        List<CellModel> cellModelList = cellModelRepository.findAll();
        assertThat(cellModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCellModel() throws Exception {
        // Initialize the database
        cellModel.setId(UUID.randomUUID().toString());
        cellModelRepository.saveAndFlush(cellModel);

        int databaseSizeBeforeDelete = cellModelRepository.findAll().size();

        // Delete the cellModel
        restCellModelMockMvc
            .perform(delete(ENTITY_API_URL_ID, cellModel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CellModel> cellModelList = cellModelRepository.findAll();
        assertThat(cellModelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
