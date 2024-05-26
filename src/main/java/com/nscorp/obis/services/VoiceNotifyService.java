package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import com.nscorp.obis.domain.VoiceNotify;

public interface VoiceNotifyService {

	
	VoiceNotify getVoiceNotify(Long notifyQueueId);

	List<VoiceNotify> getVoiceNtfyList(String notifyStat, Long termId, String notifyMethod);

	VoiceNotify updateVoiceNotify(VoiceNotify voiceNotify, Map<String, String> headers);

	//List<VoiceNotify> getAllVoiceNotify(Long notifyQueueId);
}
