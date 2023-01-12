package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CharacterizationDataDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.CharacterizationData}.
 */
public interface CharacterizationDataService {
    /**
     * Save a characterizationData.
     *
     * @param characterizationDataDTO the entity to save.
     * @return the persisted entity.
     */
    CharacterizationDataDTO save(CharacterizationDataDTO characterizationDataDTO);

    /**
     * Updates a characterizationData.
     *
     * @param characterizationDataDTO the entity to update.
     * @return the persisted entity.
     */
    CharacterizationDataDTO update(CharacterizationDataDTO characterizationDataDTO);

    /**
     * Partially updates a characterizationData.
     *
     * @param characterizationDataDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CharacterizationDataDTO> partialUpdate(CharacterizationDataDTO characterizationDataDTO);

    /**
     * Get all the characterizationData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CharacterizationDataDTO> findAll(Pageable pageable);

    /**
     * Get the "id" characterizationData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CharacterizationDataDTO> findOne(Long id);

    /**
     * Delete the "id" characterizationData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
