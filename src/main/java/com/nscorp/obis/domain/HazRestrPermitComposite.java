package com.nscorp.obis.domain;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class HazRestrPermitComposite implements Serializable{
	
	@Column(name = "UN_CD", columnDefinition = "char(6)", nullable = false)
    private String unCd;
	
	@Column(name="CUST_ID", columnDefinition = "Double(15)", nullable = false)
    private Long customerId;

	public HazRestrPermitComposite(String unCd, Long customerId) {
		super();
		this.unCd = unCd;
		this.customerId = customerId;
	}

	public HazRestrPermitComposite() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
