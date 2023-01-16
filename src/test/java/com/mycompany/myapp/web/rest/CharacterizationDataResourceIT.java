package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CharacterizationData;
import com.mycompany.myapp.repository.CharacterizationDataRepository;
import com.mycompany.myapp.service.dto.CharacterizationDataDTO;
import com.mycompany.myapp.service.mapper.CharacterizationDataMapper;
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
 * Integration tests for the {@link CharacterizationDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CharacterizationDataResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/characterization-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CharacterizationDataRepository characterizationDataRepository;

    @Autowired
    private CharacterizationDataMapper characterizationDataMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCharacterizationDataMockMvc;

    private CharacterizationData characterizationData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CharacterizationData createEntity(EntityManager em) {
        CharacterizationData characterizationData = new CharacterizationData().description(DEFAULT_DESCRIPTION).link(DEFAULT_LINK);
        return characterizationData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CharacterizationData createUpdatedEntity(EntityManager em) {
        CharacterizationData characterizationData = new CharacterizationData().description(UPDATED_DESCRIPTION).link(UPDATED_LINK);
        return characterizationData;
    }

    @BeforeEach
    public void initTest() {
        characterizationData = createEntity(em);
    }

    @Test
    @Transactional
    void createCharacterizationData() throws Exception {
        int databaseSizeBeforeCreate = characterizationDataRepository.findAll().size();
        // Create the CharacterizationData
        CharacterizationDataDTO characterizationDataDTO = characterizationDataMapper.toDto(characterizationData);
        restCharacterizationDataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(characterizationDataDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CharacterizationData in the database
        List<CharacterizationData> characterizationDataList = characterizationDataRepository.findAll();
        assertThat(characterizationDataList).hasSize(databaseSizeBeforeCreate + 1);
        CharacterizationData testCharacterizationData = characterizationDataList.get(characterizationDataList.size() - 1);
        assertThat(testCharacterizationData.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCharacterizationData.getLink()).isEqualTo(DEFAULT_LINK);
    }

    @Test
    @Transactional
    void createCharacterizationDataWithExistingId() throws Exception {
        // Create the CharacterizationData with an existing ID
        characterizationData.setId(1L);
        CharacterizationDataDTO characterizationDataDTO = characterizationDataMapper.toDto(characterizationData);

        int databaseSizeBeforeCreate = characterizationDataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCharacterizationDataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(characterizationDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CharacterizationData in the database
        List<CharacterizationData> characterizationDataList = characterizationDataRepository.findAll();
        assertThat(characterizationDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = characterizationDataRepository.findAll().size();
        // set the field null
        characterizationData.setDescription(null);

        // Create the CharacterizationData, which fails.
        CharacterizationDataDTO characterizationDataDTO = characterizationDataMapper.toDto(characterizationData);

        restCharacterizationDataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(characterizationDataDTO))
            )
            .andExpect(status().isBadRequest());

        List<CharacterizationData> characterizationDataList = characterizationDataRepository.findAll();
        assertThat(characterizationDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLinkIsRequired() throws Exception {
        int databaseSizeBeforeTest = characterizationDataRepository.findAll().size();
        // set the field null
        characterizationData.setLink(null);

        // Create the CharacterizationData, which fails.
        CharacterizationDataDTO characterizationDataDTO = characterizationDataMapper.toDto(characterizationData);

        restCharacterizationDataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(characterizationDataDTO))
            )
            .andExpect(status().isBadRequest());

        List<CharacterizationData> characterizationDataList = characterizationDataRepository.findAll();
        assertThat(characterizationDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCharacterizationData() throws Exception {
        // Initialize the database
        characterizationDataRepository.saveAndFlush(characterizationData);

        // Get all the characterizationDataList
        restCharacterizationDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(characterizationData.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)));
    }

    @Test
    @Transactional
    void getCharacterizationData() throws Exception {
        // Initialize the database
        characterizationDataRepository.saveAndFlush(characterizationData);

        // Get the characterizationData
        restCharacterizationDataMockMvc
            .perform(get(ENTITY_API_URL_ID, characterizationData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(characterizationData.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK));
    }

    @Test
    @Transactional
    void getNonExistingCharacterizationData() throws Exception {
        // Get the characterizationData
        restCharacterizationDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCharacterizationData() throws Exception {
        // Initialize the database
        characterizationDataRepository.saveAndFlush(characterizationData);

        int databaseSizeBeforeUpdate = characterizationDataRepository.findAll().size();

        // Update the characterizationData
        CharacterizationData updatedCharacterizationData = characterizationDataRepository.findById(characterizationData.getId()).get();
        // Disconnect from session so that the updates on updatedCharacterizationData are not directly saved in db
        em.detach(updatedCharacterizationData);
        updatedCharacterizationData.description(UPDATED_DESCRIPTION).link(UPDATED_LINK);
        CharacterizationDataDTO characterizationDataDTO = characterizationDataMapper.toDto(updatedCharacterizationData);

        restCharacterizationDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, characterizationDataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(characterizationDataDTO))
            )
            .andExpect(status().isOk());

        // Validate the CharacterizationData in the database
        List<CharacterizationData> characterizationDataList = characterizationDataRepository.findAll();
        assertThat(characterizationDataList).hasSize(databaseSizeBeforeUpdate);
        CharacterizationData testCharacterizationData = characterizationDataList.get(characterizationDataList.size() - 1);
        assertThat(testCharacterizationData.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCharacterizationData.getLink()).isEqualTo(UPDATED_LINK);
    }

    @Test
    @Transactional
    void putNonExistingCharacterizationData() throws Exception {
        int databaseSizeBeforeUpdate = characterizationDataRepository.findAll().size();
        characterizationData.setId(count.incrementAndGet());

        // Create the CharacterizationData
        CharacterizationDataDTO characterizationDataDTO = characterizationDataMapper.toDto(characterizationData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCharacterizationDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, characterizationDataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(characterizationDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CharacterizationData in the database
        List<CharacterizationData> characterizationDataList = characterizationDataRepository.findAll();
        assertThat(characterizationDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCharacterizationData() throws Exception {
        int databaseSizeBeforeUpdate = characterizationDataRepository.findAll().size();
        characterizationData.setId(count.incrementAndGet());

        // Create the CharacterizationData
        CharacterizationDataDTO characterizationDataDTO = characterizationDataMapper.toDto(characterizationData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCharacterizationDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(characterizationDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CharacterizationData in the database
        List<CharacterizationData> characterizationDataList = characterizationDataRepository.findAll();
        assertThat(characterizationDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCharacterizationData() throws Exception {
        int databaseSizeBeforeUpdate = characterizationDataRepository.findAll().size();
        characterizationData.setId(count.incrementAndGet());

        // Create the CharacterizationData
        CharacterizationDataDTO characterizationDataDTO = characterizationDataMapper.toDto(characterizationData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCharacterizationDataMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(characterizationDataDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CharacterizationData in the database
        List<CharacterizationData> characterizationDataList = characterizationDataRepository.findAll();
        assertThat(characterizationDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCharacterizationDataWithPatch() throws Exception {
        // Initialize the database
        characterizationDataRepository.saveAndFlush(characterizationData);

        int databaseSizeBeforeUpdate = characterizationDataRepository.findAll().size();

        // Update the characterizationData using partial update
        CharacterizationData partialUpdatedCharacterizationData = new CharacterizationData();
        partialUpdatedCharacterizationData.setId(characterizationData.getId());

        partialUpdatedCharacterizationData.description(UPDATED_DESCRIPTION).link(UPDATED_LINK);

        restCharacterizationDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCharacterizationData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCharacterizationData))
            )
            .andExpect(status().isOk());

        // Validate the CharacterizationData in the database
        List<CharacterizationData> characterizationDataList = characterizationDataRepository.findAll();
        assertThat(characterizationDataList).hasSize(databaseSizeBeforeUpdate);
        CharacterizationData testCharacterizationData = characterizationDataList.get(characterizationDataList.size() - 1);
        assertThat(testCharacterizationData.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCharacterizationData.getLink()).isEqualTo(UPDATED_LINK);
    }

    @Test
    @Transactional
    void fullUpdateCharacterizationDataWithPatch() throws Exception {
        // Initialize the database
        characterizationDataRepository.saveAndFlush(characterizationData);

        int databaseSizeBeforeUpdate = characterizationDataRepository.findAll().size();

        // Update the characterizationData using partial update
        CharacterizationData partialUpdatedCharacterizationData = new CharacterizationData();
        partialUpdatedCharacterizationData.setId(characterizationData.getId());

        partialUpdatedCharacterizationData.description(UPDATED_DESCRIPTION).link(UPDATED_LINK);

        restCharacterizationDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCharacterizationData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCharacterizationData))
            )
            .andExpect(status().isOk());

        // Validate the CharacterizationData in the database
        List<CharacterizationData> characterizationDataList = characterizationDataRepository.findAll();
        assertThat(characterizationDataList).hasSize(databaseSizeBeforeUpdate);
        CharacterizationData testCharacterizationData = characterizationDataList.get(characterizationDataList.size() - 1);
        assertThat(testCharacterizationData.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCharacterizationData.getLink()).isEqualTo(UPDATED_LINK);
    }

    @Test
    @Transactional
    void patchNonExistingCharacterizationData() throws Exception {
        int databaseSizeBeforeUpdate = characterizationDataRepository.findAll().size();
        characterizationData.setId(count.incrementAndGet());

        // Create the CharacterizationData
        CharacterizationDataDTO characterizationDataDTO = characterizationDataMapper.toDto(characterizationData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCharacterizationDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, characterizationDataDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(characterizationDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CharacterizationData in the database
        List<CharacterizationData> characterizationDataList = characterizationDataRepository.findAll();
        assertThat(characterizationDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCharacterizationData() throws Exception {
        int databaseSizeBeforeUpdate = characterizationDataRepository.findAll().size();
        characterizationData.setId(count.incrementAndGet());

        // Create the CharacterizationData
        CharacterizationDataDTO characterizationDataDTO = characterizationDataMapper.toDto(characterizationData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCharacterizationDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(characterizationDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CharacterizationData in the database
        List<CharacterizationData> characterizationDataList = characterizationDataRepository.findAll();
        assertThat(characterizationDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCharacterizationData() throws Exception {
        int databaseSizeBeforeUpdate = characterizationDataRepository.findAll().size();
        characterizationData.setId(count.incrementAndGet());

        // Create the CharacterizationData
        CharacterizationDataDTO characterizationDataDTO = characterizationDataMapper.toDto(characterizationData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCharacterizationDataMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(characterizationDataDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CharacterizationData in the database
        List<CharacterizationData> characterizationDataList = characterizationDataRepository.findAll();
        assertThat(characterizationDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCharacterizationData() throws Exception {
        // Initialize the database
        characterizationDataRepository.saveAndFlush(characterizationData);

        int databaseSizeBeforeDelete = characterizationDataRepository.findAll().size();

        // Delete the characterizationData
        restCharacterizationDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, characterizationData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CharacterizationData> characterizationDataList = characterizationDataRepository.findAll();
        assertThat(characterizationDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
