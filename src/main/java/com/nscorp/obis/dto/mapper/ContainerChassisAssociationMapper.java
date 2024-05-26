package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.ContainerChassisAssociation;
import com.nscorp.obis.dto.ContainerChassisAssociationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ContainerChassisAssociationMapper {

    ContainerChassisAssociationMapper INSTANCE = Mappers.getMapper(ContainerChassisAssociationMapper.class);

    ContainerChassisAssociationDTO containerChassisAssociationToContainerChassisAssociationDTO(ContainerChassisAssociation containerChassisAssociation);

    ContainerChassisAssociation containerChassisAssociationDTOToContainerChassisAssociation(ContainerChassisAssociationDTO containerChassisAssociationDTO);
}
