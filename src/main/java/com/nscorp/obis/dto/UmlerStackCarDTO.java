package com.nscorp.obis.dto;

import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.nscorp.obis.common.CommonConstants;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UmlerStackCarDTO extends BaseUmlerCarDTO {

	private List<StackWellLengthDTO> stackWellLengthList;

	private List<StackEquipmentWidthDTO> stackEquipmentWidthList;

	@Schema(required = false,description="This indicates if the width restriction depends on the equipment length.", example="N")
	@Size(min= CommonConstants.LR_IND_MIN_SIZE, max=CommonConstants.LR_IND_MAX_SIZE, message = "'lengthDeterminedWidthRestrictionsInd' length should be between {min} and {max}")
	@Pattern(regexp="^(Y|N)$",message="'lengthDeterminedWidthRestrictionsInd' should be null or should contain only 'Y' or 'N'")
	private String lengthDeterminedWidthRestrictionsInd;

	@Schema(required = false,description="This indicates if the Load Configuration is being defined Per Car or Per Platform.", example="TEST")
	@Size(min= CommonConstants.PLAT_CAR_IND_MIN_SIZE, max=CommonConstants.PLAT_CAR_IND_MAX_SIZE, message = "'numberOfPlatform' length should be between {min} and {max}")
	private String numberOfPlatform;

	@Schema(required = false,description="This gives the length, in feet, of the last well.", example="40")
	@Digits(integer=CommonConstants.MIN_EQ_LEN_MAX_SIZE, fraction=0,message="'endWellLength' cannot have more than 5 digits")
    @Min(value = 0, message = "'endWellLength' value must not be negative")
	private Integer endWellLength;

	@Schema(required = false,description="This gives the length, in feet, of the middle well", example="40")
	@Digits(integer=CommonConstants.MIN_EQ_LEN_MAX_SIZE, fraction=0,message="'medWellLength' cannot have more than 5 digits")
    @Min(value = 0, message = "'medWellLength' value must not be negative")
	private Integer medWellLength;
	
	@Schema(required = false,description="The Minimum Equipment Length of the Stack Car being used.", example="40")
    @Digits(integer=CommonConstants.MIN_EQ_LEN_MAX_SIZE, fraction=0,message="'minEqLength' cannot have more than 5 digits")
    @Min(value = 0, message = "'minEqLength' value must not be negative")
	private Integer minEqLength;

	@Schema(required = false,description="The Maximum Equipment Length of the Stack Car being used.", example="53")
    @Digits(integer=CommonConstants.MAX_EQ_LEN_MAX_SIZE, fraction=0,message="'maxEqLength' cannot have more than 5 digits")
    @Min(value = 0, message = "'maxEqLength' value must not be negative")
	private Integer maxEqLength;

	@Schema(required = false,description="This indicates the Equipment Length Restriction on All Wells.", example="53")
    @Digits(integer=CommonConstants.TOP_MIN_EQ_LGTH_SIZE, fraction=0,message="'topMinEqLength' cannot have more than 5 digits")
    @Min(value = 0, message = "'topMinEqLength' value must not be negative")
	private Integer topMinEqLength;

	@Schema(required = false,description="This indicates the Equipment Length Restriction on All Wells.", example="53")
    @Digits(integer=CommonConstants.TOP_MAX_EQ_LGTH_SIZE, fraction=0,message="'topMaxEqLength' cannot have more than 5 digits")
    @Min(value = 0, message = "'topMaxEqLength' value must not be negative")
	private Integer topMaxEqLength;

	@Schema(required = false,description="This indicates the Equipment Length Restriction on Intermediate Wells.", example="53")
    @Digits(integer=CommonConstants.TOP_MAX_EQ_LGTH_SIZE, fraction=0,message="'medMinEqLength' cannot have more than 5 digits")
    @Min(value = 0, message = "'medMinEqLength' value must not be negative")
	private Integer medMinEqLength;

	@Schema(required = false,description="This indicates the Equipment Length Restriction on Intermediate Wells.", example="53")
    @Digits(integer=CommonConstants.TOP_MAX_EQ_LGTH_SIZE, fraction=0,message="'medMaxEqLength' cannot have more than 5 digits")
    @Min(value = 0, message = "'medMaxEqLength' value must not be negative")
	private Integer medMaxEqLength;

	@Schema(required = false,description="This indicates the Equipment Length Restriction on Intermediate Wells.", example="53")
    @Digits(integer=CommonConstants.TOP_MAX_EQ_LGTH_SIZE, fraction=0,message="'medTopMinEqLength' cannot have more than 5 digits")
    @Min(value = 0, message = "'medTopMinEqLength' value must not be negative")
	private Integer medTopMinEqLength;

	@Schema(required = false,description="This indicates the Equipment Length Restriction on Intermediate Wells.", example="53")
    @Digits(integer=CommonConstants.TOP_MAX_EQ_LGTH_SIZE, fraction=0,message="'medTopMaxEqLength' cannot have more than 5 digits")
    @Min(value = 0, message = "'medTopMaxEqLength' value must not be negative")
	private Integer medTopMaxEqLength;

	@Schema(required = false,description="This indicates the Equipment Length Restriction on Intermediate Wells.", example="53")
    @Digits(integer=CommonConstants.TOP_MAX_EQ_LGTH_SIZE, fraction=0,message="'med2MinEqLength' cannot have more than 5 digits")
    @Min(value = 0, message = "'med2MinEqLength' value must not be negative")
	private Integer med2MinEqLength;

	@Schema(required = false,description="This indicates the Equipment Length Restriction on Intermediate Wells.", example="53")
    @Digits(integer=CommonConstants.TOP_MAX_EQ_LGTH_SIZE, fraction=0,message="'med2MaxEqLength' cannot have more than 5 digits")
    @Min(value = 0, message = "'med2MaxEqLength' value must not be negative")
	private Integer med2MaxEqLength;

	@Schema(required = false,description="This indicates the Equipment Length Restriction on Intermediate Wells.", example="53")
    @Digits(integer=CommonConstants.TOP_MAX_EQ_LGTH_SIZE, fraction=0,message="'med2TopMinEqLength' cannot have more than 5 digits")
    @Min(value = 0, message = "'med2TopMinEqLength' value must not be negative")
	private Integer med2TopMinEqLength;

	@Schema(required = false,description="This indicates the Equipment Length Restriction on Intermediate Wells.", example="53")
    @Digits(integer=CommonConstants.TOP_MAX_EQ_LGTH_SIZE, fraction=0,message="'med2TopMaxEqLength' cannot have more than 5 digits")
    @Min(value = 0, message = "'med2TopMaxEqLength' value must not be negative")
	private Integer med2TopMaxEqLength;

	@Schema(required = false,description="This indicates the No Intermediate Length on Top Well.", example="N")
	@Size(min= CommonConstants.NO_IND_MIN_SIZE, max=CommonConstants.NO_IND_MAX_SIZE, message = "'noMedLengthOnTopInd' length should be between {min} and {max}")
	@Pattern(regexp="^(Y|N)$",message="'noMedLengthOnTopInd' should be null or should contain only 'Y' or 'N'")
	private String noMedLengthOnTopInd;

	@Schema(required = false,description="This indicates the Conditional Loading Restrictions.", example="28")
    @Digits(integer=CommonConstants.COND_MAX_EQ_LGTH_SIZE, fraction=0,message="'condMaxEqLength' cannot have more than 5 digits")
    @Min(value = 0, message = "'condMaxEqLength' value must not be negative")
	private Integer condMaxEqLength;

	@Schema(required = false,description="This indicates the Accept Container Pairs in All Well.", example="L")
	@Size(min= CommonConstants.EQ_PRS_IND_MIN_SIZE, max=CommonConstants.EQ_PRS_IND_MAX_SIZE, message = "'eqPairsWellInd' length should be between {min} and {max}")
	@Pattern(regexp="^(L|I|N)$",message="'eqPairsWellInd' should be null or should contain only 'L' or 'I' or 'N'")
	private String eqPairsWellInd;

	@Schema(required = false,description="The indicates the Minimum Length of Equipment Pairs.", example="28")
    @Digits(integer=CommonConstants.EQ_PRS_MIN_SIZE, fraction=0,message="'eqPairsMinLength' cannot have more than 5 digits")
    @Min(value = 0, message = "'eqPairsMinLength' value must not be negative")
	private Integer eqPairsMinLength;

	@Schema(required = false,description="The indicates the Maximum Length of Equipment Pairs.", example="28")
    @Digits(integer=CommonConstants.EQ_PRS_MAX_SIZE, fraction=0,message="'eqPairsMaxLength' cannot have more than 5 digits")
    @Min(value = 0, message = "'eqPairsMaxLength' value must not be negative")
	private Integer eqPairsMaxLength;

	@Schema(required = false,description="This indicates the Accept Container Pairs on Top Well Indicator.", example="N")
	@Size(min= CommonConstants.EQ_PRS_IND_MIN_SIZE, max=CommonConstants.EQ_PRS_IND_MAX_SIZE, message = "'acceptCntPairsOnTop' length should be between {min} and {max}")
	@Pattern(regexp="^(Y|N)$",message="'acceptCntPairsOnTop' should be null or should contain only 'Y' or 'N'")
	private String acceptCntPairsOnTop;

	@Schema(required = false,description="The indicates the Top Container Pairs Min Length.", example="28")
    @Digits(integer=CommonConstants.TOP_PRS_IND_MIN_SIZE, fraction=0,message="'topCntPairsMinLength' cannot have more than 5 digits")
    @Min(value = 0, message = "'topCntPairsMinLength' value must not be negative")
	private Integer topCntPairsMinLength;

	@Schema(required = false,description="The indicates the Top Container Pairs Max Length.", example="28")
    @Digits(integer=CommonConstants.TOP_PRS_IND_MAX_SIZE, fraction=0,message="'topCntPairsMaxLength' cannot have more than 5 digits")
    @Min(value = 0, message = "'topCntPairsMaxLength' value must not be negative")
	private Integer topCntPairsMaxLength;

	@Schema(required = false,description="The Minimum Trailer Length of the Stack Car being used.", example="28")
    @Digits(integer=CommonConstants.MIN_TRLR_LEN_MAX_SIZE, fraction=0,message="'minTrlrLength' cannot have more than 4 digits")
    @Min(value = 0, message = "'minTrlrLength' value must not be negative")
	private Integer minTrlrLength;

	@Schema(required = false,description="The Maximum Trailer Length of the Stack Car being used.", example="28")
    @Digits(integer=CommonConstants.MAX_TRLR_LEN_MAX_SIZE, fraction=0,message="'maxTrlrLength' cannot have more than 4 digits")
    @Min(value = 0, message = "'maxTrlrLength' value must not be negative")
	private Integer maxTrlrLength;

	@Schema(required = false,description="This indicates the Accept Trailer Pairs Indicator.", example="N")
	@Size(min= CommonConstants.ACPT_TRLR_IND_MIN_SIZE, max=CommonConstants.ACPT_TRLR_IND_MAX_SIZE, message = "'acceptTrlrPairsInd' length should be between {min} and {max}")
	@Pattern(regexp="^(Y|N)$",message="'acceptTrlrPairsInd' should be null or should contain only 'Y' or 'N'")
	private String acceptTrlrPairsInd;

	@Schema(required = false,description="The indicates the Trailer Pairs Min Length.", example="28")
    @Digits(integer=CommonConstants.TRLR_PRS_MIN_SIZE, fraction=0,message="'trlrPairsMinLength' cannot have more than 5 digits")
    @Min(value = 0, message = "'trlrPairsMinLength' value must not be negative")
	private Integer trlrPairsMinLength;

	@Schema(required = false,description="The indicates the Trailer Pairs Max Length.", example="28")
    @Digits(integer=CommonConstants.TRLR_PRS_MAX_SIZE, fraction=0,message="'trlrPairsMaxLength' cannot have more than 5 digits")
    @Min(value = 0, message = "'trlrPairsMaxLength' value must not be negative")
	private Integer trlrPairsMaxLength;

	@Schema(required = false,description="The Accept Nose Mounted Reefer indicates if the railcar can carry nose mounted reefer unit", example="Y")
    @Size(min= CommonConstants.ACCPT_NOSE_MNT_REEFER_MIN_SIZE, max=CommonConstants.ACCPT_NOSE_MNT_REEFER_MAX_SIZE, message = "'acceptNoseMountedReefer' length should be between {min} and {max}")
	@Pattern(regexp="^(Y|N)$",message="'acceptNoseMountedReefer' should be null or should contain only 'Y' or 'N'")
	private String acceptNoseMountedReefer;

	@Schema(required = false,description="The Accept Chassis Bumper of the Low Profile Car being used", example="Y")
	@Size(min= CommonConstants.ACCPT_CHAS_MIN_SIZE, max=CommonConstants.ACCPT_CHAS_MIN_SIZE, message = "'acceptChasBumper' length should be between {min} and {max}")
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
