package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.TerminalFunction;
import com.nscorp.obis.dto.TerminalFunctionDTO;

@Mapper(componentModel = "spring")
public interface TerminalFunctionMapper {

	TerminalFunctionMapper INSTANCE = Mappers.getMapper(TerminalFunctionMapper.class);
	TerminalFunctionDTO terminalFunctionToTerminalFunctionDTO(TerminalFunction terminalFunction);
	TerminalFunction terminalFunctionDTOToTerminalFunction(TerminalFunctionDTO terminalFunctionDTO);
}
