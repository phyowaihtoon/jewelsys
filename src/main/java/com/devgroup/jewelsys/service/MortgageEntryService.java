package com.devgroup.jewelsys.service;

import com.devgroup.jewelsys.service.dto.CommonDTO;
import com.devgroup.jewelsys.service.dto.MortgageEntryDTO;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.devgroup.jewelsys.domain.MortgageEntry}.
 */
public interface MortgageEntryService {
    /**
     * Save a mortgageEntry.
     *
     * @param mortgageEntryDTO the entity to save.
     * @return the persisted entity.
     */
    MortgageEntryDTO save(MortgageEntryDTO mortgageEntryDTO);

    /**
     * Partially updates a mortgageEntry.
     *
     * @param mortgageEntryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MortgageEntryDTO> partialUpdate(MortgageEntryDTO mortgageEntryDTO);

    /**
     * Get all the mortgageEntries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    CommonDTO findAll(Pageable pageable);

    /**
     * Get the "id" mortgageEntry.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MortgageEntryDTO> findOne(Long id);

    /**
     * Delete the "id" mortgageEntry.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
