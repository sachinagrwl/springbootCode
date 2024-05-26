package com.nscorp.obis.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ENDORSEMENT_CD")
public class EndorsementCode extends AuditInfo {
	@Id
    @Column(name = "ENDORSEMENT_CD", columnDefinition = "char(2)", nullable = false)
    private String endorsementCd;

    @Column(name = "ENDORSE_CD_DSC", columnDefinition = "char(25)", nullable = true)
    private String endorseCdDesc;

	public String getEndorsementCd() {
		return endorsementCd;
	}

	public void setEndorsementCd(String endorsementCd) {
		this.endorsementCd = endorsementCd;
	}

	public String getEndorseCdDesc() {
		if(endorseCdDesc!=null) {
			return endorseCdDesc.trim();
		}
		return endorseCdDesc;
	}

	public void setEndorseCdDesc(String endorseCdDesc) {
		this.endorseCdDesc = endorseCdDesc;
	}

	public EndorsementCode(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, String endorsementCd, String endorseCdDesc) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.endorsementCd = endorsementCd;
		this.endorseCdDesc = endorseCdDesc;
	}

	public EndorsementCode() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EndorsementCode(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}
    
    
}