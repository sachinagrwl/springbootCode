package com.nscorp.obis.dto;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ConventionalLoadCapabilitiesDTO {
	
	List<UmlerConventionalCarDTO> umlerConventionalCarList;
    List<ConventionalEquipmentWidthDTO> conventionalEquipmentWidthList;

}
