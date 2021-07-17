package com.devgroup.jewelsys.repository;

import com.devgroup.jewelsys.domain.GoldItemGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the GoldItemGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GoldItemGroupRepository extends JpaRepository<GoldItemGroup, Long> {}
