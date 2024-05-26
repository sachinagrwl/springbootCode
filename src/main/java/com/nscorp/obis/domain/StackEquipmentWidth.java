package com.nscorp.obis.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@IdClass(StackEquipmentWidthPrimaryKey.class)
@Table(name = "STACK_EQ_WDTH")
@EqualsAndHashCode(callSuper = false)
public class StackEquipmentWidth extends AuditInfo {

	@Id
	@Column(name = "UMLER_ID", columnDefinition = "double", nullable = false)
	private Long umlerId;

	@Id
	@Column(name = "MIN_EQ_LGTH", columnDefinition = "smallint", nullable = false)
	private Integer minimumEquipmentLength;

	@Id
	@Column(name = "MAX_EQ_LGTH", columnDefinition = "smallint", nullable = false)
	private Integer maximumEquipmentLength;

	@Column(name = "MIN_EQ_WDTH", columnDefinition = "smallint", nullable = false)
	private Integer minimumEquipmentWidth;

	@Column(name = "MAX_EQ_WDTH", columnDefinition = "smallint", nullable = false)
	private Integer maximumEquipmentWidth;

}