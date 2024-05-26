package com.nscorp.obis.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.EquipmentOverrideTareWeight;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.EquipmentOverrideTareWeightRepository;

@Transactional
@Service
public class EquipmentOverrideTareWeightServiceImpl implements EquipmentOverrideTareWeightService {
	
	@Autowired
	EquipmentOverrideTareWeightRepository eqOverrideRepo;

	public void equipmentOverrideValidations(EquipmentOverrideTareWeight equipmentOverrideTareWeight) {
		if((equipmentOverrideTareWeight.getEquipmentInit() == null) && (!(equipmentOverrideTareWeight.getEquipmentType().isEmpty())
				&& (equipmentOverrideTareWeight.getEquipmentLength() == null))) {
			throw new RecordNotAddedException("Equipment Init value should be present!");
		}
		if(equipmentOverrideTareWeight.getEquipmentNumberLow().compareTo(equipmentOverrideTareWeight.getEquipmentNumberHigh()) > 0) {
			throw new RecordNotAddedException("Equipment Low Number: " + equipmentOverrideTareWeight.getEquipmentNumberLow() + " should be less than or equals to Equipment High Number: "
			+ equipmentOverrideTareWeight.getEquipmentNumberHigh());
		}
		BigDecimal eqNrLow = equipmentOverrideTareWeight.getEquipmentNumberLow();
		BigDecimal eqNrHigh = equipmentOverrideTareWeight.getEquipmentNumberHigh();
		if(eqOverrideRepo.existsByEquipmentInit(equipmentOverrideTareWeight.getEquipmentInit())) {
			List<EquipmentOverrideTareWeight> eqOverLapList = eqOverrideRepo.findByEquipmentInit(equipmentOverrideTareWeight.getEquipmentInit());
			eqOverLapList.forEach(eqOverLap -> {
				if((!eqOverLap.getOverrideId().equals(equipmentOverrideTareWeight.getOverrideId())) && ((eqOverLap.getEquipmentNumberLow().compareTo(eqNrLow) >= 0 && eqOverLap.getEquipmentNumberHigh().compareTo(eqNrHigh) <= 0) ||
						(eqOverLap.getEquipmentNumberLow().compareTo(eqNrLow) <= 0 && eqOverLap.getEquipmentNumberHigh().compareTo(eqNrHigh) >= 0) ||
						(eqOverLap.getEquipmentNumberLow().compareTo(eqNrLow) <= 0 && eqOverLap.getEquipmentNumberHigh().compareTo(eqNrLow) >= 0) ||
						(eqOverLap.getEquipmentNumberLow().compareTo(eqNrHigh) <= 0 && eqOverLap.getEquipmentNumberHigh().compareTo(eqNrHigh) >= 0))) {
					throw new RecordNotAddedException("Equipment Init and Equipment Number Range are overlapping with existing records");
				}
			});
		}
	}
	
	@Override
	public List<EquipmentOverrideTareWeight> getAllTareWeights(String equipmentInit, BigDecimal equipmentNumberLow,
			BigDecimal equipmentNumberHigh, String equipmentType, Integer overrideTareWeight) {
		
		List<EquipmentOverrideTareWeight> eqOverrideList = eqOverrideRepo.findAll(equipmentInit,equipmentNumberLow,equipmentNumberHigh,equipmentType,overrideTareWeight);
		if(eqOverrideList.isEmpty()) {
			throw new NoRecordsFoundException("No Record Found under this search!");
		}
		return eqOverrideList;
	}

	@Override
	public EquipmentOverrideTareWeight deleteOverrideWeights(EquipmentOverrideTareWeight equipmentOverrideTareWeight) {
		// TODO Auto-generated method stub
		if (eqOverrideRepo.existsByOverrideId(equipmentOverrideTareWeight.getOverrideId())) {
			EquipmentOverrideTareWeight overrideTareWeight = eqOverrideRepo.findByOverrideId(equipmentOverrideTareWeight.getOverrideId());
			eqOverrideRepo.deleteByOverrideId(equipmentOverrideTareWeight.getOverrideId());
			return overrideTareWeight;
		} else
			throw new NoRecordsFoundException("No record Found to delete Under this Equipment Override Id: " + equipmentOverrideTareWeight.getOverrideId());
	}

	public EquipmentOverrideTareWeight addOverrideTareWeight(EquipmentOverrideTareWeight equipmentOverrideTareWeight, Map<String, String> headers) {
		UserId.headerUserID(headers);
		String userId = headers.get(CommonConstants.USER_ID);
		String extensionSchema = headers.get(CommonConstants.EXTENSION_SCHEMA);
		Long generatedOverrideId = eqOverrideRepo.SGKLong();
		equipmentOverrideTareWeight.setOverrideId(generatedOverrideId);
		equipmentOverrideTareWeight.setCreateUserId(userId.toUpperCase());
		equipmentOverrideTareWeight.setUpdateUserId(userId.toUpperCase());
		equipmentOverrideTareWeight.setUpdateExtensionSchema(extensionSchema);
		equipmentOverrideTareWeight.setUversion("!");
		equipmentOverrideValidations(equipmentOverrideTareWeight);
		eqOverrideRepo.save(equipmentOverrideTareWeight);
		return eqOverrideRepo.findByOverrideId(equipmentOverrideTareWeight.getOverrideId());
	}
	@Override
	public EquipmentOverrideTareWeight updateEquipmentOverrideTareWeight(
			EquipmentOverrideTareWeight equipmentOverrideTareWeight, Map<String, String> headers) {
		UserId.headerUserID(headers);
        if(eqOverrideRepo.existsById(equipmentOverrideTareWeight.getOverrideId())) {
        	EquipmentOverrideTareWeight existingEquipmentOverrideTareWeight =eqOverrideRepo.findByOverrideId(equipmentOverrideTareWeight.getOverrideId());
        	equipmentOverrideValidations(equipmentOverrideTareWeight);
    		return updateEquipmentOverideTareWeight(existingEquipmentOverrideTareWeight,equipmentOverrideTareWeight,headers);
    	}else 
    		throw new NoRecordsFoundException("No record Found Under this overRide:"+equipmentOverrideTareWeight.getOverrideId());
	}
	
	private EquipmentOverrideTareWeight updateEquipmentOverideTareWeight(EquipmentOverrideTareWeight existingEquipmentOverrideTareWeight,EquipmentOverrideTareWeight equipmentOverrideTareWeight,Map<String, String> headers) {
		  existingEquipmentOverrideTareWeight.setUpdateUserId(headers.get(CommonConstants.USER_ID));
		  existingEquipmentOverrideTareWeight.setEquipmentInit(equipmentOverrideTareWeight.getEquipmentInit());
		  existingEquipmentOverrideTareWeight.setEquipmentNumberLow(equipmentOverrideTareWeight.getEquipmentNumberLow());
		  existingEquipmentOverrideTareWeight.setEquipmentNumberHigh(equipmentOverrideTareWeight.getEquipmentNumberHigh());
		  existingEquipmentOverrideTareWeight.setEquipmentType(equipmentOverrideTareWeight.getEquipmentType());
		  existingEquipmentOverrideTareWeight.setOverrideTareWeight(equipmentOverrideTareWeight.getOverrideTareWeight());
		  existingEquipmentOverrideTareWeight.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		  existingEquipmentOverrideTareWeight.setEquipmentLength(equipmentOverrideTareWeight.getEquipmentLength());
		  if(StringUtils.isNotEmpty(existingEquipmentOverrideTareWeight.getUversion())) {
			  existingEquipmentOverrideTareWeight.setUversion(
					  Character.toString((char) ((((int)existingEquipmentOverrideTareWeight.getUversion().charAt(0) - 32) % 94) + 33)));  
		  }
		  eqOverrideRepo.save(existingEquipmentOverrideTareWeight);
		  return existingEquipmentOverrideTareWeight;
	}
}
