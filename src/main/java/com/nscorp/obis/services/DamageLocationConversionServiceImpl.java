package com.nscorp.obis.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.DamageLocationConversion;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.DamageLocationConversionRepository;

@Service
public class DamageLocationConversionServiceImpl implements DamageLocationConversionService {
	@Autowired
	DamageLocationConversionRepository damageLocationConversionRepository;

	@Override
	public List<DamageLocationConversion> getAllDamageLocationConversion() {
		List<DamageLocationConversion> damageLocationConversionList = damageLocationConversionRepository.findAll();
		if (damageLocationConversionList.isEmpty()) {
			throw new NoRecordsFoundException("No DamageLocationConversion Found!");
		}
		return damageLocationConversionList;
	}

	@Override
	public void deleteDamageLocationConversion(DamageLocationConversion damageLocationConversion) {
		if (damageLocationConversionRepository.existsByLocDscrAndUversion(damageLocationConversion.getLocDscr(),
				damageLocationConversion.getUversion())) {
			DamageLocationConversion existingRecord = damageLocationConversionRepository.findByLocDscrAndUversion(
					damageLocationConversion.getLocDscr(), damageLocationConversion.getUversion());
			damageLocationConversionRepository.delete(existingRecord);
		} else {
			String rep = damageLocationConversion.getLocDscr() + " Record Not Found!";
			throw new RecordNotDeletedException(rep);
		}
	}

	@Override
	public DamageLocationConversion insertDamageLocationConversion(DamageLocationConversion damageLocationConversionObj,
			Map<String, String> headers) {
		// TODO Auto-generated method stub
		UserId.headerUserID(headers);
		if (damageLocationConversionRepository.existsByLocDscr(damageLocationConversionObj.getLocDscr())) {
			throw new RecordAlreadyExistsException("Location Description already exists ");
		} else {
			damageLocationConversionObj.setCreateUserId(headers.get(CommonConstants.USER_ID));
			damageLocationConversionObj.setUpdateUserId(headers.get(CommonConstants.USER_ID));
			damageLocationConversionObj.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
			damageLocationConversionObj.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
			damageLocationConversionObj.setUversion(damageLocationConversionObj.getUversion());
			damageLocationConversionObj.setLocCd(damageLocationConversionObj.getLocCd());
			damageLocationConversionObj.setLocDesc(damageLocationConversionObj.getLocDesc());

			damageLocationConversionRepository.save(damageLocationConversionObj);
			return damageLocationConversionObj;
		}
	}

	@Override
	public DamageLocationConversion updateDamageLocationConversion(DamageLocationConversion damageLocationConversionObj,
			Map<String, String> headers) {
		// TODO Auto-generated method stub
		UserId.headerUserID(headers);
		if (damageLocationConversionRepository.existsByLocDscrAndUversion(damageLocationConversionObj.getLocDscr(),
				damageLocationConversionObj.getUversion())) {
			DamageLocationConversion dmgLocConversion = damageLocationConversionRepository
					.findByLocDscr(damageLocationConversionObj.getLocDscr());
			dmgLocConversion.setCreateUserId(headers.get(CommonConstants.USER_ID));
			dmgLocConversion.setUpdateUserId(headers.get(CommonConstants.USER_ID));
			dmgLocConversion.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
			dmgLocConversion.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
			dmgLocConversion.setUversion(damageLocationConversionObj.getUversion());
			dmgLocConversion.setLocCd(damageLocationConversionObj.getLocCd());
			dmgLocConversion.setLocDesc(damageLocationConversionObj.getLocDesc());

			damageLocationConversionRepository.save(dmgLocConversion);
			return dmgLocConversion;
		} else
			throw new NoRecordsFoundException("Not a valid location description");
	}
}
