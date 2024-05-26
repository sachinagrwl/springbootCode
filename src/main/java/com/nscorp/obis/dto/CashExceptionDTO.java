package com.nscorp.obis.dto;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.NullOrNotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.*;

public class CashExceptionDTO extends AuditInfoDTO {

	@Schema(required = true, description = "Cash Exception Id")
	private Long cashExceptionId;

	@Size(min = CommonConstants.EQ_INIT_MIN_SIZE, max = CommonConstants.EQ_INIT_MAX_SIZE, message = "'Equipment Init' size should be between {min} and {max}")
	@Pattern(regexp="^[a-zA-Z]*$",message="Only Alphabets is allowed.")
	@Schema(required = false, description = "The Alpha ID of the equipment")
	private String equipInit;

	@Digits(integer=CommonConstants.EQ_NR_LOW_MAX_SIZE, fraction=0,message="'Equipment Number' cannot have more than 6 digits")
	@Min(value = 1, message = "'Equipment Number' value cannot be less than 1")
	@Schema(required = false, description = "Equipment Number")
	private Integer equipNbr;

	@Size(max = CommonConstants.EQ_TYPE_MAX_SIZE, message="'Equipment Type' size cannot have more than {max}")
	@Pattern(regexp="^(C|T|Z)$",message="Only C,T,Z allowed")
	@Schema(required = false,description="The type of the equipment being used.", example="C")
	private String equipType;

	@Size(max = CommonConstants.EQ_LENGTH_MAX_SIZE, message="'Equip Id' size cannot have more than {max}")
	@Schema(required = false, description = "")
	private String equipId;

	@Schema(required = false, description = "")
	private Long termId;

	// @NotNull(message="Must enter effective date")
	@JsonFormat(shape = Shape.STRING, pattern = "MM-dd-yyyy")
	@Schema(required = true, description = "Effactive Date")
	private LocalDate effectiveDate;

	@JsonFormat(shape = Shape.STRING, pattern = "MM-dd-yyyy")
	@Schema(required = false, description = "")
	private LocalDate endDate;

	@NullOrNotBlank(min=CommonConstants.CUST_PRIMARY_6_MIN_SIZE,max=CommonConstants.CUST_PRIMARY_6_MAX_SIZE,message="Beneficial Primary Six should be length of 6")
	@Schema(required = false, description = "")
	private String bnfPrimarySix;

	@NullOrNotBlank(min=CommonConstants.CUST_NR_MIN_SIZE,max=CommonConstants.CUST_NR_MAX_SIZE,message="Beneficial Customer Number length should be between 1 and 10")
	@Schema(required = false, description = "")
	private String bnfCustomerNumber;

	// @NotNull(message="Must enter customer primary six number")
	@NullOrNotBlank(min=CommonConstants.CUST_PRIMARY_6_MIN_SIZE,max=CommonConstants.CUST_PRIMARY_6_MAX_SIZE,message="Customer Primary Six should be length of 6")
	@Schema(required = true, description = "Customer Primary Six number")
	private String customerPrimarySix;

	// @NotNull(message="Must enter customer name")
	@NullOrNotBlank(min=CommonConstants.CUST_NM_MIN_SIZE,max=CommonConstants.CUST_NM_MAX_SIZE,message="Customer Name length should be between 1 and 35")
	@Schema(required = true, description = "The name of the customer")
	private String customerName;
	@Size(min= CommonConstants.Load_Empty_Status_MIN_SIZE,max=CommonConstants.Load_Empty_Status_MAX_SIZE,message="Loaded or Empty Status should be length of 1")
	@Pattern(regexp = "^[LE]{1}", message="Loaded or Empty Status should be L or E")
	@Schema(required = false, description = "This is the identifier for a loaded or empty shipment; L=Loaded, E=Empty")
	private String loadedOrEmpty;

	public Long getCashExceptionId() {
		return cashExceptionId;
	}

	public void setCashExceptionId(Long cashExceptionId) {
		this.cashExceptionId = cashExceptionId;
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

	public Long getTermId() {
		return termId;
	}

	public void setTermId(Long termId) {
		this.termId = termId;
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

	public String getBnfPrimarySix() {
		return bnfPrimarySix;
	}

	public void setBnfPrimarySix(String bnfPrimarySix) {
		this.bnfPrimarySix = bnfPrimarySix;
	}

	public String getCustomerPrimarySix() {
		return customerPrimarySix;
	}

	public void setCustomerPrimarySix(String customerPrimarySix) {
		this.customerPrimarySix = customerPrimarySix;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getLoadedOrEmpty() {
		return loadedOrEmpty;
	}

	public void setLoadedOrEmpty(String loadedOrEmpty) {
		this.loadedOrEmpty = loadedOrEmpty;
	}

	public String getBnfCustomerNumber() {
		return bnfCustomerNumber;
	}

	public void setBnfCustomerNumber(String bnfCustomerNumber) {
		this.bnfCustomerNumber = bnfCustomerNumber;
	}

	public CashExceptionDTO() {
		super();
	}

	@Override
	public String toString() {
		return "CashExceptionDTO [cashExceptionId=" + cashExceptionId + ", equipInit=" + equipInit + ", equipNbr="
				+ equipNbr + ", equipType=" + equipType + ", equipId=" + equipId + ", termId=" + termId
				+ ", effectiveDate=" + effectiveDate + ", endDate=" + endDate + ", bnfPrimarySix=" + bnfPrimarySix
				+ ", bnfCustomerNumber=" + bnfCustomerNumber + ", customerPrimarySix=" + customerPrimarySix
				+ ", customerName=" + customerName + ", loadedOrEmpty=" + loadedOrEmpty + ", getUversion()="
				+ getUversion() + ", getCreateUserId()=" + getCreateUserId() + ", getCreateDateTime()="
				+ getCreateDateTime() + ", getUpdateUserId()=" + getUpdateUserId() + ", getUpdateDateTime()="
				+ getUpdateDateTime() + ", getUpdateExtensionSchema()=" + getUpdateExtensionSchema() + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cashExceptionId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CashExceptionDTO cashExceptionDTO = (CashExceptionDTO) obj;
		return Objects.equals(cashExceptionId, cashExceptionDTO.cashExceptionId);
	}

}
