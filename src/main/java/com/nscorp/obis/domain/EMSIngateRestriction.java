package com.nscorp.obis.domain;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TEMP_RESTRICT")
public class EMSIngateRestriction extends BaseEMSIngateRestriction {

	@Id
	@Column(name = "RESTRICT_ID", length = 15, columnDefinition = "double", nullable = false)
	public Long restrictId;

	@Column(name = "PRIMARY_LOB", columnDefinition = "char(1)", nullable = true)
	private String primaryLineOfBusiness;

	@Column(name = "EQ_TP", columnDefinition = "char(1)", nullable = true)
	private String equipmentType;

	public String getPrimaryLineOfBusiness() {
		if (primaryLineOfBusiness != null) {
			return primaryLineOfBusiness.trim();
		} else {
			return primaryLineOfBusiness;
		}
	}

	public void setPrimaryLineOfBusiness(String primaryLineOfBusiness) {
		if (primaryLineOfBusiness != null) {
			this.primaryLineOfBusiness = primaryLineOfBusiness.toUpperCase();
		} else {
			this.primaryLineOfBusiness = primaryLineOfBusiness;
		}
	}

	public String getEquipmentType() {
		if (equipmentType != null) {
			return equipmentType.trim();
		} else {
			return equipmentType;
		}
	}

	public void setEquipmentType(String equipmentType) {
		if (equipmentType != null) {
			this.equipmentType = equipmentType.toUpperCase();
		} else {
			this.equipmentType = equipmentType;
		}
	}

	public Long getRestrictId() {
		return restrictId;
	}

	public void setRestrictId(Long restrictId) {
		this.restrictId = restrictId;
	}

	public EMSIngateRestriction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EMSIngateRestriction(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Long ingateTerminalId, Terminal ingateTerminal,
			Station onlineOriginStation, Station onlineDestinationStation, Station offlineDestinationStation,
			String equipmentInit, Integer equipmentLowestNumber, Integer equipmentHighestNumber,
			Long corporateCustomerId, CorporateCustomer corporateCustomer, String loadEmptyCode,
			Integer equipmentLength, Integer grossWeight, String domestic, String international, String premium,
			List<LineOfBusiness> lineOfBusinesses, String container, String trailer, String chassis,
			List<EquipmentType> equipmentTypes, String wayBillRoute, Boolean hazardousIndicator,
			WeightQualifier weightQualifier, String active, LocalDate startDate, LocalTime startTime, LocalDate endDate,
			LocalTime endTime, String createExtensionSchema, String sunday, String monday, String tuesday,
			String wednesday, String thursday, String friday, String saturday, List<DayOfWeek> restrictedDays,
			String local, String steelWheel, String rubberTire, List<TrafficType> trafficTypes, Boolean reeferIndicator,
			String temporaryIndicator, String twentyFeet, String fortyFeet, String fortyFiveFeet, String fiftyThreeFeet,
			String allLengths, List<EMSEquipmentLengthRestriction> emsEquipmentLengthRestrictions, Long restrictId,
			String primaryLineOfBusiness, String equipmentType) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema,
				ingateTerminalId, ingateTerminal, onlineOriginStation, onlineDestinationStation,
				offlineDestinationStation, equipmentInit, equipmentLowestNumber, equipmentHighestNumber,
				corporateCustomerId, corporateCustomer, loadEmptyCode, equipmentLength, grossWeight, domestic,
				international, premium, lineOfBusinesses, container, trailer, chassis, equipmentTypes, wayBillRoute,
				hazardousIndicator, weightQualifier, active, startDate, startTime, endDate, endTime,
				createExtensionSchema, sunday, monday, tuesday, wednesday, thursday, friday, saturday, restrictedDays,
				local, steelWheel, rubberTire, trafficTypes, reeferIndicator, temporaryIndicator, twentyFeet, fortyFeet,
				fortyFiveFeet, fiftyThreeFeet, allLengths, emsEquipmentLengthRestrictions);
		this.restrictId = restrictId;
		this.primaryLineOfBusiness = primaryLineOfBusiness;
		this.equipmentType = equipmentType;
	}

	@Override
	public String toString() {
		return "EMSIngateRestriction [restrictId=" + restrictId + ", primaryLineOfBusiness=" + primaryLineOfBusiness
				+ ", equipmentType=" + equipmentType + ", getPrimaryLineOfBusiness()=" + getPrimaryLineOfBusiness()
				+ ", getEquipmentType()=" + getEquipmentType() + ", getRestrictId()=" + getRestrictId()
				+ ", getTwentyFeet()=" + getTwentyFeet() + ", getFortyFeet()=" + getFortyFeet()
				+ ", getFortyFiveFeet()=" + getFortyFiveFeet() + ", getFiftyThreeFeet()=" + getFiftyThreeFeet()
				+ ", getAllLengths()=" + getAllLengths() + ", getWeightQualifier()=" + getWeightQualifier()
				+ ", getIngateTerminalId()=" + getIngateTerminalId() + ", getOnlineOriginStation()="
				+ getOnlineOriginStation() + ", getOnlineDestinationStation()=" + getOnlineDestinationStation()
				+ ", getOfflineDestinationStation()=" + getOfflineDestinationStation() + ", getCorporateCustomerId()="
				+ getCorporateCustomerId() + ", getLineOfBusinesses()=" + getLineOfBusinesses()
				+ ", getEquipmentInit()=" + getEquipmentInit() + ", getEquipmentLowestNumber()="
				+ getEquipmentLowestNumber() + ", getEquipmentHighestNumber()=" + getEquipmentHighestNumber()
				+ ", getLoadEmptyCode()=" + getLoadEmptyCode() + ", getEquipmentLength()=" + getEquipmentLength()
				+ ", getGrossWeight()=" + getGrossWeight() + ", getWayBillRoute()=" + getWayBillRoute()
				+ ", getTrafficTypes()=" + getTrafficTypes() + ", getHazardousIndicator()=" + getHazardousIndicator()
				+ ", getActive()=" + getActive() + ", getStartDate()=" + getStartDate() + ", getStartTime()="
				+ getStartTime() + ", getEndDate()=" + getEndDate() + ", getEndTime()=" + getEndTime()
				+ ", getCreateExtensionSchema()=" + getCreateExtensionSchema() + ", getReeferIndicator()="
				+ getReeferIndicator() + ", getTemporaryIndicator()=" + getTemporaryIndicator()
				+ ", getEquipmentTypes()=" + getEquipmentTypes() + ", getRestrictedDays()=" + getRestrictedDays()
				+ ", getEmsEquipmentLengthRestrictions()=" + getEmsEquipmentLengthRestrictions() + ", getUversion()="
				+ getUversion() + ", getCreateUserId()=" + getCreateUserId() + ", getCreateDateTime()="
				+ getCreateDateTime() + ", getUpdateUserId()=" + getUpdateUserId() + ", getUpdateDateTime()="
				+ getUpdateDateTime() + ", getUpdateExtensionSchema()=" + getUpdateExtensionSchema() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

	
}