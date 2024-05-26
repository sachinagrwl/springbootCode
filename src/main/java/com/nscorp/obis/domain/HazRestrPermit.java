package com.nscorp.obis.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity()
@Table(name = "HAZ_RESTR_PERMIT")
@IdClass(HazRestrPermitComposite.class)
public class HazRestrPermit extends AuditInfo {

	@Id
    @Column(name = "UN_CD", columnDefinition = "char(6)", nullable = false)
    private String unCd;

    @Column(name = "PERMIT_NR", columnDefinition = "char(3)", nullable = false)
    private String permitNr;

    @Id
    @Column(name="CUST_ID", columnDefinition = "Double(15)", nullable = false)
    private Long customerId;

    @Transient
	private UnCd unCode;
    
    @Transient
	private Customer customer;

	public String getUnCd() {
		return unCd;
	}

	public void setUnCd(String unCd) {
		this.unCd = unCd;
	}

	public String getPermitNr() {
		return permitNr;
	}

	public void setPermitNr(String permitNr) {
		this.permitNr = permitNr;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public UnCd getUnCode() {
		return unCode;
	}

	public void setUnCode(UnCd unCode) {
		this.unCode = unCode;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public HazRestrPermit(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, String unCd, String permitNr, Long customerId,
			UnCd unCode, Customer customer) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.unCd = unCd;
		this.permitNr = permitNr;
		this.customerId = customerId;
		this.unCode = unCode;
		this.customer = customer;
	}

	public HazRestrPermit() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HazRestrPermit(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}
    
    
}
