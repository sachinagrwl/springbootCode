package com.nscorp.obis.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.nscorp.obis.common.CommonConstants;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class GuaranteeCustCrossRefDTO extends AuditInfoDTO {
	@Schema(required = true, description = "Guarantee Cust XRF Id being used.", example = "1.639857332557E12")
	private Long guaranteeCustXrefId;

	@Schema(required = true, description = "The Corporate Customer Id being used.", example = "1.639857332557E12")
	@Digits(integer = 15, fraction = 0, message = "Corporate Customer id length cannot be more than 15")
	@Min(value = 1, message = "Corporate Customer id Length must be greater than 0")
	private Long corpCustId;
	
	@Schema(required = true, description = "The Corporate Long Name being used.", example = "KLINE THE RAILBRIDGE CORP")
	@Size(max = CommonConstants.CORP_LONG_NAME_MAX_SIZE, message = "Corporate Long Name Length must not be greater than 30.")
	private String guaranteeCustLongName;

	@Schema(required = true, description = "The terminalName being used.", example = "KLINE THE RAILBRIDGE CORP")
	@Size(max = CommonConstants.CORP_LONG_NAME_MAX_SIZE, message = "Terminal Name Length must not be greater than 30.")
	@Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "Only Alpha Numeric values is allowed.")
	private String terminalName;

	@Digits(integer = 15, fraction = 0, message = "Terminal id length cannot be more than 15")
	private Long terminalId;

	@Schema(required = false, description = "The field designates the customer number.")
	@Size(max = CommonConstants.EMBARGO_DESCRIPTION_MAX_SIZE, message = "Guarantee Customer Number Length must not be greater than 50.")
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Only Alpha Numeric values is allowed.")
	private String guaranteeCustomerNumber;

	@Digits(integer = 15, fraction = 0, message = "Customer id length cannot be more than 15")
	private Long customerId;
}
