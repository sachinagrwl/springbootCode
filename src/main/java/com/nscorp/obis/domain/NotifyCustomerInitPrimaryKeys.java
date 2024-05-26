package com.nscorp.obis.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class NotifyCustomerInitPrimaryKeys implements Serializable {

    @Column(name = "EQ_INIT", columnDefinition = "char(4)", nullable = false)
    private String eqInit;

    @Column(name = "CUST_ID", columnDefinition = "double", nullable = false)
    private Long custId;

}