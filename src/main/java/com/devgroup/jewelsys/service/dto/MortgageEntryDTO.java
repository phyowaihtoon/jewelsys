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

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String address;

    private String phone;

    @NotNull
    private MortgageItemGroup groupCode;

    @NotNull
    private String itemCode;

    private String itemName;

    private MortgageDamageType damageType;

    private Integer wInKyat;

    private Integer wInPae;

    private Integer wInYway;

    @NotNull
    private Double principalAmount;

    @NotNull
    private Instant startDate;

    private Double interestRate;

    private String mmYear;

    private String mmMonth;

    private String mmDayGR;

    private String mmDay;

    private String delFlg;

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

    public String getMmMonth() {
        return mmMonth;
    }

    public void setMmMonth(String mmMonth) {
        this.mmMonth = mmMonth;
    }

    public String getMmDayGR() {
        return mmDayGR;
    }

    public void setMmDayGR(String mmDayGR) {
        this.mmDayGR = mmDayGR;
    }

    public String getMmDay() {
        return mmDay;
    }

    public void setMmDay(String mmDay) {
        this.mmDay = mmDay;
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
            ", delFlg='" + getDelFlg() + "'" +
            "}";
    }
}
