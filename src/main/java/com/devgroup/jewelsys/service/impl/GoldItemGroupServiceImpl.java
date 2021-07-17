package com.devgroup.jewelsys.service.impl;

import com.devgroup.jewelsys.domain.GoldItemGroup;
import com.devgroup.jewelsys.repository.GoldItemGroupRepository;
import com.devgroup.jewelsys.service.GoldItemGroupService;
import com.devgroup.jewelsys.service.dto.GoldItemGroupDTO;
import com.devgroup.jewelsys.service.mapper.GoldItemGroupMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link GoldItemGroup}.
 */
@Service
@Transactional
public class GoldItemGroupServiceImpl implements GoldItemGroupService {

    private final Logger log = LoggerFactory.getLogger(GoldItemGroupServiceImpl.class);

    private final GoldItemGroupRepository goldItemGroupRepository;

    private final GoldItemGroupMapper goldItemGroupMapper;

    public GoldItemGroupServiceImpl(GoldItemGroupRepository goldItemGroupRepository, GoldItemGroupMapper goldItemGroupMapper) {
        this.goldItemGroupRepository = goldItemGroupRepository;
        this.goldItemGroupMapper = goldItemGroupMapper;
    }

    @Override
    public GoldItemGroupDTO save(GoldItemGroupDTO goldItemGroupDTO) {
        log.debug("Request to save GoldItemGroup : {}", goldItemGroupDTO);
        GoldItemGroup goldItemGroup = goldItemGroupMapper.toEntity(goldItemGroupDTO);
        goldItemGroup = goldItemGroupRepository.save(goldItemGroup);
        return goldItemGroupMapper.toDto(goldItemGroup);
    }

    @Override
    public Optional<GoldItemGroupDTO> partialUpdate(GoldItemGroupDTO goldItemGroupDTO) {
        log.debug("Request to partially update GoldItemGroup : {}", goldItemGroupDTO);

        return goldItemGroupRepository
            .findById(goldItemGroupDTO.getId())
            .map(
                existingGoldItemGroup -> {
                    goldItemGroupMapper.partialUpdate(existingGoldItemGroup, goldItemGroupDTO);
                    return existingGoldItemGroup;
                }
            )
            .map(goldItemGroupRepository::save)
            .map(goldItemGroupMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GoldItemGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GoldItemGroups");
        return goldItemGroupRepository.findAll(pageable).map(goldItemGroupMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GoldItemGroupDTO> findOne(Long id) {
        log.debug("Request to get GoldItemGroup : {}", id);
        return goldItemGroupRepository.findById(id).map(goldItemGroupMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GoldItemGroup : {}", id);
        goldItemGroupRepository.deleteById(id);
    }
}
