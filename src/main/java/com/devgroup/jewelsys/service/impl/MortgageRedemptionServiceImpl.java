package com.devgroup.jewelsys.service.impl;

import com.devgroup.jewelsys.domain.MortgageEntry;
import com.devgroup.jewelsys.domain.MortgageRedemption;
import com.devgroup.jewelsys.repository.MortgageEntryRepository;
import com.devgroup.jewelsys.repository.MortgageRedemptionRepository;
import com.devgroup.jewelsys.service.MortgageRedemptionService;
import com.devgroup.jewelsys.service.dto.MortgageRedemptionDTO;
import com.devgroup.jewelsys.service.mapper.MortgageRedemptionMapper;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MortgageRedemptionServiceImpl implements MortgageRedemptionService {

    private final MortgageRedemptionRepository mrRedemptionRepository;
    private final MortgageRedemptionMapper mrRedemptionMapper;
    private final MortgageEntryRepository mrEntryRepository;

    public MortgageRedemptionServiceImpl(
        MortgageRedemptionRepository mrRedemptionRepository,
        MortgageRedemptionMapper mrRedemptionMapper,
        MortgageEntryRepository mrEntryRepository
    ) {
        this.mrRedemptionRepository = mrRedemptionRepository;
        this.mrRedemptionMapper = mrRedemptionMapper;
        this.mrEntryRepository = mrEntryRepository;
    }

    @Override
    public MortgageRedemptionDTO save(MortgageRedemptionDTO mrRedemptionDTO) {
        Optional<MortgageEntry> meOptional = this.mrEntryRepository.findById(mrRedemptionDTO.getMortgageID());
        MortgageEntry meData = meOptional.get();
        //Updating mortgage status,"MR" as taken by customer
        meData.setMortgageStatus("MR");
        meData = this.mrEntryRepository.save(meData);
        //Inserting new redemption record
        MortgageRedemption entity = this.mrRedemptionMapper.toEntity(mrRedemptionDTO);
        entity.setDelFlg("N");
        entity = this.mrRedemptionRepository.save(entity);
        return this.mrRedemptionMapper.toDto(entity);
    }

    @Override
    public MortgageRedemptionDTO search(Long mortgageID) {
        MortgageRedemption entity = this.mrRedemptionRepository.findByMortgageID(mortgageID);
        return this.mrRedemptionMapper.toDto(entity);
    }
}
