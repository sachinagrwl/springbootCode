package com.nscorp.obis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nscorp.obis.domain.AARHitch;
import com.nscorp.obis.domain.AARHitchPrimaryKeys;

@Repository
public interface AARHitchRepository extends JpaRepository<AARHitch, AARHitchPrimaryKeys>{

	@Query(value = "SELECT aarHitch " +
			"from AARHitch aarHitch " +
			"where (aarHitch.aarType like CONCAT('%',upper(:aarType),'%') or :aarType is null) " +
			"AND (aarHitch.hitchLocation like CONCAT('%',upper(:hitchLocation),'%') or :hitchLocation is null) " +
			"Order By aarHitch.aarType"
			)
	List<AARHitch> findAARHitch(String aarType, String hitchLocation);

}
