package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.TerminalNotifyProfile;
import com.nscorp.obis.dto.TerminalNotifyProfileDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TerminalNotifyProfileMapper {
	TerminalNotifyProfileMapper INSTANCE = Mappers.getMapper(TerminalNotifyProfileMapper.class);

	TerminalNotifyProfileDTO terminalNotifyProfileToTerminalNotifyProfileDTO(TerminalNotifyProfile terminalNotifyProfiles);

	TerminalNotifyProfile terminalNotifyProfileDTOToTerminalNotifyProfile(TerminalNotifyProfileDTO terminalNotifyProfilesDTO);
	
}
