package com.nscorp.obis.services;

import com.nscorp.obis.dto.CityStateDTO;

import java.util.List;

public interface CityStateService {

    List<String> getStates();

    List<String> getCityByState(String state);


}
