package com.nscorp.obis.services;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.EMSAllotmentType;
import com.nscorp.obis.domain.EMSEquipmentLengthRestriction;
import com.nscorp.obis.domain.EMSIngateAllocation;
import com.nscorp.obis.domain.Station;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.repository.CorporateCustomerRepository;
import com.nscorp.obis.repository.EMSIngateAllocationRepository;
import com.nscorp.obis.repository.StationRepository;
import com.nscorp.obis.repository.TerminalRepository;

@Service
@Transactional
public class EMSIngateAllocationServiceImpl implements EMSIngateAllocationService {

	@Autowired
	EMSIngateAllocationRepository emsIngateAllocationRepository;
	
	@Autowired
    TerminalRepository terminalRepository;
    
    @Autowired
    StationRepository stationRepository;
    
    @Autowired
    CorporateCustomerRepository corporateCustomerRepository;
	
private void emsAllocationValidations(EMSIngateAllocation allocationObj) {
    	
    	// Check Ingate terminal is valid or not
    	if(allocationObj.getIngateTerminalId() != null && !terminalRepository.existsByTerminalIdAndExpiredDateIsNull((Long) allocationObj.getIngateTerminalId())) {
			throw new NoRecordsFoundException("Ingate Terminal with Id "+allocationObj.getIngateTerminalId()+" not found!");
		}

        //Auto populate online origin using STN_XRF ID
        if(allocationObj.getIngateTerminalId() != null && allocationObj.getIngateTerminalId() != 99999999999999L){
            Long originStationId = terminalRepository.getStationXrfId(allocationObj.getIngateTerminalId());

            if(allocationObj.getOnlineOriginStation() == null) {
            	allocationObj.setOnlineOriginStation(new Station());
            	allocationObj.getOnlineOriginStation().setTermId(originStationId);
            }
            else if(!originStationId.equals(allocationObj.getOnlineOriginStation().getTermId())){
                throw new RecordNotAddedException("Online Origin station with Id " + allocationObj.getOnlineOriginStation().getTermId() + " is either not valid or not found in 'Terminal' table!");
            }
        }

        //Check for Non-terminal
        if(allocationObj.getIngateTerminalId() == CommonConstants.NON_TERMINAL_SPECIFIC && allocationObj.getOnlineOriginStation() != null) {
                throw new RecordNotAddedException("Online Origin Station Id should be null for Non-Terminal specific EMS Ingate Restriction!");
            }

      //Check Online destination and offline destinations are valid or not; STATION_XRF where expired date is null
		if(allocationObj.getOnlineDestinationStation() != null && !stationRepository.existsByTermIdAndExpiredDateIsNull(allocationObj.getOnlineDestinationStation().getTermId())) {
			throw new NoRecordsFoundException("Online Destination Station with Id "+allocationObj.getOnlineDestinationStation().getTermId()+" not found!");
		}

		if(allocationObj.getOfflineDestinationStation() != null && !stationRepository.existsByTermIdAndExpiredDateIsNull(allocationObj.getOfflineDestinationStation().getTermId())) {
			throw new NoRecordsFoundException("Offline Destination Station with Id "+allocationObj.getOfflineDestinationStation().getTermId()+" not found!");
		}
		
		if(allocationObj.getEquipmentInit() != null && !allocationObj.getEquipmentInit().matches("^[a-zA-Z]*$")) {
			throw new RecordNotAddedException("Equipment Init should have only character values");
		}
		
		if(allocationObj.getAllotmentType().equals(EMSAllotmentType.FIXED)) {
			if(allocationObj.getStartDate() == null) {
				throw new RecordNotAddedException("For AllotmentType 'FIXED', Both 'StartDate' & 'EndDate' are required");
			}
			if(allocationObj.getEndDate() == null) {
				throw new RecordNotAddedException("For AllotmentType 'FIXED', Both 'StartDate' & 'EndDate' are required");
			}
		}
		
		if(allocationObj.getNumberIngated() == null) {
			allocationObj.setNumberIngated(0);
		}

		//Check Corporate customerId is valid or not
		if(allocationObj.getCorporateCustomerId() != null && !corporateCustomerRepository.existsById(allocationObj.getCorporateCustomerId())) {
			throw new NoRecordsFoundException("Corporate Customer Id : "+allocationObj.getCorporateCustomerId()+" not found!");
		}

		// Both fields are required at a time
		if(allocationObj.getCorporateCustomerId() == null && allocationObj.getLineOfBusinesses().isEmpty()) {
				throw new RecordNotAddedException("A 'Corporate Shipper(Customer)' or a 'Primary Line Of Business' value is required!");
		}
		if(allocationObj.getWayBillRoute() == null && allocationObj.getTrafficTypes().isEmpty()) {
				throw new RecordNotAddedException("A 'Way Bill Route' or a 'Traffic Type' value is required!");
		}

		//Active field is required
		if(!(allocationObj.getActive().equalsIgnoreCase("T"))
				&& !(allocationObj.getActive().equalsIgnoreCase("F"))) {
			throw new RecordNotAddedException("'active' value must be either 'T' or 'F'");
		}

		//TemporaryIndicator fields is required
		if(!(allocationObj.getTemporaryIndicator().equalsIgnoreCase("T"))
				&& !(allocationObj.getTemporaryIndicator().equalsIgnoreCase("F"))) {
			throw new RecordNotAddedException("'temporaryIndicator' value must be either 'T' or 'F'");
		}

		//Load Empty code is required and values must be L,E or B
		if(!(allocationObj.getLoadEmptyCode().equalsIgnoreCase("L"))
				&& !(allocationObj.getLoadEmptyCode().equalsIgnoreCase("E")) && !(allocationObj.getLoadEmptyCode().equalsIgnoreCase("B"))) {
			throw new RecordNotAddedException("'loadEmptyCode' value must be 'L', 'E' or 'B'");
		}

		//Equipment length should be '240', '336', '480', '540', '576', '636' or null
		if(allocationObj.getEquipmentLength() != null) {
			if(allocationObj.getEquipmentLength() != 240
					&& allocationObj.getEquipmentLength() != 336 && allocationObj.getEquipmentLength() != 480
					&& allocationObj.getEquipmentLength() != 540 && allocationObj.getEquipmentLength() != 576
					&& allocationObj.getEquipmentLength() != 636) {
				throw new RecordNotAddedException("'equipmentLength' value must be '240', '336', '480', '540', '576', '636' or null");
			}
		}
    	
		//Check whether any one Equipment Type & Length value is given
		if(allocationObj.getEquipmentTypes().isEmpty()) {
			throw new RecordNotAddedException("Any One Equipment Type value should be provided!");
		}
		if(allocationObj.getEmsEquipmentLengthRestrictions().isEmpty()) {
			throw new RecordNotAddedException("Any One Equipment Length value should be provided!");
		}

		//Either RESTRICT_ALL or ANY size
		if(allocationObj.getEmsEquipmentLengthRestrictions().size() > 1 && allocationObj.getEmsEquipmentLengthRestrictions().contains(EMSEquipmentLengthRestriction.RESTRICT_ALL)){
			throw new RecordNotAddedException("EMS Length Restrictions field requires either 'RESTRICT_ALL' or Size");
		}
		
    }

	@Override
	public Page<EMSIngateAllocation> searchIngateAllocation(Long ingateTerminalId, Station onlineOriginStation,
															Station onlineDestinationStation, Station offlineDestinationStation, Long corporateCustomerId,
															List<String> lineOfBusinesses, String wayBillRoute, List<String> trafficTypes, LocalDate st_date, LocalDate end_date,
															LocalDate currentDate, Boolean includeInactive, Boolean includeExpired, Boolean includePermanent, Pageable pageable) {
		String domestic = null, international = null, premium = null;
		String local = null, steelWheel = null, rubberTire = null;
		String active = null, temporaryIndicator = null;
		
		if (trafficTypes != null) {
			if (trafficTypes.contains("LOCAL"))
				local = "T";
			if (trafficTypes.contains("STEEL_WHEEL"))
				steelWheel = "T";
			if (trafficTypes.contains("RUBBER_TIRE"))
				rubberTire = "T";
		}
		if (lineOfBusinesses != null) {
			if (lineOfBusinesses.contains("DOMESTIC"))
				domestic = "T";
			if (lineOfBusinesses.contains("INTERNATIONAL"))
				international = "T";
			if (lineOfBusinesses.contains("PREMIUM"))
				premium = "T";
		}
		if ((includeInactive != null && includeInactive) && (includePermanent != null && includePermanent) && (includeExpired != null && includeExpired)) {
			active = null;
			temporaryIndicator = null;
		}
		else if ((includeInactive != null && includeInactive) && (includePermanent != null && includePermanent)) {
			active = null;
			temporaryIndicator = null;
			currentDate = LocalDate.now();
		}
		else if ((includeInactive != null && includeInactive) && (includeExpired != null && includeExpired)) {
			temporaryIndicator = "T";
		}
		else if ((includePermanent != null && includePermanent) && (includeExpired != null && includeExpired)) {
			active = "T";
		}
		else if (includeInactive != null && includeInactive) {
			temporaryIndicator = "T";
			currentDate = LocalDate.now();
		}
		else if (includePermanent != null && includePermanent) {
			active = "T";
			currentDate = LocalDate.now();
		}
		else if (includeExpired != null && includeExpired) {
			temporaryIndicator = "T";
			active = "T";
		}
		else{
			active = "T";
			temporaryIndicator = "T";
			currentDate = LocalDate.now();
		}

		Page<EMSIngateAllocation> searchIngateAllocationsPage = emsIngateAllocationRepository
				.searchAllIngateAllocations(ingateTerminalId, onlineOriginStation, onlineDestinationStation,
						offlineDestinationStation, corporateCustomerId, domestic, international, premium, active,
						wayBillRoute, local, steelWheel, rubberTire, st_date, end_date, currentDate, temporaryIndicator, pageable);

		if (searchIngateAllocationsPage.getTotalPages() == 0)
			throw new NoRecordsFoundException("No records matches this search");

		if (searchIngateAllocationsPage.getPageable().getPageNumber() >= searchIngateAllocationsPage.getTotalPages())
			throw new SizeExceedException(
					"Page Number must be less than or equal to " + searchIngateAllocationsPage.getTotalPages());
		return searchIngateAllocationsPage;
	}

	@Override
	public EMSIngateAllocation insertAllocation(EMSIngateAllocation allocationObj, Map<String,String> headers) {
		
		UserId.headerUserID(headers);
		String userId = headers.get(CommonConstants.USER_ID);
		String extensionSchema = headers.get(CommonConstants.EXTENSION_SCHEMA);
		
		Long generatedRestrictId = emsIngateAllocationRepository.SGK();
		allocationObj.setTimsId(generatedRestrictId);
		
		if(allocationObj.getOnlineOriginStation() != null) {
			Station onlineOriginStation = stationRepository.findById(allocationObj.getOnlineOriginStation().getTermId()).get();
			allocationObj.setOnlineOriginStation(onlineOriginStation);
		}
		
		if(allocationObj.getOnlineDestinationStation() != null) {
			Station onlineDestinationStation = stationRepository.findById(allocationObj.getOnlineDestinationStation().getTermId()).get();
			allocationObj.setOnlineDestinationStation(onlineDestinationStation);
		}
		
		if(allocationObj.getOfflineDestinationStation() != null) {
			Station offlineDestinationStation = stationRepository.findById(allocationObj.getOfflineDestinationStation().getTermId()).get();
			allocationObj.setOfflineDestinationStation(offlineDestinationStation);
		}

		if(extensionSchema != null) {
			extensionSchema = extensionSchema.toUpperCase();
		} else {
			throw new NullPointerException("Extension Schema should not be null, empty or blank");
		}
		
		emsAllocationValidations(allocationObj);

		// NumberIngated value is set to '0' by default as a user cannot enter its value.
		allocationObj.setNumberIngated(0);

		//AuditInfo Required Fields
		allocationObj.setCreateUserId(userId.toUpperCase());
		allocationObj.setUpdateUserId(userId.toUpperCase());
		allocationObj.setCreateExtensionSchema(extensionSchema);
		allocationObj.setUpdateExtensionSchema(extensionSchema);
		allocationObj.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
		allocationObj.setUversion("!");
		
		if(allocationObj.getIngateTerminalId() != null && allocationObj.getStartTime() != null
				&& allocationObj.getEndTime() != null && !(allocationObj.getEmsEquipmentLengthRestrictions().isEmpty())
				&& !(allocationObj.getEquipmentTypes().isEmpty())) {
			emsIngateAllocationRepository.save(allocationObj);
		} else {
			throw new RecordNotAddedException("'Ingate Terminal, Start Date, End Date, Equipment Type & Equipment Length' "
					+ "values should be present!");
		}

		EMSIngateAllocation allocation = emsIngateAllocationRepository.findByTimsId(allocationObj.getTimsId());
		return allocation;
		
	}

	@Override
	public EMSIngateAllocation updateAllocation(EMSIngateAllocation allocations, Map<String,String> headers) {

		UserId.headerUserID(headers);
		String userId = headers.get(CommonConstants.USER_ID);
		String extensionSchema = headers.get(CommonConstants.EXTENSION_SCHEMA);
		Timestamp currentDateTime = new Timestamp(System.currentTimeMillis());
		
		//Check TimsId is not null
		if (allocations.getTimsId() == null) {
			throw new NoRecordsFoundException("Tims Id is mandatory field");
		}
		
		//Check TimsId is available or not
		if (!emsIngateAllocationRepository.existsByTimsId(allocations.getTimsId())) {
			throw new NoRecordsFoundException("Record Not Found!");
		}
		
		if(allocations.getOnlineOriginStation() != null) {
			Station onlineOriginStation = stationRepository.findById(allocations.getOnlineOriginStation().getTermId()).get();
			allocations.setOnlineOriginStation(onlineOriginStation);
		}
		
		if(allocations.getOnlineDestinationStation() != null) {
			Station onlineDestinationStation = stationRepository.findById(allocations.getOnlineDestinationStation().getTermId()).get();
			allocations.setOnlineDestinationStation(onlineDestinationStation);
		}
		
		if(allocations.getOfflineDestinationStation() != null) {
			Station offlineDestinationStation = stationRepository.findById(allocations.getOfflineDestinationStation().getTermId()).get();
			allocations.setOfflineDestinationStation(offlineDestinationStation);
		}
		
		//Validating each fields
		emsAllocationValidations(allocations);
		
		//Create Date is throwing an error, So setting the Date from repository
		EMSIngateAllocation emsIngateAllocation = emsIngateAllocationRepository.findByTimsId(allocations.getTimsId());

		if(emsIngateAllocation.getCreateExtensionSchema() == null) {		
			allocations.setCreateExtensionSchema(extensionSchema);		
		}
		allocations.setUpdateDateTime(currentDateTime);
		allocations.setUpdateUserId(userId.toUpperCase());
		emsIngateAllocation.setUversion(allocations.getUversion());
		allocations.setUpdateExtensionSchema(extensionSchema);
		if(emsIngateAllocation.getCreateUserId() == null) {
			allocations.setCreateUserId(userId.toUpperCase());
		}

		
		//Check all required fields are there
		if(allocations.getIngateTerminalId() != null && allocations.getStartTime() != null
				&& allocations.getEndTime() != null && !(allocations.getEmsEquipmentLengthRestrictions().isEmpty())
				&& !(allocations.getEquipmentTypes().isEmpty())) {
			emsIngateAllocationRepository.save(allocations);
		}else {
			throw new RecordNotAddedException("'Ingate Terminal, Start Date, End Date, Equipment Type & Equipment Length' "
					+ "values should be present!");
		}

		return emsIngateAllocation;
	}
}