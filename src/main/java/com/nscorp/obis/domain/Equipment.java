package com.nscorp.obis.domain;

import java.sql.Timestamp;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "QT_USE_REC")
public class Equipment extends AuditInfo {
	
	@Id
	@Column(name = "EQ_INIT", columnDefinition = "char(4)", nullable = false)
    private String equipInit;
    

	@Column(name = "EQ_NR", length = 6, columnDefinition = "decimal", nullable = false)
    private Integer equipNbr;
    
	@Column(name = "EQ_TP", columnDefinition = "char(1)", nullable = false)
	private String equipType;
	
	@Column(name = "EQ_ID", columnDefinition = "char(4)", nullable = false)
	private String equipId;
	
	@Column(name = "QT_USE_REC_STAT ", columnDefinition = "char(1)", nullable = false)
	private String qtUseRec;
       
    
	public String getEquipInit() {
		return equipInit;
	}
	public void setEquipInit(String equipInit) {
		this.equipInit = equipInit;
	}
	public Integer getEquipNbr() {
		return equipNbr;
	}
	public void setEquipNbr(Integer equipNbr) {
		this.equipNbr = equipNbr;
	}
	
	
	public String getEquipType() {
		return equipType;
	}
	public void setEquipType(String equipType) {
		this.equipType = equipType;
	}
	public String getEquipId() {
		return equipId;
	}
	public void setEquipId(String equipId) {
		this.equipId = equipId;
	}
	public String getQtUseRec() {
		return qtUseRec;
	}
	public void setQtUseRec(String qtUseRec) {
		this.qtUseRec = qtUseRec;
	}
	public Equipment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Equipment(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, String equipInit, Integer equipNbr,
			String equipType, String equipId, String qtUseRec) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.equipInit = equipInit;
		this.equipNbr = equipNbr;
		this.equipType = equipType;
		this.equipId = equipId;
		this.qtUseRec = qtUseRec;
	}
	
	

	
	
    
    
}