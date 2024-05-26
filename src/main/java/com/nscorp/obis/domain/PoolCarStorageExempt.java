package com.nscorp.obis.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
@Data
@Entity
@Table(name = "ON_CAR_XMPT")
@EqualsAndHashCode(callSuper = false)
public class PoolCarStorageExempt extends AuditInfo {
    @Id
    @Column(name="POOL_ID", length = 15, columnDefinition = "Double", nullable=false)
    private Long poolId;
    
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "POOL_ID", nullable = false, insertable = false, updatable = false)
    private Pool pool;
    
}