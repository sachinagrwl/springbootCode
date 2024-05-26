package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.nscorp.obis.domain.AARWhyMadeCodes;

public interface AARWhyMadeCodesService {

	public List<AARWhyMadeCodes> getAARWhyMadeCodes();
	AARWhyMadeCodes updateAARWhyMadeCodes(@Valid AARWhyMadeCodes aarWhyMadeCodeObj,Map<String, String> headers);
	void deleteAARWhyMadeCodes(AARWhyMadeCodes aarWhyMadeCodesObj);

	AARWhyMadeCodes addAARWhyMadeCodes(@Valid AARWhyMadeCodes aarWhyMadeCodeObj,Map<String, String> headers);

}
