package com.nscorp.obis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.domain.StationTermHandle;

public interface StationTermHandleRepository extends JpaRepository<StationTermHandle, Long> {

	List<StationTermHandle> findByHandleTermId(Long handleTermId);

	boolean existsByHandleTermIdAndStationId(Long handleTermId, Long stationId);
	
	StationTermHandle findByHandleTermIdAndStationId(Long handleTermId, Long stationId);

}
