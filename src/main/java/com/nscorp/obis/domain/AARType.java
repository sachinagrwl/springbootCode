package com.nscorp.obis.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "AAR_TP")
public class AARType extends AuditInfo {
	
	@Id
	@Column(name = "AAR_TP", columnDefinition = "char(4)", nullable = false)
	private String aarType;
	
	@Column(name = "AAR_DSC", columnDefinition = "char(30)", nullable = true)
	private String aarDescription;
	
	@Column(name = "AAR_CAPACITY", columnDefinition = "SMALLINT", nullable = true)
	private Integer aarCapacity;
	
	@Column(name = "IM_DSC", columnDefinition = "char(30)", nullable = true)
	private String imDescription;
	
	@Column(name = "STANDARD_AAR_TP", columnDefinition = "char(1)", nullable = true)
	private String standardAarType;

	public AARType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AARType(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, String aarType, String aarDescription,
			Integer aarCapacity, String imDescription, String standardAarType) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.aarType = aarType;
		this.aarDescription = aarDescription;
		this.aarCapacity = aarCapacity;
		this.imDescription = imDescription;
		this.standardAarType = standardAarType;
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

	public String getAarDescription() {
		if(aarDescription != null) {
			return aarDescription.trim();
		}
		else {
			return aarDescription;
		}
	}

	public void setAarDescription(String aarDescription) {
		if (aarDescription != null) {
			this.aarDescription = aarDescription.toUpperCase();
		} else {
			this.aarDescription = aarDescription;
		}
	}

	public Integer getAarCapacity() {
		return aarCapacity;
	}

	public void setAarCapacity(Integer aarCapacity) {
		this.aarCapacity = aarCapacity;
	}

	public String getImDescription() {
		if(imDescription != null) {
			return imDescription.trim();
		}
		else {
			return imDescription;
		}
	}

	public void setImDescription(String imDescription) {
		this.imDescription = imDescription;
	}

	public String getStandardAarType() {
		if(standardAarType != null) {
			return standardAarType.trim();
		}
		else {
			return standardAarType;
		}
	}

	public void setStandardAarType(String standardAarType) {
		this.standardAarType = standardAarType;
	}

}
