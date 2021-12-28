package com.devgroup.jewelsys.service.mapper;

import com.devgroup.jewelsys.domain.*;
import com.devgroup.jewelsys.service.dto.MortgageEntryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MortgageEntry} and its DTO {@link MortgageEntryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MortgageEntryMapper extends EntityMapper<MortgageEntryDTO, MortgageEntry> {}
