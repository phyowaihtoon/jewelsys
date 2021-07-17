package com.devgroup.jewelsys.service;

import com.devgroup.jewelsys.service.dto.MenuGroupDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.devgroup.jewelsys.domain.MenuGroup}.
 */
public interface MenuGroupService {
    /**
     * Save a menuGroup.
     *
     * @param menuGroupDTO the entity to save.
     * @return the persisted entity.
     */
    MenuGroupDTO save(MenuGroupDTO menuGroupDTO);

    /**
     * Partially updates a menuGroup.
     *
     * @param menuGroupDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MenuGroupDTO> partialUpdate(MenuGroupDTO menuGroupDTO);

    /**
     * Get all the menuGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MenuGroupDTO> findAll(Pageable pageable);

    /**
     * Get the "id" menuGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MenuGroupDTO> findOne(Long id);

    /**
     * Delete the "id" menuGroup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
