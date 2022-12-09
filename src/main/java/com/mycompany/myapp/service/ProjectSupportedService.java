package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ProjectSupportedDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.ProjectSupported}.
 */
public interface ProjectSupportedService {
    /**
     * Save a projectSupported.
     *
     * @param projectSupportedDTO the entity to save.
     * @return the persisted entity.
     */
    ProjectSupportedDTO save(ProjectSupportedDTO projectSupportedDTO);

    /**
     * Updates a projectSupported.
     *
     * @param projectSupportedDTO the entity to update.
     * @return the persisted entity.
     */
    ProjectSupportedDTO update(ProjectSupportedDTO projectSupportedDTO);

    /**
     * Partially updates a projectSupported.
     *
     * @param projectSupportedDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProjectSupportedDTO> partialUpdate(ProjectSupportedDTO projectSupportedDTO);

    /**
     * Get all the projectSupporteds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProjectSupportedDTO> findAll(Pageable pageable);

    /**
     * Get the "id" projectSupported.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProjectSupportedDTO> findOne(Long id);

    /**
     * Delete the "id" projectSupported.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
