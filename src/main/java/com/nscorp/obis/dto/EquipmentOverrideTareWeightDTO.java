package com.nscorp.obis.dto;

import java.math.BigDecimal;

import javax.validation.constraints.*;

import com.nscorp.obis.common.CommonConstants;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EquipmentOverrideTareWeightDTO extends AuditInfoDTO {
	
//	@NotNull(message = "'overrideId' should not be Null")
	@Schema(required = true,description="The Override Id of the equipment being used.", example="1.4414106360672E13")
//	@Digits(integer=CommonConstants.OVERRIDE_ID_MAX_SIZE, fraction=0,message="'overrideId' cannot have more than 53 digits")
//	@Min(value = 0, message = "'overrideId' value must be greater than or equal to 0")
	private Long overrideId;

//	@NotBlank(message = "'equipmentInit' should not be Blank or Null")
	@Schema(required = true,description="The Initializer value of the equipment being used.", example="TEST")
	@Size(min = CommonConstants.EQ_INIT_MIN_SIZE, max = CommonConstants.EQ_INIT_MAX_SIZE, message = "'equipmentInit' size should be between {min} and {max}")
	@Pattern(regexp="^[a-zA-Z]*$",message="Only Alphabets is allowed.")
	private String equipmentInit;

	@NotNull(message = "'equipmentNumberLow' value should not be Null.")
	@Schema(required = true,description="The Minimum height of the equipment being used.", example="62121")
	@Digits(integer=CommonConstants.EQ_NR_LOW_MAX_SIZE, fraction=0,message="'equipmentNumberLow' cannot have more than 6 digits")
	@Min(value = 1, message = "'equipmentNumberLow' value cannot be less than 1")
	private BigDecimal equipmentNumberLow;

	@NotNull(message = "'equipmentNumberHigh' value should not be Null.")
	@Schema(required = true,description="The Maximum height of the equipment being used.", example="62200")
	@Digits(integer=CommonConstants.EQ_NR_HIGH_MAX_SIZE, fraction=0,message="'equipmentNumberHigh' cannot have more than 6 digits")
	@Min(value = 1, message = "'equipmentNumberHigh' value cannot be less than 1")
	private BigDecimal equipmentNumberHigh;

	@NotBlank(message = "'equipmentType' should not be Blank or Null.")
    @Schema(required = true,description="The type of the equipment being used.", example="C")
	@Size(max = CommonConstants.EQ_TYPE_MAX_SIZE, message="'equipmentType' size cannot have more than {max}")
	@Pattern(regexp="^(C|T|Z|F|G)$",message="Only C,T,Z,F and G is allowed")
	private String equipmentType;

//	@NotNull(message = "'equipmentLength' should not be Null.")
    @Schema(required = true,description="The length of the equipment being used.", example="45")
    @Digits(integer=CommonConstants.EQ_LNGTH_MAX_SIZE, fraction=0, message="'equipmentLength' cannot have more than 5 digits")
    @Min(value = 0, message = "'equipmentLength' must be greater than or equals to 0")
	private Integer equipmentLength;
	
	@NotNull(message = "'overrideTareWeight' should not be Null.")
    @Schema(required = false,description="The Override Tare Weight of the equipment being used.", example="6000")
    @Digits(integer=CommonConstants.OVERRIDE_TARE_WEIGHT_MAX_SIZE, fraction = 0, message="'overrideTareWeight' cannot have more than 6 digits")
    @Min(value = 0, message = "'overrideTareWeight' must be greater than or equals to 0")
	private Integer overrideTareWeight;

}
