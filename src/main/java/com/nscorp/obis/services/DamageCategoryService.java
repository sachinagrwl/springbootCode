package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import com.nscorp.obis.domain.DamageCategory;
import com.nscorp.obis.domain.UnCd;

public interface DamageCategoryService {

	List<DamageCategory> getAllDamageCategory(Integer catCd);

	List<DamageCategory> deleteDamageCategory(DamageCategory damageCategory);

	DamageCategory addDamageCategory(DamageCategory damageCategory, Map<String, String> headers);
	DamageCategory updateDamageCategory(DamageCategory damageCategory, Map<String, String> headers);

}
