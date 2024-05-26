package com.nscorp.obis.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PLACARD_TP")
public class PlacardType extends AuditInfo {

    @Id
    @Column(name = "PLACARD_CD", columnDefinition = "char(2)", nullable = false)
    private String placardCd;

    @Column(name = "PLACARD_LONG_DESC", columnDefinition = "char(30)", nullable = false)
    private String placardLongDesc;

    @Column(name = "PLACARD_SHORT_DESC", columnDefinition = "char(10)", nullable = false)
    private String placardShortDesc;

    
	public String getPlacardCd() {
		if(placardCd != null) {
			return placardCd.trim();
		}
		else {
			return placardCd ;
		}
	}

	public void setPlacardCd(String placardCd) {
		this.placardCd = placardCd;
	}

	public String getPlacardLongDesc() {
		if(placardLongDesc != null) {
			return placardLongDesc.trim();
		}
		else {
			return placardLongDesc ;
		}
	}

	public void setPlacardLongDesc(String placardLongDesc) {
		this.placardLongDesc = placardLongDesc;
	}

	public String getPlacardShortDesc() {
		if(placardShortDesc != null) {
			return placardShortDesc.trim();
		}
		else {
			return placardShortDesc ;
		}
	}

	public void setPlacardShortDesc(String placardShortDesc) {
		this.placardShortDesc = placardShortDesc;
	}

	public PlacardType(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, String placardCd, String placardLongDesc,
			String placardShortDesc) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.placardCd = placardCd;
		this.placardLongDesc = placardLongDesc;
		this.placardShortDesc = placardShortDesc;
	}

	public PlacardType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlacardType(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}
    
    

}
