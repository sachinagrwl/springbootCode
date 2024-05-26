package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.Customer;
import com.nscorp.obis.dto.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

	CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

	CustomerDTO customerToCustomerDTO(Customer customer);

	Customer customerDTOToCustomer(CustomerDTO customerDTO);
}
