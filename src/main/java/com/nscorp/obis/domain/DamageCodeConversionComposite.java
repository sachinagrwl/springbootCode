package com.nscorp.obis.domain;

import java.io.Serializable;

import javax.persistence.Column;

@SuppressWarnings("serial")
public class DamageCodeConversionComposite implements Serializable{
	
    @Column(name = "CAT_CD", columnDefinition = "SMALLINT(5)", nullable = false)
    private Integer catCd;

    @Column(name = "REASON_CD", columnDefinition = "CHAR(1)", nullable = false)
    private String reasonCd;

	public Integer getCatCd() {
		return catCd;
	}

	public void setCatCd(Integer catCd) {
		this.catCd = catCd;
	}

	public String getReasonCd() {
		return reasonCd;
	}

	public void setReasonCd(String reasonCd) {
		this.reasonCd = reasonCd;
	}

	public DamageCodeConversionComposite(Integer catCd, String reasonCd) {
		super();
		this.catCd = catCd;
		this.reasonCd = reasonCd;
	}

	public DamageCodeConversionComposite() {
		super();
		// TODO Auto-generated constructor stub
	}

}
