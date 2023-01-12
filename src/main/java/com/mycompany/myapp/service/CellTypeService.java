package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CellTypeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.CellType}.
 */
public interface CellTypeService {
    /**
     * Save a cellType.
     *
     * @param cellTypeDTO the entity to save.
     * @return the persisted entity.
     */
    CellTypeDTO save(CellTypeDTO cellTypeDTO);

    /**
     * Updates a cellType.
     *
     * @param cellTypeDTO the entity to update.
     * @return the persisted entity.
     */
    CellTypeDTO update(CellTypeDTO cellTypeDTO);

    /**
     * Partially updates a cellType.
     *
     * @param cellTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CellTypeDTO> partialUpdate(CellTypeDTO cellTypeDTO);

    /**
     * Get all the cellTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CellTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cellType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CellTypeDTO> findOne(Long id);

    /**
     * Delete the "id" cellType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
