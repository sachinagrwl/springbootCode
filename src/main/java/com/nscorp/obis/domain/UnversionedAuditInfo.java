package com.nscorp.obis.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import java.sql.Timestamp;

@MappedSuperclass
public class UnversionedAuditInfo {
	@Column(name = "CREATE_USER_ID", columnDefinition = "char(8)", nullable = true)
	private String createUserId;
	
	@CreationTimestamp
	@Column(name = "CREATE_DT_TM", nullable = true)
	private Timestamp createDateTime;
	
	@Column(name = "UPD_USER_ID", columnDefinition = "char(8)", nullable = true)
	private String updateUserId;
	
	@UpdateTimestamp
	@Column(name = "UPD_DT_TM", nullable = true)
	private Timestamp updateDateTime;
	
	@Column(name = "UPD_EXTN_SCHEMA", columnDefinition = "char(16)", nullable = true)
	private String updateExtensionSchema;

	public UnversionedAuditInfo(String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super();
		this.createUserId = createUserId;
		this.createDateTime = createDateTime;
		this.updateUserId = updateUserId;
		this.updateDateTime = updateDateTime;
		this.updateExtensionSchema = updateExtensionSchema;
	}

	public String getCreateUserId() {
		if(createUserId!=null) {
			return createUserId.trim();
		}
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Timestamp getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Timestamp createDateTime) {
		this.createDateTime = createDateTime;
	}

	public String getUpdateUserId() {
		if(updateUserId!=null) {
			return updateUserId.trim();
		}
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Timestamp getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(Timestamp updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	public String getUpdateExtensionSchema() {
		if(updateExtensionSchema!=null) {
			return updateExtensionSchema.trim();
		}
		return updateExtensionSchema;
	}

	public void setUpdateExtensionSchema(String updateExtensionSchema) {
		this.updateExtensionSchema = updateExtensionSchema;
	}

	@Override
	public String toString() {
		return "AuditInfo [createUserId=" + createUserId + ", createDateTime="
				+ createDateTime + ", updateUserId=" + updateUserId + ", updateDateTime=" + updateDateTime
				+ ", updateExtensionSchema=" + updateExtensionSchema + "]";
	}

	public UnversionedAuditInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
}
