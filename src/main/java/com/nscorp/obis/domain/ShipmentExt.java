package com.nscorp.obis.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SHIPMENT_EXT")
public class ShipmentExt extends AuditInfo{

    @Id
    @Column(name = "SVC_ID",length = 15, columnDefinition = "double", nullable = false)
    private Long svcId;

    @Column(name = "SPEC_ENDOR_CD1", columnDefinition = "char(2)", nullable = true)
    private String specEndorCd1;

    @Column(name = "SPEC_ENDOR_CD2", columnDefinition = "char(2)", nullable = true)
    private String specEndorCd2;

    @Column(name = "SPEC_ENDOR_CD3", columnDefinition = "char(2)", nullable = true)
    private String specEndorCd3;

    @Column(name = "SPEC_ENDOR_CD4", columnDefinition = "char(2)", nullable = true)
    private String specEndorCd4;

    @Column(name = "SPEC_ENDOR_CD5", columnDefinition = "char(2)", nullable = true)
    private String specEndorCd5;

    @Column(name = "SPEC_ENDOR_CD6", columnDefinition = "char(2)", nullable = true)
    private String specEndorCd6;

    @Column(name = "SPEC_ENDOR_CD7", columnDefinition = "char(2)", nullable = true)
    private String specEndorCd7;

    @Column(name = "SPEC_ENDOR_CD8", columnDefinition = "char(2)", nullable = true)
    private String specEndorCd8;

    @Column(name = "SPEC_ENDOR_CD9", columnDefinition = "char(2)", nullable = true)
    private String specEndorCd9;

    @Column(name = "SPEC_ENDOR_CD10", columnDefinition = "char(2)", nullable = true)
    private String specEndorCd10;

    @Column(name = "SPEC_ENDOR_CD11", columnDefinition = "char(2)", nullable = true)
    private String specEndorCd11;

    public Long getSvcId() {
        return svcId;
    }

    public void setSvcId(Long svcId) {
        this.svcId = svcId;
    }

    public String getSpecEndorCd1() {
        return specEndorCd1;
    }

    public void setSpecEndorCd1(String specEndorCd1) {
        this.specEndorCd1 = specEndorCd1;
    }

    public String getSpecEndorCd2() {
        return specEndorCd2;
    }

    public void setSpecEndorCd2(String specEndorCd2) {
        this.specEndorCd2 = specEndorCd2;
    }

    public String getSpecEndorCd3() {
        return specEndorCd3;
    }

    public void setSpecEndorCd3(String specEndorCd3) {
        this.specEndorCd3 = specEndorCd3;
    }

    public String getSpecEndorCd4() {
        return specEndorCd4;
    }

    public void setSpecEndorCd4(String specEndorCd4) {
        this.specEndorCd4 = specEndorCd4;
    }

    public String getSpecEndorCd5() {
        return specEndorCd5;
    }

    public void setSpecEndorCd5(String specEndorCd5) {
        this.specEndorCd5 = specEndorCd5;
    }

    public String getSpecEndorCd6() {
        return specEndorCd6;
    }

    public void setSpecEndorCd6(String specEndorCd6) {
        this.specEndorCd6 = specEndorCd6;
    }

    public String getSpecEndorCd7() {
        return specEndorCd7;
    }

    public void setSpecEndorCd7(String specEndorCd7) {
        this.specEndorCd7 = specEndorCd7;
    }

    public String getSpecEndorCd8() {
        return specEndorCd8;
    }

    public void setSpecEndorCd8(String specEndorCd8) {
        this.specEndorCd8 = specEndorCd8;
    }

    public String getSpecEndorCd9() {
        return specEndorCd9;
    }

    public void setSpecEndorCd9(String specEndorCd9) {
        this.specEndorCd9 = specEndorCd9;
    }

    public String getSpecEndorCd10() {
        return specEndorCd10;
    }

    public void setSpecEndorCd10(String specEndorCd10) {
        this.specEndorCd10 = specEndorCd10;
    }

    public String getSpecEndorCd11() {
        return specEndorCd11;
    }

    public void setSpecEndorCd11(String specEndorCd11) {
        this.specEndorCd11 = specEndorCd11;
    }
}
