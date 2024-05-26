package com.nscorp.obis.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "DAMAGE_COMP_SIZE")
@IdClass(DamageComponentSizePrimaryKey.class)
public class DamageComponentSize extends AuditInfo {
    
    @Id
	@Column(name = "JOB_CODE", columnDefinition = "SMALLINT(5)", nullable = false)
	private Integer jobCode;

	@Id
	@Column(name = "AAR_WHY_MADE_CD", columnDefinition = "SMALLINT(5)", nullable = false)
	private Integer aarWhyMadeCode;
	
    @Id
    @Column(name = "COMP_SIZE_CD", columnDefinition = "Double(15)", nullable = false)
	private Long componentSizeCode;

	@Column(name = "ORDER_CD",columnDefinition = "char(1)",nullable = true)
	private String orderCode;
	
	@Column(name = "DMGE_SIZE", columnDefinition = "SMALLINT(5)", nullable = true)
	private Integer damageSize;
	
	@Column(name = "SIZE_DISPLAY_TP",columnDefinition = "char(2)",nullable = true)
	private String sizeDisplayTp;
	
	@Column(name = "SIZE_DISPLAY_SIGN",columnDefinition = "char(1)",nullable = true)
	private String sizeDisplaySign;
	
	@Column(name = "DISPLAY_CD",columnDefinition = "char(1)",nullable = true)
	private String displayCode;

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

	public Long getComponentSizeCode() {
		return componentSizeCode;
	}

	public void setComponentSizeCode(Long componentSizeCode) {
		this.componentSizeCode = componentSizeCode;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Integer getDamageSize() {
		return damageSize;
	}

	public void setDamageSize(Integer damageSize) {
		this.damageSize = damageSize;
	}

	public String getSizeDisplayTp() {
		return sizeDisplayTp;
	}

	public void setSizeDisplayTp(String sizeDisplayTp) {
		this.sizeDisplayTp = sizeDisplayTp;
	}

	public String getSizeDisplaySign() {
		return sizeDisplaySign;
	}

	public void setSizeDisplaySign(String sizeDisplaySign) {
		this.sizeDisplaySign = sizeDisplaySign;
	}

	public String getDisplayCode() {
		return displayCode;
	}

	public void setDisplayCode(String displayCode) {
		this.displayCode = displayCode;
	}

	@Override
	public String toString() {
		return "DamageComponentSize [jobCode=" + jobCode + ", aarWhyMadeCode=" + aarWhyMadeCode + ", componentSizeCode="
				+ componentSizeCode + ", orderCode=" + orderCode + ", damageSize=" + damageSize + ", sizeDisplayTp="
				+ sizeDisplayTp + ", sizeDisplaySign=" + sizeDisplaySign + ", displayCode=" + displayCode + "]";
	}

	public DamageComponentSize(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Integer jobCode, Integer aarWhyMadeCode,
			Long componentSizeCode, String orderCode, Integer damageSize, String sizeDisplayTp, String sizeDisplaySign,
			String displayCode) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.jobCode = jobCode;
		this.aarWhyMadeCode = aarWhyMadeCode;
		this.componentSizeCode = componentSizeCode;
		this.orderCode = orderCode;
		this.damageSize = damageSize;
		this.sizeDisplayTp = sizeDisplayTp;
		this.sizeDisplaySign = sizeDisplaySign;
		this.displayCode = displayCode;
	}

	public DamageComponentSize() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DamageComponentSize(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}

	
}
