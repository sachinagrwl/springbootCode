package com.nscorp.obis.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "STORAGE_RATES")
public class StorageRates extends AuditInfo implements Serializable {

	@Id
	@Column(name = "STORAGE_ID", columnDefinition = "Double(8)", nullable = false)
	private Long storageId;

	@Column(name = "TERM_ID", columnDefinition = "Double(8)", nullable = false)
	private Long termId;

	@Column(name = "GATE_IND", columnDefinition = "char(1)", nullable = false)
	private String gateInd;

	@Column(name = "EQ_TP", columnDefinition = "char(1)", nullable = false)
	private String equipTp;

	@Column(name = "EQ_INIT", columnDefinition = "char(4)", nullable = false)
	private String equipInit;

	@Column(name = "EQ_LGTH", columnDefinition = "smallint(2)", nullable = false)
	private Integer equipLgth;

	@Column(name = "RT_TP", columnDefinition = "char(1)", nullable = false)
	private String rateTp;

	@Column(name = "FREE_DD_LMT", columnDefinition = "smallint(2)", nullable = false)
	private Integer freeDDLmt;
	
	@Column(name = "BOND_FREE_DD", columnDefinition = "smallint(2)", nullable = false)
	private Integer bondFreeDD;
	
	@Column(name = "ADDL_FREE_HR", columnDefinition = "smallint(2)", nullable = false)
	private Integer addlFreeHR;

	@Column(name = "RT1_DD_LMT", columnDefinition = "smallint(2)", nullable = false)
	private Integer rateDDLmt;
	
	@Column(name = "RT2_DD_LMT", columnDefinition = "smallint(2)", nullable = false)
	private Integer rate2DDLmt;

	@Column(name = "RT1_AMT", columnDefinition = "Decimal(19)", nullable = false)
	private BigDecimal rate1Amt;

	@Column(name = "RT2_AMT", columnDefinition = "Decimal(19)", nullable = false)
	private BigDecimal rate2Amt;
	
	@Column(name = "RT3_AMT", columnDefinition = "Decimal(19)", nullable = false)
	private BigDecimal rate3Amt;

	@Column(name = "CUST_NM", columnDefinition = "char(35)", nullable = false)
	private String customerNm;
	
	@Column(name = "CUST_PRIM_SIX", columnDefinition = "char(6)", nullable = false)
	private String customerPrimSix;

	@Column(name = "BNF_CUST_NM", columnDefinition = "char(35)", nullable = false)
	private String bnfCustomerNm;
	
	@Column(name = "BNF_PRIM_SIX", columnDefinition = "char(6)", nullable = false)
	private String bnfPrimSix;

	@Column(name = "SHIP_CUST_NM", columnDefinition = "char(35)", nullable = false)
	private String shipCustomerNm;
	
	@Column(name = "SHIP_PRIM_SIX", columnDefinition = "char(6)", nullable = false)
	private String shipPrimSix;

	@Column(name = "BILL_CUST_ID", columnDefinition = "Double(8)", nullable = false)
	private Long billCustId;

	@Column(name = "SCHD_DEL_RATE_IND", columnDefinition = "char(2)", nullable = false)
	private String schedDelRateInd;
	
	@Column(name = "SCHD_DEL_FAIL_EXTRA_DAY", columnDefinition = "integer(4)", nullable = false)
	private Integer schedDelFail;
	
	@Column(name = "SCHD_DEL_ALLOWANCE_HRS", columnDefinition = "integer(4)", nullable = false)
	private Integer schedDelAllowance;

	@Column(name = "PEAK_RT1_AMT", columnDefinition = "decimal(19)", nullable = false)
	private BigDecimal peakRt1Amt;
	
	@Column(name = "PEAK_RT2_AMT", columnDefinition = "Decimal(19)", nullable = false)
	private BigDecimal peakRt2Amt;
	
	@Column(name = "OFF_PEAK_RT1_AMT", columnDefinition = "decimal(19)", nullable = false)
	private BigDecimal offPeakRt1Amt;
	
	@Column(name = "OFF_PEAK_RT2_AMT", columnDefinition = "Decimal(19)", nullable = false)
	private BigDecimal offPeakRate2Amt;

	@Column(name = "PEAK_BGN_TM", columnDefinition = "Time(3)", nullable = false)
	private Timestamp peakBgnDtTm;

	@Column(name = "PEAK_END_TM", columnDefinition = "Time(3)", nullable = false)
	private Timestamp peakEndDtTm;
	
	@Column(name = "CNT_WEEKEND", columnDefinition = "char(1)", nullable = false)
	private String cntWeekend;

	@Column(name = "EFF_DT", columnDefinition = "Date(4)", nullable = false)
	private LocalDate effectiveDate;

	@Column(name = "END_DT", columnDefinition = "Date(4)", nullable = false)
	private LocalDate endDate;

	@Column(name = "CNT_AFTERNOON", columnDefinition = "char(1)", nullable = false)
	private String cntAfternoon;
	
	@Column(name = "PM_NOPA_START_TM", columnDefinition = "smallint(2)", nullable = false)
	private Integer pmNopaStartTm;
	
	@Column(name = "NOTEPAD_TXT", columnDefinition = "char(255)", nullable = false)
	private String notepadTxt;
	
	@Column(name = "RR_IND", columnDefinition = "char(1)", nullable = false)
	private String rrInd;
	
	@Column(name = "CNT_SATURDAY", columnDefinition = "char(1)", nullable = false)
	private String cntSaturday;
	
	@Column(name = "CNT_SUNDAY", columnDefinition = "char(1)", nullable = false)
	private String cntSunday;
	
	@Column(name = "LCL_INTER_IND", columnDefinition = "char(2)", nullable = false)
	private String lclInterInd;
	
	@Column(name = "LD_EMPTY_CD", columnDefinition = "char(1)", nullable = false)
	private String ldEmptyCd;

	@Transient
	private String lastUpdated;
	
	@Transient
	private Terminal terminal;

	@Transient
	private CustomerCharge customerCharge;


	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Terminal getTerminal() {
		return terminal;
	}

	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}

	public CustomerCharge getCustomerCharge() {
		return customerCharge;
	}

	public void setCustomerCharge(CustomerCharge customerCharge) {
		this.customerCharge = customerCharge;
	}

	public Long getStorageId() {
		return storageId;
	}

	public void setStorageId(Long storageId) {
		this.storageId = storageId;
	}

	public Long getTermId() {
		return termId;
	}

	public void setTermId(Long termId) {
		this.termId = termId;
	}

	public String getGateInd() {
		return gateInd;
	}

	public void setGateInd(String gateInd) {
		this.gateInd = gateInd;
	}

	public String getEquipTp() {
		return equipTp;
	}

	public void setEquipTp(String equipTp) {
		this.equipTp = equipTp;
	}
	
	

	public String getEquipInit() {
		return equipInit;
	}

	public void setEquipInit(String equipInit) {
		this.equipInit = equipInit;
	}

	public Integer getEquipLgth() {
//		if(equipLgth !=null) {
//			return equipLgth / 12;
//		}
		return equipLgth;
	}

	public void setEquipLgth(Integer equipLgth) {
		this.equipLgth = equipLgth;
	}

	public String getRateTp() {
		return rateTp;
	}

	public void setRateTp(String rateTp) {
		this.rateTp = rateTp;
	}

	public Integer getFreeDDLmt() {
		return freeDDLmt;
	}

	public void setFreeDDLmt(Integer freeDDLmt) {
		this.freeDDLmt = freeDDLmt;
	}

	public Integer getRateDDLmt() {
		return rateDDLmt;
	}

	public void setRateDDLmt(Integer rateDDLmt) {
		this.rateDDLmt = rateDDLmt;
	}

	public BigDecimal getRate1Amt() {
		return rate1Amt;
	}

	public void setRate1Amt(BigDecimal rate1Amt) {
		this.rate1Amt = rate1Amt;
	}

	public BigDecimal getRate2Amt() {
		return rate2Amt;
	}

	public void setRate2Amt(BigDecimal rate2Amt) {
		this.rate2Amt = rate2Amt;
	}

	public String getCustomerNm() {
		return customerNm != null ? customerNm.trim() : customerNm;
	}

	public void setCustomerNm(String customerNm) {
		this.customerNm = customerNm;
	}

	public String getBnfCustomerNm() {
		return bnfCustomerNm != null ? bnfCustomerNm.trim() : bnfCustomerNm;
	}

	public void setBnfCustomerNm(String bnfCustomerNm) {
		this.bnfCustomerNm = bnfCustomerNm;
	}

	public String getShipCustomerNm() {
		return shipCustomerNm != null ? shipCustomerNm.trim() : shipCustomerNm;
	}

	public void setShipCustomerNm(String shipCustomerNm) {
		this.shipCustomerNm = shipCustomerNm;
	}

	public Long getBillCustId() {
		return billCustId;
	}

	public void setBillCustId(Long billCustId) {
		this.billCustId = billCustId;
	}

	public String getSchedDelRateInd() {
		return schedDelRateInd;
	}

	public void setSchedDelRateInd(String schedDelRateInd) {
		this.schedDelRateInd = schedDelRateInd;
	}

	public BigDecimal getPeakRt1Amt() {
		return peakRt1Amt;
	}

	public void setPeakRt1Amt(BigDecimal peakRt1Amt) {
		this.peakRt1Amt = peakRt1Amt;
	}

	public Timestamp getPeakBgnDtTm() {
		return peakBgnDtTm;
	}

	public void setPeakBgnDtTm(Timestamp peakBgnDtTm) {
		this.peakBgnDtTm = peakBgnDtTm;
	}

	public Timestamp getPeakEndDtTm() {
		return peakEndDtTm;
	}

	public void setPeakEndDtTm(Timestamp peakEndDtTm) {
		this.peakEndDtTm = peakEndDtTm;
	}

	public BigDecimal getPeakRt2Amt() {
		return peakRt2Amt;
	}

	public void setPeakRt2Amt(BigDecimal peakRate2Amt) {
		this.peakRt2Amt = peakRate2Amt;
	}

	public Integer getBondFreeDD() {
		return bondFreeDD;
	}

	public void setBondFreeDD(Integer bondFreeDD) {
		this.bondFreeDD = bondFreeDD;
	}

	public Integer getAddlFreeHR() {
		return addlFreeHR;
	}

	public void setAddlFreeHR(Integer addlFreeHR) {
		this.addlFreeHR = addlFreeHR;
	}

	public Integer getRate2DDLmt() {
		return rate2DDLmt;
	}

	public void setRate2DDLmt(Integer rate2ddLmt) {
		rate2DDLmt = rate2ddLmt;
	}

	public BigDecimal getRate3Amt() {
		return rate3Amt;
	}

	public void setRate3Amt(BigDecimal rate3Amt) {
		this.rate3Amt = rate3Amt;
	}

	public String getCustomerPrimSix() {
		return customerPrimSix;
	}

	public void setCustomerPrimSix(String customerPrimSix) {
		this.customerPrimSix = customerPrimSix;
	}

	public String getBnfPrimSix() {
		return bnfPrimSix;
	}

	public void setBnfPrimSix(String bnfPrimSix) {
		this.bnfPrimSix = bnfPrimSix;
	}

	public String getShipPrimSix() {
		return shipPrimSix;
	}

	public void setShipPrimSix(String shipPrimSix) {
		this.shipPrimSix = shipPrimSix;
	}

	public Integer getSchedDelFail() {
		return schedDelFail;
	}

	public void setSchedDelFail(Integer schedDelFail) {
		this.schedDelFail = schedDelFail;
	}

	public Integer getSchedDelAllowance() {
		return schedDelAllowance;
	}

	public void setSchedDelAllowance(Integer schedDelAllowance) {
		this.schedDelAllowance = schedDelAllowance;
	}

	public BigDecimal getOffPeakRt1Amt() {
		return offPeakRt1Amt;
	}

	public void setOffPeakRt1Amt(BigDecimal offPeakRt1Amt) {
		this.offPeakRt1Amt = offPeakRt1Amt;
	}

	public BigDecimal getOffPeakRate2Amt() {
		return offPeakRate2Amt;
	}

	public void setOffPeakRate2Amt(BigDecimal offPeakRate2Amt) {
		this.offPeakRate2Amt = offPeakRate2Amt;
	}

	public String getCntWeekend() {
		return cntWeekend;
	}

	public void setCntWeekend(String cntWeekend) {
		this.cntWeekend = cntWeekend;
	}

	public LocalDate getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(LocalDate effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getCntAfternoon() {
		return cntAfternoon;
	}

	public void setCntAfternoon(String cntAfternoon) {
		this.cntAfternoon = cntAfternoon;
	}

	public Integer getPmNopaStartTm() {
		return pmNopaStartTm;
	}

	public void setPmNopaStartTm(Integer pmNopaStartTm) {
		this.pmNopaStartTm = pmNopaStartTm;
	}

	public String getNotepadTxt() {
		return notepadTxt;
	}

	public void setNotepadTxt(String notepadTxt) {
		this.notepadTxt = notepadTxt;
	}

	public String getRrInd() {
		return rrInd;
	}

	public void setRrInd(String rrInd) {
		this.rrInd = rrInd;
	}

	public String getCntSaturday() {
		return cntSaturday;
	}

	public void setCntSaturday(String cntSaturday) {
		this.cntSaturday = cntSaturday;
	}

	public String getCntSunday() {
		return cntSunday;
	}

	public void setCntSunday(String cntSunday) {
		this.cntSunday = cntSunday;
	}

	public String getLclInterInd() {
		return lclInterInd;
	}

	public void setLclInterInd(String lclInterInd) {
		this.lclInterInd = lclInterInd;
	}

	public String getLdEmptyCd() {
		return ldEmptyCd;
	}

	public void setLdEmptyCd(String ldEmptyCd) {
		this.ldEmptyCd = ldEmptyCd;
	}

	public StorageRates(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Long storageId, Long termId, String gateInd,
			String equipTp, String equipInit, Integer equipLgth, String rateTp, Integer freeDDLmt, Integer bondFreeDD,
			Integer addlFreeHR, Integer rateDDLmt, Integer rate2ddLmt, BigDecimal rate1Amt, BigDecimal rate2Amt,
			BigDecimal rate3Amt, String customerNm, String customerPrimSix, String bnfCustomerNm, String bnfPrimSix,
			String shipCustomerNm, String shipPrimSix, Long billCustId, String schedDelRateInd, Integer schedDelFail,
			Integer schedDelAllowance, BigDecimal peakRt1Amt, BigDecimal peakRate2Amt, BigDecimal offPeakRt1Amt,
			BigDecimal offPeakRate2Amt, Timestamp peakBgnDtTm, Timestamp peakEndDtTm, String cntWeekend,
						LocalDate effectiveDate, LocalDate endDate, String cntAfternoon, Integer pmNopaStartTm,
			String notepadTxt, String rrInd, String cntSaturday, String cntSunday,String lclInterInd, String ldEmptyCd,
			Terminal terminal, CustomerCharge customerCharge, String lastUpdated) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.storageId = storageId;
		this.termId = termId;
		this.gateInd = gateInd;
		this.equipTp = equipTp;
		this.equipInit = equipInit;
		this.equipLgth = equipLgth;
		this.rateTp = rateTp;
		this.freeDDLmt = freeDDLmt;
		this.bondFreeDD = bondFreeDD;
		this.addlFreeHR = addlFreeHR;
		this.rateDDLmt = rateDDLmt;
		this.rate2DDLmt = rate2ddLmt;
		this.rate1Amt = rate1Amt;
		this.rate2Amt = rate2Amt;
		this.rate3Amt = rate3Amt;
		this.customerNm = customerNm;
		this.customerPrimSix = customerPrimSix;
		this.bnfCustomerNm = bnfCustomerNm;
		this.bnfPrimSix = bnfPrimSix;
		this.shipCustomerNm = shipCustomerNm;
		this.shipPrimSix = shipPrimSix;
		this.billCustId = billCustId;
		this.schedDelRateInd = schedDelRateInd;
		this.schedDelFail = schedDelFail;
		this.schedDelAllowance = schedDelAllowance;
		this.peakRt1Amt = peakRt1Amt;
		this.peakRt2Amt = peakRate2Amt;
		this.offPeakRt1Amt = offPeakRt1Amt;
		this.offPeakRate2Amt = offPeakRate2Amt;
		this.peakBgnDtTm = peakBgnDtTm;
		this.peakEndDtTm = peakEndDtTm;
		this.cntWeekend = cntWeekend;
		this.effectiveDate = effectiveDate;
		this.endDate = endDate;
		this.cntAfternoon = cntAfternoon;
		this.pmNopaStartTm = pmNopaStartTm;
		this.notepadTxt = notepadTxt;
		this.rrInd = rrInd;
		this.cntSaturday = cntSaturday;
		this.cntSunday = cntSunday;
		this.lclInterInd = lclInterInd;
		this.ldEmptyCd = ldEmptyCd;
		this.terminal = terminal;
		this.customerCharge =customerCharge;
		this.lastUpdated=lastUpdated;
		
	}

	public StorageRates() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StorageRates(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}

}
