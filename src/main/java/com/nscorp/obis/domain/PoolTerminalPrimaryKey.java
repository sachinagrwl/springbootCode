package com.nscorp.obis.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class PoolTerminalPrimaryKey implements Serializable {

    @Id
    @Column(name="POOL_ID", length = 15, columnDefinition = "Double", nullable=false)
    private Long poolId;

    @Id
    @Column(name="TERM_ID", length = 15, columnDefinition = "Double", nullable=false)
    Long terminalId;

    public PoolTerminalPrimaryKey(Long poolId, Long terminalId) {
        this.poolId = poolId;
        this.terminalId = terminalId;
    }

    public PoolTerminalPrimaryKey() {
        super();
    }
}