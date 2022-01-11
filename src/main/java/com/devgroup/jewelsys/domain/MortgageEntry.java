package com.devgroup.jewelsys.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * MortgageEntry.
 */
@Entity
@Table(name = "mortgage_entry")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MortgageEntry extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone")
    private String phone;

    @NotNull
    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "w_in_kyat")
    private Integer wInKyat;

    @Column(name = "w_in_pae")
    private Integer wInPae;

    @Column(name = "w_in_yway")
    private Integer wInYway;

    @NotNull
    @Column(name = "principal_amount", nullable = false)
    private Double principalAmount;

    @NotNull
    @Column(name = "interest_rate", nullable = false)
    private Double interestRate;

    @Column(name = "term")
    private Integer term;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private Instant startDate;

    @Column(name = "del_flg")
    private String delFlg;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MortgageEntry id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public MortgageEntry name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public MortgageEntry address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return this.phone;
    }

    public MortgageEntry phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getItemName() {
        return this.itemName;
    }

    public MortgageEntry itemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getwInKyat() {
        return this.wInKyat;
    }

    public MortgageEntry wInKyat(Integer wInKyat) {
        this.wInKyat = wInKyat;
        return this;
    }

    public void setwInKyat(Integer wInKyat) {
        this.wInKyat = wInKyat;
    }

    public Integer getwInPae() {
        return this.wInPae;
    }

    public MortgageEntry wInPae(Integer wInPae) {
        this.wInPae = wInPae;
        return this;
    }

    public void setwInPae(Integer wInPae) {
        this.wInPae = wInPae;
    }

    public Integer getwInYway() {
        return this.wInYway;
    }

    public MortgageEntry wInYway(Integer wInYway) {
        this.wInYway = wInYway;
        return this;
    }

    public void setwInYway(Integer wInYway) {
        this.wInYway = wInYway;
    }

    public Double getPrincipalAmount() {
        return this.principalAmount;
    }

    public MortgageEntry principalAmount(Double principalAmount) {
        this.principalAmount = principalAmount;
        return this;
    }

    public void setPrincipalAmount(Double principalAmount) {
        this.principalAmount = principalAmount;
    }

    public Double getInterestRate() {
        return this.interestRate;
    }

    public MortgageEntry interestRate(Double interestRate) {
        this.interestRate = interestRate;
        return this;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Integer getTerm() {
        return this.term;
    }

    public MortgageEntry term(Integer term) {
        this.term = term;
        return this;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public MortgageEntry startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public String getDelFlg() {
        return this.delFlg;
    }

    public MortgageEntry delFlg(String delFlg) {
        this.delFlg = delFlg;
        return this;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MortgageEntry)) {
            return false;
        }
        return id != null && id.equals(((MortgageEntry) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MortgageEntry{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", phone='" + getPhone() + "'" +
            ", itemName='" + getItemName() + "'" +
            ", wInKyat=" + getwInKyat() +
            ", wInPae=" + getwInPae() +
            ", wInYway=" + getwInYway() +
            ", principalAmount=" + getPrincipalAmount() +
            ", interestRate=" + getInterestRate() +
            ", term=" + getTerm() +
            ", startDate='" + getStartDate() + "'" +
            ", delFlg='" + getDelFlg() + "'" +
            "}";
    }
}
