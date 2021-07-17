package com.devgroup.jewelsys.repository;

import com.devgroup.jewelsys.domain.MenuGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MenuGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MenuGroupRepository extends JpaRepository<MenuGroup, Long> {}
