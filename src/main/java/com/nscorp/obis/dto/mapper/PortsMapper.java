package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.Ports;
import com.nscorp.obis.dto.PortsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PortsMapper {
    PortsMapper INSTANCE = Mappers.getMapper(PortsMapper.class);
    PortsDTO PortsToPortsDTO(Ports ports);
    Ports PortsDTOToPorts(PortsDTO portsDTO);
}
