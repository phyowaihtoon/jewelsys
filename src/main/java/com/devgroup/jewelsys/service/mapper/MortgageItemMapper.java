package com.devgroup.jewelsys.service.mapper;

import com.devgroup.jewelsys.domain.*;
import com.devgroup.jewelsys.service.dto.MortgageItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MortgageItem} and its DTO {@link MortgageItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MortgageItemMapper extends EntityMapper<MortgageItemDTO, MortgageItem> {}
