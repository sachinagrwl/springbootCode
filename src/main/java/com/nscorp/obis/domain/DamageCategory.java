package com.nscorp.obis.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DAMAGE_CATEGORY")
public class DamageCategory extends AuditInfo {

    @Id
    @Column(name = "CAT_CD", columnDefinition = "SMALLINT(2)", nullable = false)
    private Integer catCd;

    @Column(name = "CAT_DSCR", columnDefinition = "CHAR(20)")
    private String catDscr;

    @Column(name = "PRT_ORDER", columnDefinition = "SMALLINT(2)")
    private Integer prtOrder;

    
    
	public Integer getCatCd() {
		return catCd;
	}

	public void setCatCd(Integer catCd) {
		this.catCd = catCd;
	}

	public String getCatDscr() {
		if(catDscr!=null) {
			return catDscr.trim();
		}else {
			return catDscr;
		}
	}

	public void setCatDscr(String catDscr) {
		this.catDscr = catDscr;
	}

	public Integer getPrtOrder() {
		return prtOrder;
	}

	public void setPrtOrder(Integer prtOrder) {
		this.prtOrder = prtOrder;
	}
	
	

	public DamageCategory(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Integer catCd, String catDscr, Integer prtOrder) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.catCd = catCd;
		this.catDscr = catDscr;
		this.prtOrder = prtOrder;
	}

	public DamageCategory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DamageCategory(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}
	
	
    
    
}