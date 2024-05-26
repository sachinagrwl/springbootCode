package com.nscorp.obis.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.nscorp.obis.exception.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.EquipmentOwnerPrefix;
import com.nscorp.obis.repository.EquipmentOwnerPrefixRepository;

@Service
@Transactional
public class EquipmentOwnerPrefixServiceImpl implements EquipmentOwnerPrefixService {

	@Autowired
	EquipmentOwnerPrefixRepository equipmentOwnerPrefixRepo;

	public List<EquipmentOwnerPrefix> getAllTables() {

		List<EquipmentOwnerPrefix> equipmentOwner = equipmentOwnerPrefixRepo.findAll();
		if (equipmentOwner.isEmpty()) {
			throw new NoRecordsFoundException("No Records found");
		}
		return equipmentOwner;
	}

	@Override
	public void deleteEquipmentOwnerPrefixTable(EquipmentOwnerPrefix equipmentOwnerPrefix) {
		if(equipmentOwnerPrefixRepo.existsByEquipInit(equipmentOwnerPrefix.getEquipInit())) {
			equipmentOwnerPrefixRepo.deleteByEquipInit(equipmentOwnerPrefix.getEquipInit());
		}
		else {
			throw new RecordNotDeletedException("Record Not Found!");
		}
	}

	@Override
	public EquipmentOwnerPrefix addEquipmentOwnerPrefix(EquipmentOwnerPrefix equipmentOwnerPrefix,Map<String, String> headers) {
		// TODO Auto-generated method stub
		UserId.headerUserID(headers);
		String ichgPartyCode = equipmentOwnerPrefix.getInterchangeCd();
		while(ichgPartyCode!=null && ichgPartyCode.length() < 4){
			ichgPartyCode += StringUtils.SPACE;
		}
		equipmentOwnerPrefix.setInterchangeCd(ichgPartyCode);
		if(equipmentOwnerPrefixRepo.existsByEquipInit(equipmentOwnerPrefix.getEquipInit())){
			throw new RecordAlreadyExistsException("EquipmentOwnerPrefix is already exists under EquipInit:" + equipmentOwnerPrefix.getEquipInit());
			
		}
		equipmentOwnerPrefix.setCreateUserId(headers.get(CommonConstants.USER_ID));
		equipmentOwnerPrefix.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		equipmentOwnerPrefix.setUversion("!");
			EquipmentOwnerPrefix equipmentOwner=equipmentOwnerPrefixRepo.save(equipmentOwnerPrefix);
			if(equipmentOwner== null) {
				throw new RecordNotAddedException("Record Not added to Database");
			}
			return equipmentOwner;
	}
	public void validateEquipmentOwnerPrefix(EquipmentOwnerPrefix equipmentOwnerPrefix){
		List<String> ownership = new ArrayList<>();
		ownership.add("F");
		ownership.add("S");
		ownership.add("L");
		ownership.add("P");
		if(!equipmentOwnerPrefixRepo.existsByEquipInit(equipmentOwnerPrefix.getEquipInit()))
			throw new NoRecordsFoundException("No Record Found!");
		if(!ownership.contains(equipmentOwnerPrefix.getOwnership()))
			throw new InvalidDataException("Invalid Ownership");
		if(!equipmentOwnerPrefix.getOwnership().equalsIgnoreCase("P") && !StringUtils.isNotEmpty(equipmentOwnerPrefix.getInterchangeCd()))
			throw new InvalidDataException("must select Interchange Party");
	}
	@Override
	public EquipmentOwnerPrefix updateEquipmentOwnerPrefix(EquipmentOwnerPrefix equipmentOwnerPrefix, Map<String, String> headers) {
		UserId.headerUserID(headers);
		validateEquipmentOwnerPrefix(equipmentOwnerPrefix);
		String ichgPartyCode = equipmentOwnerPrefix.getInterchangeCd();
		while(ichgPartyCode!=null && ichgPartyCode.length() < 4){
			ichgPartyCode += StringUtils.SPACE;
		}
		equipmentOwnerPrefix.setInterchangeCd(ichgPartyCode);
		equipmentOwnerPrefix.setUpdateUserId(headers.get(CommonConstants.USER_ID));
		equipmentOwnerPrefix.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
		equipmentOwnerPrefix.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		equipmentOwnerPrefix.setUversion("!");
		EquipmentOwnerPrefix equipmentOwner = equipmentOwnerPrefixRepo.save(equipmentOwnerPrefix);
		if(equipmentOwner== null) {
			throw new RecordNotAddedException("Record Not updated to Database");
		}
		return equipmentOwner;
	}

}

