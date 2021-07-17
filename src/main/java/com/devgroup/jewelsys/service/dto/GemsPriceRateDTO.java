package com.devgroup.jewelsys.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.devgroup.jewelsys.domain.GemsPriceRate} entity.
 */
public class GemsPriceRateDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant effectiveDate;

    @NotNull
    private Integer rateSrNo;

    @NotNull
    private String rateType;

    private Double priceRate;

    private String delFlg;

    private GemsItemDTO gemsItem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Instant effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Integer getRateSrNo() {
        return rateSrNo;
    }

    public void setRateSrNo(Integer rateSrNo) {
        this.rateSrNo = rateSrNo;
    }

    public String getRateType() {
        return rateType;
    }

    public void setRateType(String rateType) {
        this.rateType = rateType;
    }

    public Double getPriceRate() {
        return priceRate;
    }

    public void setPriceRate(Double priceRate) {
        this.priceRate = priceRate;
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    public GemsItemDTO getGemsItem() {
        return gemsItem;
    }

    public void setGemsItem(GemsItemDTO gemsItem) {
        this.gemsItem = gemsItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GemsPriceRateDTO)) {
            return false;
        }

        GemsPriceRateDTO gemsPriceRateDTO = (GemsPriceRateDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, gemsPriceRateDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GemsPriceRateDTO{" +
            "id=" + getId() +
            ", effectiveDate='" + getEffectiveDate() + "'" +
            ", rateSrNo=" + getRateSrNo() +
            ", rateType='" + getRateType() + "'" +
            ", priceRate=" + getPriceRate() +
            ", delFlg='" + getDelFlg() + "'" +
            ", gemsItem=" + getGemsItem() +
            "}";
    }
}
