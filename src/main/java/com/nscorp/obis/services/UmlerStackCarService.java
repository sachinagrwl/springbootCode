package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import com.nscorp.obis.domain.UmlerStackCar;

public interface UmlerStackCarService {

	List<UmlerStackCar> getUmlerStackCars(String aarType, String carInit);

	UmlerStackCar deleteUmlerStackCar(UmlerStackCar umlerStackCar);

	UmlerStackCar insertStackCar(UmlerStackCar umlerStackCar, Map<String, String> headers);

	UmlerStackCar updateStackCar(UmlerStackCar umlerStackCar, Map<String, String> headers);

}
