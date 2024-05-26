package com.nscorp.obis.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "CUST_CHRG")
public class CustomerCharge extends AuditInfo implements Serializable {

	@Id
	@Column(name = "CHRG_ID", columnDefinition = "Double(15)", nullable = false)
	private Long chrgId;

	@Column(name = "EQ_INIT", columnDefinition = "char(4)", nullable = false)
	private String equipInit;

	@Column(name = "EQ_NR", columnDefinition = "decimal", nullable = false)
	private Integer equipNbr;

	@Column(name = "EQ_TP", columnDefinition = "char(1)", nullable = false)
	private String equipType;

	@Column(name = "EQ_ID", columnDefinition = "char(4)", nullable = false)
	private String equipId;

	@Column(name = "BILL_CUST_ID", columnDefinition = "Double(8)", nullable = false)
	private Long billCustId;

	@Column(name = "CHRG_TP", columnDefinition = "char(3)", nullable = false)
	private String chrgTp;

	@Column(name = "RT_TP", columnDefinition = "char(1)", nullable = false)
	private String rateType;

	@Column(name = "RATE_ID", columnDefinition = "Double(8)", nullable = false)
	private Long rateId;

	@Column(name = "CHRG_CD", columnDefinition = "char(3)", nullable = false)
	private String chrgCd;

	@Column(name = "CHRG_AMT", columnDefinition = "Decimal(3)", nullable = false)
	private BigDecimal chrgAmt;

	@Column(name = "CHRG_BASE_DAYS", columnDefinition = "smallInt(2)", nullable = false)
	private Integer chrgBaseDays;

	@Column(name = "CHRG_DAYS", columnDefinition = "smallInt(2)", nullable = false)
	private Integer chrgDays;

	@Column(name = "BGN_EVT_CD", columnDefinition = "char(4)", nullable = false)
	private String bgnEvtCd;

	@Column(name = "BGN_LCL_DT_TM", columnDefinition = "Time(10)", nullable = false)
	private Timestamp bgnLclDtTm;

	@Column(name = "END_LCL_DT_TM", columnDefinition = "Time(10)", nullable = false)
	private Timestamp endLclDtTm;

	@Column(name = "SVC_ID", columnDefinition = "Double(15)", nullable = false)
	private Long svcId;

	@Column(name = "BGN_L_E_IND", columnDefinition = "char(1)", nullable = false)
	private String bgnLEInd;

	@Column(name = "END_EVT_CD", columnDefinition = "char(4)", nullable = false)
	private String endEvtCd;

	@Column(name = "END_L_E_IND", columnDefinition = "char(1)", nullable = false)
	private String endLEInd;

	@Column(name = "LAST_FREE_DT_TM", columnDefinition = "TimeStamp(10)", nullable = false)
	private Date lastFreeDtTm;

	@Column(name = "OVERRIDE_NM", columnDefinition = "char(40)", nullable = false)
	private String overrideNm;

	@Column(name = "OVERRIDE_USER_ID", columnDefinition = "char(8)", nullable = false)
	private String overrideUserId;

	@Column(name = "OVERRIDE_TITLE", columnDefinition = "char(20)", nullable = false)
	private String overrideTitle;

	@Column(name = "LOCALLY_BILLED_IND", columnDefinition = "char(1)", nullable = false)
	private String locallyBilledInd;

	@Column(name = "BILL_RELEASE_DT", columnDefinition = "Date(4)", nullable = false)
	private Date billReleaseDate;

	@Column(name = "BONDED_IND", columnDefinition = "char(1)", nullable = false)
	private String bondedInd;

	@Column(name = "PEAK_RT_IND", columnDefinition = "char(1)", nullable = false)
	private String peakRtInd;

	@Column(name = "BGN_TERM_ID", columnDefinition = "Double(8)", nullable = false)
	private Long bgnTermId;

	@Column(name = "END_TERM_ID", columnDefinition = "Double(8)", nullable = false)
	private Long endTermId;

	@Column(name = "CASH_EXCP_ID", columnDefinition = "Double(8)", nullable = false)
	private Long cashExcpId;

	@Transient
	private String chrgTypeCode;

	@Transient
	private String rateTypeCode;

	@Transient
	private BigDecimal wbSerNr;

	public BigDecimal getWbSerNr() {
		return wbSerNr;
	}

	public void setWbSerNr(BigDecimal wbSerNr) {
		this.wbSerNr = wbSerNr;
	}

	public String getChrgTypeCode() {
		return chrgTypeCode;
	}

	public void setChrgTypeCode(String chrgTypeCode) {
		this.chrgTypeCode = chrgTypeCode;
	}

	public String getRateTypeCode() {
		return rateTypeCode;
	}

	public void setRateTypeCode(String rateTypeCode) {
		this.rateTypeCode = rateTypeCode;
	}

	public Long getChrgId() {
		return chrgId;
	}

	public void setChrgId(Long chrgId) {
		this.chrgId = chrgId;
	}

	public String getEquipInit() {
		return equipInit;
	}

	public void setEquipInit(String equipInit) {
		this.equipInit = equipInit;
	}

	public Integer getEquipNbr() {
		return equipNbr;
	}

	public void setEquipNbr(Integer equipNbr) {
		this.equipNbr = equipNbr;
	}

	public String getEquipType() {
		return equipType;
	}

	public void setEquipType(String equipType) {
		this.equipType = equipType;
	}

	public String getEquipId() {
		return equipId;
	}

	public void setEquipId(String equipId) {
		this.equipId = equipId;
	}

	public Long getBillCustId() {
		return billCustId;
	}

	public void setBillCustId(Long billCustId) {
		this.billCustId = billCustId;
	}

	public String getChrgTp() {
		return chrgTp;
	}

	public void setChrgTp(String chrgTp) {
		this.chrgTp = chrgTp;
	}

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public Long getRateId() {
		return rateId;
	}

	public void setRateId(Long rateId) {
		this.rateId = rateId;
	}

	public String getChrgCd() {
		return chrgCd;
	}

	public void setChrgCd(String chrgCd) {
		this.chrgCd = chrgCd;
	}

	public BigDecimal getChrgAmt() {
		return chrgAmt;
	}

	public void setChrgAmt(BigDecimal chrgAmt) {
		this.chrgAmt = chrgAmt;
	}

	public Integer getChrgBaseDays() {
		return chrgBaseDays;
	}

	public void setChrgBaseDays(Integer chrgBaseDays) {
		this.chrgBaseDays = chrgBaseDays;
	}

	public Integer getChrgDays() {
		return chrgDays;
	}

	public void setChrgDays(Integer chrgDays) {
		this.chrgDays = chrgDays;
	}

	public String getBgnEvtCd() {
		return bgnEvtCd;
	}

	public void setBgnEvtCd(String bgnEvtCd) {
		this.bgnEvtCd = bgnEvtCd;
	}

	public Timestamp getBgnLclDtTm() {
		return bgnLclDtTm;
	}

	public void setBgnLclDtTm(Timestamp bgnLclDtTm) {
		this.bgnLclDtTm = bgnLclDtTm;
	}

	public Timestamp getEndLclDtTm() {
		return endLclDtTm;
	}

	public void setEndLclDtTm(Timestamp endLclDtTm) {
		this.endLclDtTm = endLclDtTm;
	}

	public Long getSvcId() {
		return svcId;
	}

	public void setSvcId(Long svcId) {
		this.svcId = svcId;
	}

	public String getBgnLEInd() {
		return bgnLEInd;
	}

	public void setBgnLEInd(String bgnLEInd) {
		this.bgnLEInd = bgnLEInd;
	}

	public String getEndEvtCd() {
		return endEvtCd;
	}

	public void setEndEvtCd(String endEvtCd) {
		this.endEvtCd = endEvtCd;
	}

	public String getEndLEInd() {
		return endLEInd;
	}

	public void setEndLEInd(String endLEInd) {
		this.endLEInd = endLEInd;
	}

	public Date getLastFreeDtTm() {
		return lastFreeDtTm;
	}

	public void setLastFreeDtTm(Date lastFreeDtTm) {
		this.lastFreeDtTm = lastFreeDtTm;
	}

	public String getOverrideNm() {
		if (overrideNm != null) {
			return overrideNm.trim();
		} else {
			return overrideNm;
		}
	}

	public void setOverrideNm(String overrideNm) {
		this.overrideNm = overrideNm;
	}

	public String getLocallyBilledInd() {
		return locallyBilledInd;
	}

	public void setLocallyBilledInd(String locallyBilledInd) {
		this.locallyBilledInd = locallyBilledInd;
	}

	public Date getBillReleaseDate() {
		return billReleaseDate;
	}

	public void setBillReleaseDate(Date billReleaseDate) {
		this.billReleaseDate = billReleaseDate;
	}

	public String getBondedInd() {
		return bondedInd;
	}

	public void setBondedInd(String bondedInd) {
		this.bondedInd = bondedInd;
	}

	public String getPeakRtInd() {
		return peakRtInd;
	}

	public void setPeakRtInd(String peakRtInd) {
		this.peakRtInd = peakRtInd;
	}

	public Long getBgnTermId() {
		return bgnTermId;
	}

	public void setBgnTermId(Long bgnTermId) {
		this.bgnTermId = bgnTermId;
	}

	public Long getEndTermId() {
		return endTermId;
	}

	public void setEndTermId(Long endTermId) {
		this.endTermId = endTermId;
	}

	public String getOverrideUserId() {
		if (overrideUserId != null) {
			return overrideUserId.trim();
		} else {
			return overrideUserId;
		}
	}

	public void setOverrideUserId(String overrideUserId) {
		this.overrideUserId = overrideUserId;
	}

	public String getOverrideTitle() {
		if (overrideTitle != null) {
			return overrideTitle.trim();
		} else {
			return overrideTitle;
		}
	}

	public void setOverrideTitle(String overrideTitle) {
		this.overrideTitle = overrideTitle;
	}

	public Long getCashExcpId() {
		return cashExcpId;
	}

	public void setCashExcpId(Long cashExcpId) {
		this.cashExcpId = cashExcpId;
	}

	public CustomerCharge(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Long chrgId, String equipInit, Integer equipNbr,
			String equipType, String equipId, Long billCustId, String chrgTp, String rateType, Long rateId,
			String chrgCd, BigDecimal chrgAmt, Integer chrgBaseDays, Integer chrgDays, String bgnEvtCd,
			Timestamp bgnLclDtTm, Timestamp endLclDtTm, Long svcId, Long bgnTermId, Long endTermId, Long cashExcpId,
			String bgnLEInd, String endEvtCd, String endLEInd, Date lastFreeDtTm, String overrideNm,
			String overrideUserId, String overrideTitle, String locallyBilledInd, Date billReleaseDate,
			String bondedInd, String peakRtInd, BigDecimal wbSerNr) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.chrgId = chrgId;
		this.equipInit = equipInit;
		this.equipNbr = equipNbr;
		this.equipType = equipType;
		this.equipId = equipId;
		this.billCustId = billCustId;
		this.chrgTp = chrgTp;
		this.rateType = rateType;
		this.rateId = rateId;
		this.chrgCd = chrgCd;
		this.chrgAmt = chrgAmt;
		this.chrgBaseDays = chrgBaseDays;
		this.chrgDays = chrgDays;
		this.bgnEvtCd = bgnEvtCd;
		this.bgnLclDtTm = bgnLclDtTm;
		this.endLclDtTm = endLclDtTm;
		this.svcId = svcId;
		this.bgnLEInd = bgnLEInd;
		this.endEvtCd = endEvtCd;
		this.endLEInd = endLEInd;
		this.lastFreeDtTm = lastFreeDtTm;
		this.overrideNm = overrideNm;
		this.overrideTitle = overrideTitle;
		this.overrideUserId = overrideUserId;
		this.locallyBilledInd = locallyBilledInd;
		this.billReleaseDate = billReleaseDate;
		this.bondedInd = bondedInd;
		this.peakRtInd = peakRtInd;
		this.bgnTermId = bgnTermId;
		this.endTermId = endTermId;
		this.cashExcpId = cashExcpId;
		this.wbSerNr = wbSerNr;
	}

	public CustomerCharge() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomerCharge(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}

}
