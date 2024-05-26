package com.nscorp.obis.dto;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.NullOrNotBlank;
import com.nscorp.obis.domain.AuditInfo;
import com.nscorp.obis.domain.CustomerCharge;
import com.nscorp.obis.domain.Terminal;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.UniqueElements;

@EqualsAndHashCode(callSuper = false)
public class StorageRatesListDTO extends AuditInfo {

	private Long storageId;
	@UniqueElements(message = "termIds must have unique values")
	private List<Long> termIds;
	@NotBlank(message = "Either ingate, outgate or oncar must be specified.")
	@Pattern(regexp = "^[IOC]{1}", message = "gateInd should be either I, O or C !")
	private String gateInd;
	@NotBlank(message = "An equipment type must be specified either trailer, container or chassis.")
	@Pattern(regexp = "^[CTZ]{1}", message = "equipTp should be either C, T or Z !")
	private String equipTp;
	@UniqueElements(message = "equipInits must have unique values")
	private List<@Size(min = 1, max = 4, message = "equipInit length should be 1 and 4") String> equipInits;
	private Integer equipLgth;
	@Pattern(regexp = "^[DMH]{1}", message = "rate type should be either D, M or H !")
	private String rateTp;

	@Min(value=0, message="freeDDLmt should be positive value")
	@Max(value=127, message="freeDDLmt can't be greater than 127")
	private Integer freeDDLmt;
	private Integer bondFreeDD;
	private Integer addlFreeHR;
	@Min(value=0, message="rateDDLmt should be positive value")
	@Max(value=127, message="rateDDLmt can't be greater than 127")
	private Integer rateDDLmt;
	@Min(value=0, message="rate2DDLmt should be positive value")
	@Max(value=127, message="rate2DDLmt can't be greater than 127")
	private Integer rate2DDLmt;
	@Digits(integer = 17, fraction = 2, message="rate1Amt digits cannot be more than 19 and scale cannot be more than 2")
	@Min(value=0, message="rate1Amt should be positive value")
	private BigDecimal rate1Amt;
	@Digits(integer = 17, fraction = 2, message="rate2Amt digits cannot be more than 19 and scale cannot be more than 2")
	@Min(value=0, message="rate2Amt should be positive value")
	private BigDecimal rate2Amt;
	@Digits(integer = 17, fraction = 2, message="rate3Amt digits cannot be more than 19 and scale cannot be more than 2")
	@Min(value=0, message="rate3Amt should be positive value")
	private BigDecimal rate3Amt;
	@NullOrNotBlank(min = CommonConstants.CUST_NM_MIN_SIZE, max = CommonConstants.CUST_NM_MAX_SIZE, message = "customerName field length should be between 1 and 35.")
	private String customerNm;
	@NullOrNotBlank(min = 6, max = 6, message = "customerPrimSix length should be equal to 6")
	private String customerPrimSix;

	@NullOrNotBlank(min = CommonConstants.CUST_NM_MIN_SIZE, max = CommonConstants.CUST_NM_MAX_SIZE, message = "bnfcustomerName field length should be between 1 and 35.")
	private String bnfCustomerNm;
	@NullOrNotBlank(min = 6, max = 6, message = "bnfPrimSix length should be equal to 6")
	private String bnfPrimSix;
	@NullOrNotBlank(min = CommonConstants.CUST_NM_MIN_SIZE, max = CommonConstants.CUST_NM_MAX_SIZE, message = "shipper customerName field length should be between 1 and 35.")
	private String shipCustomerNm;
	@NullOrNotBlank(min = 6, max = 6, message = "shipPrimSix length should be equal to 6")
	private String shipPrimSix;
	private Long billCustId;
	private String schedDelRateInd;
	private Integer schedDelFail;
	private Integer schedDelAllowance;
	@Digits(integer = 17, fraction = 2, message="peakRt1Amt digits cannot be more than 19 and scale cannot be more than 2")
	@Min(value=0, message="peakRt1Amt should be positive value")
	private BigDecimal peakRt1Amt;
	@Digits(integer = 17, fraction = 2, message="peakRt2Amt digits cannot be more than 19 and scale cannot be more than 2")
	@Min(value=0, message="peakRt2Amt should be positive value")
	private BigDecimal peakRt2Amt;
	@Digits(integer = 17, fraction = 2, message="offPeakRt1Amt digits cannot be more than 19 and scale cannot be more than 2")
	@Min(value=0, message="offPeakRt1Amt should be positive value")
	private BigDecimal offPeakRt1Amt;
	@Digits(integer = 17, fraction = 2, message="offPeakRate2Amt digits cannot be more than 19 and scale cannot be more than 2")
	@Min(value=0, message="offPeakRate2Amt should be positive value")
	private BigDecimal offPeakRate2Amt;

	private Timestamp peakBgnDtTm;
	private Timestamp peakEndDtTm;

	@Pattern(regexp = "^[YN]{1}", message = "cntWeekend should be either Y or N !")
	private String cntWeekend;
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
	private LocalDate effectiveDate;
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
	private LocalDate endDate;
	@Pattern(regexp = "^[YN]{1}", message = "cntAfternoon should be either Y or N !")
	private String cntAfternoon;
	private Integer pmNopaStartTm;

	@NullOrNotBlank(min = 1, max = 255, message = "notepadTxt length should be between 1 and 255")
	private String notepadTxt;

	@Pattern(regexp = "^[YN]{1}", message = "rrInd should be either Y or N !")
	private String rrInd;
	@Pattern(regexp = "^[YN]{1}", message = "cntSaturday should be either Y or N !")
	private String cntSaturday;
	@Pattern(regexp = "^[YN]{1}", message = "cntSunday should be either Y or N !")
	private String cntSunday;

	@NotNull
	@Size(min = 1, max = 2, message = "lclInterInds length should be either 1 or 2")
	@UniqueElements(message = "lclInterInd must have unique values")
	private List<@Pattern(regexp = "^[LI]{1}", message = "lclInterInd should be either L or I !") String> lclInterInds;

	@NotNull
	@Size(min = 1, max = 2, message = "ldEmptyCds length should be either 1 or 2")
	@UniqueElements(message = "ldEmptyCd must have unique values")
	private List<@Pattern(regexp = "^[LE]{1}", message = "ldEmptyCd should be either L or E !") String> ldEmptyCds;

	private String lastUpdated;
	private Terminal terminal;
	private CustomerCharge customerCharge;

	public Long getStorageId() {
		return storageId;
	}

	public void setStorageId(Long storageId) {
		this.storageId = storageId;
	}

	public List<Long> getTermIds() {
		return termIds;
	}

	public void setTermIds(List<Long> termIds) {
		this.termIds = termIds;
	}

	public String getGateInd() {
		return gateInd != null ? gateInd.trim().toUpperCase() : null;
	}

	public void setGateInd(String gateInd) {
		this.gateInd = gateInd;
	}

	public String getEquipTp() {
		return equipTp != null ? equipTp.trim().toUpperCase() : null;
	}

	public void setEquipTp(String equipTp) {
		this.equipTp = equipTp;
	}

	public List<String> getEquipInits() {
		return equipInits;
	}

	public void setEquipInits(List<String> equipInits) {
		this.equipInits = equipInits;
	}

	public Integer getEquipLgth() {
		return equipLgth;
	}

	public void setEquipLgth(Integer equipLgth) {
		this.equipLgth = equipLgth;
	}

	public String getRateTp() {
		return rateTp != null ? rateTp.trim().toUpperCase() : null;
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
		return customerNm != null ? customerNm.trim().toUpperCase() : null;
	}

	public void setCustomerNm(String customerNm) {
		this.customerNm = customerNm;
	}

	public String getCustomerPrimSix() {
		return customerPrimSix != null ? customerPrimSix.trim().toUpperCase() : null;
	}

	public void setCustomerPrimSix(String customerPrimSix) {
		this.customerPrimSix = customerPrimSix;
	}

	public String getBnfCustomerNm() {
		return bnfCustomerNm != null ? bnfCustomerNm.trim().toUpperCase() : null;
	}

	public void setBnfCustomerNm(String bnfCustomerNm) {
		this.bnfCustomerNm = bnfCustomerNm;
	}

	public String getBnfPrimSix() {
		return bnfPrimSix != null ? bnfPrimSix.trim().toUpperCase() : null;
	}

	public void setBnfPrimSix(String bnfPrimSix) {
		this.bnfPrimSix = bnfPrimSix;
	}

	public String getShipCustomerNm() {
		return shipCustomerNm != null ? shipCustomerNm.trim().toUpperCase() : null;
	}

	public void setShipCustomerNm(String shipCustomerNm) {
		this.shipCustomerNm = shipCustomerNm;
	}

	public String getShipPrimSix() {
		return shipPrimSix != null ? shipPrimSix.trim().toUpperCase() : null;
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
		return schedDelRateInd != null ? schedDelRateInd.trim().toUpperCase() : null;
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
		return cntWeekend != null ? cntWeekend.trim().toUpperCase() : null;
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
		return cntAfternoon != null ? cntAfternoon.trim().toUpperCase() : null;
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
		return notepadTxt != null ? notepadTxt.trim().toUpperCase() : null;
	}

	public void setNotepadTxt(String notepadTxt) {
		this.notepadTxt = notepadTxt;
	}

	public String getRrInd() {
		return rrInd != null ? rrInd.trim().toUpperCase() : null;
	}

	public void setRrInd(String rrInd) {
		this.rrInd = rrInd;
	}

	public String getCntSaturday() {
		return cntSaturday != null ? cntSaturday.trim().toUpperCase() : null;
	}

	public void setCntSaturday(String cntSaturday) {
		this.cntSaturday = cntSaturday;
	}

	public String getCntSunday() {
		return cntSunday != null ? cntSunday.trim().toUpperCase() : null;
	}

	public void setCntSunday(String cntSunday) {
		this.cntSunday = cntSunday;
	}

	public String getLastUpdated() {
		return lastUpdated != null ? lastUpdated.trim().toUpperCase() : null;
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

	public List<String> getLclInterInds() {
		return lclInterInds.stream().map(String::toUpperCase).collect(Collectors.toList());
	}

	public void setLclInterInds(List<String> lclInterInds) {
		this.lclInterInds = lclInterInds;
	}

	public List<String> getLdEmptyCds() {
		return ldEmptyCds.stream().map(String::toUpperCase).collect(Collectors.toList());
	}

	public void setLdEmptyCds(List<String> ldEmptyCds) {
		this.ldEmptyCds = ldEmptyCds;
	}

	@Override
	public String toString() {
		return "StorageRatesListDTO [storageId=" + storageId + ", termIds=" + termIds + ", gateInd=" + gateInd
				+ ", equipTp=" + equipTp + ", equipInits=" + equipInits + ", equipLgth=" + equipLgth + ", rateTp="
				+ rateTp + ", freeDDLmt=" + freeDDLmt + ", bondFreeDD=" + bondFreeDD + ", addlFreeHR=" + addlFreeHR
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
				+ cntSunday + ", lclInterInds=" + lclInterInds + ", ldEmptyCds=" + ldEmptyCds + ", lastUpdated="
				+ lastUpdated + ", terminal=" + terminal + ", customerCharge=" + customerCharge + ", getUversion()="
				+ getUversion() + ", getCreateUserId()=" + getCreateUserId() + ", getCreateDateTime()="
				+ getCreateDateTime() + ", getUpdateUserId()=" + getUpdateUserId() + ", getUpdateDateTime()="
				+ getUpdateDateTime() + ", getUpdateExtensionSchema()=" + getUpdateExtensionSchema() + "]";
	}
	
	
	
}
