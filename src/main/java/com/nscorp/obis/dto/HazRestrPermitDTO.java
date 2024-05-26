package com.nscorp.obis.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.nscorp.obis.domain.Customer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class HazRestrPermitDTO {

	@NotBlank(message = "'UnCd' Required")
	@Schema(required = true,description="The Un Code is being used.", example="UN3077")
	private String unCd;
	
	@NotBlank(message = "'permitNr' should not be Blank or Null.")
	@Schema(required = true,description="The Permit Number is being used.", example="H01")
	@Size(max=3, message = "'permitNr' size should not be greater than {max}")
	private String permitNr;
	
	private Long customerId;
	
	private UnCdDTO unCodeDto;
	
	private Customer customer;
}
