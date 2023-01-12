package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.PublicationDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Publication}.
 */
public interface PublicationService {
    /**
     * Save a publication.
     *
     * @param publicationDTO the entity to save.
     * @return the persisted entity.
     */
    PublicationDTO save(PublicationDTO publicationDTO);

    /**
     * Updates a publication.
     *
     * @param publicationDTO the entity to update.
     * @return the persisted entity.
     */
    PublicationDTO update(PublicationDTO publicationDTO);

    /**
     * Partially updates a publication.
     *
     * @param publicationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PublicationDTO> partialUpdate(PublicationDTO publicationDTO);

    /**
     * Get all the publications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PublicationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" publication.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PublicationDTO> findOne(Long id);

    /**
     * Delete the "id" publication.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
