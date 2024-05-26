package com.nscorp.obis.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.nscorp.obis.common.CommonConstants;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class EquipmentRestrictDTO extends AuditInfoDTO {

    @Schema(required = true,description="The RestrictionId of the Equipment being used.", example="1.0030614902021E13")
    private Long restrictionId;

    @Schema(required = true,description="The Equipment Initializer of the Equipment being used.", example="RCSU")
    @NotBlank(message = "'equipmentInit' value should not be Blank or Null.")
    @Size(min= CommonConstants.EQUIPMENT_INIT_MIN_SIZE, max=CommonConstants.EQUIPMENT_INIT_MAX_SIZE, message="'equipmentInit' size should be between {min} and {max}")
    private String equipmentInit;

    @Schema(required = true,description="The Equipment Type of the Equipment being used.", example="C")
    @NotBlank(message = "'equipmentType' value should not be Blank or Null.")
    @Size(min= CommonConstants.EQUIPMENT_TYPE_MIN_SIZE, max=CommonConstants.EQUIPMENT_TYPE_MAX_SIZE, message="'equipmentInit' size should not be greater than {max}")
    private String equipmentType;

    @Schema(required = false,description="The Equipment Lowest Number of the Equipment being used.", example="140000")
    @Digits(integer=CommonConstants.EQUIPMENT_NR_LOW_MAX_SIZE, fraction=0,message="'equipmentNumberLow' cannot have more than 6 digits")
    @Min(value = CommonConstants.EQUIPMENT_NR_LOW_MIN_SIZE, message = "'equipmentNumberLow' value cannot be less than 1")
    private BigDecimal equipmentNumberLow;

    @Schema(required = false,description="The Equipment Highest Number of the Equipment being used.", example="149999")
    @Digits(integer=CommonConstants.EQUIPMENT_NR_HIGH_MAX_SIZE, fraction=0,message="'equipmentNumberHigh' cannot have more than 6 digits")
    @Min(value = CommonConstants.EQUIPMENT_NR_HIGH_MIN_SIZE, message = "'equipmentNumberHigh' value cannot be less than 1")
    private BigDecimal equipmentNumberHigh;

    @Schema(required = false,description="The Equipment Restriction Type of the Equipment being used.", example="D")
    @Size(min= CommonConstants.EQUIPMENT_RESTRICT_TYPE_MAX_SIZE, max=CommonConstants.EQUIPMENT_RESTRICT_TYPE_MAX_SIZE, message="'equipmentRestrictionType' size should not be greater than {max}")
    private String equipmentRestrictionType;

}
