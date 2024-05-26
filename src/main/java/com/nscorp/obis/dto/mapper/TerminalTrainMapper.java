package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.TerminalTrain;
import com.nscorp.obis.dto.TerminalTrainDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TerminalTrainMapper {

	TerminalTrainMapper INSTANCE = Mappers.getMapper(TerminalTrainMapper.class);

	TerminalTrainDTO terminalTrainToTerminalTrainDTO(TerminalTrain terminalTrain);

	TerminalTrain terminalTrainDTOToTerminalTrain(TerminalTrainDTO terminalTrainDTO);
}
