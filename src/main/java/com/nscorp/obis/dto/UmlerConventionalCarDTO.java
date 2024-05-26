package com.nscorp.obis.dto;

import java.util.List;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.domain.ConventionalEquipmentWidth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = false)
public class UmlerConventionalCarDTO extends BaseUmlerCarDTO {
	
    private List<@Valid ConventionalEquipmentWidthDTO> conventionalEquipmentWidth;

    @Schema(required = true,description="The Single/Double Well Indicator value of the Conventional Car being used.", example="S")
    @Size(min= CommonConstants.SIN_DBLE_WELL_IND_MIN_SIZE, max=CommonConstants.SIN_DBLE_WELL_IND_MAX_SIZE, message = "'singleDoubleWellInd' length should be between {min} and {max}")
    @Pattern(regexp="^(S|D)$",message="'singleDoubleWellInd' should be null or should contain only 'S' & 'D'")
    private String singleDoubleWellInd;

    @Schema(required = false,description="The Minimum Equipment Length of the Conventional Car being used.", example="40")
    @Digits(integer=CommonConstants.MIN_EQ_LEN_MAX_SIZE, fraction=0,message="'minEqLength' cannot have more than 5 digits")
    @Min(value = 0, message = "'minEqLength' value must not be negative")
    @Max(value = CommonConstants.SMALL_INT_DATA_TYPE_MAX_VALUE, message = "'minEqLength' value cannot be more than 32,767")
    private Integer minEqLength;

    @Schema(required = false,description="The Maximum Equipment Length of the Conventional Car being used.", example="53")
    @Digits(integer=CommonConstants.MAX_EQ_LEN_MAX_SIZE, fraction=0,message="'maxEqLength' cannot have more than 5 digits")
    @Min(value = 0, message = "'maxEqLength' value must not be negative")
    @Max(value = CommonConstants.SMALL_INT_DATA_TYPE_MAX_VALUE, message = "'maxEqLength' value cannot be more than 32,767")
    private Integer maxEqLength;

    @Schema(required = false,description="The Minimum Trailer Length of the Conventional Car being used.", example="28")
    @Digits(integer=CommonConstants.MIN_TRLR_LEN_MAX_SIZE, fraction=0,message="'minTrailerLength' cannot have more than 4 digits")
    @Min(value = 0, message = "'minTrailerLength' value must not be negative")
    private Integer minTrailerLength;

    @Schema(required = false,description="The Maximum Trailer Length of the Conventional Car being used.", example="28")
    @Digits(integer=CommonConstants.MAX_TRLR_LEN_MAX_SIZE, fraction=0,message="'maxTrailerLength' cannot have more than 4 digits")
    @Min(value = 0, message = "'maxTrailerLength' value must not be negative")
    private Integer maxTrailerLength;

    @Schema(required = false,description="The Aggregate Length of the Conventional Car being used.", example="85")
    @Digits(integer=CommonConstants.AGGRGTE_LEN_MAX_SIZE, fraction=0,message="'aggregateLength' cannot have more than 5 digits")
    @Min(value = 0, message = "'aggregateLength' value must not be negative")
    private Integer aggregateLength;

    @Schema(required = false,description="The Total COFC Length of the Conventional Car being used.", example="85")
    @Digits(integer=CommonConstants.TOT_COFC_LEN_MAX_SIZE, fraction=0,message="'totCofcLength' cannot have more than 5 digits")
    @Min(value = 0, message = "'totCofcLength' value must not be negative")
    @Max(value = CommonConstants.SMALL_INT_DATA_TYPE_MAX_VALUE, message = "'totCofcLength' value cannot be more than 32,767")
    private Integer totCofcLength;

    @Schema(required = false,description="The Accept 2-20’ Containers Indicator indicates if the railcar can carry two 20 feet containers", example="Y")
    @Size(min= CommonConstants.ACCPT_2_20_C_IND_MIN_SIZE, max=CommonConstants.ACCPT_2_20_C_IND_MAX_SIZE, message = "'accept2c20Ind' length should be between {min} and {max}")
    @Pattern(regexp="^(Y|N)$",message="'accept2c20Ind' should be null or should contain only 'Y' or 'N'")
    private String accept2c20Ind;

    @Schema(required = false,description="The Accept 3-28’ Trailers Indicator indicates if the railcar can carry three 28 feet trailers", example="Y")
    @Size(min= CommonConstants.ACCPT_3_28_T_IND_MIN_SIZE, max=CommonConstants.ACCPT_3_28_T_IND_MAX_SIZE, message = "'accept3t28Ind' length should be between {min} and {max}")
    @Pattern(regexp="^(Y|N)$",message="'accept3t28Ind' should be null or should contain only 'Y' or 'N'")
    private String accept3t28Ind;

    @Schema(required = false,description="The Accept Nose Mounted Reefer indicates if the railcar can carry nose mounted reefer unit", example="Y")
    @Size(min= CommonConstants.ACCPT_NOSE_MNT_REEFER_MIN_SIZE, max=CommonConstants.ACCPT_NOSE_MNT_REEFER_MAX_SIZE, message = "'acceptNoseMountedReefer' length should be between {min} and {max}")
    @Pattern(regexp="^(Y|N)$",message="'acceptNoseMountedReefer' should be null or should contain only 'Y' or 'N'")
    private String acceptNoseMountedReefer;

    @Schema(required = false,description="The Reefer Only in “A” Hitch Position indicates if the railcar can carry reefer unit only in “A” hitch positions", example="Y")
    @Size(min= CommonConstants.REEFER_A_WELL_IND_MIN_SIZE, max=CommonConstants.REEFER_A_WELL_IND_MAX_SIZE, message = "'reeferAwellInd' length should be between {min} and {max}")
    @Pattern(regexp="^(Y|N)$",message="'reeferAwellInd' should be null or should contain only 'Y' or 'N'")
    private String reeferAwellInd;

    @Schema(required = false,description="The Reefer Allowed Only for TOFC indicates if the reefer unit is only allowed to be carried as a TOFC", example="Y")
    @Size(min= CommonConstants.REEFER_TOFC_IND_MIN_SIZE, max=CommonConstants.REEFER_TOFC_IND_MAX_SIZE, message = "'reeferTofcInd' length should be between {min} and {max}")
    @Pattern(regexp="^(Y|N)$",message="'reeferTofcInd' should be null or should contain only 'Y' or 'N'")
    private String reeferTofcInd;

    @Schema(required = false,description="The Reefer Not Allowed for 40’ TOFC if Paired with 45’ TOFC indicates that the reefer unit is not allowed for a 40 feet TOFC if it is paired with a 45 feet TOFC", example="Y")
    @Size(min= CommonConstants.REEFER_T40_IND_MIN_SIZE, max=CommonConstants.REEFER_T40_IND_MAX_SIZE, message = "'noReeferT40Ind' length should be between {min} and {max}")
    @Pattern(regexp="^(Y|N)$",message="'noReeferT40Ind' should be null or should contain only 'Y' or 'N'")
    private String noReeferT40Ind;

    @Schema(required = false,description="The Max Articulated Unit Load Weight (lbs) gives the maximum combined equipment weight that the railcar can carry.", example="85")
    @Digits(integer=CommonConstants.MAX_LOAD_WGT_MAX_SIZE, fraction=0,message="'maxLoadWeight' cannot have more than 6 digits")
    @Min(value = 0, message = "'maxLoadWeight' value must not be negative")
    private Integer maxLoadWeight;

    @Schema(required = false,description="The 20’ Container Load Limit (lbs) gives the maximum possible weight of a container loaded on the railcar.", example="85")
    @Digits(integer=CommonConstants.C20_MAXWEIGHT_MAX_SIZE, fraction=0,message="'c20MaxWeight' cannot have more than 6 digits")
    @Min(value = 0, message = "'c20MaxWeight' value must not be negative")
    private Integer c20MaxWeight;

}
