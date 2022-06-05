package com.devgroup.jewelsys.service.dto;

import com.devgroup.jewelsys.domain.enumeration.MortgageDamageType;
import com.devgroup.jewelsys.domain.enumeration.MortgageItemGroup;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link com.devgroup.jewelsys.domain.MortgageEntry} entity.
 */
public class MortgageEntryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String address;

    private String phone;

    @NotNull
    private MortgageItemGroup groupCode;

    private String groupDesc;

    @NotNull
    private String itemCode;

    private String itemName;

    private MortgageDamageType damageType;

    private String damageTypeDesc;

    private Integer wInKyat;

    private Integer wInPae;

    private Integer wInYway;

    @NotNull
    private Double principalAmount;

    @NotNull
    private Instant startDate;

    private Double interestRate;

    private String mmYear;

    private String mmYearDesc;

    private String mmMonth;

    private String mmMonthDesc;

    private String mmDayGR;

    private String mmDayGRDesc;

    private String mmDay;

    private String mmDayDesc;

    private String mortgageStatus;

    private String mortStatusDesc;

    private String delFlg;

    // Additional Fields for Reporting
    private String startDateStr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public MortgageItemGroup getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(MortgageItemGroup groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public MortgageDamageType getDamageType() {
        return damageType;
    }

    public void setDamageType(MortgageDamageType damageType) {
        this.damageType = damageType;
    }

    public String getDamageTypeDesc() {
        return damageTypeDesc;
    }

    public void setDamageTypeDesc(String damageTypeDesc) {
        this.damageTypeDesc = damageTypeDesc;
    }

    public Integer getwInKyat() {
        return wInKyat;
    }

    public void setwInKyat(Integer wInKyat) {
        this.wInKyat = wInKyat;
    }

    public Integer getwInPae() {
        return wInPae;
    }

    public void setwInPae(Integer wInPae) {
        this.wInPae = wInPae;
    }

    public Integer getwInYway() {
        return wInYway;
    }

    public void setwInYway(Integer wInYway) {
        this.wInYway = wInYway;
    }

    public Double getPrincipalAmount() {
        return principalAmount;
    }

    public void setPrincipalAmount(Double principalAmount) {
        this.principalAmount = principalAmount;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public String getMmYear() {
        return mmYear;
    }

    public void setMmYear(String mmYear) {
        this.mmYear = mmYear;
    }

    public String getMmYearDesc() {
        return mmYearDesc;
    }

    public void setMmYearDesc(String mmYearDesc) {
        this.mmYearDesc = mmYearDesc;
    }

    public String getMmMonth() {
        return mmMonth;
    }

    public void setMmMonth(String mmMonth) {
        this.mmMonth = mmMonth;
    }

    public String getMmMonthDesc() {
        return mmMonthDesc;
    }

    public void setMmMonthDesc(String mmMonthDesc) {
        this.mmMonthDesc = mmMonthDesc;
    }

    public String getMmDayGR() {
        return mmDayGR;
    }

    public void setMmDayGR(String mmDayGR) {
        this.mmDayGR = mmDayGR;
    }

    public String getMmDayGRDesc() {
        return mmDayGRDesc;
    }

    public void setMmDayGRDesc(String mmDayGRDesc) {
        this.mmDayGRDesc = mmDayGRDesc;
    }

    public String getMmDay() {
        return mmDay;
    }

    public void setMmDay(String mmDay) {
        this.mmDay = mmDay;
    }

    public String getMmDayDesc() {
        return mmDayDesc;
    }

    public void setMmDayDesc(String mmDayDesc) {
        this.mmDayDesc = mmDayDesc;
    }

    public String getMortgageStatus() {
        return mortgageStatus;
    }

    public void setMortgageStatus(String mortgageStatus) {
        this.mortgageStatus = mortgageStatus;
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    public String getMortStatusDesc() {
        return mortStatusDesc;
    }

    public void setMortStatusDesc(String mortStatusDesc) {
        this.mortStatusDesc = mortStatusDesc;
    }

    public String getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MortgageEntryDTO)) {
            return false;
        }

        MortgageEntryDTO mortgageEntryDTO = (MortgageEntryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, mortgageEntryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MortgageEntryDTO{" +
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
