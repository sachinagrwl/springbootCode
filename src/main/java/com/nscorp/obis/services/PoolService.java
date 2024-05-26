package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import com.nscorp.obis.domain.Pool;

public interface PoolService {

	List<Pool> getPools(Long poolId, String poolName);

	Pool updatePoolTerminal(Pool pool, Map<String, String> headers);

    Pool addPool(Pool pool, Map<String, String> headers);

	Pool updatePool(Pool pool, Map<String, String> headers);

	Pool deletePool(Pool poolDtoToPool);
}

