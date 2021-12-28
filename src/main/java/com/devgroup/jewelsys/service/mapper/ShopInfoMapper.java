package com.devgroup.jewelsys.service.mapper;

import com.devgroup.jewelsys.domain.*;
import com.devgroup.jewelsys.service.dto.ShopInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ShopInfo} and its DTO {@link ShopInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ShopInfoMapper extends EntityMapper<ShopInfoDTO, ShopInfo> {}
