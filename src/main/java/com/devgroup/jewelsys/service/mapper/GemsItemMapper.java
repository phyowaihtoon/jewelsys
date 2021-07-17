package com.devgroup.jewelsys.service.mapper;

import com.devgroup.jewelsys.domain.*;
import com.devgroup.jewelsys.service.dto.GemsItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GemsItem} and its DTO {@link GemsItemDTO}.
 */
@Mapper(componentModel = "spring", uses = { GemsTypeMapper.class })
public interface GemsItemMapper extends EntityMapper<GemsItemDTO, GemsItem> {}
