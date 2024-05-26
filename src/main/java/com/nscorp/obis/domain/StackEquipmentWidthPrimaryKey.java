package com.nscorp.obis.domain;

import java.io.Serializable;

import javax.persistence.Column;

@SuppressWarnings("serial")
public class StackEquipmentWidthPrimaryKey implements Serializable {

	@Column(name = "UMLER_ID", columnDefinition = "double", nullable = false)
	private Long umlerId;

	@Column(name = "MIN_EQ_LGTH", columnDefinition = "smallint", nullable = false)
	private Integer minimumEquipmentLength;

	@Column(name = "MAX_EQ_LGTH", columnDefinition = "smallint", nullable = false)
	private Integer maximumEquipmentLength;

}