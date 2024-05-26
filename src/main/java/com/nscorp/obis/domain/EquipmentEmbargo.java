package com.nscorp.obis.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "EQ_EMBARGO")
public class EquipmentEmbargo extends AuditInfo {
	
	@Id
    @Column(name = "EMBARGO_ID", columnDefinition = "double", nullable = false)
    private Long embargoId;

    @Column(name = "EQ_INIT", columnDefinition = "char(4)", nullable = false)
    private String equipmentInit;

    @Column(name = "EQ_NR_LOW", columnDefinition = "decimal(19)", nullable = false)
    private BigDecimal equipmentNumberLow;

    @Column(name = "EQ_NR_HIGH", columnDefinition = "decimal(19)", nullable = false)
    private BigDecimal equipmentNumberHigh;

    @Column(name = "EQ_TP", columnDefinition = "char(1)", nullable = false)
    private String equipmentType;
    
    @Transient
    private EquipmentType equipmentTypeCode;

    @Column(name = "EQ_AAR_TP", columnDefinition = "char(4)", nullable = true)
    private String equipmentAarType;

    @Column(name = "EQ_LGTH", columnDefinition = "smallint", nullable = true)
    private Integer equipmentLength;

    @Column(name = "TOFC_COFC_IND", columnDefinition = "char(1)", nullable = true)
    private String tofcCofcIndicator;
    
    @ManyToOne
	@JoinColumn(name = "EMBRGO_TERM_ID", referencedColumnName = "TERM_ID")
    private Station destinationTerminal;
    
    @ManyToOne
	@JoinColumn(name = "ORIGIN_TERM_ID", referencedColumnName = "TERM_ID")
    private Station originTerminal;

	@Column(name = "ROAD_NM", columnDefinition = "char(4)", nullable = true)
    private String roadName;

    @Column(name = "RESTRICTION", columnDefinition = "char(1)", nullable = true)
    private String restriction;

    @Column(name = "DESCRIPTION", columnDefinition = "char(50)", nullable = true)
    private String description;

    @Column(name = "CAR_INIT", columnDefinition = "char(4)", nullable = true)
    private String carInit;

    @Column(name = "CAR_NR_LOW", columnDefinition = "decimal(19)", nullable = true)
    private BigDecimal carNumberLow;

    @Column(name = "CAR_NR_HIGH", columnDefinition = "decimal(19)", nullable = true)
    private BigDecimal carNumberHigh;

    @Column(name = "CAR_EQ_TP", columnDefinition = "char(1)", nullable = false)
    private String carEquipmentType;

    @Column(name = "CAR_AAR_TP", columnDefinition = "char(4)", nullable = true)
    private String carAarType;

    @Column(name = "WELL_A", columnDefinition = "char(1)", nullable = true)
    private String wellA;

    @Column(name = "WELL_B", columnDefinition = "char(1)", nullable = true)
    private String wellB;

    @Column(name = "WELL_C", columnDefinition = "char(1)", nullable = true)
    private String wellC;

    @Column(name = "WELL_D", columnDefinition = "char(1)", nullable = true)
    private String wellD;

    @Column(name = "WELL_E", columnDefinition = "char(1)", nullable = true)
    private String wellE;
    
    @Transient
	private List<RestrictedWells> restrictedWells;

	public EquipmentEmbargo(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Long embargoId, String equipmentInit,
			BigDecimal equipmentNumberLow, BigDecimal equipmentNumberHigh, String equipmentType,
			EquipmentType equipmentTypeCode, String equipmentAarType, Integer equipmentLength, String tofcCofcIndicator,
			Station destinationTerminal, Station originTerminal,
			String roadName, String restriction, String description, String carInit, BigDecimal carNumberLow,
			BigDecimal carNumberHigh, String carEquipmentType, String carAarType, String wellA, String wellB,
			String wellC, String wellD, String wellE, List<RestrictedWells> restrictedWells) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.embargoId = embargoId;
		this.equipmentInit = equipmentInit;
		this.equipmentNumberLow = equipmentNumberLow;
		this.equipmentNumberHigh = equipmentNumberHigh;
		this.equipmentType = equipmentType;
		this.equipmentTypeCode = equipmentTypeCode;
		this.equipmentAarType = equipmentAarType;
		this.equipmentLength = equipmentLength;
		this.tofcCofcIndicator = tofcCofcIndicator;
		this.destinationTerminal = destinationTerminal;
		this.originTerminal = originTerminal;
		this.roadName = roadName;
		this.restriction = restriction;
		this.description = description;
		this.carInit = carInit;
		this.carNumberLow = carNumberLow;
		this.carNumberHigh = carNumberHigh;
		this.carEquipmentType = carEquipmentType;
		this.carAarType = carAarType;
		this.wellA = wellA;
		this.wellB = wellB;
		this.wellC = wellC;
		this.wellD = wellD;
		this.wellE = wellE;
		this.restrictedWells = restrictedWells;
	}

	public Long getEmbargoId() {
		return embargoId;
	}

	public void setEmbargoId(Long embargoId) {
		this.embargoId = embargoId;
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
		if(equipmentInit != null)
			this.equipmentInit = equipmentInit.toUpperCase();
		else 
			this.equipmentInit = equipmentInit;
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

	public String getEquipmentType() {
		if(equipmentType != null) {
			return equipmentType.trim();
		}
		else {
			return equipmentType;
		}
	}

	public void setEquipmentType(String equipmentType) {
		if(equipmentType != null){
			this.equipmentType = equipmentType.toUpperCase();
		}
		else {
			this.equipmentType = equipmentType;
		}
	}

	public EquipmentType getEquipmentTypeCode() {
		return equipmentTypeCode;
	}

	public void setEquipmentTypeCode(EquipmentType equipmentTypeCode) {
		this.equipmentTypeCode = equipmentTypeCode;
	}

	public String getEquipmentAarType() {
		if(equipmentAarType != null) {
			return equipmentAarType.trim();
		}
		else {
			return equipmentAarType;
		}
	}

	public void setEquipmentAarType(String equipmentAarType) {
		if(equipmentAarType != null)
			this.equipmentAarType = equipmentAarType.toUpperCase();
		else
			this.equipmentAarType = equipmentAarType;
	}

	public Integer getEquipmentLength() {
		return equipmentLength;
	}

	public void setEquipmentLength(Integer equipmentLength) {
		this.equipmentLength = equipmentLength;
	}

	public String getTofcCofcIndicator() {
		if(tofcCofcIndicator != null) {
			return tofcCofcIndicator.trim();
		}
		else {
			return tofcCofcIndicator;
		}
	}

	public void setTofcCofcIndicator(String tofcCofcIndicator) {
		if(tofcCofcIndicator != null)
			this.tofcCofcIndicator = tofcCofcIndicator.toUpperCase();
		else
			this.tofcCofcIndicator = tofcCofcIndicator;
	}

	public Station getDestinationTerminal() {
		if(destinationTerminal != null) {
			return destinationTerminal;
		} else {
			return null;
		}
	}

	public void setDestinationTerminal(Station destinationTerminal) {
		this.destinationTerminal = destinationTerminal;
	}

	public Station getOriginTerminal() {
		if(originTerminal != null) {
			return originTerminal;
		} else {
			return null;
		}
	}

	public void setOriginTerminal(Station originTerminal) {
		this.originTerminal = originTerminal;
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
		if(roadName != null)
			 this.roadName = roadName.toUpperCase();
		else
			this.roadName = roadName;
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
		if(restriction != null)
			this.restriction = restriction.toUpperCase();
		else
			this.restriction = restriction;
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
		if(description != null)
			this.description = description.toUpperCase();
		else
			this.description = description;
	}

	public String getCarInit() {
		if(carInit != null) {
			return carInit.trim();
		}
		else {
			return carInit;
		}
	}

	public void setCarInit(String carInit) {
		if(carInit != null)
			this.carInit = carInit.toUpperCase();
		else
			this.carInit = carInit;
	}

	public BigDecimal getCarNumberLow() {
		return carNumberLow;
	}

	public void setCarNumberLow(BigDecimal carNumberLow) {
		this.carNumberLow = carNumberLow;
	}

	public BigDecimal getCarNumberHigh() {
		return carNumberHigh;
	}

	public void setCarNumberHigh(BigDecimal carNumberHigh) {
		this.carNumberHigh = carNumberHigh;
	}

	public String getCarEquipmentType() {
		if(carEquipmentType != null) {
			return carEquipmentType.trim();
		}
		else {
			return carEquipmentType;
		}
	}

	public void setCarEquipmentType(String carEquipmentType) {
		if(carEquipmentType != null)
			this.carEquipmentType = carEquipmentType.toUpperCase();
		else
			this.carEquipmentType = carEquipmentType;
	}

	public String getCarAarType() {
		if(carAarType != null) {
			return carAarType.trim();
		}
		else {
			return carAarType;
		}
	}

	public void setCarAarType(String carAarType) {
		if(carAarType != null)
			this.carAarType = carAarType.toUpperCase();
		else
			this.carAarType = carAarType;
	}

	public List<RestrictedWells> getRestrictedWells() {
		this.restrictedWells = new ArrayList<RestrictedWells>();
		if("T".equalsIgnoreCase(this.wellA))
			this.restrictedWells.add(RestrictedWells.A);
		if("T".equalsIgnoreCase(this.wellB))
			this.restrictedWells.add(RestrictedWells.B);
		if("T".equalsIgnoreCase(this.wellC))
			this.restrictedWells.add(RestrictedWells.C);
		if("T".equalsIgnoreCase(this.wellD))
			this.restrictedWells.add(RestrictedWells.D);
		if("T".equalsIgnoreCase(this.wellE))
			this.restrictedWells.add(RestrictedWells.E);
		return restrictedWells;
	}

	public void setRestrictedWells(List<RestrictedWells> restrictedWells) {
		
		this.wellA = restrictedWells.contains(RestrictedWells.A)?"T":null;
		this.wellB = restrictedWells.contains(RestrictedWells.B)?"T":null;
		this.wellC = restrictedWells.contains(RestrictedWells.C)?"T":null;
		this.wellD = restrictedWells.contains(RestrictedWells.D)?"T":null;
		this.wellE = restrictedWells.contains(RestrictedWells.E)?"T":null;
		this.restrictedWells = restrictedWells;		
		
	}

	public EquipmentEmbargo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EquipmentEmbargo(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}

}
