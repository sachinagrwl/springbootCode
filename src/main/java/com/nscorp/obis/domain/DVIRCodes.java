package com.nscorp.obis.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "DVIR_CODES")
public class DVIRCodes extends AuditInfo {

    @Id
    @Column(name = "DVIR_CD", columnDefinition = "CHAR(2)", nullable = false)
    private String dvirCd;

    @Column(name = "DVIR_DESC", columnDefinition = "CHAR(40)", nullable = true)
    private String dvirDesc;

    @Column(name = "DVIR_HH_DESC", columnDefinition = "CHAR(19)", nullable = true)
    private String dvirHHDesc;

    @Column(name = "DISPLAY_CD", columnDefinition = "CHAR(1)", nullable = true)
    private String displayCd;


    public String getDvirCd() {
        if(dvirCd != null) {
            return dvirCd.trim();
        }
        else {
        return dvirCd;
        }
    }

    public void setDvirCd(String dvirCd) {
        this.dvirCd = dvirCd;
    }

    public String getDvirDesc() {
        if(dvirDesc != null) {
            return dvirDesc.trim();
        }
        else {
        return dvirDesc;
        }
    }

    public void setDvirDesc(String dvirDesc) {
        this.dvirDesc = dvirDesc;
    }

    public String getDvirHHDesc() {
        if(dvirHHDesc != null) {
            return dvirHHDesc.trim();
        }
        else {
        return dvirHHDesc;
        }
    }

    public void setDvirHHDesc(String dvirHHDesc) {
        this.dvirHHDesc = dvirHHDesc;
    }

    public String getDisplayCd() {
        if(displayCd != null) {
            return displayCd.trim();
        }
        else {
        return displayCd;
        }
    }

    public void setDisplayCd(String displayCd) {
        this.displayCd = displayCd;
    }

    public DVIRCodes(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
                       Timestamp updateDateTime, String updateExtensionSchema, String dvirCd, String dvirDesc,
                       String dvirHHDesc, String displayCd) {
        super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
        this.dvirCd = dvirCd;
        this.dvirDesc = dvirDesc;
        this.dvirHHDesc = dvirHHDesc;
        this.displayCd = displayCd;
    }

    public DVIRCodes() {
        super();
        // TODO Auto-generated constructor stub
    }

    public DVIRCodes(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
                       Timestamp updateDateTime, String updateExtensionSchema) {
        super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
        // TODO Auto-generated constructor stub
    }
}
