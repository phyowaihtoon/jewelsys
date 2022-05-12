package com.devgroup.jewelsys.service.dto;

import java.util.Objects;
import javax.validation.constraints.NotNull;

public class MortgageRedemptionDTO {

    private Long id;

    @NotNull
    private Long mortgageID;

    @NotNull
    private Double interestAmount;

    @NotNull
    private String mrDate;

    @NotNull
    private String mrTime;

    private String mrMMYear;

    private String mrMMMonth;

    private String mrMMDayGR;

    private String mrMMDay;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMortgageID() {
        return mortgageID;
    }

    public void setMortgageID(Long mortgageID) {
        this.mortgageID = mortgageID;
    }

    public Double getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(Double interestAmount) {
        this.interestAmount = interestAmount;
    }

    public String getMrDate() {
        return mrDate;
    }

    public void setMrDate(String mrDate) {
        this.mrDate = mrDate;
    }

    public String getMrTime() {
        return mrTime;
    }

    public void setMrTime(String mrTime) {
        this.mrTime = mrTime;
    }

    public String getMrMMYear() {
        return mrMMYear;
    }

    public void setMrMMYear(String mrMMYear) {
        this.mrMMYear = mrMMYear;
    }

    public String getMrMMMonth() {
        return mrMMMonth;
    }

    public void setMrMMMonth(String mrMMMonth) {
        this.mrMMMonth = mrMMMonth;
    }

    public String getMrMMDayGR() {
        return mrMMDayGR;
    }

    public void setMrMMDayGR(String mrMMDayGR) {
        this.mrMMDayGR = mrMMDayGR;
    }

    public String getMrMMDay() {
        return mrMMDay;
    }

    public void setMrMMDay(String mrMMDay) {
        this.mrMMDay = mrMMDay;
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return Objects.hash(this.id);
    }

    @Override
    public boolean equals(Object o) {
        // TODO Auto-generated method stub
        if (this == o) {
            return true;
        }
        if (!(o instanceof MortgageRedemptionDTO)) {
            return false;
        }

        MortgageRedemptionDTO mortgageRedemptionDTO = (MortgageRedemptionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, mortgageRedemptionDTO.id);
    }
}
