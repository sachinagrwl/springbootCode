package com.nscorp.obis.dto;

import com.nscorp.obis.common.CommonConstants;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;


@Data
@EqualsAndHashCode(callSuper=false)
public class RulesCircularDTO extends AuditInfoDTO {
	
	@NotBlank(message = "'equipmentType' Should not be Blank or Null.")
    @Schema(required = true,description="The type of the equipment being used.", example="C")
	private String equipmentType;
	
    @NotNull(message = "'equipmentLength' Should not be Null.")
    @Schema(required = true,description="The length of the equipment being used.", example="45")
    @Digits(integer=CommonConstants.EQ_LENGTH_RULES_CIRCULAR_MAX_SIZE, fraction=0, message="'equipmentLength' cannot have more than 2 digits")
    @Min(value = 0, message = "'equipmentLength' must be greater than or equals to 0")
    private Integer equipmentLength;

    @NotNull(message = "'maximumShipWeight' Should not be Null.")
    @Schema(required = false,description="The Weight of the equipment being used.", example="6000")
    @Digits(integer=CommonConstants.MAXIMUM_SHIP_WEIGHT_MAX_SIZE, fraction = 0, message="'maximumShipWeight' cannot have more than 9 digits")
    @Min(value = 0, message = "'maximumShipWeight' must be greater than or equals to 0")
    private Integer maximumShipWeight;

}
