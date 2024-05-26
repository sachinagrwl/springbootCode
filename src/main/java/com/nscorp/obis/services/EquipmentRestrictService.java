package com.nscorp.obis.services;

import com.nscorp.obis.domain.EquipmentRestrict;

import java.util.List;
import java.util.Map;

public interface EquipmentRestrictService {
    List<EquipmentRestrict> getAllEquipRestrictions();

    EquipmentRestrict addEquipRestrictions(EquipmentRestrict equipRestrict, Map<String, String> headers);

    EquipmentRestrict updateEquipRestriction(EquipmentRestrict equipRestrict, Map<String, String> headers);

    EquipmentRestrict deleteEquipRestriction(EquipmentRestrict equipmentRestrictDTOToEquipmentRestrict);
}
