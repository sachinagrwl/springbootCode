package com.nscorp.obis.services;

import com.nscorp.obis.domain.Station;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Map;


public interface StationService {

    Page<Station> searchStations(String stationName, String roadNumber, String FSAC, String state, String billAtFsac, String roadName, String operationStation, String splc,
								 String rule260Station, String intermodalIndicator, String char5Spell, String char5Alias, String char8Spell, String division, Date expirationDate,
								 Pageable pageable);

	Station updateStation(@NotNull Station stationObj, Map<String,String> headers);


}
