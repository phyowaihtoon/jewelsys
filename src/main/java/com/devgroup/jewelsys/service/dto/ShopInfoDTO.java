package com.devgroup.jewelsys.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.devgroup.jewelsys.domain.ShopInfo} entity.
 */
public class ShopInfoDTO implements Serializable {

    private Long id;

    @NotNull
    private String shopCode;

    @NotNull
    private String nameEng;

    private String nameMyan;

    private String addrEng;

    private String addrMyan;

    private String phone;

    private String delFlg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public String getNameMyan() {
        return nameMyan;
    }

    public void setNameMyan(String nameMyan) {
        this.nameMyan = nameMyan;
    }

    public String getAddrEng() {
        return addrEng;
    }

    public void setAddrEng(String addrEng) {
        this.addrEng = addrEng;
    }

    public String getAddrMyan() {
        return addrMyan;
    }

    public void setAddrMyan(String addrMyan) {
        this.addrMyan = addrMyan;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
        if (!(o instanceof ShopInfoDTO)) {
            return false;
        }

        ShopInfoDTO shopInfoDTO = (ShopInfoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, shopInfoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShopInfoDTO{" +
            "id=" + getId() +
            ", shopCode='" + getShopCode() + "'" +
            ", nameEng='" + getNameEng() + "'" +
            ", nameMyan='" + getNameMyan() + "'" +
            ", addrEng='" + getAddrEng() + "'" +
            ", addrMyan='" + getAddrMyan() + "'" +
            ", phone='" + getPhone() + "'" +
            ", delFlg='" + getDelFlg() + "'" +
            "}";
    }
}
