package com.nscorp.obis.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.CommonConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(value = "true")
public class EMSIngateRestrictionDTO extends BaseEMSIngateRestrictionDTO{

	@Schema(required = true, description="The Restriction Id being used.", example="11440062382569")
	@Digits(integer=CommonConstants.RESTRICT_ID_MAX_SIZE, fraction = 0, message=" Restriction Id cannot have more than 15 digits")
    private Long restrictId;

    @Schema(required = false,description="The Primary Line Of Business being used.")
	@Size(max=CommonConstants.PRIMARY_LOB_EMS_MAX_SIZE, message="'primaryLineOfBusiness' size should not be more than {max}")
    private String primaryLineOfBusiness;
    
    @Schema(required = false,description="The Equipment Type being used.")
	@Size(max=CommonConstants.EQ_TP_MAX_SIZE, message="'equipmentType' size should not be more than {max}")
    private String equipmentType;
}
