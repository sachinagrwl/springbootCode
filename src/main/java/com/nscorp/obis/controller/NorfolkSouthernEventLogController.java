package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.NorfolkSouthernEventLog;
import com.nscorp.obis.dto.NorfolkSouthernEventLogDTO;
import com.nscorp.obis.dto.mapper.NorfolkSouthernEventLogMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.NorfolkSouthernEventLogService;

@RestController
@RequestMapping(ControllerConstants.NS_EVENT_LOG)
public class NorfolkSouthernEventLogController {

	@Autowired
	NorfolkSouthernEventLogService norfolkSouthernEventLogService;

	@GetMapping
	public ResponseEntity<APIResponse<List<NorfolkSouthernEventLogDTO>>> getNorfolkSouthernEventLog(
			@RequestParam(required = true, name = "ntfy-queue-id") Long notifyQueueId) {

		try {
			List<NorfolkSouthernEventLogDTO> norfolkSouthernEventLogDTOList = Collections.emptyList();
			List<NorfolkSouthernEventLog> norfolkSouthernEventLog = norfolkSouthernEventLogService
					.getNorfolkSouthernEventLog(notifyQueueId);
			if (!CollectionUtils.isEmpty(norfolkSouthernEventLog)) {
				norfolkSouthernEventLogDTOList = norfolkSouthernEventLog.stream().map(
						NorfolkSouthernEventLogMapper.INSTANCE::NorfolkSouthernEventLogToNorfolkSouthernEventLogDTO)
						.collect(Collectors.toList());
			}
			APIResponse<List<NorfolkSouthernEventLogDTO>> responseObj = new APIResponse<>(
					Arrays.asList(CommonConstants.RETRIVED_SUCCESS_MESSAGE), norfolkSouthernEventLogDTOList,
					ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<List<NorfolkSouthernEventLogDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<NorfolkSouthernEventLogDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}

	}
}
