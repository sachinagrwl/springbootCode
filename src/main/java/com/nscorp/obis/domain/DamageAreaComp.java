package com.nscorp.obis.domain;

import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name = "DAMAGE_AREA_COMP")
@IdClass(DamageAreaComponentPrimaryKeys.class)
public class DamageAreaComp extends AuditInfo {
	@Id
	@Column(name = "AREA_CD", columnDefinition = "char(1)", nullable = true)
	private String areaCd;

	@Id
	@Column(name = "JOB_CODE" , length = 4, columnDefinition = "SMALLINT", nullable = false)
	private Integer jobCode;

	//@Id
	@Column(name = "ORDER_CD", columnDefinition = "char(1)", nullable = true)
	private String orderCode;
	@Column(name = "DISPLAY_CD", columnDefinition = "char(1)", nullable = true)
	private String displayCd;

	public String getDisplayCd() {
		return displayCd;
	}

	public void setDisplayCd(String displayCd) {
		this.displayCd = displayCd;
	}


	public String getAreaCd() {
		if(areaCd != null) {
			return areaCd.trim();
		}
		else {
			return areaCd;
		}
	}

	public void setAreaCd(String areaCd) {
		this.areaCd = areaCd;
	}

	public Integer getJobCode() {
		return jobCode;
	}

	public void setJobCode(Integer jobCode) {
		this.jobCode = jobCode;
	}

	public String getOrderCode() {
		if(orderCode != null) {
			return orderCode.trim();
		}
		else {
			return orderCode;
		}
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}



	public DamageAreaComp(String uversion, String createUserId, Timestamp createDateTime, String updateUserId, Timestamp updateDateTime, String updateExtensionSchema, String areaCd, Integer jobCode, String orderCode, String displayCd) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.areaCd = areaCd;
		this.jobCode = jobCode;
		this.orderCode = orderCode;
		this.displayCd = displayCd;
	}

	public DamageAreaComp() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DamageAreaComp(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
							   Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "DamageAreaComponent ["
				+ "areaCd=" + areaCd
				+ ",jobCode=" + jobCode
				+ ", orderCode=" + orderCode
				+ ", displayCd=" + displayCd
				+ ", getUversion()=" + getUversion() + ", getCreateUserId()=" + getCreateUserId()
				+ ", getCreateDateTime()=" + getCreateDateTime() + ", getUpdateUserId()=" + getUpdateUserId()
				+ ", getUpdateDateTime()=" + getUpdateDateTime() + ", getUpdateExtensionSchema()="
				+ getUpdateExtensionSchema() + "]";
	}
    
    

}