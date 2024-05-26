package com.nscorp.obis.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class VoiceNotify2DTO {
	
	private Long svcId;
	private String custBill;
	private String custShip;
	private String stationName;
}
