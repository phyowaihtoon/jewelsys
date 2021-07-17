package com.devgroup.jewelsys.service.mapper;

import com.devgroup.jewelsys.domain.*;
import com.devgroup.jewelsys.service.dto.MenuDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Menu} and its DTO {@link MenuDTO}.
 */
@Mapper(componentModel = "spring", uses = { MenuGroupMapper.class })
public interface MenuMapper extends EntityMapper<MenuDTO, Menu> {}
