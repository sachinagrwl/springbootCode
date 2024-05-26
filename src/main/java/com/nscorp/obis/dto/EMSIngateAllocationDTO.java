package com.nscorp.obis.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.domain.EMSAllotmentType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(value = "true")
public class EMSIngateAllocationDTO extends BaseEMSIngateRestrictionDTO{
	
	//@NotNull(message = "'TimsId' should not be null.")
	@Schema(required = true, description="The Tims Id of the EMS Ingate Allocation being used.", example="9.260293421922E12")
	@Digits(integer=CommonConstants.TIMS_ID_MAX_SIZE, fraction = 0, message="Ingate terminal Id cannot have more than 15 digits")
	private Long timsId;

	@NotNull(message = "'allotmentType' should not be null.")
	@Schema(required = false, description="The Allotment Type of the EMS Ingate Allocation being used.", example="D")
	private EMSAllotmentType allotmentType;

	@NotNull(message = "'totalIngatesAllowed' field should not be null")
    @Schema(required = false, description="The Total Ingates Allowed of the EMS Ingate Allocation being used.", example="10")
    @Digits(integer=CommonConstants.TOTAL_INGATES_ALLOWED_MAX_SIZE, fraction=0, message="'totalIngatesAllowed' length cannot have more than 10 digits")
    @Min(value =CommonConstants.TOTAL_INGATES_ALLOWED_MIN_SIZE , message = "'totalIngatesAllowed' value must be greater than 0")
	private Integer totalIngatesAllowed;

    @Schema(required = false, description="The Number Ingated of the EMS Ingate Allocation being used.", example="3")
    @Digits(integer=CommonConstants.NUMBER_INGATED_MAX_SIZE, fraction=0, message="'numberIngated' length cannot have more than 10 digits")
    @Min(value =CommonConstants.NUMBER_INGATED_MIN_SIZE , message = "'numberIngated' value must be greater than or equal to 0")
	private Integer numberIngated;
}
