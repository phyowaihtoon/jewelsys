package com.devgroup.jewelsys.service.mapper;

import com.devgroup.jewelsys.domain.*;
import com.devgroup.jewelsys.service.dto.GoldPriceRateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GoldPriceRate} and its DTO {@link GoldPriceRateDTO}.
 */
@Mapper(componentModel = "spring", uses = { GoldTypeMapper.class })
public interface GoldPriceRateMapper extends EntityMapper<GoldPriceRateDTO, GoldPriceRate> {
    @Mapping(target = "goldType", source = "goldType", qualifiedByName = "name")
    GoldPriceRateDTO toDto(GoldPriceRate s);
}
