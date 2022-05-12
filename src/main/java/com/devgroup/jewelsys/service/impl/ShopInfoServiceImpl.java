package com.devgroup.jewelsys.service.impl;

import com.devgroup.jewelsys.domain.ShopInfo;
import com.devgroup.jewelsys.repository.ShopInfoRepository;
import com.devgroup.jewelsys.service.ShopInfoService;
import com.devgroup.jewelsys.service.dto.ShopInfoDTO;
import com.devgroup.jewelsys.service.mapper.ShopInfoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ShopInfo}.
 */
@Service
@Transactional
public class ShopInfoServiceImpl implements ShopInfoService {

    private final Logger log = LoggerFactory.getLogger(ShopInfoService.class);

    private final ShopInfoRepository shopInfoRepository;

    private final ShopInfoMapper shopInfoMapper;

    public ShopInfoServiceImpl(ShopInfoRepository shopInfoRepository, ShopInfoMapper shopInfoMapper) {
        this.shopInfoRepository = shopInfoRepository;
        this.shopInfoMapper = shopInfoMapper;
    }

    /**
     * Save a shopInfo.
     *
     * @param shopInfoDTO the entity to save.
     * @return the persisted entity.
     */
    public ShopInfoDTO save(ShopInfoDTO shopInfoDTO) {
        log.debug("Request to save ShopInfo : {}", shopInfoDTO);
        ShopInfo shopInfo = shopInfoMapper.toEntity(shopInfoDTO);
        shopInfo = shopInfoRepository.save(shopInfo);
        return shopInfoMapper.toDto(shopInfo);
    }

    /**
     * Partially update a shopInfo.
     *
     * @param shopInfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ShopInfoDTO> partialUpdate(ShopInfoDTO shopInfoDTO) {
        log.debug("Request to partially update ShopInfo : {}", shopInfoDTO);

        return shopInfoRepository
            .findById(shopInfoDTO.getId())
            .map(
                existingShopInfo -> {
                    shopInfoMapper.partialUpdate(existingShopInfo, shopInfoDTO);
                    return existingShopInfo;
                }
            )
            .map(shopInfoRepository::save)
            .map(shopInfoMapper::toDto);
    }

    /**
     * Get all the shopInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ShopInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ShopInfos");
        return shopInfoRepository.findAll(pageable).map(shopInfoMapper::toDto);
    }

    /**
     * Get one shopInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ShopInfoDTO> findOne(Long id) {
        log.debug("Request to get ShopInfo : {}", id);
        return shopInfoRepository.findById(id).map(shopInfoMapper::toDto);
    }

    /**
     * Delete the shopInfo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ShopInfo : {}", id);
        shopInfoRepository.deleteById(id);
    }
}
