package com.devgroup.jewelsys.repository;

import com.devgroup.jewelsys.domain.RoleMenuMap;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RoleMenuMap entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoleMenuMapRepository extends JpaRepository<RoleMenuMap, Long> {
    List<RoleMenuMap> findByRoleIdIn(Set<String> roles);
}
