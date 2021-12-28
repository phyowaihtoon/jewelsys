package com.devgroup.jewelsys.service;

import com.devgroup.jewelsys.service.dto.CounterInfoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.devgroup.jewelsys.domain.CounterInfo}.
 */
public interface CounterInfoService {
    /**
     * Save a counterInfo.
     *
     * @param counterInfoDTO the entity to save.
     * @return the persisted entity.
     */
    CounterInfoDTO save(CounterInfoDTO counterInfoDTO);

    /**
     * Partially updates a counterInfo.
     *
     * @param counterInfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CounterInfoDTO> partialUpdate(CounterInfoDTO counterInfoDTO);

    /**
     * Get all the counterInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CounterInfoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" counterInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CounterInfoDTO> findOne(Long id);

    /**
     * Delete the "id" counterInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
