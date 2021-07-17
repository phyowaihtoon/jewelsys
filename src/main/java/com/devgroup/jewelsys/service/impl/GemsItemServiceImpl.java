package com.devgroup.jewelsys.service.impl;

import com.devgroup.jewelsys.domain.GemsItem;
import com.devgroup.jewelsys.repository.GemsItemRepository;
import com.devgroup.jewelsys.service.GemsItemService;
import com.devgroup.jewelsys.service.dto.GemsItemDTO;
import com.devgroup.jewelsys.service.mapper.GemsItemMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link GemsItem}.
 */
@Service
@Transactional
public class GemsItemServiceImpl implements GemsItemService {

    private final Logger log = LoggerFactory.getLogger(GemsItemServiceImpl.class);

    private final GemsItemRepository gemsItemRepository;

    private final GemsItemMapper gemsItemMapper;

    public GemsItemServiceImpl(GemsItemRepository gemsItemRepository, GemsItemMapper gemsItemMapper) {
        this.gemsItemRepository = gemsItemRepository;
        this.gemsItemMapper = gemsItemMapper;
    }

    @Override
    public GemsItemDTO save(GemsItemDTO gemsItemDTO) {
        log.debug("Request to save GemsItem : {}", gemsItemDTO);
        GemsItem gemsItem = gemsItemMapper.toEntity(gemsItemDTO);
        gemsItem = gemsItemRepository.save(gemsItem);
        return gemsItemMapper.toDto(gemsItem);
    }

    @Override
    public Optional<GemsItemDTO> partialUpdate(GemsItemDTO gemsItemDTO) {
        log.debug("Request to partially update GemsItem : {}", gemsItemDTO);

        return gemsItemRepository
            .findById(gemsItemDTO.getId())
            .map(
                existingGemsItem -> {
                    gemsItemMapper.partialUpdate(existingGemsItem, gemsItemDTO);
                    return existingGemsItem;
                }
            )
            .map(gemsItemRepository::save)
            .map(gemsItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GemsItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GemsItems");
        return gemsItemRepository.findAll(pageable).map(gemsItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GemsItemDTO> findOne(Long id) {
        log.debug("Request to get GemsItem : {}", id);
        return gemsItemRepository.findById(id).map(gemsItemMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GemsItem : {}", id);
        gemsItemRepository.deleteById(id);
    }
}
