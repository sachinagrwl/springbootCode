package com.nscorp.obis.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "VOICE_NOTIFY_V1")
public class VoiceNotify {

	@ManyToOne
	@JoinColumn(name = "SVC_ID", referencedColumnName = "SVC_ID", insertable = false, updatable = false)
	@JsonIgnore
	@NotFound(action = NotFoundAction.IGNORE)
	private VoiceNotify2 voiceNotify2;

	@Id
	@Column(name = "NTFY_QUEUE_ID", length = 15, columnDefinition = "double", nullable = false)
	private Long notifyQueueId;

	@Column(name = "NTFY_CUST_ID", length = 15, columnDefinition = "double", nullable = true)
	private Long notifyCustId;
	
	@Transient
	private String customerName;
	
	@Transient
	private List<NotepadEntry> note;

	@Column(name = "TERM_ID", length = 15, columnDefinition = "double", nullable = true)
	private Long termId;

	@Column(name = "SVC_ID", length = 15, columnDefinition = "double", nullable = true)
	private Long svcId;

	@Column(name = "EVT_CD", columnDefinition = "char(4)", nullable = true)
	private String eventCd;

	@Column(name = "NTFY_STAT", columnDefinition = "char(4)", nullable = true)
	private String notifyStat;

	@Column(name = "PERSON_NTFIED", columnDefinition = "char(30)", nullable = true)
	private String personField;

	@Column(name = "NTFY_MTHD", columnDefinition = "char(6)", nullable = true)
	private String notifyMethod;

	@Column(name = "RENOT_CNT", columnDefinition = "Integer", nullable = true)
	private Integer renotCount;

	@Column(name = "NTFY_NM", columnDefinition = "char(30)", nullable = true)
	private String notifyName;

	@Column(name = "NTFY_AREA_CD", columnDefinition = "smallint", nullable = true)
	private String notifyArea;

	@Column(name = "NTFY_PREFIX", columnDefinition = "smallint", nullable = true)
	private Integer notifyPrefix;

	@Column(name = "NTFY_SUFFIX", columnDefinition = "smallint", nullable = true)
	private Integer notifySufix;

	@Column(name = "NTFY_EXT", columnDefinition = "smallint", nullable = true)
	private Integer notifyExit;

	@Column(name = "DRIVER_ID", length = 15, columnDefinition = "double", nullable = true)
	private Long driverId;

	@Column(name = "TRAIN_NR", columnDefinition = "char(4)", nullable = true)
	private String trainName;

	@Column(name = "DRAY_CUST_ID", length = 15, columnDefinition = "double", nullable = true)
	private Long drayCust;

	@Column(name = "LCL_DT_TM", columnDefinition = "timestamp", nullable = true)
	private LocalDateTime localDateTm;

	@Column(name = "LD_EMPTY_CD", columnDefinition = "char(1)", nullable = true)
	private String emptyCd;

	@Column(name = "SHIP_VESSEL_NM", columnDefinition = "char(30)", nullable = true)
	private String shipVesselName;

	@Column(name = "BOOK_NR", columnDefinition = "char(17)", nullable = true)
	private String bookNr;

	@Column(name = "BONDED_IND", columnDefinition = "char(1)", nullable = true)
	private String bondedInd;

	@Column(name = "LADING_WT", columnDefinition = "integer", nullable = true)
	private Integer landingWeight;

	@Column(name = " HAZ_SHIP_IND", columnDefinition = "char(1)", nullable = true)
	private String hazShip;

	@Column(name = " NTFY_OVRD_NM", columnDefinition = "char(35)", nullable = true)
	private String notifyOvrdName;

	@Column(name = " NTFY_OVRD_EXCH", columnDefinition = "Smallint", nullable = true)
	private Integer notifyOvrdExchange;

	@Column(name = " NTFY_OVRD_BASE", columnDefinition = "Smallint", nullable = true)
	private Integer notifyOvrdBase;

	@Column(name = " NTFY_OVRD_AREA_CD", columnDefinition = "Smallint", nullable = true)
	private Integer notifyOvrdArea;

	@Column(name = " NTFY_OVRD_EXT", columnDefinition = "smallint", nullable = true)
	private Integer notifyOvrdExt;

	@Column(name = " OP_STN_8_SPELL", columnDefinition = "char(8)", nullable = true)
	private String opSpell;

	@Column(name = " NTFY_REASON_CD", columnDefinition = "char(20)", nullable = true)
	private String notifyReason;

	@Column(name = " UPD_DT_TM", columnDefinition = "timestamp", nullable = true)
	private LocalDateTime updateDtTm;

	@Column(name = " UPD_EXTN_SCHEMA", columnDefinition = "char(16)", nullable = true)
	private String updateExtSchema;

	@Column(name = "EQ_ID", columnDefinition = "char(4)", nullable = true)
	private String equipId;

	@Column(name = "EQ_TP", columnDefinition = "char(1)", nullable = true)
	private String equipTp;

	@Column(name = "EQ_NR", length = 19, columnDefinition = "decimal", nullable = true)
	private BigDecimal equipNbr;

	@Column(name = "EQ_INIT", columnDefinition = "char(4)", nullable = true)
	private String equipInit;

	@Column(name = "CHAS_INIT", columnDefinition = "char(4)", nullable = true)
	private String chasInit;

	@Column(name = "CHAS_NR", length = 19, columnDefinition = "decimal", nullable = true)
	private BigDecimal chasNbr;

	@Column(name = "CHAS_TP", columnDefinition = "char(1)", nullable = true)
	private String chasTp;

	@Column(name = "CHAS_ID", columnDefinition = "char(4)", nullable = true)
	private String chasId;

	@Column(name = "DMGE_IND", columnDefinition = "char(1)", nullable = true)
	private String dmgInd;

	@Column(name = "PICKUP_NR", columnDefinition = "char(6)", nullable = true)
	private String pickupNr;

	@Column(name = "SHIP_CUST", length = 15, columnDefinition = "double", nullable = true)
	private Long shipCust;

	@Transient
	private String evtDesc;

	@Transient
	private Station stationName;

	@Transient
	private GenericCodeUpdate genericCodeUpdate;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public List<NotepadEntry> getNote() {
		return note;
	}

	public void setNote(List<NotepadEntry> note) {
		this.note = note;
	}

	public GenericCodeUpdate getGenericCodeUpdate() {
		return genericCodeUpdate;
	}

	public void setGenericCodeUpdate(GenericCodeUpdate genericCodeUpdate) {
		this.genericCodeUpdate = genericCodeUpdate;
	}

	public Station getStationName() {
		return stationName;
	}

	public void setStationName(Station stationName) {
		this.stationName = stationName;
	}

	public String getPickupNr() {
		if (pickupNr != null) {
			return pickupNr.trim();
		} else {
			return pickupNr;
		}
	}

	public void setPickupNr(String pickupNr) {
		this.pickupNr = pickupNr;
	}

	public VoiceNotify2 getVoiceNotify2() {
		return voiceNotify2;
	}

	public void setVoiceNotify2(VoiceNotify2 voiceNotify2) {
		this.voiceNotify2 = voiceNotify2;
	}

	public String getPersonField() {
		return personField;
	}

	public void setPersonField(String personField) {
		this.personField = personField;
	}

	public String getNotifyMethod() {
		if (notifyMethod != null) {
			return notifyMethod.trim();
		} else {
			return notifyMethod;
		}

	}

	public void setNotifyMethod(String notifyMethod) {
		this.notifyMethod = notifyMethod;
	}

	public Integer getRenotCount() {
		return renotCount;
	}

	public void setRenotCount(Integer renotCount) {
		this.renotCount = renotCount;
	}

	public String getNotifyName() {
		if (notifyName != null) {
			return notifyName.trim();
		} else {
			return notifyName;
		}
	}

	public void setNotifyName(String notifyName) {
		this.notifyName = notifyName;
	}

	public String getNotifyArea() {
		return notifyArea;
	}

	public void setNotifyArea(String notifyArea) {
		this.notifyArea = notifyArea;
	}

	public Integer getNotifyPrefix() {
		return notifyPrefix;
	}

	public void setNotifyPrefix(Integer notifyPrefix) {
		this.notifyPrefix = notifyPrefix;
	}

	public Integer getNotifySufix() {
		return notifySufix;
	}

	public void setNotifySufix(Integer notifySufix) {
		this.notifySufix = notifySufix;
	}

	public Integer getNotifyExit() {
		return notifyExit;
	}

	public void setNotifyExit(Integer notifyExit) {
		this.notifyExit = notifyExit;
	}

	public Long getDriverId() {
		return driverId;
	}

	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}

	public String getTrainName() {
		if (trainName != null) {
			return trainName.trim();
		} else {
			return trainName;
		}
	}

	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}

	public Long getDrayCust() {
		return drayCust;
	}

	public void setDrayCust(Long drayCust) {
		this.drayCust = drayCust;
	}

	public LocalDateTime getLocalDateTm() {
		return localDateTm;
	}

	public void setLocalDateTm(LocalDateTime localDateTm) {
		this.localDateTm = localDateTm;
	}

	public String getEmptyCd() {
		return emptyCd;
	}

	public void setEmptyCd(String emptyCd) {
		this.emptyCd = emptyCd;
	}

	public String getShipVesselName() {
		return shipVesselName;
	}

	public void setShipVesselName(String shipVesselName) {
		this.shipVesselName = shipVesselName;
	}

	public String getBookNr() {
		return bookNr;
	}

	public void setBookNr(String bookNr) {
		this.bookNr = bookNr;
	}

	public String getBondedInd() {
		return bondedInd;
	}

	public void setBondedInd(String bondedInd) {
		this.bondedInd = bondedInd;
	}

	public Integer getLandingWeight() {
		return landingWeight;
	}

	public void setLandingWeight(Integer landingWeight) {
		this.landingWeight = landingWeight;
	}

	public String getHazShip() {
		return hazShip;
	}

	public void setHazShip(String hazShip) {
		this.hazShip = hazShip;
	}

	public String getNotifyOvrdName() {
		if (notifyOvrdName != null) {
			return notifyOvrdName.trim();
		} else {
			return notifyOvrdName;
		}
	}

	public void setNotifyOvrdName(String notifyOvrdName) {
		this.notifyOvrdName = notifyOvrdName;
	}

	public Integer getNotifyOvrdExchange() {
		return notifyOvrdExchange;
	}

	public void setNotifyOvrdExchange(Integer notifyOvrdExchange) {
		this.notifyOvrdExchange = notifyOvrdExchange;
	}

	public Integer getNotifyOvrdBase() {
		return notifyOvrdBase;
	}

	public void setNotifyOvrdBase(Integer notifyOvrdBase) {
		this.notifyOvrdBase = notifyOvrdBase;
	}

	public Integer getNotifyOvrdArea() {
		return notifyOvrdArea;
	}

	public void setNotifyOvrdArea(Integer notifyOvrdArea) {
		this.notifyOvrdArea = notifyOvrdArea;
	}

	public Integer getNotifyOvrdExt() {
		return notifyOvrdExt;
	}

	public void setNotifyOvrdExt(Integer notifyOvrdExt) {
		this.notifyOvrdExt = notifyOvrdExt;
	}

	public String getOpSpell() {
		return opSpell;
	}

	public void setOpSpell(String opSpell) {
		this.opSpell = opSpell;
	}

	public String getNotifyReason() {
		return notifyReason;
	}

	public void setNotifyReason(String notifyReason) {
		this.notifyReason = notifyReason;
	}

	public LocalDateTime getUpdateDtTm() {
		return updateDtTm;
	}

	public void setUpdateDtTm(LocalDateTime updateDtTm) {
		this.updateDtTm = updateDtTm;
	}

	public String getUpdateExtSchema() {
		if (updateExtSchema != null) {
			return updateExtSchema.trim();
		} else {
			return updateExtSchema;
		}
	}

	public void setUpdateExtSchema(String updateExtSchema) {
		this.updateExtSchema = updateExtSchema;
	}

	public Long getSvcId() {
		return svcId;
	}

	public void setSvcId(Long svcId) {
		this.svcId = svcId;
	}

	public String getEvtDesc() {
		return evtDesc;
	}

	public void setEvtDesc(String evtDesc) {
		this.evtDesc = evtDesc;
	}

	public String getDmgInd() {
		return dmgInd;
	}

	public void setDmgInd(String dmgInd) {
		this.dmgInd = dmgInd;
	}

	public String getChasInit() {
		return chasInit;
	}

	public void setChasInit(String chasInit) {
		this.chasInit = chasInit;
	}

	public BigDecimal getChasNbr() {
		return chasNbr;
	}

	public void setChasNbr(BigDecimal chasNbr) {
		this.chasNbr = chasNbr;
	}

	public String getChasTp() {
		return chasTp;
	}

	public void setChasTp(String chasTp) {
		this.chasTp = chasTp;
	}

	public String getChasId() {
		if (chasId != null) {
			return chasId.trim();
		}
		return chasId;
	}

	public void setChasId(String chasId) {
		this.chasId = chasId;
	}

	public String getEquipId() {
		if (equipId != null) {
			return equipId.trim();
		} else {
			return equipId;
		}
	}

	public void setEquipId(String equipId) {
		this.equipId = equipId;
	}

	public String getEquipTp() {
		return equipTp;
	}

	public void setEquipTp(String equipTp) {
		this.equipTp = equipTp;
	}

	public BigDecimal getEquipNbr() {
		return equipNbr;
	}

	public void setEquipNbr(BigDecimal equipNbr) {
		this.equipNbr = equipNbr;
	}

	public String getEquipInit() {
		return equipInit;
	}

	public void setEquipInit(String equipInit) {
		this.equipInit = equipInit;
	}

	public Long getNotifyQueueId() {
		return notifyQueueId;
	}

	public void setNotifyQueueId(Long notifyQueueId) {
		this.notifyQueueId = notifyQueueId;
	}

	public Long getNotifyCustId() {
		return notifyCustId;
	}

	public void setNotifyCustId(Long notifyCustId) {
		this.notifyCustId = notifyCustId;
	}

	public Long getTermId() {
		return termId;
	}

	public void setTermId(Long termId) {
		this.termId = termId;
	}

	public String getEventCd() {
		return eventCd;
	}

	public void setEventCd(String eventCd) {
		this.eventCd = eventCd;
	}

	public String getNotifyStat() {
		return notifyStat;
	}

	public void setNotifyStat(String notifyStat) {
		this.notifyStat = notifyStat;
	}

	public Long getShipCust() {
		return shipCust;
	}

	public void setShipCust(Long shipCust) {
		this.shipCust = shipCust;
	}

	public VoiceNotify(VoiceNotify2 voiceNotify2, Long notifyQueueId, Long notifyCustId, String customerName, List<NotepadEntry> note, Long termId, Long svcId, String eventCd, String notifyStat, String personField, String notifyMethod, Integer renotCount, String notifyName, String notifyArea, Integer notifyPrefix, Integer notifySufix, Integer notifyExit, Long driverId, String trainName, Long drayCust, LocalDateTime localDateTm, String emptyCd, String shipVesselName, String bookNr, String bondedInd, Integer landingWeight, String hazShip, String notifyOvrdName, Integer notifyOvrdExchange, Integer notifyOvrdBase, Integer notifyOvrdArea, Integer notifyOvrdExt, String opSpell, String notifyReason, LocalDateTime updateDtTm, String updateExtSchema, String equipId, String equipTp, BigDecimal equipNbr, String equipInit, String chasInit, BigDecimal chasNbr, String chasTp, String chasId, String dmgInd, String pickupNr, Long shipCust, String evtDesc, Station stationName, GenericCodeUpdate genericCodeUpdate) {
		this.voiceNotify2 = voiceNotify2;
		this.notifyQueueId = notifyQueueId;
		this.notifyCustId = notifyCustId;
		this.customerName = customerName;
		this.note = note;
		this.termId = termId;
		this.svcId = svcId;
		this.eventCd = eventCd;
		this.notifyStat = notifyStat;
		this.personField = personField;
		this.notifyMethod = notifyMethod;
		this.renotCount = renotCount;
		this.notifyName = notifyName;
		this.notifyArea = notifyArea;
		this.notifyPrefix = notifyPrefix;
		this.notifySufix = notifySufix;
		this.notifyExit = notifyExit;
		this.driverId = driverId;
		this.trainName = trainName;
		this.drayCust = drayCust;
		this.localDateTm = localDateTm;
		this.emptyCd = emptyCd;
		this.shipVesselName = shipVesselName;
		this.bookNr = bookNr;
		this.bondedInd = bondedInd;
		this.landingWeight = landingWeight;
		this.hazShip = hazShip;
		this.notifyOvrdName = notifyOvrdName;
		this.notifyOvrdExchange = notifyOvrdExchange;
		this.notifyOvrdBase = notifyOvrdBase;
		this.notifyOvrdArea = notifyOvrdArea;
		this.notifyOvrdExt = notifyOvrdExt;
		this.opSpell = opSpell;
		this.notifyReason = notifyReason;
		this.updateDtTm = updateDtTm;
		this.updateExtSchema = updateExtSchema;
		this.equipId = equipId;
		this.equipTp = equipTp;
		this.equipNbr = equipNbr;
		this.equipInit = equipInit;
		this.chasInit = chasInit;
		this.chasNbr = chasNbr;
		this.chasTp = chasTp;
		this.chasId = chasId;
		this.dmgInd = dmgInd;
		this.pickupNr = pickupNr;
		this.shipCust = shipCust;
		this.evtDesc = evtDesc;
		this.stationName = stationName;
		this.genericCodeUpdate = genericCodeUpdate;
	}

	public VoiceNotify(VoiceNotify2 voiceNotify2, Long notifyQueueId, Long notifyCustId, String customerName,
					   List<NotepadEntry> note, Long termId, Long svcId, String eventCd, String notifyStat, String personField,
					   String notifyMethod, Integer renotCount, String notifyName, String notifyArea, Integer notifyPrefix,
					   Integer notifySufix, Integer notifyExit, Long driverId, String trainName, Long drayCust,
					   LocalDateTime localDateTm, String emptyCd, String shipVesselName, String bookNr, String bondedInd,
					   Integer landingWeight, String hazShip, String notifyOvrdName, Integer notifyOvrdExchange,
					   Integer notifyOvrdBase, Integer notifyOvrdArea, Integer notifyOvrdExt, String opSpell, String notifyReason,
					   LocalDateTime updateDtTm, String updateExtSchema, String equipId, String equipTp, BigDecimal equipNbr,
					   String equipInit, String chasInit, BigDecimal chasNbr, String chasTp, String chasId, String dmgInd,
					   String pickupNr, String evtDesc, Station stationName, GenericCodeUpdate genericCodeUpdate) {
		super();
		this.voiceNotify2 = voiceNotify2;
		this.notifyQueueId = notifyQueueId;
		this.notifyCustId = notifyCustId;
		this.customerName = customerName;
		this.note = note;
		this.termId = termId;
		this.svcId = svcId;
		this.eventCd = eventCd;
		this.notifyStat = notifyStat;
		this.personField = personField;
		this.notifyMethod = notifyMethod;
		this.renotCount = renotCount;
		this.notifyName = notifyName;
		this.notifyArea = notifyArea;
		this.notifyPrefix = notifyPrefix;
		this.notifySufix = notifySufix;
		this.notifyExit = notifyExit;
		this.driverId = driverId;
		this.trainName = trainName;
		this.drayCust = drayCust;
		this.localDateTm = localDateTm;
		this.emptyCd = emptyCd;
		this.shipVesselName = shipVesselName;
		this.bookNr = bookNr;
		this.bondedInd = bondedInd;
		this.landingWeight = landingWeight;
		this.hazShip = hazShip;
		this.notifyOvrdName = notifyOvrdName;
		this.notifyOvrdExchange = notifyOvrdExchange;
		this.notifyOvrdBase = notifyOvrdBase;
		this.notifyOvrdArea = notifyOvrdArea;
		this.notifyOvrdExt = notifyOvrdExt;
		this.opSpell = opSpell;
		this.notifyReason = notifyReason;
		this.updateDtTm = updateDtTm;
		this.updateExtSchema = updateExtSchema;
		this.equipId = equipId;
		this.equipTp = equipTp;
		this.equipNbr = equipNbr;
		this.equipInit = equipInit;
		this.chasInit = chasInit;
		this.chasNbr = chasNbr;
		this.chasTp = chasTp;
		this.chasId = chasId;
		this.dmgInd = dmgInd;
		this.pickupNr = pickupNr;
		this.evtDesc = evtDesc;
		this.stationName = stationName;
		this.genericCodeUpdate = genericCodeUpdate;
	}

	public VoiceNotify() {
		super();
		// TODO Auto-generated constructor stub
	}

}