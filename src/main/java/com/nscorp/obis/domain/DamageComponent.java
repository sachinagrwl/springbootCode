package com.nscorp.obis.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "DAMAGE_COMPONENT")
public class DamageComponent extends AuditInfo {

	@Id
	@Column(name = "JOB_CODE", length = 4, columnDefinition = "SMALLINT", nullable = false)
	private Integer jobCode;

	@Column(name = "COMP_DSCR", columnDefinition = "char(16)", nullable = true)
	private String compDscr;

	@Column(name = "T_IND", columnDefinition = "char(1)", nullable = true)
	private String tInd;

	@Column(name = "C_IND", columnDefinition = "char(1)", nullable = true)
	private String cInd;

	@Column(name = "Z_IND", columnDefinition = "char(1)", nullable = true)
	private String zInd;

	@Column(name = "REASON_TP", columnDefinition = "char(1)", nullable = true)
	private String reasonTp;

	public Integer getJobCode() {
		return jobCode;
	}

	public void setJobCode(Integer jobCode) {
		this.jobCode = jobCode;
	}

	public String getCompDscr() {
		if (compDscr != null) {
			return compDscr.trim();
		} else {
			return compDscr;
		}
	}

	public void setCompDscr(String compDscr) {
		if (compDscr != null) {
			this.compDscr = compDscr.toUpperCase();
		} else {
			this.compDscr = compDscr;
		}
	}

	public String getTInd() {
		return tInd;
	}

	public void setTInd(String tInd) {
		this.tInd = tInd;
	}

	public String getCInd() {
		return cInd;
	}

	public void setCInd(String cInd) {
		this.cInd = cInd;
	}

	public String getZInd() {
		return zInd;
	}

	public void setZInd(String zInd) {
		this.zInd = zInd;
	}

	public String getReasonTp() {
		return reasonTp;
	}

	public void setReasonTp(String reasonTp) {
		this.reasonTp = reasonTp;
	}

	public DamageComponent(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Integer jobCode, String compDscr, String tInd,
			String cInd, String zInd, String reasonTp) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.jobCode = jobCode;
		this.compDscr = compDscr;
		this.tInd = tInd;
		this.cInd = cInd;
		this.zInd = zInd;
		this.reasonTp = reasonTp;
	}

	public DamageComponent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DamageComponent(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}

}
