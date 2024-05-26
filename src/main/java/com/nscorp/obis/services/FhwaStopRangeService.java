package com.nscorp.obis.services;

import com.nscorp.obis.domain.FhwaStopRange;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface FhwaStopRangeService {

    List<FhwaStopRange> getAllFhwaStopRanges(String equipmentInit, String equipmentType, BigDecimal equipmentNumberLow,
                                BigDecimal equipmentNumberHigh);

    FhwaStopRange addFhwaStopRange(FhwaStopRange fhwaStopRange, Map<String,String> headers);

    FhwaStopRange updateFhwaStopRange(FhwaStopRange fhwaStopRange, Map<String,String> headers);

    void deleteFhwaStopRange(FhwaStopRange fhwaStopRange);

}
