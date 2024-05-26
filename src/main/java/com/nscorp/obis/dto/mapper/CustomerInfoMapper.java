package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.CustomerInfo;
import com.nscorp.obis.dto.CustomerInfoDTO;

@Mapper(componentModel = "spring")
public interface CustomerInfoMapper {
	
	CustomerInfoMapper INSTANCE=Mappers.getMapper(CustomerInfoMapper.class);
	
	CustomerInfoDTO customerInfoToCustomerInfoDTO(CustomerInfo customerInfo);
	
	CustomerInfo customerInfoDTOToCustomerInfo(CustomerInfoDTO customerInfoDTO);
}
