package com.devgroup.jewelsys.service.mapper;

import com.devgroup.jewelsys.domain.*;
import com.devgroup.jewelsys.service.dto.CounterInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CounterInfo} and its DTO {@link CounterInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CounterInfoMapper extends EntityMapper<CounterInfoDTO, CounterInfo> {}
