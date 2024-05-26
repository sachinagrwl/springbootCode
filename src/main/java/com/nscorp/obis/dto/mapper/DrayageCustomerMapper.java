package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.DrayageCustomer;
import com.nscorp.obis.dto.DrayageCustomerDTO;

@Mapper(componentModel = "spring")
public interface DrayageCustomerMapper {
	DrayageCustomerMapper INSTANCE = Mappers.getMapper(DrayageCustomerMapper.class);
	DrayageCustomerDTO drayageCustomerToDrayageCustomerDTO(DrayageCustomer drayageCustomer);
	DrayageCustomer drayageCustomerDtoToDrayageCustomer(DrayageCustomerDTO drayageCustomerDto);
}
