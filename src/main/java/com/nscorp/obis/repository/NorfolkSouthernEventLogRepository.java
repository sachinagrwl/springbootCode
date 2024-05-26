package com.nscorp.obis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.domain.NorfolkSouthernEventLog;

public interface NorfolkSouthernEventLogRepository extends JpaRepository<NorfolkSouthernEventLog, Long> {

	boolean existsByServiceId(Long svcId);

	List<NorfolkSouthernEventLog> findByServiceId(Long svcId);

	boolean existsByServiceIdAndEvtCd(Long svcId, String eventCd);

	List<NorfolkSouthernEventLog> findByServiceIdAndEvtCd(Long svcId, String eventCd);

	NorfolkSouthernEventLog findByEventLogId(Long evtLogId);

}
