package com.nscorp.obis.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "EQ_TRLR")
@IdClass(EquipmentTrailorPrimaryKey.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class EquipmentTrailor extends AuditInfo{

    @Id
    @Column(name = "TRLR_INIT", columnDefinition = "char(4)", nullable = false)
    private String trailorInit;

    @Id
    @Column(name = "TRLR_NR", columnDefinition = "decimal", nullable = false)
    private BigDecimal trailorNumber;

    @Id
    @Column(name = "TRLR_EQ_TP", columnDefinition = "char(1)", nullable = false)
    private String trailorEquipType;

    @Id
    @Column(name = "TRLR_ID", columnDefinition = "char(4)", nullable = false)
    private String trailorId;

}
