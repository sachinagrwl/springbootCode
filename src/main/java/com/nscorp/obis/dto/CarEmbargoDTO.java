package com.nscorp.obis.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.domain.AuditInfo;
import com.nscorp.obis.domain.Station;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class CarEmbargoDTO extends AuditInfo {

	@Schema(required = true, description="The Embargo Id of the RailCar being used.", example="99990010120629")
    private Long embargoId;

	@Schema(required = false,description="The American Association Of Railroads Type being used.", example="P121")
	@Size(min=CommonConstants.AAR_TP_MIN_SIZE, max=CommonConstants.AAR_TP_MAX_SIZE, message="AAR Type size should be between {min} and {max}")
    private String aarType;

	@NotBlank(message = "'roadName' should not be Blank or Null.")
	@Schema(required = false,description="This gives the name initials of the railcar associated with the terminal.", example="TEST")
	@Size(min = CommonConstants.ROAD_NAME_MIN_SIZE, max = CommonConstants.ROAD_NAME_MAX_SIZE, message = "'roadName' size should be between {min} and {max}")
	@Pattern(regexp="^[a-zA-Z0-9]*$",message="Only Alpha Numeric values is allowed.")
    private String roadName;

	@NotBlank(message = "'restriction' should not be Blank or Null.")
    @Schema(required = true,description="The Restriction of the railcar being used.", example="C")
	@Size(max = CommonConstants.RESTRICTION_MAX_SIZE, message="'restriction' size cannot have more than {max}")
	@Pattern(regexp="^(Y|N)$",message="Only Y and N is allowed")
    private String restriction;

	@Schema(required = false,description="The Description is being used.", example="DO NOT LOAD 20'. NOT 20' CAPABLE")
	@Size(max = CommonConstants.EMBARGO_DESCRIPTION_MAX_SIZE, message="'description' size cannot have more than {max}")
    private String description;

//	@Schema(required = false,description="The Embargo Id of the Terminal being used.", example="1.4414106360672E13")
//	@Digits(integer=CommonConstants.EMBARGO_TERM_ID_MAX_SIZE, fraction=0,message="'embargoTerminalId' cannot have more than 53 digits")
//	@Min(value = 0, message = "'embargoTerminalId' value must be greater than or equal to 0")
//    private Long embargoTerminalId;
    
    private Station terminal;

    @Schema(required = false,description="The Initializer value of the equipment being used.", example="TEST")
	@Size(min = CommonConstants.EQ_INIT_MIN_SIZE, max = CommonConstants.EQ_INIT_MAX_SIZE, message = "'equipmentInit' size should be between {min} and {max}")
	@Pattern(regexp="^[a-zA-Z]*$",message="'equipmentInit' should not contain Blank or Numbers or Special Characters")
    private String equipmentInit;

    @Schema(required = false,description="The Minimum height of the equipment being used.", example="62121")
	@Digits(integer=CommonConstants.EQ_NR_LOW_MAX_SIZE, fraction=0,message="'equipmentNumberLow' cannot have more than 6 digits")
	@Min(value = 1, message = "'equipmentNumberLow' value cannot be less than 1")
    private BigDecimal equipmentNumberLow;

    @Schema(required = false,description="The Maximum height of the equipment being used.", example="62200")
	@Digits(integer=CommonConstants.EQ_NR_HIGH_MAX_SIZE, fraction=0,message="'equipmentNumberHigh' cannot have more than 6 digits")
	@Min(value = 1, message = "'equipmentNumberHigh' value cannot be less than 1")
    private BigDecimal equipmentNumberHigh;
}
