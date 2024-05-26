package com.nscorp.obis.services;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.StationRestriction;
import com.nscorp.obis.exception.*;
import com.nscorp.obis.repository.StationRestrictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class StationRestrictionServiceImpl implements StationRestrictionService {

	@Autowired
	private StationRestrictionRepository stationRestrictionRepo;
	
	@Override
	public List<StationRestriction> getStationRestriction(Long termId) {
		List<StationRestriction> stations = stationRestrictionRepo.findByStationCrossReferenceId(termId);
		if(stations.isEmpty()) {
			throw new NoRecordsFoundException("No Records are found for Stations");
		}
		return stations;
	}

	@Override
	public StationRestriction addStationRestriction(Long termId, StationRestriction stationRestriction,
													Map<String,String> headers) {
		
		String carType = null, freightType = null;

		if(stationRestriction.getCarType() != null) {
			carType = stationRestriction.getCarType().toUpperCase();
		}

		if(stationRestriction.getFreightType() != null) {
			freightType = stationRestriction.getFreightType().toUpperCase();
		}

		if(carType != null && carType.length() != 0 && (!carType.startsWith("P") && !carType.startsWith("Q") && !carType.startsWith("S")|| carType.startsWith("_"))) {
			if((carType.startsWith("_") && freightType == null) || (carType.startsWith("_")  && !freightType.startsWith("_"))) {
				if(!carType.equalsIgnoreCase("____") ) {
					throw new RecordNotAddedException("CarType : Character 2 to 4 must be '_'");
				}
			}
			else
				throw new RecordNotAddedException("Car Type should start with 'P', 'Q' & 'S' AND Car Type and Freight Type should not be null or '_'");
		}

		if(freightType != null && freightType.length() != 0 && (!freightType.startsWith("U") && !freightType.startsWith("Z") || freightType.startsWith("_"))){
			if((freightType.startsWith("_") && carType == null) || (freightType.startsWith("_") && !carType.startsWith("_"))) {
				if(!freightType.equalsIgnoreCase("____") ) {
					throw new RecordNotAddedException("FreightType : Character 2 to 4 must be '_'");
				}
			}
			else
				throw new RecordNotAddedException("Freight Type should start with 'U' & 'Z' AND Car Type and Freight Type should not be null or '_'");
		}

		if(carType == null){
			carType = "____";
		}

		if(freightType == null){
			freightType = "____";
		}

		if(carType.length() != CommonConstants.CAR_TYPE_MAX_SIZE ){
			for(int i=carType.length();i<CommonConstants.CAR_TYPE_MAX_SIZE;i++){
				carType += "_";
			}
		}

		if(freightType.length() != CommonConstants.FREIGHT_TYPE_MAX_SIZE ){
			for(int i=freightType.length();i<CommonConstants.FREIGHT_TYPE_MAX_SIZE;i++){
				freightType += "_";
			}
		}

		if(carType.equalsIgnoreCase("____") && freightType.equalsIgnoreCase("____")) {
			throw new SizeExceedException("Car Type and Freight Type both should not be null or '_'");
		}

		if(!carType.substring(1).matches(CommonConstants.STN_REGEX)){
			throw new RecordNotAddedException("CarType : Character 2 to 4 must be numeric or '_'");
		}

		if(!freightType.substring(1).matches(CommonConstants.STN_REGEX)){
			throw new RecordNotAddedException("FreightType : Character 2 to 4 must be numeric or '_'");
		}

		if(stationRestrictionRepo.existsByStationCrossReferenceIdAndCarTypeAndFreightType(termId, carType, freightType)){
			throw new RecordAlreadyExistsException("Record with Reference Id "+termId+ ", Car Type "
					+stationRestriction.getCarType()+ " and Freight Type " + stationRestriction.getFreightType()+ " Already Exists!");
		}
		UserId.headerUserID(headers);

		stationRestriction.setCarType(carType);
		stationRestriction.setFreightType(freightType);
		stationRestriction.setStationCrossReferenceId(termId);
		stationRestriction.setCreateUserId(headers.get(CommonConstants.USER_ID));
		stationRestriction.setUpdateUserId(headers.get(CommonConstants.USER_ID));
		stationRestriction.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
		stationRestriction.setUversion("!");
		StationRestriction restrictions = stationRestrictionRepo.save(stationRestriction);
		
		if(restrictions == null) {
			throw new RecordNotAddedException("Record with Reference Id "+ termId+ ", Car Type "
					+stationRestriction.getCarType().toUpperCase()+ " and Freight Type " + stationRestriction.getFreightType().toUpperCase()+ " Not Added!");
		}
		return restrictions;

	}

	@Override
	public void deleteStationRestriction(Long termId,
			@Valid StationRestriction stationRestrictionsObj) {
        String carType, freightType;
		if (stationRestrictionsObj.getCarType() != null) {
            carType = stationRestrictionsObj.getCarType().toUpperCase();
        } else {
            carType = stationRestrictionsObj.getCarType();
        }
        if (stationRestrictionsObj.getFreightType() != null) {
            freightType = stationRestrictionsObj.getFreightType().toUpperCase();
        } else {
            freightType = stationRestrictionsObj.getFreightType();
        }
        if (stationRestrictionRepo.existsByStationCrossReferenceIdAndCarTypeAndFreightType(termId, carType, freightType)) {
            stationRestrictionRepo.deleteByStationCrossReferenceIdAndCarTypeAndFreightType(termId, carType, freightType);
        } else {
            String rep = termId + ", " + carType + " and " + freightType + " Record Not Found!";
            throw new RecordNotDeletedException(rep);
        }
    }

}
