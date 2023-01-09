package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CharacterizationData} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CharacterizationDataDTO implements Serializable {

    private Long id;

    @NotNull
    private String description;

    @NotNull
    private String link;

    private CellModelDTO cellModel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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
        if (!(o instanceof CharacterizationDataDTO)) {
            return false;
        }

        CharacterizationDataDTO characterizationDataDTO = (CharacterizationDataDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, characterizationDataDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CharacterizationDataDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", link='" + getLink() + "'" +
            ", cellModel=" + getCellModel() +
            "}";
    }
}
