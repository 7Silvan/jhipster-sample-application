package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.ProjectSupported} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProjectSupportedDTO implements Serializable {

    private Long id;

    @NotNull
    private String decisionMaking;

    @NotNull
    private Boolean isRegulatory;

    @NotNull
    private String projectNameOrThemeNumber;

    private CellModelDTO cellModel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDecisionMaking() {
        return decisionMaking;
    }

    public void setDecisionMaking(String decisionMaking) {
        this.decisionMaking = decisionMaking;
    }

    public Boolean getIsRegulatory() {
        return isRegulatory;
    }

    public void setIsRegulatory(Boolean isRegulatory) {
        this.isRegulatory = isRegulatory;
    }

    public String getProjectNameOrThemeNumber() {
        return projectNameOrThemeNumber;
    }

    public void setProjectNameOrThemeNumber(String projectNameOrThemeNumber) {
        this.projectNameOrThemeNumber = projectNameOrThemeNumber;
    }

    public CellModelDTO getCellModel() {
        return cellModel;
    }

    public void setCellModel(CellModelDTO cellModel) {
        this.cellModel = cellModel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectSupportedDTO)) {
            return false;
        }

        ProjectSupportedDTO projectSupportedDTO = (ProjectSupportedDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, projectSupportedDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectSupportedDTO{" +
            "id=" + getId() +
            ", decisionMaking='" + getDecisionMaking() + "'" +
            ", isRegulatory='" + getIsRegulatory() + "'" +
            ", projectNameOrThemeNumber='" + getProjectNameOrThemeNumber() + "'" +
            ", cellModel=" + getCellModel() +
            "}";
    }
}
