package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ProjectSupportedRepository;
import com.mycompany.myapp.service.ProjectSupportedService;
import com.mycompany.myapp.service.dto.ProjectSupportedDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ProjectSupported}.
 */
@RestController
@RequestMapping("/api")
public class ProjectSupportedResource {

    private final Logger log = LoggerFactory.getLogger(ProjectSupportedResource.class);

    private static final String ENTITY_NAME = "projectSupported";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectSupportedService projectSupportedService;

    private final ProjectSupportedRepository projectSupportedRepository;

    public ProjectSupportedResource(
        ProjectSupportedService projectSupportedService,
        ProjectSupportedRepository projectSupportedRepository
    ) {
        this.projectSupportedService = projectSupportedService;
        this.projectSupportedRepository = projectSupportedRepository;
    }

    /**
     * {@code POST  /project-supporteds} : Create a new projectSupported.
     *
     * @param projectSupportedDTO the projectSupportedDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectSupportedDTO, or with status {@code 400 (Bad Request)} if the projectSupported has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-supporteds")
    public ResponseEntity<ProjectSupportedDTO> createProjectSupported(@Valid @RequestBody ProjectSupportedDTO projectSupportedDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProjectSupported : {}", projectSupportedDTO);
        if (projectSupportedDTO.getId() != null) {
            throw new BadRequestAlertException("A new projectSupported cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectSupportedDTO result = projectSupportedService.save(projectSupportedDTO);
        return ResponseEntity
            .created(new URI("/api/project-supporteds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-supporteds/:id} : Updates an existing projectSupported.
     *
     * @param id the id of the projectSupportedDTO to save.
     * @param projectSupportedDTO the projectSupportedDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectSupportedDTO,
     * or with status {@code 400 (Bad Request)} if the projectSupportedDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectSupportedDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-supporteds/{id}")
    public ResponseEntity<ProjectSupportedDTO> updateProjectSupported(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProjectSupportedDTO projectSupportedDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProjectSupported : {}, {}", id, projectSupportedDTO);
        if (projectSupportedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectSupportedDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectSupportedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProjectSupportedDTO result = projectSupportedService.update(projectSupportedDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectSupportedDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /project-supporteds/:id} : Partial updates given fields of an existing projectSupported, field will ignore if it is null
     *
     * @param id the id of the projectSupportedDTO to save.
     * @param projectSupportedDTO the projectSupportedDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectSupportedDTO,
     * or with status {@code 400 (Bad Request)} if the projectSupportedDTO is not valid,
     * or with status {@code 404 (Not Found)} if the projectSupportedDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the projectSupportedDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/project-supporteds/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProjectSupportedDTO> partialUpdateProjectSupported(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProjectSupportedDTO projectSupportedDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProjectSupported partially : {}, {}", id, projectSupportedDTO);
        if (projectSupportedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectSupportedDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectSupportedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProjectSupportedDTO> result = projectSupportedService.partialUpdate(projectSupportedDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectSupportedDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /project-supporteds} : get all the projectSupporteds.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectSupporteds in body.
     */
    @GetMapping("/project-supporteds")
    public ResponseEntity<List<ProjectSupportedDTO>> getAllProjectSupporteds(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ProjectSupporteds");
        Page<ProjectSupportedDTO> page = projectSupportedService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-supporteds/:id} : get the "id" projectSupported.
     *
     * @param id the id of the projectSupportedDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectSupportedDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-supporteds/{id}")
    public ResponseEntity<ProjectSupportedDTO> getProjectSupported(@PathVariable Long id) {
        log.debug("REST request to get ProjectSupported : {}", id);
        Optional<ProjectSupportedDTO> projectSupportedDTO = projectSupportedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectSupportedDTO);
    }

    /**
     * {@code DELETE  /project-supporteds/:id} : delete the "id" projectSupported.
     *
     * @param id the id of the projectSupportedDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-supporteds/{id}")
    public ResponseEntity<Void> deleteProjectSupported(@PathVariable Long id) {
        log.debug("REST request to delete ProjectSupported : {}", id);
        projectSupportedService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
