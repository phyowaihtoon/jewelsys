package com.devgroup.jewelsys.service.impl;

import com.devgroup.jewelsys.domain.GemsType;
import com.devgroup.jewelsys.repository.GemsTypeRepository;
import com.devgroup.jewelsys.service.GemsTypeService;
import com.devgroup.jewelsys.service.dto.GemsTypeDTO;
import com.devgroup.jewelsys.service.mapper.GemsTypeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link GemsType}.
 */
@Service
@Transactional
public class GemsTypeServiceImpl implements GemsTypeService {

    private final Logger log = LoggerFactory.getLogger(GemsTypeServiceImpl.class);

    private final GemsTypeRepository gemsTypeRepository;

    private final GemsTypeMapper gemsTypeMapper;

    public GemsTypeServiceImpl(GemsTypeRepository gemsTypeRepository, GemsTypeMapper gemsTypeMapper) {
        this.gemsTypeRepository = gemsTypeRepository;
        this.gemsTypeMapper = gemsTypeMapper;
    }

    @Override
    public GemsTypeDTO save(GemsTypeDTO gemsTypeDTO) {
        log.debug("Request to save GemsType : {}", gemsTypeDTO);
        GemsType gemsType = gemsTypeMapper.toEntity(gemsTypeDTO);
        gemsType = gemsTypeRepository.save(gemsType);
        return gemsTypeMapper.toDto(gemsType);
    }

    @Override
    public Optional<GemsTypeDTO> partialUpdate(GemsTypeDTO gemsTypeDTO) {
        log.debug("Request to partially update GemsType : {}", gemsTypeDTO);

        return gemsTypeRepository
            .findById(gemsTypeDTO.getId())
            .map(
                existingGemsType -> {
                    gemsTypeMapper.partialUpdate(existingGemsType, gemsTypeDTO);
                    return existingGemsType;
                }
            )
            .map(gemsTypeRepository::save)
            .map(gemsTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GemsTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GemsTypes");
        return gemsTypeRepository.findAll(pageable).map(gemsTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GemsTypeDTO> findOne(Long id) {
        log.debug("Request to get GemsType : {}", id);
        return gemsTypeRepository.findById(id).map(gemsTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GemsType : {}", id);
        gemsTypeRepository.deleteById(id);
    }
}
