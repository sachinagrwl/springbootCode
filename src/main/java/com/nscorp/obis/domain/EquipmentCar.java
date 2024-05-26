package com.nscorp.obis.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import lombok.EqualsAndHashCode;

@Entity
@Table(name = "EQ_CAR")
@IdClass(EquipmentCarPrimaryKey.class)
@EqualsAndHashCode(callSuper = false)
public class EquipmentCar extends AuditInfo {

	@Id
	@Column(name = "CAR_INIT", columnDefinition = "char(4)", nullable = false)
	private String carInit;

	@Id
	@Column(name = "CAR_NR", columnDefinition = "decimal", nullable = false)
	private BigDecimal carNbr;

	@Id
	@Column(name = "CAR_EQ_TP", columnDefinition = "char(1)", nullable = false)
	private String carEquipType;

	@Column(name = "AAR_TP", columnDefinition = "char(4)", nullable = true)
	private String aarType;

	@Column(name = "CAR_LGTH", columnDefinition = "smallint", nullable = true)
	private Integer carLgth;

	@Column(name = "CAR_TARE_WGT", columnDefinition = "smallint", nullable = true)
	private Integer carTareWgt;

	@Column(name = "DMGE_IND", columnDefinition = "char(1)", nullable = true)
	private String damageInd;

	@Column(name = "BORD_IND", columnDefinition = "char(1)", nullable = true)
	private String badOrderInd;

	@Column(name = "CAR_SA", columnDefinition = "double", nullable = true)
	private Long carSa;

	@Column(name = "PREV_STCC", columnDefinition = "integer", nullable = true)
	private Integer prevStcc;

	@Column(name = "CAR_OWNR_TP", columnDefinition = "char(1)", nullable = true)
	private String carOwnerType;

	@Column(name = "PLAT_HGTH", columnDefinition = "smallint", nullable = true)
	private Integer platformHeight_inches;

	@Column(name = "ARTICULATE", columnDefinition = "char(1)", nullable = true)
	private String articulate;

	@Column(name = "NR_OF_AXL", columnDefinition = "smallint", nullable = true)
	private Integer nrOfAxles;

	@Column(name = "CAR_LOAD_LMT", columnDefinition = "smallint", nullable = true)
	private Integer carLoadLimit;

	@Transient
	private List<EquipmentHitch> equipmentHitch;

	@Transient
	private List<EquipmentLocation> equipmentLocation;

	@Transient
	private List<HoldOrders> holdOrders;

	public String getCarInit() {
		return StringUtils.isNotBlank(carInit) ? carInit.trim() : carInit;
	}

	public void setCarInit(String carInit) {
		this.carInit = StringUtils.isNotBlank(carInit) ? carInit.trim() : carInit;
	}

	public BigDecimal getCarNbr() {
		return carNbr;
	}

	public void setCarNbr(BigDecimal carNbr) {
		this.carNbr = carNbr;
	}

	public String getCarEquipType() {
		return carEquipType;
	}

	public void setCarEquipType(String carEquipType) {
		this.carEquipType = carEquipType;
	}

	public String getAarType() {
		return aarType;
	}

	public void setAarType(String aarType) {
		this.aarType = aarType;
	}

	public Integer getCarLgth() {
		return carLgth;
	}

	public void setCarLgth(Integer carLgth) {
		this.carLgth = carLgth;
	}

	public Integer getCarTareWgt() {
		return carTareWgt;
	}

	public void setCarTareWgt(Integer carTareWgt) {
		this.carTareWgt = carTareWgt;
	}

	public String getDamageInd() {
		return damageInd;
	}

	public void setDamageInd(String damageInd) {
		this.damageInd = damageInd;
	}

	public String getBadOrderInd() {
		return badOrderInd;
	}

	public void setBadOrderInd(String badOrderInd) {
		this.badOrderInd = badOrderInd;
	}

	public Long getCarSa() {
		return carSa;
	}

	public void setCarSa(Long carSa) {
		this.carSa = carSa;
	}

	public Integer getPrevStcc() {
		return prevStcc;
	}

	public void setPrevStcc(Integer prevStcc) {
		this.prevStcc = prevStcc;
	}

	public String getCarOwnerType() {
		return carOwnerType;
	}

	public void setCarOwnerType(String carOwnerType) {
		this.carOwnerType = carOwnerType;
	}

	public Integer getPlatformHeight_inches() {
		return platformHeight_inches;
	}

	public void setPlatformHeight_inches(Integer platformHeight_inches) {
		this.platformHeight_inches = platformHeight_inches;
	}

	public String getArticulate() {
		return articulate;
	}

	public void setArticulate(String articulate) {
		this.articulate = articulate;
	}

	public Integer getNrOfAxles() {
		return nrOfAxles;
	}

	public void setNrOfAxles(Integer nrOfAxles) {
		this.nrOfAxles = nrOfAxles;
	}

	public Integer getCarLoadLimit() {
		return carLoadLimit;
	}

	public void setCarLoadLimit(Integer carLoadLimit) {
		this.carLoadLimit = carLoadLimit;
	}

	public List<EquipmentHitch> getEquipmentHitch() {
		return equipmentHitch;
	}

	public void setEquipmentHitch(List<EquipmentHitch> equipmentHitch) {
		this.equipmentHitch = equipmentHitch;
	}

	public List<EquipmentLocation> getEquipmentLocation() {
		return equipmentLocation;
	}

	public void setEquipmentLocation(List<EquipmentLocation> equipmentLocation) {
		this.equipmentLocation = equipmentLocation;
	}

	public List<HoldOrders> getHoldOrders() {
		return holdOrders;
	}

	public void setHoldOrders(List<HoldOrders> holdOrders) {
		this.holdOrders = holdOrders;
	}

	public EquipmentCar() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EquipmentCar(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}

	public EquipmentCar(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, String carInit, BigDecimal carNbr,
			String carEquipType, String aarType, Integer carLgth, Integer carTareWgt, String damageInd,
			String badOrderInd, Long carSa, Integer prevStcc, String carOwnerType, Integer platformHeight_inches,
			String articulate, Integer nrOfAxles, Integer carLoadLimit, List<EquipmentHitch> equipmentHitch,
			List<EquipmentLocation> equipmentLocation, List<HoldOrders> holdOrders) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.carInit = carInit;
		this.carNbr = carNbr;
		this.carEquipType = carEquipType;
		this.aarType = aarType;
		this.carLgth = carLgth;
		this.carTareWgt = carTareWgt;
		this.damageInd = damageInd;
		this.badOrderInd = badOrderInd;
		this.carSa = carSa;
		this.prevStcc = prevStcc;
		this.carOwnerType = carOwnerType;
		this.platformHeight_inches = platformHeight_inches;
		this.articulate = articulate;
		this.nrOfAxles = nrOfAxles;
		this.carLoadLimit = carLoadLimit;
		this.equipmentHitch = equipmentHitch;
		this.equipmentLocation = equipmentLocation;
		this.holdOrders = holdOrders;
	}

}
