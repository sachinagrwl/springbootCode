package com.nscorp.obis.services;

import java.util.Map;
import java.util.Set;

import com.nscorp.obis.domain.Pool;

public interface PoolTerminalConflictService {

	Set<Pool> validatePoolTerminalConflict(Pool pool, Map<String, String> headers);

}
