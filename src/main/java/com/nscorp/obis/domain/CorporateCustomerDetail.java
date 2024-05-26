package com.nscorp.obis.domain;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="CORP_CUST_DTL")
@IdClass(CorporateCustomerDetailComposite.class)
public class CorporateCustomerDetail extends AuditInfo  {

	@Id
	@Column(name="CORP_CUST_ID",length = 15, columnDefinition = "Double(15)", nullable = false )
    private Long corpCustId;
	
	@Id
	@Column(name="CORP_PRIMARY_6",length = 15, columnDefinition = "char(6)", nullable = false )
    private String corpCust6;
	
	@Transient
	private List<Customer> customer;
	
	
	
	public List<Customer> getCustomerNumber() {
		return customer;
	}
	public void setCustomerNumber(List<Customer> customer) {
		this.customer = customer;
	}
	public Long getCorpCustId() {
		return corpCustId;
	}
	public void setCorpCustId(Long corpCustId) {
		this.corpCustId = corpCustId;
	}
	public String getCorpCust6() {
		return corpCust6;
	}
	public void setCorpCust6(String corpCust6) {
		this.corpCust6 = corpCust6;
	}
	
	public CorporateCustomerDetail(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Long corpCustId, String corpCust6) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.corpCustId = corpCustId;
		this.corpCust6 = corpCust6;
	}
	

	public CorporateCustomerDetail() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CorporateCustomerDetail(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}
    
	
    
}