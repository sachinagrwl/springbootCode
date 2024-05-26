package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import com.nscorp.obis.repository.PoolRepository;
import com.nscorp.obis.repository.PoolTypeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.PoolType;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;

@Service
@Transactional
public class PoolTypeServiceImpl implements PoolTypeService {
	
	@Autowired
	PoolTypeRepository poolTypeRepo;
	
	@Autowired 
	PoolRepository poolRepo;

	private void poolTypeValidations(PoolType poolTp){

		if(poolTp.getAdvRqdInd().equals("Y")) {
			poolTp.setAdvAllowInd("Y");
		} else {
			if(poolTp.getAdvAllowInd() == null) {
				throw new InvalidDataException("'advAllowInd' value should not be Blank or Null.");
			}
		}
		

		if(poolTp.getAdvOverride().equals("Y") && poolTp.getAdvRqdInd().equals("N")) {
			throw new RecordNotAddedException("Advance Reservation must be required for the EMP Exception");
		}

		if(poolTp.getUdfParam1().equals("Y") && poolTp.getAdvAllowInd().equals("N")) {
			throw new RecordNotAddedException("Advanced Reservations must be allowed to require specific Eq");
		}

	}

	@Override
	public List<PoolType> getReserveType() {
		
		List<PoolType> reserveTypeList = poolTypeRepo.findAll();
		
		if(reserveTypeList.isEmpty()){
            throw new NoRecordsFoundException("No Records Found!");
        }
        return reserveTypeList;
	}

	@Override
	public PoolType insertReservationType(PoolType poolTp, Map<String, String> headers) {
		
		UserId.headerUserID(headers);
		String extensionSchema = headers.get(CommonConstants.EXTENSION_SCHEMA);
		String userId = headers.get(CommonConstants.USER_ID);
		
		if (extensionSchema != null) {
			extensionSchema = extensionSchema.toUpperCase();
		} else {
			throw new NullPointerException(CommonConstants.EXTENSION_SCHEMA_EXCEPTION_MESSAGE);
		}

		poolTypeValidations(poolTp);

		if(poolTypeRepo.existsById(poolTp.getPoolTp())) {
			throw new RecordAlreadyExistsException("Record Already Exists under the Code: " + poolTp.getPoolTp());
		}
		
		poolTp.setCreateUserId(userId.toUpperCase());
		poolTp.setUpdateUserId(userId.toUpperCase());
		poolTp.setUpdateExtensionSchema(extensionSchema);
		poolTp.setUversion("!");
		
		PoolType poolType = poolTypeRepo.save(poolTp);
		return poolType;
		
	}

	@Override
	public PoolType updateReservationType(PoolType poolType, Map<String, String> headers) {
		UserId.headerUserID(headers);
		String extensionSchema = headers.get(CommonConstants.EXTENSION_SCHEMA);
		if (extensionSchema != null) {
			extensionSchema = extensionSchema.toUpperCase();
		} else {
			throw new NullPointerException(CommonConstants.EXTENSION_SCHEMA_EXCEPTION_MESSAGE);
		}
		if(poolTypeRepo.existsById(poolType.getPoolTp())) {
			PoolType existingPoolType = poolTypeRepo.findById(poolType.getPoolTp()).get();
			existingPoolType.setUpdateUserId(headers.get(CommonConstants.USER_ID));
			if(headers.get(CommonConstants.EXTENSION_SCHEMA) != null){
				existingPoolType.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
			}
			existingPoolType.setPoolTpDesc(poolType.getPoolTpDesc());
			existingPoolType.setRsrvInd(poolType.getRsrvInd());
			existingPoolType.setMultiRsrvInd(poolType.getMultiRsrvInd());
			existingPoolType.setAdvAllowInd(poolType.getAdvAllowInd());
			existingPoolType.setAdvRqdInd(poolType.getAdvRqdInd());
			existingPoolType.setAdvOverride(poolType.getAdvOverride());
			existingPoolType.setUdfParam1(poolType.getUdfParam1());
			existingPoolType.setPuRqdInd(poolType.getPuRqdInd());
			existingPoolType.setAgreementRqd(poolType.getAgreementRqd());
			poolTypeValidations(existingPoolType);
			if(StringUtils.isNotEmpty(existingPoolType.getUversion())) {
				existingPoolType.setUversion(
						Character.toString((char) ((((int)existingPoolType.getUversion().charAt(0) - 32) % 94) + 33)));
			}
			return poolTypeRepo.save(existingPoolType);
		} else{
			throw new NoRecordsFoundException("No record found for this 'poolType': "+poolType.getPoolTp());
		}

	}

	@Override
	public PoolType deleteReservationType(PoolType poolType) {
		if(poolTypeRepo.existsById(poolType.getPoolTp())){
			
			if(poolRepo.existsByPoolReservationType(poolType.getPoolTp())) {
				throw new RecordAlreadyExistsException("The Reservation Type: " + poolType.getPoolTp() + " is associated to Pool(s) and cannot be deleted");
			}
			
			PoolType PoolTp = poolTypeRepo.findById(poolType.getPoolTp()).get();
			poolTypeRepo.deleteById(poolType.getPoolTp());
			return PoolTp;
		} else {
			throw new NoRecordsFoundException("No record Found to delete Under this Pool Type: " + poolType.getPoolTp());
		}
	}

}
