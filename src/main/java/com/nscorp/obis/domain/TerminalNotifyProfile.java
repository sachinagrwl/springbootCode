package com.nscorp.obis.domain;
import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name="NS_NTFY_PRF")
public class TerminalNotifyProfile extends BaseTerminalNotifyProfile{
	public TerminalNotifyProfile() {
		super();
	}
}
