package com.devgroup.jewelsys.service.impl;

import com.devgroup.jewelsys.domain.MortgageItem;
import com.devgroup.jewelsys.repository.MortgageItemRepository;
import com.devgroup.jewelsys.service.MortgageItemService;
import com.devgroup.jewelsys.service.dto.MortgageItemDTO;
import com.devgroup.jewelsys.service.mapper.MortgageItemMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MortgageItem}.
 */
@Service
@Transactional
public class MortgageItemServiceImpl implements MortgageItemService {

    private final Logger log = LoggerFactory.getLogger(MortgageItemServiceImpl.class);

    private final MortgageItemRepository mortgageItemRepository;

    private final MortgageItemMapper mortgageItemMapper;

    public MortgageItemServiceImpl(MortgageItemRepository mortgageItemRepository, MortgageItemMapper mortgageItemMapper) {
        this.mortgageItemRepository = mortgageItemRepository;
        this.mortgageItemMapper = mortgageItemMapper;
    }

    @Override
    public MortgageItemDTO save(MortgageItemDTO mortgageItemDTO) {
        log.debug("Request to save MortgageItem : {}", mortgageItemDTO);
        MortgageItem mortgageItem = mortgageItemMapper.toEntity(mortgageItemDTO);
        mortgageItem = mortgageItemRepository.save(mortgageItem);
        return mortgageItemMapper.toDto(mortgageItem);
    }

    @Override
    public Optional<MortgageItemDTO> partialUpdate(MortgageItemDTO mortgageItemDTO) {
        log.debug("Request to partially update MortgageItem : {}", mortgageItemDTO);

        return mortgageItemRepository
            .findById(mortgageItemDTO.getId())
            .map(
                existingMortgageItem -> {
                    mortgageItemMapper.partialUpdate(existingMortgageItem, mortgageItemDTO);
                    return existingMortgageItem;
                }
            )
            .map(mortgageItemRepository::save)
            .map(mortgageItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MortgageItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MortgageItems");
        return mortgageItemRepository.findAll(pageable).map(mortgageItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MortgageItemDTO> findOne(Long id) {
        log.debug("Request to get MortgageItem : {}", id);
        return mortgageItemRepository.findById(id).map(mortgageItemMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MortgageItem : {}", id);
        mortgageItemRepository.deleteById(id);
    }

    @Override
    public List<MortgageItemDTO> loadAll() {
        List<MortgageItemDTO> dtoList = new ArrayList<MortgageItemDTO>();
        List<MortgageItem> itemList = mortgageItemRepository.findAll();
        for (MortgageItem data : itemList) {
            MortgageItemDTO dto = mortgageItemMapper.toDto(data);
            dtoList.add(dto);
        }
        return dtoList;
    }
}
