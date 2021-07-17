package com.devgroup.jewelsys.service.mapper;

import com.devgroup.jewelsys.domain.*;
import com.devgroup.jewelsys.service.dto.GoldItemGroupDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GoldItemGroup} and its DTO {@link GoldItemGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = { GoldTypeMapper.class })
public interface GoldItemGroupMapper extends EntityMapper<GoldItemGroupDTO, GoldItemGroup> {
    @Mapping(target = "goldType", source = "goldType", qualifiedByName = "name")
    GoldItemGroupDTO toDto(GoldItemGroup s);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    GoldItemGroupDTO toDtoName(GoldItemGroup goldItemGroup);
}
