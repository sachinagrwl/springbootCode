package com.nscorp.obis.services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.domain.EquipmentInitialSpeedCodeMaintenance;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.EquipmentInitialSpeedCodeMaintenanceRepository;

@Service
@Transactional
public class EquipmentInitialSpeedCodeMaintenanceServiceImpl implements EquipmentInitialSpeedCodeMaintenanceService {
	
	@Autowired
	EquipmentInitialSpeedCodeMaintenanceRepository eqInitSpeedCodeRepo;

	@Override
	public List<EquipmentInitialSpeedCodeMaintenance> getAllInitSpeedCode(String eqInitShort) {

		if(eqInitShort != null){
			eqInitShort = eqInitShort.toUpperCase();
		}
		List<EquipmentInitialSpeedCodeMaintenance> eqInitSpeedCode = eqInitSpeedCodeRepo.findAll(eqInitShort);
		if(eqInitSpeedCode.isEmpty()) {
			throw new NoRecordsFoundException("No Records found!");
		}
		return eqInitSpeedCode;
	}

}
