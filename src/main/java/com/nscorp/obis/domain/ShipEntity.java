package com.nscorp.obis.domain;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

@SuppressWarnings("serial")
@Entity
@Data
//@IdClass(ShipEntityPrimary.class)
@Table(name="SHIP_ENTITY")
public class ShipEntity extends AuditInfo implements Serializable {
//    @Id
//    @Column(name = "ENT_ID", columnDefinition = "Double(15)", nullable = false)
//    private Long entId;
    @Id
    @Column(name = "SVC_ID", columnDefinition = "Double(15)", nullable = false)
    private Long svcId;
    @Column(name = "SEG_TP", columnDefinition = "char(3)", nullable = false)
    private String segType;
    @Column(name = "ENT_CUST_NR", columnDefinition = "char(35)", nullable = false)
    private String entCustNr;
}