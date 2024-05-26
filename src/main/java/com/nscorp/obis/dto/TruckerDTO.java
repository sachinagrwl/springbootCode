package com.nscorp.obis.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import com.nscorp.obis.common.CommonConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class TruckerDTO {
	@NotBlank(message = "'truckerCode' should not be Blank or Null.")
	@Schema(required = true,description="The Trucker Code is being used.", example="P110")
	@Size(max=CommonConstants.AAR_TP_MAX_SIZE, message = "'truckerCode' size should not be greater than {max}")
	private String truckerCode;
	
	@NotBlank(message = "'truckerName' should not be Blank or Null.")
	@Schema(required = true,description="The Trucker Name is being used.", example="P110")
	@Size(max=CommonConstants.HCH_LOC_MAX_SIZE, message = "'truckerName' size should not be greater than {max}")
	private String truckerName;
}
