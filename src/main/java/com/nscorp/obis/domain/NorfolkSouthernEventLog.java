package com.nscorp.obis.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;

@Entity
@Table(name = "NS_EVT_LOG")
@EqualsAndHashCode(callSuper=false)
public class NorfolkSouthernEventLog extends AuditInfo {

	@Id
	@Column(name = "EVT_LOG_ID", columnDefinition = "double", nullable = false)
	private Long eventLogId;

	@Column(name = "EQ_INIT", columnDefinition = "char(4)", nullable = true)
	private String equipmentInit;

	@Column(name = "EQ_NR", columnDefinition = "decimal(19)", nullable = true)
	private BigDecimal equipmentNumber;

	@Column(name = "EQ_TP", columnDefinition = "char(1)", nullable = true)
	private String equipmentType;

	@Column(name = "EQ_ID", columnDefinition = "char(4)", nullable = true)
	private String equipmentId;

	@Column(name = "TO_EQ_INIT", columnDefinition = "char(4)", nullable = true)
	private String toEquipmentInit;

	@Column(name = "TO_EQ_NR", columnDefinition = "decimal(19)", nullable = true)
	private BigDecimal toEquipmentNumber;

	@Column(name = "TO_EQ_TP", columnDefinition = "char(1)", nullable = true)
	private String toEquipmentType;

	@Column(name = "TO_EQ_ID", columnDefinition = "char(4)", nullable = true)
	private String toEquipmentId;

	@Column(name = "L_E_IND", columnDefinition = "char(1)", nullable = true)
	private String leInd;

	@Column(name = "INTCH_IND", columnDefinition = "char(1)", nullable = true)
	private String intchInd;

	@Column(name = "HAZ_IND", columnDefinition = "char(1)", nullable = true)
	private String hazInd;

	@Column(name = "DRIVER_ID", columnDefinition = "double", nullable = true)
	private Long driverId;

	@Column(name = "EVT_CD", columnDefinition = "char(4)", nullable = true)
	private String evtCd;

	@Column(name = "EVT_DT_TM", nullable = true)
	private Timestamp eventDateTime;

	@Column(name = "CUST_ID", columnDefinition = "double", nullable = true)
	private Long customerId;

	@Column(name = "SVC_ID", columnDefinition = "double", nullable = true)
	private Long serviceId;

	@Column(name = "TERM_ID", columnDefinition = "double", nullable = true)
	private Long terminalId;

	@Column(name = "TOFC_COFC_FG", columnDefinition = "char(1)", nullable = true)
	private String tofcCofcFg;

	@Column(name = "REASON_CD", columnDefinition = "char(4)", nullable = true)
	private String reasonCd;

	@Column(name = "TRAIN_NR", columnDefinition = "char(4)", nullable = true)
	private String trainNumber;

	@Column(name = "TRAIN_ORIG_DAY", columnDefinition = "smallint", nullable = true)
	private Integer trainOrigDay;

	@Column(name = "TRAIN_LN_SEGMENT", columnDefinition = "char(2)", nullable = true)
	private String trainInSegment;

	@Column(name = "DRAY_CUST_ID", columnDefinition = "double", nullable = true)
	private Long drayCustId;

	@Column(name = "RP_DT_TM", nullable = true)
	private Timestamp rpDateTime;

	@Column(name = "MSG_PRIORITY", columnDefinition = "char(2)", nullable = true)
	private String messagePriority;

	@Column(name = "DMGE_TP", columnDefinition = "char(4)", nullable = true)
	private String damageType;

	@Column(name = "BORD_IND", columnDefinition = "char(1)", nullable = true)
	private String bondInd;

	@Column(name = "INSP_DT_TM", nullable = true)
	private Timestamp inspDateTime;

	@Column(name = "INSP_WRKR_ID", columnDefinition = "char(8)", nullable = true)
	private String inspWorkerId;

	@Column(name = "INSP_TEMP", columnDefinition = "char(3)", nullable = true)
	private String inspTemp;

	@Column(name = "INSP_FL_LVL", columnDefinition = "char(1)", nullable = true)
	private String inspFlLvl;

	@Column(name = "INSP_RUN_IND", columnDefinition = "char(1)", nullable = true)
	private String inspRunInd;

	@Column(name = "SET_TEMP", columnDefinition = "char(3)", nullable = true)
	private String setTemp;

	@Column(name = "DMGE_IND", columnDefinition = "char(1)", nullable = true)
	private String dmgeInd;

	@Column(name = "INSP_EVT_LOG_ID", columnDefinition = "double", nullable = true)
	private Long inspEvtLogId;

	@Column(name = "CRANE_ID", columnDefinition = "char(8)", nullable = true)
	private String craneId;

	@Column(name = "MAINT_TP_CD", columnDefinition = "char(2)", nullable = true)
	private String maintTpCd;

	@Column(name = "CAR_INIT", columnDefinition = "char(4)", nullable = true)
	private String carInit;

	@Column(name = "CAR_NR", columnDefinition = "decimal(19)", nullable = true)
	private BigDecimal carNumber;

	@Column(name = "CAR_TP", columnDefinition = "char(1)", nullable = true)
	private String carType;

	@Column(name = "CHAS_INIT", columnDefinition = "char(4)", nullable = true)
	private String chasInit;

	@Column(name = "CHAS_NR", columnDefinition = "decimal(19)", nullable = true)
	private BigDecimal chasNumber;

	@Column(name = "CHAS_TP", columnDefinition = "char(1)", nullable = true)
	private String chasType;

	@Column(name = "CHAS_ID", columnDefinition = "char(4)", nullable = true)
	private String chasId;

	@Column(name = "LOT_AREA_ID", columnDefinition = "double", nullable = true)
	private Long lotAreaId;

	@Column(name = "EQ_DMGE_ID", columnDefinition = "double", nullable = true)
	private Long eqDmgeId;

	@Column(name = "TRACK_ID", columnDefinition = "char(4)", nullable = true)
	private String trackId;

	@Column(name = "SP_PGM_ACT_CD", columnDefinition = "char(3)", nullable = true)
	private String spPgmActCd;

	@Column(name = "SP_PGM_AMT", columnDefinition = "char(8)", nullable = true)
	private String spPgmAmt;

	@Column(name = "SP_PGM_AMT_TP", columnDefinition = "char(1)", nullable = true)
	private String spPgmAmtTp;

	@Column(name = "SP_PGM_AMT_ACT", columnDefinition = "char(1)", nullable = true)
	private String spPgmAmtAct;

	@Column(name = "PGM_NM", columnDefinition = "char(80)", nullable = true)
	private String programName;

	@Column(name = "ICHG_ROAD_NM", columnDefinition = "char(4)", nullable = true)
	private String ichgRoadName;

	@Column(name = "OTHER_ICHG_CD", columnDefinition = "char(4)", nullable = true)
	private String ichgIchgName;

	@Column(name = "CAR_ROUTE", columnDefinition = "char(90)", nullable = true)
	private String carRoute;

	@Column(name = "SW_LIST_NR", columnDefinition = "integer", nullable = true)
	private Integer swListNr;

	@Column(name = "LINE_NR", columnDefinition = "smallint", nullable = true)
	private Integer lineNr;

	@Column(name = "BLOCK_ID", columnDefinition = "double", nullable = true)
	private Long blockId;

	@Column(name = "CAR_LD_POS", columnDefinition = "char(2)", nullable = true)
	private String carLdPos;

	@Column(name = "CHRG_ID", columnDefinition = "double", nullable = true)
	private Long chrgId;

	@Column(name = "MOVED_WITH_BILLING_IND", columnDefinition = "char(1)", nullable = true)
	private String movedWithBillingInd;

	@Column(name = "PASS_TO_LEGACY", columnDefinition = "char(1)", nullable = true)
	private String passToLegacy;

	@Column(name = "LCL_DT_TM", nullable = true)
	private Timestamp lclDateTime;

	@Column(name = "STD_DT_TM", nullable = true)
	private Timestamp stdDateTime;

	@Column(name = "GEN_FREIGHT_ID", columnDefinition = "char(10)", nullable = true)
	private String genFreightId;

	@Column(name = "LD_LVL_CD", columnDefinition = "char(2)", nullable = true)
	private String lsLvlCd;

	@Column(name = "WB_VERSION", columnDefinition = "smallint", nullable = true)
	private Integer wbVersion;

	@Column(name = "SWITCH_ID", columnDefinition = "double", nullable = true)
	private Long switchId;

	@Column(name = "TKR_CD", columnDefinition = "char(4)", nullable = true)
	private String tkrCd;

	public Long getEventLogId() {
		return eventLogId;
	}

	public void setEventLogId(Long eventLogId) {
		this.eventLogId = eventLogId;
	}

	public String getEquipmentInit() {
		return equipmentInit;
	}

	public void setEquipmentInit(String equipmentInit) {
		this.equipmentInit = equipmentInit;
	}

	public BigDecimal getEquipmentNumber() {
		return equipmentNumber;
	}

	public void setEquipmentNumber(BigDecimal equipmentNumber) {
		this.equipmentNumber = equipmentNumber;
	}

	public String getEquipmentType() {
		if(equipmentType != null) {
			return equipmentType.trim();
		}
		else {
			return equipmentType;
		}
	}

	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}

	public String getEquipmentId() {
		if(equipmentId != null) {
			return equipmentId.trim();
		}
		else {
			return equipmentId;
		}
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getToEquipmentInit() {
		return toEquipmentInit;
	}

	public void setToEquipmentInit(String toEquipmentInit) {
		this.toEquipmentInit = toEquipmentInit;
	}

	public BigDecimal getToEquipmentNumber() {
		return toEquipmentNumber;
	}

	public void setToEquipmentNumber(BigDecimal toEquipmentNumber) {
		this.toEquipmentNumber = toEquipmentNumber;
	}

	public String getToEquipmentType() {
		return toEquipmentType;
	}

	public void setToEquipmentType(String toEquipmentType) {
		this.toEquipmentType = toEquipmentType;
	}

	public String getToEquipmentId() {
		return toEquipmentId;
	}

	public void setToEquipmentId(String toEquipmentId) {
		this.toEquipmentId = toEquipmentId;
	}

	public String getLeInd() {
		return leInd;
	}

	public void setLeInd(String leInd) {
		this.leInd = leInd;
	}

	public String getIntchInd() {
		return intchInd;
	}

	public void setIntchInd(String intchInd) {
		this.intchInd = intchInd;
	}

	public String getHazInd() {
		return hazInd;
	}

	public void setHazInd(String hazInd) {
		this.hazInd = hazInd;
	}

	public Long getDriverId() {
		return driverId;
	}

	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}

	public String getEvtCd() {
		if(evtCd != null) {
			return evtCd.trim();
		}
		else {
			return evtCd;
		}
	}

	public void setEvtCd(String evtCd) {
		this.evtCd = evtCd;
	}

	public Timestamp getEventDateTime() {
		return eventDateTime;
	}

	public void setEventDateTime(Timestamp eventDateTime) {
		this.eventDateTime = eventDateTime;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Long getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}

	public String getTofcCofcFg() {
		return tofcCofcFg;
	}

	public void setTofcCofcFg(String tofcCofcFg) {
		this.tofcCofcFg = tofcCofcFg;
	}

	public String getReasonCd() {
		return reasonCd;
	}

	public void setReasonCd(String reasonCd) {
		this.reasonCd = reasonCd;
	}

	public String getTrainNumber() {
		return trainNumber;
	}

	public void setTrainNumber(String trainNumber) {
		this.trainNumber = trainNumber;
	}

	public Integer getTrainOrigDay() {
		return trainOrigDay;
	}

	public void setTrainOrigDay(Integer trainOrigDay) {
		this.trainOrigDay = trainOrigDay;
	}

	public String getTrainInSegment() {
		return trainInSegment;
	}

	public void setTrainInSegment(String trainInSegment) {
		this.trainInSegment = trainInSegment;
	}

	public Long getDrayCustId() {
		return drayCustId;
	}

	public void setDrayCustId(Long drayCustId) {
		this.drayCustId = drayCustId;
	}

	public Timestamp getRpDateTime() {
		return rpDateTime;
	}

	public void setRpDateTime(Timestamp rpDateTime) {
		this.rpDateTime = rpDateTime;
	}

	public String getMessagePriority() {
		return messagePriority;
	}

	public void setMessagePriority(String messagePriority) {
		this.messagePriority = messagePriority;
	}

	public String getDamageType() {
		return damageType;
	}

	public void setDamageType(String damageType) {
		this.damageType = damageType;
	}

	public String getBondInd() {
		return bondInd;
	}

	public void setBondInd(String bondInd) {
		this.bondInd = bondInd;
	}

	public Timestamp getInspDateTime() {
		return inspDateTime;
	}

	public void setInspDateTime(Timestamp inspDateTime) {
		this.inspDateTime = inspDateTime;
	}

	public String getInspWorkerId() {
		return inspWorkerId;
	}

	public void setInspWorkerId(String inspWorkerId) {
		this.inspWorkerId = inspWorkerId;
	}

	public String getInspTemp() {
		return inspTemp;
	}

	public void setInspTemp(String inspTemp) {
		this.inspTemp = inspTemp;
	}

	public String getInspFlLvl() {
		return inspFlLvl;
	}

	public void setInspFlLvl(String inspFlLvl) {
		this.inspFlLvl = inspFlLvl;
	}

	public String getInspRunInd() {
		return inspRunInd;
	}

	public void setInspRunInd(String inspRunInd) {
		this.inspRunInd = inspRunInd;
	}

	public String getSetTemp() {
		return setTemp;
	}

	public void setSetTemp(String setTemp) {
		this.setTemp = setTemp;
	}

	public String getDmgeInd() {
		return dmgeInd;
	}

	public void setDmgeInd(String dmgeInd) {
		this.dmgeInd = dmgeInd;
	}

	public Long getInspEvtLogId() {
		return inspEvtLogId;
	}

	public void setInspEvtLogId(Long inspEvtLogId) {
		this.inspEvtLogId = inspEvtLogId;
	}

	public String getCraneId() {
		return craneId;
	}

	public void setCraneId(String craneId) {
		this.craneId = craneId;
	}

	public String getMaintTpCd() {
		return maintTpCd;
	}

	public void setMaintTpCd(String maintTpCd) {
		this.maintTpCd = maintTpCd;
	}

	public String getCarInit() {
		return carInit;
	}

	public void setCarInit(String carInit) {
		this.carInit = carInit;
	}

	public BigDecimal getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(BigDecimal carNumber) {
		this.carNumber = carNumber;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getChasInit() {
		return chasInit;
	}

	public void setChasInit(String chasInit) {
		this.chasInit = chasInit;
	}

	public BigDecimal getChasNumber() {
		return chasNumber;
	}

	public void setChasNumber(BigDecimal chasNumber) {
		this.chasNumber = chasNumber;
	}

	public String getChasType() {
		return chasType;
	}

	public void setChasType(String chasType) {
		this.chasType = chasType;
	}

	public String getChasId() {
		return chasId;
	}

	public void setChasId(String chasId) {
		this.chasId = chasId;
	}

	public Long getLotAreaId() {
		return lotAreaId;
	}

	public void setLotAreaId(Long lotAreaId) {
		this.lotAreaId = lotAreaId;
	}

	public Long getEqDmgeId() {
		return eqDmgeId;
	}

	public void setEqDmgeId(Long eqDmgeId) {
		this.eqDmgeId = eqDmgeId;
	}

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public String getSpPgmActCd() {
		return spPgmActCd;
	}

	public void setSpPgmActCd(String spPgmActCd) {
		this.spPgmActCd = spPgmActCd;
	}

	public String getSpPgmAmt() {
		return spPgmAmt;
	}

	public void setSpPgmAmt(String spPgmAmt) {
		this.spPgmAmt = spPgmAmt;
	}

	public String getSpPgmAmtTp() {
		return spPgmAmtTp;
	}

	public void setSpPgmAmtTp(String spPgmAmtTp) {
		this.spPgmAmtTp = spPgmAmtTp;
	}

	public String getSpPgmAmtAct() {
		return spPgmAmtAct;
	}

	public void setSpPgmAmtAct(String spPgmAmtAct) {
		this.spPgmAmtAct = spPgmAmtAct;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getIchgRoadName() {
		return ichgRoadName;
	}

	public void setIchgRoadName(String ichgRoadName) {
		this.ichgRoadName = ichgRoadName;
	}

	public String getIchgIchgName() {
		return ichgIchgName;
	}

	public void setIchgIchgName(String ichgIchgName) {
		this.ichgIchgName = ichgIchgName;
	}

	public String getCarRoute() {
		return carRoute;
	}

	public void setCarRoute(String carRoute) {
		this.carRoute = carRoute;
	}

	public Integer getSwListNr() {
		return swListNr;
	}

	public void setSwListNr(Integer swListNr) {
		this.swListNr = swListNr;
	}

	public Integer getLineNr() {
		return lineNr;
	}

	public void setLineNr(Integer lineNr) {
		this.lineNr = lineNr;
	}

	public Long getBlockId() {
		return blockId;
	}

	public void setBlockId(Long blockId) {
		this.blockId = blockId;
	}

	public String getCarLdPos() {
		return carLdPos;
	}

	public void setCarLdPos(String carLdPos) {
		this.carLdPos = carLdPos;
	}

	public Long getChrgId() {
		return chrgId;
	}

	public void setChrgId(Long chrgId) {
		this.chrgId = chrgId;
	}

	public String getMovedWithBillingInd() {
		return movedWithBillingInd;
	}

	public void setMovedWithBillingInd(String movedWithBillingInd) {
		this.movedWithBillingInd = movedWithBillingInd;
	}

	public String getPassToLegacy() {
		return passToLegacy;
	}

	public void setPassToLegacy(String passToLegacy) {
		this.passToLegacy = passToLegacy;
	}

	public Timestamp getLclDateTime() {
		return lclDateTime;
	}

	public void setLclDateTime(Timestamp lclDateTime) {
		this.lclDateTime = lclDateTime;
	}

	public Timestamp getStdDateTime() {
		return stdDateTime;
	}

	public void setStdDateTime(Timestamp stdDateTime) {
		this.stdDateTime = stdDateTime;
	}

	public String getGenFreightId() {
		return genFreightId;
	}

	public void setGenFreightId(String genFreightId) {
		this.genFreightId = genFreightId;
	}

	public String getLsLvlCd() {
		return lsLvlCd;
	}

	public void setLsLvlCd(String lsLvlCd) {
		this.lsLvlCd = lsLvlCd;
	}

	public Integer getWbVersion() {
		return wbVersion;
	}

	public void setWbVersion(Integer wbVersion) {
		this.wbVersion = wbVersion;
	}

	public Long getSwitchId() {
		return switchId;
	}

	public void setSwitchId(Long switchId) {
		this.switchId = switchId;
	}

	public String getTkrCd() {
		return tkrCd;
	}

	public void setTkrCd(String tkrCd) {
		this.tkrCd = tkrCd;
	}

	public NorfolkSouthernEventLog(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Long eventLogId, String equipmentInit,
			BigDecimal equipmentNumber, String equipmentType, String equipmentId, String toEquipmentInit,
			BigDecimal toEquipmentNumber, String toEquipmentType, String toEquipmentId, String leInd, String intchInd,
			String hazInd, Long driverId, String evtCd, Timestamp eventDateTime, Long customerId, Long serviceId,
			Long terminalId, String tofcCofcFg, String reasonCd, String trainNumber, Integer trainOrigDay,
			String trainInSegment, Long drayCustId, Timestamp rpDateTime, String messagePriority, String damageType,
			String bondInd, Timestamp inspDateTime, String inspWorkerId, String inspTemp, String inspFlLvl,
			String inspRunInd, String setTemp, String dmgeInd, Long inspEvtLogId, String craneId, String maintTpCd,
			String carInit, BigDecimal carNumber, String carType, String chasInit, BigDecimal chasNumber,
			String chasType, String chasId, Long lotAreaId, Long eqDmgeId, String trackId, String spPgmActCd,
			String spPgmAmt, String spPgmAmtTp, String spPgmAmtAct, String programName, String ichgRoadName,
			String ichgIchgName, String carRoute, Integer swListNr, Integer lineNr, Long blockId, String carLdPos,
			Long chrgId, String movedWithBillingInd, String passToLegacy, Timestamp lclDateTime, Timestamp stdDateTime,
			String genFreightId, String lsLvlCd, Integer wbVersion, Long switchId, String tkrCd) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.eventLogId = eventLogId;
		this.equipmentInit = equipmentInit;
		this.equipmentNumber = equipmentNumber;
		this.equipmentType = equipmentType;
		this.equipmentId = equipmentId;
		this.toEquipmentInit = toEquipmentInit;
		this.toEquipmentNumber = toEquipmentNumber;
		this.toEquipmentType = toEquipmentType;
		this.toEquipmentId = toEquipmentId;
		this.leInd = leInd;
		this.intchInd = intchInd;
		this.hazInd = hazInd;
		this.driverId = driverId;
		this.evtCd = evtCd;
		this.eventDateTime = eventDateTime;
		this.customerId = customerId;
		this.serviceId = serviceId;
		this.terminalId = terminalId;
		this.tofcCofcFg = tofcCofcFg;
		this.reasonCd = reasonCd;
		this.trainNumber = trainNumber;
		this.trainOrigDay = trainOrigDay;
		this.trainInSegment = trainInSegment;
		this.drayCustId = drayCustId;
		this.rpDateTime = rpDateTime;
		this.messagePriority = messagePriority;
		this.damageType = damageType;
		this.bondInd = bondInd;
		this.inspDateTime = inspDateTime;
		this.inspWorkerId = inspWorkerId;
		this.inspTemp = inspTemp;
		this.inspFlLvl = inspFlLvl;
		this.inspRunInd = inspRunInd;
		this.setTemp = setTemp;
		this.dmgeInd = dmgeInd;
		this.inspEvtLogId = inspEvtLogId;
		this.craneId = craneId;
		this.maintTpCd = maintTpCd;
		this.carInit = carInit;
		this.carNumber = carNumber;
		this.carType = carType;
		this.chasInit = chasInit;
		this.chasNumber = chasNumber;
		this.chasType = chasType;
		this.chasId = chasId;
		this.lotAreaId = lotAreaId;
		this.eqDmgeId = eqDmgeId;
		this.trackId = trackId;
		this.spPgmActCd = spPgmActCd;
		this.spPgmAmt = spPgmAmt;
		this.spPgmAmtTp = spPgmAmtTp;
		this.spPgmAmtAct = spPgmAmtAct;
		this.programName = programName;
		this.ichgRoadName = ichgRoadName;
		this.ichgIchgName = ichgIchgName;
		this.carRoute = carRoute;
		this.swListNr = swListNr;
		this.lineNr = lineNr;
		this.blockId = blockId;
		this.carLdPos = carLdPos;
		this.chrgId = chrgId;
		this.movedWithBillingInd = movedWithBillingInd;
		this.passToLegacy = passToLegacy;
		this.lclDateTime = lclDateTime;
		this.stdDateTime = stdDateTime;
		this.genFreightId = genFreightId;
		this.lsLvlCd = lsLvlCd;
		this.wbVersion = wbVersion;
		this.switchId = switchId;
		this.tkrCd = tkrCd;
	}

	public NorfolkSouthernEventLog() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NorfolkSouthernEventLog(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}

}