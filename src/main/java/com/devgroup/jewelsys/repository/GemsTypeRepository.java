package com.devgroup.jewelsys.repository;

import com.devgroup.jewelsys.domain.GemsType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the GemsType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GemsTypeRepository extends JpaRepository<GemsType, Long> {}
