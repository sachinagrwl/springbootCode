package com.nscorp.obis.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Entity
@Table(name = "EQ_LOC")
@IdClass(EquipLocPrimaryKey.class)
@NoArgsConstructor
public class EquipLoc {

	@Id
	@Column(name = "EQ_INIT", columnDefinition = "char(4)", nullable = false)
    private String equipInit;

	@Id
	@Column(name = "EQ_NR",length = 19, columnDefinition = "decimal", nullable = false)
    private BigDecimal equipNbr;

	@Id
	@Column(name = "EQ_TP", columnDefinition = "char(1)", nullable = false)
    private String equipTp;

	@Id
	@Column(name = "EQ_ID", columnDefinition = "char(4)", nullable = false)
    private String equipId;

	@Column(name = "TERM_ID", columnDefinition = "double(19)", nullable = false)
    private Long terminalId;

	@Column(name = "CURR_LOC_TP", columnDefinition = "char(40)", nullable = true)
    private String currLoc;

	@Column(name = "TRACK_ID", columnDefinition = "char(4)", nullable = true)
	private String trackId;

	@Column(name = "EQ_LOC_AAR_CD", columnDefinition = "char(4)", nullable = true)
	private String equipLocationAarCode;

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

	public String getEquipInit() {
		return equipInit;
	}

	public void setEquipInit(String equipInit) {
		this.equipInit = equipInit;
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

	public String getEquipId() {
		return equipId;
	}

	public void setEquipId(String equipId) {
		this.equipId = equipId;
	}

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

	public EquipLoc(String equipInit, String equipTp, BigDecimal equipNbr, String equipId, Long terminalId,
			String currLoc, String trackId, String equipLocationAarCode) {
		super();
		this.equipInit = equipInit;
		this.equipTp = equipTp;
		this.equipNbr = equipNbr;
		this.equipId = equipId;
		this.terminalId = terminalId;
		this.currLoc = currLoc;
		this.trackId = trackId;
		this.equipLocationAarCode = equipLocationAarCode;
	}

	

}