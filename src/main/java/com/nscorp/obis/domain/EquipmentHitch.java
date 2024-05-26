package com.nscorp.obis.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "EQ_HITCH")
@IdClass(EquipmentHitchPrimaryKey.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class EquipmentHitch extends AuditInfo {

	@Id
	@Column(name = "CAR_INIT", columnDefinition = "char(4)", nullable = false)
	private String carInit;

	@Id
	@Column(name = "CAR_NR", columnDefinition = "decimal", nullable = false)
	private BigDecimal carNbr;

	@Id
	@Column(name = "CAR_TP", columnDefinition = "char(1)", nullable = false)
	private String carEquipType;

	@Id
	@Column(name = "HCH_LOC", columnDefinition = "char(2)", nullable = false)
	private String hitchLocation;

	@Column(name = "HCH_POS", columnDefinition = "char(1)", nullable = true)
	private String hitchPosition;

	@Column(name = "BAD_ORD_IND", columnDefinition = "char(1)", nullable = true)
	private String BadOrderInd;

	@Column(name = "DMGE_IND", columnDefinition = "char(1)", nullable = true)
	private String DamageInd;

	@Column(name = "EVT_LOG_ID", columnDefinition = "double", nullable = true)
	private Long evtLogId;

}