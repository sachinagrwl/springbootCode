package com.nscorp.obis.dto;

import com.nscorp.obis.common.CommonConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PoolEquipmentRangeDTO extends AuditInfoDTO{
    @Schema(required = true,description="The Pool Range Id being used.", example="1.4472617900444E13")
    private Long poolRangeId;

    @NotNull(message = "poolId should not be Null.")
    @Schema(required = true,description="The Pool Id being used.", example="1.4462411580444E13")
    @Digits(integer=CommonConstants.POOL_ID_MAX_SIZE, fraction=0,message="poolId cannot have more than 6 digits")
    @Min(value = 0, message = "poolId must be greater than or equal to 1")
    private Long poolId;

    @NotBlank(message = "'equipmentType' should not be Blank or Null.")
    @Schema(required = true,description="The type of the equipment being used.", example="C")
    @Size(max = CommonConstants.EQ_TYPE_MAX_SIZE, message="'equipmentType' size cannot have more than {max}")
    @Pattern(regexp="^(C|T|Z)$",message="Only C,T and Z is allowed")
    private String equipmentType;

    @NotBlank(message = "'equipmentInit' should not be Blank or Null")
    @Schema(required = true,description="The Initializer value of the equipment being used.", example="TEST")
    @Size(min = CommonConstants.EQ_INIT_MIN_SIZE, max = CommonConstants.EQ_INIT_MAX_SIZE, message = "'equipmentInit' size should be between {min} and {max}")
    @Pattern(regexp="^[a-zA-Z]*$",message="Only Alphabets is allowed.")
    private String equipmentInit;

    @NotNull(message = "'equipmentLowNumber' value should not be Null.")
    @Schema(required = true,description="The Minimum height of the equipment being used.", example="62121")
    @Digits(integer=CommonConstants.EQ_NR_LOW_MAX_SIZE, fraction=0,message="'equipmentLowNumber' cannot have more than 6 digits")
    @Min(value = 1, message = "'equipmentLowNumber' value cannot be less than 1")
    private BigDecimal equipmentLowNumber;

    @NotNull(message = "'equipmentHighNumber' value should not be Null.")
    @Schema(required = true,description="The Maximum height of the equipment being used.", example="62200")
    @Digits(integer=CommonConstants.EQ_NR_HIGH_MAX_SIZE, fraction=0,message="'equipmentHighNumber' cannot have more than 6 digits")
    @Min(value = 1, message = "'equipmentHighNumber' value cannot be less than 1")
    private BigDecimal equipmentHighNumber;
}
