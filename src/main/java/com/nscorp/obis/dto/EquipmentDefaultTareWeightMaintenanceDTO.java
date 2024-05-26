package com.nscorp.obis.dto;

import com.nscorp.obis.common.CommonConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;

@Data
@EqualsAndHashCode(callSuper=false)
public class EquipmentDefaultTareWeightMaintenanceDTO extends AuditInfoDTO {
	
	@NotBlank(message = "Equipment Type Should not be Blank or Null.")
	@Schema(required = true,description="The type of the equipment being used.", example="C")
	@Size(min=CommonConstants.EQ_TYPE_MIN_SIZE, max=CommonConstants.EQ_TYPE_MAX_SIZE, message ="Equipment Type must be between {min} and {max}")
	private String eqTp;
	
	@NotNull(message = "Equipment Length Should not be Null.")
	@Schema(required = true,description="The length of the equipment being used.", example="45")
	@Digits(integer=CommonConstants.EQ_LENGTH_MAX_SIZE, fraction=0, message="Equipment Length cannot have more than 4 digits")
	@Min(value = 1, message = "Equipment Length must be greater than 0")
	private Integer eqLgth;
	
	@Schema(required = false,description="The Weight of the equipment being used.", example="6000")
	@Digits(integer=CommonConstants.TARE_WEIGHT_MAX_SIZE, fraction = 0, message="Tare Weight cannot have more than 6 digits")
	@Min(value = 0, message = "Tare Weight must be greater than or equal to 0")
	private Integer tareWgt;

}
