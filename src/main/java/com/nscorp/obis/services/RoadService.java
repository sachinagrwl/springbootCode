package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import com.nscorp.obis.domain.Road;

import javax.validation.Valid;

public interface RoadService {

	List<Road> getAllRoads(String roadNumber, String roadName, String roadFullName, String roadType);

	Road addRoad(Road roadDTOToRoad, Map<String, String> headers);

	Road updateRoad(Road road, Map<String, String> headers);

	Road deleteRoad(@Valid Road road);

}
