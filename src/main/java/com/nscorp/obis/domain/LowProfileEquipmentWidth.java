package com.nscorp.obis.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@IdClass(LowProfileEquipmentWidthPrimaryKey.class)
@Table(name = "LOWP_EQ_WDTH")
@EqualsAndHashCode(callSuper = false)
public class LowProfileEquipmentWidth extends AuditInfo {
	@Id
	@Column(name = "UMLER_ID", columnDefinition = "double", nullable = false)
	private Long umlerId;

	@Id
	@Column(name = "AAR_1ST_NR", columnDefinition = "char(1)", nullable = false)
	private String aar1stNr;

	@Column(name = "MIN_EQ_WDTH", columnDefinition = "smallint", nullable = false)
	private Integer minEqWidth;

	@Column(name = "MAX_EQ_WDTH", columnDefinition = "smallint", nullable = false)
	private Integer maxEqWidth;

}