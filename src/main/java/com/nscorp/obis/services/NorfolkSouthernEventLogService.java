package com.nscorp.obis.services;

import java.util.List;

import com.nscorp.obis.domain.NorfolkSouthernEventLog;

public interface NorfolkSouthernEventLogService {

	List<NorfolkSouthernEventLog> getNorfolkSouthernEventLog(Long notifyQueueId);

}
