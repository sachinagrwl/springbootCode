package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.CustomerTerm;
import com.nscorp.obis.dto.CustomerTermDTO;

@Mapper(componentModel = "spring")
public interface CustomerTermMapper {
	
	CustomerTermMapper INSTANCE = Mappers.getMapper(CustomerTermMapper.class);
	
	CustomerTerm CustomerTermDTOToCustomerTerm(CustomerTermDTO customerTermDTO);
	CustomerTermDTO CustomerTermToCustomerTermDTO(CustomerTerm customerTerm);

}
