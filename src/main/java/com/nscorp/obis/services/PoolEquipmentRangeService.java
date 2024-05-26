package com.nscorp.obis.services;

import com.nscorp.obis.domain.PoolEquipmentRange;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

public interface PoolEquipmentRangeService {
    List<PoolEquipmentRange> getAllPoolEquipmentRanges();
    PoolEquipmentRange addPoolEquipmentRange(PoolEquipmentRange poolEquipmentRange, Map<String,String> headers);
    PoolEquipmentRange updatePoolEquipmentRange(PoolEquipmentRange poolEquipmentRange, Map<String,String> headers);
    PoolEquipmentRange deletePoolEquipmentRange(@Valid PoolEquipmentRange poolEquipmentRange);
}
