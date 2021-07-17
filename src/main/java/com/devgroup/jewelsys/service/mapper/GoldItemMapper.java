package com.devgroup.jewelsys.service.mapper;

import com.devgroup.jewelsys.domain.*;
import com.devgroup.jewelsys.service.dto.GoldItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GoldItem} and its DTO {@link GoldItemDTO}.
 */
@Mapper(componentModel = "spring", uses = { GoldItemGroupMapper.class })
public interface GoldItemMapper extends EntityMapper<GoldItemDTO, GoldItem> {
    @Mapping(target = "goldItemGroup", source = "goldItemGroup", qualifiedByName = "name")
    GoldItemDTO toDto(GoldItem s);
}
