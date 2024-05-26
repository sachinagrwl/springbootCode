package com.nscorp.obis.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.domain.NorfolkSouthernEventLog;
import com.nscorp.obis.domain.VoiceNotify;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.NorfolkSouthernEventLogRepository;
import com.nscorp.obis.repository.VoiceNotifyRepository;

@Service
@Transactional
public class NorfolkSouthernEventLogServiceImpl implements NorfolkSouthernEventLogService {
	@Autowired(required = true)
	VoiceNotifyRepository voiceNotifyRepo;

	@Autowired
	NorfolkSouthernEventLogRepository eventLogRepository;

	@Override
	public List<NorfolkSouthernEventLog> getNorfolkSouthernEventLog(Long notifyQueueId) {
		VoiceNotify voiceNotify = voiceNotifyRepo.findByNotifyQueueId(notifyQueueId);
		if (voiceNotify == null) {
			throw new NoRecordsFoundException("No Records Found in VoiceNotify with notifyQueueId:" + notifyQueueId);
		}
		if (voiceNotify.getSvcId() == null || !eventLogRepository.existsByServiceId(voiceNotify.getSvcId())) {
			throw new NoRecordsFoundException("No Records Found in NorfolkSouthernEventLog with SVC ID:"
					+ voiceNotify.getSvcId() + " of provided notifyQueueId:" + notifyQueueId);
		}
		List<NorfolkSouthernEventLog> norfolkSouthernEventLogList = eventLogRepository
				.findByServiceId(voiceNotify.getSvcId());
		return norfolkSouthernEventLogList;
	}

}
