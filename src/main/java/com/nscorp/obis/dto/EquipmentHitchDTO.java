package com.nscorp.obis.dto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EquipmentHitchDTO {

	private String carInit;

	private BigDecimal carNbr;

	private String carEquipType;

	private String hitchLocation;

	private String hitchPosition;

	private String BadOrderInd;

	private String DamageInd;

	private Long evtLogId;

}
