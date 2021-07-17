package com.devgroup.jewelsys.service;

import com.devgroup.jewelsys.service.dto.GemsTypeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.devgroup.jewelsys.domain.GemsType}.
 */
public interface GemsTypeService {
    /**
     * Save a gemsType.
     *
     * @param gemsTypeDTO the entity to save.
     * @return the persisted entity.
     */
    GemsTypeDTO save(GemsTypeDTO gemsTypeDTO);

    /**
     * Partially updates a gemsType.
     *
     * @param gemsTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<GemsTypeDTO> partialUpdate(GemsTypeDTO gemsTypeDTO);

    /**
     * Get all the gemsTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GemsTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" gemsType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GemsTypeDTO> findOne(Long id);

    /**
     * Delete the "id" gemsType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
