package com.nscorp.obis.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Data
@EqualsAndHashCode(callSuper = false)
public class DVIRCodesDTO extends AuditInfoDTO {
    @NotBlank(message="DVIRCode cannot be empty/null")
    @Size(min=2, max=2, message = "DVIRCode should be atleast 2 characters and cannot be more than 2 Characters")
    @Pattern(regexp="^[0-9]*$",message="'DVIRCode' field allows only Numeric values.")
    private String dvirCd;
    private String dvirDesc;
    private String dvirHHDesc;
    
	@Pattern(regexp="^(Y|N|)$",message="Only Y, N and Null is allowed")
    private String displayCd;
}
