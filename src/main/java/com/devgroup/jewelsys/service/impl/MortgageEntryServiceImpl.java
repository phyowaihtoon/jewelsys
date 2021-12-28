package com.devgroup.jewelsys.service.impl;

import com.devgroup.jewelsys.domain.MortgageEntry;
import com.devgroup.jewelsys.repository.MortgageEntryRepository;
import com.devgroup.jewelsys.service.MortgageEntryService;
import com.devgroup.jewelsys.service.dto.MortgageEntryDTO;
import com.devgroup.jewelsys.service.mapper.MortgageEntryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MortgageEntry}.
 */
@Service
@Transactional
public class MortgageEntryServiceImpl implements MortgageEntryService {

    private final Logger log = LoggerFactory.getLogger(MortgageEntryServiceImpl.class);

    private final MortgageEntryRepository mortgageEntryRepository;

    private final MortgageEntryMapper mortgageEntryMapper;

    public MortgageEntryServiceImpl(MortgageEntryRepository mortgageEntryRepository, MortgageEntryMapper mortgageEntryMapper) {
        this.mortgageEntryRepository = mortgageEntryRepository;
        this.mortgageEntryMapper = mortgageEntryMapper;
    }

    @Override
    public MortgageEntryDTO save(MortgageEntryDTO mortgageEntryDTO) {
        log.debug("Request to save MortgageEntry : {}", mortgageEntryDTO);
        MortgageEntry mortgageEntry = mortgageEntryMapper.toEntity(mortgageEntryDTO);
        mortgageEntry = mortgageEntryRepository.save(mortgageEntry);
        return mortgageEntryMapper.toDto(mortgageEntry);
    }

    @Override
    public Optional<MortgageEntryDTO> partialUpdate(MortgageEntryDTO mortgageEntryDTO) {
        log.debug("Request to partially update MortgageEntry : {}", mortgageEntryDTO);

        return mortgageEntryRepository
            .findById(mortgageEntryDTO.getId())
            .map(
                existingMortgageEntry -> {
                    mortgageEntryMapper.partialUpdate(existingMortgageEntry, mortgageEntryDTO);
                    return existingMortgageEntry;
                }
            )
            .map(mortgageEntryRepository::save)
            .map(mortgageEntryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MortgageEntryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MortgageEntries");
        return mortgageEntryRepository.findAll(pageable).map(mortgageEntryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MortgageEntryDTO> findOne(Long id) {
        log.debug("Request to get MortgageEntry : {}", id);
        return mortgageEntryRepository.findById(id).map(mortgageEntryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MortgageEntry : {}", id);
        mortgageEntryRepository.deleteById(id);
    }
}
