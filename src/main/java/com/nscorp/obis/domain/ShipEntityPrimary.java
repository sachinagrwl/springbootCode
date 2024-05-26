package com.nscorp.obis.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@SuppressWarnings("serial")
public class ShipEntityPrimary implements Serializable {
    @Id
    @Column(name = "ENT_ID", columnDefinition = "Double(15)", nullable = false)
    private Long entId;
    @Id
    @Column(name = "SVC_ID", columnDefinition = "Double(15)", nullable = false)
    private Long svcId;
}
