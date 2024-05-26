package com.nscorp.obis.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.NullOrNotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

//@Data
//@EqualsAndHashCode(callSuper=false)
public class DrayageCustomerDTO extends AuditInfoDTO{
	@NullOrNotBlank(min=1, max = 4, message = "Drayage Id length should be between 1 and 4.")
    @Schema(required = true, description = "This identifies this is trucker as defined in SCAC.")
	private String drayageId;
	
	@Digits(integer = CommonConstants.CUST_ID_MAX_SIZE, fraction = 0, message = "Customer id cannot be more than 15")
	@Schema(required = true, description = "The Customer Id being used.", example = "3.000768653273E12")
	private Long customerId;
	
	@NullOrNotBlank(min = CommonConstants.CUST_NR_MIN_SIZE, max = CommonConstants.CUST_NR_MAX_SIZE, message = "customerNumber field length should be between 1 and 10.")
//	@Pattern(regexp = "^[016].*", message = "Number should start either with 0 or 1 or 6")
	@Schema(required = false, description = "The field designates the customer number.")
	private String customerNumber;

	@NotNull
	@NullOrNotBlank(min = CommonConstants.CUST_NM_MIN_SIZE, max = CommonConstants.CUST_NM_MAX_SIZE, message = "customerName field length should be between 1 and 35.")
	@Schema(required = false, description = "The name of the customer to be notified.")
	private String customerName;
	
	private CustomerInfoDTO customer;

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

	public CustomerInfoDTO getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerInfoDTO customer) {
		this.customer = customer;
		this.customerId = customer.getCustomerId();
	}
	
}
