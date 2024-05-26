package com.nscorp.obis.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=false)
public class TerminalIndDTO   {

	private Long terminalId;
	
	@Pattern(regexp="^(Y|N)$",message="Only Y, N & Null is allowed")
    private String privateInd;
    
    @NotEmpty(message =" AGS Indicator must be Y or N ")
	@Pattern(regexp="^(Y|N)$",message="Only Y and N is allowed")
    private String agsIndicator;
    
	@Pattern(regexp="^(Y|N|)$",message="Only Y, N & Null is allowed")
    private String lastMovNSNotOK;
}
