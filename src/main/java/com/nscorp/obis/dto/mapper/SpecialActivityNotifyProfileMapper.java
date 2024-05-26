package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.SpecialActivityNotifyProfile;
import com.nscorp.obis.dto.SpecialActivityNotifyDTO;
import com.nscorp.obis.dto.SpecialActivityNotifyProfileDTO;

@Mapper(componentModel = "spring")
public interface SpecialActivityNotifyProfileMapper {
    
    SpecialActivityNotifyProfileMapper INSTANCE = Mappers.getMapper(SpecialActivityNotifyProfileMapper.class);
    SpecialActivityNotifyProfileDTO specialActivityNotifyProfileToSpecialActivityNotifyProfileDto(SpecialActivityNotifyProfile specialActivityNotifyProfile);
	SpecialActivityNotifyProfile specialActivityNotifyProfileDtoToSpecialActivityNotifyProfile(SpecialActivityNotifyProfileDTO specialActivityNotifyProfileDto);

    SpecialActivityNotifyDTO specialActivityNotifyProfileToSpecialActivityNotifyDto(SpecialActivityNotifyProfile specialActivityNotifyProfile);
    SpecialActivityNotifyProfile specialActivityNotifyDtoToSpecialActivityNotifyProfile(SpecialActivityNotifyDTO specialActivityNotifyDto);
}
