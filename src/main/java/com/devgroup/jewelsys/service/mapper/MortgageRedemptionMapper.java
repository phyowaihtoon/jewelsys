package com.devgroup.jewelsys.service.mapper;

import com.devgroup.jewelsys.domain.MortgageRedemption;
import com.devgroup.jewelsys.service.dto.MortgageRedemptionDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link MortgageEntry} and its DTO {@link MortgageEntryDTO}.
 */

@Mapper(componentModel = "spring", uses = {})
public interface MortgageRedemptionMapper extends EntityMapper<MortgageRedemptionDTO, MortgageRedemption> {}
