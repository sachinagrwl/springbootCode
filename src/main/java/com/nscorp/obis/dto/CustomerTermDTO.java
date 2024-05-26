package com.nscorp.obis.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

import com.nscorp.obis.common.CommonConstants;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CustomerTermDTO {

	@Schema(required = true,description="The Customer Id being used.", example="3.000768653273E12")
	@Digits(integer=CommonConstants.CUST_ID_MAX_SIZE, fraction=0, message= "Customer id cannot be more than 15")
	@Min(value = 1, message = "Customer id Length must be greater than 0")
	private Long customerId;
	
	@Schema(required = false, description="The Terminal Id being used.", example="3.000768653273E12")
	private Long terminalId;

	@Schema(required=false, description="The name of the customer to be notified.")
	private String customerName;
	
	@Schema(required=false, description="The description of the customer to be notified.")
	private String customerDescription;

	@Schema(required=false, description="The city of the customer to be notified.")
	private String customerCity;
	
	@Schema(required=false, description="The field designates the customer state or province.")
	private String custormerState;

	@Schema(required=false, description="The field designates the customer number.")
	private String customerNumber;
	
}
