package com.nscorp.obis.services;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.PositionalWeightLimitMaintenance;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.PositionalWeightLimitMaintenanceRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PositionalWeightLimitMaintenanceServiceImpl implements PositionalWeightLimitMaintenanceService{
	@Autowired
	private PositionalWeightLimitMaintenanceRepository loadrepository;
	
	/* This Method Is Used To Fetch All Values */
    @Override
	public List<PositionalWeightLimitMaintenance> getAllLoadLimits()  {
    	List<PositionalWeightLimitMaintenance> loads = loadrepository.findAll();
    	if(loads.isEmpty()) {
    		throw new NoRecordsFoundException("No Records Found!");
    	}
    	return loads;
    }

	@Override
	public PositionalWeightLimitMaintenance insertLoad(@Valid PositionalWeightLimitMaintenance loadObj,
			Map<String, String> headers) {
		
		UserId.headerUserID(headers);
		if(loadrepository.existsByCarInitAndCarNrLowAndCarNrHighAndCarEquipmentType(loadObj.getCarInit(), loadObj.getCarNrLow(), loadObj.getCarNrHigh(), loadObj.getCarEquipmentType())){
			throw new RecordAlreadyExistsException("Record already exists under CAR INIT: " + loadObj.getCarInit() + ", LOW NR: " + loadObj.getCarNrLow() + ", HIGH NR: " + loadObj.getCarNrHigh());

		}

		loadObj.setCreateUserId(headers.get(CommonConstants.USER_ID));
		loadObj.setUpdateUserId(headers.get(CommonConstants.USER_ID));

		loadObj.setAarType(loadObj.getAarType());
		loadObj.setCarInit(loadObj.getCarInit());
		loadObj.setCarOwner(loadObj.getCarOwner());
		loadObj.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		loadObj.setUversion("!");
		loadrepository.save(loadObj);
		PositionalWeightLimitMaintenance loadLimit=loadrepository.findByCarInitAndCarNrLowAndCarNrHighAndCarEquipmentType(loadObj.getCarInit(),loadObj.getCarNrLow(), loadObj.getCarNrHigh(), loadObj.getCarEquipmentType());
		
		if(loadLimit == null) {
			throw new RecordNotAddedException("Record Not added");
		}
		return loadLimit;
		
	}

	@Override
	public PositionalWeightLimitMaintenance updatePositionalWeightLimitMaintenance(
			@Valid PositionalWeightLimitMaintenance loadObj, Map<String, String> headers) {
		// TODO Auto-generated method stub
		UserId.headerUserID(headers);
		if(!loadrepository.existsByCarInitAndCarNrLowAndCarNrHighAndCarEquipmentType(loadObj.getCarInit(), loadObj.getCarNrLow(), loadObj.getCarNrHigh(), loadObj.getCarEquipmentType())) {
			throw new NoRecordsFoundException("No record Found for CAR INIT: " + loadObj.getCarInit() + ", LOW NR: " + loadObj.getCarNrLow() + ", HIGH NR: " + loadObj.getCarNrHigh());

		}
		PositionalWeightLimitMaintenance existingloadObj = loadrepository.findByCarInitAndCarNrLowAndCarNrHighAndCarEquipmentType(loadObj.getCarInit(), loadObj.getCarNrLow(), loadObj.getCarNrHigh(), loadObj.getCarEquipmentType());
		//PositionalWeightLimitMaintenance existingloadObj = loadrepository.save(loadObj);
		existingloadObj.setUpdateUserId(headers.get(CommonConstants.USER_ID));

		existingloadObj.setAarType(loadObj.getAarType());
		existingloadObj.setC20MaxWeight(loadObj.getC20MaxWeight());
		existingloadObj.setCarDescription(loadObj.getCarDescription());
		existingloadObj.setCarOwner(loadObj.getCarOwner());

		/* Audit fields */
		if(StringUtils.isNotEmpty(existingloadObj.getUversion())) {
			existingloadObj.setUversion(
					Character.toString((char) ((((int)existingloadObj.getUversion().charAt(0) - 32) % 94) + 33)));
		}
		existingloadObj.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));

		loadrepository.save(existingloadObj);
		return existingloadObj;
	}

	@Override
	public void deletePositionalWeightLimitMaintenance(
			@Valid PositionalWeightLimitMaintenance loadObjList) {
			
	if(loadrepository.existsByCarInitAndCarNrLowAndCarNrHighAndCarEquipmentType(loadObjList.getCarInit(), loadObjList.getCarNrLow(), loadObjList.getCarNrHigh(), loadObjList.getCarEquipmentType())) {
		loadrepository.deleteByCarInitAndCarNrLowAndCarNrHighAndCarEquipmentType(loadObjList.getCarInit(), loadObjList.getCarNrLow(), loadObjList.getCarNrHigh(), loadObjList.getCarEquipmentType());
	}
	else {
		String rep = loadObjList.getCarInit()  + " and " + loadObjList.getCarNrLow()  + " and " + loadObjList.getCarNrHigh()  + " and " + loadObjList.getCarEquipmentType() + " Record Not Found!";
		throw new RecordNotDeletedException(rep);
		}
	}
}
