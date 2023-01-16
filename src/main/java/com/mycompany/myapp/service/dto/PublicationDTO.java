package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Publication} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PublicationDTO implements Serializable {

    private Long id;

    @NotNull
    private String link;

    private CellModelDTO cellModel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(o instanceof PublicationDTO)) {
            return false;
        }

        PublicationDTO publicationDTO = (PublicationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, publicationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PublicationDTO{" +
            "id=" + getId() +
            ", link='" + getLink() + "'" +
            ", cellModel=" + getCellModel() +
            "}";
    }
}
