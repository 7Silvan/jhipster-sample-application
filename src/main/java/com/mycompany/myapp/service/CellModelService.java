package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CellModelDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.CellModel}.
 */
public interface CellModelService {
    /**
     * Save a cellModel.
     *
     * @param cellModelDTO the entity to save.
     * @return the persisted entity.
     */
    CellModelDTO save(CellModelDTO cellModelDTO);

    /**
     * Updates a cellModel.
     *
     * @param cellModelDTO the entity to update.
     * @return the persisted entity.
     */
    CellModelDTO update(CellModelDTO cellModelDTO);

    /**
     * Partially updates a cellModel.
     *
     * @param cellModelDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CellModelDTO> partialUpdate(CellModelDTO cellModelDTO);

    /**
     * Get all the cellModels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CellModelDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cellModel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CellModelDTO> findOne(String id);

    /**
     * Delete the "id" cellModel.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
