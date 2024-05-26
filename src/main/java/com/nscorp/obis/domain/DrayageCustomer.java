package com.nscorp.obis.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@SuppressWarnings("serial")
@Entity
@Immutable
@Table(name = "DRAYAGE_CUST")
@Subselect("select * from INTERMODAL.DRAYAGE_CUST")
@IdClass(DrayageCustomerPrimaryKeys.class)
public class DrayageCustomer extends AuditInfo implements Serializable {

	@Id
	@Column(name = "DRAY_ID", columnDefinition = "char(4)", nullable = false)
	private String drayageId;

//	@Id
//	@Column(name = "CUST_ID", columnDefinition = "Double(15)", nullable = false, insertable = false, updatable = false)
//	private Long customerId;

	@Column(name = "CUST_NR", columnDefinition = "char(10)", nullable = false)
	private String customerNumber;

	@Column(name = "CUST_NM", columnDefinition = "char(40)", nullable = false)
	private String customerName;
	
	@Id
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "CUST_ID", nullable = true)
    private CustomerInfo customer;
    
	public String getDrayageId() {
		return drayageId;
	}

	public void setDrayageId(String drayageId) {
		this.drayageId = drayageId;
	}

//	public Long getCustomerId() {
//		return customerId;
//	}
//
//	public void setCustomerId(Long customerId) {
//		this.customerId = customerId;
//	}

	public String getCustomerNumber() {
		if (customerNumber != null) {
			return customerNumber.trim();
		}
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getCustomerName() {
		if (customerName != null) {
			return customerName.trim();
		}
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public DrayageCustomer() {
		super();
		// TODO Auto-generated constructor stub
	}
   
	
	public DrayageCustomer(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, String drayageId, 
			String customerNumber, String customerName, CustomerInfo customer) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.drayageId = drayageId;
		//this.customerId = customerId;
		this.customerNumber = customerNumber;
		this.customerName = customerName;
		this.customer = customer;
	}

	public CustomerInfo getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerInfo customer) {
		this.customer = customer;
	}

	public DrayageCustomer(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "DrayageCustomer [drayageId=" + drayageId + ", customerNumber=" + customerNumber + ", customerName=" + customerName
				+ ", customer=" + customer
				+ ", getUversion()=" + getUversion() + ", getCreateUserId()=" + getCreateUserId()
				+ ", getCreateDateTime()=" + getCreateDateTime() + ", getUpdateUserId()=" + getUpdateUserId()
				+ ", getUpdateDateTime()=" + getUpdateDateTime() + ", getUpdateExtensionSchema()="
				+ getUpdateExtensionSchema() + "]";
	}
}
