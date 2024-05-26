package com.nscorp.obis.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.lang.NonNull;

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
public class EquipmentOwnerPrefixDTO extends AuditInfoDTO{
	@NotNull
	@NotEmpty
	@Schema(required = true,description="The Equipment Initializer being used.", example="JBHU")
	@Size(max=4, message="equipmentInit size should not be more than {max}")
    private String equipInit;
	
	@Schema(required = false,description="The OwnerShip is being used.", example="J")
	@Size(max=1, message="OwnerShip size should not be more than {max}")
    private String ownership;
    
	@Schema(required = false,description="The InterChanged Cd being used.", example="JBHU")
	@Size(max=4, message="InterChanged Cd size should not be more than {max}")
    private String interchangeCd;



}
