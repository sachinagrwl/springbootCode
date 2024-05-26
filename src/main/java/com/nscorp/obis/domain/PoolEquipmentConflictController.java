package com.nscorp.obis.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "POOL_CTLR_CON_V")
@IdClass(value = PoolEquipmentConflictControllerPrimaryKeys.class)
public class PoolEquipmentConflictController {

	@Id
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "POOL_ID", referencedColumnName = "POOL_ID", insertable = false, updatable = false)
    private Pool pool;
	
	@Id
	@Column(name = "CTLR_POOL_ID", columnDefinition = "double", nullable = false)
    private Long poolControllerId;
	
	@Id
	@Column(name = "CUST_PRI_6", columnDefinition = "char(6)", nullable = false)
    private String customerPrimary6;
	
	@Id
	@Column(name = "CTLR_TP", columnDefinition = "char(1)", nullable = false)
    private String controllerType;
	
	@Id
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TERM_ID", referencedColumnName = "TERM_ID", insertable = false, updatable = false)
	private Terminal terminal;

	public Pool getPool() {
		return pool;
	}

	public Long getPoolControllerId() {
		return poolControllerId;
	}

	public String getCustomerPrimary6() {
		return customerPrimary6;
	}

	public String getControllerType() {
		return controllerType;
	}

	public Terminal getTerminal() {
		return terminal;
	}
	
	public void setPool(Pool pool) {
		this.pool = pool;
	}

	public void setPoolControllerId(Long poolControllerId) {
		this.poolControllerId = poolControllerId;
	}

	public void setCustomerPrimary6(String customerPrimary6) {
		this.customerPrimary6 = customerPrimary6;
	}

	public void setControllerType(String controllerType) {
		this.controllerType = controllerType;
	}

	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}

	public PoolEquipmentConflictController(String uversion, String createUserId, Timestamp createDateTime,
			String updateUserId, Timestamp updateDateTime, String updateExtensionSchema, Pool pool,
			Long poolControllerId, String customerPrimary6, String controllerType, Terminal terminal) {
		this.pool = pool;
		this.poolControllerId = poolControllerId;
		this.customerPrimary6 = customerPrimary6;
		this.controllerType = controllerType;
		this.terminal = terminal;
	}

	public PoolEquipmentConflictController() {
		super();
		// TODO Auto-generated constructor stub
	}

}
