package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CellModel;
import com.mycompany.myapp.repository.CellModelRepository;
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

    private static final ZonedDateTime DEFAULT_DATE_UPDATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_UPDATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

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

    private static final String DEFAULT_CHARACTERIZATION_DATA = "AAAAAAAAAA";
    private static final String UPDATED_CHARACTERIZATION_DATA = "BBBBBBBBBB";

    private static final Integer DEFAULT_RATING_CHARACTERIZATION = 1;
    private static final Integer UPDATED_RATING_CHARACTERIZATION = 2;

    private static final Integer DEFAULT_RATING_COMPLEXITY = 1;
    private static final Integer UPDATED_RATING_COMPLEXITY = 2;

    private static final Integer DEFAULT_RATING_MODEL_COST = 1;
    private static final Integer UPDATED_RATING_MODEL_COST = 2;

    private static final Integer DEFAULT_RATING_THROUGH_PUT_CAPACITY = 1;
    private static final Integer UPDATED_RATING_THROUGH_PUT_CAPACITY = 2;

    private static final Integer DEFAULT_RATING_TURN_AROUND_TIME = 1;
    private static final Integer UPDATED_RATING_TURN_AROUND_TIME = 2;

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

    private static final Boolean DEFAULT_IS_REGULATORY_SUBMISSION = false;
    private static final Boolean UPDATED_IS_REGULATORY_SUBMISSION = true;

    private static final String DEFAULT_PUBLICATIONS = "AAAAAAAAAA";
    private static final String UPDATED_PUBLICATIONS = "BBBBBBBBBB";

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
            .characterizationData(DEFAULT_CHARACTERIZATION_DATA)
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
            .isRegulatorySubmission(DEFAULT_IS_REGULATORY_SUBMISSION)
            .publications(DEFAULT_PUBLICATIONS)
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
            .characterizationData(UPDATED_CHARACTERIZATION_DATA)
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
            .isRegulatorySubmission(UPDATED_IS_REGULATORY_SUBMISSION)
            .publications(UPDATED_PUBLICATIONS)
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
        assertThat(testCellModel.getCharacterizationData()).isEqualTo(DEFAULT_CHARACTERIZATION_DATA);
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
        assertThat(testCellModel.getIsRegulatorySubmission()).isEqualTo(DEFAULT_IS_REGULATORY_SUBMISSION);
        assertThat(testCellModel.getPublications()).isEqualTo(DEFAULT_PUBLICATIONS);
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
    void checkIsRegulatorySubmissionIsRequired() throws Exception {
        int databaseSizeBeforeTest = cellModelRepository.findAll().size();
        // set the field null
        cellModel.setIsRegulatorySubmission(null);

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
            .andExpect(jsonPath("$.[*].characterizationData").value(hasItem(DEFAULT_CHARACTERIZATION_DATA)))
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
            .andExpect(jsonPath("$.[*].isRegulatorySubmission").value(hasItem(DEFAULT_IS_REGULATORY_SUBMISSION.booleanValue())))
            .andExpect(jsonPath("$.[*].publications").value(hasItem(DEFAULT_PUBLICATIONS)))
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
            .andExpect(jsonPath("$.characterizationData").value(DEFAULT_CHARACTERIZATION_DATA))
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
            .andExpect(jsonPath("$.isRegulatorySubmission").value(DEFAULT_IS_REGULATORY_SUBMISSION.booleanValue()))
            .andExpect(jsonPath("$.publications").value(DEFAULT_PUBLICATIONS))
            .andExpect(jsonPath("$.limitationPercieved").value(DEFAULT_LIMITATION_PERCIEVED));
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
            .characterizationData(UPDATED_CHARACTERIZATION_DATA)
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
            .isRegulatorySubmission(UPDATED_IS_REGULATORY_SUBMISSION)
            .publications(UPDATED_PUBLICATIONS)
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
        assertThat(testCellModel.getCharacterizationData()).isEqualTo(UPDATED_CHARACTERIZATION_DATA);
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
        assertThat(testCellModel.getIsRegulatorySubmission()).isEqualTo(UPDATED_IS_REGULATORY_SUBMISSION);
        assertThat(testCellModel.getPublications()).isEqualTo(UPDATED_PUBLICATIONS);
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
            .ratingComplexity(UPDATED_RATING_COMPLEXITY)
            .department(UPDATED_DEPARTMENT)
            .modelState(UPDATED_MODEL_STATE)
            .applicationADME(UPDATED_APPLICATION_ADME)
            .applicationEfficacy(UPDATED_APPLICATION_EFFICACY)
            .applicationNone(UPDATED_APPLICATION_NONE)
            .animalAssayReplaced(UPDATED_ANIMAL_ASSAY_REPLACED)
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
        assertThat(testCellModel.getCharacterizationData()).isEqualTo(DEFAULT_CHARACTERIZATION_DATA);
        assertThat(testCellModel.getRatingCharacterization()).isEqualTo(DEFAULT_RATING_CHARACTERIZATION);
        assertThat(testCellModel.getRatingComplexity()).isEqualTo(UPDATED_RATING_COMPLEXITY);
        assertThat(testCellModel.getRatingModelCost()).isEqualTo(DEFAULT_RATING_MODEL_COST);
        assertThat(testCellModel.getRatingThroughPutCapacity()).isEqualTo(DEFAULT_RATING_THROUGH_PUT_CAPACITY);
        assertThat(testCellModel.getRatingTurnAroundTime()).isEqualTo(DEFAULT_RATING_TURN_AROUND_TIME);
        assertThat(testCellModel.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testCellModel.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testCellModel.getModelState()).isEqualTo(UPDATED_MODEL_STATE);
        assertThat(testCellModel.getApplicationADME()).isEqualTo(UPDATED_APPLICATION_ADME);
        assertThat(testCellModel.getApplicationEfficacy()).isEqualTo(UPDATED_APPLICATION_EFFICACY);
        assertThat(testCellModel.getApplicationNone()).isEqualTo(UPDATED_APPLICATION_NONE);
        assertThat(testCellModel.getApplicationSafety()).isEqualTo(DEFAULT_APPLICATION_SAFETY);
        assertThat(testCellModel.getAnimalAssayReplaced()).isEqualTo(UPDATED_ANIMAL_ASSAY_REPLACED);
        assertThat(testCellModel.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testCellModel.getExperts()).isEqualTo(UPDATED_EXPERTS);
        assertThat(testCellModel.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testCellModel.getIsRegulatorySubmission()).isEqualTo(DEFAULT_IS_REGULATORY_SUBMISSION);
        assertThat(testCellModel.getPublications()).isEqualTo(DEFAULT_PUBLICATIONS);
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
            .characterizationData(UPDATED_CHARACTERIZATION_DATA)
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
            .isRegulatorySubmission(UPDATED_IS_REGULATORY_SUBMISSION)
            .publications(UPDATED_PUBLICATIONS)
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
        assertThat(testCellModel.getCharacterizationData()).isEqualTo(UPDATED_CHARACTERIZATION_DATA);
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
        assertThat(testCellModel.getIsRegulatorySubmission()).isEqualTo(UPDATED_IS_REGULATORY_SUBMISSION);
        assertThat(testCellModel.getPublications()).isEqualTo(UPDATED_PUBLICATIONS);
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
