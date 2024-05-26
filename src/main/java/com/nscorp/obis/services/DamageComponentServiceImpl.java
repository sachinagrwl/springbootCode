package com.nscorp.obis.services;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.DamageCompLoc;
import com.nscorp.obis.domain.DamageComponent;
import com.nscorp.obis.domain.DamageComponentReason;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.DamageCompLocRepository;
import com.nscorp.obis.repository.DamageComponentReasonRepository;
import com.nscorp.obis.repository.DamageComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DamageComponentServiceImpl implements DamageComponentService {

	@Autowired
	DamageComponentRepository damageComponentRepository;
	@Autowired
	DamageCompLocRepository damageCompLocRepository;
	@Autowired
	DamageComponentReasonRepository damageComponentReasonRepository;



	@Override
	public List<DamageComponent> getAllDamageComponents() {
		List<DamageComponent> damageComponentList = damageComponentRepository.findAllByOrderByJobCode();

		if (damageComponentList.isEmpty()) {
			throw new NoRecordsFoundException("No DamageComponentList Found!");
		}
		return damageComponentList;
	}

	@Override
	public DamageComponent getDamageComponentsByJobCode(Integer jobCode) {

		DamageComponent damageComponentJobCode = damageComponentRepository.findByJobCode(jobCode);

		if (damageComponentJobCode == null) {
			throw new NoRecordsFoundException("No DamageComponentList Found!");
		}
		return damageComponentJobCode;
	}

	@Override
	public void deleteDamageComponent(DamageComponent damageComponent) {

		if(damageCompLocRepository.existsByJobCode(damageComponent.getJobCode())){
			List<DamageCompLoc> existingRec = damageCompLocRepository.findByJobCode(damageComponent.getJobCode());
			for(DamageCompLoc exRec: existingRec){
				damageCompLocRepository.delete(exRec);
			}

		}

		if(damageComponentReasonRepository.existsByJobCode(damageComponent.getJobCode())) {
			List<DamageComponentReason> existingRecords = damageComponentReasonRepository.findByJobCode(damageComponent.getJobCode());
			for (DamageComponentReason exRec : existingRecords) {
				damageComponentReasonRepository.delete(exRec);
			}
		}
		if (damageComponentRepository.existsByJobCodeAndUversion(damageComponent.getJobCode(), damageComponent.getUversion())) {

			DamageComponent existingRecord = damageComponentRepository.findByJobCode(damageComponent.getJobCode());
			damageComponentRepository.delete(existingRecord);

		} else {
			String rep = damageComponent.getJobCode() + " Record Not Found!";
			throw new RecordNotDeletedException(rep);
		}

	}

	@Override
	public DamageComponent insertDamageComponent(DamageComponent damageComponent, Map<String, String> headers) {
		UserId.headerUserID(headers);
		if (damageComponent.getJobCode() != null
				&& damageComponentRepository.existsByJobCode(damageComponent.getJobCode())) {
			throw new RecordAlreadyExistsException("Record with Job Code Already Exists!");
		}
		if(damageComponent.getCInd().equalsIgnoreCase("Y") || damageComponent.getZInd().equalsIgnoreCase("Y") ||damageComponent.getTInd().equalsIgnoreCase("Y") ){
			damageComponent.setTInd(damageComponent.getTInd());
			damageComponent.setZInd(damageComponent.getZInd());
			damageComponent.setCInd(damageComponent.getCInd());
		}else
			throw new RecordNotAddedException("Atleast one of the Equipment Type should be Y");

		damageComponent.setCreateUserId(headers.get(CommonConstants.USER_ID));
		damageComponent.setUpdateUserId(headers.get(CommonConstants.USER_ID));
		damageComponent.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		damageComponent.setUversion("!");
		DamageComponent dmgCompData = damageComponentRepository.save(damageComponent);
		return dmgCompData;
	}

	@Override
	public DamageComponent UpdateDamageComponent(DamageComponent damageComponentObj, Map<String, String> headers) {
		UserId.headerUserID(headers);
		if (damageComponentObj.getJobCode() != null
				&& damageComponentRepository.existsByJobCode(damageComponentObj.getJobCode())) {
			DamageComponent damageComponent = damageComponentRepository.findByJobCode(damageComponentObj.getJobCode());
			damageComponent.setUpdateUserId(headers.get(CommonConstants.USER_ID));
			damageComponent.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
			damageComponent.setUversion(damageComponentObj.getUversion());
			damageComponent.setCompDscr(damageComponentObj.getCompDscr());
			damageComponent.setReasonTp(damageComponentObj.getReasonTp());
				if(damageComponentObj.getCInd().equalsIgnoreCase("Y") || damageComponentObj.getZInd().equalsIgnoreCase("Y") ||damageComponentObj.getTInd().equalsIgnoreCase("Y") ){
					damageComponent.setTInd(damageComponentObj.getTInd());
					damageComponent.setZInd(damageComponentObj.getZInd());
					damageComponent.setCInd(damageComponentObj.getCInd());
				}else
					throw new RecordNotAddedException("Atleast one of the Equipment Type should be Y");


			DamageComponent DmgComp = damageComponentRepository.save(damageComponent);
			return DmgComp;

		}else
			throw new NoRecordsFoundException("No Record Found!");

	}
}
