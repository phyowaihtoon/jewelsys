package com.devgroup.jewelsys.service.mapper;

import com.devgroup.jewelsys.domain.*;
import com.devgroup.jewelsys.service.dto.RoleMenuMapDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RoleMenuMap} and its DTO {@link RoleMenuMapDTO}.
 */
@Mapper(componentModel = "spring", uses = { MenuMapper.class })
public interface RoleMenuMapMapper extends EntityMapper<RoleMenuMapDTO, RoleMenuMap> {}
