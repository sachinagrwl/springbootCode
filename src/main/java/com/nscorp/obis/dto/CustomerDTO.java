package com.nscorp.obis.dto;

import com.nscorp.obis.common.CommonConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

@Data
@EqualsAndHashCode(callSuper=false)
public class CustomerDTO extends AuditInfoDTO{

//	@NotNull(message = "Customer Id Should not be Null.")
	//@NotBlank(message = "Customer Id Should not be Blank or Null.")
	@Schema(required = true,description="The Customer Id being used.", example="3.000768653273E12")
	@Digits(integer=CommonConstants.CUST_ID_MAX_SIZE, fraction=0, message= "Customer id cannot be more than 15")
	@Min(value = 1, message = "Customer id Length must be greater than 0")
	private Long customerId;
	
	@Schema(required = false,description="The Corporate Customer Id being used.", example="1.639857332557E12")
	@Digits(integer=15, fraction=0, message= "Corporate Customer id cannot be more than 15")
	@Min(value = 1, message = "Corporate Customer id Length must be greater than 0")
	private Long corporateCustomerId;

	@Schema(required=false, description="The name of the customer to be notified.")
	private String customerName;

	@Schema(required=false, description="The field designates the customer number.")
	private String customerNumber;

	DeliveryDetailDTO deliveryDetail;
}
