package com.nscorp.obis.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(value = "true")
public class EquipmentLocationDTO extends AuditInfoDTO {

	private String equipId;
	private String equipTp;
	private BigDecimal equipNbr;
	private String equipInit;
	private String chasInit;
	private Long chasNbr;
	private String chasTp;
	private String chasId;
	private Long terminalId;
	private String currLoc;
	private String trackId;
	private String equipLocationAarCode;

}
