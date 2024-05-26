package com.nscorp.obis.services;

import java.math.BigDecimal;
import java.util.Map;

import com.nscorp.obis.domain.EquipmentCar;

public interface EquipmentCarService {

	EquipmentCar getEquipmentCar(String carInit, BigDecimal carNbr, String carEquipType);

    EquipmentCar addEquipmentCar(EquipmentCar eqCar, Map<String, String> headers);
    
    EquipmentCar updateEquipmentCar(EquipmentCar eqCar, Map<String, String> headers);
}
