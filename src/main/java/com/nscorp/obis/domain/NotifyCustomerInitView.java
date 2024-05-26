package com.nscorp.obis.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Entity
@Table(name = "NTFY_CUST_INIT_V")
public class NotifyCustomerInitView {

    @Id
    @Column(name = "CUST_ID", columnDefinition = "double", nullable = false)
    private Long customerId;

    @Column(name = "EQ_INIT", columnDefinition = "char(4)", nullable = false)
    private String equipmentInit;

    @Column(name = "CUST_NR", columnDefinition = "char(10)", nullable = true)
    private String customerNo;

    @Column(name = "CUST_NM", columnDefinition = "char(35)", nullable = true)
    private String customerName;

    @UpdateTimestamp
    @Column(name = "UPD_DT_TM", nullable = true)
    private Timestamp updateDateTime;

}