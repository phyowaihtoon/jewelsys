package com.devgroup.jewelsys.repository;

import com.devgroup.jewelsys.domain.MortgageRedemption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MortgageRedemptionRepository extends JpaRepository<MortgageRedemption, Long> {
    MortgageRedemption findByMortgageID(Long mortgageID);
}
