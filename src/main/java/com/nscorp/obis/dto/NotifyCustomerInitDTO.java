package com.nscorp.obis.dto;

import java.util.Set;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
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
@EqualsAndHashCode(callSuper = false)
public class NotifyCustomerInitDTO extends AuditInfoDTO {

	@Schema(required = true, description = "The name of the Equipment Initial being used.", example = "BMDZ")
	@Size(min = CommonConstants.EQ_INIT_MIN_SIZE, max = CommonConstants.EQ_INIT_MAX_SIZE, message = "'eqInit' size should be between {min} and {max}")
	private String eqInit;

	@NotNull(message = "'custId' should not be null.")
	@Schema(required = true, description = "The Customer Id being used.", example = "88391035696600")
	@Digits(integer = CommonConstants.CUST_ID_MAX_SIZE, fraction = 0, message = "'custId' cannot have more than 15 digits")
	@Min(value = CommonConstants.CUST_ID_MIN_SIZE, message = "'custId' Length must be greater than 0")
	private Long custId;

	Set<String> eqInitList;
}
