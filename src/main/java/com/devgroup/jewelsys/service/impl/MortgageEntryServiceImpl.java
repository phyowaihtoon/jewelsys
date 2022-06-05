package com.devgroup.jewelsys.service.impl;

import com.devgroup.jewelsys.domain.DataCategory;
import com.devgroup.jewelsys.domain.MortgageEntry;
import com.devgroup.jewelsys.domain.MortgageItem;
import com.devgroup.jewelsys.repository.DataCategoryRepository;
import com.devgroup.jewelsys.repository.MortgageEntryRepository;
import com.devgroup.jewelsys.repository.MortgageItemRepository;
import com.devgroup.jewelsys.service.MortgageEntryService;
import com.devgroup.jewelsys.service.dto.CommonDTO;
import com.devgroup.jewelsys.service.dto.MortgageEntryDTO;
import com.devgroup.jewelsys.service.mapper.MortgageEntryMapper;
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
 * Service Implementation for managing {@link MortgageEntry}.
 */
@Service
@Transactional
public class MortgageEntryServiceImpl implements MortgageEntryService {

    private final Logger log = LoggerFactory.getLogger(MortgageEntryServiceImpl.class);

    private final MortgageEntryRepository mortgageEntryRepository;

    private final MortgageItemRepository mortgageItemRepository;

    private final DataCategoryRepository dataCategoryRepository;

    private final MortgageEntryMapper mortgageEntryMapper;

    public MortgageEntryServiceImpl(
        MortgageEntryRepository mortgageEntryRepository,
        MortgageEntryMapper mortgageEntryMapper,
        MortgageItemRepository mortgageItemRepository,
        DataCategoryRepository dataCategoryRepository
    ) {
        this.mortgageEntryRepository = mortgageEntryRepository;
        this.mortgageEntryMapper = mortgageEntryMapper;
        this.mortgageItemRepository = mortgageItemRepository;
        this.dataCategoryRepository = dataCategoryRepository;
    }

    @Override
    public MortgageEntryDTO save(MortgageEntryDTO mortgageEntryDTO) {
        log.debug("Request to save MortgageEntry : {}", mortgageEntryDTO);
        mortgageEntryDTO.setDelFlg("N");
        mortgageEntryDTO.setMortgageStatus("MN");
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
    public CommonDTO findAll(Pageable pageable) {
        log.debug("Request to get all MortgageEntries");
        CommonDTO commonDTO = new CommonDTO();
        Page<MortgageEntryDTO> page = mortgageEntryRepository.findAll(pageable).map(mortgageEntryMapper::toDto);
        commonDTO.setmEntryPage(page);
        List<MortgageEntryDTO> mortgageList = page.getContent();
        if (page != null && mortgageList != null && mortgageList.size() > 0) {
            List<MortgageEntryDTO> updatedList = new ArrayList<MortgageEntryDTO>();
            for (MortgageEntryDTO mortgage : mortgageList) {
                MortgageItem item = mortgageItemRepository.findByCode(mortgage.getItemCode());
                mortgage.setItemName(item.getItemName());
                DataCategory dcMortStatus = dataCategoryRepository.findByCategoryCode(mortgage.getMortgageStatus());
                mortgage.setMortStatusDesc(dcMortStatus.getCategoryDesc());
                updatedList.add(mortgage);
            }
            commonDTO.setUpdatedMEList(updatedList);
        }
        return commonDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MortgageEntryDTO> findOne(Long id) {
        log.debug("Request to get MortgageEntry : {}", id);
        Optional<MortgageEntryDTO> dtoOptional = mortgageEntryRepository.findById(id).map(mortgageEntryMapper::toDto);
        if (dtoOptional != null) {
            //Extracting Mortgage Item Description
            MortgageItem item = mortgageItemRepository.findByCode(dtoOptional.get().getItemCode());
            dtoOptional.get().setItemName(item.getItemName());
            //Extracting Mortgage Status Description
            DataCategory dcMortStatus = dataCategoryRepository.findByCategoryCode(dtoOptional.get().getMortgageStatus());
            dtoOptional.get().setMortStatusDesc(dcMortStatus.getCategoryDesc());
            //Extracting MM Year Description
            DataCategory dcMMYear = dataCategoryRepository.findByCategoryCode(dtoOptional.get().getMmYear());
            dtoOptional.get().setMmYearDesc(dcMMYear != null ? dcMMYear.getCategoryDesc() : "");
            //Extracting MM Month Description
            DataCategory dcMMMonth = dataCategoryRepository.findByCategoryCode(dtoOptional.get().getMmMonth());
            dtoOptional.get().setMmMonthDesc(dcMMMonth != null ? dcMMMonth.getCategoryDesc() : "");
            //Extracting MM Day Group Description
            DataCategory dcMMDayGroup = dataCategoryRepository.findByCategoryCode(dtoOptional.get().getMmDayGR());
            dtoOptional.get().setMmDayGRDesc(dcMMDayGroup != null ? dcMMDayGroup.getCategoryDesc() : "");
            //Extracting MM Day Description
            DataCategory dcMMDay = dataCategoryRepository.findByCategoryCode(dtoOptional.get().getMmDay());
            dtoOptional.get().setMmDayDesc(dcMMDay != null ? dcMMDay.getCategoryDesc() : "");
        }
        return dtoOptional;
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MortgageEntry : {}", id);
        mortgageEntryRepository.deleteById(id);
    }

    @Override
    public Optional<MortgageEntryDTO> findOneByMortgageID(String id) {
        log.debug("Request to get MortgageEntry : {}", id);
        Optional<MortgageEntryDTO> dtoOptional = mortgageEntryRepository.findById(Long.valueOf(id)).map(mortgageEntryMapper::toDto);
        if (dtoOptional != null) {
            //Extracting Mortgage Item Description
            MortgageItem item = mortgageItemRepository.findByCode(dtoOptional.get().getItemCode());
            dtoOptional.get().setItemName(item.getItemName());
            //Extracting Mortgage Status Description
            DataCategory dcMortStatus = dataCategoryRepository.findByCategoryCode(dtoOptional.get().getMortgageStatus());
            dtoOptional.get().setMortStatusDesc(dcMortStatus.getCategoryDesc());
            //Extracting MM Year Description
            DataCategory dcMMYear = dataCategoryRepository.findByCategoryCode(dtoOptional.get().getMmYear());
            dtoOptional.get().setMmYearDesc(dcMMYear != null ? dcMMYear.getCategoryDesc() : "");
            //Extracting MM Month Description
            DataCategory dcMMMonth = dataCategoryRepository.findByCategoryCode(dtoOptional.get().getMmMonth());
            dtoOptional.get().setMmMonthDesc(dcMMMonth != null ? dcMMMonth.getCategoryDesc() : "");
            //Extracting MM Day Group Description
            DataCategory dcMMDayGroup = dataCategoryRepository.findByCategoryCode(dtoOptional.get().getMmDayGR());
            dtoOptional.get().setMmDayGRDesc(dcMMDayGroup != null ? dcMMDayGroup.getCategoryDesc() : "");
            //Extracting MM Day Description
            DataCategory dcMMDay = dataCategoryRepository.findByCategoryCode(dtoOptional.get().getMmDay());
            dtoOptional.get().setMmDayDesc(dcMMDay != null ? dcMMDay.getCategoryDesc() : "");
        }
        return dtoOptional;
    }
}
