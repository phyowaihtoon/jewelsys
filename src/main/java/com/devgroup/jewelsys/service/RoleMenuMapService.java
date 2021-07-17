package com.devgroup.jewelsys.service;

import com.devgroup.jewelsys.service.dto.MenuAccessDTO;
import com.devgroup.jewelsys.service.dto.RoleMenuMapDTO;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.devgroup.jewelsys.domain.RoleMenuMap}.
 */
public interface RoleMenuMapService {
    /**
     * Save a roleMenuMap.
     *
     * @param roleMenuMapDTO the entity to save.
     * @return the persisted entity.
     */
    RoleMenuMapDTO save(RoleMenuMapDTO roleMenuMapDTO);

    /**
     * Partially updates a roleMenuMap.
     *
     * @param roleMenuMapDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RoleMenuMapDTO> partialUpdate(RoleMenuMapDTO roleMenuMapDTO);

    /**
     * Get all the roleMenuMaps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RoleMenuMapDTO> findAll(Pageable pageable);

    /**
     * Get the "id" roleMenuMap.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RoleMenuMapDTO> findOne(Long id);

    /**
     * Delete the "id" roleMenuMap.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<MenuAccessDTO> findMenusByRole(Set<String> roles);
}
