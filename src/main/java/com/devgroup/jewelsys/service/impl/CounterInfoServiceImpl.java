package com.devgroup.jewelsys.service.impl;

import com.devgroup.jewelsys.domain.CounterInfo;
import com.devgroup.jewelsys.repository.CounterInfoRepository;
import com.devgroup.jewelsys.service.CounterInfoService;
import com.devgroup.jewelsys.service.dto.CounterInfoDTO;
import com.devgroup.jewelsys.service.mapper.CounterInfoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CounterInfo}.
 */
@Service
@Transactional
public class CounterInfoServiceImpl implements CounterInfoService {

    private final Logger log = LoggerFactory.getLogger(CounterInfoServiceImpl.class);

    private final CounterInfoRepository counterInfoRepository;

    private final CounterInfoMapper counterInfoMapper;

    public CounterInfoServiceImpl(CounterInfoRepository counterInfoRepository, CounterInfoMapper counterInfoMapper) {
        this.counterInfoRepository = counterInfoRepository;
        this.counterInfoMapper = counterInfoMapper;
    }

    @Override
    public CounterInfoDTO save(CounterInfoDTO counterInfoDTO) {
        log.debug("Request to save CounterInfo : {}", counterInfoDTO);
        CounterInfo counterInfo = counterInfoMapper.toEntity(counterInfoDTO);
        counterInfo = counterInfoRepository.save(counterInfo);
        return counterInfoMapper.toDto(counterInfo);
    }

    @Override
    public Optional<CounterInfoDTO> partialUpdate(CounterInfoDTO counterInfoDTO) {
        log.debug("Request to partially update CounterInfo : {}", counterInfoDTO);

        return counterInfoRepository
            .findById(counterInfoDTO.getId())
            .map(
                existingCounterInfo -> {
                    counterInfoMapper.partialUpdate(existingCounterInfo, counterInfoDTO);
                    return existingCounterInfo;
                }
            )
            .map(counterInfoRepository::save)
            .map(counterInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CounterInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CounterInfos");
        return counterInfoRepository.findAll(pageable).map(counterInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CounterInfoDTO> findOne(Long id) {
        log.debug("Request to get CounterInfo : {}", id);
        return counterInfoRepository.findById(id).map(counterInfoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CounterInfo : {}", id);
        counterInfoRepository.deleteById(id);
    }
}
