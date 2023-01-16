package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CellModel} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CellModelDTO implements Serializable {

    private String id;

    private ZonedDateTime dateAdded;

    private ZonedDateTime dateUpdated;

    @NotNull
    private String species;

    @NotNull
    private String targetOrgan;

    @NotNull
    private String targetTissue;

    @NotNull
    private String vesselInfo;

    private String protocol;

    private String manufacturer;

    private String matrixType;

    private String vesselType;

    private Boolean inhouse;

    @NotNull
    private Integer ratingCharacterization;

    @NotNull
    private Integer ratingComplexity;

    @NotNull
    private Integer ratingModelCost;

    @NotNull
    private Integer ratingThroughPutCapacity;

    @NotNull
    private Integer ratingTurnAroundTime;

    private String imageUrl;

    @NotNull
    private String department;

    @NotNull
    private String modelState;

    @NotNull
    private Boolean applicationADME;

    @NotNull
    private Boolean applicationEfficacy;

    @NotNull
    private Boolean applicationNone;

    @NotNull
    private Boolean applicationSafety;

    private String animalAssayReplaced;

    private String note;

    private String experts;

    private String link;

    private String limitationPerceived;

    private UserDTO user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ZonedDateTime getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(ZonedDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    public ZonedDateTime getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(ZonedDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getTargetOrgan() {
        return targetOrgan;
    }

    public void setTargetOrgan(String targetOrgan) {
        this.targetOrgan = targetOrgan;
    }

    public String getTargetTissue() {
        return targetTissue;
    }

    public void setTargetTissue(String targetTissue) {
        this.targetTissue = targetTissue;
    }

    public String getVesselInfo() {
        return vesselInfo;
    }

    public void setVesselInfo(String vesselInfo) {
        this.vesselInfo = vesselInfo;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getMatrixType() {
        return matrixType;
    }

    public void setMatrixType(String matrixType) {
        this.matrixType = matrixType;
    }

    public String getVesselType() {
        return vesselType;
    }

    public void setVesselType(String vesselType) {
        this.vesselType = vesselType;
    }

    public Boolean getInhouse() {
        return inhouse;
    }

    public void setInhouse(Boolean inhouse) {
        this.inhouse = inhouse;
    }

    public Integer getRatingCharacterization() {
        return ratingCharacterization;
    }

    public void setRatingCharacterization(Integer ratingCharacterization) {
        this.ratingCharacterization = ratingCharacterization;
    }

    public Integer getRatingComplexity() {
        return ratingComplexity;
    }

    public void setRatingComplexity(Integer ratingComplexity) {
        this.ratingComplexity = ratingComplexity;
    }

    public Integer getRatingModelCost() {
        return ratingModelCost;
    }

    public void setRatingModelCost(Integer ratingModelCost) {
        this.ratingModelCost = ratingModelCost;
    }

    public Integer getRatingThroughPutCapacity() {
        return ratingThroughPutCapacity;
    }

    public void setRatingThroughPutCapacity(Integer ratingThroughPutCapacity) {
        this.ratingThroughPutCapacity = ratingThroughPutCapacity;
    }

    public Integer getRatingTurnAroundTime() {
        return ratingTurnAroundTime;
    }

    public void setRatingTurnAroundTime(Integer ratingTurnAroundTime) {
        this.ratingTurnAroundTime = ratingTurnAroundTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getModelState() {
        return modelState;
    }

    public void setModelState(String modelState) {
        this.modelState = modelState;
    }

    public Boolean getApplicationADME() {
        return applicationADME;
    }

    public void setApplicationADME(Boolean applicationADME) {
        this.applicationADME = applicationADME;
    }

    public Boolean getApplicationEfficacy() {
        return applicationEfficacy;
    }

    public void setApplicationEfficacy(Boolean applicationEfficacy) {
        this.applicationEfficacy = applicationEfficacy;
    }

    public Boolean getApplicationNone() {
        return applicationNone;
    }

    public void setApplicationNone(Boolean applicationNone) {
        this.applicationNone = applicationNone;
    }

    public Boolean getApplicationSafety() {
        return applicationSafety;
    }

    public void setApplicationSafety(Boolean applicationSafety) {
        this.applicationSafety = applicationSafety;
    }

    public String getAnimalAssayReplaced() {
        return animalAssayReplaced;
    }

    public void setAnimalAssayReplaced(String animalAssayReplaced) {
        this.animalAssayReplaced = animalAssayReplaced;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getExperts() {
        return experts;
    }

    public void setExperts(String experts) {
        this.experts = experts;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLimitationPerceived() {
        return limitationPerceived;
    }

    public void setLimitationPerceived(String limitationPerceived) {
        this.limitationPerceived = limitationPerceived;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CellModelDTO)) {
            return false;
        }

        CellModelDTO cellModelDTO = (CellModelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cellModelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CellModelDTO{" +
            "id='" + getId() + "'" +
            ", dateAdded='" + getDateAdded() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", species='" + getSpecies() + "'" +
            ", targetOrgan='" + getTargetOrgan() + "'" +
            ", targetTissue='" + getTargetTissue() + "'" +
            ", vesselInfo='" + getVesselInfo() + "'" +
            ", protocol='" + getProtocol() + "'" +
            ", manufacturer='" + getManufacturer() + "'" +
            ", matrixType='" + getMatrixType() + "'" +
            ", vesselType='" + getVesselType() + "'" +
            ", inhouse='" + getInhouse() + "'" +
            ", ratingCharacterization=" + getRatingCharacterization() +
            ", ratingComplexity=" + getRatingComplexity() +
            ", ratingModelCost=" + getRatingModelCost() +
            ", ratingThroughPutCapacity=" + getRatingThroughPutCapacity() +
            ", ratingTurnAroundTime=" + getRatingTurnAroundTime() +
            ", imageUrl='" + getImageUrl() + "'" +
            ", department='" + getDepartment() + "'" +
            ", modelState='" + getModelState() + "'" +
            ", applicationADME='" + getApplicationADME() + "'" +
            ", applicationEfficacy='" + getApplicationEfficacy() + "'" +
            ", applicationNone='" + getApplicationNone() + "'" +
            ", applicationSafety='" + getApplicationSafety() + "'" +
            ", animalAssayReplaced='" + getAnimalAssayReplaced() + "'" +
            ", note='" + getNote() + "'" +
            ", experts='" + getExperts() + "'" +
            ", link='" + getLink() + "'" +
            ", limitationPerceived='" + getLimitationPerceived() + "'" +
            ", user=" + getUser() +
            "}";
    }
}
