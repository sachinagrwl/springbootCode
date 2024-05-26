package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import com.nscorp.obis.domain.UmlerLowProfileCar;

public interface UmlerLowProfileCarService {

	List<UmlerLowProfileCar> getUmlerLowProfileCars(String aarType, String carInit);

	UmlerLowProfileCar updateUmlerLowProfileCars(UmlerLowProfileCar umlerLowProfileCar, Map<String, String> headers);

	UmlerLowProfileCar deleteProfileLoad(UmlerLowProfileCar umlerLowProfileCar);

    UmlerLowProfileCar addLowProfileCar(UmlerLowProfileCar lowProfileCar, Map<String, String> headers);
}
