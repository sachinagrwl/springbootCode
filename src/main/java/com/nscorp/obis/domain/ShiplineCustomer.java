package com.nscorp.obis.domain;

import javax.persistence.*;
import java.sql.Timestamp;
@Entity
@Table(name="STEAMSHIP_CUST")
public class ShiplineCustomer extends AuditInfo{
	@Id
	@Column(name = "SHIPLINE_NR", columnDefinition = "char(7)", nullable = false)
	private String shiplineNumber;
	
	@Column(name = "CUST_ID", columnDefinition = "double(15)", nullable = true)
	private Long customerId;
	
	@Column(name = "DESCRIPTION", columnDefinition = "char(35)", nullable = true)
	private String description;
	
	@OneToOne
    @JoinColumns({
        @JoinColumn(updatable=false,insertable=false,name="CUST_ID", referencedColumnName="CUST_ID")
    })

	CorporateCustomer customerCustomerRef;

	public String getShiplineNumber() {
		if(shiplineNumber !=  null) {
			return shiplineNumber.trim();
		}
		else {
			return shiplineNumber;
		}
	}

	public void setShiplineNumber(String shiplineNumber) {
		this.shiplineNumber = shiplineNumber;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getDescription() {
		if(description != null) {
		return description.trim();
		}
		else {
			return description;
		}
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CorporateCustomer getCustomerCustomer() {
		return customerCustomerRef;
	}

	public void setCustomerCustomer(CorporateCustomer customerCustomerRef) {
		this.customerCustomerRef = customerCustomerRef;
	}

	public ShiplineCustomer(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, String shiplineNumber, Long customerId,
			String description, CorporateCustomer customerCustomerRef) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.shiplineNumber = shiplineNumber;
		this.customerId = customerId;
		this.description = description;
		this.customerCustomerRef = customerCustomerRef;
	}

	public ShiplineCustomer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShiplineCustomer(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}
	
}
