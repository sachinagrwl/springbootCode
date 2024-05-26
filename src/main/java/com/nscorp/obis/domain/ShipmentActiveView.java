package com.nscorp.obis.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "SHIP_ACTIVE_V")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShipmentActiveView{
	
	@Id
    @Column(name = "SVC_ID",length = 15, columnDefinition = "double", nullable = false)
	private Long svcId;	
	
	@Column(name = "U_VERSION", columnDefinition = "char(1)", nullable = true)
	private String uversion;
	
	@CreationTimestamp
	@Column(name = "CREATE_DT_TM", nullable = true)
	private Timestamp createDateTime;

	
	@OneToOne(targetEntity = Shipment.class, orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "SVC_ID", referencedColumnName = "SVC_ID", insertable = false, updatable = false)
	@JsonIgnore
	private Shipment shipment;
	
	
    
	public Shipment getShipment() {
		return shipment;
	}

	public void setShipment(Shipment shipment) {
		this.shipment = shipment;
	}
	
	

	public ShipmentActiveView(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Shipment shipment) {
		super();
		this.shipment = shipment;
	}



	@Column(name = "EQ_INIT", columnDefinition = "char(4)", nullable = false)
	private String equipInit;
	
	@Column(name = "EQ_NR", columnDefinition = "decimal", nullable = false)
    private BigDecimal equipNbr;
	
	@Column(name = "EQ_TP", columnDefinition = "char(1)", nullable = false)
    private String equipTp;
	
	@Column(name = "EQ_ID", columnDefinition = "char(4)", nullable = false)
    private String equipId;

	public Long getSvcId() {
		return svcId;
	}

	public void setSvcId(Long svcId) {
		this.svcId = svcId;
	}

	public String getEquipInit() {
		return equipInit;
	}

	public void setEquipInit(String equipInit) {
		this.equipInit = equipInit;
	}

	public BigDecimal getEquipNbr() {
		return equipNbr;
	}

	public void setEquipNbr(BigDecimal equipNbr) {
		this.equipNbr = equipNbr;
	}

	public String getEquipTp() {
		return equipTp;
	}

	public void setEquipTp(String equipTp) {
		this.equipTp = equipTp;
	}

	public String getEquipId() {
		return equipId;
	}

	public void setEquipId(String equipId) {
		this.equipId = equipId;
	}

	public ShipmentActiveView(Long svcId, String uversion, Timestamp createDateTime, Shipment shipment,
			String equipInit, BigDecimal equipNbr, String equipTp, String equipId) {
		super();
		this.svcId = svcId;
		this.uversion = uversion;
		this.createDateTime = createDateTime;
		this.shipment = shipment;
		this.equipInit = equipInit;
		this.equipNbr = equipNbr;
		this.equipTp = equipTp;
		this.equipId = equipId;
	}

	public ShipmentActiveView() {
		super();
		// TODO Auto-generated constructor stub
	}



	
	
	
	
	
	
	

}
