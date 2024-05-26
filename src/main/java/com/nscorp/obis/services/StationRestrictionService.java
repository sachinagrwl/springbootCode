package com.nscorp.obis.services;

import com.nscorp.obis.domain.StationRestriction;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

public interface StationRestrictionService {

	List<StationRestriction> getStationRestriction(Long termId);

	void deleteStationRestriction(Long termId, @Valid StationRestriction stationRestrictionsObj);

	StationRestriction addStationRestriction(Long termId, StationRestriction stationRestrictions, Map<String,String> headers);

}
