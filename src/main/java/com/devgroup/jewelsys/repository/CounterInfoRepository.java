package com.devgroup.jewelsys.repository;

import com.devgroup.jewelsys.domain.CounterInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CounterInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CounterInfoRepository extends JpaRepository<CounterInfo, Long> {}
