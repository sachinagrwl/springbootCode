package com.nscorp.obis.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.nscorp.obis.domain.CustomerIndex;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PayGuaranteeDTO {
	private Long payGuarId;
	@NotNull(message = "'Charge Id' should not be Null.")
	private Long chrgId;
	
	
	@NotNull(message = "'Guarantee Amount' should not be Blank or Null.")
	@Min(value = 1, message = "Amount entered must be greater than zero")
	@Digits(integer=19, fraction=2, message = "Amount should not be more than 19 Digits")
	private BigDecimal amount;
	
	@Digits(integer=15, fraction=0, message= "GuarCustId cannot be more than 15")
	private Long guarCustId;
	
	@Size(max=30, message="GuarRefNr can not be more than 30")
	private String guarRefNr;

	private CustomerIndex customer;

}
