package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.NotifyQueueRetry;
import com.nscorp.obis.dto.NotifyQueueRetryDTO;

@Mapper(componentModel = "spring")
public interface NotifyQueueRetryMapper {
	
	NotifyQueueRetryMapper INSTANCE = Mappers.getMapper(NotifyQueueRetryMapper.class);
	NotifyQueueRetryDTO notifyQueueRetryToNotifyQueueRetryDTO(NotifyQueueRetry notifyQueueRetry);
	NotifyQueueRetry notifyQueueRetryDtoToNotifyQueueRetry(NotifyQueueRetryDTO notifyQueueRetryDto);

}
