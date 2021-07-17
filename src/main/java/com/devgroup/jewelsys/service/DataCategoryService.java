package com.devgroup.jewelsys.service;

import com.devgroup.jewelsys.service.dto.DataCategoryDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.devgroup.jewelsys.domain.DataCategory}.
 */
public interface DataCategoryService {
    /**
     * Save a dataCategory.
     *
     * @param dataCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    DataCategoryDTO save(DataCategoryDTO dataCategoryDTO);

    /**
     * Partially updates a dataCategory.
     *
     * @param dataCategoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DataCategoryDTO> partialUpdate(DataCategoryDTO dataCategoryDTO);

    /**
     * Get all the dataCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DataCategoryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" dataCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DataCategoryDTO> findOne(Long id);

    /**
     * Delete the "id" dataCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
