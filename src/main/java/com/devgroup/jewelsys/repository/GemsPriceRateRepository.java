package com.devgroup.jewelsys.repository;

import com.devgroup.jewelsys.domain.GemsPriceRate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the GemsPriceRate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GemsPriceRateRepository extends JpaRepository<GemsPriceRate, Long> {}
