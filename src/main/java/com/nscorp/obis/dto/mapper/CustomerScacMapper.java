package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.CustomerScac;
import com.nscorp.obis.dto.CustomerScacDTO;

@Mapper(componentModel = "spring")
public interface CustomerScacMapper {

	CustomerScacMapper INSTANCE = Mappers.getMapper(CustomerScacMapper.class);

	CustomerScac customerScacDTOToCustomerScac(CustomerScacDTO customerScacDto);

	CustomerScacDTO customerScacTCustomerScacDTO(CustomerScac customerScac);

}
