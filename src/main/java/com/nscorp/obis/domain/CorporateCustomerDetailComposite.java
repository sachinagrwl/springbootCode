package com.nscorp.obis.domain;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@SuppressWarnings("serial")
@Data
public class CorporateCustomerDetailComposite implements Serializable {

	@Column(name="CORP_CUST_ID",length = 15, columnDefinition = "Double(15)", nullable = false )
	private Long corpCustId;

	@Column(name="CORP_PRIMARY_6",length = 15, columnDefinition = "char(6)", nullable = false )
	private String corpCust6;

	public CorporateCustomerDetailComposite(Long corpCustId, String corpCust6) {
		super();
		this.corpCustId = corpCustId;
		this.corpCust6 = corpCust6;
	}

	public CorporateCustomerDetailComposite() {
		super();
		// TODO Auto-generated constructor stub
	}
}
