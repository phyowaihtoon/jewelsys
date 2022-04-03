package com.devgroup.jewelsys.domain;

import com.devgroup.jewelsys.domain.enumeration.MortgageDamageType;
import com.devgroup.jewelsys.domain.enumeration.MortgageItemGroup;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MortgageEntry.
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
    @Enumerated(EnumType.STRING)
    @Column(name = "group_code", nullable = false)
    private MortgageItemGroup groupCode;

    @NotNull
    @Column(name = "item_code", nullable = false)
    private String itemCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "damage_type")
    private MortgageDamageType damageType;

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
    @Column(name = "start_date", nullable = false)
    private Instant startDate;

    @Column(name = "interest_rate")
    private Double interestRate;

    @Column(name = "mm_year")
    private String mmYear;

    @Column(name = "mm_month")
    private String mmMonth;

    @Column(name = "mm_day_gr")
    private String mmDayGR;

    @Column(name = "mm_day")
    private String mmDay;

    @NotNull
    @Column(name = "mortgage_status")
    private String mortgageStatus;

    @NotNull
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

    public MortgageItemGroup getGroupCode() {
        return this.groupCode;
    }

    public MortgageEntry groupCode(MortgageItemGroup groupCode) {
        this.groupCode = groupCode;
        return this;
    }

    public void setGroupCode(MortgageItemGroup groupCode) {
        this.groupCode = groupCode;
    }

    public String getItemCode() {
        return this.itemCode;
    }

    public MortgageEntry itemCode(String itemCode) {
        this.itemCode = itemCode;
        return this;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public MortgageDamageType getDamageType() {
        return this.damageType;
    }

    public MortgageEntry damageType(MortgageDamageType damageType) {
        this.damageType = damageType;
        return this;
    }

    public void setDamageType(MortgageDamageType damageType) {
        this.damageType = damageType;
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

    public String getMmYear() {
        return this.mmYear;
    }

    public MortgageEntry mmYear(String mmYear) {
        this.mmYear = mmYear;
        return this;
    }

    public void setMmYear(String mmYear) {
        this.mmYear = mmYear;
    }

    public String getMmMonth() {
        return this.mmMonth;
    }

    public MortgageEntry mmMonth(String mmMonth) {
        this.mmMonth = mmMonth;
        return this;
    }

    public void setMmMonth(String mmMonth) {
        this.mmMonth = mmMonth;
    }

    public String getMmDayGR() {
        return this.mmDayGR;
    }

    public MortgageEntry mmDayGR(String mmDayGR) {
        this.mmDayGR = mmDayGR;
        return this;
    }

    public void setMmDayGR(String mmDayGR) {
        this.mmDayGR = mmDayGR;
    }

    public String getMmDay() {
        return this.mmDay;
    }

    public MortgageEntry mmDay(String mmDay) {
        this.mmDay = mmDay;
        return this;
    }

    public void setMmDay(String mmDay) {
        this.mmDay = mmDay;
    }

    public String getMortgageStatus() {
        return mortgageStatus;
    }

    public MortgageEntry mortgageStatus(String mortgageStatus) {
        this.mortgageStatus = mortgageStatus;
        return this;
    }

    public void setMortgageStatus(String mortgageStatus) {
        this.mortgageStatus = mortgageStatus;
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
            ", groupCode='" + getGroupCode() + "'" +
            ", itemCode='" + getItemCode() + "'" +
            ", damageType='" + getDamageType() + "'" +
            ", wInKyat=" + getwInKyat() +
            ", wInPae=" + getwInPae() +
            ", wInYway=" + getwInYway() +
            ", principalAmount=" + getPrincipalAmount() +
            ", startDate='" + getStartDate() + "'" +
            ", interestRate=" + getInterestRate() +
            ", mmYear='" + getMmYear() + "'" +
            ", mmMonth='" + getMmMonth() + "'" +
            ", mmDayGR='" + getMmDayGR() + "'" +
            ", mmDay='" + getMmDay() + "'" +
            ", mortgageStatus='" + getMortgageStatus() + "'" +
            ", delFlg='" + getDelFlg() + "'" +
            "}";
    }
}
