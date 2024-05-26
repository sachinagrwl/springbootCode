package com.nscorp.obis.domain;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "HOLD_ORDERS")
@Data
@EqualsAndHashCode(callSuper = false)
public class HoldOrders extends AuditInfo{
    @Id
    @Column(name = "HOLD_ORDER_ID", columnDefinition = "Double", nullable = false)
    private Long holdOrderId;

    @Column(name = "EQ_INIT", columnDefinition = "char(4)", nullable = false)
    private String equipmentInit;

    @Column(name = "EQ_NR", columnDefinition = "decimal", nullable = true)
    private BigDecimal equipmentNbr;

    @Column(name = "EQ_TP", columnDefinition = "char(1)", nullable = true)
    private String equipmentType;

    @Column(name = "SVC_ID", columnDefinition = "double", nullable = true)
    private Long svcId;

    @Column(name = "HOLD_REASON_CD", columnDefinition = "char(4)", nullable = false)
    private String holdReasonCd;

    @Column(name = "HOLD_UNTIL_DT", columnDefinition = "date", nullable = false)
    private Date holdUnitDt;

    @Column(name = "WORK_QUEUE_LOG_ID", columnDefinition = "double", nullable = false)
    private Long workQueueLogId;

    @Column(name = "ASSOC_TERM_ID", columnDefinition = "double", nullable = false)
    private Long assocTermId;

}