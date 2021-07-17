package com.devgroup.jewelsys.repository;

import com.devgroup.jewelsys.domain.GoldPriceRate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the GoldPriceRate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GoldPriceRateRepository extends JpaRepository<GoldPriceRate, Long> {}
