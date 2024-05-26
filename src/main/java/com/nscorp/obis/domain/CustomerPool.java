package com.nscorp.obis.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "POOL_CUST")
@IdClass(CustomerPoolPrimaryKeys.class)
public class CustomerPool extends AuditInfo{
	@Id
	@Column(name = "CUST_ID", columnDefinition = "Double(15)", nullable = false)
	private Long customerId;

	@Id
	@Column(name = "POOL_ID", columnDefinition = "Double(15)", nullable = true)
	private Long poolId;

	public CustomerPool(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Long customerId, Long poolId) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.customerId = customerId;
		this.poolId = poolId;
	}

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

	public CustomerPool() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomerPool(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}
    

}
