package com.nscorp.obis.dto;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.domain.AuditInfo;
import com.nscorp.obis.domain.CustomerCharge;
import com.nscorp.obis.domain.Terminal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = false)
public class StorageRatesDTO extends AuditInfo {

	private Long storageId;
	private Long termId;
	private String gateInd;
	private String equipTp;
	private String equipInit;

	@Schema(required = false, description = "The length of the equipment being used.", example = "45")
	@Digits(integer = 5, fraction = 0, message = "Equipment Length cannot have more than 5 digits")
	@Min(value = 1, message = "Equipment Length must be greater than 0")
	private Integer equipLgth;
	private String rateTp;

	private Integer freeDDLmt;
	private Integer bondFreeDD;
	private Integer addlFreeHR;
	private Integer rateDDLmt;
	private Integer rate2DDLmt;
	private BigDecimal rate1Amt;
	private BigDecimal rate2Amt;
	private BigDecimal rate3Amt;
	private String customerNm;
	private String customerPrimSix;

	private String bnfCustomerNm;
	private String bnfPrimSix;
	private String shipCustomerNm;
	private String shipPrimSix;
	private Long billCustId;
	private String schedDelRateInd;
	private Integer schedDelFail;
	private Integer schedDelAllowance;
	private BigDecimal peakRt1Amt;
	private BigDecimal peakRt2Amt;
	private BigDecimal offPeakRt1Amt;
	private BigDecimal offPeakRate2Amt;

	private Timestamp peakBgnDtTm;
	private Timestamp peakEndDtTm;

	private String cntWeekend;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
	@Schema(required = true, description = "Effactive Date")
	private LocalDate effectiveDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
	@Schema(required = false, description = "")
	private LocalDate endDate;

	private String cntAfternoon;
	private Integer pmNopaStartTm;

	@Schema(required = false, description = "Notepad text being used.", example = "")
	@Size(max = CommonConstants.NOTEPAD_ENTRY_TEXT_MAX_SIZE, message = "Notepad text size should not be more than {max}")
	private String notepadTxt;

	private String rrInd;
	private String cntSaturday;
	private String cntSunday;
	private String lclInterInd;
	private String ldEmptyCd;

	private String lastUpdated;
	private Terminal terminal;
	private CustomerCharge customerCharge;

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

	public Integer getRateDDLmt() {
		return rateDDLmt;
	}

	public void setRateDDLmt(Integer rateDDLmt) {
		this.rateDDLmt = rateDDLmt;
	}

	public Integer getRate2DDLmt() {
		return rate2DDLmt;
	}

	public void setRate2DDLmt(Integer rate2DDLmt) {
		this.rate2DDLmt = rate2DDLmt;
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

	public BigDecimal getRate3Amt() {
		return rate3Amt;
	}

	public void setRate3Amt(BigDecimal rate3Amt) {
		this.rate3Amt = rate3Amt;
	}

	public String getCustomerNm() {
		return customerNm;
	}

	public void setCustomerNm(String customerNm) {
		this.customerNm = customerNm;
	}

	public String getCustomerPrimSix() {
		return customerPrimSix;
	}

	public void setCustomerPrimSix(String customerPrimSix) {
		this.customerPrimSix = customerPrimSix;
	}

	public String getBnfCustomerNm() {
		return bnfCustomerNm;
	}

	public void setBnfCustomerNm(String bnfCustomerNm) {
		this.bnfCustomerNm = bnfCustomerNm;
	}

	public String getBnfPrimSix() {
		return bnfPrimSix;
	}

	public void setBnfPrimSix(String bnfPrimSix) {
		this.bnfPrimSix = bnfPrimSix;
	}

	public String getShipCustomerNm() {
		return shipCustomerNm;
	}

	public void setShipCustomerNm(String shipCustomerNm) {
		this.shipCustomerNm = shipCustomerNm;
	}

	public String getShipPrimSix() {
		return shipPrimSix;
	}

	public void setShipPrimSix(String shipPrimSix) {
		this.shipPrimSix = shipPrimSix;
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

	public BigDecimal getPeakRt1Amt() {
		return peakRt1Amt;
	}

	public void setPeakRt1Amt(BigDecimal peakRt1Amt) {
		this.peakRt1Amt = peakRt1Amt;
	}

	public BigDecimal getPeakRt2Amt() {
		return peakRt2Amt;
	}

	public void setPeakRt2Amt(BigDecimal peakRt2Amt) {
		this.peakRt2Amt = peakRt2Amt;
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
		if(notepadTxt!=null){
			notepadTxt = notepadTxt.toUpperCase();
		}
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


	public void setTermIds(List<Long> termIds) {
	}


	public void setEquipInits(List<String> equipInits) {
	}


	public void setLclInterInds(List<String> lclInterInds) {
	}

	public void setLdEmptyCds(List<String> ldEmptyCds) {
	}

	@Override
	public String toString() {
		return "StorageRatesDTO [storageId=" + storageId + ", termId=" + termId + ", gateInd=" + gateInd + ", equipTp="
				+ equipTp + ", equipInit=" + equipInit + ", equipLgth=" + equipLgth + ", rateTp=" + rateTp
				+ ", freeDDLmt=" + freeDDLmt + ", bondFreeDD=" + bondFreeDD + ", addlFreeHR=" + addlFreeHR
				+ ", rateDDLmt=" + rateDDLmt + ", rate2DDLmt=" + rate2DDLmt + ", rate1Amt=" + rate1Amt + ", rate2Amt="
				+ rate2Amt + ", rate3Amt=" + rate3Amt + ", customerNm=" + customerNm + ", customerPrimSix="
				+ customerPrimSix + ", bnfCustomerNm=" + bnfCustomerNm + ", bnfPrimSix=" + bnfPrimSix
				+ ", shipCustomerNm=" + shipCustomerNm + ", shipPrimSix=" + shipPrimSix + ", billCustId=" + billCustId
				+ ", schedDelRateInd=" + schedDelRateInd + ", schedDelFail=" + schedDelFail + ", schedDelAllowance="
				+ schedDelAllowance + ", peakRt1Amt=" + peakRt1Amt + ", peakRt2Amt=" + peakRt2Amt + ", offPeakRt1Amt="
				+ offPeakRt1Amt + ", offPeakRate2Amt=" + offPeakRate2Amt + ", peakBgnDtTm=" + peakBgnDtTm
				+ ", peakEndDtTm=" + peakEndDtTm + ", cntWeekend=" + cntWeekend + ", effectiveDate=" + effectiveDate
				+ ", endDate=" + endDate + ", cntAfternoon=" + cntAfternoon + ", pmNopaStartTm=" + pmNopaStartTm
				+ ", notepadTxt=" + notepadTxt + ", rrInd=" + rrInd + ", cntSaturday=" + cntSaturday + ", cntSunday="
				+ cntSunday + ", lclInterInd=" + lclInterInd + ", ldEmptyCd=" + ldEmptyCd + ", lastUpdated="
				+ lastUpdated + ", terminal=" + terminal + ", customerCharge=" + customerCharge + ", getUversion()="
				+ getUversion() + ", getCreateUserId()=" + getCreateUserId() + ", getCreateDateTime()="
				+ getCreateDateTime() + ", getUpdateUserId()=" + getUpdateUserId() + ", getUpdateDateTime()="
				+ getUpdateDateTime() + ", getUpdateExtensionSchema()=" + getUpdateExtensionSchema() + "]";
	}
	
	
}
