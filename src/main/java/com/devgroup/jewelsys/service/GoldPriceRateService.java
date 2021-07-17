package com.devgroup.jewelsys.service;

import com.devgroup.jewelsys.service.dto.GoldPriceRateDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.devgroup.jewelsys.domain.GoldPriceRate}.
 */
public interface GoldPriceRateService {
    /**
     * Save a goldPriceRate.
     *
     * @param goldPriceRateDTO the entity to save.
     * @return the persisted entity.
     */
    GoldPriceRateDTO save(GoldPriceRateDTO goldPriceRateDTO);

    /**
     * Partially updates a goldPriceRate.
     *
     * @param goldPriceRateDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<GoldPriceRateDTO> partialUpdate(GoldPriceRateDTO goldPriceRateDTO);

    /**
     * Get all the goldPriceRates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GoldPriceRateDTO> findAll(Pageable pageable);

    /**
     * Get the "id" goldPriceRate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GoldPriceRateDTO> findOne(Long id);

    /**
     * Delete the "id" goldPriceRate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
