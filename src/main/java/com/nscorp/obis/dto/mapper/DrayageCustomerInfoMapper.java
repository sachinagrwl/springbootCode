package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.DrayageCustomerInfo;
import com.nscorp.obis.dto.DrayageCustomerInfoDTO;

@Mapper(componentModel = "spring")
public interface DrayageCustomerInfoMapper {
	DrayageCustomerInfoMapper INSTANCE = Mappers.getMapper(DrayageCustomerInfoMapper.class);

	DrayageCustomerInfoDTO drayageCustomerInfoToDrayageCustomerInfoDTO(DrayageCustomerInfo drayageCustomerInfo);

	DrayageCustomerInfo drayageCustomerInfoDtoToDrayageCustomerInfo(DrayageCustomerInfoDTO drayageCustomerInfoDto);
}
