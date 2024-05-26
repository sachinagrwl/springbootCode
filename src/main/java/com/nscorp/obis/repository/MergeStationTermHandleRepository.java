package com.nscorp.obis.repository;

import com.nscorp.obis.domain.MergeStationTermHandle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MergeStationTermHandleRepository extends JpaRepository<MergeStationTermHandle, Long> {

	List<MergeStationTermHandle> findByHandleTermId(Long handleTermId);

	boolean existsByHandleTermIdAndStationId(Long handleTermId, Long stationId);

	MergeStationTermHandle findByHandleTermIdAndStationId(Long handleTermId, Long stationId);

}
