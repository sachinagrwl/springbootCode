package com.nscorp.obis.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import com.nscorp.obis.domain.Station;
import com.nscorp.obis.domain.UnCd;
import com.nscorp.obis.exception.InvalidDataException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.DamageCategory;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.repository.DamageCategoryRepository;

@Transactional
@Service
public class DamageCategoryServiceImpl implements DamageCategoryService {

	@Autowired
	DamageCategoryRepository damageCategoryRepo;

	@Override
	public List<DamageCategory> getAllDamageCategory(Integer catCd) {

		List<DamageCategory> damageCategoryList = new ArrayList<>();
		damageCategoryList = damageCategoryRepo.findAll();
		if (damageCategoryList.isEmpty()) {
			throw new NoRecordsFoundException("No Records found!");
		}
		return damageCategoryList;
	}
	
	@Override
	public List<DamageCategory> deleteDamageCategory(DamageCategory damageCategory) {
		if(damageCategoryRepo.existsByCatCd(damageCategory.getCatCd())) {
			List<DamageCategory> damageDel = damageCategoryRepo.findByCatCd(damageCategory.getCatCd());
			damageCategoryRepo.deleteByCatCd(damageCategory.getCatCd());
			return damageDel;
		}
		else {
			throw new NoRecordsFoundException("No Records Found!");
		}
	}

	@Override
	public DamageCategory addDamageCategory(DamageCategory damageCategoryObj, Map<String, String> headers) {
		UserId.headerUserID(headers);
		
		if(damageCategoryObj.getCatCd()!=null && damageCategoryRepo.existsByCatCd(damageCategoryObj.getCatCd())) {
			throw new RecordAlreadyExistsException("Record with "+damageCategoryObj.getCatCd() +" Already Exists!");
		}
		
		damageCategoryObj.setCreateUserId(headers.get(CommonConstants.USER_ID));
		damageCategoryObj.setUpdateUserId(headers.get(CommonConstants.USER_ID));
		damageCategoryObj.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		damageCategoryObj.setUversion("!");

		DamageCategory code = damageCategoryRepo.save(damageCategoryObj);
		return code;
	}

	@Override
	public DamageCategory updateDamageCategory(DamageCategory damageCategory, Map<String, String> headers) {
		UserId.headerUserID(headers);

		if (damageCategoryRepo.existsByCatCdAndUversion(damageCategory.getCatCd(), damageCategory.getUversion())) {

			DamageCategory existingDmgCat = damageCategoryRepo.findById(damageCategory.getCatCd()).get();

			if (!StringUtils.equals(String.valueOf(existingDmgCat.getCatCd()), String.valueOf(damageCategory.getCatCd())))
				throw new InvalidDataException("Invalid Change, CAT_CD is not editable");

			existingDmgCat.setUversion("!");
			existingDmgCat.setCatDscr(damageCategory.getCatDscr());
			existingDmgCat.setUpdateUserId(headers.get(CommonConstants.USER_ID).toUpperCase());
			existingDmgCat.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA).toUpperCase());

			damageCategoryRepo.save(existingDmgCat);
			return existingDmgCat;
		} else {
			throw new NoRecordsFoundException("No record found for this 'CAT_CD': " + damageCategory.getCatCd());
		}
	}

}
