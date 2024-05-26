package com.nscorp.obis.dto;

import com.nscorp.obis.common.CommonConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper=false)
public class AARTypeDTO extends AuditInfoDTO {
	
	@NotBlank(message = "AAR Type Should not be Null or Blank.")
	@Schema(required = true,description="The American Association Of Railroads Type being used.", example="P121")
	@Size(min=CommonConstants.AAR_TP_MIN_SIZE, max=CommonConstants.AAR_TP_MAX_SIZE, 
	message="AAR Type size should be between {min} and {max}")
	private String aarType;
	
	@Schema(required = false,description="The American Association Of Railroads Description being used.", example="INT FLAT")
	@Size(max=CommonConstants.AAR_DESC_TYPE_MAX_SIZE, message="AAR Description size should not be more than {max}")
	@NotBlank(message = "AAR Description Should not be Null or Blank.")
	private String aarDescription;
	
	@Schema(required = false,description="The American Association Of Railroads Capacity being used.", example="1")
	@Digits(integer=CommonConstants.AAR_CAPACITY_MAX_SIZE, fraction=0,message="AAR Capacity cannot have more than 4 digits")
	private Integer aarCapacity;
	
	@Schema(required = false,description="The IM Description being used.")
	@Size(max=CommonConstants.IM_DESC_TYPE_MAX_SIZE,  message="IM Description size should not be more than {max}")
	private String imDescription;
	
	@Schema(required = false,description="The Standard AAR Type being used.")
	@Pattern(regexp="^(Y|N|)$",message="Only Y, N & Null is allowed")
	//@Size(max=CommonConstants.STANDARD_AAR_TYPE_MAX_SIZE,  message="Standard AAR Type size should not be more than {max}")
	private String standardAarType;

}
