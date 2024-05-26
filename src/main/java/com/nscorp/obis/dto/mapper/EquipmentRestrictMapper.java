package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.CodeTableSelection;
import com.nscorp.obis.domain.EquipmentRestrict;
import com.nscorp.obis.dto.CodeTableSelectionDTO;
import com.nscorp.obis.dto.EquipmentRestrictDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EquipmentRestrictMapper {

    EquipmentRestrictMapper INSTANCE = Mappers.getMapper(EquipmentRestrictMapper.class);

    EquipmentRestrictDTO equipmentRestrictToEquipmentRestrictDTO(EquipmentRestrict equipmentRestrict);

    EquipmentRestrict equipmentRestrictDTOToEquipmentRestrict(EquipmentRestrictDTO equipmentRestrictDTO);
}
