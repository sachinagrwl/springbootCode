package com.nscorp.obis.domain;

import java.io.Serializable;

import javax.persistence.Column;

@SuppressWarnings("serial")
public class CustomerPoolPrimaryKeys implements Serializable{
	@Column(name = "CUST_ID", columnDefinition = "Double(15)", nullable = false)
	private Long customerId;
	
	@Column(name = "POOL_ID", columnDefinition = "Double(15)", nullable = true)
	private Long poolId;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getPoolId() {
		return poolId;
	}

	public void setPoolId(Long poolId) {
		this.poolId = poolId;
	}

	public CustomerPoolPrimaryKeys(Long customerId, Long poolId) {
		super();
		this.customerId = customerId;
		this.poolId = poolId;
	}

	public CustomerPoolPrimaryKeys() {
		super();
		// TODO Auto-generated constructor stub
	}
    
}
