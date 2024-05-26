package com.nscorp.obis.services;

import com.nscorp.obis.domain.CarEmbargo;
import com.nscorp.obis.domain.HazRestriction;

import java.util.List;
import java.util.Map;

public interface HazRestrictionService {

    HazRestriction insertHazRestriction(HazRestriction hazRestrictionObj, Map<String, String> headers);
    HazRestriction updateHazRestriction(HazRestriction hazRestrictionObj, Map<String, String> headers);
	List<HazRestriction> getHazRestriction(String unCd);
    HazRestriction deleteHazRestriction(HazRestriction hazRestriction);
}
