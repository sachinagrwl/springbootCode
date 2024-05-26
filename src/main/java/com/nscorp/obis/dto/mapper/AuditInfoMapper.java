package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.AuditInfo;
import com.nscorp.obis.dto.AuditInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AuditInfoMapper {

	AuditInfoMapper INSTANCE = Mappers.getMapper(AuditInfoMapper.class);

	AuditInfoDTO auditInfoToAuditInfoDTO(AuditInfo auditInfo);

	AuditInfo auditInfoDTOToAuditInfo(AuditInfoDTO auditInfoDTO);
}
