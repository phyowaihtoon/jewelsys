package com.devgroup.jewelsys.service.mapper;

import com.devgroup.jewelsys.domain.*;
import com.devgroup.jewelsys.service.dto.GemsTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GemsType} and its DTO {@link GemsTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GemsTypeMapper extends EntityMapper<GemsTypeDTO, GemsType> {}
