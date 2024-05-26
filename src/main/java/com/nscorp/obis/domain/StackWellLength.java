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
@IdClass(StackWellLengthPrimaryKey.class)
@Table(name = "STACK_WELL_LGTH")
@EqualsAndHashCode(callSuper = false)
public class StackWellLength extends AuditInfo {
	@Id
	@Column(name = "UMLER_ID", columnDefinition = "double", nullable = false)
	private Long umlerId;

	@Id
	@Column(name = "AAR_1ST_NR", columnDefinition = "char(1)", nullable = false)
	private String aar1stNr;

	@Column(name = "END_WELL_LGTH", columnDefinition = "smallint", nullable = false)
	private Integer endWellLength;

	@Column(name = "MED_WELL_LGTH", columnDefinition = "smallint", nullable = false)
	private Integer medWellLength;

}