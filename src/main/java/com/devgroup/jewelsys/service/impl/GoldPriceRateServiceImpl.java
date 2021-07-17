package com.devgroup.jewelsys.service.impl;

import com.devgroup.jewelsys.domain.GoldPriceRate;
import com.devgroup.jewelsys.repository.GoldPriceRateRepository;
import com.devgroup.jewelsys.service.GoldPriceRateService;
import com.devgroup.jewelsys.service.dto.GoldPriceRateDTO;
import com.devgroup.jewelsys.service.mapper.GoldPriceRateMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link GoldPriceRate}.
 */
@Service
@Transactional
public class GoldPriceRateServiceImpl implements GoldPriceRateService {

    private final Logger log = LoggerFactory.getLogger(GoldPriceRateServiceImpl.class);

    private final GoldPriceRateRepository goldPriceRateRepository;

    private final GoldPriceRateMapper goldPriceRateMapper;

    public GoldPriceRateServiceImpl(GoldPriceRateRepository goldPriceRateRepository, GoldPriceRateMapper goldPriceRateMapper) {
        this.goldPriceRateRepository = goldPriceRateRepository;
        this.goldPriceRateMapper = goldPriceRateMapper;
    }

    @Override
    public GoldPriceRateDTO save(GoldPriceRateDTO goldPriceRateDTO) {
        log.debug("Request to save GoldPriceRate : {}", goldPriceRateDTO);
        GoldPriceRate goldPriceRate = goldPriceRateMapper.toEntity(goldPriceRateDTO);
        goldPriceRate = goldPriceRateRepository.save(goldPriceRate);
        return goldPriceRateMapper.toDto(goldPriceRate);
    }

    @Override
    public Optional<GoldPriceRateDTO> partialUpdate(GoldPriceRateDTO goldPriceRateDTO) {
        log.debug("Request to partially update GoldPriceRate : {}", goldPriceRateDTO);

        return goldPriceRateRepository
            .findById(goldPriceRateDTO.getId())
            .map(
                existingGoldPriceRate -> {
                    goldPriceRateMapper.partialUpdate(existingGoldPriceRate, goldPriceRateDTO);
                    return existingGoldPriceRate;
                }
            )
            .map(goldPriceRateRepository::save)
            .map(goldPriceRateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GoldPriceRateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GoldPriceRates");
        return goldPriceRateRepository.findAll(pageable).map(goldPriceRateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GoldPriceRateDTO> findOne(Long id) {
        log.debug("Request to get GoldPriceRate : {}", id);
        return goldPriceRateRepository.findById(id).map(goldPriceRateMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GoldPriceRate : {}", id);
        goldPriceRateRepository.deleteById(id);
    }
}
