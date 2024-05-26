package com.nscorp.obis.dto;

import java.math.BigDecimal;

import com.nscorp.obis.domain.Shipment;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShipmentActiveViewDTO {
				
	private Long svcId;
	private String equipInit;
	private BigDecimal equipNbr;
	private String equipTp;
	private String equipId;
	private ShipmentDTO shipment;
}
