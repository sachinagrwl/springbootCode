package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.CustomerIndex;
import com.nscorp.obis.dto.CustomerIndexDTO;

@Mapper(componentModel = "spring")
public interface CustomerIndexMapper {

	CustomerIndexMapper INSTANCE = Mappers.getMapper(CustomerIndexMapper.class);

	CustomerIndex customerIndexDTOToCustomerIndex(CustomerIndexDTO customerIndexDto);

	CustomerIndexDTO customerIndexTCustomerIndexDTO(CustomerIndex customerIndex);

}
