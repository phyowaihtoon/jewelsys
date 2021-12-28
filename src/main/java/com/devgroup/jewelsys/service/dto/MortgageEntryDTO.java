package com.devgroup.jewelsys.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

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
    private String itemName;

    private Integer wInKyat;

    private Integer wInPae;

    private Integer wInYway;

    @NotNull
    private Double principalAmount;

    @NotNull
    private Double interestRate;

    private Integer term;

    @NotNull
    private Instant startDate;

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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
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

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
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
