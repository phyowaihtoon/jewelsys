package com.devgroup.jewelsys.service;

import com.devgroup.jewelsys.service.dto.ShopInfoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface ShopInfoService {
    /**
     * Save a shopInfo.
     *
     * @param shopInfoDTO the entity to save.
     * @return the persisted entity.
     */
    public ShopInfoDTO save(ShopInfoDTO shopInfoDTO);

    /**
     * Partially update a shopInfo.
     *
     * @param shopInfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ShopInfoDTO> partialUpdate(ShopInfoDTO shopInfoDTO);

    /**
     * Get all the shopInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ShopInfoDTO> findAll(Pageable pageable);

    /**
     * Get one shopInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ShopInfoDTO> findOne(Long id);

    /**
     * Delete the shopInfo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id);
}
