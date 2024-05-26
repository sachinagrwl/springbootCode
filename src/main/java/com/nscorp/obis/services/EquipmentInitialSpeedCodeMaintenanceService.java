package com.nscorp.obis.services;

import java.util.List;


import com.nscorp.obis.domain.EquipmentInitialSpeedCodeMaintenance;

public interface EquipmentInitialSpeedCodeMaintenanceService {

	List<EquipmentInitialSpeedCodeMaintenance> getAllInitSpeedCode(String eqInitShort);

}
