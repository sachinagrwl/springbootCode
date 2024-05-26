package com.nscorp.obis.services;

import java.util.Map;

import com.nscorp.obis.domain.TempEVT;

public interface TempEVTService {

	TempEVT addTempEVT(TempEVT tempEVT, Map<String, String> headers) throws Exception;

}
