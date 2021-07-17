package com.devgroup.jewelsys.repository;

import com.devgroup.jewelsys.domain.GemsItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the GemsItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GemsItemRepository extends JpaRepository<GemsItem, Long> {}
