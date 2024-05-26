package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.CustomerTerminal;
import com.nscorp.obis.dto.CustomerTerminalDTO;

@Mapper(componentModel = "spring")
public interface CustomerTerminalMapper {
	
	CustomerTerminalMapper INSTANCE = Mappers.getMapper(CustomerTerminalMapper.class);
	
	CustomerTerminal CustomerTerminalDTOToCustomerTerminal(CustomerTerminalDTO customerTermDTO);
	CustomerTerminalDTO CustomerTerminalToCustomerTerminalDTO(CustomerTerminal customerTerm);

}
