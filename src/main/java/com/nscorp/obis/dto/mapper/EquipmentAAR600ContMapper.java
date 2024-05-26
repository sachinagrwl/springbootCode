package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.EquipmentAAR600Cont;
import com.nscorp.obis.dto.EquipmentAAR600ContDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EquipmentAAR600ContMapper {

    EquipmentAAR600ContMapper INSTANCE = Mappers.getMapper(EquipmentAAR600ContMapper.class);
    EquipmentAAR600ContDTO EquipmentAAR600ContToEquipmentAAR600ContDTO(EquipmentAAR600Cont equipmentAAR600Cont);
    EquipmentAAR600Cont EquipmentAAR600ContDTOToEquipmentAAR600Cont(EquipmentAAR600ContDTO equipmentAAR600ContDTO);
}
