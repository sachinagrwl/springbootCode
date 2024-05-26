package com.nscorp.obis.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;

@SuppressWarnings("serial")
public class DamageComponentReasonPrimaryKey implements Serializable {

	@Column(name = "JOB_CODE", columnDefinition = "SMALLINT(5)", nullable = false)
	private Integer jobCode;

	@Column(name = "AAR_WHY_MADE_CD", columnDefinition = "SMALLINT(5)", nullable = false)
	private Integer aarWhyMadeCode;

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

	@Override
	public int hashCode() {
		return Objects.hash(jobCode,aarWhyMadeCode);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DamageComponentReasonPrimaryKey other = (DamageComponentReasonPrimaryKey) obj;
		return Objects.equals(obj, other);
	}

	@Override
	public String toString() {
		return "DamageComponentReasonPrimaryKey [jobCode=" + jobCode + ", aarWhyMadeCode=" + aarWhyMadeCode + "]";
	}

}
