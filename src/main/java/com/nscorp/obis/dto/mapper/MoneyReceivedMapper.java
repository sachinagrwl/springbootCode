package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.MoneyReceived;
import com.nscorp.obis.dto.MoneyReceivedDTO;

@Mapper(componentModel = "spring")
public interface MoneyReceivedMapper {
	
	MoneyReceivedMapper INSTANCE = Mappers.getMapper(MoneyReceivedMapper.class);
	MoneyReceivedDTO moneyReceivedTomoneyReceivedDTO(MoneyReceived moneyReceived);
	MoneyReceived moneyReceivedDTOTomoneyReceived(MoneyReceivedDTO moneyReceivedDTO);

}
