package com.devgroup.jewelsys.service.impl;

import com.devgroup.jewelsys.domain.GoldItem;
import com.devgroup.jewelsys.repository.GoldItemRepository;
import com.devgroup.jewelsys.service.GoldItemService;
import com.devgroup.jewelsys.service.dto.GoldItemDTO;
import com.devgroup.jewelsys.service.mapper.GoldItemMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link GoldItem}.
 */
@Service
@Transactional
public class GoldItemServiceImpl implements GoldItemService {

    private final Logger log = LoggerFactory.getLogger(GoldItemServiceImpl.class);

    private final GoldItemRepository goldItemRepository;

    private final GoldItemMapper goldItemMapper;

    public GoldItemServiceImpl(GoldItemRepository goldItemRepository, GoldItemMapper goldItemMapper) {
        this.goldItemRepository = goldItemRepository;
        this.goldItemMapper = goldItemMapper;
    }

    @Override
    public GoldItemDTO save(GoldItemDTO goldItemDTO) {
        log.debug("Request to save GoldItem : {}", goldItemDTO);
        GoldItem goldItem = goldItemMapper.toEntity(goldItemDTO);
        goldItem = goldItemRepository.save(goldItem);
        return goldItemMapper.toDto(goldItem);
    }

    @Override
    public Optional<GoldItemDTO> partialUpdate(GoldItemDTO goldItemDTO) {
        log.debug("Request to partially update GoldItem : {}", goldItemDTO);

        return goldItemRepository
            .findById(goldItemDTO.getId())
            .map(
                existingGoldItem -> {
                    goldItemMapper.partialUpdate(existingGoldItem, goldItemDTO);
                    return existingGoldItem;
                }
            )
            .map(goldItemRepository::save)
            .map(goldItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GoldItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GoldItems");
        return goldItemRepository.findAll(pageable).map(goldItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GoldItemDTO> findOne(Long id) {
        log.debug("Request to get GoldItem : {}", id);
        return goldItemRepository.findById(id).map(goldItemMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GoldItem : {}", id);
        goldItemRepository.deleteById(id);
    }
}
