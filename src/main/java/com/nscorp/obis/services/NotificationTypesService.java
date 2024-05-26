package com.nscorp.obis.services;

import com.nscorp.obis.dto.NotificationTypesResponseDTO;

import java.util.List;

public interface NotificationTypesService {

	List<NotificationTypesResponseDTO> fetchNotificationTypes();

}
