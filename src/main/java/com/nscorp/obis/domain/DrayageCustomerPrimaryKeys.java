package com.nscorp.obis.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class DrayageCustomerPrimaryKeys implements Serializable {
	
	@Column(name = "DRAY_ID",columnDefinition = "char(4)",nullable = false)
	private String drayageId;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "CUST_ID", nullable = true)
    private CustomerInfo customer;


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		DrayageCustomerPrimaryKeys that = (DrayageCustomerPrimaryKeys) o;
		return Objects.equals(drayageId, that.drayageId) && Objects.equals(customer, that.customer);
	}

	@Override
	public int hashCode() {
		return Objects.hash(drayageId, customer);
	}
}
