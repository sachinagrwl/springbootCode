package com.nscorp.obis.dto;

import java.util.List;

import javax.persistence.Column;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.nscorp.obis.common.CommonConstants;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CustomerTerminalListDTO {
    
    @Schema(required = true,description="The Customer Id being used.", example="3.000768653273E12")
	@Digits(integer=CommonConstants.CUST_ID_MAX_SIZE, fraction=0, message= "Customer id length cannot be more than 15")
	@Min(value = 1, message = "Customer id must be greater than 0")
	private Long customerId;
	
	// @Schema(required = false, description="The Terminal Id being used.", example="3.000768653273E12")
	// @Digits(integer=CommonConstants.TERM_ID_MAX_SIZE, fraction=0, message= "Terminal id length cannot be more than 15")
	// @Min(value = 1, message = "Terminal id must be greater than 0")
	// private Long terminalId;

    @NotNull
	@Column(name = "TERM_ID", columnDefinition = "Double(15)", nullable = true)
	private List<Long> terminalId;

   
}
