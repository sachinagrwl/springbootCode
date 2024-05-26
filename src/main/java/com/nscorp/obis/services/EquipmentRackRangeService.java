package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import com.nscorp.obis.domain.EquipmentRackRange;

public interface EquipmentRackRangeService {
//	public EquipmentRackRange insertEquipmentRackRange(EquipmentRackRange equipmentRackRange,Map<String, String> headers);
	public List<EquipmentRackRange> getAllTables();
	public void deleteEquipmentRackRange(EquipmentRackRange equipmentRackRange);
	EquipmentRackRange addEquipmentRackRange(EquipmentRackRange equipmentRackRange, Map<String, String> headers);
	EquipmentRackRange updateEquipmentRackRange(EquipmentRackRange equipmentRackRange, Map<String, String> headers);
}
