package com.nscorp.obis.services;

import com.nscorp.obis.domain.EMSIngateRestriction;

import java.util.List;
import java.util.Map;

public interface EMSIngateRestrictionService {

    List<EMSIngateRestriction> getAllRestrictions();

	EMSIngateRestriction insertRestriction(EMSIngateRestriction restrictionObj, Map<String,String> headers);

	EMSIngateRestriction updateRestriction(EMSIngateRestriction restrictionObj, Map<String,String> headers);
}
