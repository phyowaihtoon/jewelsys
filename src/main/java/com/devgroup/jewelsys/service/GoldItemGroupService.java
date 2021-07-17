package com.devgroup.jewelsys.service;

import com.devgroup.jewelsys.service.dto.GoldItemGroupDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.devgroup.jewelsys.domain.GoldItemGroup}.
 */
public interface GoldItemGroupService {
    /**
     * Save a goldItemGroup.
     *
     * @param goldItemGroupDTO the entity to save.
     * @return the persisted entity.
     */
    GoldItemGroupDTO save(GoldItemGroupDTO goldItemGroupDTO);

    /**
     * Partially updates a goldItemGroup.
     *
     * @param goldItemGroupDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<GoldItemGroupDTO> partialUpdate(GoldItemGroupDTO goldItemGroupDTO);

    /**
     * Get all the goldItemGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GoldItemGroupDTO> findAll(Pageable pageable);

    /**
     * Get the "id" goldItemGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GoldItemGroupDTO> findOne(Long id);

    /**
     * Delete the "id" goldItemGroup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
