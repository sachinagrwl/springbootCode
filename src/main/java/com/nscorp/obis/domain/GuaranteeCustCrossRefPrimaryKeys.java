package com.nscorp.obis.domain;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@SuppressWarnings("serial")
@Data
public class GuaranteeCustCrossRefPrimaryKeys  implements Serializable {

    @Column(name = "GUAR_CUST_XRF_ID", columnDefinition = "double", nullable = false)
    private Long guaranteeCustXrefId;

    @Column(name = "CORP_CUST_ID", columnDefinition = "double", nullable = false)
    private Long corpCustId;

    public GuaranteeCustCrossRefPrimaryKeys(Long guaranteeCustXrefId, Long corpCustId) {
        super();
        this.guaranteeCustXrefId = guaranteeCustXrefId;
        this.corpCustId = corpCustId;
    }

    public GuaranteeCustCrossRefPrimaryKeys() {
        super();
        // TODO Auto-generated constructor stub
    }

}