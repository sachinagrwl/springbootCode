package com.nscorp.obis.domain;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "DAMAGE_AREA")
public class DamageArea extends AuditInfo {

    @Id
    @Column(name = "AREA_CD", columnDefinition = "CHAR(1)", nullable = false)
    private String areaCd;

    @Column(name = "AREA_DSCR", columnDefinition = "CHAR(16)", nullable = true)
    private String areaDscr;

    @Column(name = "DISPLAY_CD", columnDefinition = "CHAR(1)", nullable = true)
    private String displayCd;
 

    public String getAreaCd() {
        return areaCd;
    } 

    public void setAreaCd(String areaCd) {
        this.areaCd = areaCd;
    }

    public String getAreaDscr() {
    	if(areaDscr!=null) {
    		return areaDscr.trim();
    	}
    	else {
        return areaDscr;
    	}
    }

    public void setAreaDscr(String areaDscr) {
        this.areaDscr = areaDscr;
    }

    public String getDisplayCd() {
        return displayCd;
    }

    public void setDisplayCd(String displayCd) {
        this.displayCd = displayCd;
    }

	public DamageArea(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, String areaCd, String areaDscr, String displayCd) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.areaCd = areaCd;
		this.areaDscr = areaDscr;
		this.displayCd = displayCd;
	}

	public DamageArea() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DamageArea(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}
    
    

  
}
