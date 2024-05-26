package com.nscorp.obis.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "EQ_CONT")
@IdClass(EquipmentContPrimaryKeys.class)
public class EquipmentCont extends AuditInfo{
	@Id
	@Column(name = "CONT_INIT", columnDefinition = "char(4)", nullable = false)
    private String containerInit;

	@Id
	@Column(name = "CONT_EQ_TP", columnDefinition = "char(1)", nullable = false)
	private String containerEquipType;
    
	@Id
	@Column(name = "CONT_NR", length = 19, columnDefinition = "decimal", nullable = false)
	private BigDecimal containerNbr;
	
	@Id
	@Column(name = "CONT_ID",  columnDefinition = "char(4)", nullable = false)
	private String containerId;
	
	
	
	public String getContainerId() {
		return containerId;
	}
	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}
	public String getContainerInit() {
		return containerInit;
	}
	public void setContainerInit(String containerInit) {
		this.containerInit = containerInit;
	}
	public String getContainerEquipType() {
		return containerEquipType;
	}
	public void setContainerEquipType(String containerEquipType) {
		this.containerEquipType = containerEquipType;
	}
	public BigDecimal getContainerNbr() {
		return containerNbr;
	}
	public void setContainerNbr(BigDecimal containerNbr) {
		this.containerNbr = containerNbr;
	}
	public EquipmentCont(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, String containerInit, String containerEquipType,
						 BigDecimal containerNbr, String containerId) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.containerInit = containerInit;
		this.containerEquipType = containerEquipType;
		this.containerNbr = containerNbr;
		this.containerId = containerId;
	}
	public EquipmentCont() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EquipmentCont(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}
    
    
}

