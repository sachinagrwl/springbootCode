package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.DestinationSetting;
import com.nscorp.obis.dto.DestinationSettingDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper( componentModel="spring")
public interface DestinationSettingMapper {
	
	DestinationSettingMapper INSTANCE = Mappers.getMapper(DestinationSettingMapper.class);
	DestinationSettingDTO destinationsettingToDestinationSettingDTO(DestinationSetting destinationsetting);
	DestinationSetting destinationsettingDTOToDestinationSetting(DestinationSettingDTO destinationsettinDTO);
}
