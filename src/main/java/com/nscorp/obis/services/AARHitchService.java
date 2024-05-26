package com.nscorp.obis.services;

import java.util.List;

import com.nscorp.obis.domain.AARHitch;

public interface AARHitchService {

	List<AARHitch> getAllHitch(String aarType, String hitchLocation);

}
