package com.nscorp.obis.domain;

import java.sql.Timestamp;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "LOC_CONV")
public class DamageLocationConversion extends AuditInfo {
	@Id
	@Column(name = "LOC_DSCR", columnDefinition = "CHAR(20)", nullable = false)
	private String locDscr;

	@Column(name = "LOC_CD", columnDefinition = "CHAR(3)")
	private String locCd;

	@Column(name = "LOC_DESC", columnDefinition = "CHAR(20)")
	private String locDesc;


	public String getLocDscr() {
		if (locDscr != null) {
			return locDscr.trim();
		}
		return locDscr;
	}

	public void setLocDscr(String locDscr) {
		this.locDscr = locDscr;
	}

	public String getLocCd() {
		if (locCd != null) {
			return locCd.trim();
		}
		return locCd;
	}

	public void setLocCd(String locCd) {
		this.locCd = locCd;
	}

	public String getLocDesc() {
		if (locDesc != null) {
			return locDesc.trim();
		}
		return locDesc;
	}

	public void setLocDesc(String locDesc) {
		this.locDesc = locDesc;
	}

	public DamageLocationConversion(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
									Timestamp updateDateTime, String updateExtensionSchema, String locDscr, String locCd, String locDesc) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.locDscr = locDscr;
		this.locCd = locCd;
		this.locDesc = locDesc;
	}

	public DamageLocationConversion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DamageLocationConversion(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
									Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}


}