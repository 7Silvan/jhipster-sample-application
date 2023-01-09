package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProjectSupported.
 */
@Entity
@Table(name = "project_supported")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProjectSupported implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "decision_making", nullable = false)
    private String decisionMaking;

    @NotNull
    @Column(name = "is_regulatory", nullable = false)
    private Boolean isRegulatory;

    @NotNull
    @Column(name = "project_name_or_theme_number", nullable = false)
    private String projectNameOrThemeNumber;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "user", "projectSupporteds", "cellTypes", "comments", "characterizationData", "publications" },
        allowSetters = true
    )
    private CellModel cellModel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProjectSupported id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDecisionMaking() {
        return this.decisionMaking;
    }

    public ProjectSupported decisionMaking(String decisionMaking) {
        this.setDecisionMaking(decisionMaking);
        return this;
    }

    public void setDecisionMaking(String decisionMaking) {
        this.decisionMaking = decisionMaking;
    }

    public Boolean getIsRegulatory() {
        return this.isRegulatory;
    }

    public ProjectSupported isRegulatory(Boolean isRegulatory) {
        this.setIsRegulatory(isRegulatory);
        return this;
    }

    public void setIsRegulatory(Boolean isRegulatory) {
        this.isRegulatory = isRegulatory;
    }

    public String getProjectNameOrThemeNumber() {
        return this.projectNameOrThemeNumber;
    }

    public ProjectSupported projectNameOrThemeNumber(String projectNameOrThemeNumber) {
        this.setProjectNameOrThemeNumber(projectNameOrThemeNumber);
        return this;
    }

    public void setProjectNameOrThemeNumber(String projectNameOrThemeNumber) {
        this.projectNameOrThemeNumber = projectNameOrThemeNumber;
    }

    public CellModel getCellModel() {
        return this.cellModel;
    }

    public void setCellModel(CellModel cellModel) {
        this.cellModel = cellModel;
    }

    public ProjectSupported cellModel(CellModel cellModel) {
        this.setCellModel(cellModel);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectSupported)) {
            return false;
        }
        return id != null && id.equals(((ProjectSupported) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectSupported{" +
            "id=" + getId() +
            ", decisionMaking='" + getDecisionMaking() + "'" +
            ", isRegulatory='" + getIsRegulatory() + "'" +
            ", projectNameOrThemeNumber='" + getProjectNameOrThemeNumber() + "'" +
            "}";
    }
}
