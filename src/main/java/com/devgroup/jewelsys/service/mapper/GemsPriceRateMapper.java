package com.devgroup.jewelsys.service.mapper;

import com.devgroup.jewelsys.domain.*;
import com.devgroup.jewelsys.service.dto.GemsPriceRateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GemsPriceRate} and its DTO {@link GemsPriceRateDTO}.
 */
@Mapper(componentModel = "spring", uses = { GemsItemMapper.class })
public interface GemsPriceRateMapper extends EntityMapper<GemsPriceRateDTO, GemsPriceRate> {}
