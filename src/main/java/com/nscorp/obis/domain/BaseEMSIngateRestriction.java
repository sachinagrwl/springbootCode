package com.nscorp.obis.domain;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public abstract class BaseEMSIngateRestriction extends AuditInfo {

	@Column(name = "INGT_TERM", length = 15, columnDefinition = "double", nullable = false)
	private Long ingateTerminalId;

	@ManyToOne
	@JoinColumn(name = "INGT_TERM", referencedColumnName = "TERM_ID", insertable = false, updatable = false)
	@JsonIgnore
	private Terminal ingateTerminal;

	@ManyToOne
	@JoinColumn(name = "ONL_ORIG", referencedColumnName = "TERM_ID")
    private Station onlineOriginStation;

	@ManyToOne
	@JoinColumn(name = "ONL_DEST", referencedColumnName = "TERM_ID")
	private Station onlineDestinationStation;

	@ManyToOne
	@JoinColumn(name = "OFFL_DEST", referencedColumnName = "TERM_ID")
	private Station offlineDestinationStation;

	@Column(name = "EQ_INIT", columnDefinition = "char(4)", nullable = true)
	private String equipmentInit;

	@Column(name = "EQ_LOW_NR", length = 10, columnDefinition = "Integer", nullable = true)
	private Integer equipmentLowestNumber;

	@Column(name = "EQ_HIGH_NR", length = 10, columnDefinition = "Integer", nullable = true)
	private Integer equipmentHighestNumber;

	@Column(name = "CORP_CUST_ID", length = 15, columnDefinition = "double", nullable = true)
	private Long corporateCustomerId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CORP_CUST_ID", referencedColumnName = "CORP_CUST_ID", insertable = false, updatable = false)
	private CorporateCustomer corporateCustomer;

	@Column(name = "LOAD_EMPTY_CD", columnDefinition = "char(1)", nullable = true)
	private String loadEmptyCode;

	@Column(name = "EQ_LGTH", length = 10, columnDefinition = "Integer", nullable = true)
	private Integer equipmentLength;

	@Column(name = "GROSS_WEIGHT", length = 10, columnDefinition = "Integer", nullable = true)
	private Integer grossWeight;

	@Column(name = "DOMESTIC", columnDefinition = "char(1)", nullable = true)
	private String domestic = "F";

	@Column(name = "INTERNATIONAL", columnDefinition = "char(1)", nullable = true)
	private String international = "F";

	@Column(name = "PREMIUM", columnDefinition = "char(1)", nullable = true)
	private String premium = "F";

	@Transient
	private List<LineOfBusiness> lineOfBusinesses;

	@Column(name = "CONTAINER", columnDefinition = "char(1)", nullable = true)
	private String container = "F";

	@Column(name = "TRAILER", columnDefinition = "char(1)", nullable = true)
	private String trailer = "F";

	@Column(name = "CHASSIS", columnDefinition = "char(1)", nullable = true)
	private String chassis = "F";

	@Transient
	private List<EquipmentType> equipmentTypes;

	@Column(name = "WB_ROUTE", columnDefinition = "char(110)", nullable = true)
	private String wayBillRoute;

	@Type(type = "yes_no")
	@Column(name = "HAZ_IND", columnDefinition = "char(1)", nullable = true)
	private Boolean hazardousIndicator;

	@Enumerated(EnumType.STRING)
	@Column(name = "WT_QUAL", columnDefinition = "char(2)", nullable = true)
	private WeightQualifier weightQualifier;

	@Column(name = "ACTIVE", columnDefinition = "char(1)", nullable = true)
	private String active;

	@Column(name = "ST_DATE", columnDefinition = "DATE(10)", nullable = true)
	private LocalDate startDate;

	@Column(name = "ST_TIME", columnDefinition = "TIME(8)", nullable = true)
	private LocalTime startTime;

	@Column(name = "END_DATE", columnDefinition = "DATE(10)", nullable = true)
	private LocalDate endDate;

	@Column(name = "END_TIME", columnDefinition = "TIME(8)", nullable = true)
	private LocalTime endTime;

	@Column(name = "CREATE_EXTN_SCHEMA", columnDefinition = "char(32)", nullable = true)
	private String createExtensionSchema;

	@Column(name = "SUNDAY", columnDefinition = "char(1)", nullable = true)
	private String sunday = "F";

	@Column(name = "MONDAY", columnDefinition = "char(1)", nullable = true)
	private String monday = "F";

	@Column(name = "TUESDAY", columnDefinition = "char(1)", nullable = true)
	private String tuesday = "F";

	@Column(name = "WEDNESDAY", columnDefinition = "char(1)", nullable = true)
	private String wednesday = "F";

	@Column(name = "THURSDAY", columnDefinition = "char(1)", nullable = true)
	private String thursday = "F";

	@Column(name = "FRIDAY", columnDefinition = "char(1)", nullable = true)
	private String friday = "F";

	@Column(name = "SATURDAY", columnDefinition = "char(1)", nullable = true)
	private String saturday = "F";

	@Transient
	private List<DayOfWeek> restrictedDays;

	@Column(name = "LOCAL", columnDefinition = "char(1)", nullable = true)
	private String local = "F";

	@Column(name = "STEELWHEEL", columnDefinition = "char(1)", nullable = true)
	private String steelWheel = "F";

	@Column(name = "RUBBERTIRE", columnDefinition = "char(1)", nullable = true)
	private String rubberTire = "F";

	@Transient
	private List<TrafficType> trafficTypes;

	@Type(type = "yes_no")
	@Column(name = "IS_REEFER", columnDefinition = "char(1)", nullable = true)
	private Boolean reeferIndicator;

	@Column(name = "TEMP_IND", columnDefinition = "char(1)", nullable = true)
	private String temporaryIndicator;

	@Column(name = "TWENTY_FT", columnDefinition = "char(1)", nullable = true)
	private String twentyFeet = "F";

	@Column(name = "FORTY_FT", columnDefinition = "char(1)", nullable = true)
	private String fortyFeet = "F";

	@Column(name = "FORTYFIVE_FT", columnDefinition = "char(1)", nullable = true)
	private String fortyFiveFeet = "F";

	@Column(name = "FIFTYTHREE_FT", columnDefinition = "char(1)", nullable = true)
	private String fiftyThreeFeet = "F";

	@Column(name = "ALL_LGTHS", columnDefinition = "char(1)", nullable = true)
	private String allLengths = "F";

//	/* Audit Fields */
//	@Column(name = "U_VERSION", columnDefinition = "char(1)", nullable = true)
//	private String uversion;
//
//	@Column(name = "CREATE_USER_ID", columnDefinition = "char(8)", nullable = true)
//	private String createUserId;
//
//	@CreationTimestamp
//	@Column(name = "CREATE_DT_TM", nullable = true)
//	private Timestamp createDateTime;
//
//	@Column(name = "UPD_USER_ID", columnDefinition = "char(8)", nullable = true)
//	private String updateUserId;
//
//	@UpdateTimestamp
//	@Column(name = "UPD_DT_TM", nullable = true)
//	private Timestamp updateDateTime;
//
//	@Column(name = "UPD_EXTN_SCHEMA", columnDefinition = "char(16)", nullable = true)
//	private String updateExtensionSchema;

	@Transient
	private List<EMSEquipmentLengthRestriction> emsEquipmentLengthRestrictions;

	public BaseEMSIngateRestriction() {
	}

//	public BaseEMSIngateRestriction(Terminal ingateTerminal,
//			Station onlineOriginStation, Station onlineDestinationStation, Station offlineDestinationStation,
//			String equipmentInit, Integer equipmentLowestNumber, Integer equipmentHighestNumber,
//			CorporateCustomer corporateCustomer, String container, String trailer, String chassis,
//			List<EquipmentType> equipmentTypes, String loadEmptyCode, Integer equipmentLength, Integer grossWeight,
//			String wayBillRoute, Boolean hazardousIndicator, String active, LocalDate startDate, LocalTime startTime,
//			LocalDate endDate, LocalTime endTime, String createExtensionSchema, Boolean reeferIndicator,
//			String temporaryIndicator, String allLengths) {
//		this.ingateTerminal = ingateTerminal;
//		this.onlineOriginStation = onlineOriginStation;
//		this.onlineDestinationStation = onlineDestinationStation;
//		this.offlineDestinationStation = offlineDestinationStation;
//		this.equipmentInit = equipmentInit;
//		this.equipmentLowestNumber = equipmentLowestNumber;
//		this.equipmentHighestNumber = equipmentHighestNumber;
//		this.corporateCustomer = corporateCustomer;
//		this.container = container;
//		this.trailer = trailer;
//		this.chassis = chassis;
//		this.equipmentTypes = equipmentTypes;
//		this.loadEmptyCode = loadEmptyCode;
//		this.equipmentLength = equipmentLength;
//		this.grossWeight = grossWeight;
//		this.wayBillRoute = wayBillRoute;
//		this.hazardousIndicator = hazardousIndicator;
//		this.active = active;
//		this.startDate = startDate;
//		this.startTime = startTime;
//		this.endDate = endDate;
//		this.endTime = endTime;
//		this.createExtensionSchema = createExtensionSchema;
//		this.reeferIndicator = reeferIndicator;
//		this.temporaryIndicator = temporaryIndicator;
//		this.allLengths = allLengths;
//		this.uversion = uversion;
//		this.createUserId = createUserId;
//		this.createDateTime = createDateTime;
//		this.updateUserId = updateUserId;
//		this.updateDateTime = updateDateTime;
//		this.updateExtensionSchema = updateExtensionSchema;
//	}
	
	

//	public BaseEMSIngateRestriction(Long ingateTerminalId, String equipmentInit,
//			Integer equipmentLowestNumber, Integer equipmentHighestNumber,Station onlineOriginStation, 
//			Station onlineDestinationStation, Station offlineDestinationStation, Long corporateCustomerId,
//			String loadEmptyCode, Integer equipmentLength, Integer grossWeight, String domestic, String international,
//			String premium, String container, String trailer, String chassis, String wayBillRoute,
//			Boolean hazardousIndicator, WeightQualifier weightQualifier, String active, Object startDate,
//			Object startTime, Object endDate, Object endTime, String createExtensionSchema, String sunday,
//			String monday, String tuesday, String wednesday, String thursday, String friday, String saturday,
//			String local, String steelWheel, String rubberTire, Boolean reeferIndicator, String temporaryIndicator,
//			String twentyFeet, String fortyFeet, String fortyFiveFeet, String fiftyThreeFeet, String allLengths) {
//		this.ingateTerminalId = ingateTerminalId;
////		this.onlineOriginStationTermId = onlineOriginStationTermId;
//		this.onlineOriginStation = onlineOriginStation;
////		this.onlineDestinationStationTermId = onlineDestinationStationTermId;
//		this.onlineDestinationStation = onlineDestinationStation;
////		this.offlineDestinationStationTermId = offlineDestinationStationTermId;
//		this.offlineDestinationStation = offlineDestinationStation;
//		this.equipmentInit = equipmentInit;
//		this.equipmentLowestNumber = equipmentLowestNumber;
//		this.equipmentHighestNumber = equipmentHighestNumber;
//		this.corporateCustomerId = corporateCustomerId;
//		this.loadEmptyCode = loadEmptyCode;
//		this.equipmentLength = equipmentLength;
//		this.grossWeight = grossWeight;
//		this.domestic = domestic;
//		this.international = international;
//		this.premium = premium;
//		this.container = container;
//		this.trailer = trailer;
//		this.chassis = chassis;
//		this.wayBillRoute = wayBillRoute;
//		this.hazardousIndicator = hazardousIndicator;
//		this.weightQualifier = weightQualifier;
//		this.active = active;
//		this.startDate = (LocalDate) startDate;
//		this.startTime = (LocalTime) startTime;
//		this.endDate = (LocalDate) endDate;
//		this.endTime = (LocalTime) endTime;
//		this.createExtensionSchema = createExtensionSchema;
//		this.sunday = sunday;
//		this.monday = monday;
//		this.tuesday = tuesday;
//		this.wednesday = wednesday;
//		this.thursday = thursday;
//		this.friday = friday;
//		this.saturday = saturday;
//		this.local = local;
//		this.steelWheel = steelWheel;
//		this.rubberTire = rubberTire;
//		this.reeferIndicator = reeferIndicator;
//		this.twentyFeet = twentyFeet;
//		this.fortyFeet = fortyFeet;
//		this.fortyFiveFeet = fortyFiveFeet;
//		this.fiftyThreeFeet = fiftyThreeFeet;
//		this.allLengths = allLengths;
//		this.temporaryIndicator = temporaryIndicator;
//		this.uversion = uversion;
//		this.createUserId = createUserId;
//		this.createDateTime = (Timestamp) createDateTime;
//		this.updateUserId = updateUserId;
//		this.updateDateTime = (Timestamp) updateDateTime;
//		this.updateExtensionSchema = updateExtensionSchema;
//	}
	
	public BaseEMSIngateRestriction(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
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
		List<EMSEquipmentLengthRestriction> emsEquipmentLengthRestrictions) {
	super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
	this.ingateTerminalId = ingateTerminalId;
	this.ingateTerminal = ingateTerminal;
	this.onlineOriginStation = onlineOriginStation;
	this.onlineDestinationStation = onlineDestinationStation;
	this.offlineDestinationStation = offlineDestinationStation;
	this.equipmentInit = equipmentInit;
	this.equipmentLowestNumber = equipmentLowestNumber;
	this.equipmentHighestNumber = equipmentHighestNumber;
	this.corporateCustomerId = corporateCustomerId;
	this.corporateCustomer = corporateCustomer;
	this.loadEmptyCode = loadEmptyCode;
	this.equipmentLength = equipmentLength;
	this.grossWeight = grossWeight;
	this.domestic = domestic;
	this.international = international;
	this.premium = premium;
	this.lineOfBusinesses = lineOfBusinesses;
	this.container = container;
	this.trailer = trailer;
	this.chassis = chassis;
	this.equipmentTypes = equipmentTypes;
	this.wayBillRoute = wayBillRoute;
	this.hazardousIndicator = hazardousIndicator;
	this.weightQualifier = weightQualifier;
	this.active = active;
	this.startDate = startDate;
	this.startTime = startTime;
	this.endDate = endDate;
	this.endTime = endTime;
	this.createExtensionSchema = createExtensionSchema;
	this.sunday = sunday;
	this.monday = monday;
	this.tuesday = tuesday;
	this.wednesday = wednesday;
	this.thursday = thursday;
	this.friday = friday;
	this.saturday = saturday;
	this.restrictedDays = restrictedDays;
	this.local = local;
	this.steelWheel = steelWheel;
	this.rubberTire = rubberTire;
	this.trafficTypes = trafficTypes;
	this.reeferIndicator = reeferIndicator;
	this.temporaryIndicator = temporaryIndicator;
	this.twentyFeet = twentyFeet;
	this.fortyFeet = fortyFeet;
	this.fortyFiveFeet = fortyFiveFeet;
	this.fiftyThreeFeet = fiftyThreeFeet;
	this.allLengths = allLengths;
	this.emsEquipmentLengthRestrictions = emsEquipmentLengthRestrictions;
}

	public String getTwentyFeet() {
		return twentyFeet;
	}

	public void setTwentyFeet(String twentyFeet) {
		this.twentyFeet = twentyFeet;
	}

	public String getFortyFeet() {
		return fortyFeet;
	}

	public void setFortyFeet(String fortyFeet) {
		this.fortyFeet = fortyFeet;
	}

	public String getFortyFiveFeet() {
		return fortyFiveFeet;
	}

	public void setFortyFiveFeet(String fortyFiveFeet) {
		this.fortyFiveFeet = fortyFiveFeet;
	}

	public String getFiftyThreeFeet() {
		return fiftyThreeFeet;
	}

	public void setFiftyThreeFeet(String fiftyThreeFeet) {
		this.fiftyThreeFeet = fiftyThreeFeet;
	}

	public String getAllLengths() {
		return allLengths;
	}

	public void setAllLengths(String allLengths) {
		this.allLengths = allLengths;
	}

	public WeightQualifier getWeightQualifier() {
		return weightQualifier;
	}

	public void setWeightQualifier(WeightQualifier weightQualifier) {
		this.weightQualifier = weightQualifier;
	}

	/* To retrieve TermId from Terminal Table */
	public Long getIngateTerminalId() {
		return ingateTerminalId;
	}

	public void setIngateTerminalId(Long ingateTerminalId) {
		this.ingateTerminalId = ingateTerminalId;
	}

	public Station getOnlineOriginStation() {
		if (onlineOriginStation != null) {
//			Long stnId = onlineOriginStation.getTermId();
//			String stName = onlineOriginStation.getStationName();
//			String roadName = onlineOriginStation.getRoadName();
//			onlineOriginStation = new Station();
//			onlineOriginStation.setTermId(stnId);
//			onlineOriginStation.setRoadName(roadName);
//			onlineOriginStation.setStationName(stName);
			return onlineOriginStation;
		} else {
			return null;
		}
	}
	
	public void setOnlineOriginStation(Station onlineOriginStation) {
		this.onlineOriginStation = onlineOriginStation;
	}

	public Station getOnlineDestinationStation() {
		if (onlineDestinationStation != null) {
//			Long stnId = onlineDestinationStation.getTermId();
//			String stName = onlineDestinationStation.getStationName();
//			String roadName = onlineDestinationStation.getRoadName();
//			onlineDestinationStation = new Station();
//			onlineDestinationStation.setTermId(stnId);
//			onlineDestinationStation.setRoadName(roadName);
//			onlineDestinationStation.setStationName(stName);
			return onlineDestinationStation;
		} else {
			return null;
		}
	}
	
	public void setOnlineDestinationStation(Station onlineDestinationStation) {
		this.onlineDestinationStation = onlineDestinationStation;
	}

	public Station getOfflineDestinationStation() {
		if (offlineDestinationStation != null) {
//			Long stnId = offlineDestinationStation.getTermId();
//			String stName = offlineDestinationStation.getStationName();
//			String roadName = offlineDestinationStation.getRoadName();
//			offlineDestinationStation = new Station();
//			offlineDestinationStation.setTermId(stnId);
//			offlineDestinationStation.setRoadName(roadName);
//			offlineDestinationStation.setStationName(stName);
			return offlineDestinationStation;
		} else {
			return null;
		}
	}
	
	public void setOfflineDestinationStation(Station offlineDestinationStation) {
		this.offlineDestinationStation = offlineDestinationStation;
	}

	public Long getCorporateCustomerId() {
		return corporateCustomerId;
	}

	public void setCorporateCustomerId(Long corporateCustomerId) {
		this.corporateCustomerId = corporateCustomerId;
	}

	public List<LineOfBusiness> getLineOfBusinesses() {
		this.lineOfBusinesses = new ArrayList<LineOfBusiness>();
		if ("T".equalsIgnoreCase(this.domestic))
			this.lineOfBusinesses.add(LineOfBusiness.DOMESTIC);
		if ("T".equalsIgnoreCase(this.international))
			this.lineOfBusinesses.add(LineOfBusiness.INTERNATIONAL);
		if ("T".equalsIgnoreCase(this.premium))
			this.lineOfBusinesses.add(LineOfBusiness.PREMIUM);
		return lineOfBusinesses;
	}

	public void setLineOfBusinesses(List<LineOfBusiness> lineOfBusinesses) {
		this.lineOfBusinesses = lineOfBusinesses;
		if (this.corporateCustomerId != null) {
			this.domestic = null;
			this.international = null;
			this.premium = null;
		} else {
			for (LineOfBusiness lob : lineOfBusinesses) {
				if (LineOfBusiness.DOMESTIC.equals(lob)) {
					this.domestic = "T";
				}
				if (LineOfBusiness.INTERNATIONAL.equals(lob)) {
					this.international = "T";
				}
				if (LineOfBusiness.PREMIUM.equals(lob)) {
					this.premium = "T";
				}
			}
		}
	}

	public String getEquipmentInit() {
		if (equipmentInit != null) {
			return equipmentInit.trim();
		} else {
			return equipmentInit;
		}
	}

	public void setEquipmentInit(String equipmentInit) {
		if (equipmentInit != null) {
			this.equipmentInit = equipmentInit.toUpperCase();
		} else {
			this.equipmentInit = equipmentInit;
		}
	}

	public Integer getEquipmentLowestNumber() {
		return equipmentLowestNumber;
	}

	public void setEquipmentLowestNumber(Integer equipmentLowestNumber) {
		this.equipmentLowestNumber = equipmentLowestNumber;
	}

	public Integer getEquipmentHighestNumber() {
		return equipmentHighestNumber;
	}

	public void setEquipmentHighestNumber(Integer equipmentHighestNumber) {
		this.equipmentHighestNumber = equipmentHighestNumber;
	}

	public String getLoadEmptyCode() {
		if (loadEmptyCode != null) {
			return loadEmptyCode.trim();
		} else {
			return loadEmptyCode;
		}
	}

	public void setLoadEmptyCode(String loadEmptyCode) {
		if (loadEmptyCode != null) {
			this.loadEmptyCode = loadEmptyCode.toUpperCase();
		} else {
			this.loadEmptyCode = loadEmptyCode;
		}
	}

	public Integer getEquipmentLength() {
		return equipmentLength;
	}

	public void setEquipmentLength(Integer equipmentLength) {
		this.equipmentLength = equipmentLength;
	}

	public Integer getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(Integer grossWeight) {
		this.grossWeight = grossWeight;
	}

	public String getWayBillRoute() {
		if (wayBillRoute != null) {
			return wayBillRoute.trim();
		} else {
			return wayBillRoute;
		}
	}

	public void setWayBillRoute(String wayBillRoute) {
		if (wayBillRoute != null) {
			this.wayBillRoute = wayBillRoute.toUpperCase();
		} else {
			this.wayBillRoute = wayBillRoute;
		}
	}

	public List<TrafficType> getTrafficTypes() {
		this.trafficTypes = new ArrayList<TrafficType>();
		if ("T".equalsIgnoreCase(this.local))
			this.trafficTypes.add(TrafficType.LOCAL);
		if ("T".equalsIgnoreCase(this.steelWheel))
			this.trafficTypes.add(TrafficType.STEEL_WHEEL);
		if ("T".equalsIgnoreCase(this.rubberTire))
			this.trafficTypes.add(TrafficType.RUBBER_TIRE);
		return trafficTypes;
	}

	public void setTrafficTypes(List<TrafficType> trafficTypes) {
		this.trafficTypes = trafficTypes;
		if (this.wayBillRoute != null) {
			this.steelWheel = null;
			this.rubberTire = null;
			this.local = null;
		} else {
			for (TrafficType trafficType : trafficTypes) {
				if (TrafficType.STEEL_WHEEL.equals(trafficType)) {
					this.steelWheel = "T";
				}
				if (TrafficType.RUBBER_TIRE.equals(trafficType)) {
					this.rubberTire = "T";
				}
				if (TrafficType.LOCAL.equals(trafficType)) {
					this.local = "T";
				}
			}
		}
	}

	public Boolean getHazardousIndicator() {
		return hazardousIndicator;
	}

	public void setHazardousIndicator(Boolean hazardousIndicator) {
		this.hazardousIndicator = hazardousIndicator;
	}

	public String getActive() {
		if (active != null) {
			return active.trim();
		} else {
			return active;
		}
	}

	public void setActive(String active) {
		if (active != null) {
			this.active = active.toUpperCase();
		} else {
			this.active = active;
		}
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public String getCreateExtensionSchema() {
		if (createExtensionSchema != null) {
			return createExtensionSchema.trim();
		} else {
			return createExtensionSchema;
		}
	}

	public void setCreateExtensionSchema(String createExtensionSchema) {
		if (createExtensionSchema != null) {
			this.createExtensionSchema = createExtensionSchema.toUpperCase();
		} else {
			this.createExtensionSchema = createExtensionSchema;
		}
	}

	public Boolean getReeferIndicator() {
		return reeferIndicator;
	}

	public void setReeferIndicator(Boolean reeferIndicator) {
		this.reeferIndicator = reeferIndicator;
	}

	public String getTemporaryIndicator() {
		if (temporaryIndicator != null) {
			return temporaryIndicator.trim();
		} else {
			return temporaryIndicator;
		}
	}

	public void setTemporaryIndicator(String temporaryIndicator) {
		if (temporaryIndicator != null) {
			this.temporaryIndicator = temporaryIndicator.toUpperCase();
		} else {
			this.temporaryIndicator = temporaryIndicator;
		}
	}

	public List<EquipmentType> getEquipmentTypes() {
		this.equipmentTypes = new ArrayList<EquipmentType>();
		if ("T".equalsIgnoreCase(this.container))
			this.equipmentTypes.add(EquipmentType.CONTAINER);
		if ("T".equalsIgnoreCase(this.chassis))
			this.equipmentTypes.add(EquipmentType.CHASSIS);
		if ("T".equalsIgnoreCase(this.trailer))
			this.equipmentTypes.add(EquipmentType.TRAILER);
		return this.equipmentTypes;
	}

	public void setEquipmentTypes(List<EquipmentType> equipmentTypes) {
		if(!equipmentTypes.isEmpty() && equipmentTypes.get(0) != null) {
			this.equipmentTypes = equipmentTypes;
			for (EquipmentType eqType : equipmentTypes) {
				if ("C".equalsIgnoreCase(eqType.getCode()))
					this.container = "T";
				if ("T".equalsIgnoreCase(eqType.getCode()))
					this.trailer = "T";
				if ("Z".equalsIgnoreCase(eqType.getCode()))
					this.chassis = "T";
			}
		}
	}

	public List<DayOfWeek> getRestrictedDays() {
		this.restrictedDays = new ArrayList<DayOfWeek>();
		if ("T".equalsIgnoreCase(this.sunday))
			this.restrictedDays.add(DayOfWeek.SUN);
		if ("T".equalsIgnoreCase(this.monday))
			this.restrictedDays.add(DayOfWeek.MON);
		if ("T".equalsIgnoreCase(this.tuesday))
			this.restrictedDays.add(DayOfWeek.TUE);
		if ("T".equalsIgnoreCase(this.wednesday))
			this.restrictedDays.add(DayOfWeek.WED);
		if ("T".equalsIgnoreCase(this.thursday))
			this.restrictedDays.add(DayOfWeek.THU);
		if ("T".equalsIgnoreCase(this.friday))
			this.restrictedDays.add(DayOfWeek.FRI);
		if ("T".equalsIgnoreCase(this.saturday))
			this.restrictedDays.add(DayOfWeek.SAT);
		return restrictedDays;
	}

	public void setRestrictedDays(List<DayOfWeek> restrictedDays) {
		this.restrictedDays = restrictedDays;
		for (DayOfWeek day : restrictedDays) {
			if (day.getValue() == 0) {
				this.sunday = "T";
			}
			if (day.getValue() == 1) {
				this.monday = "T";
			}
			if (day.getValue() == 2) {
				this.tuesday = "T";
			}
			if (day.getValue() == 3) {
				this.wednesday = "T";
			}
			if (day.getValue() == 4) {
				this.thursday = "T";
			}
			if (day.getValue() == 5) {
				this.friday = "T";
			}
			if (day.getValue() == 6) {
				this.saturday = "T";
			}
		}
	}

	public List<EMSEquipmentLengthRestriction> getEmsEquipmentLengthRestrictions() {
		this.emsEquipmentLengthRestrictions = new ArrayList<EMSEquipmentLengthRestriction>();
		if ("T".equalsIgnoreCase(this.twentyFeet))
			this.emsEquipmentLengthRestrictions.add(EMSEquipmentLengthRestriction.RESTRICT_20_FT);
		if ("T".equalsIgnoreCase(this.fortyFeet))
			this.emsEquipmentLengthRestrictions.add(EMSEquipmentLengthRestriction.RESTRICT_40_FT);
		if ("T".equalsIgnoreCase(this.fortyFiveFeet))
			this.emsEquipmentLengthRestrictions.add(EMSEquipmentLengthRestriction.RESTRICT_45_FT);
		if ("T".equalsIgnoreCase(this.fiftyThreeFeet))
			this.emsEquipmentLengthRestrictions.add(EMSEquipmentLengthRestriction.RESTRICT_53_FT);
		if ("T".equalsIgnoreCase(this.allLengths))
			this.emsEquipmentLengthRestrictions.add(EMSEquipmentLengthRestriction.RESTRICT_ALL);
		return emsEquipmentLengthRestrictions;
	}

	public void setEmsEquipmentLengthRestrictions(List<EMSEquipmentLengthRestriction> emsEquipmentLengthRestrictions) {
		if(!emsEquipmentLengthRestrictions.isEmpty() && emsEquipmentLengthRestrictions.get(0) != null) {
			this.emsEquipmentLengthRestrictions = emsEquipmentLengthRestrictions;
			for (EMSEquipmentLengthRestriction lengthType : emsEquipmentLengthRestrictions) {
				if (EMSEquipmentLengthRestriction.RESTRICT_ALL.equals(lengthType)) {
					this.allLengths = "T";
				}
				if (EMSEquipmentLengthRestriction.RESTRICT_20_FT.equals(lengthType)) {
					this.twentyFeet = "T";
				}
				if (EMSEquipmentLengthRestriction.RESTRICT_40_FT.equals(lengthType)) {
					this.fortyFeet = "T";
				}
				if (EMSEquipmentLengthRestriction.RESTRICT_45_FT.equals(lengthType)) {
					this.fortyFiveFeet = "T";
				}
				if (EMSEquipmentLengthRestriction.RESTRICT_53_FT.equals(lengthType)) {
					this.fiftyThreeFeet = "T";
				}
			}
		}
	}

//	public String getCreateUserId() {
//		return createUserId;
//	}
//
//	public void setCreateUserId(String createUserId) {
//		this.createUserId = createUserId;
//	}
//
//	public Timestamp getCreateDateTime() {
//		return createDateTime;
//	}
//
//	public void setCreateDateTime(Timestamp createDateTime) {
//		this.createDateTime = createDateTime;
//	}
//
//	public String getUpdateUserId() {
//		return updateUserId;
//	}
//
//	public void setUpdateUserId(String updateUserId) {
//		this.updateUserId = updateUserId;
//	}
//
//	public Timestamp getUpdateDateTime() {
//		return updateDateTime;
//	}
//
//	public void setUpdateDateTime(Timestamp updateDateTime) {
//		this.updateDateTime = updateDateTime;
//	}
//
//	public String getUversion() {
//		return uversion;
//	}
//
//	public void setUversion(String uversion) {
//		this.uversion = uversion;
//	}
//
//	public String getUpdateExtensionSchema() {
//		return updateExtensionSchema;
//	}
//
//	public void setUpdateExtensionSchema(String updateExtensionSchema) {
//		this.updateExtensionSchema = updateExtensionSchema;
//	}

//	public BaseEMSIngateRestriction(Long ingateTerminalId,
//									Station onlineOriginStation, 
//									Station onlineDestinationStation, Station offlineDestinationStation,
//									String equipmentInit, Integer equipmentLowestNumber, Integer equipmentHighestNumber,
//									Long corporateCustomerId, String loadEmptyCode, Integer equipmentLength, Integer grossWeight,
//									String domestic, String international, String premium, String container, String trailer, String chassis,
//									String wayBillRoute, Boolean hazardousIndicator, WeightQualifier weightQualifier, String active,
//									Object startDate, Object startTime, Object endDate, Object endTime, String createExtensionSchema,
//									String sunday, String monday, String tuesday, String wednesday, String thursday, String friday,
//									String saturday, String local, String steelWheel, String rubberTire, Boolean reeferIndicator,
//									String temporaryIndicator, String twentyFeet, String fortyFeet, String fortyFiveFeet, String fiftyThreeFeet,
//									String allLengths) {
//		this.ingateTerminalId = ingateTerminalId;
////		this.onlineOriginStationTermId = onlineOriginStationTermId;
//		this.onlineOriginStation = onlineOriginStation;
////		this.onlineDestinationStationTermId = onlineDestinationStationTermId;
//		this.onlineDestinationStation = onlineDestinationStation;
////		this.offlineDestinationStationTermId = offlineDestinationStationTermId;
//		this.offlineDestinationStation = offlineDestinationStation;
//		this.equipmentInit = equipmentInit;
////		this.lineOfBusinesses = lineOfBusiness;
//		this.equipmentLowestNumber = equipmentLowestNumber;
//		this.equipmentHighestNumber = equipmentHighestNumber;
//		this.corporateCustomerId = corporateCustomerId;
//		this.loadEmptyCode = loadEmptyCode;
//		this.equipmentLength = equipmentLength;
//		this.grossWeight = grossWeight;
//		this.domestic = domestic;
//		this.international = international;
//		this.premium = premium;
//		this.container = container;
//		this.trailer = trailer;
//		this.chassis = chassis;
//		this.wayBillRoute = wayBillRoute;
//		this.hazardousIndicator = hazardousIndicator;
//		this.weightQualifier = weightQualifier;
//		this.active = active;
//		this.startDate = (LocalDate) startDate;
//		this.startTime = (LocalTime) startTime;
//		this.endDate = (LocalDate) endDate;
//		this.endTime = (LocalTime) endTime;
//		this.createExtensionSchema = createExtensionSchema;
//		this.sunday = sunday;
//		this.monday = monday;
//		this.tuesday = tuesday;
//		this.wednesday = wednesday;
//		this.thursday = thursday;
//		this.friday = friday;
//		this.saturday = saturday;
//		this.local = local;
//		this.steelWheel = steelWheel;
//		this.rubberTire = rubberTire;
//		this.reeferIndicator = reeferIndicator;
//		this.twentyFeet = twentyFeet;
//		this.fortyFeet = fortyFeet;
//		this.fortyFiveFeet = fortyFiveFeet;
//		this.fiftyThreeFeet = fiftyThreeFeet;
//		this.allLengths = allLengths;
//		this.temporaryIndicator = temporaryIndicator;
////		this.uversion = uversion;
////		this.createUserId = createUserId;
////		this.createDateTime = (Timestamp) createDateTime;
////		this.updateUserId = updateUserId;
////		this.updateDateTime = (Timestamp) updateDateTime;
////		this.updateExtensionSchema = updateExtensionSchema;
//	}

}