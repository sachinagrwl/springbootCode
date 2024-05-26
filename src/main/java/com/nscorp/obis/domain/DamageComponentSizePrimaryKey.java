package com.nscorp.obis.domain;

import java.io.Serializable;

import javax.persistence.Column;

@SuppressWarnings("serial")
public class DamageComponentSizePrimaryKey implements Serializable {

    @Column(name = "JOB_CODE", columnDefinition = "SMALLINT(5)", nullable = true)
	private Integer jobCode;

	@Column(name = "AAR_WHY_MADE_CD", columnDefinition = "SMALLINT(5)", nullable = true)
	private Integer aarWhyMadeCode;

    @Column(name = "COMP_SIZE_CD", columnDefinition = "Double(15)", nullable = true)
	private Long componentSizeCode;

	public Integer getJobCode() {
		return jobCode;
	}

	public void setJobCode(Integer jobCode) {
		this.jobCode = jobCode;
	}

	public Integer getAarWhyMadeCode() {
		return aarWhyMadeCode;
	}

	public void setAarWhyMadeCode(Integer aarWhyMadeCode) {
		this.aarWhyMadeCode = aarWhyMadeCode;
	}

	public Long getComponentSizeCode() {
		return componentSizeCode;
	}

	public void setComponentSizeCode(Long componentSizeCode) {
		this.componentSizeCode = componentSizeCode;
	}

	@Override
	public String toString() {
		return "DamageComponentSizePrimaryKey [jobCode=" + jobCode + ", aarWhyMadeCode=" + aarWhyMadeCode
				+ ", componentSizeCode=" + componentSizeCode + "]";
	}

	public DamageComponentSizePrimaryKey(Integer jobCode, Integer aarWhyMadeCode, Long componentSizeCode) {
		super();
		this.jobCode = jobCode;
		this.aarWhyMadeCode = aarWhyMadeCode;
		this.componentSizeCode = componentSizeCode;
	}

	public DamageComponentSizePrimaryKey() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aarWhyMadeCode == null) ? 0 : aarWhyMadeCode.hashCode());
		result = prime * result + ((componentSizeCode == null) ? 0 : componentSizeCode.hashCode());
		result = prime * result + ((jobCode == null) ? 0 : jobCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DamageComponentSizePrimaryKey other = (DamageComponentSizePrimaryKey) obj;
		if (aarWhyMadeCode == null) {
			if (other.aarWhyMadeCode != null)
				return false;
		} else if (!aarWhyMadeCode.equals(other.aarWhyMadeCode))
			return false;
		if (componentSizeCode == null) {
			if (other.componentSizeCode != null)
				return false;
		} else if (!componentSizeCode.equals(other.componentSizeCode))
			return false;
		if (jobCode == null) {
			if (other.jobCode != null)
				return false;
		} else if (!jobCode.equals(other.jobCode))
			return false;
		return true;
	}
	
    
}
