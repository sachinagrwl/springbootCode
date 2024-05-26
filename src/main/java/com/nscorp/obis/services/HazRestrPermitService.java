package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import com.nscorp.obis.domain.HazRestrPermit;

public interface HazRestrPermitService {

	List<HazRestrPermit> getHazRestrPermit();

	HazRestrPermit deleteHazRestrPermit(HazRestrPermit object);

	HazRestrPermit addHazRestrPermit(HazRestrPermit hazRestrPermit, Map<String, String> headers);

    HazRestrPermit updateHazRestrPermit(HazRestrPermit hazRestrictionReq, Map<String, String> headers);
}
