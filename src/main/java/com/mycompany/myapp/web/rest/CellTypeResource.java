package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CellTypeRepository;
import com.mycompany.myapp.service.CellTypeService;
import com.mycompany.myapp.service.dto.CellTypeDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.CellType}.
 */
@RestController
@RequestMapping("/api")
public class CellTypeResource {

    private final Logger log = LoggerFactory.getLogger(CellTypeResource.class);

    private static final String ENTITY_NAME = "cellType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CellTypeService cellTypeService;

    private final CellTypeRepository cellTypeRepository;

    public CellTypeResource(CellTypeService cellTypeService, CellTypeRepository cellTypeRepository) {
        this.cellTypeService = cellTypeService;
        this.cellTypeRepository = cellTypeRepository;
    }

    /**
     * {@code POST  /cell-types} : Create a new cellType.
     *
     * @param cellTypeDTO the cellTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cellTypeDTO, or with status {@code 400 (Bad Request)} if the cellType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cell-types")
    public ResponseEntity<CellTypeDTO> createCellType(@Valid @RequestBody CellTypeDTO cellTypeDTO) throws URISyntaxException {
        log.debug("REST request to save CellType : {}", cellTypeDTO);
        if (cellTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new cellType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CellTypeDTO result = cellTypeService.save(cellTypeDTO);
        return ResponseEntity
            .created(new URI("/api/cell-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cell-types/:id} : Updates an existing cellType.
     *
     * @param id the id of the cellTypeDTO to save.
     * @param cellTypeDTO the cellTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cellTypeDTO,
     * or with status {@code 400 (Bad Request)} if the cellTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cellTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cell-types/{id}")
    public ResponseEntity<CellTypeDTO> updateCellType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CellTypeDTO cellTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CellType : {}, {}", id, cellTypeDTO);
        if (cellTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cellTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cellTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CellTypeDTO result = cellTypeService.update(cellTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cellTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cell-types/:id} : Partial updates given fields of an existing cellType, field will ignore if it is null
     *
     * @param id the id of the cellTypeDTO to save.
     * @param cellTypeDTO the cellTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cellTypeDTO,
     * or with status {@code 400 (Bad Request)} if the cellTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cellTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cellTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cell-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CellTypeDTO> partialUpdateCellType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CellTypeDTO cellTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CellType partially : {}, {}", id, cellTypeDTO);
        if (cellTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cellTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cellTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CellTypeDTO> result = cellTypeService.partialUpdate(cellTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cellTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cell-types} : get all the cellTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cellTypes in body.
     */
    @GetMapping("/cell-types")
    public ResponseEntity<List<CellTypeDTO>> getAllCellTypes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CellTypes");
        Page<CellTypeDTO> page = cellTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cell-types/:id} : get the "id" cellType.
     *
     * @param id the id of the cellTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cellTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cell-types/{id}")
    public ResponseEntity<CellTypeDTO> getCellType(@PathVariable Long id) {
        log.debug("REST request to get CellType : {}", id);
        Optional<CellTypeDTO> cellTypeDTO = cellTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cellTypeDTO);
    }

    /**
     * {@code DELETE  /cell-types/:id} : delete the "id" cellType.
     *
     * @param id the id of the cellTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cell-types/{id}")
    public ResponseEntity<Void> deleteCellType(@PathVariable Long id) {
        log.debug("REST request to delete CellType : {}", id);
        cellTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
