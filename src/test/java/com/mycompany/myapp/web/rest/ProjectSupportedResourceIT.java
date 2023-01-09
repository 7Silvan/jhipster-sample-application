package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ProjectSupported;
import com.mycompany.myapp.repository.ProjectSupportedRepository;
import com.mycompany.myapp.service.dto.ProjectSupportedDTO;
import com.mycompany.myapp.service.mapper.ProjectSupportedMapper;
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
 * Integration tests for the {@link ProjectSupportedResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProjectSupportedResourceIT {

    private static final String DEFAULT_DECISION_MAKING = "AAAAAAAAAA";
    private static final String UPDATED_DECISION_MAKING = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_REGULATORY = false;
    private static final Boolean UPDATED_IS_REGULATORY = true;

    private static final String DEFAULT_PROJECT_NAME_OR_THEME_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_NAME_OR_THEME_NUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/project-supporteds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProjectSupportedRepository projectSupportedRepository;

    @Autowired
    private ProjectSupportedMapper projectSupportedMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectSupportedMockMvc;

    private ProjectSupported projectSupported;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectSupported createEntity(EntityManager em) {
        ProjectSupported projectSupported = new ProjectSupported()
            .decisionMaking(DEFAULT_DECISION_MAKING)
            .isRegulatory(DEFAULT_IS_REGULATORY)
            .projectNameOrThemeNumber(DEFAULT_PROJECT_NAME_OR_THEME_NUMBER);
        return projectSupported;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectSupported createUpdatedEntity(EntityManager em) {
        ProjectSupported projectSupported = new ProjectSupported()
            .decisionMaking(UPDATED_DECISION_MAKING)
            .isRegulatory(UPDATED_IS_REGULATORY)
            .projectNameOrThemeNumber(UPDATED_PROJECT_NAME_OR_THEME_NUMBER);
        return projectSupported;
    }

    @BeforeEach
    public void initTest() {
        projectSupported = createEntity(em);
    }

    @Test
    @Transactional
    void createProjectSupported() throws Exception {
        int databaseSizeBeforeCreate = projectSupportedRepository.findAll().size();
        // Create the ProjectSupported
        ProjectSupportedDTO projectSupportedDTO = projectSupportedMapper.toDto(projectSupported);
        restProjectSupportedMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectSupportedDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProjectSupported in the database
        List<ProjectSupported> projectSupportedList = projectSupportedRepository.findAll();
        assertThat(projectSupportedList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectSupported testProjectSupported = projectSupportedList.get(projectSupportedList.size() - 1);
        assertThat(testProjectSupported.getDecisionMaking()).isEqualTo(DEFAULT_DECISION_MAKING);
        assertThat(testProjectSupported.getIsRegulatory()).isEqualTo(DEFAULT_IS_REGULATORY);
        assertThat(testProjectSupported.getProjectNameOrThemeNumber()).isEqualTo(DEFAULT_PROJECT_NAME_OR_THEME_NUMBER);
    }

    @Test
    @Transactional
    void createProjectSupportedWithExistingId() throws Exception {
        // Create the ProjectSupported with an existing ID
        projectSupported.setId(1L);
        ProjectSupportedDTO projectSupportedDTO = projectSupportedMapper.toDto(projectSupported);

        int databaseSizeBeforeCreate = projectSupportedRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectSupportedMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectSupportedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectSupported in the database
        List<ProjectSupported> projectSupportedList = projectSupportedRepository.findAll();
        assertThat(projectSupportedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDecisionMakingIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectSupportedRepository.findAll().size();
        // set the field null
        projectSupported.setDecisionMaking(null);

        // Create the ProjectSupported, which fails.
        ProjectSupportedDTO projectSupportedDTO = projectSupportedMapper.toDto(projectSupported);

        restProjectSupportedMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectSupportedDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProjectSupported> projectSupportedList = projectSupportedRepository.findAll();
        assertThat(projectSupportedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsRegulatoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectSupportedRepository.findAll().size();
        // set the field null
        projectSupported.setIsRegulatory(null);

        // Create the ProjectSupported, which fails.
        ProjectSupportedDTO projectSupportedDTO = projectSupportedMapper.toDto(projectSupported);

        restProjectSupportedMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectSupportedDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProjectSupported> projectSupportedList = projectSupportedRepository.findAll();
        assertThat(projectSupportedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProjectNameOrThemeNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectSupportedRepository.findAll().size();
        // set the field null
        projectSupported.setProjectNameOrThemeNumber(null);

        // Create the ProjectSupported, which fails.
        ProjectSupportedDTO projectSupportedDTO = projectSupportedMapper.toDto(projectSupported);

        restProjectSupportedMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectSupportedDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProjectSupported> projectSupportedList = projectSupportedRepository.findAll();
        assertThat(projectSupportedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProjectSupporteds() throws Exception {
        // Initialize the database
        projectSupportedRepository.saveAndFlush(projectSupported);

        // Get all the projectSupportedList
        restProjectSupportedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectSupported.getId().intValue())))
            .andExpect(jsonPath("$.[*].decisionMaking").value(hasItem(DEFAULT_DECISION_MAKING)))
            .andExpect(jsonPath("$.[*].isRegulatory").value(hasItem(DEFAULT_IS_REGULATORY.booleanValue())))
            .andExpect(jsonPath("$.[*].projectNameOrThemeNumber").value(hasItem(DEFAULT_PROJECT_NAME_OR_THEME_NUMBER)));
    }

    @Test
    @Transactional
    void getProjectSupported() throws Exception {
        // Initialize the database
        projectSupportedRepository.saveAndFlush(projectSupported);

        // Get the projectSupported
        restProjectSupportedMockMvc
            .perform(get(ENTITY_API_URL_ID, projectSupported.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectSupported.getId().intValue()))
            .andExpect(jsonPath("$.decisionMaking").value(DEFAULT_DECISION_MAKING))
            .andExpect(jsonPath("$.isRegulatory").value(DEFAULT_IS_REGULATORY.booleanValue()))
            .andExpect(jsonPath("$.projectNameOrThemeNumber").value(DEFAULT_PROJECT_NAME_OR_THEME_NUMBER));
    }

    @Test
    @Transactional
    void getNonExistingProjectSupported() throws Exception {
        // Get the projectSupported
        restProjectSupportedMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProjectSupported() throws Exception {
        // Initialize the database
        projectSupportedRepository.saveAndFlush(projectSupported);

        int databaseSizeBeforeUpdate = projectSupportedRepository.findAll().size();

        // Update the projectSupported
        ProjectSupported updatedProjectSupported = projectSupportedRepository.findById(projectSupported.getId()).get();
        // Disconnect from session so that the updates on updatedProjectSupported are not directly saved in db
        em.detach(updatedProjectSupported);
        updatedProjectSupported
            .decisionMaking(UPDATED_DECISION_MAKING)
            .isRegulatory(UPDATED_IS_REGULATORY)
            .projectNameOrThemeNumber(UPDATED_PROJECT_NAME_OR_THEME_NUMBER);
        ProjectSupportedDTO projectSupportedDTO = projectSupportedMapper.toDto(updatedProjectSupported);

        restProjectSupportedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectSupportedDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectSupportedDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProjectSupported in the database
        List<ProjectSupported> projectSupportedList = projectSupportedRepository.findAll();
        assertThat(projectSupportedList).hasSize(databaseSizeBeforeUpdate);
        ProjectSupported testProjectSupported = projectSupportedList.get(projectSupportedList.size() - 1);
        assertThat(testProjectSupported.getDecisionMaking()).isEqualTo(UPDATED_DECISION_MAKING);
        assertThat(testProjectSupported.getIsRegulatory()).isEqualTo(UPDATED_IS_REGULATORY);
        assertThat(testProjectSupported.getProjectNameOrThemeNumber()).isEqualTo(UPDATED_PROJECT_NAME_OR_THEME_NUMBER);
    }

    @Test
    @Transactional
    void putNonExistingProjectSupported() throws Exception {
        int databaseSizeBeforeUpdate = projectSupportedRepository.findAll().size();
        projectSupported.setId(count.incrementAndGet());

        // Create the ProjectSupported
        ProjectSupportedDTO projectSupportedDTO = projectSupportedMapper.toDto(projectSupported);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectSupportedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectSupportedDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectSupportedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectSupported in the database
        List<ProjectSupported> projectSupportedList = projectSupportedRepository.findAll();
        assertThat(projectSupportedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProjectSupported() throws Exception {
        int databaseSizeBeforeUpdate = projectSupportedRepository.findAll().size();
        projectSupported.setId(count.incrementAndGet());

        // Create the ProjectSupported
        ProjectSupportedDTO projectSupportedDTO = projectSupportedMapper.toDto(projectSupported);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectSupportedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectSupportedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectSupported in the database
        List<ProjectSupported> projectSupportedList = projectSupportedRepository.findAll();
        assertThat(projectSupportedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProjectSupported() throws Exception {
        int databaseSizeBeforeUpdate = projectSupportedRepository.findAll().size();
        projectSupported.setId(count.incrementAndGet());

        // Create the ProjectSupported
        ProjectSupportedDTO projectSupportedDTO = projectSupportedMapper.toDto(projectSupported);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectSupportedMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectSupportedDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectSupported in the database
        List<ProjectSupported> projectSupportedList = projectSupportedRepository.findAll();
        assertThat(projectSupportedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectSupportedWithPatch() throws Exception {
        // Initialize the database
        projectSupportedRepository.saveAndFlush(projectSupported);

        int databaseSizeBeforeUpdate = projectSupportedRepository.findAll().size();

        // Update the projectSupported using partial update
        ProjectSupported partialUpdatedProjectSupported = new ProjectSupported();
        partialUpdatedProjectSupported.setId(projectSupported.getId());

        partialUpdatedProjectSupported.isRegulatory(UPDATED_IS_REGULATORY).projectNameOrThemeNumber(UPDATED_PROJECT_NAME_OR_THEME_NUMBER);

        restProjectSupportedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectSupported.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectSupported))
            )
            .andExpect(status().isOk());

        // Validate the ProjectSupported in the database
        List<ProjectSupported> projectSupportedList = projectSupportedRepository.findAll();
        assertThat(projectSupportedList).hasSize(databaseSizeBeforeUpdate);
        ProjectSupported testProjectSupported = projectSupportedList.get(projectSupportedList.size() - 1);
        assertThat(testProjectSupported.getDecisionMaking()).isEqualTo(DEFAULT_DECISION_MAKING);
        assertThat(testProjectSupported.getIsRegulatory()).isEqualTo(UPDATED_IS_REGULATORY);
        assertThat(testProjectSupported.getProjectNameOrThemeNumber()).isEqualTo(UPDATED_PROJECT_NAME_OR_THEME_NUMBER);
    }

    @Test
    @Transactional
    void fullUpdateProjectSupportedWithPatch() throws Exception {
        // Initialize the database
        projectSupportedRepository.saveAndFlush(projectSupported);

        int databaseSizeBeforeUpdate = projectSupportedRepository.findAll().size();

        // Update the projectSupported using partial update
        ProjectSupported partialUpdatedProjectSupported = new ProjectSupported();
        partialUpdatedProjectSupported.setId(projectSupported.getId());

        partialUpdatedProjectSupported
            .decisionMaking(UPDATED_DECISION_MAKING)
            .isRegulatory(UPDATED_IS_REGULATORY)
            .projectNameOrThemeNumber(UPDATED_PROJECT_NAME_OR_THEME_NUMBER);

        restProjectSupportedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectSupported.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectSupported))
            )
            .andExpect(status().isOk());

        // Validate the ProjectSupported in the database
        List<ProjectSupported> projectSupportedList = projectSupportedRepository.findAll();
        assertThat(projectSupportedList).hasSize(databaseSizeBeforeUpdate);
        ProjectSupported testProjectSupported = projectSupportedList.get(projectSupportedList.size() - 1);
        assertThat(testProjectSupported.getDecisionMaking()).isEqualTo(UPDATED_DECISION_MAKING);
        assertThat(testProjectSupported.getIsRegulatory()).isEqualTo(UPDATED_IS_REGULATORY);
        assertThat(testProjectSupported.getProjectNameOrThemeNumber()).isEqualTo(UPDATED_PROJECT_NAME_OR_THEME_NUMBER);
    }

    @Test
    @Transactional
    void patchNonExistingProjectSupported() throws Exception {
        int databaseSizeBeforeUpdate = projectSupportedRepository.findAll().size();
        projectSupported.setId(count.incrementAndGet());

        // Create the ProjectSupported
        ProjectSupportedDTO projectSupportedDTO = projectSupportedMapper.toDto(projectSupported);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectSupportedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projectSupportedDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectSupportedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectSupported in the database
        List<ProjectSupported> projectSupportedList = projectSupportedRepository.findAll();
        assertThat(projectSupportedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProjectSupported() throws Exception {
        int databaseSizeBeforeUpdate = projectSupportedRepository.findAll().size();
        projectSupported.setId(count.incrementAndGet());

        // Create the ProjectSupported
        ProjectSupportedDTO projectSupportedDTO = projectSupportedMapper.toDto(projectSupported);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectSupportedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectSupportedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectSupported in the database
        List<ProjectSupported> projectSupportedList = projectSupportedRepository.findAll();
        assertThat(projectSupportedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProjectSupported() throws Exception {
        int databaseSizeBeforeUpdate = projectSupportedRepository.findAll().size();
        projectSupported.setId(count.incrementAndGet());

        // Create the ProjectSupported
        ProjectSupportedDTO projectSupportedDTO = projectSupportedMapper.toDto(projectSupported);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectSupportedMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectSupportedDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectSupported in the database
        List<ProjectSupported> projectSupportedList = projectSupportedRepository.findAll();
        assertThat(projectSupportedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProjectSupported() throws Exception {
        // Initialize the database
        projectSupportedRepository.saveAndFlush(projectSupported);

        int databaseSizeBeforeDelete = projectSupportedRepository.findAll().size();

        // Delete the projectSupported
        restProjectSupportedMockMvc
            .perform(delete(ENTITY_API_URL_ID, projectSupported.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjectSupported> projectSupportedList = projectSupportedRepository.findAll();
        assertThat(projectSupportedList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
