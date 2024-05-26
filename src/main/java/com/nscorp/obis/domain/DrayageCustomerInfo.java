package com.nscorp.obis.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Entity
@Table(name = "DRAYAGE_CUST")
@IdClass(DrayageCustomerInfoPrimaryKey.class)
@NoArgsConstructor
public class DrayageCustomerInfo extends AuditInfo implements Serializable {
	@Id
	@Column(name = "DRAY_ID", columnDefinition = "char(4)", nullable = false)
	private String drayageId;
	@Id
	@Column(name = "CUST_ID", columnDefinition = "Double(15)", nullable = false)
	private Long customerId;

	@Column(name = "CUST_NR", columnDefinition = "char(10)", nullable = false)
	private String customerNumber;

	@Column(name = "CUST_NM", columnDefinition = "char(40)", nullable = false)
	private String customerName;

	public String getDrayageId() {
		return drayageId;
	}

	public void setDrayageId(String drayageId) {
		this.drayageId = drayageId;
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

	@Override
	public String toString() {
		return "DrayageCustomerInfo [drayageId=" + drayageId + ", customerId=" + customerId + ", customerNumber="
				+ customerNumber + ", customerName=" + customerName + ", getUversion()=" + getUversion()
				+ ", getCreateUserId()=" + getCreateUserId() + ", getCreateDateTime()=" + getCreateDateTime()
				+ ", getUpdateUserId()=" + getUpdateUserId() + ", getUpdateDateTime()=" + getUpdateDateTime()
				+ ", getUpdateExtensionSchema()=" + getUpdateExtensionSchema() + "]";
	}
}
