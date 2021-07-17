package com.devgroup.jewelsys.service.mapper;

import com.devgroup.jewelsys.domain.*;
import com.devgroup.jewelsys.service.dto.DataCategoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DataCategory} and its DTO {@link DataCategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DataCategoryMapper extends EntityMapper<DataCategoryDTO, DataCategory> {}
