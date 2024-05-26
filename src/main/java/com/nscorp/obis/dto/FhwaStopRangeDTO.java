package com.nscorp.obis.dto;

import com.nscorp.obis.common.CommonConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
public class FhwaStopRangeDTO extends AuditInfoDTO{

    @Schema(required = true,description="The Range Id of the Fhwa Stop Range being used.", example="1.6433605471087E13")
    private Long rangeId;

    @NotBlank(message = "'equipmentType' should not be Blank or Null.")
    @Schema(required = true,description="The type of the equipment being used.", example="Z")
    @Size(max = CommonConstants.EQ_TYPE_MAX_SIZE, message="'equipmentType' size cannot have more than {max}")
    @Pattern(regexp="^(T|Z)$",message="Only T and Z is allowed.")
    private String equipmentType;

    @NotBlank(message = "'equipmentInit' should not be Blank or Null")
    @Schema(required = true,description="The Initializer value of the equipment being used.", example="CNRZ")
    @Size(min = CommonConstants.EQ_INIT_MIN_SIZE, max = CommonConstants.EQ_INIT_MAX_SIZE, message = "'equipmentInit' size should be between {min} and {max}")
    @Pattern(regexp="^[a-zA-Z]*$",message="Only Alphabets is allowed.")
    private String equipmentInit;

    @NotNull(message = "'equipmentNumberLow' value should not be Null.")
    @Schema(required = true,description="The Minimum height of the equipment being used.", example="1")
    @Digits(integer=CommonConstants.EQ_NR_LOW_MAX_SIZE, fraction=0,message="'equipmentNumberLow' cannot have more than 6 digits")
    @Min(value = 1, message = "'equipmentNumberLow' value cannot be less than 1")
    private BigDecimal equipmentNumberLow;

    @NotNull(message = "'equipmentNumberHigh' value should not be Null.")
    @Schema(required = true,description="The Maximum height of the equipment being used.", example="999999")
    @Digits(integer=CommonConstants.EQ_NR_HIGH_MAX_SIZE, fraction=0,message="'equipmentNumberHigh' cannot have more than 6 digits")
    @Min(value = 1, message = "'equipmentNumberHigh' value cannot be less than 1")
    private BigDecimal equipmentNumberHigh;
}
