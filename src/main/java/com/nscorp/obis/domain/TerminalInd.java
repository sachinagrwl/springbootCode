package com.nscorp.obis.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="TERMINAL_IND")
public class TerminalInd {
	@Id
	@Column(name="TERM_ID", columnDefinition = "Double", nullable=false)
    private Long terminalId;
	
	@Column(name="PRIVATE_IND", columnDefinition = "Char(1)", nullable=false)
    private String privateInd;
	
	@Column(name="AGS_IND", columnDefinition = "Char(1)", nullable=false)
    private String agsIndicator;

	@Column(name="LAST_MOV_NOT_NS_OK", columnDefinition = "Char(1)", nullable=false)
    private String lastMovNSNotOK;
	
	

	public TerminalInd( Long terminalId, String privateInd,String agsIndicator, String lastMovNSNotOK) {
		super();
		this.terminalId = terminalId;
		this.privateInd = privateInd;
		this.agsIndicator = agsIndicator;
		this.lastMovNSNotOK = lastMovNSNotOK;
	}
	
	

	public TerminalInd() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Long getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}

	public String getPrivateInd() {
		return privateInd;
	}

	public void setPrivateInd(String privateInd) {
		this.privateInd = privateInd;
	}

	public String getAgsIndicator() {
		return agsIndicator;
	}

	public void setAgsIndicator(String agsIndicator) {
		this.agsIndicator = agsIndicator;
	}

	public String getLastMovNSNotOK() {
		return lastMovNSNotOK;
	}

	public void setLastMovNSNotOK(String lastMovNSNotOK) {
		this.lastMovNSNotOK = lastMovNSNotOK;
	}
    
}