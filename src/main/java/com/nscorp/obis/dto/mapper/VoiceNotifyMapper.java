package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.VoiceNotify;
import com.nscorp.obis.dto.VoiceNotifyDTO;

@Mapper(componentModel = "spring")
public interface VoiceNotifyMapper {

	VoiceNotifyMapper INSTANCE = Mappers.getMapper(VoiceNotifyMapper.class);
	VoiceNotifyDTO voiceNotifyToVoiceNotifyDTO(VoiceNotify voiceNotify);
	VoiceNotify voiceNotifyDTOToVoiceNotify(VoiceNotifyDTO voiceNotifyDTO);
	
	
}
