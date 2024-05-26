package com.nscorp.obis.dto;

import com.nscorp.obis.common.CommonConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@EqualsAndHashCode(callSuper=false)
public class ContainerChassisAssociationDTO extends AuditInfoDTO {

    @Schema(required = true,description="The Association Id of the Container/Chassis being used.", example="1.0030614902021E13")
    private Long associationId;

    @NotBlank(message = "Container Initial should not be null or blank.")
    @Schema(required = true,description="The Container Initializer being used.", example="PTSU")
    @NotBlank(message = "'containerInit' value should not be Blank or Null.")
    @Size(min= CommonConstants.CONTAINER_INIT_MIN_SIZE, max=CommonConstants.CONTAINER_INIT_MAX_SIZE, message="'containerInit' size should not be greater than {max}")
    private String containerInit;

    @NotNull(message = "Container Low Number should not be null")
    @Schema(required = false,description="The Container Lowest Number being used.", example="840000")
    @Digits(integer=CommonConstants.CONTAINER_NR_LOW_MAX_SIZE, fraction=0,message="'containerLowNumber' cannot have more than 6 digits")
    @Min(value = CommonConstants.CONTAINER_NR_LOW_MIN_SIZE, message = "'containerLowNumber' value cannot be less than 1")
    private BigDecimal containerLowNumber;

    @NotNull(message = "Container High Number should not be null")
    @Schema(required = false,description="The Container Highest Number being used.", example="980000")
    @Digits(integer=CommonConstants.CONTAINER_NR_HIGH_MAX_SIZE, fraction=0,message="'containerHighNumber' cannot have more than 6 digits")
    @Min(value = CommonConstants.CONTAINER_NR_HIGH_MIN_SIZE, message = "'containerHighNumber' value cannot be less than 1")
    private BigDecimal containerHighNumber;

    @Schema(required = false,description="The Container Length being used.", example="53")
    @Digits(integer=CommonConstants.CONTAINER_LENGTH_MAX_SIZE, fraction=0,message="'containerLength' cannot have more than 5 digits")
    @Min(value = CommonConstants.CONTAINER_LENGTH_MIN_SIZE, message = "'containerLength' value cannot be less than 1")
    private Integer containerLength;

    @Schema(required = true,description="The Chassis Initializer being used.", example="NSFZ")
    @NotBlank(message = "'chassisInit' value should not be Blank or Null.")
    @Size(min= CommonConstants.CHASSIS_INIT_MIN_SIZE, max=CommonConstants.CHASSIS_INIT_MAX_SIZE, message="'chassisInit' size should not be greater than {max}")
    private String chassisInit;

    @Schema(required = false,description="The Chassis Lowest Number being used.", example="1")
    @Digits(integer=CommonConstants.CHASSIS_NR_LOW_MAX_SIZE, fraction=0,message="'chassisLowNumber' cannot have more than 6 digits")
    @Min(value = CommonConstants.CHASSIS_NR_LOW_MIN_SIZE, message = "'chassisLowNumber' value cannot be less than 1")
    private BigDecimal chassisLowNumber;

    @Schema(required = false,description="The Chassis Highest Number being used.", example="999999")
    @Digits(integer=CommonConstants.CHASSIS_NR_HIGH_MAX_SIZE, fraction=0,message="'chassisHighNumber' cannot have more than 6 digits")
    @Min(value = CommonConstants.CHASSIS_NR_HIGH_MIN_SIZE, message = "'chassisHighNumber' value cannot be less than 1")
    private BigDecimal chassisHighNumber;

    @Schema(required = false,description="The Chassis Length being used.", example="53")
    @Digits(integer=CommonConstants.CHASSIS_LENGTH_MAX_SIZE, fraction=0,message="'chassisLength' cannot have more than 5 digits")
    @Min(value = CommonConstants.CHASSIS_LENGTH_MIN_SIZE, message = "'chassisLength' value cannot be less than 1")
    private Integer chassisLength;

    @Schema(required = false,description="The Allow Indicator being used.", example="Y")
    @NotBlank(message = "'allowIndicator' value should not be Blank or Null.")
    @Size(min= CommonConstants.ALLOW_IND_MIN_SIZE, max=CommonConstants.ALLOW_IND_MAX_SIZE, message="'allowIndicator' size should not be greater than {max}")
    @Pattern(regexp="^(Y|N)$",message="Only 'Y' & 'N' values are allowed")
    private String allowIndicator;

    @Schema(required = false ,description="The time at which the record had expired.", example="1996-08-16 12:10:25")
    private Timestamp expiredDateTime;
}
