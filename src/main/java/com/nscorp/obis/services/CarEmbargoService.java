package com.nscorp.obis.services;

import com.nscorp.obis.domain.CarEmbargo;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

public interface CarEmbargoService {
    List<CarEmbargo> getAllCarEmbargo();

    CarEmbargo insertCarEmbargo(CarEmbargo carEmbargoObj, Map<String, String> headers);

    CarEmbargo updateCarEmbargo(CarEmbargo carEmbargoObj, Map<String, String> headers);

    CarEmbargo deleteCarEmbargo(@Valid CarEmbargo carEmbargo);
}
