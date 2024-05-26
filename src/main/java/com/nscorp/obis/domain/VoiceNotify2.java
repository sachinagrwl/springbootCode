package com.nscorp.obis.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VOICE_NOTIFY_V2")
public class VoiceNotify2 {
	
	@Id
	@Column(name="SVC_ID",length = 15,  columnDefinition = "double", nullable = false)
    private Long svcId;
	
	@Column(name="CUST_BILL_TO_GS",  columnDefinition = "char(10)", nullable = true)
    private String custBill;
	
	@Column(name="CUST_SHIP_GS",  columnDefinition = "char(10)", nullable = true)
    private String custShip;
	
	@Column(name="STN_NM",  columnDefinition = "char(19)", nullable = true)
    private String stationName;

	public Long getSvcId() {
		return svcId;
	}

	public void setSvcId(Long svcId) {
		this.svcId = svcId;
	}

	public String getCustBill() {
		if(custBill != null) {
			return custBill.trim();
		}
		else {
			return custBill;
		}
	}

	public void setCustBill(String custBill) {
		this.custBill = custBill;
	}

	public String getCustShip() {
		if(custShip != null) {
			return custShip.trim();
		}
		else {
			return custShip;
		}
	}

	public void setCustShip(String custShip) {
		this.custShip = custShip;
	}

	public String getStationName() {
		if(stationName != null) {
			return stationName.trim();
		}
		else {
			return stationName;
		}
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public VoiceNotify2(Long svcId, String custBill, String custShip, String stationName) {
		super();
		this.svcId = svcId;
		this.custBill = custBill;
		this.custShip = custShip;
		this.stationName = stationName;
	}

	public VoiceNotify2() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
