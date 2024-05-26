package com.nscorp.obis.domain;

import java.sql.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CUSTOMER")
public class CustomerScac {
	@Id
	@Column(name = "CUST_ID", columnDefinition = "Double", nullable = false)
	private Long customerId;
	@Column(name = "CUST_NR", columnDefinition = "char(10)", nullable = true)
	private String customerNumber;
	@Column(name = "CUST_NM", columnDefinition = "char(35)", nullable = true)
	private String customerName;
	@Column(name = "EXPIRED_DT", columnDefinition = "date(10)", nullable = true)
	private Date expiredDate;
	@OneToOne
	@JoinTable(name = "DRAYAGE_CUST", joinColumns = {
			@JoinColumn(name = "CUST_ID", referencedColumnName = "CUST_ID") }, inverseJoinColumns = {
					@JoinColumn(name = "DRAY_ID", referencedColumnName = "DRAY_ID") })
	private DrayageScac scac;

	public CustomerScac() {

	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	public DrayageScac getScac() {
		return scac;
	}

	public void setScac(DrayageScac scac) {
		this.scac = scac;
	}

	@Override
	public String toString() {
		return "CustomerScac [customerId=" + customerId + ", customerNumber=" + customerNumber + ", customerName="
				+ customerName + ", expiredDate=" + expiredDate + ", scac=" + scac + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerScac other = (CustomerScac) obj;
		return Objects.equals(customerId, other.customerId);
	}

}
