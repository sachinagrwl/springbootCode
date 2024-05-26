package com.nscorp.obis.repository;

import java.util.List;

import com.nscorp.obis.common.CommonKeyGenerator;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.domain.Pool;

public interface PoolRepository extends JpaRepository<Pool, Long>, CommonKeyGenerator {
	Pool getByPoolName(String poolName);

	Pool getByPoolId(Long poolId);

	List<Pool> findByPoolId(Long poolId);

	List<Pool> findByPoolName(String poolName);

	boolean existsByPoolId(Long poolId);

	List<Pool> findAll(Specification<Pool> specs);

	@Transactional
	@Procedure(procedureName = "INTERMODAL.POOL_TERM_CON", outputParameterName = "V_OUTPUT")
	Long poolTerminalConflict(Long I_POOL_ID, Long I_TERM_ID);

	boolean existsByPoolName(String poolName);

	boolean existsByDescription(String description);

	boolean existsByPoolReservationType(String poolTp);

	boolean existsByTruckerGroup_TruckerGroupCode(String truckerGroupCode);
}
