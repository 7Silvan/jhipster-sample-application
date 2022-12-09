package com.mycompany.myapp.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CellType} entity.
 */
@Schema(description = "not an ignored comment")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CellTypeDTO implements Serializable {

    private Long id;

    @NotNull
    private String cellSourceInfo;

    @NotNull
    private String cellArchitecture;

    @NotNull
    private String vendor;

    private CellModelDTO cellModel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCellSourceInfo() {
        return cellSourceInfo;
    }

    public void setCellSourceInfo(String cellSourceInfo) {
        this.cellSourceInfo = cellSourceInfo;
    }

    public String getCellArchitecture() {
        return cellArchitecture;
    }

    public void setCellArchitecture(String cellArchitecture) {
        this.cellArchitecture = cellArchitecture;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
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
        if (!(o instanceof CellTypeDTO)) {
            return false;
        }

        CellTypeDTO cellTypeDTO = (CellTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cellTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CellTypeDTO{" +
            "id=" + getId() +
            ", cellSourceInfo='" + getCellSourceInfo() + "'" +
            ", cellArchitecture='" + getCellArchitecture() + "'" +
            ", vendor='" + getVendor() + "'" +
            ", cellModel=" + getCellModel() +
            "}";
    }
}
