package com.nscorp.obis.controller;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.dto.NotificationTypesResponseDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.NotificationTypesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;


@RestController
@Validated
@RequestMapping("/")
public class NotificationTypesController {
	
	@Autowired(required = true)
	NotificationTypesService notificationTypesService;
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(NotificationTypesController.class);
	
	@GetMapping(value = ControllerConstants.NOTIFICATION_TYPES)
	public ResponseEntity<APIResponse<List<NotificationTypesResponseDTO>>> getNotificationTypes() throws SQLException {
		try {
			List<NotificationTypesResponseDTO> notificationTypesDTO = notificationTypesService.fetchNotificationTypes();
			APIResponse<List<NotificationTypesResponseDTO>> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieve data!"),notificationTypesDTO,
					ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		}catch (NoRecordsFoundException e) {
			APIResponse<List<NotificationTypesResponseDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		}
	}

}
