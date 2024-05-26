package com.nscorp.obis.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.nscorp.obis.common.CommonConstants;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PoolTypeDTO extends AuditInfoDTO {
	
	@NotBlank(message = "'poolTp' value should not be Blank or Null.")
	@Schema(required = false, description = "This is the short unique identifier (code) for the pool type", example = "PT")
	@Size(max= CommonConstants.POOL_RSRV_TP_MAX_SIZE, message = "'poolTp' length should not be greater than {max}")
	private String poolTp;

	@NotBlank(message = "'poolTpDesc' value should not be Blank or Null.")
	@Schema(required = true, description = "This gives the pool type", example = "PRIVATE")
	@Size(max=CommonConstants.RSRV_TP_MAX_SIZE, message = "'poolTpDesc' length should not be greater than {max}")
	private String poolTpDesc;

	@NotBlank(message = "'rsrvInd' value should not be Blank or Null.")
	@Schema(required = true, description = "This indicates if shipment creation should be suspended for members of the pool", example = "Y")
	@Size(max = CommonConstants.IND_MAX_SIZE, message="'rsrvInd' size cannot have more than {max}")
	@Pattern(regexp="^(Y|N)$",message="Only Y and N is allowed")
	private String rsrvInd;

	@NotBlank(message = "'advRqdInd' value should not be Blank or Null.")
	@Schema(required = true, description = "This indicates if a reservation is required to outgate equipment from the pool.", example = "Y")
	@Size(max = CommonConstants.IND_MAX_SIZE, message="'advRqdInd' size cannot have more than {max}")
	@Pattern(regexp="^(Y|N)$",message="Only Y and N is allowed")
	private String advRqdInd;
	
//	@NotBlank(message = "'ADV ALLOW' value should not be Blank or Null.")
	@Schema(required = true, description = "This indicates if a reservation/ pickup number can be entered prior to outgate.", example = "Y")
	@Size(max = CommonConstants.IND_MAX_SIZE, message="'advAllowInd' size cannot have more than {max}")
	@Pattern(regexp="^(Y|N)$",message="Only Y and N is allowed")
	private String advAllowInd;

	@NotBlank(message = "'advOverride' value should not be Blank or Null.")
	@Schema(required = true, description = "This indicates that the User, at outgate, can override the requirement that a reservation exists for this pool", example = "Y")
	@Size(max = CommonConstants.IND_MAX_SIZE, message="'advOverride' size cannot have more than {max}")
	@Pattern(regexp="^(Y|N)$",message="Only Y and N is allowed")
	private String advOverride;

	@NotBlank(message = "'puRqdInd' value should not be Blank or Null.")
	@Schema(required = true, description = "This indicates if a pickup number is required when outgating an empty from the pool.", example = "Y")
	@Size(max = CommonConstants.IND_MAX_SIZE, message="'puRqdInd' size cannot have more than {max}")
	@Pattern(regexp="^(Y|N)$",message="Only Y and N is allowed")
	private String puRqdInd;

	@Schema(required = false, description = "This indicates if a trucker must be defined as having access to the pool to outgate any equipment from the pool (loaded or empty).", example = "Y")
	@Size(max = CommonConstants.IND_MAX_SIZE, message="'puAllowInd' size cannot have more than {max}")
	@Pattern(regexp="^(Y|N|Null)$",message="Only Y, N and null is allowed")
	private String puAllowInd;

	@NotBlank(message = "'agreementRqd' value should not be Blank or Null.")
	@Schema(required = true, description = "This indicates if a trucker must be defined as having access to the pool to outgate any equipment from the pool (loaded or empty).", example = "Y")
	@Size(max = CommonConstants.IND_MAX_SIZE, message="'agreementRqd' size cannot have more than {max}")
	@Pattern(regexp="^(Y|N)$",message="Only Y and N is allowed")
	private String agreementRqd;

	@NotBlank(message = "'udfParam1' value should not be Blank or Null.")
	@Schema(required = true, description = "This indicates if equipment details must be specified when entering a reservation/ pickup number prior to outgate.", example = "Y")
	@Size(max = CommonConstants.IND_MAX_SIZE, message="'udfParam1' size cannot have more than {max}")
	@Pattern(regexp="^(Y|N)$",message="Only Y and N is allowed")
	private String udfParam1;

	@Schema(required = false, description = "This indicates if equipment details must be specified when entering a reservation/ pickup number prior to outgate.", example = "Y")
	@Size(max = CommonConstants.IND_MAX_SIZE, message="'udfParam2' size cannot have more than {max}")
	@Pattern(regexp="^(Y|N|Null)$",message="Only Y, N and null is allowed")
	private String udfParam2;

	@Schema(required = false, description = "This indicates if equipment details must be specified when entering a reservation/ pickup number prior to outgate.", example = "Y")
	@Size(max = CommonConstants.IND_MAX_SIZE, message="'udfParam3' size cannot have more than {max}")
	@Pattern(regexp="^(Y|N|Null)$",message="Only Y, N and null is allowed")
	private String udfParam3;

	@Schema(required = false, description = "This indicates if equipment details must be specified when entering a reservation/ pickup number prior to outgate.", example = "Y")
	@Size(max = CommonConstants.IND_MAX_SIZE, message="'udfParam4' size cannot have more than {max}")
	@Pattern(regexp="^(Y|N|Null)$",message="Only Y, N and null is allowed")
	private String udfParam4;

	@Schema(required = false, description = "This indicates if equipment details must be specified when entering a reservation/ pickup number prior to outgate.", example = "Y")
	@Size(max = CommonConstants.IND_MAX_SIZE, message="'udfParam5' size cannot have more than {max}")
	@Pattern(regexp="^(Y|N|Null)$",message="Only Y, N and null is allowed")
	private String udfParam5;

	@NotBlank(message = "'multiRsrvInd' value should not be Blank or Null.")
	@Schema(required = true, description = "This indicates if one reservation/ pickup number can be used for multiple pieces of equipment.", example = "Y")
	@Size(max = CommonConstants.IND_MAX_SIZE, message="'multiRsrvInd' size cannot have more than {max}")
	@Pattern(regexp="^(Y|N)$",message="Only Y and N is allowed")
	private String multiRsrvInd;
}
