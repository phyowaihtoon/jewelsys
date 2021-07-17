package com.devgroup.jewelsys.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.devgroup.jewelsys.domain.MenuGroup} entity.
 */
public class MenuGroupDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Integer sequenceNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuGroupDTO)) {
            return false;
        }

        MenuGroupDTO menuGroupDTO = (MenuGroupDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, menuGroupDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MenuGroupDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", sequenceNo=" + getSequenceNo() +
            "}";
    }
}
