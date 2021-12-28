package com.devgroup.jewelsys.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.devgroup.jewelsys.domain.CounterInfo} entity.
 */
public class CounterInfoDTO implements Serializable {

    private Long id;

    @NotNull
    private String counterNo;

    @NotNull
    private String counterName;

    private String delFlg;

    private ShopInfoDTO shopInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCounterNo() {
        return counterNo;
    }

    public void setCounterNo(String counterNo) {
        this.counterNo = counterNo;
    }

    public String getCounterName() {
        return counterName;
    }

    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    public ShopInfoDTO getShopInfo() {
        return shopInfo;
    }

    public void setShopInfo(ShopInfoDTO shopInfo) {
        this.shopInfo = shopInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CounterInfoDTO)) {
            return false;
        }

        CounterInfoDTO counterInfoDTO = (CounterInfoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, counterInfoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CounterInfoDTO{" +
            "id=" + getId() +
            ", counterNo='" + getCounterNo() + "'" +
            ", counterName='" + getCounterName() + "'" +
            ", delFlg='" + getDelFlg() + "'" +
            ", shopInfo=" + getShopInfo() +
            "}";
    }
}
