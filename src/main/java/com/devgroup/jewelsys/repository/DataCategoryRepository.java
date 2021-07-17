package com.devgroup.jewelsys.repository;

import com.devgroup.jewelsys.domain.DataCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DataCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataCategoryRepository extends JpaRepository<DataCategory, Long> {}
