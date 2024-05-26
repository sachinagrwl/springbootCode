package com.nscorp.obis.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "DMGE_CODE_CONV")
@IdClass(DamageCodeConversionComposite.class)
public class DamageCodeConversion extends AuditInfo {
    @Id
    @Column(name = "CAT_CD", columnDefinition = "SMALLINT(5)", nullable = false)
    private Integer catCd;

    @Id
    @Column(name = "REASON_CD", columnDefinition = "CHAR(1)", nullable = false)
    private String reasonCd;

    @Column(name = "AAR_JOB_CODE", columnDefinition = "SMALLINT(5)", nullable=true)
    private Integer aarJobCd;

    @Column(name = "AAR_WHY_MADE_CD", columnDefinition = "SMALLINT(5)", nullable=true)
    private Integer aarWhyMadeCode;

    
	public Integer getCatCd() {
		return catCd;
	}

	public void setCatCd(Integer catCd) {
		this.catCd = catCd;
	}

	public String getReasonCd() {
		return reasonCd;
	}

	public void setReasonCd(String reasonCd) {
		this.reasonCd = reasonCd;
	}

	public Integer getAarJobCode() {
		return aarJobCd;
	}

	public void setAarJobCode(Integer aarJobCd) {
		this.aarJobCd = aarJobCd;
	}

	public Integer getAarWhyMadeCode() {
		return aarWhyMadeCode;
	}

	public void setAarWhyMadeCode(Integer aarWhyMadeCode) {
		this.aarWhyMadeCode = aarWhyMadeCode;
	}

	public DamageCodeConversion(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Integer catCd, String reasonCd, Integer aarJobCd,
			Integer aarWhyMadeCode) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.catCd = catCd;
		this.reasonCd = reasonCd;
		this.aarJobCd = aarJobCd;
		this.aarWhyMadeCode = aarWhyMadeCode;
	}

	public DamageCodeConversion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DamageCodeConversion(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}
   
}
