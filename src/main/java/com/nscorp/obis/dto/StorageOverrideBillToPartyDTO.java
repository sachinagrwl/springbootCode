package com.nscorp.obis.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class StorageOverrideBillToPartyDTO extends AuditInfoDTO {
	@NotNull
	@Schema(required = true, description = "The Corporate Customer Id being used.", example = "1.639857332557E12")
	@Digits(integer = 15, fraction = 0, message = "Corporate Customer id cannot be more than 15")
	@Min(value = 1, message = "Corporate Customer id Length must be greater than 0")
	private Long corporateCustomerId;

	@NotBlank
	@Schema(required = true, description = "The override status of the bill to party for the unit�s storage charges is used", example = "Y")
	@Pattern(regexp = "^(Y|N)$", message = "Only Y, N is allowed")
	private String overrideInd;

}
