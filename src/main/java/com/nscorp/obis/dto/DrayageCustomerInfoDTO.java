package com.nscorp.obis.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.NullOrNotBlank;

import io.swagger.v3.oas.annotations.media.Schema;

public class DrayageCustomerInfoDTO extends AuditInfoDTO {
	@NotBlank(message = "Drayage Id can't be empty or null")
	@Size(min = CommonConstants.DRAY_ID_MIN_SIZE, max = CommonConstants.DRAY_ID_MAX_SIZE, message = "Drayage Id length should be between 1 and 4")
	@Schema(required = true, description = "This identifies this is trucker as defined in SCAC.")
	private String drayageId;

	@NotNull(message = "Customer Id can't Be null")
	@Digits(integer = CommonConstants.CUST_ID_MAX_SIZE, fraction = 0, message = "Customer id can't be more than 15 digits")
	@Schema(required = true, description = "The Customer Id being used.", example = "3.000768653273E12")
	private Long customerId;

	@NullOrNotBlank(message = "Customer Number can't be empty")
	@Size(min = CommonConstants.CUST_NR_MIN_SIZE, max = CommonConstants.CUST_NR_MAX_SIZE, message = "customerNumber field length should be between 1 and 10.")
	@Schema(required = false, description = "The field designates the customer number.")
	private String customerNumber;

	@NullOrNotBlank(message = "Customer Name can't be empty")
	@Size(min = CommonConstants.CUST_NM_MIN_SIZE, max = CommonConstants.CUST_NM_MAX_SIZE, message = "customerName field length should be between 1 and 40.")
	@Schema(required = false, description = "The name of the customer to be notified.")
	private String customerName;

	public String getDrayageId() {
		return drayageId;
	}

	public void setDrayageId(String drayageId) {
		this.drayageId = drayageId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Override
	public String toString() {
		return "DrayageCustomerInfoDTO [drayageId=" + drayageId + ", customerId=" + customerId + ", customerNumber="
				+ customerNumber + ", customerName=" + customerName + ", getUversion()=" + getUversion()
				+ ", getCreateUserId()=" + getCreateUserId() + ", getCreateDateTime()=" + getCreateDateTime()
				+ ", getUpdateUserId()=" + getUpdateUserId() + ", getUpdateDateTime()=" + getUpdateDateTime()
				+ ", getUpdateExtensionSchema()=" + getUpdateExtensionSchema() + "]";
	}

}
