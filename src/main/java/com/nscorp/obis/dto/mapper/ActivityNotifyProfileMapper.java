package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.ActivityNotifyProfile;
import com.nscorp.obis.dto.ActivityNotifyProfileDTO;

@Mapper(componentModel = "spring")
public interface ActivityNotifyProfileMapper {

	ActivityNotifyProfileMapper INSTANCE = Mappers.getMapper(ActivityNotifyProfileMapper.class);

	ActivityNotifyProfile activityNotifyProfileDTOtoActivityNotifyProfile(
			ActivityNotifyProfileDTO activityNotifyProfileDTO);

	ActivityNotifyProfileDTO activityNotifyProfiletoActivityNotifyProfileDTO(
			ActivityNotifyProfile activityNotifyProfile);

}
