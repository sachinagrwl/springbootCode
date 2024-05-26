package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import com.nscorp.obis.domain.DamageArea;


public interface DamageAreaService {
	List<DamageArea> getAllDamageArea();
    DamageArea addDamageArea(DamageArea damageAreaDTOToDamageArea, Map<String, String> headers);
	public void deleteDamageArea(DamageArea damageAreaObj);
	DamageArea updateDamageArea(DamageArea damageArea, Map<String, String> headers);
}
