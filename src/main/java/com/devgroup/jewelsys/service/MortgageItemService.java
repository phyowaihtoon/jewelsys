package com.devgroup.jewelsys.service;

import com.devgroup.jewelsys.service.dto.GoldTypeDTO;
import com.devgroup.jewelsys.service.dto.MortgageItemDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.devgroup.jewelsys.domain.MortgageItem}.
 */
public interface MortgageItemService {
    /**
     * Save a mortgageItem.
     *
     * @param mortgageItemDTO the entity to save.
     * @return the persisted entity.
     */
    MortgageItemDTO save(MortgageItemDTO mortgageItemDTO);

    /**
     * Partially updates a mortgageItem.
     *
     * @param mortgageItemDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MortgageItemDTO> partialUpdate(MortgageItemDTO mortgageItemDTO);

    /**
     * Get all the mortgageItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MortgageItemDTO> findAll(Pageable pageable);

    /**
     * Get the "id" mortgageItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MortgageItemDTO> findOne(Long id);

    /**
     * Delete the "id" mortgageItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<MortgageItemDTO> loadAll();
}
