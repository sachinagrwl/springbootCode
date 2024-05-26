package com.nscorp.obis.dto;

import java.math.BigDecimal;

import com.nscorp.obis.common.CommonConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = false)
public class BaseUmlerCarDTO extends AuditInfoDTO {

	@Schema(required = true, description = "The Umler Id of the Railcar being used.", example = "1.4163614650827E13")
	private Long umlerId;

	@Schema(required = false, description = "The Single Multiple AAR Indicator of the Railcar being used.", example = "M")
	@Size(min = CommonConstants.SIN_MUL_AAR_IND_MIN, max = CommonConstants.SIN_MUL_AAR_IND_MAX, message = "'singleMultipleAarInd' size should be between {min} and {max}")
	private String singleMultipleAarInd;

	@Schema(required = false, description = "The AAR Type of the Railcar being used.", example = "P870")
	@Size(min = CommonConstants.AAR_TP_MIN, max = CommonConstants.AAR_TP_MAX, message = "'aarType' size should be between {min} and {max}")
	private String aarType;

	@Schema(required = false, description = "The AAR Code of the Railcar being used.", example = "P")
	@Size(min = CommonConstants.AAR_CD_MIN, max = CommonConstants.AAR_CD_MAX, message = "'aarCd' size should be between {min} and {max}")
	private String aarCd;

	@Schema(required = false, description = "The AAR 1st Number Low of the Railcar being used.", example = "1")
	@Size(min = CommonConstants.AAR_1ST_NO_LOW_MIN, max = CommonConstants.AAR_1ST_NO_LOW_MAX, message = "'aar1stNoLow' size should be between {min} and {max}")
	@Pattern(regexp = "^[0-9]*$", message = "'aar1stNoLow' field allows only Numeric values.")
	private String aar1stNoLow;

	@Schema(required = false, description = "The AAR 1st Number High of the Railcar being used.", example = "4")
	@Size(min = CommonConstants.AAR_1ST_NO_HIGH_MIN, max = CommonConstants.AAR_1ST_NO_HIGH_MAX, message = "'aar1stNoHigh' size should be between {min} and {max}")
	@Pattern(regexp = "^[0-9]*$", message = "'aar1stNoHigh' field allows only Numeric values.")
	private String aar1stNoHigh;

	@Schema(required = false, description = "The AAR 2nd Number Low of the Railcar being used.", example = "1")
	@Size(min = CommonConstants.AAR_2ND_NO_LOW_MIN, max = CommonConstants.AAR_2ND_NO_LOW_MAX, message = "'aar2ndNoLow' size should be between {min} and {max}")
	@Pattern(regexp = "^[0-9]*$", message = "'aar2ndNoLow' field allows only Numeric values.")
	private String aar2ndNoLow;

	@Schema(required = false, description = "The AAR 2nd Number High of the Railcar being used.", example = "3")
	@Size(min = CommonConstants.AAR_2ND_NO_HIGH_MIN, max = CommonConstants.AAR_2ND_NO_HIGH_MAX, message = "'aar2ndNoHigh' size should be between {min} and {max}")
	@Pattern(regexp = "^[0-9]*$", message = "'aar2ndNoHigh' field allows only Numeric values.")
	private String aar2ndNoHigh;

	@Schema(required = false, description = "The AAR 3rd Number of the Railcar being used.", example = "5")
	@Size(min = CommonConstants.AAR_3RD_NO_MIN, max = CommonConstants.AAR_3RD_NO_MAX, message = "'aar3rdNo' size should be between {min} and {max}")
	@Pattern(regexp = "^[0-9]*$", message = "'aar3rdNo' field allows only Numeric values.")
	private String aar3rdNo;

	@Schema(required = false, description = "The Car Init of the Railcar being used.", example = "DTTX")
	@Size(min = CommonConstants.CAR_INIT_MIN_SIZE, max = CommonConstants.CAR_INIT_MAX_SIZE, message = "'carInit' size should be between {min} and {max}")
	@Pattern(regexp = "^[a-zA-Z]*$", message = "'carInit' field allows only Alphabets.")
	private String carInit;

	@Schema(required = false, description = "The Car Low Number of the Railcar being used.", example = "61500")
	@Digits(integer = CommonConstants.CAR_NR_LOW_MAX_SIZE, fraction = 0, message = "'carLowNr' cannot have more than 6 digits")
	@Min(value = 1, message = "'carLowNr' value must be greater than or equal to 1")
	private BigDecimal carLowNr;

	@Schema(required = false, description = "The Car High Number of the Railcar being used.", example = "62179")
	@Digits(integer = CommonConstants.CAR_NR_HIGH_MAX_SIZE, fraction = 0, message = "'carHighNr' cannot have more than 6 digits")
	@Min(value = 1, message = "'carHighNr' value must be greater than or equal to 1")
	private BigDecimal carHighNr;

	@Schema(required = false, description = "The Car Owner of the Railcar being used.", example = "TTX")
	@Size(min = CommonConstants.CONV_CAR_OWNER_MIN_SIZE, max = CommonConstants.CONV_CAR_OWNER_MAX_SIZE, message = "'carOwner' size should be between {min} and {max}")
	private String carOwner;

	@Schema(required = false, description = "The maximum permitted length for all the equipment being carried as COFC", example = "TTX")
	@Size(min = CommonConstants.TOFC_COFC_IND_MIN_SIZE, max = CommonConstants.TOFC_COFC_IND_MAX_SIZE, message = "'tofcCofcInd' size should be between {min} and {max}")
	@Pattern(regexp = "^(B|C|T)$", message = "'tofcCofcInd' should be null or should contain only 'B', 'C' & 'T'")
	private String tofcCofcInd;

	@Schema(required = false, description = "The Minimum Equipment Width of the Railcar being used.", example = "40")
	@Digits(integer = CommonConstants.MIN_EQ_WIDTH_MAX_SIZE, fraction = 0, message = "'minEqWidth' cannot have more than 5 digits")
	@Min(value = 0, message = "'minEqWidth' value must be greater than or equal to 0")
	@Max(value = CommonConstants.SMALL_INT_DATA_TYPE_MAX_VALUE, message = "'minEqWidth' value cannot be more than 32,767")
	private Integer minEqWidth;

	@Schema(required = false, description = "The Maximum Equipment Width of the Railcar being used.", example = "53")
	@Digits(integer = CommonConstants.MAX_EQ_WIDTH_MAX_SIZE, fraction = 0, message = "'maxEqWidth' cannot have more than 5 digits")
	@Min(value = 0, message = "'maxEqWidth' value must be greater than or equal to 0")
	@Max(value = CommonConstants.SMALL_INT_DATA_TYPE_MAX_VALUE, message = "'maxEqWidth' value cannot be more than 32,767")
	private Integer maxEqWidth;

}
