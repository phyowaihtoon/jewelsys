package com.devgroup.jewelsys.service;

import com.devgroup.jewelsys.service.dto.GemsPriceRateDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.devgroup.jewelsys.domain.GemsPriceRate}.
 */
public interface GemsPriceRateService {
    /**
     * Save a gemsPriceRate.
     *
     * @param gemsPriceRateDTO the entity to save.
     * @return the persisted entity.
     */
    GemsPriceRateDTO save(GemsPriceRateDTO gemsPriceRateDTO);

    /**
     * Partially updates a gemsPriceRate.
     *
     * @param gemsPriceRateDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<GemsPriceRateDTO> partialUpdate(GemsPriceRateDTO gemsPriceRateDTO);

    /**
     * Get all the gemsPriceRates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GemsPriceRateDTO> findAll(Pageable pageable);

    /**
     * Get the "id" gemsPriceRate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GemsPriceRateDTO> findOne(Long id);

    /**
     * Delete the "id" gemsPriceRate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
