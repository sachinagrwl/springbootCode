package com.nscorp.obis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.domain.PoolCarStorageExempt;

public interface PoolCarStorageExemptRepository extends JpaRepository<PoolCarStorageExempt, Long>{

	boolean existsByPoolIdAndUversion(Long poolId, String uversion);

}
