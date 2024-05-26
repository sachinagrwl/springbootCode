package com.nscorp.obis.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CustomerShipmentDTO {
	
	private String customerName;
	private String customerNumber;

}
