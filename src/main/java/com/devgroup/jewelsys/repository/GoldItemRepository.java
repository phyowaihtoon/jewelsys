package com.devgroup.jewelsys.repository;

import com.devgroup.jewelsys.domain.GoldItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the GoldItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GoldItemRepository extends JpaRepository<GoldItem, Long> {}
