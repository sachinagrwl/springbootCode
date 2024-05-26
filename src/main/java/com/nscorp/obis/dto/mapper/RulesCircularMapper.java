package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.RulesCircular;
import com.nscorp.obis.dto.RulesCircularDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RulesCircularMapper {

    RulesCircularMapper INSTANCE = Mappers.getMapper(RulesCircularMapper.class);
    RulesCircularDTO RulesCircularToRulesCircularDTO(RulesCircular RulesCircular);
    RulesCircular RulesCircularDTOToRulesCircular(RulesCircularDTO RulesCircularDTO);
}
