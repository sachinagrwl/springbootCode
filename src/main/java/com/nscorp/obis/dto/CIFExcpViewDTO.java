package com.nscorp.obis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class CIFExcpViewDTO {
    private String primarySix;
    private String customerName;

    public String getPrimarySix() {
        return primarySix;
    }

    public void setPrimarySix(String primarySix) {
        if(primarySix!=null)
            this.primarySix = primarySix.trim();
        this.primarySix = primarySix;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        if(customerName!=null)
            this.customerName = customerName.trim();
        this.customerName = customerName;
    }
}
