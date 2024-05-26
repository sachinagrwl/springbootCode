package com.nscorp.obis.dto;

import com.nscorp.obis.common.CommonConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class StationRestrictionDTO extends AuditInfoDTO{
	
	@NotNull(message = "Station Cross Reference Id Should not be Null.")
	@Schema(required = true, description="The Cross Reference Id of the Station being used.", example="2.0018620102947E13")
	@Digits(integer=15, fraction = 0, message=" Reference Id cannot have more than 15 digits")
	private long stationCrossReferenceId;
	
	
//	@NotBlank(message = "Car Type Should not be Null or Blank.")
	@Schema(required = true, description="The Type of the Car being used.", example="Q112")
	@Size(max=CommonConstants.CAR_TYPE_MAX_SIZE, message = "Car Type max size should be {max}")
	private String carType;
	
//	@NotBlank(message = "Freight Type Should not be Null or Blank.")
	@Schema(required = true, description="The Type of the Freight being used.", example="Z___")
	@Size(max=CommonConstants.FREIGHT_TYPE_MAX_SIZE, message = "Freight Type size should be {max}")
	private String freightType;
}
