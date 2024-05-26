package com.nscorp.obis.domain;

import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name="RE_NOTIFY_V1")
public class ReNotifyView {
    @Id
    @Column(name = "NTFY_QUEUE_ID", columnDefinition = "double", nullable = false)
    private Long ntfyQueueId;
    @Column(name = "TERM_ID", columnDefinition = "double", nullable = false)
    private double termId;
    @Column(name = "RENOT_CNT", columnDefinition = "integer(10)", nullable = false)
    private Integer renotifyCnt;
    @Column(name = "NTFY_CUST_NM", columnDefinition = "char(35)", nullable = false)
    private String notifyCustomerName;
    @Column(name = "EQ_INIT", columnDefinition = "char(4)", nullable = false)
    private String equipInit;
    @Column(name = "EQ_NR", length = 6, columnDefinition = "decimal", nullable = false)
    private Integer equipNbr;
    @Column(name = "EQ_TP", columnDefinition = "char(1)", nullable = false)
    private String equipTp;
    @Column(name = "EQ_ID", columnDefinition = "char(4)", nullable = false)
    private Integer equipId;
    @Column(name = "NTFY_STAT", columnDefinition = "char(4)", nullable = false)
    private String notifyStat;
    @Column(name = "LCL_DT_TM", columnDefinition = "TIMESTAMP", nullable = false)
    private String localDateTime;
    @Column(name = "PICKUP_NR", columnDefinition = "char(6)", nullable = false)
    private String pichUpNR;
    @Column(name = "LD_EMPTY_CD", columnDefinition = "char(1)", nullable = false)
    private String ldEmptyCode;
    @Column(name = "TRACK_ID", columnDefinition = "char(4)", nullable = false)
    private String trackId;
    @Column(name = "EVT_CD", columnDefinition = "char(4)", nullable = false)
    private String eventCode;
}