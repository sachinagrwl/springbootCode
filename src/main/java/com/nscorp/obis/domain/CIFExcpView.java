package com.nscorp.obis.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;

@Entity
@ToString
@Table(name = "CIF_EXCP_V")
public class CIFExcpView {
    @Id
    @Column(name = "CUST_PRIM_SIX", columnDefinition = "char(6)", nullable = true)
    private String primarySix;

    @Column(name = "CUST_NM", columnDefinition = "char(50)", nullable = true)
    private String customerName;

    public String getPrimarySix() {
        if(primarySix!=null)
            return primarySix.trim();
        else
            return primarySix;
    }

    public void setPrimarySix(String primarySix) {
        if(primarySix!=null)
            this.primarySix = primarySix.trim();
        this.primarySix = primarySix;
    }

    public String getCustomerName() {
        if(customerName!=null)
            return customerName.trim();
        else
            return customerName;
    }

    public void setCustomerName(String customerName) {
        if(customerName!=null)
            this.customerName = customerName.trim();
        this.customerName = customerName;
    }
}
