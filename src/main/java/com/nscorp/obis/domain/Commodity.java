package com.nscorp.obis.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@SuppressWarnings("serial")
@Entity
@Table(name = "COMMODITY")
@IdClass(CommodityCompositePrimaryKeys.class)
public class Commodity extends AuditInfo {

    @Id
    @Column(name = "STCC_CD_5", columnDefinition = "Integer(5)", nullable = false)
    private Integer commodityCode5;

    @Id
    @Column(name = "STCC_CD_2", columnDefinition = "smallint(2)", nullable = false)
    private Integer commodityCode2;

    @Id
    @Column(name = "STCC_SUB_CD", columnDefinition = "smallint(2)", nullable = false)
    private Integer commoditySubCode;

    @Column(name = "STCC_LONG_NM", columnDefinition = "char(60)", nullable = true)
    private String commodityCodeLongName;

    @Column(name = "STCC_SHORT_NM", columnDefinition = "char(15)", nullable = true)
    private String commodityCodeShortName;

    @Column(name = "STCC_HAZ_IND", columnDefinition = "char(1)", nullable = true)
    private String hazardIndicator;

    @Column(name = "STCC_PRIME_IND", columnDefinition = "char(1)", nullable = true)
    private String primeIndicator;

    @Column(name = "EXPIRED_DT", columnDefinition = "timestamp", nullable = true)
    private Timestamp expiredDateTime;

    public Integer getCommodityCode5() {
        return commodityCode5;
    }

    public void setCommodityCode5(Integer commodityCode5) {
        this.commodityCode5 = commodityCode5;
    }

    public Integer getCommodityCode2() {
        return commodityCode2;
    }

    public void setCommodityCode2(Integer commodityCode2) {
        this.commodityCode2 = commodityCode2;
    }

    public Integer getCommoditySubCode() {
        return commoditySubCode;
    }

    public void setCommoditySubCode(Integer commoditySubCode) {
        this.commoditySubCode = commoditySubCode;
    }

    public String getCommodityCodeLongName() {
        if (commodityCodeLongName != null) {
            return commodityCodeLongName.trim();
        } else {
            return commodityCodeLongName;
        }
    }

    public void setCommodityCodeLongName(String commodityCodeLongName) {
        this.commodityCodeLongName = commodityCodeLongName;
    }

    public String getCommodityCodeShortName() {
        if (commodityCodeShortName != null) {
            return commodityCodeShortName.trim();
        } else {
            return commodityCodeShortName;
        }
    }

    public void setCommodityCodeShortName(String commodityCodeShortName) {
        this.commodityCodeShortName = commodityCodeShortName;
    }

    public String getHazardIndicator() {
        return hazardIndicator;
    }

    public void setHazardIndicator(String hazardIndicator) {
        this.hazardIndicator = hazardIndicator;
    }

    public String getPrimeIndicator() {
        if(primeIndicator != null) {
            return primeIndicator.trim().toUpperCase();
        }
        else {
            return primeIndicator;
        }
    }

    public void setPrimeIndicator(String primeIndicator) {
        if(primeIndicator != null) {
            primeIndicator = primeIndicator.toUpperCase();
        }

        this.primeIndicator = primeIndicator;
    }

    public Timestamp getExpiredDateTime() {
        return expiredDateTime;
    }

    public void setExpiredDateTime(Timestamp expiredDateTime) {
        this.expiredDateTime = expiredDateTime;
    }

    public Commodity() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Commodity(String uversion, String createUserId, Timestamp createDateTime, String updateUserId, Timestamp updateDateTime, String updateExtensionSchema, Integer commodityCode5, Integer commodityCode2, Integer commoditySubCode, String commodityCodeLongName, String commodityCodeShortName, String hazardIndicator, String primeIndicator, Timestamp expiredDateTime) {
        super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
        this.commodityCode5 = commodityCode5;
        this.commodityCode2 = commodityCode2;
        this.commoditySubCode = commoditySubCode;
        this.commodityCodeLongName = commodityCodeLongName;
        this.commodityCodeShortName = commodityCodeShortName;
        this.hazardIndicator = hazardIndicator;
        this.primeIndicator = primeIndicator;
        this.expiredDateTime = expiredDateTime;
    }

    @Override
    public String toString() {
        return "Commodity [commodityCode5=" + commodityCode5 + ", commodityCode2=" + commodityCode2 + ", commoditySubCode=" + commoditySubCode
                + ", commoditySubCode=" + commoditySubCode
                + ", commodityCodeLongName='" + commodityCodeLongName
                + ", commodityCodeShortName='" + commodityCodeShortName
                + ", hazardIndicator='" + hazardIndicator
                + ", primeIndicator='" + primeIndicator
                + ", expiredDateTime=" + expiredDateTime
                + ", getUversion()=" + getUversion() + ", getCreateUserId()=" + getCreateUserId()
                + ", getCreateDateTime()=" + getCreateDateTime() + ", getUpdateUserId()=" + getUpdateUserId()
                + ", getUpdateDateTime()=" + getUpdateDateTime() + ", getUpdateExtensionSchema()="
                + getUpdateExtensionSchema() + "]";
    }


}
