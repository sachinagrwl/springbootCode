package com.nscorp.obis.dto;


import java.sql.Timestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShipmentDlvyDateDTO {
	
	 private Long svcId;
	    private Timestamp dlvyByDtTm;
}
