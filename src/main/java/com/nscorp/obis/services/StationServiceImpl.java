package com.nscorp.obis.services;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.Station;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.repository.StationRepository;
import com.nscorp.obis.repository.StationRestrictionRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Map;


@Service
@Transactional
public class StationServiceImpl implements StationService {

    @Autowired
    StationRepository stationRepository;
    
    @Autowired
    StationRestrictionRepository stationRestrictionRepo;


   @Override
	public Page<Station> searchStations(String stationName, String roadNumber, String FSAC, String state, String billAtFsac, String roadName, String operationStation, String splc,
										String rule260Station, String intermodalIndicator, String char5Spell, String char5Alias, String char8Spell, String division, Date expirationDate,
										Pageable pageable) {


		Page<Station> searchStationsPage = stationRepository.searchAll(stationName,roadNumber,FSAC,state,billAtFsac,roadName,operationStation,
				splc,rule260Station,intermodalIndicator,char5Spell,char5Alias,char8Spell,division,expirationDate, pageable);

	   if(searchStationsPage.getTotalPages() == 0)
			throw new NoRecordsFoundException("No records matches this search");

		if(searchStationsPage.getPageable().getPageNumber() >= searchStationsPage.getTotalPages())
			throw new SizeExceedException("Page Number must be less than or equal to " + searchStationsPage.getTotalPages());

		return searchStationsPage;
	}

	@Override
	public Station updateStation(@NotNull Station stationObj, Map<String,String> headers) {
		

		if(stationRepository.existsByTermId(stationObj.getTermId())) {
			UserId.headerUserID(headers);
			String extensionSchema = headers.get(CommonConstants.EXTENSION_SCHEMA);
			String userId = headers.get(CommonConstants.USER_ID);

			Station station = stationRepository.getByTermId(stationObj.getTermId());

			if(stationObj.getBillAtFsac() != null) {
				if(stationObj.getBillAtFsac().matches(CommonConstants.REGEX)){
					station.setBillAtFsac(stationObj.getBillAtFsac());
				} else {
					throw new RecordNotAddedException("'billAtFsac' should be in numeric");
				}
			} else {
				station.setBillAtFsac(stationObj.getBillAtFsac());
			}

			if(stationObj.getState() != null) {
				if(stationObj.getState().matches("[a-zA-Z]+")) {
					station.setState(stationObj.getState());
				} else {
					throw new RecordNotAddedException("'State' should have only alphabets");
				}
			} else {
				station.setState(stationObj.getState());
			}

			station.setOperationStation(stationObj.getOperationStation());
			if(stationObj.getIntermodalIndicator() != null) {
				if(stationObj.getIntermodalIndicator().equalsIgnoreCase("O") || 
						stationObj.getIntermodalIndicator().equalsIgnoreCase("S")) {
					station.setIntermodalIndicator(stationObj.getIntermodalIndicator());
				} else {
					throw new RecordNotAddedException("IntermodalIndicator value should be 'O','S' or null");
				}
			} 
			else {
				station.setIntermodalIndicator(stationObj.getIntermodalIndicator());
			}

			station.setRule260Station(stationObj.getRule260Station());

			if(stationObj.getRoadNumber() != null) {
				if(stationObj.getRoadNumber().matches(CommonConstants.REGEX)) {
					station.setRoadNumber(stationObj.getRoadNumber());
				} else {
					throw new RecordNotAddedException("'roadNumber' should be in numeric!");
				}
			} else {
				station.setRoadNumber(stationObj.getRoadNumber());
			}

			if(stationObj.getFSAC() != null) {
				if(stationObj.getFSAC().matches(CommonConstants.REGEX)) {
					station.setFSAC(stationObj.getFSAC());
				} else {
					throw new RecordNotAddedException("'fsac' should be in numeric!");
				}
			} else {
				station.setFSAC(stationObj.getFSAC());
			}

			if(StringUtils.isNotEmpty(stationObj.getUversion())) {
				stationObj.setUversion(
						Character.toString((char) ((((int)stationObj.getUversion().charAt(0) - 32) % 94) + 33)));
			}

			station.setStationName(stationObj.getStationName());
			station.setRoadName(stationObj.getRoadName());
			station.setSplc(stationObj.getSplc());
			station.setChar5Spell(stationObj.getChar5Spell());
			station.setChar5Alias(stationObj.getChar5Alias());
			station.setChar8Spell(stationObj.getChar8Spell());
			station.setDivision(stationObj.getDivision());
			station.setTopPick(stationObj.getTopPick());
			station.setBottomPick(stationObj.getBottomPick());
			station.setExpirationDate(stationObj.getExpirationDate());
			station.setUpdateDateTime(stationObj.getUpdateDateTime());
			if(StringUtils.isNotEmpty(station.getUversion())) {
				station.setUversion(
						Character.toString((char) ((((int)station.getUversion().charAt(0) - 32) % 94) + 33)));
			}
			station.setUpdateUserId(userId.toUpperCase());
			station.setUpdateExtensionSchema(extensionSchema);
			stationRepository.save(station);
			return station;
		}else {
			throw new NoRecordsFoundException("Record with TermId "+stationObj.getTermId()+" Not Found!");
		}
	}

}
