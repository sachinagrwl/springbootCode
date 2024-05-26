package com.nscorp.obis.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "DAMAGE_COMP_REASON")
@IdClass(DamageComponentReasonPrimaryKey.class)
public class DamageComponentReason extends AuditInfo {
	
	@Id
	@Column(name = "JOB_CODE", columnDefinition = "SMALLINT(5)", nullable = false)
	private Integer jobCode;

	@Id
	@Column(name = "AAR_WHY_MADE_CD", columnDefinition = "SMALLINT(5)", nullable = false)
	private Integer aarWhyMadeCode;
	
	@Column(name = "ORDER_CD",columnDefinition = "char(1)",nullable = true)
	private String orderCode;
	
	@Column(name = "MAX_QUANTITY",columnDefinition = "smallint(5)",nullable = true)
	private Integer maxQuantity;
	
	@Column(name = "SIZE_REQ",columnDefinition = "char(1)",nullable = true)
	private String sizeRequired;
	
	@Column(name = "BORD_IND",columnDefinition = "char(1)",nullable = true)
	private String badOrder;
	
	@Column(name = "DISPLAY_CD",columnDefinition = "char(1)",nullable = true)
	private String displayCode;
	
	@ManyToOne(optional = false,fetch = FetchType.EAGER)
	@JoinColumn(name = "JOB_CODE",insertable = false,updatable = false)
	DamageComponent damageComponent;
	
	@ManyToOne(optional = false,fetch = FetchType.EAGER)
	@JoinColumn(name = "AAR_WHY_MADE_CD",insertable = false,updatable = false)
	AARWhyMadeCodes aarWhyMadeCodes;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Integer getMaxQuantity() {
		return maxQuantity;
	}

	public void setMaxQuantity(Integer maxQuantity) {
		this.maxQuantity = maxQuantity;
	}

	public String getSizeRequired() {
		return sizeRequired;
	}

	public void setSizeRequired(String sizeRequired) {
		this.sizeRequired = sizeRequired;
	}

	public String getBadOrder() {
		return badOrder;
	}

	public void setBadOrder(String badOrder) {
		this.badOrder = badOrder;
	}

	public String getDisplayCode() {
		return displayCode;
	}

	public void setDisplayCode(String displayCode) {
		this.displayCode = displayCode;
	}

	public DamageComponent getDamageComponent() {
		return damageComponent;
	}

	public void setDamageComponent(DamageComponent damageComponent) {
		this.damageComponent = damageComponent;
	}

	public AARWhyMadeCodes getAarWhyMadeCodes() {
		return aarWhyMadeCodes;
	}

	public void setAarWhyMadeCodes(AARWhyMadeCodes aarWhyMadeCodes) {
		this.aarWhyMadeCodes = aarWhyMadeCodes;
	}

	public Integer getJobCode() {
		return jobCode;
	}

	public void setJobCode(Integer jobCode) {
		this.jobCode = jobCode;
	}

	public Integer getAarWhyMadeCode() {
		return aarWhyMadeCode;
	}

	public void setAarWhyMadeCode(Integer aarWhyMadeCode) {
		this.aarWhyMadeCode = aarWhyMadeCode;
	}

	@Override
	public String toString() {
		return "DamageComponentReason [jobCode=" + jobCode + ", aarWhyMadeCode=" + aarWhyMadeCode + ", orderCode="
				+ orderCode + ", maxQuantity=" + maxQuantity + ", sizeRequired=" + sizeRequired + ", badOrder="
				+ badOrder + ", displayCode=" + displayCode + ", damageComponent=" + damageComponent
				+ ", aarWhyMadeCodes=" + aarWhyMadeCodes + ", getUversion()=" + getUversion() + ", getCreateUserId()="
				+ getCreateUserId() + ", getCreateDateTime()=" + getCreateDateTime() + ", getUpdateUserId()="
				+ getUpdateUserId() + ", getUpdateDateTime()=" + getUpdateDateTime() + ", getUpdateExtensionSchema()="
				+ getUpdateExtensionSchema() + "]";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(jobCode,aarWhyMadeCode);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DamageComponentReason other = (DamageComponentReason) obj;
		return Objects.equals(obj, other);
	}
	
}
