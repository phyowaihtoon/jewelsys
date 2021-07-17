package com.devgroup.jewelsys.service.mapper;

import com.devgroup.jewelsys.domain.*;
import com.devgroup.jewelsys.service.dto.GoldTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GoldType} and its DTO {@link GoldTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GoldTypeMapper extends EntityMapper<GoldTypeDTO, GoldType> {
    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    GoldTypeDTO toDtoName(GoldType goldType);
}
