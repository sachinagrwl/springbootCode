package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.nscorp.obis.domain.AARLocationCode;
import com.nscorp.obis.domain.DamageComponent;

public interface AARLocationCodeService {

	AARLocationCode updateAARLocationCode(@Valid AARLocationCode aarLocationCodeObj, Map<String, String> headers);

	AARLocationCode addAARLocationCode(AARLocationCode aarLocationCode, Map<String, String> headers);

	AARLocationCode getAARLocationCodesByLocCode(String aarLocationCode);

	List<AARLocationCode> getAllAARLocationCodes();

	void deleteAARLocationCode(AARLocationCode aarLocationCode);


}
