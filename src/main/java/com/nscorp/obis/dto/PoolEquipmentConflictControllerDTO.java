package com.nscorp.obis.dto;

import com.nscorp.obis.domain.Pool;
import com.nscorp.obis.domain.Terminal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PoolEquipmentConflictControllerDTO {
	
	private Pool pool;
	
	private Long poolControllerId;
	
	private String customerPrimary6;
	
	private String controllerType;
	
	private Terminal terminal;

}
