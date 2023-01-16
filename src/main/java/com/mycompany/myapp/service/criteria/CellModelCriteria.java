package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.CellModel} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CellModelResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cell-models?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CellModelCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private StringFilter id;

    private ZonedDateTimeFilter dateAdded;

    private ZonedDateTimeFilter dateUpdated;

    private StringFilter species;

    private StringFilter targetOrgan;

    private StringFilter targetTissue;

    private StringFilter vesselInfo;

    private StringFilter protocol;

    private StringFilter manufacturer;

    private StringFilter matrixType;

    private StringFilter vesselType;

    private BooleanFilter inhouse;

    private IntegerFilter ratingCharacterization;

    private IntegerFilter ratingComplexity;

    private IntegerFilter ratingModelCost;

    private IntegerFilter ratingThroughPutCapacity;

    private IntegerFilter ratingTurnAroundTime;

    private StringFilter imageUrl;

    private StringFilter department;

    private StringFilter modelState;

    private BooleanFilter applicationADME;

    private BooleanFilter applicationEfficacy;

    private BooleanFilter applicationNone;

    private BooleanFilter applicationSafety;

    private StringFilter animalAssayReplaced;

    private StringFilter note;

    private StringFilter experts;

    private StringFilter link;

    private StringFilter limitationPerceived;

    private LongFilter userId;

    private LongFilter projectSupportedId;

    private LongFilter cellTypeId;

    private LongFilter commentId;

    private LongFilter characterizationDataId;

    private LongFilter publicationId;

    private Boolean distinct;

    public CellModelCriteria() {}

    public CellModelCriteria(CellModelCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dateAdded = other.dateAdded == null ? null : other.dateAdded.copy();
        this.dateUpdated = other.dateUpdated == null ? null : other.dateUpdated.copy();
        this.species = other.species == null ? null : other.species.copy();
        this.targetOrgan = other.targetOrgan == null ? null : other.targetOrgan.copy();
        this.targetTissue = other.targetTissue == null ? null : other.targetTissue.copy();
        this.vesselInfo = other.vesselInfo == null ? null : other.vesselInfo.copy();
        this.protocol = other.protocol == null ? null : other.protocol.copy();
        this.manufacturer = other.manufacturer == null ? null : other.manufacturer.copy();
        this.matrixType = other.matrixType == null ? null : other.matrixType.copy();
        this.vesselType = other.vesselType == null ? null : other.vesselType.copy();
        this.inhouse = other.inhouse == null ? null : other.inhouse.copy();
        this.ratingCharacterization = other.ratingCharacterization == null ? null : other.ratingCharacterization.copy();
        this.ratingComplexity = other.ratingComplexity == null ? null : other.ratingComplexity.copy();
        this.ratingModelCost = other.ratingModelCost == null ? null : other.ratingModelCost.copy();
        this.ratingThroughPutCapacity = other.ratingThroughPutCapacity == null ? null : other.ratingThroughPutCapacity.copy();
        this.ratingTurnAroundTime = other.ratingTurnAroundTime == null ? null : other.ratingTurnAroundTime.copy();
        this.imageUrl = other.imageUrl == null ? null : other.imageUrl.copy();
        this.department = other.department == null ? null : other.department.copy();
        this.modelState = other.modelState == null ? null : other.modelState.copy();
        this.applicationADME = other.applicationADME == null ? null : other.applicationADME.copy();
        this.applicationEfficacy = other.applicationEfficacy == null ? null : other.applicationEfficacy.copy();
        this.applicationNone = other.applicationNone == null ? null : other.applicationNone.copy();
        this.applicationSafety = other.applicationSafety == null ? null : other.applicationSafety.copy();
        this.animalAssayReplaced = other.animalAssayReplaced == null ? null : other.animalAssayReplaced.copy();
        this.note = other.note == null ? null : other.note.copy();
        this.experts = other.experts == null ? null : other.experts.copy();
        this.link = other.link == null ? null : other.link.copy();
        this.limitationPerceived = other.limitationPerceived == null ? null : other.limitationPerceived.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.projectSupportedId = other.projectSupportedId == null ? null : other.projectSupportedId.copy();
        this.cellTypeId = other.cellTypeId == null ? null : other.cellTypeId.copy();
        this.commentId = other.commentId == null ? null : other.commentId.copy();
        this.characterizationDataId = other.characterizationDataId == null ? null : other.characterizationDataId.copy();
        this.publicationId = other.publicationId == null ? null : other.publicationId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CellModelCriteria copy() {
        return new CellModelCriteria(this);
    }

    public StringFilter getId() {
        return id;
    }

    public StringFilter id() {
        if (id == null) {
            id = new StringFilter();
        }
        return id;
    }

    public void setId(StringFilter id) {
        this.id = id;
    }

    public ZonedDateTimeFilter getDateAdded() {
        return dateAdded;
    }

    public ZonedDateTimeFilter dateAdded() {
        if (dateAdded == null) {
            dateAdded = new ZonedDateTimeFilter();
        }
        return dateAdded;
    }

    public void setDateAdded(ZonedDateTimeFilter dateAdded) {
        this.dateAdded = dateAdded;
    }

    public ZonedDateTimeFilter getDateUpdated() {
        return dateUpdated;
    }

    public ZonedDateTimeFilter dateUpdated() {
        if (dateUpdated == null) {
            dateUpdated = new ZonedDateTimeFilter();
        }
        return dateUpdated;
    }

    public void setDateUpdated(ZonedDateTimeFilter dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public StringFilter getSpecies() {
        return species;
    }

    public StringFilter species() {
        if (species == null) {
            species = new StringFilter();
        }
        return species;
    }

    public void setSpecies(StringFilter species) {
        this.species = species;
    }

    public StringFilter getTargetOrgan() {
        return targetOrgan;
    }

    public StringFilter targetOrgan() {
        if (targetOrgan == null) {
            targetOrgan = new StringFilter();
        }
        return targetOrgan;
    }

    public void setTargetOrgan(StringFilter targetOrgan) {
        this.targetOrgan = targetOrgan;
    }

    public StringFilter getTargetTissue() {
        return targetTissue;
    }

    public StringFilter targetTissue() {
        if (targetTissue == null) {
            targetTissue = new StringFilter();
        }
        return targetTissue;
    }

    public void setTargetTissue(StringFilter targetTissue) {
        this.targetTissue = targetTissue;
    }

    public StringFilter getVesselInfo() {
        return vesselInfo;
    }

    public StringFilter vesselInfo() {
        if (vesselInfo == null) {
            vesselInfo = new StringFilter();
        }
        return vesselInfo;
    }

    public void setVesselInfo(StringFilter vesselInfo) {
        this.vesselInfo = vesselInfo;
    }

    public StringFilter getProtocol() {
        return protocol;
    }

    public StringFilter protocol() {
        if (protocol == null) {
            protocol = new StringFilter();
        }
        return protocol;
    }

    public void setProtocol(StringFilter protocol) {
        this.protocol = protocol;
    }

    public StringFilter getManufacturer() {
        return manufacturer;
    }

    public StringFilter manufacturer() {
        if (manufacturer == null) {
            manufacturer = new StringFilter();
        }
        return manufacturer;
    }

    public void setManufacturer(StringFilter manufacturer) {
        this.manufacturer = manufacturer;
    }

    public StringFilter getMatrixType() {
        return matrixType;
    }

    public StringFilter matrixType() {
        if (matrixType == null) {
            matrixType = new StringFilter();
        }
        return matrixType;
    }

    public void setMatrixType(StringFilter matrixType) {
        this.matrixType = matrixType;
    }

    public StringFilter getVesselType() {
        return vesselType;
    }

    public StringFilter vesselType() {
        if (vesselType == null) {
            vesselType = new StringFilter();
        }
        return vesselType;
    }

    public void setVesselType(StringFilter vesselType) {
        this.vesselType = vesselType;
    }

    public BooleanFilter getInhouse() {
        return inhouse;
    }

    public BooleanFilter inhouse() {
        if (inhouse == null) {
            inhouse = new BooleanFilter();
        }
        return inhouse;
    }

    public void setInhouse(BooleanFilter inhouse) {
        this.inhouse = inhouse;
    }

    public IntegerFilter getRatingCharacterization() {
        return ratingCharacterization;
    }

    public IntegerFilter ratingCharacterization() {
        if (ratingCharacterization == null) {
            ratingCharacterization = new IntegerFilter();
        }
        return ratingCharacterization;
    }

    public void setRatingCharacterization(IntegerFilter ratingCharacterization) {
        this.ratingCharacterization = ratingCharacterization;
    }

    public IntegerFilter getRatingComplexity() {
        return ratingComplexity;
    }

    public IntegerFilter ratingComplexity() {
        if (ratingComplexity == null) {
            ratingComplexity = new IntegerFilter();
        }
        return ratingComplexity;
    }

    public void setRatingComplexity(IntegerFilter ratingComplexity) {
        this.ratingComplexity = ratingComplexity;
    }

    public IntegerFilter getRatingModelCost() {
        return ratingModelCost;
    }

    public IntegerFilter ratingModelCost() {
        if (ratingModelCost == null) {
            ratingModelCost = new IntegerFilter();
        }
        return ratingModelCost;
    }

    public void setRatingModelCost(IntegerFilter ratingModelCost) {
        this.ratingModelCost = ratingModelCost;
    }

    public IntegerFilter getRatingThroughPutCapacity() {
        return ratingThroughPutCapacity;
    }

    public IntegerFilter ratingThroughPutCapacity() {
        if (ratingThroughPutCapacity == null) {
            ratingThroughPutCapacity = new IntegerFilter();
        }
        return ratingThroughPutCapacity;
    }

    public void setRatingThroughPutCapacity(IntegerFilter ratingThroughPutCapacity) {
        this.ratingThroughPutCapacity = ratingThroughPutCapacity;
    }

    public IntegerFilter getRatingTurnAroundTime() {
        return ratingTurnAroundTime;
    }

    public IntegerFilter ratingTurnAroundTime() {
        if (ratingTurnAroundTime == null) {
            ratingTurnAroundTime = new IntegerFilter();
        }
        return ratingTurnAroundTime;
    }

    public void setRatingTurnAroundTime(IntegerFilter ratingTurnAroundTime) {
        this.ratingTurnAroundTime = ratingTurnAroundTime;
    }

    public StringFilter getImageUrl() {
        return imageUrl;
    }

    public StringFilter imageUrl() {
        if (imageUrl == null) {
            imageUrl = new StringFilter();
        }
        return imageUrl;
    }

    public void setImageUrl(StringFilter imageUrl) {
        this.imageUrl = imageUrl;
    }

    public StringFilter getDepartment() {
        return department;
    }

    public StringFilter department() {
        if (department == null) {
            department = new StringFilter();
        }
        return department;
    }

    public void setDepartment(StringFilter department) {
        this.department = department;
    }

    public StringFilter getModelState() {
        return modelState;
    }

    public StringFilter modelState() {
        if (modelState == null) {
            modelState = new StringFilter();
        }
        return modelState;
    }

    public void setModelState(StringFilter modelState) {
        this.modelState = modelState;
    }

    public BooleanFilter getApplicationADME() {
        return applicationADME;
    }

    public BooleanFilter applicationADME() {
        if (applicationADME == null) {
            applicationADME = new BooleanFilter();
        }
        return applicationADME;
    }

    public void setApplicationADME(BooleanFilter applicationADME) {
        this.applicationADME = applicationADME;
    }

    public BooleanFilter getApplicationEfficacy() {
        return applicationEfficacy;
    }

    public BooleanFilter applicationEfficacy() {
        if (applicationEfficacy == null) {
            applicationEfficacy = new BooleanFilter();
        }
        return applicationEfficacy;
    }

    public void setApplicationEfficacy(BooleanFilter applicationEfficacy) {
        this.applicationEfficacy = applicationEfficacy;
    }

    public BooleanFilter getApplicationNone() {
        return applicationNone;
    }

    public BooleanFilter applicationNone() {
        if (applicationNone == null) {
            applicationNone = new BooleanFilter();
        }
        return applicationNone;
    }

    public void setApplicationNone(BooleanFilter applicationNone) {
        this.applicationNone = applicationNone;
    }

    public BooleanFilter getApplicationSafety() {
        return applicationSafety;
    }

    public BooleanFilter applicationSafety() {
        if (applicationSafety == null) {
            applicationSafety = new BooleanFilter();
        }
        return applicationSafety;
    }

    public void setApplicationSafety(BooleanFilter applicationSafety) {
        this.applicationSafety = applicationSafety;
    }

    public StringFilter getAnimalAssayReplaced() {
        return animalAssayReplaced;
    }

    public StringFilter animalAssayReplaced() {
        if (animalAssayReplaced == null) {
            animalAssayReplaced = new StringFilter();
        }
        return animalAssayReplaced;
    }

    public void setAnimalAssayReplaced(StringFilter animalAssayReplaced) {
        this.animalAssayReplaced = animalAssayReplaced;
    }

    public StringFilter getNote() {
        return note;
    }

    public StringFilter note() {
        if (note == null) {
            note = new StringFilter();
        }
        return note;
    }

    public void setNote(StringFilter note) {
        this.note = note;
    }

    public StringFilter getExperts() {
        return experts;
    }

    public StringFilter experts() {
        if (experts == null) {
            experts = new StringFilter();
        }
        return experts;
    }

    public void setExperts(StringFilter experts) {
        this.experts = experts;
    }

    public StringFilter getLink() {
        return link;
    }

    public StringFilter link() {
        if (link == null) {
            link = new StringFilter();
        }
        return link;
    }

    public void setLink(StringFilter link) {
        this.link = link;
    }

    public StringFilter getLimitationPerceived() {
        return limitationPerceived;
    }

    public StringFilter limitationPerceived() {
        if (limitationPerceived == null) {
            limitationPerceived = new StringFilter();
        }
        return limitationPerceived;
    }

    public void setLimitationPerceived(StringFilter limitationPerceived) {
        this.limitationPerceived = limitationPerceived;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public LongFilter userId() {
        if (userId == null) {
            userId = new LongFilter();
        }
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getProjectSupportedId() {
        return projectSupportedId;
    }

    public LongFilter projectSupportedId() {
        if (projectSupportedId == null) {
            projectSupportedId = new LongFilter();
        }
        return projectSupportedId;
    }

    public void setProjectSupportedId(LongFilter projectSupportedId) {
        this.projectSupportedId = projectSupportedId;
    }

    public LongFilter getCellTypeId() {
        return cellTypeId;
    }

    public LongFilter cellTypeId() {
        if (cellTypeId == null) {
            cellTypeId = new LongFilter();
        }
        return cellTypeId;
    }

    public void setCellTypeId(LongFilter cellTypeId) {
        this.cellTypeId = cellTypeId;
    }

    public LongFilter getCommentId() {
        return commentId;
    }

    public LongFilter commentId() {
        if (commentId == null) {
            commentId = new LongFilter();
        }
        return commentId;
    }

    public void setCommentId(LongFilter commentId) {
        this.commentId = commentId;
    }

    public LongFilter getCharacterizationDataId() {
        return characterizationDataId;
    }

    public LongFilter characterizationDataId() {
        if (characterizationDataId == null) {
            characterizationDataId = new LongFilter();
        }
        return characterizationDataId;
    }

    public void setCharacterizationDataId(LongFilter characterizationDataId) {
        this.characterizationDataId = characterizationDataId;
    }

    public LongFilter getPublicationId() {
        return publicationId;
    }

    public LongFilter publicationId() {
        if (publicationId == null) {
            publicationId = new LongFilter();
        }
        return publicationId;
    }

    public void setPublicationId(LongFilter publicationId) {
        this.publicationId = publicationId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CellModelCriteria that = (CellModelCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(dateAdded, that.dateAdded) &&
            Objects.equals(dateUpdated, that.dateUpdated) &&
            Objects.equals(species, that.species) &&
            Objects.equals(targetOrgan, that.targetOrgan) &&
            Objects.equals(targetTissue, that.targetTissue) &&
            Objects.equals(vesselInfo, that.vesselInfo) &&
            Objects.equals(protocol, that.protocol) &&
            Objects.equals(manufacturer, that.manufacturer) &&
            Objects.equals(matrixType, that.matrixType) &&
            Objects.equals(vesselType, that.vesselType) &&
            Objects.equals(inhouse, that.inhouse) &&
            Objects.equals(ratingCharacterization, that.ratingCharacterization) &&
            Objects.equals(ratingComplexity, that.ratingComplexity) &&
            Objects.equals(ratingModelCost, that.ratingModelCost) &&
            Objects.equals(ratingThroughPutCapacity, that.ratingThroughPutCapacity) &&
            Objects.equals(ratingTurnAroundTime, that.ratingTurnAroundTime) &&
            Objects.equals(imageUrl, that.imageUrl) &&
            Objects.equals(department, that.department) &&
            Objects.equals(modelState, that.modelState) &&
            Objects.equals(applicationADME, that.applicationADME) &&
            Objects.equals(applicationEfficacy, that.applicationEfficacy) &&
            Objects.equals(applicationNone, that.applicationNone) &&
            Objects.equals(applicationSafety, that.applicationSafety) &&
            Objects.equals(animalAssayReplaced, that.animalAssayReplaced) &&
            Objects.equals(note, that.note) &&
            Objects.equals(experts, that.experts) &&
            Objects.equals(link, that.link) &&
            Objects.equals(limitationPerceived, that.limitationPerceived) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(projectSupportedId, that.projectSupportedId) &&
            Objects.equals(cellTypeId, that.cellTypeId) &&
            Objects.equals(commentId, that.commentId) &&
            Objects.equals(characterizationDataId, that.characterizationDataId) &&
            Objects.equals(publicationId, that.publicationId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            dateAdded,
            dateUpdated,
            species,
            targetOrgan,
            targetTissue,
            vesselInfo,
            protocol,
            manufacturer,
            matrixType,
            vesselType,
            inhouse,
            ratingCharacterization,
            ratingComplexity,
            ratingModelCost,
            ratingThroughPutCapacity,
            ratingTurnAroundTime,
            imageUrl,
            department,
            modelState,
            applicationADME,
            applicationEfficacy,
            applicationNone,
            applicationSafety,
            animalAssayReplaced,
            note,
            experts,
            link,
            limitationPerceived,
            userId,
            projectSupportedId,
            cellTypeId,
            commentId,
            characterizationDataId,
            publicationId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CellModelCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (dateAdded != null ? "dateAdded=" + dateAdded + ", " : "") +
            (dateUpdated != null ? "dateUpdated=" + dateUpdated + ", " : "") +
            (species != null ? "species=" + species + ", " : "") +
            (targetOrgan != null ? "targetOrgan=" + targetOrgan + ", " : "") +
            (targetTissue != null ? "targetTissue=" + targetTissue + ", " : "") +
            (vesselInfo != null ? "vesselInfo=" + vesselInfo + ", " : "") +
            (protocol != null ? "protocol=" + protocol + ", " : "") +
            (manufacturer != null ? "manufacturer=" + manufacturer + ", " : "") +
            (matrixType != null ? "matrixType=" + matrixType + ", " : "") +
            (vesselType != null ? "vesselType=" + vesselType + ", " : "") +
            (inhouse != null ? "inhouse=" + inhouse + ", " : "") +
            (ratingCharacterization != null ? "ratingCharacterization=" + ratingCharacterization + ", " : "") +
            (ratingComplexity != null ? "ratingComplexity=" + ratingComplexity + ", " : "") +
            (ratingModelCost != null ? "ratingModelCost=" + ratingModelCost + ", " : "") +
            (ratingThroughPutCapacity != null ? "ratingThroughPutCapacity=" + ratingThroughPutCapacity + ", " : "") +
            (ratingTurnAroundTime != null ? "ratingTurnAroundTime=" + ratingTurnAroundTime + ", " : "") +
            (imageUrl != null ? "imageUrl=" + imageUrl + ", " : "") +
            (department != null ? "department=" + department + ", " : "") +
            (modelState != null ? "modelState=" + modelState + ", " : "") +
            (applicationADME != null ? "applicationADME=" + applicationADME + ", " : "") +
            (applicationEfficacy != null ? "applicationEfficacy=" + applicationEfficacy + ", " : "") +
            (applicationNone != null ? "applicationNone=" + applicationNone + ", " : "") +
            (applicationSafety != null ? "applicationSafety=" + applicationSafety + ", " : "") +
            (animalAssayReplaced != null ? "animalAssayReplaced=" + animalAssayReplaced + ", " : "") +
            (note != null ? "note=" + note + ", " : "") +
            (experts != null ? "experts=" + experts + ", " : "") +
            (link != null ? "link=" + link + ", " : "") +
            (limitationPerceived != null ? "limitationPerceived=" + limitationPerceived + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            (projectSupportedId != null ? "projectSupportedId=" + projectSupportedId + ", " : "") +
            (cellTypeId != null ? "cellTypeId=" + cellTypeId + ", " : "") +
            (commentId != null ? "commentId=" + commentId + ", " : "") +
            (characterizationDataId != null ? "characterizationDataId=" + characterizationDataId + ", " : "") +
            (publicationId != null ? "publicationId=" + publicationId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
