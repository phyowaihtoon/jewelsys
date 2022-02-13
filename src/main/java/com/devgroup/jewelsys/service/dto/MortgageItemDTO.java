package com.devgroup.jewelsys.service.dto;

import com.devgroup.jewelsys.domain.enumeration.MortgageItemGroup;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.devgroup.jewelsys.domain.MortgageItem} entity.
 */
public class MortgageItemDTO implements Serializable {

    private Long id;

    @NotNull
    private MortgageItemGroup groupCode;

    @NotNull
    private String code;

    @NotNull
    private String itemName;

    private String delFlg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MortgageItemGroup getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(MortgageItemGroup groupCode) {
        this.groupCode = groupCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MortgageItemDTO)) {
            return false;
        }

        MortgageItemDTO mortgageItemDTO = (MortgageItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, mortgageItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MortgageItemDTO{" +
            "id=" + getId() +
            ", groupCode='" + getGroupCode() + "'" +
            ", code='" + getCode() + "'" +
            ", itemName='" + getItemName() + "'" +
            ", delFlg='" + getDelFlg() + "'" +
            "}";
    }
}
