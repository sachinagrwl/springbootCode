package com.nscorp.obis.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "POOL_EQ_CTLR")
public class PoolEquipmentController extends AuditInfo{
	
    @Id
    @Column(name = "POOL_CTLR_ID", columnDefinition = "double", nullable = false)
    private Long poolControllerId;

    @Column(name = "POOL_ID", columnDefinition = "double", nullable = false)
    private Long poolId;

    @Column(name = "EQ_TP", columnDefinition = "char(1)", nullable = false)
    private String equipmentType;

    @Column(name = "CUST_PRI_6", columnDefinition = "char(6)", nullable = false)
    private String customerPrimary6;
    
    @ManyToOne
    @JoinColumn(name = "CUST_ID", referencedColumnName = "CUST_ID")
    private Customer customer;

	public Long getPoolControllerId() {
		return poolControllerId;
	}

	public void setPoolControllerId(Long poolControllerId) {
		this.poolControllerId = poolControllerId;
	}

	public Long getPoolId() {
		return poolId;
	}

	public void setPoolId(Long poolId) {
		this.poolId = poolId;
	}

	public String getEquipmentType() {
		if(equipmentType != null) {
			return equipmentType.trim();
		}
		else {
			return equipmentType;
		}
	}

	public void setEquipmentType(String equipmentType) {
		if(equipmentType != null){
			this.equipmentType = equipmentType.toUpperCase();
		}
		else {
			this.equipmentType = equipmentType;
		}
	}

	public String getCustomerPrimary6() {
		return customerPrimary6;
	}

	public void setCustomerPrimary6(String customerPrimary6) {
		this.customerPrimary6 = customerPrimary6;
	}	

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public PoolEquipmentController(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Long poolControllerId, Long poolId,
			String equipmentType, String customerPrimary6, Customer customer) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.poolControllerId = poolControllerId;
		this.poolId = poolId;
		this.equipmentType = equipmentType;
		this.customerPrimary6 = customerPrimary6;
		this.customer = customer;
	}
	
//	public PoolEquipmentController(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
//			Timestamp updateDateTime, String updateExtensionSchema, Long poolControllerId, Long poolId,
//			String equipmentType, String customerPrimary6, Customer customer, Terminal terminals) {
//		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
//		this.poolControllerId = poolControllerId;
//		this.poolId = poolId;
//		this.equipmentType = equipmentType;
//		this.customerPrimary6 = customerPrimary6;
//		this.customer = customer;
//		this.terminals = terminals;
//	}

	public PoolEquipmentController() {
		super();
	}

	public PoolEquipmentController(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}
	
	

}
