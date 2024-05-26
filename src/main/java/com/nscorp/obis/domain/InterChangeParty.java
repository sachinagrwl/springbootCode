package com.nscorp.obis.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ICHG_PARTY")
public class InterChangeParty extends AuditInfo{

	@Id
	@Column(name = "ICHG_CD", columnDefinition = "char(4)", nullable = false)
    private String ichgCode;
	
	@Column(name = "RD_OTHER_IND", columnDefinition = "char(1)", nullable = false)
    private String roadOtherInd;
	
	@Column(name = "ICHG_CD_DESC", columnDefinition = "char(30)", nullable = false)
    private String ichgCdDesc;
	
	public String getIchgCode() {
		if(ichgCode != null) {
			return ichgCode.trim();
		}
		else {
			return ichgCode;
		}
	}

	public void setIchgCode(String ichgCode) {
		this.ichgCode = ichgCode;
	}

	public String getRoadOtherInd() {
		return roadOtherInd;
	}

	public void setRoadOtherInd(String roadOtherInd) {
		this.roadOtherInd = roadOtherInd;
	}

	public String getIchgCdDesc() {
		if(ichgCdDesc != null) {
			return ichgCdDesc.trim();
		}
		else {
			return ichgCdDesc;
		}
	}

	public void setIchgCdDesc(String ichgCdDesc) {
		this.ichgCdDesc = ichgCdDesc;
	}

	public InterChangeParty(String ichgCode, String roadOtherInd, String ichgCdDesc, Road road) {
		super();
		this.ichgCode = ichgCode;
		this.roadOtherInd = roadOtherInd;
		this.ichgCdDesc = ichgCdDesc;
	}

	public InterChangeParty() {
		super();
	}
	
	
}