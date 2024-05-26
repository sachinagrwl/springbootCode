package com.nscorp.obis.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "CAR_EMBARGO")
public class CarEmbargo extends AuditInfo {

    @Id
    @Column(name = "EMBARGO_ID", columnDefinition = "Double", nullable = false)
    private Long embargoId;

    @Column(name = "AAR_TP", columnDefinition = "char(4)", nullable = true)
    private String aarType;

    @Column(name = "ROAD_NM", columnDefinition = "char(4)", nullable = true)
    private String roadName;

    @Column(name = "RESTRICTION", columnDefinition = "char(1)", nullable = true)
    private String restriction;

    @Column(name = "DESCRIPTION", columnDefinition = "char(50)", nullable = true)
    private String description;

//    @Column(name = "EMBRGO_TERM_ID", columnDefinition = "Double", nullable = true)
//    private Long embargoTerminalId;
    
    @ManyToOne
	@JoinColumn(name = "EMBRGO_TERM_ID", referencedColumnName = "TERM_ID")
    private Station terminal;

    @Column(name = "EQ_INIT", columnDefinition = "char(4)", nullable = false)
    private String equipmentInit;

    @Column(name = "EQ_NR_LOW", columnDefinition = "Double", nullable = true)
    private BigDecimal equipmentNumberLow;

    @Column(name = "EQ_NR_HIGH", columnDefinition = "Double", nullable = true)
    private BigDecimal equipmentNumberHigh;

	public Long getEmbargoId() {
		return embargoId;
	}

	public void setEmbargoId(Long embargoId) {
		this.embargoId = embargoId;
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
		if(aarType != null){
			this.aarType = aarType.toUpperCase();
		}
		else {
			this.aarType = aarType;
		}
	}

	public String getRoadName() {
		if(roadName != null) {
			return roadName.trim();
		}
		else {
			return roadName;
		}
	}

	public void setRoadName(String roadName) {
		if(roadName != null){
			this.roadName = roadName.toUpperCase();
		}
		else {
			this.roadName = roadName;
		}
	}

	public String getRestriction() {
		if(restriction != null) {
			return restriction.trim();
		}
		else {
			return restriction;
		}
	}

	public void setRestriction(String restriction) {
		if(restriction != null){
			this.restriction = restriction.toUpperCase();
		}
		else {
			this.restriction = restriction;
		}
	}

	public String getDescription() {
		if(description != null) {
			return description.trim();
		}
		else {
			return description;
		}
	}

	public void setDescription(String description) {
		if(description != null){
			this.description = description.toUpperCase();
		}
		else {
			this.description = description;
		}
	}

//	public Long getEmbargoTerminalId() {
//		return embargoTerminalId;
//	}
//
//	public void setEmbargoTerminalId(Long embargoTerminalId) {
//		this.embargoTerminalId = embargoTerminalId;
//	}

	public Station getTerminal() {
		if(terminal != null) {
			return terminal;
		} else {
			return null;
		}
	}

	public void setTerminal(Station terminal) {
		this.terminal = terminal;
	}

	public String getEquipmentInit() {
		if(equipmentInit != null) {
			return equipmentInit.trim();
		}
		else {
			return equipmentInit;
		}
	}

	public void setEquipmentInit(String equipmentInit) {
		if(equipmentInit != null){
			this.equipmentInit = equipmentInit.toUpperCase();
		}
		else {
			this.equipmentInit = equipmentInit;
		}
	}

	public BigDecimal getEquipmentNumberLow() {
		return equipmentNumberLow;
	}

	public void setEquipmentNumberLow(BigDecimal equipmentNumberLow) {
		this.equipmentNumberLow = equipmentNumberLow;
	}

	public BigDecimal getEquipmentNumberHigh() {
		return equipmentNumberHigh;
	}

	public void setEquipmentNumberHigh(BigDecimal equipmentNumberHigh) {
		this.equipmentNumberHigh = equipmentNumberHigh;
	}

	public CarEmbargo(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Long embargoId, String aarType, String roadName,
			String restriction, String description, Station terminal, String equipmentInit,
			BigDecimal equipmentNumberLow, BigDecimal equipmentNumberHigh) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.embargoId = embargoId;
		this.aarType = aarType;
		this.roadName = roadName;
		this.restriction = restriction;
		this.description = description;
		this.terminal = terminal;
		this.equipmentInit = equipmentInit;
		this.equipmentNumberLow = equipmentNumberLow;
		this.equipmentNumberHigh = equipmentNumberHigh;
	}

	public CarEmbargo() {
		super();
	}

	public CarEmbargo(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
	}
  
}