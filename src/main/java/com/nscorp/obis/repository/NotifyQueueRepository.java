package com.nscorp.obis.repository;

import com.nscorp.obis.common.CommonKeyGenerator;
import com.nscorp.obis.domain.NotifyQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface NotifyQueueRepository extends JpaRepository<NotifyQueue,Long>, CommonKeyGenerator {
    @Query(value="SELECT COUNT(nq.notifyStat) FROM NotifyQueue nq WHERE nq.notifyStat = :status And nq.updateDateTime < :updateTime And (nq.eventCode = 'RMFC' Or nq.eventCode = 'RCOV' Or nq.eventCode = 'NTFY') And nq.termId = :termId")
    int getCountNotifyStateForTerminal(double termId, String status, Date updateTime);
	
	NotifyQueue findByNtfyQueueIdAndUversion(Long ntfyQueueId, String uversion);

    NotifyQueue findByNtfyQueueId(Long notifyQueueId);

	boolean existsByEvtLogId(Long evtLogId);

	List<NotifyQueue> findByEvtLogId(Long evtLogId);

}
