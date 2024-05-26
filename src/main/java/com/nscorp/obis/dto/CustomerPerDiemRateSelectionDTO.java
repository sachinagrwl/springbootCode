package com.nscorp.obis.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.NullOrNotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CustomerPerDiemRateSelectionDTO extends AuditInfoDTO {

//	@NotNull
	@Schema(required = true, description = "Per Diem Id")
	private Long perDiemId;

	@Schema(required = false, description = "This is the name of the intermodal facility.")
	private Long terminalId;

	@Schema(required = false, description = "Equip Init")
	private String equipInit;

	@Schema(required = false, description = "Equip Nr Low")
	private BigDecimal equipNrLow;

	@Schema(required = false, description = "Equip Nr High")
	private BigDecimal equipNrHigh;
	@NotNull(message="equipTp should be not null")
	@NullOrNotBlank(min=CommonConstants.EQ_TYPE_MIN_SIZE,max=CommonConstants.EQ_TYPE_MAX_SIZE,message="equipTp should be length of 1")
	@Pattern(regexp = "^[CTZ]{1}", message="equipTp should be C= Container, T=Trailer, Z = Chassis")
	@Schema(required=true, description="This is the AAR Code associated with the equipment. ")
	private String equipTp;

	@NotNull(message="outgateLoadEmptyStatus should be not null")
	@NullOrNotBlank(min=CommonConstants.Load_Empty_Status_MIN_SIZE,max=CommonConstants.Load_Empty_Status_MAX_SIZE,message="out Load Empty Status should be length of 1")
	@Pattern(regexp = "^[LE]{1}", message="out Load Empty Status should be L or E")
	@Schema(required = true, description = "This is the load empty status at outgate time")
	private String outgateLoadEmptyStatus;

	@NotNull(message="ingateLoadEmptyStatus should be not null")
	@NullOrNotBlank(min=CommonConstants.Load_Empty_Status_MIN_SIZE,max=CommonConstants.Load_Empty_Status_MAX_SIZE,message="ingate Load Empty Status should be length of 1")
	@Pattern(regexp = "^[LE]{1}", message="ingate Load Empty Status should be L or E")
	@Schema(required = true, description = "This is the load empty status at ingate time")
	private String ingateLoadEmptyStatus;

	@Schema(required = false, description = "Svc_Cd")
	private String svcCd;

	@NullOrNotBlank(min=CommonConstants.CNT_WK_MIN_SIZE,max=CommonConstants.CNT_WK_MAX_SIZE,message="counted on weekend should be length of 1")
	@Pattern(regexp = "^[YN]{1}", message="counted weekend should be Y or N")
	@Schema(required = false, description = "This is the First Weekend Free field.")
	private String countWeekend;
	
	@NullOrNotBlank(min=CommonConstants.RT_TP_MIN_SIZE,max=CommonConstants.RT_TP_MAX_SIZE,message="rate type should be length of 1")
	@Pattern(regexp = "^[HDM]{1}", message="rate type should be H,D or M")
	@Schema(required = false, description = "Rate type for storage or per diem charges. ")
	private String rateType;

	@Min(value=0, message="free day limit should be positive value")
	@Schema(required = false, description = "This is the Number of Free days before charges apply")
	private Integer freeDayLimit;
	
	@Min(value=0, message="rate day limit should be positive value")
	@Schema(required = false, description = "This is the number of free days before second rate applies.")
	private Integer rateDayLimit;
	
	@Digits(integer = 17, fraction = 2, message="initial rate digits cannot be more than 19 and scale cannot be more than 2")
	@Min(value=0, message="intial rate should be positive value")
	@Schema(required = false, description = "This is the initial rate")
	private BigDecimal initialRate;
	
	@Digits(integer = 17, fraction = 2, message="secondary rate digits cannot be more than 19 and scale cannot be more than 2")
	@Min(value=0, message="secodary rate should be positive value")
	@Schema(required = false, description = "This is the second rate")
	private BigDecimal secondaryRate;

	@Schema(required = false, description = "Average Unit Limit")
	private Integer averageUnitLimit;

	@Schema(required = false, description = "Mm Day Limit")
	private Integer mmDayLimit;

	@JsonFormat(shape = Shape.STRING, pattern = "MM-dd-yyyy")
	@Schema(required = false, description = "This indicates the effective date for a cash-in-fist exception.")
	private Date effectiveDate;
	
	@JsonFormat(shape = Shape.STRING, pattern = "MM-dd-yyyy")
	@Schema(required = false, description = "This indicates the end date for this function. It must be later than the start date")
	private Date endDate;

	@NullOrNotBlank(min=CommonConstants.CUST_PRIMARY_6_MIN_SIZE,max=CommonConstants.CUST_PRIMARY_6_MAX_SIZE,message="Beneficial Prim Six should be length of 6")
	@Schema(required = false, description = "Beneficial Prim Six")
	private String beneficialPrimSix;

	@NotNull(message="custPrimSix should be not null")
	@NullOrNotBlank(min=CommonConstants.CUST_PRIMARY_6_MIN_SIZE,max=CommonConstants.CUST_PRIMARY_6_MAX_SIZE,message="Cust Prim Six should be length of 6")
	@Schema(required = true, description = "Cust Prim Six")
	private String custPrimSix;

	@NotNull(message="customerName should be not null")
	@NullOrNotBlank(min=CommonConstants.CUST_NM_MIN_SIZE,max=CommonConstants.CUST_NM_MAX_SIZE,message="Customer Name length should be between 1 and 35")
	@Schema(required = true, description = "The name of the customer e.g. The shipper, consignee, beneficial owner, third party, bill to party, etc.")
	private String customerName;
	
	@NullOrNotBlank(min=CommonConstants.CUST_NM_MIN_SIZE,max=CommonConstants.CUST_NM_MAX_SIZE,message="beneficial Customer Name length should be between 1 and 35")
	@Schema(required = false, description = "This is the beneficial customer associated with the shipment")
	private String beneficialCustomerName;

	@NullOrNotBlank(min=CommonConstants.CUST_PRIMARY_6_MIN_SIZE,max=CommonConstants.CUST_PRIMARY_6_MAX_SIZE,message="Ship Prim Six should be length of 6")
	@Schema(required = false, description = "Ship Prim Six")
	private String shipPrimSix;

	@NullOrNotBlank(min=CommonConstants.CUST_NM_MIN_SIZE,max=CommonConstants.CUST_NM_MAX_SIZE,message="ship Customer Name length should be between 1 and 35")
	@Schema(required = false, description = "This screen allows the user to create, update the Customer’s Per Diem Rate Profile.")
	private String shipCustomerName;

	@Schema(required = false, description = "Bill Customer Id")
	private Long billCustomerId;

	public Long getPerDiemId() {
		return perDiemId;
	}

	public void setPerDiemId(Long perDiemId) {
		this.perDiemId = perDiemId;
	}

	public Long getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}

	public String getEquipInit() {
		return equipInit == null ? equipInit : equipInit.trim();
	}

	public void setEquipInit(String equipInit) {
		this.equipInit = equipInit;
	}

	public BigDecimal getEquipNrLow() {
		return equipNrLow;
	}

	public void setEquipNrLow(BigDecimal equipNrLow) {
		this.equipNrLow = equipNrLow;
	}

	public BigDecimal getEquipNrHigh() {
		return equipNrHigh;
	}

	public void setEquipNrHigh(BigDecimal equipNrHigh) {
		this.equipNrHigh = equipNrHigh;
	}

	public String getEquipTp() {
		return equipTp == null ? equipTp : equipTp.trim();
	}

	public void setEquipTp(String equipTp) {
		this.equipTp = equipTp;
	}

	public String getOutgateLoadEmptyStatus() {
		return outgateLoadEmptyStatus == null ? outgateLoadEmptyStatus : outgateLoadEmptyStatus.trim();
	}

	public void setOutgateLoadEmptyStatus(String outgateLoadEmptyStatus) {
		this.outgateLoadEmptyStatus = outgateLoadEmptyStatus;
	}

	public String getIngateLoadEmptyStatus() {
		return ingateLoadEmptyStatus == null ? ingateLoadEmptyStatus : ingateLoadEmptyStatus.trim();
	}

	public void setIngateLoadEmptyStatus(String ingateLoadEmptyStatus) {
		this.ingateLoadEmptyStatus = ingateLoadEmptyStatus;
	}

	public String getSvcCd() {
		return svcCd == null ? svcCd : svcCd.trim();
	}

	public void setSvcCd(String svcCd) {
		this.svcCd = svcCd;
	}

	public String getCountWeekend() {
		return countWeekend == null ? countWeekend : countWeekend.trim();
	}

	public void setCountWeekend(String countWeekend) {
		this.countWeekend = countWeekend;
	}

	public String getRateType() {
		return rateType == null ? rateType : rateType.trim();
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public Integer getFreeDayLimit() {
		return freeDayLimit;
	}

	public void setFreeDayLimit(Integer freeDayLimit) {
		this.freeDayLimit = freeDayLimit;
	}

	public Integer getRateDayLimit() {
		return rateDayLimit;
	}

	public void setRateDayLimit(Integer rateDayLimit) {
		this.rateDayLimit = rateDayLimit;
	}

	public BigDecimal getInitialRate() {
		return initialRate;
	}

	public void setInitialRate(BigDecimal initialRate) {
		this.initialRate = initialRate;
	}

	public BigDecimal getSecondaryRate() {
		return secondaryRate;
	}

	public void setSecondaryRate(BigDecimal secondaryRate) {
		this.secondaryRate = secondaryRate;
	}

	public Integer getAverageUnitLimit() {
		return averageUnitLimit;
	}

	public void setAverageUnitLimit(Integer averageUnitLimit) {
		this.averageUnitLimit = averageUnitLimit;
	}

	public Integer getMmDayLimit() {
		return mmDayLimit;
	}

	public void setMmDayLimit(Integer mmDayLimit) {
		this.mmDayLimit = mmDayLimit;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getBeneficialPrimSix() {
		return beneficialPrimSix == null ? beneficialPrimSix : beneficialPrimSix.trim();
	}

	public void setBeneficialPrimSix(String beneficialPrimSix) {
		this.beneficialPrimSix = beneficialPrimSix;
	}

	public String getCustPrimSix() {
		return custPrimSix == null ? custPrimSix : custPrimSix.trim();
	}

	public void setCustPrimSix(String custPrimSix) {
		this.custPrimSix = custPrimSix;
	}

	public String getCustomerName() {
		return customerName == null ? customerName : customerName.trim();
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getBeneficialCustomerName() {
		return beneficialCustomerName == null ? beneficialCustomerName : beneficialCustomerName.trim();
	}

	public void setBeneficialCustomerName(String beneficialCustomerName) {
		this.beneficialCustomerName = beneficialCustomerName;
	}

	public String getShipPrimSix() {
		return shipPrimSix == null ? shipPrimSix : shipPrimSix.trim();
	}

	public void setShipPrimSix(String shipPrimSix) {
		this.shipPrimSix = shipPrimSix;
	}

	public String getShipCustomerName() {
		return shipCustomerName == null ? shipCustomerName : shipCustomerName.trim();
	}

	public void setShipCustomerName(String shipCustomerName) {
		this.shipCustomerName = shipCustomerName;
	}

	public Long getBillCustomerId() {
		return billCustomerId;
	}

	public void setBillCustomerId(Long billCustomerId) {
		this.billCustomerId = billCustomerId;
	}

	@Override
	public String toString() {
		return "CustomerPerDiemRateSelectionDTO [perDiemId=" + perDiemId + ", terminalId=" + terminalId + ", equipInit="
				+ equipInit + ", equipNrLow=" + equipNrLow + ", equipNrHigh=" + equipNrHigh + ", equipTp=" + equipTp
				+ ", outgateLoadEmptyStatus=" + outgateLoadEmptyStatus + ", ingateLoadEmptyStatus="
				+ ingateLoadEmptyStatus + ", svcCd=" + svcCd + ", countWeekend=" + countWeekend + ", rateType="
				+ rateType + ", freeDayLimit=" + freeDayLimit + ", rateDayLimit=" + rateDayLimit + ", initialRate="
				+ initialRate + ", secondaryRate=" + secondaryRate + ", averageUnitLimit=" + averageUnitLimit
				+ ", mmDayLimit=" + mmDayLimit + ", effectiveDate=" + effectiveDate + ", endDate=" + endDate
				+ ", beneficialPrimSix=" + beneficialPrimSix + ", custPrimSix=" + custPrimSix + ", customerName="
				+ customerName + ", beneficialCustomerName=" + beneficialCustomerName + ", shipPrimSix=" + shipPrimSix
				+ ", shipCustomerName=" + shipCustomerName + ", billCustomerId=" + billCustomerId + ", getUversion()="
				+ getUversion() + ", getCreateUserId()=" + getCreateUserId() + ", getCreateDateTime()="
				+ getCreateDateTime() + ", getUpdateUserId()=" + getUpdateUserId() + ", getUpdateDateTime()="
				+ getUpdateDateTime() + ", getUpdateExtensionSchema()=" + getUpdateExtensionSchema() + "]";
	}

}
