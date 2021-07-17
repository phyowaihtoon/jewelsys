package com.devgroup.jewelsys.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.devgroup.jewelsys.domain.GoldItemGroup} entity.
 */
public class GoldItemGroupDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    private String name;

    private String delFlg;

    private GoldTypeDTO goldType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    public GoldTypeDTO getGoldType() {
        return goldType;
    }

    public void setGoldType(GoldTypeDTO goldType) {
        this.goldType = goldType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GoldItemGroupDTO)) {
            return false;
        }

        GoldItemGroupDTO goldItemGroupDTO = (GoldItemGroupDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, goldItemGroupDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GoldItemGroupDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", delFlg='" + getDelFlg() + "'" +
            ", goldType=" + getGoldType() +
            "}";
    }
}
