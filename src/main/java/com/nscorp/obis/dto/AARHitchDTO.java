package com.nscorp.obis.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
@EqualsAndHashCode(callSuper=false)
public class AARHitchDTO extends AuditInfoDTO {
	
	@NotBlank(message = "'aarType' should not be Blank or Null.")
//	@NotNull(message = "Car Higher Equipment Type should not be Null.")
	@Schema(required = true,description="The AAR Type is being used.", example="P110")
	@Size(max=CommonConstants.AAR_TP_MAX_SIZE, message = "'aarType' size should not be greater than {max}")
	private String aarType;
	
	@NotBlank(message = "'hitchLocation' should not be Blank or Null.")
//	@NotNull(message = "Car Higher Equipment Type should not be Null.")
	@Schema(required = true,description="The Hitch Location is being used.", example="P110")
	@Size(max=CommonConstants.HCH_LOC_MAX_SIZE, message = "'hitchLocation' size should not be greater than {max}")
	private String hitchLocation;

}
