package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * not an ignored comment
 */
@Entity
@Table(name = "cell_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CellType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "cell_source_info", nullable = false)
    private String cellSourceInfo;

    @NotNull
    @Column(name = "cell_architecture", nullable = false)
    private String cellArchitecture;

    @NotNull
    @Column(name = "vendor", nullable = false)
    private String vendor;

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

    public CellType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCellSourceInfo() {
        return this.cellSourceInfo;
    }

    public CellType cellSourceInfo(String cellSourceInfo) {
        this.setCellSourceInfo(cellSourceInfo);
        return this;
    }

    public void setCellSourceInfo(String cellSourceInfo) {
        this.cellSourceInfo = cellSourceInfo;
    }

    public String getCellArchitecture() {
        return this.cellArchitecture;
    }

    public CellType cellArchitecture(String cellArchitecture) {
        this.setCellArchitecture(cellArchitecture);
        return this;
    }

    public void setCellArchitecture(String cellArchitecture) {
        this.cellArchitecture = cellArchitecture;
    }

    public String getVendor() {
        return this.vendor;
    }

    public CellType vendor(String vendor) {
        this.setVendor(vendor);
        return this;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public CellModel getCellModel() {
        return this.cellModel;
    }

    public void setCellModel(CellModel cellModel) {
        this.cellModel = cellModel;
    }

    public CellType cellModel(CellModel cellModel) {
        this.setCellModel(cellModel);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CellType)) {
            return false;
        }
        return id != null && id.equals(((CellType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CellType{" +
            "id=" + getId() +
            ", cellSourceInfo='" + getCellSourceInfo() + "'" +
            ", cellArchitecture='" + getCellArchitecture() + "'" +
            ", vendor='" + getVendor() + "'" +
            "}";
    }
}
