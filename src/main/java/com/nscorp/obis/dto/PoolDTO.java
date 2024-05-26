package com.nscorp.obis.dto;

import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.domain.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PoolDTO extends AuditInfoDTO {
	
	@Digits(integer = 15, fraction = 0, message = "Pool Id should not have more than 15 digits. ")
	@Min(value = 0, message = "Pool Id value must be greater than or equal to 0")
	@Schema(required = true, description = "This is the  Id of Pool.", example = "978284241407")
	private Long poolId;

	Set<Terminal> terminals;

	Set<PoolEquipmentController> controllers;

	Set<PoolEquipmentRange> equipmentRanges;

	@NotBlank(message="'poolName' should not be null or blank")
	@Schema(required = false, description = "This represents the short unique identifier for equipment pool", example = "COFC CNTR")
	@Size(max=CommonConstants.POOL_NAME_MAX_SIZE, message = "'POOL NAME' length should not be greater than {max}")
//	@Pattern(regexp="^[a-zA-Z0-9]*$",message="'poolName' should not contain Blank or Special Characters")
	private String poolName;

	@NotBlank(message="'description' should not be null or blank")
	@Schema(required = false, description = "This reprsents the long description of the equipment pool", example = "COFC CONTAINER POOL")
	@Size(max=CommonConstants.POOL_DESC_MAX_SIZE, message = "'POOL DESCRIPTION' length should not be greater than {max}")
	private String description;
	
	@Schema(required = false, description = "This represents the pool type", example = "PT")
	@Size(max=CommonConstants.POOL_RSRV_TP_MAX_SIZE, message = "'POOL RESERVATION TYPE' length should not be greater than {max}")
	private String poolReservationType;
	
//	@Schema(required = false, description = "", example = "")
//	private String truckerGroupCD;

	private TruckerGroup truckerGroup;
	
	@Schema(required = false, description = "", example = "")
	@Size(max=CommonConstants.POOL_AGREEMENT_REQ_MAX_SIZE, message="'agreementRequired' size should not be more than {max}")
	private String agreementRequired;
	
	@Schema(required = false, description = "", example = "")
	@Size(max=CommonConstants.LOAD_EMPTY_CD_MAX_SIZE, message="'checkTrucker' size should not be more than {max}")
	@Pattern(regexp="^(L|B|E)$",message="'checkTrucker' should not be Blank or should contain only L, B and E")
	private String checkTrucker;

	private PoolTypeDTO poolType;
	
	private Set<Customer> customers;
}
