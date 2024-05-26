package com.nscorp.obis.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name="SHIP_VESSEL")
public class ShipVessel extends AuditInfo {

    @Id
    @Column(name = "SVC_ID",length = 15, columnDefinition = "double", nullable = false)
    private Long svcId;

    @Column(name = "VESSEL_DIR_CD", columnDefinition = "char(1)", nullable = false)
    private String vesselDirCd;

    @Column(name = "VESSEL_SAIL_DT", columnDefinition = "TimeStamp(10)", nullable = true)
    private Timestamp vesselSailDt;

    @Column(name = "SB_VALID_IND", columnDefinition = "char(1)", nullable = false)
    private String sbValidInd;

    public Long getSvcId() {
        return svcId;
    }

    public String getSbValidInd() {
        return sbValidInd;
    }

    public void setSbValidInd(String sbValidInd) {
        this.sbValidInd = sbValidInd;
    }

    public String getVesselDirCd() {
        return vesselDirCd;
    }

    public Timestamp getVesselSailDt() {
        return vesselSailDt;
    }

    public void setSvcId(Long svcId) {
        this.svcId = svcId;
    }

    public void setVesselDirCd(String vesselDirCd) {
        this.vesselDirCd = vesselDirCd;
    }

    public void setVesselSailDt(Timestamp vesselSailDt) {
        this.vesselSailDt = vesselSailDt;
    }
}
