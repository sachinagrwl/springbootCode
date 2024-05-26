package com.nscorp.obis.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "EQ_CUST_RANGE")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class EquipmentCustomerRange extends AuditInfo{

    @Id
    @Column(name = "EQ_CUST_RANGE_ID", columnDefinition = "double", nullable = false)
    private Long equipmentCustomerRangeId;

    @Column(name = "EQ_INIT", columnDefinition = "char(4)", nullable = false)
    private String equipmentInit;

    @Column(name = "EQ_LOW_NR", columnDefinition = "decimal(19)", nullable = false)
    private BigDecimal equipmentLowNumber;

    @Column(name = "EQ_HIGH_NR", columnDefinition = "decimal(19)", nullable = false)
    private BigDecimal equipmentHighNumber;

    @Column(name = "EQ_TP", columnDefinition = "char(1)", nullable = false)
    private String equipmentType;
}
