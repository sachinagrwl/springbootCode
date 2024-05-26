package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.UnCd;
import com.nscorp.obis.dto.UnCdDTO;

@Mapper(componentModel = "spring")
public interface UnCdMapper {

	UnCdMapper INSTANCE = Mappers.getMapper(UnCdMapper.class);
	UnCdDTO UnCdToUnCdDTO(UnCd unCd);
	UnCd UnCdDTOToUnCd(UnCdDTO unCdDTO);
}

