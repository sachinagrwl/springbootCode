package com.nscorp.obis.services;

import com.nscorp.obis.domain.EquipmentDefaultTareWeightMaintenance;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

public interface EquipmentDefaultTareWeightMaintenanceService  {
	List<EquipmentDefaultTareWeightMaintenance> getAllTareWeights();
	EquipmentDefaultTareWeightMaintenance addTareWeight(@Valid EquipmentDefaultTareWeightMaintenance tareWeightObj, Map<String,String> headers);
	EquipmentDefaultTareWeightMaintenance updateWeight(@Valid EquipmentDefaultTareWeightMaintenance tareWeightObj, Map<String,String> headers);
	void deleteWeight(@Valid EquipmentDefaultTareWeightMaintenance tareWeightObj);
}
