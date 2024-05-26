package com.nscorp.obis.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class DamageCompLocPrimaryKeys implements Serializable {
	 @Column(name = "COMP_LOC_CD", columnDefinition = "CHAR(3)", nullable = false)
	 private String compLocCode;

	@Column(name = "AREA_CD", columnDefinition = "CHAR(1)", nullable = false)
	private String areaCd;

	@Column(name = "JOB_CODE" , length = 4, columnDefinition = "SMALLINT", nullable = false)
	private Integer jobCode;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		DamageCompLocPrimaryKeys that = (DamageCompLocPrimaryKeys) o;
		return Objects.equals(compLocCode, that.compLocCode) && Objects.equals(areaCd, that.areaCd) && Objects.equals(jobCode, that.jobCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(compLocCode, areaCd, jobCode);
	}
}
