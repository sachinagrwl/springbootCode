package com.nscorp.obis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.domain.PoolType;

public interface ReservationTypeSelectionRepository extends JpaRepository<PoolType, String> {

}
