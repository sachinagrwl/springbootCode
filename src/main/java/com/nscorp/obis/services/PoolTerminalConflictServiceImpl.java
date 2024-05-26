package com.nscorp.obis.services;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.domain.Pool;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.PoolRepository;

@Service
@Transactional
@Slf4j
public class PoolTerminalConflictServiceImpl implements PoolTerminalConflictService {

	@Autowired
	PoolRepository poolRepository;

	@Override
	public Set<Pool> validatePoolTerminalConflict(Pool pool, Map<String, String> headers) {
		log.info("validatePoolTerminalConflict : Method Starts");
		Set<Pool> conflictPoolSet = new HashSet<>();
		Pool existingPool;
		if (pool.getPoolId() == null) {
			throw new NoRecordsFoundException("Pool Id should not be null!");
		}
		if (poolRepository.existsById(pool.getPoolId())) {
			existingPool = poolRepository.findById(pool.getPoolId()).get();
		} else {
			throw new NoRecordsFoundException(
					"PoolId: " + pool.getPoolId() + " is invalid as it doesn't exists in Pool");
		}
		List<Long> existingTerminals = existingPool.getTerminals().stream().map(terminal -> terminal.getTerminalId())
				.collect(Collectors.toList());
		pool.getTerminals().forEach(terminal -> {
			if (!existingTerminals.contains(terminal.getTerminalId())) {
				Long terminalConflictPoolId = poolRepository.poolTerminalConflict(pool.getPoolId(),
						terminal.getTerminalId());
				if (terminalConflictPoolId != null && poolRepository.existsById(terminalConflictPoolId)) {
					Pool conflictPool = poolRepository.findById(terminalConflictPoolId).get();
					conflictPoolSet.add(conflictPool);
				}
			}
		});
		log.info("validatePoolTerminalConflict : Method Ends");
		return conflictPoolSet;
	}

}
