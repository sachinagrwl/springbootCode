package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import com.nscorp.obis.domain.PoolType;

public interface PoolTypeService {

	List<PoolType> getReserveType();

	PoolType insertReservationType(PoolType poolTp, Map<String, String> headers);

    PoolType updateReservationType(PoolType poolType, Map<String, String> headers);

    PoolType deleteReservationType(PoolType poolTypeDtoToPoolType);
}
