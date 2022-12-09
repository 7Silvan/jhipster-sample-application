package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

/**
 * A CellModel.
 */
@JsonIgnoreProperties(value = { "new" })
@Entity
@Table(name = "cell_model")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CellModel implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "date_added")
    private ZonedDateTime dateAdded;

    @Column(name = "date_updated")
    private ZonedDateTime dateUpdated;

    @NotNull
    @Column(name = "species", nullable = false)
    private String species;

    @NotNull
    @Column(name = "target_organ", nullable = false)
    private String targetOrgan;

    @NotNull
    @Column(name = "target_tissue", nullable = false)
    private String targetTissue;

    @NotNull
    @Column(name = "vessel_info", nullable = false)
    private String vesselInfo;

    @Column(name = "matrix")
    private Boolean matrix;

    @Column(name = "protocol")
    private String protocol;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "matrix_type")
    private String matrixType;

    @Column(name = "vessel_type")
    private String vesselType;

    @Column(name = "inhouse")
    private Boolean inhouse;

    @Column(name = "characterization_data")
    private String characterizationData;

    @NotNull
    @Column(name = "rating_characterization", nullable = false)
    private Integer ratingCharacterization;

    @NotNull
    @Column(name = "rating_complexity", nullable = false)
    private Integer ratingComplexity;

    @NotNull
    @Column(name = "rating_model_cost", nullable = false)
    private Integer ratingModelCost;

    @NotNull
    @Column(name = "rating_through_put_capacity", nullable = false)
    private Integer ratingThroughPutCapacity;

    @NotNull
    @Column(name = "rating_turn_around_time", nullable = false)
    private Integer ratingTurnAroundTime;

    @Column(name = "image_url")
    private String imageUrl;

    @NotNull
    @Column(name = "department", nullable = false)
    private String department;

    @NotNull
    @Column(name = "model_state", nullable = false)
    private String modelState;

    @NotNull
    @Column(name = "application_adme", nullable = false)
    private Boolean applicationADME;

    @NotNull
    @Column(name = "application_efficacy", nullable = false)
    private Boolean applicationEfficacy;

    @NotNull
    @Column(name = "application_none", nullable = false)
    private Boolean applicationNone;

    @NotNull
    @Column(name = "application_safety", nullable = false)
    private Boolean applicationSafety;

    @Column(name = "animal_assay_replaced")
    private String animalAssayReplaced;

    @Column(name = "comment")
    private String comment;

    @Column(name = "experts")
    private String experts;

    @Column(name = "link")
    private String link;

    @NotNull
    @Column(name = "is_regulatory_submission", nullable = false)
    private Boolean isRegulatorySubmission;

    @Column(name = "publications")
    private String publications;

    @Column(name = "limitation_percieved")
    private String limitationPercieved;

    @Transient
    private boolean isPersisted;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "cellModel")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cellModel" }, allowSetters = true)
    private Set<ProjectSupported> projectSupporteds = new HashSet<>();

    @OneToMany(mappedBy = "cellModel")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cellModel" }, allowSetters = true)
    private Set<CellType> cellTypes = new HashSet<>();

    @OneToMany(mappedBy = "cellModel")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cellModel", "user" }, allowSetters = true)
    private Set<Comment> comments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public CellModel id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public CellModel name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getDateAdded() {
        return this.dateAdded;
    }

    public CellModel dateAdded(ZonedDateTime dateAdded) {
        this.setDateAdded(dateAdded);
        return this;
    }

    public void setDateAdded(ZonedDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    public ZonedDateTime getDateUpdated() {
        return this.dateUpdated;
    }

    public CellModel dateUpdated(ZonedDateTime dateUpdated) {
        this.setDateUpdated(dateUpdated);
        return this;
    }

    public void setDateUpdated(ZonedDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getSpecies() {
        return this.species;
    }

    public CellModel species(String species) {
        this.setSpecies(species);
        return this;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getTargetOrgan() {
        return this.targetOrgan;
    }

    public CellModel targetOrgan(String targetOrgan) {
        this.setTargetOrgan(targetOrgan);
        return this;
    }

    public void setTargetOrgan(String targetOrgan) {
        this.targetOrgan = targetOrgan;
    }

    public String getTargetTissue() {
        return this.targetTissue;
    }

    public CellModel targetTissue(String targetTissue) {
        this.setTargetTissue(targetTissue);
        return this;
    }

    public void setTargetTissue(String targetTissue) {
        this.targetTissue = targetTissue;
    }

    public String getVesselInfo() {
        return this.vesselInfo;
    }

    public CellModel vesselInfo(String vesselInfo) {
        this.setVesselInfo(vesselInfo);
        return this;
    }

    public void setVesselInfo(String vesselInfo) {
        this.vesselInfo = vesselInfo;
    }

    public Boolean getMatrix() {
        return this.matrix;
    }

    public CellModel matrix(Boolean matrix) {
        this.setMatrix(matrix);
        return this;
    }

    public void setMatrix(Boolean matrix) {
        this.matrix = matrix;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public CellModel protocol(String protocol) {
        this.setProtocol(protocol);
        return this;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public CellModel manufacturer(String manufacturer) {
        this.setManufacturer(manufacturer);
        return this;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getMatrixType() {
        return this.matrixType;
    }

    public CellModel matrixType(String matrixType) {
        this.setMatrixType(matrixType);
        return this;
    }

    public void setMatrixType(String matrixType) {
        this.matrixType = matrixType;
    }

    public String getVesselType() {
        return this.vesselType;
    }

    public CellModel vesselType(String vesselType) {
        this.setVesselType(vesselType);
        return this;
    }

    public void setVesselType(String vesselType) {
        this.vesselType = vesselType;
    }

    public Boolean getInhouse() {
        return this.inhouse;
    }

    public CellModel inhouse(Boolean inhouse) {
        this.setInhouse(inhouse);
        return this;
    }

    public void setInhouse(Boolean inhouse) {
        this.inhouse = inhouse;
    }

    public String getCharacterizationData() {
        return this.characterizationData;
    }

    public CellModel characterizationData(String characterizationData) {
        this.setCharacterizationData(characterizationData);
        return this;
    }

    public void setCharacterizationData(String characterizationData) {
        this.characterizationData = characterizationData;
    }

    public Integer getRatingCharacterization() {
        return this.ratingCharacterization;
    }

    public CellModel ratingCharacterization(Integer ratingCharacterization) {
        this.setRatingCharacterization(ratingCharacterization);
        return this;
    }

    public void setRatingCharacterization(Integer ratingCharacterization) {
        this.ratingCharacterization = ratingCharacterization;
    }

    public Integer getRatingComplexity() {
        return this.ratingComplexity;
    }

    public CellModel ratingComplexity(Integer ratingComplexity) {
        this.setRatingComplexity(ratingComplexity);
        return this;
    }

    public void setRatingComplexity(Integer ratingComplexity) {
        this.ratingComplexity = ratingComplexity;
    }

    public Integer getRatingModelCost() {
        return this.ratingModelCost;
    }

    public CellModel ratingModelCost(Integer ratingModelCost) {
        this.setRatingModelCost(ratingModelCost);
        return this;
    }

    public void setRatingModelCost(Integer ratingModelCost) {
        this.ratingModelCost = ratingModelCost;
    }

    public Integer getRatingThroughPutCapacity() {
        return this.ratingThroughPutCapacity;
    }

    public CellModel ratingThroughPutCapacity(Integer ratingThroughPutCapacity) {
        this.setRatingThroughPutCapacity(ratingThroughPutCapacity);
        return this;
    }

    public void setRatingThroughPutCapacity(Integer ratingThroughPutCapacity) {
        this.ratingThroughPutCapacity = ratingThroughPutCapacity;
    }

    public Integer getRatingTurnAroundTime() {
        return this.ratingTurnAroundTime;
    }

    public CellModel ratingTurnAroundTime(Integer ratingTurnAroundTime) {
        this.setRatingTurnAroundTime(ratingTurnAroundTime);
        return this;
    }

    public void setRatingTurnAroundTime(Integer ratingTurnAroundTime) {
        this.ratingTurnAroundTime = ratingTurnAroundTime;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public CellModel imageUrl(String imageUrl) {
        this.setImageUrl(imageUrl);
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDepartment() {
        return this.department;
    }

    public CellModel department(String department) {
        this.setDepartment(department);
        return this;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getModelState() {
        return this.modelState;
    }

    public CellModel modelState(String modelState) {
        this.setModelState(modelState);
        return this;
    }

    public void setModelState(String modelState) {
        this.modelState = modelState;
    }

    public Boolean getApplicationADME() {
        return this.applicationADME;
    }

    public CellModel applicationADME(Boolean applicationADME) {
        this.setApplicationADME(applicationADME);
        return this;
    }

    public void setApplicationADME(Boolean applicationADME) {
        this.applicationADME = applicationADME;
    }

    public Boolean getApplicationEfficacy() {
        return this.applicationEfficacy;
    }

    public CellModel applicationEfficacy(Boolean applicationEfficacy) {
        this.setApplicationEfficacy(applicationEfficacy);
        return this;
    }

    public void setApplicationEfficacy(Boolean applicationEfficacy) {
        this.applicationEfficacy = applicationEfficacy;
    }

    public Boolean getApplicationNone() {
        return this.applicationNone;
    }

    public CellModel applicationNone(Boolean applicationNone) {
        this.setApplicationNone(applicationNone);
        return this;
    }

    public void setApplicationNone(Boolean applicationNone) {
        this.applicationNone = applicationNone;
    }

    public Boolean getApplicationSafety() {
        return this.applicationSafety;
    }

    public CellModel applicationSafety(Boolean applicationSafety) {
        this.setApplicationSafety(applicationSafety);
        return this;
    }

    public void setApplicationSafety(Boolean applicationSafety) {
        this.applicationSafety = applicationSafety;
    }

    public String getAnimalAssayReplaced() {
        return this.animalAssayReplaced;
    }

    public CellModel animalAssayReplaced(String animalAssayReplaced) {
        this.setAnimalAssayReplaced(animalAssayReplaced);
        return this;
    }

    public void setAnimalAssayReplaced(String animalAssayReplaced) {
        this.animalAssayReplaced = animalAssayReplaced;
    }

    public String getComment() {
        return this.comment;
    }

    public CellModel comment(String comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getExperts() {
        return this.experts;
    }

    public CellModel experts(String experts) {
        this.setExperts(experts);
        return this;
    }

    public void setExperts(String experts) {
        this.experts = experts;
    }

    public String getLink() {
        return this.link;
    }

    public CellModel link(String link) {
        this.setLink(link);
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Boolean getIsRegulatorySubmission() {
        return this.isRegulatorySubmission;
    }

    public CellModel isRegulatorySubmission(Boolean isRegulatorySubmission) {
        this.setIsRegulatorySubmission(isRegulatorySubmission);
        return this;
    }

    public void setIsRegulatorySubmission(Boolean isRegulatorySubmission) {
        this.isRegulatorySubmission = isRegulatorySubmission;
    }

    public String getPublications() {
        return this.publications;
    }

    public CellModel publications(String publications) {
        this.setPublications(publications);
        return this;
    }

    public void setPublications(String publications) {
        this.publications = publications;
    }

    public String getLimitationPercieved() {
        return this.limitationPercieved;
    }

    public CellModel limitationPercieved(String limitationPercieved) {
        this.setLimitationPercieved(limitationPercieved);
        return this;
    }

    public void setLimitationPercieved(String limitationPercieved) {
        this.limitationPercieved = limitationPercieved;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public CellModel setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CellModel user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<ProjectSupported> getProjectSupporteds() {
        return this.projectSupporteds;
    }

    public void setProjectSupporteds(Set<ProjectSupported> projectSupporteds) {
        if (this.projectSupporteds != null) {
            this.projectSupporteds.forEach(i -> i.setCellModel(null));
        }
        if (projectSupporteds != null) {
            projectSupporteds.forEach(i -> i.setCellModel(this));
        }
        this.projectSupporteds = projectSupporteds;
    }

    public CellModel projectSupporteds(Set<ProjectSupported> projectSupporteds) {
        this.setProjectSupporteds(projectSupporteds);
        return this;
    }

    public CellModel addProjectSupported(ProjectSupported projectSupported) {
        this.projectSupporteds.add(projectSupported);
        projectSupported.setCellModel(this);
        return this;
    }

    public CellModel removeProjectSupported(ProjectSupported projectSupported) {
        this.projectSupporteds.remove(projectSupported);
        projectSupported.setCellModel(null);
        return this;
    }

    public Set<CellType> getCellTypes() {
        return this.cellTypes;
    }

    public void setCellTypes(Set<CellType> cellTypes) {
        if (this.cellTypes != null) {
            this.cellTypes.forEach(i -> i.setCellModel(null));
        }
        if (cellTypes != null) {
            cellTypes.forEach(i -> i.setCellModel(this));
        }
        this.cellTypes = cellTypes;
    }

    public CellModel cellTypes(Set<CellType> cellTypes) {
        this.setCellTypes(cellTypes);
        return this;
    }

    public CellModel addCellType(CellType cellType) {
        this.cellTypes.add(cellType);
        cellType.setCellModel(this);
        return this;
    }

    public CellModel removeCellType(CellType cellType) {
        this.cellTypes.remove(cellType);
        cellType.setCellModel(null);
        return this;
    }

    public Set<Comment> getComments() {
        return this.comments;
    }

    public void setComments(Set<Comment> comments) {
        if (this.comments != null) {
            this.comments.forEach(i -> i.setCellModel(null));
        }
        if (comments != null) {
            comments.forEach(i -> i.setCellModel(this));
        }
        this.comments = comments;
    }

    public CellModel comments(Set<Comment> comments) {
        this.setComments(comments);
        return this;
    }

    public CellModel addComment(Comment comment) {
        this.comments.add(comment);
        comment.setCellModel(this);
        return this;
    }

    public CellModel removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setCellModel(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CellModel)) {
            return false;
        }
        return id != null && id.equals(((CellModel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CellModel{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", dateAdded='" + getDateAdded() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", species='" + getSpecies() + "'" +
            ", targetOrgan='" + getTargetOrgan() + "'" +
            ", targetTissue='" + getTargetTissue() + "'" +
            ", vesselInfo='" + getVesselInfo() + "'" +
            ", matrix='" + getMatrix() + "'" +
            ", protocol='" + getProtocol() + "'" +
            ", manufacturer='" + getManufacturer() + "'" +
            ", matrixType='" + getMatrixType() + "'" +
            ", vesselType='" + getVesselType() + "'" +
            ", inhouse='" + getInhouse() + "'" +
            ", characterizationData='" + getCharacterizationData() + "'" +
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
            ", comment='" + getComment() + "'" +
            ", experts='" + getExperts() + "'" +
            ", link='" + getLink() + "'" +
            ", isRegulatorySubmission='" + getIsRegulatorySubmission() + "'" +
            ", publications='" + getPublications() + "'" +
            ", limitationPercieved='" + getLimitationPercieved() + "'" +
            "}";
    }
}
