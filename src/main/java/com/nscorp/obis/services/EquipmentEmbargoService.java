package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import com.nscorp.obis.domain.EquipmentEmbargo;

public interface EquipmentEmbargoService {

	EquipmentEmbargo addEquipEmbargo(EquipmentEmbargo eqEmbargo, Map<String, String> headers);

	List<EquipmentEmbargo> getAllEmbargo();
	
	EquipmentEmbargo updateEquipmentEmbargo(EquipmentEmbargo equipmentEmbargo, Map<String, String> headers);

	EquipmentEmbargo deleteEquipmentEmbargo(EquipmentEmbargo equipmentEmbargo);

}
