package com.nscorp.obis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.domain.PoolType;

public interface PoolTypeRepository extends JpaRepository<PoolType, String>{

    boolean existsByPoolTp(PoolType poolType);
}
