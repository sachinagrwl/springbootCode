package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.nscorp.obis.domain.NotifyQueue;
import com.nscorp.obis.domain.NotifyQueueRetry;
import com.nscorp.obis.dto.NotifyQueueUpdatedDTO;

public interface NotifyQueueService {

	List<NotifyQueue> updateNotifyQueue(@Valid @NotNull NotifyQueueUpdatedDTO notifyQueueObjDto,
			Map<String, String> headers);

	NotifyQueue addNotifyQueue(NotifyQueue notifyQueue, Map<String, String> headers);

	NotifyQueueRetry updateNotifyQueueRetry(@Valid @NotNull NotifyQueueRetry notifyQueueRetryDto,
			Map<String, String> headers);

	NotifyQueueRetry getNotifyQueueRetry(Long notifyQueueId);
}
