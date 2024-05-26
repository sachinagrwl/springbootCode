package com.nscorp.obis.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "PORTS")
public class Ports extends AuditInfo {

    @Id
    @Column(name="PORT_ID", length = 15, columnDefinition = "Double", nullable=false)
    private Long portId;

    @Column(name="PORT_LOC_CD", columnDefinition="char(3)", nullable=false)
    private String portCode;

    @Column(name="PORT_NM", columnDefinition="char(19)", nullable=false)
    private String portName;

    @Column(name="PORT_CTY", columnDefinition="char(19)", nullable=false)
    private String portCity;

    @Column(name="PORT_CTY_GS", columnDefinition="char(10)", nullable=true)
    private String portCityGoodSpell;

    @Column(name="PORT_ST_PV", columnDefinition="char(2)", nullable=true)
    private String portStateOrProvince;

    @Column(name="PORT_CNTRY", columnDefinition="char(3)", nullable=true)
    private String portCountry;

    @Column(name="EXPIRED_DT", columnDefinition="timestamp", nullable=true)
    private Timestamp expiredDate;

    public Long getPortId() {
        return portId;
    }

    public void setPortId(Long portId) {
        this.portId = portId;
    }

    public String getPortCode() {
        if(portCode != null) {
            return portCode.trim();
        }
        else {
            return portCode;
        }
    }

    public void setPortCode(String portCode) {
    	if(portCode != null)
			this.portCode = portCode.toUpperCase();
		else 
			this.portCode = portCode;
	}

    public String getPortName() {
        if(portName != null) {
            return portName.trim();
        }
        else {
            return portName;
        }
    }

    public void setPortName(String portName) {
    	if(portName != null)
			this.portName = portName.toUpperCase();
		else 
			this.portName = portName;
	}

    public String getPortCity() {
        if(portCity != null) {
            return portCity.trim();
        }
        else {
            return portCity;
        }
    }

    public void setPortCity(String portCity) {
    	if(portCity != null)
			this.portCity = portCity.toUpperCase();
		else 
			this.portCity = portCity;
	}

    public String getPortCityGoodSpell() {
        if(portCityGoodSpell != null) {
            return portCityGoodSpell.trim();
        }
        else {
            return portCityGoodSpell;
        }
    }

    public void setPortCityGoodSpell(String portCityGoodSpell) {
    	if(portCityGoodSpell != null)
			this.portCityGoodSpell = portCityGoodSpell.toUpperCase();
		else 
			this.portCityGoodSpell = portCityGoodSpell;
	}

    public String getPortStateOrProvince() {
        if(portStateOrProvince != null) {
            return portStateOrProvince.trim();
        }
        else {
            return portStateOrProvince;
        }
    }

    public void setPortStateOrProvince(String portStateOrProvince) {
    	if(portStateOrProvince != null)
			this.portStateOrProvince = portStateOrProvince.toUpperCase();
		else 
			this.portStateOrProvince = portStateOrProvince;
	}

    public String getPortCountry() {
        if(portCountry != null) {
            return portCountry.trim();
        }
        else {
            return portCountry;
        }
    }

    public void setPortCountry(String portCountry) {
    	if(portCountry != null)
			this.portCountry = portCountry.toUpperCase();
		else 
			this.portCountry = portCountry;
	}

    public Timestamp getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Timestamp expiredDate) {
        this.expiredDate = expiredDate;
    }
}
