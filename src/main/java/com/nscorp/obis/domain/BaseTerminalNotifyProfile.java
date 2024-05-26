package com.nscorp.obis.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseTerminalNotifyProfile extends NotifyProfile {
	@Column(name="TERM_ID", length = 15, columnDefinition = "Double", nullable=true)
	private Long terminalId;
	
	public Long getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}
	
}
