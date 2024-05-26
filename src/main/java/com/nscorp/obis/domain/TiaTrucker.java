package com.nscorp.obis.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TIA_TKR")
public class TiaTrucker extends AuditInfo{
	@Id
	@Column(name="TKR_CD", columnDefinition = "char(30)")
	private String truckerCode;

	public TiaTrucker() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    public TiaTrucker(String truckerCode) {
    	this.truckerCode = truckerCode;
    }
    
    public String getTruckerCode() {
		return truckerCode;
	}

	public void setTruckerCode(String truckerCode) {
		this.truckerCode = truckerCode;
	}
}