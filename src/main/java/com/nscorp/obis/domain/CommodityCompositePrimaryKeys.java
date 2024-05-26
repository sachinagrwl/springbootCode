package com.nscorp.obis.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
public class CommodityCompositePrimaryKeys implements Serializable {

	@Column(name = "STCC_CD_5", columnDefinition = "Integer(5)", nullable = false)
	private Integer commodityCode5;

	@Column(name = "STCC_CD_2", columnDefinition = "smallint(2)", nullable = false)
	private Integer commodityCode2;

	@Column(name = "STCC_SUB_CD", columnDefinition = "smallint(2)", nullable = false)
	private Integer commoditySubCode;

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

	@Override
	public String toString() {
		return "CommodityCompositePrimaryKeys [commodityCode5=" + commodityCode5 + ", commodityCode2=" + commodityCode2 + ", commoditySubCode=" + commoditySubCode + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CommodityCompositePrimaryKeys that = (CommodityCompositePrimaryKeys) o;
		return Objects.equals(commodityCode5, that.commodityCode5) && Objects.equals(commodityCode2, that.commodityCode2) && Objects.equals(commoditySubCode, that.commoditySubCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(commodityCode5, commodityCode2, commoditySubCode);
	}
}
