package com.devgroup.jewelsys.service;

import com.devgroup.jewelsys.service.dto.GemsItemDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.devgroup.jewelsys.domain.GemsItem}.
 */
public interface GemsItemService {
    /**
     * Save a gemsItem.
     *
     * @param gemsItemDTO the entity to save.
     * @return the persisted entity.
     */
    GemsItemDTO save(GemsItemDTO gemsItemDTO);

    /**
     * Partially updates a gemsItem.
     *
     * @param gemsItemDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<GemsItemDTO> partialUpdate(GemsItemDTO gemsItemDTO);

    /**
     * Get all the gemsItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GemsItemDTO> findAll(Pageable pageable);

    /**
     * Get the "id" gemsItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GemsItemDTO> findOne(Long id);

    /**
     * Delete the "id" gemsItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
