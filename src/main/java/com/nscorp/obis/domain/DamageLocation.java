package com.nscorp.obis.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "DAMAGE_LOCATION")
@IdClass(DamageLocationCompositePrimaryKeys.class)
public class DamageLocation extends AuditInfo {
    
    @Id
	@Column(name = "LOC_CD", columnDefinition = "SMALLINT", nullable = false)
	private Integer locCd;

	@Id
	@Column(name = "CAT_CD", columnDefinition = "SMALLINT", nullable = false)
	private Integer catCd;

	@Column(name = "LOC_DSCR", columnDefinition = "char(20)", nullable = true)
	private String locationDscr;
	
	@Column(name = "PRT_ORDER", columnDefinition = "SMALLINT", nullable = true)
	private Integer prtOrder;

	public Integer getLocCd() {
		return locCd;
	}

	public void setLocCd(Integer locCd) {
		this.locCd = locCd;
	}

	public Integer getCatCd() {
		return catCd;
	}

	public void setCatCd(Integer catCd) {
		this.catCd = catCd;
	}

	public String getLocationDscr() {
		return locationDscr!=null?locationDscr.trim():locationDscr;
	}

	public void setLocationDscr(String locationDscr) {
		this.locationDscr = locationDscr.toUpperCase();
	}

	public Integer getPrtOrder() {
		return prtOrder;
	}

	public void setPrtOrder(Integer prtOrder) {
		this.prtOrder = prtOrder;
	}

	public DamageLocation(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Integer locCd, Integer catCd, String locationDscr,
			Integer prtOrder) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.locCd = locCd;
		this.catCd = catCd;
		this.locationDscr = locationDscr;
		this.prtOrder = prtOrder;
	}

	public DamageLocation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DamageLocation(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "DamageLocation [locCd=" + locCd + ", catCd=" + catCd + ", locationDscr=" + locationDscr + ", prtOrder="
				+ prtOrder + "]";
	}

	

}
