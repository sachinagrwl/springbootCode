package com.nscorp.obis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.domain.PoolTerminal;
import com.nscorp.obis.domain.PoolTerminalPrimaryKey;

public interface PoolTerminalRepository extends JpaRepository<PoolTerminal, PoolTerminalPrimaryKey> {

	List<PoolTerminal> findByPoolId(Long poolId);

	boolean existsByPoolIdAndTerminalId(Long poolId, Long terminalId);

	void deleteByPoolIdAndTerminalId(Long poolId, Long exisingTerminal);

	boolean existsByPoolId(Long poolId);
}
