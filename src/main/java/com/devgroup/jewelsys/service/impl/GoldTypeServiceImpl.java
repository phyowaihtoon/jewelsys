package com.devgroup.jewelsys.service.impl;

import com.devgroup.jewelsys.domain.GoldType;
import com.devgroup.jewelsys.repository.GoldTypeRepository;
import com.devgroup.jewelsys.service.GoldTypeService;
import com.devgroup.jewelsys.service.dto.GoldTypeDTO;
import com.devgroup.jewelsys.service.mapper.GoldTypeMapper;
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
 * Service Implementation for managing {@link GoldType}.
 */
@Service
@Transactional
public class GoldTypeServiceImpl implements GoldTypeService {

    private final Logger log = LoggerFactory.getLogger(GoldTypeServiceImpl.class);

    private final GoldTypeRepository goldTypeRepository;

    private final GoldTypeMapper goldTypeMapper;

    public GoldTypeServiceImpl(GoldTypeRepository goldTypeRepository, GoldTypeMapper goldTypeMapper) {
        this.goldTypeRepository = goldTypeRepository;
        this.goldTypeMapper = goldTypeMapper;
    }

    @Override
    public GoldTypeDTO save(GoldTypeDTO goldTypeDTO) {
        log.debug("Request to save GoldType : {}", goldTypeDTO);
        GoldType goldType = goldTypeMapper.toEntity(goldTypeDTO);
        goldType = goldTypeRepository.save(goldType);
        return goldTypeMapper.toDto(goldType);
    }

    @Override
    public Optional<GoldTypeDTO> partialUpdate(GoldTypeDTO goldTypeDTO) {
        log.debug("Request to partially update GoldType : {}", goldTypeDTO);

        return goldTypeRepository
            .findById(goldTypeDTO.getId())
            .map(
                existingGoldType -> {
                    goldTypeMapper.partialUpdate(existingGoldType, goldTypeDTO);
                    return existingGoldType;
                }
            )
            .map(goldTypeRepository::save)
            .map(goldTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GoldTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GoldTypes");
        return goldTypeRepository.findAll(pageable).map(goldTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GoldTypeDTO> findOne(Long id) {
        log.debug("Request to get GoldType : {}", id);
        return goldTypeRepository.findById(id).map(goldTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GoldType : {}", id);
        goldTypeRepository.deleteById(id);
    }

    @Override
    public List<GoldTypeDTO> loadAll() {
        List<GoldTypeDTO> dtoList = new ArrayList<GoldTypeDTO>();
        List<GoldType> goldTypeList = goldTypeRepository.findAll();
        for (GoldType data : goldTypeList) {
            GoldTypeDTO dto = goldTypeMapper.toDto(data);
            dtoList.add(dto);
        }
        return dtoList;
    }
}
