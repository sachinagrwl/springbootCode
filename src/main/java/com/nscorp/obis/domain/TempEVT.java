package com.nscorp.obis.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TMP_EVT")
public class TempEVT extends AuditInfo {

	@Id
	@Column(name = "EVT_LOG_ID", columnDefinition = "double(15)", nullable = false)
	private Long evtlogId; 

	@Column(name = "QUE_STAT", columnDefinition = "char(2)", nullable = true)
	private String queStat;
	
	@Column(name = "EQ_INIT", columnDefinition = "char(4)", nullable = true)
	private String equipInit;
	
	@Column(name = "EQ_NR", columnDefinition = "decimal(19)", nullable = true)
	private BigDecimal equipNbr;
	
	@Column(name = "EQ_TP", columnDefinition = "char(1)", nullable = true)
	private String equipTp;
	
	@Column(name = "EQ_ID", columnDefinition = "char(4)", nullable = true)
	private String equipId; 
	
	@Column(name = "L_E_IND", columnDefinition = "char(1)", nullable = true)
	private String leInd;
	
	@Column(name = "HAZ_IND", columnDefinition = "char(1)", nullable = true)
	private String hazInd;
	
	@Column(name = "EVT_CD", columnDefinition = "char(4)", nullable = true)
	private String evtCd;
	
	@Column(name = "EVT_DT_TM", columnDefinition = "timestamp", nullable = true)
	private Date evtdtTm;

	@Column(name = "CUST_ID", columnDefinition = "double(15)", nullable = true)
	private Long custId;
	
	@Column(name = "SVC_ID", columnDefinition = "double(15)", nullable = true)
	private Long svcId;
	
	@Column(name = "TERM_ID", columnDefinition = "double(15)", nullable = true)
	private Long termId; 
	
	@Column(name = "CHAS_INIT", columnDefinition = "char(4)", nullable = true)
	private String chasInit;
	
	@Column(name = "CHAS_NR", columnDefinition = "decimal(15)", nullable = true)
	private BigDecimal chasNr;
	
	@Column(name = "CHAS_TP", columnDefinition = "char(1)", nullable = true)
	private String chasTp;
	
	@Column(name = "CHAS_ID", columnDefinition = "char(4)", nullable = true)
	private String chasId;
	
	@Column(name = "LOT_AREA_ID", columnDefinition = "double(15)", nullable = true)
	private Long lotareaId;
	
	@Column(name = "LCL_DT_TM", columnDefinition = "TimeStamp", nullable = false)
	private Timestamp lccdtTm;
	
	@Column(name = "STD_DT_TM", columnDefinition = "Timestamp", nullable = false)
	private Timestamp stddtTm;

	@Column(name = "REASON_CD", columnDefinition = "char(4)", nullable = true)
	private String reasonCode;

	public Long getEvtlogId() {
		return evtlogId;
	}

	public void setEvtlogId(Long evtlogId) {
		this.evtlogId = evtlogId;
	}

	public String getQueStat() {
		return queStat;
	}

	public void setQueStat(String queStat) {
		this.queStat = queStat;
	}

	public String getEquipInit() {
		return equipInit;
	}

	public void setEquipInit(String eqInit) {
		this.equipInit = eqInit;
	}

	public BigDecimal getEquipNbr() {
		return equipNbr;
	}

	public void setEquipNbr(BigDecimal eqNr) {
		this.equipNbr = eqNr;
	}

	public String getEquipTp() {
		return equipTp;
	}

	public void setEquipTp(String eqTp) {
		this.equipTp = eqTp;
	}

	public String getEquipId() {
		return equipId;
	}

	public void setEquipId(String eqId) {
		this.equipId = eqId;
	}

	public String getLeInd() {
		return leInd;
	}

	public void setLeInd(String leInd) {
		this.leInd = leInd;
	}

	public String getHazInd() {
		return hazInd;
	}

	public void setHazInd(String hazInd) {
		this.hazInd = hazInd;
	}

	public String getEvtCd() {
		return evtCd;
	}

	public void setEvtCd(String evtCd) {
		this.evtCd = evtCd;
	}

	public Date getEvtdtTm() {
		return evtdtTm;
	}

	public void setEvtdtTm(Date evtdtTm) {
		this.evtdtTm = evtdtTm;
	}

	public Long getSvcId() {
		return svcId;
	}

	public void setSvcId(Long svcId) {
		this.svcId = svcId;
	}

	public Long getTermId() {
		return termId;
	}

	public void setTermId(Long termId) {
		this.termId = termId;
	}

	public String getChasInit() {
		return chasInit;
	}

	public void setChasInit(String chasInit) {
		this.chasInit = chasInit;
	}

	public BigDecimal getChasNr() {
		return chasNr;
	}

	public void setChasNr(BigDecimal chasNr) {
		this.chasNr = chasNr;
	}

	public String getChasTp() {
		return chasTp;
	}

	public void setChasTp(String chasTp) {
		this.chasTp = chasTp;
	}

	public String getChasId() {
		return chasId;
	}

	public void setChasId(String chasId) {
		this.chasId = chasId;
	}

	public Long getLotareaId() {
		return lotareaId;
	}

	public void setLotareaId(Long lotareaId) {
		this.lotareaId = lotareaId;
	}

	public Timestamp getLccdtTm() {
		return lccdtTm;
	}

	public void setLccdtTm(Timestamp lccdtTm) {
		this.lccdtTm = lccdtTm;
	}

	public Timestamp getStddtTm() {
		return stddtTm;
	}

	public void setStddtTm(Timestamp stddtTm) {
		this.stddtTm = stddtTm;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public TempEVT(Long evtlogId, String queStat, String equipInit, BigDecimal equipNbr, String equipTp, String equipId,
				   String leInd, String hazInd, String evtCd, Date evtdtTm, Long svcId, Long termId, String chasInit,
				   BigDecimal chasNr, String chasTp, String chasId, Long lotareaId, Timestamp lccdtTm,
				   Timestamp stddtTm) {
		super();
		this.evtlogId = evtlogId;
		this.queStat = queStat;
		this.equipInit = equipInit;
		this.equipNbr = equipNbr;
		this.equipTp = equipTp;
		this.equipId = equipId;
		this.leInd = leInd;
		this.hazInd = hazInd;
		this.evtCd = evtCd;
		this.evtdtTm = evtdtTm;
		this.svcId = svcId;
		this.termId = termId;
		this.chasInit = chasInit;
		this.chasNr = chasNr;
		this.chasTp = chasTp;
		this.chasId = chasId;
		this.lotareaId = lotareaId;
		this.lccdtTm = lccdtTm;
		this.stddtTm = stddtTm;
	}
	
	public TempEVT() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TempEVT(String uversion, String createUserId, Timestamp createDateTime, String updateUserId, Timestamp updateDateTime, String updateExtensionSchema, Long evtlogId, String queStat, String equipInit, BigDecimal equipNbr, String equipTp, String equipId, String leInd, String hazInd, String evtCd, Date evtdtTm, Long custId, Long svcId, Long termId, String chasInit, BigDecimal chasNr, String chasTp, String chasId, Long lotareaId, Timestamp lccdtTm, Timestamp stddtTm, String reasonCode) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.evtlogId = evtlogId;
		this.queStat = queStat;
		this.equipInit = equipInit;
		this.equipNbr = equipNbr;
		this.equipTp = equipTp;
		this.equipId = equipId;
		this.leInd = leInd;
		this.hazInd = hazInd;
		this.evtCd = evtCd;
		this.evtdtTm = evtdtTm;
		this.custId = custId;
		this.svcId = svcId;
		this.termId = termId;
		this.chasInit = chasInit;
		this.chasNr = chasNr;
		this.chasTp = chasTp;
		this.chasId = chasId;
		this.lotareaId = lotareaId;
		this.lccdtTm = lccdtTm;
		this.stddtTm = stddtTm;
		this.reasonCode = reasonCode;
	}

	@Override
	public String toString() {
		return "TempEVT{" +
				"evtlogId=" + evtlogId +
				", queStat='" + queStat + '\'' +
				", equipInit='" + equipInit + '\'' +
				", equipNbr=" + equipNbr +
				", equipTp='" + equipTp + '\'' +
				", equipId='" + equipId + '\'' +
				", leInd='" + leInd + '\'' +
				", hazInd='" + hazInd + '\'' +
				", evtCd='" + evtCd + '\'' +
				", evtdtTm=" + evtdtTm +
				", custId=" + custId +
				", svcId=" + svcId +
				", termId=" + termId +
				", chasInit='" + chasInit + '\'' +
				", chasNr=" + chasNr +
				", chasTp='" + chasTp + '\'' +
				", chasId='" + chasId + '\'' +
				", lotareaId=" + lotareaId +
				", lccdtTm=" + lccdtTm +
				", stddtTm=" + stddtTm +
				", reasonCode='" + reasonCode + '\'' +
				'}';
	}

	public TempEVT(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
				   Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}


}
