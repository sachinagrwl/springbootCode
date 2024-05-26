package com.nscorp.obis.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.domain.Customer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PoolEquipmentControllerDTO extends AuditInfoDTO {
	
	 @Schema(required = true, description = "The Controller Id of the Pool s being used", example = "978284241407")
	 private Long poolControllerId;
	 
	 @Digits(integer = 15, fraction = 0, message = "Pool Id should not have more than 15 digits. ")
	 @Min(value = 0, message = "Pool Id value must be greater than or equal to 0")
	 @Schema(required = true, description = "This is the  Id of Pool.", example = "978284241407")
	 private Long poolId;
	 
	 @NotBlank(message = "Equipment type should not be Blank or Null.")
	 @Schema(required = true,description="The type of the equipment being used.", example="C")
	 @Size(max = CommonConstants.EQ_TYPE_MAX_SIZE, message="'equipmentType' size cannot have more than {max}")
	 @Pattern(regexp="^(C|T|Z)$",message="'equipmentType' should contain C, T, and Z only")
	 private String equipmentType;
	 
	 @Schema(required = true,description="The First Characteristics of the Customer Number is being used.", example="040798")
	 @Size(max = CommonConstants.CUST_PRI_6_MAX_SIZE, message="'customerPrimary6' size cannot have more than {max}")
	 private String customerPrimary6;
	 
	 private Customer customer;

}
