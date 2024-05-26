package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import com.nscorp.obis.domain.DamageCodeConversion;

public interface DamageCodeConversionService {

    List<DamageCodeConversion> getAllDamageCodeConversions();

    DamageCodeConversion getDamageCodeConversionByCatCode(Integer damageEntity, String reasonCd);

    void deleteCodeConversion(DamageCodeConversion damageCodeConversion);

	DamageCodeConversion addDamageCodeConversion(DamageCodeConversion damageCodeConversion,Map<String, String> headers);

	DamageCodeConversion updateDamageCodeConversion(DamageCodeConversion damageCodeReq, Map<String, String> headers);
}
