package com.devgroup.jewelsys.repository;

import com.devgroup.jewelsys.domain.ShopInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ShopInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShopInfoRepository extends JpaRepository<ShopInfo, Long> {}
