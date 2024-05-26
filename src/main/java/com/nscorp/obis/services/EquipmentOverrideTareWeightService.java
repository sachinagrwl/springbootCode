package com.nscorp.obis.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.nscorp.obis.domain.EquipmentOverrideTareWeight;



public interface EquipmentOverrideTareWeightService {

	List<EquipmentOverrideTareWeight> getAllTareWeights(String equipmentInit, BigDecimal equipmentNumberLow,
			BigDecimal equipmentNumberHigh, String equipmentType, Integer overrideTareWeight);

	EquipmentOverrideTareWeight deleteOverrideWeights(EquipmentOverrideTareWeight equipmentOverrideTareWeight);
	EquipmentOverrideTareWeight addOverrideTareWeight(EquipmentOverrideTareWeight equipmentOverrideTareWeight, Map<String,String> headers);
	
	EquipmentOverrideTareWeight updateEquipmentOverrideTareWeight(EquipmentOverrideTareWeight equipmentOverrideTareWeight, Map<String, String> headers);
}
