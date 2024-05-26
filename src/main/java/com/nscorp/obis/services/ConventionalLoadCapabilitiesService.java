package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import com.nscorp.obis.domain.UmlerConventionalCar;

public interface ConventionalLoadCapabilitiesService {

	List<UmlerConventionalCar> getConvLoad(String aarType, String carInit);
	UmlerConventionalCar addConventionalCar(UmlerConventionalCar convCar, Map<String, String> headers);

	UmlerConventionalCar updateConventionalCar(UmlerConventionalCar convCar, Map<String, String> headers);
	UmlerConventionalCar deleteConvLoad(UmlerConventionalCar umlerConventionalCar);

}
