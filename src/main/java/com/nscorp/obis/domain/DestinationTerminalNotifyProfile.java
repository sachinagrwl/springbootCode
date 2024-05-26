package com.nscorp.obis.domain;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="DEST_NTFY_PRF")
public class DestinationTerminalNotifyProfile extends BaseTerminalNotifyProfile{
	public DestinationTerminalNotifyProfile() {
		super();
	}
}
