package com.devgroup.jewelsys.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.devgroup.jewelsys.domain.DataCategory} entity.
 */
public class DataCategoryDTO implements Serializable {

    private Long id;

    private String categoryType;

    private String categoryCode;

    private String value;

    private String categoryDesc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataCategoryDTO)) {
            return false;
        }

        DataCategoryDTO dataCategoryDTO = (DataCategoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dataCategoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DataCategoryDTO{" +
            "id=" + getId() +
            ", categoryType='" + getCategoryType() + "'" +
            ", categoryCode='" + getCategoryCode() + "'" +
            ", value='" + getValue() + "'" +
            ", categoryDesc='" + getCategoryDesc() + "'" +
            "}";
    }
}
