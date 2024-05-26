package com.nscorp.obis.domain;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
public class PoolEquipmentConflictControllerPrimaryKeys implements Serializable {
	
	private Pool pool;
	
	private Long poolControllerId;
	
	private String customerPrimary6;
	
	private String controllerType;
	
	private Terminal terminal;

	public PoolEquipmentConflictControllerPrimaryKeys(Pool pool, Long poolControllerId, String customerPrimary6,
			String controllerType, Terminal terminal) {
		super();
		this.pool = pool;
		this.poolControllerId = poolControllerId;
		this.customerPrimary6 = customerPrimary6;
		this.controllerType = controllerType;
		this.terminal = terminal;
	}

	public PoolEquipmentConflictControllerPrimaryKeys() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		return Objects.hash(controllerType, customerPrimary6, pool, poolControllerId, terminal);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof PoolEquipmentConflictControllerPrimaryKeys))
			return false;
		PoolEquipmentConflictControllerPrimaryKeys other = (PoolEquipmentConflictControllerPrimaryKeys) obj;
		return Objects.equals(controllerType, other.controllerType)
				&& Objects.equals(customerPrimary6, other.customerPrimary6) && Objects.equals(pool, other.pool)
				&& Objects.equals(poolControllerId, other.poolControllerId) && Objects.equals(terminal, other.terminal);
	}
	
	

}
