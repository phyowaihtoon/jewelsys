package com.devgroup.jewelsys.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "mr_redemption")
public class MortgageRedemption extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "mortgage_id", nullable = false)
    private Long mortgageID;

    @NotNull
    @Column(name = "interest_amt", nullable = false)
    private Double interestAmount;

    @NotNull
    @Column(name = "mr_date", nullable = false)
    private String mrDate;

    @NotNull
    @Column(name = "mr_time", nullable = false)
    private String mrTime;

    @Column(name = "mr_MMYear")
    private String mrMMYear;

    @Column(name = "mr_MMMonth")
    private String mrMMMonth;

    @Column(name = "mr_MMDayGR")
    private String mrMMDayGR;

    @Column(name = "mr_MMDay")
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
}
