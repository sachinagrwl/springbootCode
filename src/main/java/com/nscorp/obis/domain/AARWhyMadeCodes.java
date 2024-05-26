package com.nscorp.obis.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AAR_WHY_MADE_CODES")
public class AARWhyMadeCodes extends AuditInfo {

    @Id
    @Column(name = "AAR_WHY_MADE_CD", columnDefinition = "SMALLINT(2)", nullable = false)
    private Integer aarWhyMadeCd;

    @Column(name = "AAR_DESCRIPTION", columnDefinition = "CHAR(30)", nullable = true)
    private String aarDesc;

	public Integer getAarWhyMadeCd() {
		return aarWhyMadeCd;
	}

	public void setAarWhyMadeCd(Integer aarWhyMadeCd) {
		this.aarWhyMadeCd = aarWhyMadeCd;
	}

	public String getAarDesc() {
		if(aarDesc!=null) {
			return aarDesc.trim();
		}
		return aarDesc;
	}

	public void setAarDesc(String aarDesc) {
		this.aarDesc = aarDesc;
	}

	public AARWhyMadeCodes(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Integer aarWhyMadeCd, String aarDesc) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.aarWhyMadeCd = aarWhyMadeCd;
		this.aarDesc = aarDesc;
	}

	public AARWhyMadeCodes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AARWhyMadeCodes(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}
    
    
}