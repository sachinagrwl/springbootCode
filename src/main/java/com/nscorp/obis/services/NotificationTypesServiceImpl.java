package com.nscorp.obis.services;

import com.nscorp.obis.domain.NotificationType;
import com.nscorp.obis.dto.NotificationTypesResponseDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class NotificationTypesServiceImpl implements NotificationTypesService {

	@Override
	public List<NotificationTypesResponseDTO> fetchNotificationTypes() {
	
		List<NotificationTypesResponseDTO> notificationTypesDTO = new ArrayList<>();
		
		
		NotificationType[] notificationTypes = NotificationType.values(); 
		List<NotificationType> types = Arrays.asList(notificationTypes);
		for(NotificationType type : types) {
			NotificationTypesResponseDTO notificationTypesResponse = new NotificationTypesResponseDTO();
			notificationTypesResponse.setDescription(type);
			notificationTypesResponse.setCode(type.getCode());
			List<String> invalidEventCodes = new  ArrayList<>();
			if(type.getCode().trim().equalsIgnoreCase("FD")) {
				invalidEventCodes.add("RMFC");
			}
			if(type.getCode().trim().equalsIgnoreCase("FS")) {
				invalidEventCodes.add("RMFC");
			}
			notificationTypesResponse.setInvalidEventCodes(invalidEventCodes);
			notificationTypesDTO.add(notificationTypesResponse);	
			
		}
		return notificationTypesDTO;
	}

}
