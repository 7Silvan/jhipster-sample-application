package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CharacterizationDataRepository;
import com.mycompany.myapp.service.CharacterizationDataService;
import com.mycompany.myapp.service.dto.CharacterizationDataDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CharacterizationData}.
 */
@RestController
@RequestMapping("/api")
public class CharacterizationDataResource {

    private final Logger log = LoggerFactory.getLogger(CharacterizationDataResource.class);

    private static final String ENTITY_NAME = "characterizationData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CharacterizationDataService characterizationDataService;

    private final CharacterizationDataRepository characterizationDataRepository;

    public CharacterizationDataResource(
        CharacterizationDataService characterizationDataService,
        CharacterizationDataRepository characterizationDataRepository
    ) {
        this.characterizationDataService = characterizationDataService;
        this.characterizationDataRepository = characterizationDataRepository;
    }

    /**
     * {@code POST  /characterization-data} : Create a new characterizationData.
     *
     * @param characterizationDataDTO the characterizationDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new characterizationDataDTO, or with status {@code 400 (Bad Request)} if the characterizationData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/characterization-data")
    public ResponseEntity<CharacterizationDataDTO> createCharacterizationData(
        @Valid @RequestBody CharacterizationDataDTO characterizationDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to save CharacterizationData : {}", characterizationDataDTO);
        if (characterizationDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new characterizationData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CharacterizationDataDTO result = characterizationDataService.save(characterizationDataDTO);
        return ResponseEntity
            .created(new URI("/api/characterization-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /characterization-data/:id} : Updates an existing characterizationData.
     *
     * @param id the id of the characterizationDataDTO to save.
     * @param characterizationDataDTO the characterizationDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated characterizationDataDTO,
     * or with status {@code 400 (Bad Request)} if the characterizationDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the characterizationDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/characterization-data/{id}")
    public ResponseEntity<CharacterizationDataDTO> updateCharacterizationData(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CharacterizationDataDTO characterizationDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CharacterizationData : {}, {}", id, characterizationDataDTO);
        if (characterizationDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, characterizationDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!characterizationDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CharacterizationDataDTO result = characterizationDataService.update(characterizationDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, characterizationDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /characterization-data/:id} : Partial updates given fields of an existing characterizationData, field will ignore if it is null
     *
     * @param id the id of the characterizationDataDTO to save.
     * @param characterizationDataDTO the characterizationDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated characterizationDataDTO,
     * or with status {@code 400 (Bad Request)} if the characterizationDataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the characterizationDataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the characterizationDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/characterization-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CharacterizationDataDTO> partialUpdateCharacterizationData(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CharacterizationDataDTO characterizationDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CharacterizationData partially : {}, {}", id, characterizationDataDTO);
        if (characterizationDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, characterizationDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!characterizationDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CharacterizationDataDTO> result = characterizationDataService.partialUpdate(characterizationDataDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, characterizationDataDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /characterization-data} : get all the characterizationData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of characterizationData in body.
     */
    @GetMapping("/characterization-data")
    public ResponseEntity<List<CharacterizationDataDTO>> getAllCharacterizationData(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CharacterizationData");
        Page<CharacterizationDataDTO> page = characterizationDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /characterization-data/:id} : get the "id" characterizationData.
     *
     * @param id the id of the characterizationDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the characterizationDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/characterization-data/{id}")
    public ResponseEntity<CharacterizationDataDTO> getCharacterizationData(@PathVariable Long id) {
        log.debug("REST request to get CharacterizationData : {}", id);
        Optional<CharacterizationDataDTO> characterizationDataDTO = characterizationDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(characterizationDataDTO);
    }

    /**
     * {@code DELETE  /characterization-data/:id} : delete the "id" characterizationData.
     *
     * @param id the id of the characterizationDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/characterization-data/{id}")
    public ResponseEntity<Void> deleteCharacterizationData(@PathVariable Long id) {
        log.debug("REST request to delete CharacterizationData : {}", id);
        characterizationDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
