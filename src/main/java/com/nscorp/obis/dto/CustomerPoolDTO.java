package com.nscorp.obis.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

import com.nscorp.obis.common.CommonConstants;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CustomerPoolDTO extends AuditInfoDTO{
	@Schema(required = true,description="The Customer Id being used.", example="3.000768653273E12")
	@Digits(integer=CommonConstants.CUST_ID_MAX_SIZE, fraction=0, message= "Customer id length cannot be more than 15")
	@Min(value = 1, message = "Customer id must be greater than 0")
	private Long customerId;
	
	@Schema(required = false, description="The Pool Id being used.", example="3.000768653273E12")
	@Digits(integer=CommonConstants.TERM_ID_MAX_SIZE, fraction=0, message= "Terminal id length cannot be more than 15")
	@Min(value = 1, message = "Pool id must be greater than 0")
	private Long poolId;
}
