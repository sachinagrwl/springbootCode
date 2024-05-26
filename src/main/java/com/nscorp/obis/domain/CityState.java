package com.nscorp.obis.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Entity
@Table(name = "CITY_STATE")
@IdClass(CityStatePrimaryKey.class)
@NoArgsConstructor
public class CityState extends AuditInfo implements Serializable {
	@Id
	@Column(name = "STATE_ABBRV",columnDefinition = "char(2)",nullable = false)
	private String stateAbbreviation;
	@Id
	@Column(name = "CITY_NM",columnDefinition = "char(19)",nullable = false)
	private String city;
	public String getStateAbbreviation() {
		return stateAbbreviation;
	}
	public void setStateAbbreviation(String stateAbbreviation) {
		this.stateAbbreviation = stateAbbreviation;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@Override
	public String toString() {
		return "CityState [stateAbbreviation=" + stateAbbreviation + ", city=" + city + ", getUversion()="
				+ getUversion() + ", getCreateUserId()=" + getCreateUserId() + ", getCreateDateTime()="
				+ getCreateDateTime() + ", getUpdateUserId()=" + getUpdateUserId() + ", getUpdateDateTime()="
				+ getUpdateDateTime() + ", getUpdateExtensionSchema()=" + getUpdateExtensionSchema() + "]";
	}
	
}
