package com.nscorp.obis.dto;

import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.nscorp.obis.common.CommonConstants;

import io.swagger.v3.oas.annotations.media.Schema;


public class PoolListDTO {
	@NotNull
	@Schema(required = true,description="The Customer Id being used.", example="3.000768653273E12")
	@Digits(integer=CommonConstants.CUST_ID_MAX_SIZE, fraction=0, message= "Customer id length cannot be more than 15")
	@Min(value = 1, message = "Customer id must be greater than 0")
	private Long customerId;
	
	@NotNull
	@Schema(required = true,description="Collection of Pool Ids to be Updated")
	List<Long> poolIds;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public List<Long> getPoolIds() {
		return poolIds;
	}

	public void setPoolIds(List<Long> poolIds) {
		this.poolIds = poolIds;
	}

	@Override
	public String toString() {
		return "PoolListDTO [customerId=" + customerId + ", poolIds=" + poolIds + "]";
	}
	
	

}
