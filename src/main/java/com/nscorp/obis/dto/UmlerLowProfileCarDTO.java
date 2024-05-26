package com.nscorp.obis.dto;

import java.util.List;

import com.nscorp.obis.common.CommonConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = false)
public class UmlerLowProfileCarDTO extends BaseUmlerCarDTO {

	private List<LowProfileEquipmentWidthDTO> lowProfileEquipmentWidth;

	@Schema(required = false,description="The Number of Platforms of the Low Profile Car being used.", example="1")
	@Digits(integer= CommonConstants.NR_OF_PLATFORM_MAX_SIZE, fraction=0,message="'numberOfPlatform' cannot have more than 5 digits")
	@Min(value = 0, message = "'numberOfPlatform' value must not be negative")
	private Integer numberOfPlatform;

	@Schema(required = false,description="The Platform Car Indicator indicates if the Load Configuration is being defined Per Car or Per Platform of the Low Profile Car being used.", example="P")
	@Size(min= CommonConstants.PLATFORM_CAR_IND_MIN_SIZE, max=CommonConstants.PLATFORM_CAR_IND_MAX_SIZE, message = "'platCarInd' length should be between {min} and {max}")
	@Pattern(regexp="^(P|C)$",message="'platCarInd' should either be null or should contain only 'P' & 'C'")
	private String platCarInd;

	@Schema(required = false,description="The T Well Indicator of the Low Profile Car being used.", example="Y")
	@Size(min= CommonConstants.T_WELL_IND_MIN_SIZE, max=CommonConstants.T_WELL_IND_MAX_SIZE, message = "'tWellInd' length should be between {min} and {max}")
	@Pattern(regexp="^(Y|N)$",message="'tWellInd' should either be null or should contain only 'Y' & 'N'")
	private String tWellInd;

	@Schema(required = false,description="The Q Well Indicator of the Low Profile Car being used.", example="Y")
	@Size(min= CommonConstants.Q_WELL_IND_MIN_SIZE, max=CommonConstants.Q_WELL_IND_MAX_SIZE, message = "'qWellInd' length should be between {min} and {max}")
	@Pattern(regexp="^(Y|N)$",message="'qWellInd' should either be null or should contain only 'Y' & 'N'")
	private String qWellInd;

	@Schema(required = false,description="The 3 Length Units Minimum Equipment Length indicate the equipment length restrictions, in feet, when the railcar is accommodating 3 units of the Low Profile Car being used.", example="28")
	@Digits(integer= CommonConstants.L3_MIN_EQ_LENGTH_MAX_SIZE, fraction=0,message="'l3MinEqLength' cannot have more than 5 digits")
	@Min(value = 0, message = "'l3MinEqLength' value must not be negative")
	private Integer l3MinEqLength;

	@Schema(required = false,description="The 3 Length Units Maximum Equipment Length indicate the equipment length restrictions, in feet, when the railcar is accommodating 3 units of the Low Profile Car being used.", example="53")
	@Digits(integer= CommonConstants.L3_MAX_EQ_LENGTH_MAX_SIZE, fraction=0,message="'l3MaxEqLength' cannot have more than 5 digits")
	@Min(value = 0, message = "'l3MaxEqLength' value must not be negative")
	private Integer l3MaxEqLength;

	@Schema(required = false,description="This field indicates the length restrictions, in feet, for the middle container when the railcar is accommodating 3 units of the Low Profile Car being used.", example="48")
	@Digits(integer= CommonConstants.L3_CENTER_MIN_LENGTH_MAX_SIZE, fraction=0,message="'l3CenterMinLength' cannot have more than 5 digits")
	@Min(value = 0, message = "'l3CenterMinLength' value must not be negative")
	private Integer l3CenterMinLength;

	@Schema(required = false,description="This field indicates the equipment length restrictions, in feet, when the railcar is accommodating 4 units of the Low Profile Car being used.", example="48")
	@Digits(integer= CommonConstants.L4_MIN_EQ_LENGTH_MAX_SIZE, fraction=0,message="'l4MinEqLength' cannot have more than 5 digits")
	@Min(value = 0, message = "'l4MinEqLength' value must not be negative")
	private Integer l4MinEqLength;

	@Schema(required = false,description="This field indicates the equipment length restrictions, in feet, when the railcar is accommodating 4 units of the Low Profile Car being used.", example="48")
	@Digits(integer= CommonConstants.L4_MAX_EQ_LENGTH_MAX_SIZE, fraction=0,message="'l4MaxEqLength' cannot have more than 5 digits")
	@Min(value = 0, message = "'l4MaxEqLength' value must not be negative")
	private Integer l4MaxEqLength;

	@Schema(required = false,description="This field indicates total equipment length, in feet, when the railcar is accommodating 4 units of the Low Profile Car being used.", example="48")
	@Digits(integer= CommonConstants.L4_2_UNITS_TOTAL_LENGTH_MAX_SIZE, fraction=0,message="'l42UnitsTotLgth' cannot have more than 5 digits")
	@Min(value = 0, message = "'l42UnitsTotLgth' value must not be negative")
	private Integer l42UnitsTotalLgth;

	@Schema(required = false,description="The Accept 2-20’ Containers Indicator indicates if the railcar can carry two 20 feet containers", example="Y")
	@Size(min= CommonConstants.ACCPT_2_20_C_IND_MIN_SIZE, max=CommonConstants.ACCPT_2_20_C_IND_MAX_SIZE, message = "'accept2c20Ind' length should be between {min} and {max}")
	@Pattern(regexp="^(Y|N)$",message="'accept2c20Ind' should be null or should contain only 'Y' or 'N'")
	private String accept2c20Ind;

	@Schema(required = false,description="The Accept 3-28’ Trailers Indicator indicates if the railcar can carry three 28 feet trailers", example="Y")
	@Size(min= CommonConstants.ACCPT_3_28_T_IND_MIN_SIZE, max=CommonConstants.ACCPT_3_28_T_IND_MAX_SIZE, message = "'accept3t28Ind' length should be between {min} and {max}")
	@Pattern(regexp="^(Y|N)$",message="'accept3t28Ind' should be null or should contain only 'Y' or 'N'")
	private String accept3t28Ind;

	@Schema(required = false,description="The Minimum Equipment Length of the Low Profile Car being used.", example="40")
	@Digits(integer=CommonConstants.MIN_EQ_LEN_MAX_SIZE, fraction=0,message="'minEqLength' cannot have more than 5 digits")
	@Min(value = 0, message = "'minEqLength' value must not be negative")
	private Integer minEqLength;

	@Schema(required = false,description="The Maximum Equipment Length of the Low Profile Car being used.", example="53")
	@Digits(integer=CommonConstants.MAX_EQ_LEN_MAX_SIZE, fraction=0,message="'maxEqLength' cannot have more than 5 digits")
	@Min(value = 0, message = "'maxEqLength' value must not be negative")
	private Integer maxEqLength;

	@Schema(required = false,description="The Minimum Trailer Length of the Low Profile Car being used.", example="28")
	@Digits(integer=CommonConstants.MIN_TRLR_LEN_MAX_SIZE, fraction=0,message="'minTrailerLength' cannot have more than 4 digits")
	@Min(value = 0, message = "'minTrailerLength' value must not be negative")
	private Integer minTrailerLength;

	@Schema(required = false,description="The Maximum Trailer Length of the Low Profile Car being used.", example="28")
	@Digits(integer=CommonConstants.MAX_TRLR_LEN_MAX_SIZE, fraction=0,message="'maxTrailerLength' cannot have more than 4 digits")
	@Min(value = 0, message = "'maxTrailerLength' value must not be negative")
	private Integer maxTrailerLength;

	@Schema(required = false,description="The field indicates that the railcar accepts container pairs in All Wells, End Well or End and D Well.", example="L")
	@Size(min= CommonConstants.CONT_PAIRS_WELL_IND_MIN_SIZE, max=CommonConstants.CONT_PAIRS_WELL_IND_MAX_SIZE, message = "'cntPairsWellInd' length should be between {min} and {max}")
	@Pattern(regexp="^(L|N|T)$",message="'cntPairsWellInd' should be null or should contain only 'L', 'N' & 'T")
	private String cntPairsWellInd;

	@Schema(required = false,description="This field indicates the combined length restrictions, in feet, for a container pair", example="20")
	@Digits(integer=CommonConstants.CONT_PAIRS_MIN_LENGTH_MAX_SIZE, fraction=0,message="'cntPairsMinLength' cannot have more than 4 digits")
	@Min(value = 0, message = "'cntPairsMinLength' value must not be negative")
	private Integer cntPairsMinLength;

	@Schema(required = false,description="This field indicates the combined length restrictions, in feet, for a container pair", example="20")
	@Digits(integer=CommonConstants.CONT_PAIRS_MAX_LENGTH_MAX_SIZE, fraction=0,message="'cntPairsMaxLength' cannot have more than 4 digits")
	@Min(value = 0, message = "'cntPairsMaxLength' value must not be negative")
	private Integer cntPairsMaxLength;

	@Schema(required = false,description="The field indicate the combined length restrictions, in feet, for a trailer pair", example="28")
	@Size(min= CommonConstants.CONT_PAIRS_WELL_IND_MIN_SIZE, max=CommonConstants.CONT_PAIRS_WELL_IND_MAX_SIZE, message = "'acceptTrailerPairsInd' length should be between {min} and {max}")
	@Pattern(regexp="^(Y|N)$",message="'acceptTrailerPairsInd' should be null or should contain only 'L', 'N' & 'T")
	private String acceptTrailerPairsInd;

	@Schema(required = false,description="This field indicates the combined length restrictions, in feet, for a trailer pair", example="20")
	@Digits(integer=CommonConstants.TRAILER_PAIR_MIN_LENGTH_MAX_SIZE, fraction=0,message="'trailerPairsMinLength' cannot have more than 5 digits")
	@Min(value = 0, message = "'trailerPairsMinLength' value must not be negative")
	private Integer trailerPairsMinLength;

	@Schema(required = false,description="This field indicates the combined length restrictions, in feet, for a trailer pair", example="20")
	@Digits(integer=CommonConstants.TRAILER_PAIR_MAX_LENGTH_MAX_SIZE, fraction=0,message="'trailerPairsMaxLength' cannot have more than 5 digits")
	@Min(value = 0, message = "'trailerPairsMaxLength' value must not be negative")
	private Integer trailerPairsMaxLength;

	@Schema(required = false,description="The Accept Nose Mounted Reefer indicates if the railcar can carry nose mounted reefer unit", example="Y")
	@Size(min= CommonConstants.ACCPT_NOSE_MNT_REEFER_MIN_SIZE, max=CommonConstants.ACCPT_NOSE_MNT_REEFER_MAX_SIZE, message = "'acceptNoseMountedReefer' length should be between {min} and {max}")
	@Pattern(regexp="^(Y|N)$",message="'acceptNoseMountedReefer' should be null or should contain only 'Y' or 'N'")
	private String acceptNoseMountedReefer;

	@Schema(required = false,description="The Reefer Indicator of the Low Profile being used", example="Y")
	@Size(min= CommonConstants.REEFER_WELL_IND_MIN_SIZE, max=CommonConstants.REEFER_WELL_IND_MAX_SIZE, message = "'reeferWellInd' length should be between {min} and {max}")
	@Pattern(regexp="^(Y|N)$",message="'reeferWellInd' should be null or should contain only 'Y' or 'N'")
	private String reeferWellInd;

	@Schema(required = false,description="This field indicates that the reefer unit is not allowed for a 40 feet TOFC if it is paired with a 45 feet TOFC", example="Y")
	@Size(min= CommonConstants.REEFER_T40_IND_MIN_SIZE, max=CommonConstants.REEFER_T40_IND_MAX_SIZE, message = "'noReeferT40Ind' length should be between {min} and {max}")
	@Pattern(regexp="^(Y|N)$",message="'noReeferT40Ind' should be null or should contain only 'Y' or 'N'")
	private String noReeferT40Ind;

	@Schema(required = false,description="The Accept Chassis Bumper of the Low Profile Car being used", example="Y")
	@Size(min= CommonConstants.ACCEPT_CHASSIS_BUMPER_MIN_SIZE, max=CommonConstants.ACCEPT_CHASSIS_BUMPER_MAX_SIZE, message = "'acceptChasBumper' length should be between {min} and {max}")
	private String acceptChasBumper;

	@Schema(required = false,description="The Max Articulated Unit Load Weight (lbs) gives the maximum combined equipment weight that the railcar can carry.", example="85")
	@Digits(integer=CommonConstants.MAX_LOAD_WGT_MAX_SIZE, fraction=0,message="'maxLoadWeight' cannot have more than 6 digits")
	@Min(value = 0, message = "'maxLoadWeight' value must not be negative")
	private Integer maxLoadWeight;

	@Schema(required = false,description="The 20’ Container Load Limit (lbs) gives the maximum possible weight of a container loaded on the railcar.", example="85")
	@Digits(integer=CommonConstants.C20_MAXWEIGHT_MAX_SIZE, fraction=0,message="'c20MaxWeight' cannot have more than 6 digits")
	@Min(value = 0, message = "'c20MaxWeight' value must not be negative")
	private Integer c20MaxWeight;

}
