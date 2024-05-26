package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.domain.NorfolkSouthernEventLog;
import com.nscorp.obis.dto.NorfolkSouthernEventLogDTO;

@Mapper(componentModel = CommonConstants.MAPPER_COMPONENT_MODEL)
public interface NorfolkSouthernEventLogMapper {
	
	NorfolkSouthernEventLogMapper INSTANCE = Mappers.getMapper(NorfolkSouthernEventLogMapper.class);
	NorfolkSouthernEventLogDTO NorfolkSouthernEventLogToNorfolkSouthernEventLogDTO(NorfolkSouthernEventLog norfolkSouthernEventLog);
	NorfolkSouthernEventLog NorfolkSouthernEventLogDTOToNorfolkSouthernEventLog(NorfolkSouthernEventLogDTO norfolkSouthernEventLogDTO);

}
