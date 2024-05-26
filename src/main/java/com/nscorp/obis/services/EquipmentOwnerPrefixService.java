package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import com.nscorp.obis.domain.EquipmentCar;
import com.nscorp.obis.domain.EquipmentOwnerPrefix;

public interface EquipmentOwnerPrefixService {

	
	public List<EquipmentOwnerPrefix> getAllTables();
	
	public void deleteEquipmentOwnerPrefixTable(EquipmentOwnerPrefix equipmentOwnerPrefix);
	
	public EquipmentOwnerPrefix addEquipmentOwnerPrefix(EquipmentOwnerPrefix equipmentOwnerPrefix,Map<String, String> headers);

	EquipmentOwnerPrefix updateEquipmentOwnerPrefix(EquipmentOwnerPrefix equipmentOwnerPrefix, Map<String, String> headers);
}
