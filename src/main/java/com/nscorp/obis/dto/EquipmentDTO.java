package com.nscorp.obis.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class EquipmentDTO {
	 private String equipInit;
	 private Integer equipNbr;
	 private String equipType;
		private String equipId;
		private String qtUseRec;

}
