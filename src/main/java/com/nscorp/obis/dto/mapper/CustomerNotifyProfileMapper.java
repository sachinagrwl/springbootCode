package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.CustomerNotifyProfile;
import com.nscorp.obis.dto.CustomerNotifyProfileDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerNotifyProfileMapper {
	CustomerNotifyProfileMapper INSTANCE = Mappers.getMapper(CustomerNotifyProfileMapper.class);
	CustomerNotifyProfileDTO customerNotifyProfileToCustomerNotifyProfileDto(CustomerNotifyProfile customerNotifyProfile);
	CustomerNotifyProfile customerNotifyProfileDtoToCustomerNotifyProfile(CustomerNotifyProfileDTO customerNotifyProfileDto);
}
