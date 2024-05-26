package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.StationRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.Road;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.repository.RoadRepository;

@Service
@Transactional
public class RoadServiceImpl implements RoadService {

	@Autowired
	private RoadRepository roadRepository;

	@Autowired
	private StationRepository stationRepository;

	@Override
	public List<Road> getAllRoads(String roadNumber, String roadName, String roadFullName, String roadType) {
		List<Road> roadList = roadRepository.findAll(roadNumber, roadName, roadFullName, roadType);
		if (roadList.isEmpty()) {
			throw new NoRecordsFoundException("No Record Found under this search!");
		}
		return roadList;
	}

	@Override
	public Road addRoad(Road road, Map<String, String> headers) {
		UserId.headerUserID(headers);
		roadValidations(road, headers);
		String userId = headers.get(CommonConstants.USER_ID);
		road.setCreateUserId(userId.toUpperCase());
		road.setUpdateUserId(userId.toUpperCase());
		road.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		road.setUversion("!");
		return roadRepository.save(road);
	}

	@Override
	public Road updateRoad(Road road, Map<String, String> headers) {
		UserId.headerUserID(headers);
		String extensionSchema = headers.get(CommonConstants.EXTENSION_SCHEMA);
		if (extensionSchema == null) {
			throw new NullPointerException("Extension Schema should not be null, empty or blank.");
		}
		String userId = headers.get(CommonConstants.USER_ID);
		if(roadRepository.existsByRoadNumberAndUversion(road.getRoadNumber(), road.getUversion())){
			Road existingRoad = roadRepository.findByRoadNumber(road.getRoadNumber());
			if(!road.getRoadName().equals(existingRoad.getRoadName())) {
				if (roadRepository.existsByRoadName(road.getRoadName())) {
					throw new RecordAlreadyExistsException(
							"The Provided RoadName: " + road.getRoadName() + " is already exists.");
				}
			}
			existingRoad.setUpdateUserId(userId.toUpperCase());
			existingRoad.setUpdateExtensionSchema(extensionSchema.toUpperCase());
			existingRoad.setRoadName(road.getRoadName());
			existingRoad.setRoadFullName(road.getRoadFullName());
			existingRoad.setRoadType(road.getRoadType());
			/* Audit fields */
			if(StringUtils.isNotEmpty(existingRoad.getUversion())) {
				existingRoad.setUversion(
						Character.toString((char) ((((int)existingRoad.getUversion().charAt(0) - 32) % 94) + 33)));
			}
			roadRepository.save(existingRoad);
			return existingRoad;
		}
		else
			throw new NoRecordsFoundException("No record Found Under this Road Number:"+road.getRoadNumber()
					+ " and Uversion:" + road.getUversion());
	}

	@Override
	public Road deleteRoad(Road road) {
		if(roadRepository.existsByRoadNumberAndUversion(road.getRoadNumber(), road.getUversion())) {
			if(stationRepository.existsByRoadNumber(road.getRoadNumber())){
				String err = road.getRoadNumber() + " still has restricted links to Station Cross Reference Table";
				throw new RecordNotDeletedException(err);
			}
			else {
				Road existingRoad = roadRepository.findById(road.getRoadNumber()).get();
				roadRepository.deleteById(road.getRoadNumber());
				return existingRoad;
			}
		}
		else {
			String rep = "No record Found Under this Road Number:" + road.getRoadNumber()
					+ " and Uversion:" + road.getUversion();
			throw new RecordNotDeletedException(rep);
		}
	}

	private void roadValidations(Road road, Map<String, String> headers) {

		if (headers.get(CommonConstants.EXTENSION_SCHEMA) == null) {
			throw new NullPointerException("Extension Schema should not be null, empty or blank.");
		}
		if (roadRepository.existsById(road.getRoadNumber())) {
			throw new RecordAlreadyExistsException(
					"The Provided RoadNumber: " + road.getRoadNumber() + " is already exists.");
		}
		if (roadRepository.existsByRoadName(road.getRoadName())) {
			throw new RecordAlreadyExistsException(
					"The Provided RoadName: " + road.getRoadName() + " is already exists.");
		}
	}
}
