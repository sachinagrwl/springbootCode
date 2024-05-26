package com.nscorp.obis.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.EMSEquipmentLengthRestriction;
import com.nscorp.obis.domain.EMSIngateRestriction;
import com.nscorp.obis.domain.Station;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.CorporateCustomerRepository;
import com.nscorp.obis.repository.EMSIngateRestrictionRepository;
import com.nscorp.obis.repository.StationRepository;
import com.nscorp.obis.repository.TerminalRepository;

@Service
@Transactional
public class EMSIngateRestrictionServiceImpl implements EMSIngateRestrictionService {

    @Autowired
	EMSIngateRestrictionRepository emsIngateRestrictionRepository;

    @Autowired
    TerminalRepository terminalRepository;
    
    @Autowired
    StationRepository stationRepository;
    
    @Autowired
    CorporateCustomerRepository corporateCustomerRepository;
    
    private void emsRestrictionValidations(EMSIngateRestriction restrictionObj) {
    	
    	// Check Ingate terminal is valid or not
    	if(restrictionObj.getIngateTerminalId() != null && !terminalRepository.existsByTerminalIdAndExpiredDateIsNull((Long) restrictionObj.getIngateTerminalId())) {
			throw new NoRecordsFoundException("Ingate Terminal with Id "+restrictionObj.getIngateTerminalId()+" not found!");
		}

    	
        //Auto populate online origin using STN_XRF ID
        if(restrictionObj.getIngateTerminalId() != null && restrictionObj.getIngateTerminalId() != 99999999999999L){
            Long originStationId = terminalRepository.getStationXrfId(restrictionObj.getIngateTerminalId());

            
            
            if(restrictionObj.getOnlineOriginStation() == null) {
            	restrictionObj.setOnlineOriginStation(new Station());
                restrictionObj.getOnlineOriginStation().setTermId(originStationId);
            }
            else if(!originStationId.equals(restrictionObj.getOnlineOriginStation().getTermId())){
                throw new RecordNotAddedException("Online Origin station Id " + restrictionObj.getOnlineOriginStation().getTermId() + " is either not valid or not found in 'Terminal' table!");
            }
        }

        //Check for Non-terminal
        
        if(restrictionObj.getIngateTerminalId() == CommonConstants.NON_TERMINAL_SPECIFIC && restrictionObj.getOnlineOriginStation() != null) {
        		throw new RecordNotAddedException("Online Origin Station with Id should be null for Non-Terminal specific EMS Ingate Restriction!");
         
        }

      //Check Online destination and offline destinations are valid or not; STATION_XRF where expired date is null
        
		if(restrictionObj.getOnlineDestinationStation() != null && !stationRepository.existsByTermIdAndExpiredDateIsNull(restrictionObj.getOnlineDestinationStation().getTermId())) {
			throw new NoRecordsFoundException("Online Destination Station with Id "+restrictionObj.getOnlineDestinationStation().getTermId()+" not found!");
		}

		
		if(restrictionObj.getOfflineDestinationStation() != null && !stationRepository.existsByTermIdAndExpiredDateIsNull(restrictionObj.getOfflineDestinationStation().getTermId())) {
			throw new NoRecordsFoundException("Offline Destination Station with Id "+restrictionObj.getOfflineDestinationStation().getTermId()+" not found!");
		}
		
		
		if(restrictionObj.getEquipmentInit() != null && !restrictionObj.getEquipmentInit().matches("^[a-zA-Z]*$")) {
			throw new RecordNotAddedException("Equipment Init should have only character values");
		}

		//Check Corporate customerId is valid or not
		
		if(restrictionObj.getCorporateCustomerId() != null && !corporateCustomerRepository.existsById(restrictionObj.getCorporateCustomerId())) {
			throw new NoRecordsFoundException("Corporate Customer Id : "+restrictionObj.getCorporateCustomerId()+" not found!");
		}

		// Both fields are required at a time
		if(restrictionObj.getCorporateCustomerId() == null && restrictionObj.getLineOfBusinesses().isEmpty()) {
				throw new RecordNotAddedException("A 'Corporate Shipper(Customer)' or a 'Primary Line Of Business' value is required!");
		}
		if(restrictionObj.getWayBillRoute() == null && restrictionObj.getTrafficTypes().isEmpty()) {
				throw new RecordNotAddedException("A 'Way Bill Route' or a 'Traffic Type' value is required!");
		}

		//Active field is required
		if(!(restrictionObj.getActive().equalsIgnoreCase("T"))
				&& !(restrictionObj.getActive().equalsIgnoreCase("F"))) {
			throw new RecordNotAddedException("'active' value must be either 'T' or 'F'");
		}

		//TemporaryIndicator fields is required
		if(!(restrictionObj.getTemporaryIndicator().equalsIgnoreCase("T"))
				&& !(restrictionObj.getTemporaryIndicator().equalsIgnoreCase("F"))) {
			throw new RecordNotAddedException("'temporaryIndicator' value must be either 'T' or 'F'");
		}

		//Load Empty code is required and values must be L,E or B
		if(!(restrictionObj.getLoadEmptyCode().equalsIgnoreCase("L"))
				&& !(restrictionObj.getLoadEmptyCode().equalsIgnoreCase("E")) && !(restrictionObj.getLoadEmptyCode().equalsIgnoreCase("B"))) {
			throw new RecordNotAddedException("'loadEmptyCode' value must be 'L', 'E' or 'B'");
		}

		//Equipment length should be '240', '336', '480', '540', '576', '636' or null
		if(restrictionObj.getEquipmentLength() != null) {
			if(restrictionObj.getEquipmentLength() != 240
					&& restrictionObj.getEquipmentLength() != 336 && restrictionObj.getEquipmentLength() != 480
					&& restrictionObj.getEquipmentLength() != 540 && restrictionObj.getEquipmentLength() != 576
					&& restrictionObj.getEquipmentLength() != 636) {
				throw new RecordNotAddedException("'equipmentLength' value must be '240', '336', '480', '540', '576', '636' or null");
			}
		}
    	
		//Check whether any one Equipment Type & Length value is given
		if(restrictionObj.getEquipmentTypes().isEmpty()) {
			throw new RecordNotAddedException("Any One Equipment Type value should be provided!");
		}
		if(restrictionObj.getEmsEquipmentLengthRestrictions().isEmpty()) {
			throw new RecordNotAddedException("Any One Equipment Length value should be provided!");
		}

		//Either RESTRICT_ALL or ANY size
		if(restrictionObj.getEmsEquipmentLengthRestrictions().size() > 1 && restrictionObj.getEmsEquipmentLengthRestrictions().contains(EMSEquipmentLengthRestriction.RESTRICT_ALL)){
			throw new RecordNotAddedException("EMS Length Restrictions field requires either 'RESTRICT_ALL' or Size");
		}
		
    }

    @Override
    public List<EMSIngateRestriction> getAllRestrictions() {
        List<EMSIngateRestriction> emsIngateRestrictions = emsIngateRestrictionRepository.findAll();

        if(emsIngateRestrictions.isEmpty())
            throw new NoRecordsFoundException("No Records are found for EMS Ingate Restrictions");

        return emsIngateRestrictions;
    }

	@Override
	public EMSIngateRestriction insertRestriction(EMSIngateRestriction restrictionObj, Map<String,String> headers) {

		Long generatedRestrictId = emsIngateRestrictionRepository.SGK();
		restrictionObj.setRestrictId(generatedRestrictId);
		UserId.headerUserID(headers);
		String extensionSchema = headers.get(CommonConstants.EXTENSION_SCHEMA);
		String userId = headers.get(CommonConstants.USER_ID);

		if(restrictionObj.getOnlineOriginStation() != null) {
			Station onlineOriginStation = stationRepository.findById(restrictionObj.getOnlineOriginStation().getTermId()).get();
			restrictionObj.setOnlineOriginStation(onlineOriginStation);
		}
		
		if(restrictionObj.getOnlineDestinationStation() != null) {
			Station onlineDestinationStation = stationRepository.findById(restrictionObj.getOnlineDestinationStation().getTermId()).get();
			restrictionObj.setOnlineDestinationStation(onlineDestinationStation);
		}
		
		if(restrictionObj.getOfflineDestinationStation() != null) {
			Station offlineDestinationStation = stationRepository.findById(restrictionObj.getOfflineDestinationStation().getTermId()).get();
			restrictionObj.setOfflineDestinationStation(offlineDestinationStation);
		}

		if(extensionSchema != null) {
			extensionSchema = extensionSchema.toUpperCase();
		} else {
			throw new NullPointerException("Extension Schema should not be null, empty or blank");
		}
		
		//Validating each fields
		emsRestrictionValidations(restrictionObj);
		
		restrictionObj.setCreateUserId(userId.toUpperCase());
		restrictionObj.setUpdateUserId(userId.toUpperCase());
		restrictionObj.setCreateExtensionSchema(extensionSchema);
		restrictionObj.setUpdateExtensionSchema(extensionSchema);
		restrictionObj.setUversion("!");

		if(restrictionObj.getIngateTerminalId() != null && restrictionObj.getStartTime() != null
				&& restrictionObj.getEndTime() != null && !(restrictionObj.getEmsEquipmentLengthRestrictions().isEmpty())
				&& !(restrictionObj.getEquipmentTypes().isEmpty())) {
			emsIngateRestrictionRepository.save(restrictionObj);
		} else {
			throw new RecordNotAddedException("'Ingate Terminal, Start Date, End Date, Equipment Type & Equipment Length' "
					+ "values should be present!");
		}

		EMSIngateRestriction restriction = emsIngateRestrictionRepository.findByRestrictId(restrictionObj.getRestrictId());
		return restriction;
	}

	@Override
	public EMSIngateRestriction updateRestriction(@Valid EMSIngateRestriction restrictionObj, Map<String,String> headers) {

		UserId.headerUserID(headers);
		String extensionSchema = headers.get(CommonConstants.EXTENSION_SCHEMA);
		String userId = headers.get(CommonConstants.USER_ID);
		Timestamp currentDateTime = new Timestamp(System.currentTimeMillis());
		
		if(restrictionObj.getOnlineOriginStation() != null) {
			Station onlineOriginStation = stationRepository.findById(restrictionObj.getOnlineOriginStation().getTermId()).get();
			restrictionObj.setOnlineOriginStation(onlineOriginStation);
		}
		
		if(restrictionObj.getOnlineDestinationStation() != null) {
			Station onlineDestinationStation = stationRepository.findById(restrictionObj.getOnlineDestinationStation().getTermId()).get();
			restrictionObj.setOnlineDestinationStation(onlineDestinationStation);
		}
		
		if(restrictionObj.getOfflineDestinationStation() != null) {
			Station offlineDestinationStation = stationRepository.findById(restrictionObj.getOfflineDestinationStation().getTermId()).get();
			restrictionObj.setOfflineDestinationStation(offlineDestinationStation);
		}


		//Check Extension Schema is not null
		if(extensionSchema != null) {
			extensionSchema = extensionSchema.toUpperCase();
		} else {
			throw new NullPointerException("Extension Schema should not be null, empty or blank");
		}

    	//Check RestrictId is not null
		if(restrictionObj.getRestrictId() == null)
			throw new NoRecordsFoundException("Restrict Id is mandatory Field");

    	//Check RestrictId is available or not
    	if(!emsIngateRestrictionRepository.existsByRestrictId(restrictionObj.getRestrictId())) {
			throw new NoRecordsFoundException("Record Not Found!");
		}
    	
    	//Validating each fields
    	emsRestrictionValidations(restrictionObj);

		//Create Date is throwing an error, So setting the Date from repository 
		EMSIngateRestriction emsIngateRestriction = emsIngateRestrictionRepository.findByRestrictId(restrictionObj.getRestrictId());
//		emsIngateRestriction.setCreateDateTime(restrictionObj.getCreateDateTime());
		if(emsIngateRestriction.getCreateExtensionSchema() == null) {
			restrictionObj.setCreateExtensionSchema(extensionSchema);
		}
		restrictionObj.setUpdateDateTime(currentDateTime);
		restrictionObj.setUpdateUserId(userId.toUpperCase());
		emsIngateRestriction.setUversion(restrictionObj.getUversion());
		restrictionObj.setUpdateExtensionSchema(extensionSchema);
		if(emsIngateRestriction.getCreateUserId() == null) {
			restrictionObj.setCreateUserId(userId.toUpperCase());
		}

		//Check all required fields are there
		if(restrictionObj.getIngateTerminalId() != null && restrictionObj.getStartTime() != null
				&& restrictionObj.getEndTime() != null && !(restrictionObj.getEmsEquipmentLengthRestrictions().isEmpty())
				&& !(restrictionObj.getEquipmentTypes().isEmpty())) {
			System.out.println(emsIngateRestriction.toString());
			System.out.println("startDate: " + emsIngateRestriction.getStartDate());
			emsIngateRestrictionRepository.save(restrictionObj);
		} else {
			throw new RecordNotAddedException("'Ingate Terminal, Start Date, End Date, Equipment Type & Equipment Length' "
					+ "values should be present!");
		}

		return emsIngateRestriction;
	}

}
