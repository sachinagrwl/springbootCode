package com.nscorp.obis.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(EquipLocPrimaryKey.class)
@Table(name = "EQ_LOC")
public class EquipmentLocation extends AuditInfo {
	
	@Id
	@Column(name = "EQ_ID", columnDefinition = "char(4)", nullable = false)
    private String equipId;
	
	@Id
	@Column(name = "EQ_TP", columnDefinition = "char(1)", nullable = false)
	private String equipTp;
    
	@Id
	@Column(name = "EQ_NR",length = 19, columnDefinition = "decimal", nullable = false)
	private BigDecimal equipNbr;
	
	@Id
	@Column(name = "EQ_INIT", columnDefinition = "char(4)", nullable = false)
	private String equipInit;
   
	@Column(name = "CHAS_INIT", columnDefinition = "char(4)", nullable = true)
	private String chasInit;
   
	@Column(name = "CHAS_NR",length = 19, columnDefinition = "decimal", nullable = true) 
	private BigDecimal chasNbr;
	
	@Column(name = "CHAS_EQ_TP", columnDefinition = "char(1)", nullable = true)
	private String chasTp;
	
	@Column(name = "CHAS_ID", columnDefinition = "char(4)", nullable = true)
	private String chasId;

	@Column(name = "TERM_ID", columnDefinition = "double(19)", nullable = false)
	private Long terminalId;

	@Column(name = "CURR_LOC_TP", columnDefinition = "char(40)", nullable = true)
	private String currLoc;

	@Column(name = "TRACK_ID", columnDefinition = "char(4)", nullable = true)
	private String trackId;

	@Column(name = "EQ_LOC_AAR_CD", columnDefinition = "char(4)", nullable = true)
	private String equipLocationAarCode;

	public Long getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}

	public String getCurrLoc() {
		return currLoc;
	}

	public void setCurrLoc(String currLoc) {
		this.currLoc = currLoc;
	}

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public String getEquipLocationAarCode() {
		return equipLocationAarCode;
	}

	public void setEquipLocationAarCode(String equipLocationAarCode) {
		this.equipLocationAarCode = equipLocationAarCode;
	}

	public String getChasTp() {
		return chasTp;
	}
	public void setChasTp(String chasTp) {
		this.chasTp = chasTp;
	}
	public String getChasId() {
		if(chasId!=null) {
			return chasId.trim();
		}
		return chasId;
	}
	public void setChasId(String chasId) {
		this.chasId = chasId;
	}
	public String getEquipId() {
		return equipId;
	}
	public void setEquipId(String equipId) {
		this.equipId = equipId;
	}
	public String getEquipTp() {
		return equipTp;
	}
	public void setEquipTp(String equipTp) {
		this.equipTp = equipTp;
	}
	public BigDecimal getEquipNbr() {
		return equipNbr;
	}
	public void setEquipNbr(BigDecimal equipNbr) {
		this.equipNbr = equipNbr;
	}
	public String getEquipInit() {
		return equipInit;
	}
	public void setEquipInit(String equipInit) {
		this.equipInit = equipInit;
	}
	public String getChasInit() {
		return chasInit;
	}
	public void setChasInit(String chasInit) {
		this.chasInit = chasInit;
	}
	public BigDecimal getChasNbr() {
		return chasNbr;
	}
	public void setChasNbr(BigDecimal chasNbr) {
		this.chasNbr = chasNbr;
	}
	
	public EquipmentLocation(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, String equipId, String equipTp, BigDecimal equipNbr,
			String equipInit, String chasInit, BigDecimal chasNbr, String chasTp, String chasId, Long terminalId, String currLoc,
			String trackId, String equipLocationAarCode) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.equipId = equipId;
		this.equipTp = equipTp;
		this.equipNbr = equipNbr;
		this.equipInit = equipInit;
		this.chasInit = chasInit;
		this.chasNbr = chasNbr;
		this.chasTp = chasTp;
		this.chasId = chasId;
		this.trackId = trackId;
		this.terminalId = terminalId;
		this.currLoc = currLoc;
		this.equipLocationAarCode = equipLocationAarCode;
	}
	public EquipmentLocation() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EquipmentLocation(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}
    
    
}
