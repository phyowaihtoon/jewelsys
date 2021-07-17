package com.devgroup.jewelsys.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A GoldPriceRate.
 */
@Entity
@Table(name = "gold_price_rate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GoldPriceRate extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "effective_date", nullable = false)
    private Instant effectiveDate;

    @NotNull
    @Column(name = "rate_sr_no", nullable = false)
    private Integer rateSrNo;

    @NotNull
    @Column(name = "rate_type", nullable = false)
    private String rateType;

    @Column(name = "price_rate")
    private Double priceRate;

    @Column(name = "del_flg")
    private String delFlg;

    @ManyToOne
    private GoldType goldType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GoldPriceRate id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getEffectiveDate() {
        return this.effectiveDate;
    }

    public GoldPriceRate effectiveDate(Instant effectiveDate) {
        this.effectiveDate = effectiveDate;
        return this;
    }

    public void setEffectiveDate(Instant effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Integer getRateSrNo() {
        return this.rateSrNo;
    }

    public GoldPriceRate rateSrNo(Integer rateSrNo) {
        this.rateSrNo = rateSrNo;
        return this;
    }

    public void setRateSrNo(Integer rateSrNo) {
        this.rateSrNo = rateSrNo;
    }

    public String getRateType() {
        return this.rateType;
    }

    public GoldPriceRate rateType(String rateType) {
        this.rateType = rateType;
        return this;
    }

    public void setRateType(String rateType) {
        this.rateType = rateType;
    }

    public Double getPriceRate() {
        return this.priceRate;
    }

    public GoldPriceRate priceRate(Double priceRate) {
        this.priceRate = priceRate;
        return this;
    }

    public void setPriceRate(Double priceRate) {
        this.priceRate = priceRate;
    }

    public String getDelFlg() {
        return this.delFlg;
    }

    public GoldPriceRate delFlg(String delFlg) {
        this.delFlg = delFlg;
        return this;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    public GoldType getGoldType() {
        return this.goldType;
    }

    public GoldPriceRate goldType(GoldType goldType) {
        this.setGoldType(goldType);
        return this;
    }

    public void setGoldType(GoldType goldType) {
        this.goldType = goldType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GoldPriceRate)) {
            return false;
        }
        return id != null && id.equals(((GoldPriceRate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GoldPriceRate{" +
            "id=" + getId() +
            ", effectiveDate='" + getEffectiveDate() + "'" +
            ", rateSrNo=" + getRateSrNo() +
            ", rateType='" + getRateType() + "'" +
            ", priceRate=" + getPriceRate() +
            ", delFlg='" + getDelFlg() + "'" +
            "}";
    }
}
