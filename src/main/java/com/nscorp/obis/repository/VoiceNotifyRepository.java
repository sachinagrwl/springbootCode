package com.nscorp.obis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nscorp.obis.domain.VoiceNotify;

@Repository
public interface VoiceNotifyRepository extends JpaRepository<VoiceNotify,Long> {

	VoiceNotify findByNotifyQueueId(Long notifyQueueId);
	
	VoiceNotify findByEquipTp(String equipTp);

	boolean existsByNotifyQueueId(Long notifyQueueId);

	@Query(value = "SELECT voiceNotify from VoiceNotify voiceNotify " +
						"where (voiceNotify.notifyStat like CONCAT(upper(:notifyStat),'%') or :notifyStat is null) " +
						"AND (voiceNotify.termId = :termId or :termId is null) " +
						"AND (voiceNotify.notifyMethod like CONCAT(upper(:notifyMethod),'%') or :notifyMethod is null) " +
						"Order By voiceNotify.notifyCustId DESC, voiceNotify.localDateTm ASC")
	List<VoiceNotify> findByNotifyStatAndTermIdAndNotifyMethod(String notifyStat, Long termId, String notifyMethod);
	
}
