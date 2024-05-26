package com.nscorp.obis.services;

import com.nscorp.obis.domain.TruckerGroup;

import java.util.List;
import java.util.Map;

public interface TruckerGroupService {


    List<TruckerGroup> getAllTruckerGroups();

    TruckerGroup addTruckerGroup(TruckerGroup truckerGroupObj, Map<String, String> headers);

    TruckerGroup updateTruckerGroup(TruckerGroup truckerGroupDTOToTruckerGroup, Map<String, String> headers);

    TruckerGroup deleteTruckerGroup(TruckerGroup truckerGroupDTOToTruckerGroup);
}
