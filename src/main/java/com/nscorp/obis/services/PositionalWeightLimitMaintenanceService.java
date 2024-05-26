package com.nscorp.obis.services;

import com.nscorp.obis.domain.PositionalWeightLimitMaintenance;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


public interface PositionalWeightLimitMaintenanceService {

	/* This Method Is Used To Fetch All Values */
	List<PositionalWeightLimitMaintenance> getAllLoadLimits();

	/* This Method Is Used To Add Values */
	PositionalWeightLimitMaintenance insertLoad(@Valid PositionalWeightLimitMaintenance loadObj,
			Map<String, String> headers);
	
	/* This Method Is Used To Update Values */
	PositionalWeightLimitMaintenance updatePositionalWeightLimitMaintenance(
			@Valid PositionalWeightLimitMaintenance loadObj, Map<String, String> headers);

	//void deletePositionalWeightLimitMaintenance(@Valid List<PositionalWeightLimitMaintenance> loadObj);

	/* This Method Is Used To Delete Values */
	void deletePositionalWeightLimitMaintenance(@Valid PositionalWeightLimitMaintenance loadObj );



	
}
