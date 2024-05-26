package com.nscorp.obis.services;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.EquipmentDefaultTareWeightMaintenance;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.EquipmentDefaultTareWeightMaintenanceRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class EquipmentDefaultTareWeightMaintenanceServiceImpl implements EquipmentDefaultTareWeightMaintenanceService {

	@Autowired
	private EquipmentDefaultTareWeightMaintenanceRepository tareWeightRepository;

	@Override
	public List<EquipmentDefaultTareWeightMaintenance> getAllTareWeights() {
		List<EquipmentDefaultTareWeightMaintenance> weights = tareWeightRepository.findAll();
		if(weights.isEmpty()) {
			throw new NoRecordsFoundException("No records found");
		}
		return weights;
	}

	@Override
	public EquipmentDefaultTareWeightMaintenance addTareWeight(@Valid EquipmentDefaultTareWeightMaintenance tareWeightObj,
			Map<String, String> headers) {
		
		UserId.headerUserID(headers);
		if(tareWeightRepository.existsByEqTpAndEqLgth(tareWeightObj.getEqTp(),tareWeightObj.getEqLgth())) {
			throw new RecordAlreadyExistsException("Equipment tare weight is already exists under Equipment Type:" + tareWeightObj.getEqTp() + " and Equipemnt Length:"+tareWeightObj.getEqLgth());
		}

		tareWeightObj.setCreateUserId(headers.get(CommonConstants.USER_ID));
		tareWeightObj.setUpdateUserId(headers.get(CommonConstants.USER_ID));

		tareWeightObj.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		tareWeightObj.setUversion("!");
		EquipmentDefaultTareWeightMaintenance weight=tareWeightRepository.save(tareWeightObj);
		if(weight== null) {
			throw new RecordNotAddedException("Record Not added to Database");
		}
		return weight;
	}

	@Override
	public EquipmentDefaultTareWeightMaintenance updateWeight(EquipmentDefaultTareWeightMaintenance tareWeightObj, Map<String,String> headers) {
		
		UserId.headerUserID(headers);
		String extensionSchema = headers.get(CommonConstants.EXTENSION_SCHEMA);
		String userId = headers.get(CommonConstants.USER_ID);

		if(tareWeightRepository.existsByEqTpAndEqLgth(tareWeightObj.getEqTp(),tareWeightObj.getEqLgth())) {
			EquipmentDefaultTareWeightMaintenance existingTareWeight =tareWeightRepository.findByEqLgthAndEqTp(tareWeightObj.getEqLgth(),tareWeightObj.getEqTp());
			existingTareWeight.setUpdateUserId(headers.get(CommonConstants.USER_ID));
			existingTareWeight.setTareWgt(tareWeightObj.getTareWgt());

			/* Audit fields */
			if(StringUtils.isNotEmpty(existingTareWeight.getUversion())) {
				existingTareWeight.setUversion(
						Character.toString((char) ((((int)existingTareWeight.getUversion().charAt(0) - 32) % 94) + 33)));
			}

			existingTareWeight.setUpdateExtensionSchema(extensionSchema);

			tareWeightRepository.save(existingTareWeight);
			return existingTareWeight;
		}
		else
			throw new NoRecordsFoundException("No record Found Under this Equipment Type:"+tareWeightObj.getEqTp()+" and Equipment Length:"+tareWeightObj.getEqLgth());
	}
	
	@Override
	public void deleteWeight(@Valid EquipmentDefaultTareWeightMaintenance tareWeightObj) {
			if(tareWeightRepository.existsByEqTpAndEqLgth(tareWeightObj.getEqTp(),tareWeightObj.getEqLgth())) {
				tareWeightRepository.deleteByEqTpAndEqLgth(tareWeightObj.getEqTp(),tareWeightObj.getEqLgth());
			}
			else {
				String rep = tareWeightObj.getEqTp()  + " and " + tareWeightObj.getEqLgth() + " Record Not Found!";
				throw new RecordNotDeletedException(rep);
			}
		}
}
