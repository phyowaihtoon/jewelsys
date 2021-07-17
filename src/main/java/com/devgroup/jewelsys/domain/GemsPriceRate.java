package com.devgroup.jewelsys.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A GemsPriceRate.
 */
@Entity
@Table(name = "gems_price_rate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GemsPriceRate extends AbstractAuditingEntity implements Serializable {

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
    private GemsItem gemsItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GemsPriceRate id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getEffectiveDate() {
        return this.effectiveDate;
    }

    public GemsPriceRate effectiveDate(Instant effectiveDate) {
        this.effectiveDate = effectiveDate;
        return this;
    }

    public void setEffectiveDate(Instant effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Integer getRateSrNo() {
        return this.rateSrNo;
    }

    public GemsPriceRate rateSrNo(Integer rateSrNo) {
        this.rateSrNo = rateSrNo;
        return this;
    }

    public void setRateSrNo(Integer rateSrNo) {
        this.rateSrNo = rateSrNo;
    }

    public String getRateType() {
        return this.rateType;
    }

    public GemsPriceRate rateType(String rateType) {
        this.rateType = rateType;
        return this;
    }

    public void setRateType(String rateType) {
        this.rateType = rateType;
    }

    public Double getPriceRate() {
        return this.priceRate;
    }

    public GemsPriceRate priceRate(Double priceRate) {
        this.priceRate = priceRate;
        return this;
    }

    public void setPriceRate(Double priceRate) {
        this.priceRate = priceRate;
    }

    public String getDelFlg() {
        return this.delFlg;
    }

    public GemsPriceRate delFlg(String delFlg) {
        this.delFlg = delFlg;
        return this;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    public GemsItem getGemsItem() {
        return this.gemsItem;
    }

    public GemsPriceRate gemsItem(GemsItem gemsItem) {
        this.setGemsItem(gemsItem);
        return this;
    }

    public void setGemsItem(GemsItem gemsItem) {
        this.gemsItem = gemsItem;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GemsPriceRate)) {
            return false;
        }
        return id != null && id.equals(((GemsPriceRate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GemsPriceRate{" +
            "id=" + getId() +
            ", effectiveDate='" + getEffectiveDate() + "'" +
            ", rateSrNo=" + getRateSrNo() +
            ", rateType='" + getRateType() + "'" +
            ", priceRate=" + getPriceRate() +
            ", delFlg='" + getDelFlg() + "'" +
            "}";
    }
}
