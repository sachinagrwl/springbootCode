package com.nscorp.obis.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TRUCKER")
public class Trucker extends AuditInfo{
	@Id
	@Column(name="TKR_CD", columnDefinition = "char(30)")
	private String truckerCode;
	@Column(name="TKR_NM", columnDefinition = "char(30)")
    private String truckerName;
    
    public Trucker() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    public Trucker(String truckerCode, String truckerName) {
    	this.truckerCode = truckerCode;
    	this.truckerName = truckerName;
    }
    
	public String getTruckerCode() {
		return truckerCode;
	}

	public void setTruckerCode(String truckerCode) {
		this.truckerCode = truckerCode;
	}

	public String getTruckerName() {
		return truckerName;
	}
	
	public void setTruckerName(String truckerName) {
		this.truckerName = truckerName;
	}
    
}