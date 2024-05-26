package com.nscorp.obis.domain;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.Data;


@Data
@SuppressWarnings("serial")
public class CityStatePrimaryKey implements Serializable {
	
	@Column(name = "STATE_ABBRV",columnDefinition = "char(2)",nullable = false)
	private String stateAbbreviation;
	
	@Column(name = "CITY_NM",columnDefinition = "char(19)",nullable = false)
	private String city;

}
