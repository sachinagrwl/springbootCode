package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import com.nscorp.obis.domain.DamageLocationConversion;

public interface DamageLocationConversionService {
	List<DamageLocationConversion> getAllDamageLocationConversion();

	void deleteDamageLocationConversion(DamageLocationConversion damageLocationConversion);

	DamageLocationConversion insertDamageLocationConversion(DamageLocationConversion damageLocationConversion,
			Map<String, String> headers);

	DamageLocationConversion updateDamageLocationConversion(DamageLocationConversion damageLocationConversion,
			Map<String, String> headers);
}
