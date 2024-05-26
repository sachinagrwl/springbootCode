package com.nscorp.obis.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.domain.CityState;
import com.nscorp.obis.domain.CityStatePrimaryKey;
import org.springframework.data.jpa.repository.Query;

public interface CityStateRepository extends JpaRepository<CityState, CityStatePrimaryKey>{
	
	List<CityState> findAllByStateAbbreviation(String stateAbbreviation);

	@Query("SELECT DISTINCT state.stateAbbreviation FROM CityState state  ORDER BY state.stateAbbreviation ASC")
	List<String> findAllDistinct();

	@Query("SELECT state.city FROM CityState state  WHERE state.stateAbbreviation=?1 ORDER BY state.city ASC")
	List<String> findCityByState(String state);
}
