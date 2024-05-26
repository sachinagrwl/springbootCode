package com.nscorp.obis.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(PoolTerminalPrimaryKey.class)
@Table(name="POOL_TERM")
public class PoolTerminal extends UnversionedAuditInfo {

    @Id
    @Column(name="POOL_ID", length = 15, columnDefinition = "Double", nullable=false)
    private Long poolId;

    @Id
    @Column(name="TERM_ID", length = 15, columnDefinition = "Double", nullable=false)
    Long terminalId;

	public Long getPoolId() {
		return poolId;
	}

	public void setPoolId(Long poolId) {
		this.poolId = poolId;
	}

	public Long getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}

	public PoolTerminal(String createUserId, Timestamp createDateTime, String updateUserId, Timestamp updateDateTime,
			String updateExtensionSchema, Long poolId, Long terminalId) {
		super(createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.poolId = poolId;
		this.terminalId = terminalId;
	}

	public PoolTerminal(String createUserId, Timestamp createDateTime, String updateUserId, Timestamp updateDateTime,
			String updateExtensionSchema) {
		super(createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}
    
	public PoolTerminal() {
		super();
	}

}