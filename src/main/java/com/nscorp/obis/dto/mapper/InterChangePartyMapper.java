package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.InterChangeParty;
import com.nscorp.obis.dto.InterChangePartyDTO;

@Mapper(componentModel = "spring")
public interface InterChangePartyMapper {
	
	InterChangePartyMapper INSTANCE = Mappers.getMapper(InterChangePartyMapper.class);
	InterChangePartyDTO interChangePartyTointerChangePartyDTO(InterChangeParty interChangeParty);
	InterChangeParty interChangePartyDTOTointerChangeParty(InterChangePartyDTO interChangePartyDTO);

}
