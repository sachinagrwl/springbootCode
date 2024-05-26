package com.nscorp.obis.services;

import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.CityStateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CityStateServiceImpl implements CityStateService {

    @Autowired
    CityStateRepository cityStateRepository;


    @Override
    public List<String> getStates() {
        log.info("getStates : Method Starts");
        List<String> cityStates = null;
        cityStates = cityStateRepository.findAllDistinct();
        if (cityStates.isEmpty()) {
            throw new NoRecordsFoundException("No records found");
        }
        log.info("getStates : Method Ends");
        return cityStates;
    }

    @Override
    public List<String> getCityByState(String state) {
        log.info("getCityByState : Method Starts");
        List<String> citys = null;
        citys = cityStateRepository.findCityByState(state);
        if (citys.isEmpty()) {
            throw new NoRecordsFoundException("No records found");
        }
        log.info("getCityByState : Method Ends");
        return citys;
    }


}
