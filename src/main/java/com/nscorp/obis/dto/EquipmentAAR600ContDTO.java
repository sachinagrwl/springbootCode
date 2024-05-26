package com.nscorp.obis.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.CommonConstants;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

import javax.validation.constraints.*;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(value = "true")
public class EquipmentAAR600ContDTO extends AuditInfoDTO {

	@NotBlank(message = "'equipInit' should not be Blank or Null.")
	@Schema(required = true,description="The initial of the equipment being used.", example="C")
	@Size(min=CommonConstants.EQ_INIT_MIN_SIZE, max=CommonConstants.EQ_INIT_MAX_SIZE, message ="'equipInit' must be between {min} and {max}")
	@Pattern(regexp="^[a-zA-Z]*$",message="'equipInit' should not contain Blank or Numbers or Special Characters")
    private String equipInit;

    @NotNull(message = "'beginningEqNr' should not be Null.")
	@Schema(required = true,description="The beginning number of the equipment being used.", example="506364")
	@Digits(integer=CommonConstants.BEG_EQ_NM_MAX_SIZE, fraction=0, message="'beginningEqNr' cannot have more than 6 digits")
	@Min(value = 1, message = "'beginningEqNr' must be greater than 0")
    private BigDecimal beginningEqNr;

    @NotNull(message = "'endEqNbr' should not be Null.")
	@Schema(required = true,description="The end number of the equipment being used.", example="506364")
	@Digits(integer=CommonConstants.END_EQ_NM_MAX_SIZE, fraction=0, message="'endEqNbr' cannot have more than 6 digits")
	@Min(value = 1, message = "'endEqNbr' must be greater than 0")
    private BigDecimal endEqNbr;

}
