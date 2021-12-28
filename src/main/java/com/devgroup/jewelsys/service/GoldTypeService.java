package com.devgroup.jewelsys.service;

import com.devgroup.jewelsys.service.dto.GoldTypeDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.devgroup.jewelsys.domain.GoldType}.
 */
public interface GoldTypeService {
    /**
     * Save a goldType.
     *
     * @param goldTypeDTO the entity to save.
     * @return the persisted entity.
     */
    GoldTypeDTO save(GoldTypeDTO goldTypeDTO);

    /**
     * Partially updates a goldType.
     *
     * @param goldTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<GoldTypeDTO> partialUpdate(GoldTypeDTO goldTypeDTO);

    /**
     * Get all the goldTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GoldTypeDTO> findAll(Pageable pageable);

    List<GoldTypeDTO> loadAll();

    /**
     * Get the "id" goldType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GoldTypeDTO> findOne(Long id);

    /**
     * Delete the "id" goldType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
