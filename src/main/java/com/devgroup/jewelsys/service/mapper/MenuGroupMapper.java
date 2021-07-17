package com.devgroup.jewelsys.service.mapper;

import com.devgroup.jewelsys.domain.*;
import com.devgroup.jewelsys.service.dto.MenuGroupDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MenuGroup} and its DTO {@link MenuGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MenuGroupMapper extends EntityMapper<MenuGroupDTO, MenuGroup> {}
