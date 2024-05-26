package com.nscorp.obis.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class DrayageCustomerInfoPrimaryKey implements Serializable {
	
	@Column(name = "DRAY_ID",columnDefinition = "char(4)",nullable = false)
	private String drayageId;
	
	@Column(name = "CUST_ID", columnDefinition = "Double", nullable = false)
	private long customerId;


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		DrayageCustomerInfoPrimaryKey that = (DrayageCustomerInfoPrimaryKey) o;
		return customerId == that.customerId && Objects.equals(drayageId, that.drayageId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(drayageId, customerId);
	}
}
