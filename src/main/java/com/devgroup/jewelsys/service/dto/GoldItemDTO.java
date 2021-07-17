package com.devgroup.jewelsys.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.devgroup.jewelsys.domain.GoldItem} entity.
 */
public class GoldItemDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    private String name;

    private String delFlg;

    private GoldItemGroupDTO goldItemGroup;

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

    public GoldItemGroupDTO getGoldItemGroup() {
        return goldItemGroup;
    }

    public void setGoldItemGroup(GoldItemGroupDTO goldItemGroup) {
        this.goldItemGroup = goldItemGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GoldItemDTO)) {
            return false;
        }

        GoldItemDTO goldItemDTO = (GoldItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, goldItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GoldItemDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", delFlg='" + getDelFlg() + "'" +
            ", goldItemGroup=" + getGoldItemGroup() +
            "}";
    }
}
