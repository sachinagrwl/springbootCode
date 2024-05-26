package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.Terminal;
import com.nscorp.obis.dto.TerminalDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
@Component
@Mapper(componentModel = "spring")
public interface TerminalMapper {

	TerminalMapper INSTANCE = Mappers.getMapper(TerminalMapper.class);

	TerminalDTO terminalToTerminalDTO(Terminal terminal);

	Terminal terminalDTOToTerminal(TerminalDTO terminalDTO);
}
