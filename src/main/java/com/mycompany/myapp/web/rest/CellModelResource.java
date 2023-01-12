package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CellModelRepository;
import com.mycompany.myapp.service.CellModelQueryService;
import com.mycompany.myapp.service.CellModelService;
import com.mycompany.myapp.service.criteria.CellModelCriteria;
import com.mycompany.myapp.service.dto.CellModelDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.CellModel}.
 */
@RestController
@RequestMapping("/api")
public class CellModelResource {

    private final Logger log = LoggerFactory.getLogger(CellModelResource.class);

    private static final String ENTITY_NAME = "cellModel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CellModelService cellModelService;

    private final CellModelRepository cellModelRepository;

    private final CellModelQueryService cellModelQueryService;

    public CellModelResource(
        CellModelService cellModelService,
        CellModelRepository cellModelRepository,
        CellModelQueryService cellModelQueryService
    ) {
        this.cellModelService = cellModelService;
        this.cellModelRepository = cellModelRepository;
        this.cellModelQueryService = cellModelQueryService;
    }

    /**
     * {@code POST  /cell-models} : Create a new cellModel.
     *
     * @param cellModelDTO the cellModelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cellModelDTO, or with status {@code 400 (Bad Request)} if the cellModel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cell-models")
    public ResponseEntity<CellModelDTO> createCellModel(@Valid @RequestBody CellModelDTO cellModelDTO) throws URISyntaxException {
        log.debug("REST request to save CellModel : {}", cellModelDTO);
        if (cellModelDTO.getId() != null) {
            throw new BadRequestAlertException("A new cellModel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CellModelDTO result = cellModelService.save(cellModelDTO);
        return ResponseEntity
            .created(new URI("/api/cell-models/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /cell-models/:id} : Updates an existing cellModel.
     *
     * @param id the id of the cellModelDTO to save.
     * @param cellModelDTO the cellModelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cellModelDTO,
     * or with status {@code 400 (Bad Request)} if the cellModelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cellModelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cell-models/{id}")
    public ResponseEntity<CellModelDTO> updateCellModel(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody CellModelDTO cellModelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CellModel : {}, {}", id, cellModelDTO);
        if (cellModelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cellModelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cellModelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CellModelDTO result = cellModelService.update(cellModelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cellModelDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /cell-models/:id} : Partial updates given fields of an existing cellModel, field will ignore if it is null
     *
     * @param id the id of the cellModelDTO to save.
     * @param cellModelDTO the cellModelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cellModelDTO,
     * or with status {@code 400 (Bad Request)} if the cellModelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cellModelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cellModelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cell-models/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CellModelDTO> partialUpdateCellModel(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody CellModelDTO cellModelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CellModel partially : {}, {}", id, cellModelDTO);
        if (cellModelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cellModelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cellModelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CellModelDTO> result = cellModelService.partialUpdate(cellModelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cellModelDTO.getId())
        );
    }

    /**
     * {@code GET  /cell-models} : get all the cellModels.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cellModels in body.
     */
    @GetMapping("/cell-models")
    public ResponseEntity<List<CellModelDTO>> getAllCellModels(
        CellModelCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CellModels by criteria: {}", criteria);
        Page<CellModelDTO> page = cellModelQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cell-models/count} : count all the cellModels.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cell-models/count")
    public ResponseEntity<Long> countCellModels(CellModelCriteria criteria) {
        log.debug("REST request to count CellModels by criteria: {}", criteria);
        return ResponseEntity.ok().body(cellModelQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cell-models/:id} : get the "id" cellModel.
     *
     * @param id the id of the cellModelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cellModelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cell-models/{id}")
    public ResponseEntity<CellModelDTO> getCellModel(@PathVariable String id) {
        log.debug("REST request to get CellModel : {}", id);
        Optional<CellModelDTO> cellModelDTO = cellModelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cellModelDTO);
    }

    /**
     * {@code DELETE  /cell-models/:id} : delete the "id" cellModel.
     *
     * @param id the id of the cellModelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cell-models/{id}")
    public ResponseEntity<Void> deleteCellModel(@PathVariable String id) {
        log.debug("REST request to delete CellModel : {}", id);
        cellModelService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
