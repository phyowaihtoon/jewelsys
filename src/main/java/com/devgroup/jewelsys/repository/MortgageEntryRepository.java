package com.devgroup.jewelsys.repository;

import com.devgroup.jewelsys.domain.MortgageEntry;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MortgageEntry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MortgageEntryRepository extends JpaRepository<MortgageEntry, Long> {}
