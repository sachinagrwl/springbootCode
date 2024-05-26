package com.nscorp.obis.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(value = "true")
public class InterChangePartyDTO extends AuditInfoDTO {
	
	 
	 @NotBlank(message = "ICHG Party is a mandatory field.")
	 @Schema(required = true,description="This gives the non-NS railroad (Interchange Party) initial")
	 //@Pattern(regexp = "{A-Za-z0-9}*")
	 @Size(min=1, max=4, message = "Ichg Code should not be more than 4 Character")
	 private String ichgCode;
	 
	 @NotBlank(message = "Road/ Other is a mandatory field.")
	 @Schema(required = true,description="Type 'R'-Road and 'O'-Other")
	 @Pattern(regexp="^(R|O)$",message="Only R and O is allowed")
	 private String roadOtherInd;
	 
	 @Schema(required = false,description="This gives the name of the non-NS railroad that might be operating the equipment")
	 @Size(min=1, max=30, message = "Ichg Cd Desc should not be more than 30 Character")
	 private String ichgCdDesc;

}


