package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.CellModel;
import com.mycompany.myapp.repository.CellModelRepository;
import com.mycompany.myapp.service.criteria.CellModelCriteria;
import com.mycompany.myapp.service.dto.CellModelDTO;
import com.mycompany.myapp.service.mapper.CellModelMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link CellModel} entities in the database.
 * The main input is a {@link CellModelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CellModelDTO} or a {@link Page} of {@link CellModelDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CellModelQueryService extends QueryService<CellModel> {

    private final Logger log = LoggerFactory.getLogger(CellModelQueryService.class);

    private final CellModelRepository cellModelRepository;

    private final CellModelMapper cellModelMapper;

    public CellModelQueryService(CellModelRepository cellModelRepository, CellModelMapper cellModelMapper) {
        this.cellModelRepository = cellModelRepository;
        this.cellModelMapper = cellModelMapper;
    }

    /**
     * Return a {@link List} of {@link CellModelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CellModelDTO> findByCriteria(CellModelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CellModel> specification = createSpecification(criteria);
        return cellModelMapper.toDto(cellModelRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CellModelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CellModelDTO> findByCriteria(CellModelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CellModel> specification = createSpecification(criteria);
        return cellModelRepository.findAll(specification, page).map(cellModelMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CellModelCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CellModel> specification = createSpecification(criteria);
        return cellModelRepository.count(specification);
    }

    /**
     * Function to convert {@link CellModelCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CellModel> createSpecification(CellModelCriteria criteria) {
        Specification<CellModel> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getId(), CellModel_.id));
            }
            if (criteria.getDateAdded() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateAdded(), CellModel_.dateAdded));
            }
            if (criteria.getDateUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateUpdated(), CellModel_.dateUpdated));
            }
            if (criteria.getSpecies() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSpecies(), CellModel_.species));
            }
            if (criteria.getTargetOrgan() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTargetOrgan(), CellModel_.targetOrgan));
            }
            if (criteria.getTargetTissue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTargetTissue(), CellModel_.targetTissue));
            }
            if (criteria.getVesselInfo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVesselInfo(), CellModel_.vesselInfo));
            }
            if (criteria.getProtocol() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProtocol(), CellModel_.protocol));
            }
            if (criteria.getManufacturer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getManufacturer(), CellModel_.manufacturer));
            }
            if (criteria.getMatrixType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMatrixType(), CellModel_.matrixType));
            }
            if (criteria.getVesselType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVesselType(), CellModel_.vesselType));
            }
            if (criteria.getInhouse() != null) {
                specification = specification.and(buildSpecification(criteria.getInhouse(), CellModel_.inhouse));
            }
            if (criteria.getRatingCharacterization() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getRatingCharacterization(), CellModel_.ratingCharacterization));
            }
            if (criteria.getRatingComplexity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRatingComplexity(), CellModel_.ratingComplexity));
            }
            if (criteria.getRatingModelCost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRatingModelCost(), CellModel_.ratingModelCost));
            }
            if (criteria.getRatingThroughPutCapacity() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getRatingThroughPutCapacity(), CellModel_.ratingThroughPutCapacity));
            }
            if (criteria.getRatingTurnAroundTime() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getRatingTurnAroundTime(), CellModel_.ratingTurnAroundTime));
            }
            if (criteria.getImageUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImageUrl(), CellModel_.imageUrl));
            }
            if (criteria.getDepartment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDepartment(), CellModel_.department));
            }
            if (criteria.getModelState() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModelState(), CellModel_.modelState));
            }
            if (criteria.getApplicationADME() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationADME(), CellModel_.applicationADME));
            }
            if (criteria.getApplicationEfficacy() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationEfficacy(), CellModel_.applicationEfficacy));
            }
            if (criteria.getApplicationNone() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationNone(), CellModel_.applicationNone));
            }
            if (criteria.getApplicationSafety() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationSafety(), CellModel_.applicationSafety));
            }
            if (criteria.getAnimalAssayReplaced() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getAnimalAssayReplaced(), CellModel_.animalAssayReplaced));
            }
            if (criteria.getNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNote(), CellModel_.note));
            }
            if (criteria.getExperts() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExperts(), CellModel_.experts));
            }
            if (criteria.getLink() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLink(), CellModel_.link));
            }
            if (criteria.getLimitationPerceived() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLimitationPerceived(), CellModel_.limitationPerceived));
            }
            if (criteria.getUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUserId(), root -> root.join(CellModel_.user, JoinType.LEFT).get(User_.id))
                    );
            }
            if (criteria.getProjectSupportedId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProjectSupportedId(),
                            root -> root.join(CellModel_.projectSupporteds, JoinType.LEFT).get(ProjectSupported_.id)
                        )
                    );
            }
            if (criteria.getCellTypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCellTypeId(),
                            root -> root.join(CellModel_.cellTypes, JoinType.LEFT).get(CellType_.id)
                        )
                    );
            }
            if (criteria.getCommentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCommentId(), root -> root.join(CellModel_.comments, JoinType.LEFT).get(Comment_.id))
                    );
            }
            if (criteria.getCharacterizationDataId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCharacterizationDataId(),
                            root -> root.join(CellModel_.characterizationData, JoinType.LEFT).get(CharacterizationData_.id)
                        )
                    );
            }
            if (criteria.getPublicationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPublicationId(),
                            root -> root.join(CellModel_.publications, JoinType.LEFT).get(Publication_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
