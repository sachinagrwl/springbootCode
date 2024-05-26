package com.nscorp.obis.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EQ_LESSEE_RANGE")
public class EquipmentCustomerLesseeRange extends AuditInfo {
	
	@Id
    @Column(name = "EQ_CUST_RANGE_ID", columnDefinition = "double", nullable = false)
    private Long equipmentCustomerRangeId;

    @Column(name = "EQ_LESSEE_ID", columnDefinition = "double", nullable = false)
    private Long equipmentLesseeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EQ_LESSEE_ID", referencedColumnName = "CORP_CUST_ID", insertable = false , updatable = false)
    private CorporateCustomer corporateCustomer;

    @Column(name = "EQ_INIT", columnDefinition = "char(4)", nullable = false)
    private String equipmentInit;

    @Column(name = "EQ_LOW_NR", columnDefinition = "decimal(19)", nullable = false)
    private BigDecimal equipmentLowNumber;

    @Column(name = "EQ_HIGH_NR", columnDefinition = "decimal(19)", nullable = false)
    private BigDecimal equipmentHighNumber;

    @Column(name = "EQ_TP", columnDefinition = "char(1)", nullable = false)
    private String equipmentType;

    @Column(name = "EQ_OWNR_TP", columnDefinition = "char(1)", nullable = false)
    private String equipmentOwnerType;

    @Column(name = "EQ_OWNR_ID", columnDefinition = "double", nullable = false)
    private Long equipmentOwnerId;

	public Long getEquipmentCustomerRangeId() {
		return equipmentCustomerRangeId;
	}

	public void setEquipmentCustomerRangeId(Long equipmentCustomerRangeId) {
		this.equipmentCustomerRangeId = equipmentCustomerRangeId;
	}

	public Long getEquipmentLesseeId() {
		return equipmentLesseeId;
	}

	public void setEquipmentLesseeId(Long equipmentLesseeId) {
		this.equipmentLesseeId = equipmentLesseeId;
	}

	public CorporateCustomer getCorporateCustomer() {
		return corporateCustomer;
	}

	public void setCorporateCustomer(CorporateCustomer corporateCustomer) {
		this.corporateCustomer = corporateCustomer;
	}

	public String getEquipmentInit() {
		return equipmentInit;
	}

	public void setEquipmentInit(String equipmentInit) {
		this.equipmentInit = equipmentInit;
	}

	public BigDecimal getEquipmentLowNumber() {
		return equipmentLowNumber;
	}

	public void setEquipmentLowNumber(BigDecimal equipmentLowNumber) {
		this.equipmentLowNumber = equipmentLowNumber;
	}

	public BigDecimal getEquipmentHighNumber() {
		return equipmentHighNumber;
	}

	public void setEquipmentHighNumber(BigDecimal equipmentHighNumber) {
		this.equipmentHighNumber = equipmentHighNumber;
	}

	public String getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}

	public String getEquipmentOwnerType() {
		return equipmentOwnerType;
	}

	public void setEquipmentOwnerType(String equipmentOwnerType) {
		this.equipmentOwnerType = equipmentOwnerType;
	}

	public Long getEquipmentOwnerId() {
		return equipmentOwnerId;
	}

	public void setEquipmentOwnerId(Long equipmentOwnerId) {
		this.equipmentOwnerId = equipmentOwnerId;
	}

	public EquipmentCustomerLesseeRange(String uversion, String createUserId, Timestamp createDateTime,
			String updateUserId, Timestamp updateDateTime, String updateExtensionSchema, Long equipmentCustomerRangeId,
			Long equipmentLesseeId, CorporateCustomer corporateCustomer, String equipmentInit,
			BigDecimal equipmentLowNumber, BigDecimal equipmentHighNumber, String equipmentType,
			String equipmentOwnerType, Long equipmentOwnerId) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.equipmentCustomerRangeId = equipmentCustomerRangeId;
		this.equipmentLesseeId = equipmentLesseeId;
		this.corporateCustomer = corporateCustomer;
		this.equipmentInit = equipmentInit;
		this.equipmentLowNumber = equipmentLowNumber;
		this.equipmentHighNumber = equipmentHighNumber;
		this.equipmentType = equipmentType;
		this.equipmentOwnerType = equipmentOwnerType;
		this.equipmentOwnerId = equipmentOwnerId;
	}

	public EquipmentCustomerLesseeRange() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EquipmentCustomerLesseeRange(String uversion, String createUserId, Timestamp createDateTime,
			String updateUserId, Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}
	
	

}
