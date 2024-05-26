package com.nscorp.obis.repository;
import com.nscorp.obis.domain.Trucker;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TruckerRepository extends JpaRepository<Trucker,String> {
	List<Trucker> findTruckerByTruckerCode(String truckerCode);
}
