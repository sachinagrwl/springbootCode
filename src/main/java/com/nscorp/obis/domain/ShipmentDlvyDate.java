package com.nscorp.obis.domain;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;


@SuppressWarnings("serial")
@Entity
@Table(name = "SHIP_DLVY_DATE")
public class ShipmentDlvyDate extends AuditInfo implements Serializable {
	
	@Id
	@Column(name = "SVC_ID", columnDefinition = "Double(15)", nullable = false)
    private Long svcId;
	
	@Column(name = "DLVY_BY_DT_TM", columnDefinition = "TimeStamp(10)", nullable = true)
    private Timestamp dlvyByDtTm;
	
	public Long getSvcId() {
		return svcId;
	}
	public void setSvcId(Long svcId) {
		this.svcId = svcId;
	}
	public Timestamp getDlvyByDtTm() {
		return dlvyByDtTm;
	}
	public void setDlvyByDtTm(Timestamp dlvyByDtTm) {
		this.dlvyByDtTm = dlvyByDtTm;
	}
	public ShipmentDlvyDate(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Long svcId, Timestamp dlvyByDtTm) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.svcId = svcId;
		this.dlvyByDtTm = dlvyByDtTm;
	}
	public ShipmentDlvyDate() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ShipmentDlvyDate(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}
    
    

}