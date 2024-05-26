package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.CustomerLocalInfo;
import com.nscorp.obis.dto.CustomerLocalInfoDTO;


@Mapper(componentModel = "spring")
public interface CustomerLocalInfoMapper {
	
	CustomerLocalInfoMapper INSTANCE = Mappers.getMapper(CustomerLocalInfoMapper.class);	
	CustomerLocalInfo customerLocalInfoDTOToCustomerLocalInfo(CustomerLocalInfoDTO customerLocalInfoDTO);
	CustomerLocalInfoDTO customerLocalInfoToCustomerLocalInfoDTO(CustomerLocalInfo customerLocalInfo);

}
