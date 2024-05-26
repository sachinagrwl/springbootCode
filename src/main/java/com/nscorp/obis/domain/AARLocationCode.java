package com.nscorp.obis.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AAR_LOC_CD")
public class AARLocationCode extends AuditInfo {

    @Id
    @Column(name = "LOC_CD", columnDefinition = "char(3)", nullable = false)
    private String locCd;

    @Column(name = "LOC_DESC", columnDefinition = "char(20)", nullable = false)
    private String locDesc;

	public String getLocCd() {
		if(locCd!=null) {
			return locCd.trim();
		}
		return locCd;
	}

	public void setLocCd(String locCd) {
		this.locCd = locCd;
	}

	public String getLocDesc() {
		if(locDesc!=null) {
			return locDesc.trim();
		}
		return locDesc;
	}

	public void setLocDesc(String locDesc) {
		this.locDesc = locDesc.toUpperCase();
	}

	public AARLocationCode(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, String locCd, String locDesc) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.locCd = locCd;
		this.locDesc = locDesc;
	}

	public AARLocationCode() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AARLocationCode(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}
    
    

}