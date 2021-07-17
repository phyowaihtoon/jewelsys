package com.devgroup.jewelsys.repository;

import com.devgroup.jewelsys.domain.GoldType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the GoldType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GoldTypeRepository extends JpaRepository<GoldType, Long> {}
