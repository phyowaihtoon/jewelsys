package com.devgroup.jewelsys.service.impl;

import com.devgroup.jewelsys.domain.GemsPriceRate;
import com.devgroup.jewelsys.repository.GemsPriceRateRepository;
import com.devgroup.jewelsys.service.GemsPriceRateService;
import com.devgroup.jewelsys.service.dto.GemsPriceRateDTO;
import com.devgroup.jewelsys.service.mapper.GemsPriceRateMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link GemsPriceRate}.
 */
@Service
@Transactional
public class GemsPriceRateServiceImpl implements GemsPriceRateService {

    private final Logger log = LoggerFactory.getLogger(GemsPriceRateServiceImpl.class);

    private final GemsPriceRateRepository gemsPriceRateRepository;

    private final GemsPriceRateMapper gemsPriceRateMapper;

    public GemsPriceRateServiceImpl(GemsPriceRateRepository gemsPriceRateRepository, GemsPriceRateMapper gemsPriceRateMapper) {
        this.gemsPriceRateRepository = gemsPriceRateRepository;
        this.gemsPriceRateMapper = gemsPriceRateMapper;
    }

    @Override
    public GemsPriceRateDTO save(GemsPriceRateDTO gemsPriceRateDTO) {
        log.debug("Request to save GemsPriceRate : {}", gemsPriceRateDTO);
        GemsPriceRate gemsPriceRate = gemsPriceRateMapper.toEntity(gemsPriceRateDTO);
        gemsPriceRate = gemsPriceRateRepository.save(gemsPriceRate);
        return gemsPriceRateMapper.toDto(gemsPriceRate);
    }

    @Override
    public Optional<GemsPriceRateDTO> partialUpdate(GemsPriceRateDTO gemsPriceRateDTO) {
        log.debug("Request to partially update GemsPriceRate : {}", gemsPriceRateDTO);

        return gemsPriceRateRepository
            .findById(gemsPriceRateDTO.getId())
            .map(
                existingGemsPriceRate -> {
                    gemsPriceRateMapper.partialUpdate(existingGemsPriceRate, gemsPriceRateDTO);
                    return existingGemsPriceRate;
                }
            )
            .map(gemsPriceRateRepository::save)
            .map(gemsPriceRateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GemsPriceRateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GemsPriceRates");
        return gemsPriceRateRepository.findAll(pageable).map(gemsPriceRateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GemsPriceRateDTO> findOne(Long id) {
        log.debug("Request to get GemsPriceRate : {}", id);
        return gemsPriceRateRepository.findById(id).map(gemsPriceRateMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GemsPriceRate : {}", id);
        gemsPriceRateRepository.deleteById(id);
    }
}
