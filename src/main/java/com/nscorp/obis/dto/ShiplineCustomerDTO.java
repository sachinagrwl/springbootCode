package com.nscorp.obis.dto;

import com.nscorp.obis.common.CommonConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShiplineCustomerDTO extends AuditInfoDTO{

	@NotBlank(message = "Shipline Number Should not be Blank or Null.")
	@Schema(required = true,description="The shipline number being used.", example="CMA")
	@Size(min=CommonConstants.SHIPLINE_NUMBER_MIN_SIZE, max=CommonConstants.SHIPLINE_NUMBER_MAX_SIZE,message = "Shipline Number length should be less than or equal to {max}")
	private String shiplineNumber;

	@Schema(required = false,description="The Corporate Customer Id being used.", example="1.18675625939E13")
	@Digits(integer=15, fraction=0,message="Customer Id cannot have more than 15 digits")
	private Long customerId;
	
	@Schema(required = false,description="The description being used.", example="CMA CGM")
	@Size(max=CommonConstants.DESCRIPTION_MAX_SIZE, message="Description length cannot be more than {max}")
	private String description;

}
