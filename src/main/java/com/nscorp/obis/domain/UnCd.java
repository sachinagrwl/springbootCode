package com.nscorp.obis.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UN_CD")
public class UnCd extends AuditInfo {

	@Id
	@Column(name = "UN_CD", columnDefinition = "char(6)", nullable = false)
	private String unCd;

	@Column(name = "UN_DSC", columnDefinition = "char(30)", nullable = true)
	private String unDsc;

	@Column(name = "UN_INSTRUCT_CD", columnDefinition = "Double(8)", nullable = true)
	private Long unInstructCd;

	public String getUnCd() {

		return unCd;
	}

	public void setUnCd(String unCd) {
		this.unCd = unCd;
	}

	public String getUnDsc() {
		if(unDsc !=null)
			return unDsc.trim();
		else
		return null;
	}

	public void setUnDsc(String unDsc) {
		this.unDsc = unDsc;
	}

	public Long getUnInstructCd() {
		return unInstructCd;
	}

	public void setUnInstructCd(Long unInstructCd) {
		this.unInstructCd = unInstructCd;
	}

	public UnCd(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, String unCd, String unDsc, Long unInstructCd) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.unCd = unCd;
		this.unDsc = unDsc;
		this.unInstructCd = unInstructCd;
	}

	public UnCd() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UnCd(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}

}
