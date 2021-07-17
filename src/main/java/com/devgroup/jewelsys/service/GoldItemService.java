package com.devgroup.jewelsys.service;

import com.devgroup.jewelsys.service.dto.GoldItemDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.devgroup.jewelsys.domain.GoldItem}.
 */
public interface GoldItemService {
    /**
     * Save a goldItem.
     *
     * @param goldItemDTO the entity to save.
     * @return the persisted entity.
     */
    GoldItemDTO save(GoldItemDTO goldItemDTO);

    /**
     * Partially updates a goldItem.
     *
     * @param goldItemDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<GoldItemDTO> partialUpdate(GoldItemDTO goldItemDTO);

    /**
     * Get all the goldItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GoldItemDTO> findAll(Pageable pageable);

    /**
     * Get the "id" goldItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GoldItemDTO> findOne(Long id);

    /**
     * Delete the "id" goldItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
