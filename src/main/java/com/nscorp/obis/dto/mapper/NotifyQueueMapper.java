package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.NotifyQueue;
import com.nscorp.obis.dto.NotifyQueueDTO;

@Mapper(componentModel = "spring")
public interface NotifyQueueMapper {

	//String INSTANCE = null;
	NotifyQueueMapper INSTANCE = Mappers.getMapper(NotifyQueueMapper.class);
	NotifyQueueDTO notifyQueueToNotifyQueueDTO(NotifyQueue notifyQueue);
	NotifyQueue notifyQueueDTOToNotifyQueue(NotifyQueueDTO notifyQueueDTO);

}
