package com.nscorp.obis.domain;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "TEMP_METERING")
public class EMSIngateAllocation extends BaseEMSIngateRestriction {

	@Id
	@Column(name = "TIMS_ID", length = 19, columnDefinition = "double", nullable = false)
	private Long timsId;

	@Transient
	private EMSAllotmentType allotmentType;

	@Column(name = "INGT_ALLOW", columnDefinition = "Integer", nullable = true)
	private Integer totalIngatesAllowed;

	@Column(name = "NR_INGT", columnDefinition = "Integer", nullable = true)
	private Integer numberIngated;

	@Column(name = "ALLOTMENT_TYPE", columnDefinition = "char(1)", nullable = true)
	private String allotmentTypeCode;

	public EMSIngateAllocation() {
	}

//	public EMSIngateAllocation(Long timsId, Long ingateTerminalId,
//							   String equipmentInit, Integer equipmentLowestNumber, Integer equipmentHighestNumber,Station onlineOriginStation, 
//								Station onlineDestinationStation, Station offlineDestinationStation,
//							   Long corporateCustomerId, String loadEmptyCode, Integer equipmentLength, Integer grossWeight,
//							   String domestic, String international, String premium, String container, String trailer, String chassis,
//							   String wayBillRoute, Boolean hazardousIndicator, WeightQualifier weightQualifier, String active,
//							   Object startDate, Object startTime, Object endDate, Object endTime, String createExtensionSchema,
//							   String sunday, String monday, String tuesday, String wednesday, String thursday, String friday,
//							   String saturday, String local, String steelWheel, String rubberTire, Boolean reeferIndicator,
//							   String temporaryIndicator, String twentyFeet, String fortyFeet, String fortyFiveFeet, String fiftyThreeFeet,
//							   String allLengths, String allotmentTypeCode, Integer totalIngatesAllowed, Integer numberIngated) {
//		super(ingateTerminalId,  onlineOriginStation, onlineDestinationStation, offlineDestinationStation, equipmentInit, equipmentLowestNumber, equipmentHighestNumber,
//				corporateCustomerId, loadEmptyCode, equipmentLength, grossWeight, domestic, international, premium,
//				container, trailer, chassis, wayBillRoute, hazardousIndicator, weightQualifier, active, startDate,
//				startTime, endDate, endTime, createExtensionSchema, sunday, monday, tuesday, wednesday, thursday,
//				friday, saturday, local, steelWheel, rubberTire, reeferIndicator, temporaryIndicator, twentyFeet,
//				fortyFeet, fortyFiveFeet, fiftyThreeFeet, allLengths);
//		this.timsId = timsId;
//		this.allotmentTypeCode = allotmentTypeCode;
//		this.totalIngatesAllowed = totalIngatesAllowed;
//		this.numberIngated = numberIngated;
//	}

		public EMSIngateAllocation(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Long ingateTerminalId, Terminal ingateTerminal,
			Station onlineOriginStation, Station onlineDestinationStation, Station offlineDestinationStation,
			String equipmentInit, Integer equipmentLowestNumber, Integer equipmentHighestNumber, Long corporateCustomerId,
			CorporateCustomer corporateCustomer, String loadEmptyCode, Integer equipmentLength, Integer grossWeight,
			String domestic, String international, String premium, List<LineOfBusiness> lineOfBusinesses, String container,
			String trailer, String chassis, List<EquipmentType> equipmentTypes, String wayBillRoute,
			Boolean hazardousIndicator, WeightQualifier weightQualifier, String active, LocalDate startDate,
			LocalTime startTime, LocalDate endDate, LocalTime endTime, String createExtensionSchema, String sunday,
			String monday, String tuesday, String wednesday, String thursday, String friday, String saturday,
			List<DayOfWeek> restrictedDays, String local, String steelWheel, String rubberTire,
			List<TrafficType> trafficTypes, Boolean reeferIndicator, String temporaryIndicator, String twentyFeet,
			String fortyFeet, String fortyFiveFeet, String fiftyThreeFeet, String allLengths,
			List<EMSEquipmentLengthRestriction> emsEquipmentLengthRestrictions, Long timsId, EMSAllotmentType allotmentType,
			Integer totalIngatesAllowed, Integer numberIngated, String allotmentTypeCode) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema, ingateTerminalId,
				ingateTerminal, onlineOriginStation, onlineDestinationStation, offlineDestinationStation, equipmentInit,
				equipmentLowestNumber, equipmentHighestNumber, corporateCustomerId, corporateCustomer, loadEmptyCode,
				equipmentLength, grossWeight, domestic, international, premium, lineOfBusinesses, container, trailer,
				chassis, equipmentTypes, wayBillRoute, hazardousIndicator, weightQualifier, active, startDate, startTime,
				endDate, endTime, createExtensionSchema, sunday, monday, tuesday, wednesday, thursday, friday, saturday,
				restrictedDays, local, steelWheel, rubberTire, trafficTypes, reeferIndicator, temporaryIndicator,
				twentyFeet, fortyFeet, fortyFiveFeet, fiftyThreeFeet, allLengths, emsEquipmentLengthRestrictions);
		this.timsId = timsId;
		this.allotmentType = allotmentType;
		this.totalIngatesAllowed = totalIngatesAllowed;
		this.numberIngated = numberIngated;
		this.allotmentTypeCode = allotmentTypeCode;
	}
	
	public Long getTimsId() {
		return timsId;
	}

	public void setTimsId(Long timsId) {
		this.timsId = timsId;
	}

	public Integer getTotalIngatesAllowed() {
		return totalIngatesAllowed;
	}

	public void setTotalIngatesAllowed(Integer totalIngatesAllowed) {
		this.totalIngatesAllowed = totalIngatesAllowed;
	}

	public Integer getNumberIngated() {
		return numberIngated;
	}

	public void setNumberIngated(Integer numberIngated) {
		this.numberIngated = numberIngated;
	}

	public String getAllotmentTypeCode() {
		return allotmentTypeCode;
	}

	public void setAllotmentTypeCode(String allotmentTypeCode) {
		this.allotmentTypeCode = allotmentTypeCode;
	}

	public EMSAllotmentType getAllotmentType() {
		if ("D".equalsIgnoreCase(this.allotmentTypeCode))
			this.allotmentType = EMSAllotmentType.DAILY;
		if ("F".equalsIgnoreCase(this.allotmentTypeCode))
			this.allotmentType = EMSAllotmentType.FIXED;
		return allotmentType;
	}

	public void setAllotmentType(EMSAllotmentType allotmentType) {
		if(allotmentType.equals(EMSAllotmentType.DAILY))
			this.allotmentTypeCode = "D";
		if(allotmentType.equals(EMSAllotmentType.FIXED))
			this.allotmentTypeCode = "F";
	}

}