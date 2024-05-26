package com.nscorp.obis.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "DAMAGE_COMP_LOC")
@IdClass(DamageCompLocPrimaryKeys.class)
public class DamageCompLoc extends AuditInfo {
	 @Id
	 @Column(name = "COMP_LOC_CD", columnDefinition = "CHAR(3)", nullable = false)
	 private String compLocCode;
	 @Id
	 @Column(name = "JOB_CODE" , length = 4, columnDefinition = "SMALLINT", nullable = false)
	 private Integer jobCode;
	 @Id
	 @Column(name = "AREA_CD", columnDefinition = "CHAR(1)", nullable = false)
	 private String areaCd;
	 @ManyToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "JOB_CODE", nullable = true, insertable=false, updatable=false)
	 private DamageComponent damageComponent;
	 
	 @ManyToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "AREA_CD", nullable = true, insertable=false, updatable=false)
	 private DamageArea damageArea;
	 
     @Column(name = "LOC_DISPLAY_CD", columnDefinition = "CHAR(1)", nullable = false)
     private String locDisplayCode;
	 
     @Column(name = "TIRE_IO_CD", columnDefinition = "CHAR(1)", nullable = false)
     private String tireIoCode;
	 
     @Column(name = "DISPLAY_CD", columnDefinition = "CHAR(1)", nullable = false)
     private String displayCode;

	public String getCompLocCode() {
		return compLocCode;
	}

	public void setCompLocCode(String compLocCode) {
		this.compLocCode = compLocCode;
	}

	public DamageComponent getDamageComponent() {
		return damageComponent;
	}

	public void setDamageComponent(DamageComponent damageComponent) {
		this.damageComponent = damageComponent;
	}

	public DamageArea getDamageArea() {
		return damageArea;
	}

	public void setDamageArea(DamageArea damageArea) {
		this.damageArea = damageArea;
	}

	public String getLocDisplayCode() {
		if(this.locDisplayCode!=null){
			this.locDisplayCode = this.locDisplayCode.trim();
		}
		return locDisplayCode;
	}

	public void setLocDisplayCode(String locDisplayCode) {
		this.locDisplayCode = locDisplayCode;
	}

	public String getTireIoCode() {
		if(this.tireIoCode!=null){
			this.tireIoCode = this.tireIoCode.trim();
		}
		return tireIoCode;
	}

	public void setTireIoCode(String tireIoCode) {
		this.tireIoCode = tireIoCode;
	}

	public String getDisplayCode() {
		if(this.displayCode!=null){
			this.displayCode = this.displayCode.trim();
		}
		return displayCode;
	}

	public void setDisplayCode(String displayCode) {
		this.displayCode = displayCode;
	}

	public String getAreaCd() {
		return areaCd;
	}

	public Integer getJobCode() {
		return jobCode;
	}

	public void setJobCode(Integer jobCode) {
		this.jobCode = jobCode;
		if(this.damageComponent==null) {
			this.damageComponent = new DamageComponent();
		}
		this.damageComponent.setJobCode(jobCode);
	}
	public void setCompDscr(String compDscr) {
		if(this.damageComponent==null) {
			this.damageComponent = new DamageComponent();
		}
		this.damageComponent.setCompDscr(compDscr);
	}
	public void setAreaCd(String areaCd) {
		this.areaCd = areaCd;
		if(this.damageArea==null) {
			this.damageArea = new DamageArea();
		}
		this.damageArea.setAreaCd(areaCd);
	}
	public void setAreaDscr(String areaDscr) {
		if(this.damageArea==null) {
			this.damageArea = new DamageArea();
		}
		this.damageArea.setAreaDscr(areaDscr);
	}

	
	public DamageCompLoc(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, String compLocCode, DamageComponent damageComponent,
			DamageArea damageArea, String locDisplayCode, String tireIoCode, String displayCode) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.compLocCode = compLocCode;
		this.damageComponent = damageComponent;
		this.damageArea = damageArea;
		this.locDisplayCode = locDisplayCode;
		this.tireIoCode = tireIoCode;
		this.displayCode = displayCode;
	}


	public DamageCompLoc() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DamageCompLoc(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "DamageCompLoc [compLocCode=" + compLocCode + ", damageComponent=" + damageComponent + ", damageArea="
				+ damageArea + ", locDisplayCode=" + locDisplayCode + ", tireIoCode=" + tireIoCode + ", displayCode="
				+ displayCode + "]";
	}	   
}