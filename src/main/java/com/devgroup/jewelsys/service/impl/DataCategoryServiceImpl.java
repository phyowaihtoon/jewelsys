package com.devgroup.jewelsys.service.impl;

import com.devgroup.jewelsys.domain.DataCategory;
import com.devgroup.jewelsys.repository.DataCategoryRepository;
import com.devgroup.jewelsys.service.DataCategoryService;
import com.devgroup.jewelsys.service.dto.DataCategoryDTO;
import com.devgroup.jewelsys.service.mapper.DataCategoryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DataCategory}.
 */
@Service
@Transactional
public class DataCategoryServiceImpl implements DataCategoryService {

    private final Logger log = LoggerFactory.getLogger(DataCategoryServiceImpl.class);

    private final DataCategoryRepository dataCategoryRepository;

    private final DataCategoryMapper dataCategoryMapper;

    public DataCategoryServiceImpl(DataCategoryRepository dataCategoryRepository, DataCategoryMapper dataCategoryMapper) {
        this.dataCategoryRepository = dataCategoryRepository;
        this.dataCategoryMapper = dataCategoryMapper;
    }

    @Override
    public DataCategoryDTO save(DataCategoryDTO dataCategoryDTO) {
        log.debug("Request to save DataCategory : {}", dataCategoryDTO);
        DataCategory dataCategory = dataCategoryMapper.toEntity(dataCategoryDTO);
        dataCategory = dataCategoryRepository.save(dataCategory);
        return dataCategoryMapper.toDto(dataCategory);
    }

    @Override
    public Optional<DataCategoryDTO> partialUpdate(DataCategoryDTO dataCategoryDTO) {
        log.debug("Request to partially update DataCategory : {}", dataCategoryDTO);

        return dataCategoryRepository
            .findById(dataCategoryDTO.getId())
            .map(
                existingDataCategory -> {
                    dataCategoryMapper.partialUpdate(existingDataCategory, dataCategoryDTO);
                    return existingDataCategory;
                }
            )
            .map(dataCategoryRepository::save)
            .map(dataCategoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DataCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DataCategories");
        return dataCategoryRepository.findAll(pageable).map(dataCategoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DataCategoryDTO> findOne(Long id) {
        log.debug("Request to get DataCategory : {}", id);
        return dataCategoryRepository.findById(id).map(dataCategoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DataCategory : {}", id);
        dataCategoryRepository.deleteById(id);
    }
}
