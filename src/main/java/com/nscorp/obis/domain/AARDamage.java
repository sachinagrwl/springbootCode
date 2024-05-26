package com.nscorp.obis.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AAR_DAMAGE")
public class AARDamage extends AuditInfo{
	
	@Id
    @Column(name = "JOB_CODE" , length = 4, columnDefinition = "SMALLINT", nullable = false)
    private Integer jobCode;
	
	@Column(name = "DESCRIPTION", columnDefinition = "char(35)", nullable = true)
    private String dscr;

    @Column(name = "CHASSIS_IND", columnDefinition = "char(1)", nullable = true)
	private String chassisInd;
    
    @Column(name = "FAMILY_CODE", columnDefinition = "SMALLINT(5)", nullable = true)
    private Integer familyCode;

    
    
	public Integer getJobCode() {
		return jobCode;
	}

	public void setJobCode(Integer jobCode) {
		this.jobCode = jobCode;
	}

	public String getDscr() {
		return dscr;
	}

	public void setDscr(String dscr) {
		this.dscr = dscr;
	}

	public String getChassisInd() {
		return chassisInd;
	}

	public void setChassisInd(String chassisInd) {
		this.chassisInd = chassisInd;
	}

	public Integer getFamilyCode() {
		return familyCode;
	}

	public void setFamilyCode(Integer familyCode) {
		this.familyCode = familyCode;
	}

	
	public AARDamage(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Integer jobCode, String dscr, String chassisInd,
			Integer familyCode) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.jobCode = jobCode;
		this.dscr = dscr;
		this.chassisInd = chassisInd;
		this.familyCode = familyCode;
	}

	public AARDamage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AARDamage(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}
   
}
