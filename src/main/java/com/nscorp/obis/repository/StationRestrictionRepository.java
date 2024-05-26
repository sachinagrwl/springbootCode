package com.nscorp.obis.repository;

import com.nscorp.obis.domain.StationRestriction;
import com.nscorp.obis.domain.StationRestrictionPrimaryKeys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StationRestrictionRepository extends JpaRepository<StationRestriction, StationRestrictionPrimaryKeys>{
	boolean existsByStationCrossReferenceIdAndCarTypeAndFreightType(long stationCrossReferenceId, String carType,
			String freightType);

	void deleteByStationCrossReferenceIdAndCarTypeAndFreightType(long stationCrossReferenceId, String carType,
			String freightType);

	List<StationRestriction> findByStationCrossReferenceId(Long termId);

}
