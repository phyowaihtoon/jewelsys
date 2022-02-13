package com.devgroup.jewelsys.repository;

import com.devgroup.jewelsys.domain.MortgageItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MortgageItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MortgageItemRepository extends JpaRepository<MortgageItem, Long> {
    MortgageItem findByCode(String code);
}
