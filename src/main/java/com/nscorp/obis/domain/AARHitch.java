package com.nscorp.obis.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "AAR_HITCH")
@IdClass(AARHitchPrimaryKeys.class)
public class AARHitch extends AuditInfo {

	@Id
	@Column(name = "AAR_TP", columnDefinition = "char(4)", nullable = false)
	private String aarType;

	@Id
	@Column(name = "HCH_LOC", columnDefinition = "char(2)", nullable = false)
    private String hitchLocation;

	public AARHitch(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, String aarType, String hitchLocation) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.aarType = aarType;
		this.hitchLocation = hitchLocation;
	}

	public AARHitch() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AARHitch(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}

	public String getAarType() {
		if(aarType != null) {
			return aarType.trim();
		}
		else {
		return aarType;
		}
	}

	public void setAarType(String aarType) {
		if (aarType != null) {
			this.aarType = aarType.toUpperCase();
		} else {
		this.aarType = aarType;
		}
	}

	public String getHitchLocation() {
		if(hitchLocation != null) {
			return hitchLocation.trim();
		}
		else {
		return hitchLocation;
		}
	}

	public void setHitchLocation(String hitchLocation) {
		if (hitchLocation != null) {
			this.hitchLocation = hitchLocation.toUpperCase();
		} else {
		this.hitchLocation = hitchLocation;
		}
	}
  
}
