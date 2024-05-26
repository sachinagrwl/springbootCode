package com.nscorp.obis.services;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.DamageArea;
import com.nscorp.obis.domain.DamageCompLoc;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.DamageAreaCompRepository;
import com.nscorp.obis.repository.DamageAreaRepository;
import com.nscorp.obis.repository.DamageCompLocRepository;

@Service
public class DamageAreaServiceImpl implements DamageAreaService {

	@Autowired
	private DamageAreaRepository damageAreaRepository;

	@Autowired
	private DamageAreaCompRepository damageAreaCompRepository;

	@Autowired
	private DamageCompLocRepository damageCompLocRepository;

	public List<DamageArea> getAllDamageArea() {
		List<DamageArea> damageArea = damageAreaRepository.findAll();
		if (damageArea.isEmpty()) {
			throw new NoRecordsFoundException("No Records found!");
		}
		return damageArea;
	}

	@Override
	public DamageArea addDamageArea(DamageArea damageArea, Map<String, String> headers) {
		if(!StringUtils.isNotBlank(damageArea.getAreaCd()))
			throw new InvalidDataException("W-AREA_CD Required");
		if(damageAreaRepository.existsById(damageArea.getAreaCd()))
			throw new RecordAlreadyExistsException("Duplicate Area Code!");
		if (damageAreaRepository.existsById(damageArea.getAreaCd()))
			throw new RecordAlreadyExistsException("Repositioned");
		UserId.headerUserID(headers);
		damageArea.setCreateUserId(headers.get(CommonConstants.USER_ID));
		damageArea.setUpdateUserId(headers.get(CommonConstants.USER_ID));
		damageArea.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		damageArea.setUversion("!");
		DamageArea addedDamageArea = damageAreaRepository.save(damageArea);
		return addedDamageArea;
	}

	@Override
	public void deleteDamageArea(DamageArea damageAreaObj) {
		if (damageAreaCompRepository.existsByAreaCd(damageAreaObj.getAreaCd()))
			throw new RecordNotDeletedException("Entity Damage_Area still has restricted links to DAMAGE_AREA_COMP");

		else{
			if(damageAreaRepository.existsById(damageAreaObj.getAreaCd())){
				damageAreaRepository.deleteById(damageAreaObj.getAreaCd());

				DamageCompLoc damageCompLoc = damageCompLocRepository.findByDamageArea_AreaCd(damageAreaObj.getAreaCd());
				if(damageCompLoc!=null) {
					Date currentDate = new Date(System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(15));
					Timestamp ts = new Timestamp(currentDate.getTime());
					int dateTime = damageCompLoc.getCreateDateTime().compareTo(ts);
					if (dateTime < 0) {
						throw new NoRecordsFoundException("Not deleting historical records, Contact SIMS Programmer to remove");
					} else {
						damageCompLocRepository.deleteByDamageArea_AreaCd(damageAreaObj.getAreaCd());
					}
				}
			}else
				throw new RecordNotDeletedException("Record Not Found!");
		}

	}

	@Override
	public DamageArea updateDamageArea(DamageArea damageArea, Map<String, String> headers) {
		if (!damageAreaRepository.existsByAreaCd(damageArea.getAreaCd()))
			throw new RecordAlreadyExistsException("Invalid Change");
		
		damageArea.setCreateUserId(headers.get(CommonConstants.USER_ID));
		damageArea.setUpdateUserId(headers.get(CommonConstants.USER_ID));
		damageArea.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
		damageArea.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		damageArea.setUversion(damageArea.getUversion());

		DamageArea putDamageArea = damageAreaRepository.save(damageArea);
		return putDamageArea;
	}

}
