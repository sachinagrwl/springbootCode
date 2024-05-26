package com.nscorp.obis.dto;

import java.util.List;
import javax.validation.constraints.NotNull;

import javax.persistence.Column;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import com.nscorp.obis.common.CommonConstants;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CustomerNicknameListDTO {
	
	@Digits(integer=CommonConstants.CUST_ID_MAX_SIZE, fraction=0, message= "Customer id cannot be more than 15")
	@Min(value = 1, message = "Customer id Length must be greater than 0")
	@Schema(required = true,description="The Customer Id being used.")
	private Long customerId;

	@Digits(integer=CommonConstants.TERM_ID_MAX_SIZE, fraction=0, message= "Terminal id cannot be more than 15")
	@Min(value = 1, message = "Terminal id Length must be greater than 0")
	@Schema(required = true,description="The Terminal Id being used.")
	private Long terminalId;

	@NotNull
	@Column(name = "NICKNAME", columnDefinition = "char(35)", nullable = false)
	private List<String> customerNickname;

}

