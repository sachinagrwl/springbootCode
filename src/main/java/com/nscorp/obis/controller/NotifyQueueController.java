package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.NotifyQueue;
import com.nscorp.obis.domain.NotifyQueueRetry;
import com.nscorp.obis.dto.NotifyQueueDTO;
import com.nscorp.obis.dto.NotifyQueueRetryDTO;
import com.nscorp.obis.dto.NotifyQueueUpdatedDTO;
import com.nscorp.obis.dto.mapper.NotifyQueueMapper;
import com.nscorp.obis.dto.mapper.NotifyQueueRetryMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.NotifyQueueService;

@Validated
@RestController
@CrossOrigin
@RequestMapping("/")
public class NotifyQueueController {
	private static final Logger logger = LoggerFactory.getLogger(NotifyQueueController.class);
	@Autowired
	NotifyQueueService notifyQueueService;

	@PutMapping(ControllerConstants.GET_RENOTIFY)
	public ResponseEntity<APIResponse<List<NotifyQueue>>> updateNotifyQueue(
			@Valid @NotNull @RequestBody NotifyQueueUpdatedDTO notifyQueueUpdatedDto,
			@RequestHeader Map<String, String> headers) {
		try {
			List<NotifyQueue> updatedNotifyQueue = notifyQueueService.updateNotifyQueue(notifyQueueUpdatedDto, headers);
			APIResponse<List<NotifyQueue>> responseObj = new APIResponse<>(
					Arrays.asList(updatedNotifyQueue.get(0).getNotifyStat().equalsIgnoreCase("SEND")
							? "I-Customer will be Re-notified."
							: "1 data voided successfully"),
					updatedNotifyQueue, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<List<NotifyQueue>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (NullPointerException e) {
			APIResponse<List<NotifyQueue>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<NotifyQueue>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@PostMapping(value = ControllerConstants.NOTIFY_RETRY)
	public ResponseEntity<APIResponse<NotifyQueueDTO>> addNotifyQueue(
			@Valid @NotNull @RequestBody NotifyQueueDTO notifyQueueDTO, @RequestHeader Map<String, String> headers) {
		try {
			NotifyQueue queueAdded = notifyQueueService
					.addNotifyQueue(NotifyQueueMapper.INSTANCE.notifyQueueDTOToNotifyQueue(notifyQueueDTO), headers);
			APIResponse<NotifyQueueDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),
					NotifyQueueMapper.INSTANCE.notifyQueueToNotifyQueueDTO(queueAdded), ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NullPointerException e) {
			APIResponse<NotifyQueueDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (Exception e) {
			APIResponse<NotifyQueueDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@PutMapping(value = ControllerConstants.NOTIFY_QUEUE_RETRY)
	public ResponseEntity<APIResponse<NotifyQueueRetryDTO>> updateNotifyQueue(
			@Valid @NotNull @RequestBody NotifyQueueRetryDTO notifyQueueRetryDto,
			@RequestHeader Map<String, String> headers) {

		try {
			NotifyQueueRetry notifyQueue = NotifyQueueRetryMapper.INSTANCE
					.notifyQueueRetryDtoToNotifyQueueRetry(notifyQueueRetryDto);
			NotifyQueueRetry notifyQueueUpdate = notifyQueueService.updateNotifyQueueRetry(notifyQueue, headers);
			NotifyQueueRetryDTO updateNotifyQueue = NotifyQueueRetryMapper.INSTANCE
					.notifyQueueRetryToNotifyQueueRetryDTO(notifyQueueUpdate);
			APIResponse<NotifyQueueRetryDTO> responseObj = new APIResponse<>(
					Arrays.asList(CommonConstants.UPDATE_SUCCESS_MESSAGE), updateNotifyQueue,
					ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<NotifyQueueRetryDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (InvalidDataException | RecordNotAddedException e) {
			APIResponse<NotifyQueueRetryDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (NullPointerException e) {
			APIResponse<NotifyQueueRetryDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (Exception e) {
			APIResponse<NotifyQueueRetryDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}

	}

	@GetMapping(value = ControllerConstants.NOTIFY_RETRY)
	public ResponseEntity<APIResponse<NotifyQueueRetryDTO>> getNotifyQueue(
			@RequestParam(required = true, name = "ntfy-queue-id") Long notifyQueueId) {
		try {
			logger.info(":::getNotifyQueue Method Starts:::");
			NotifyQueueRetry queueRetry = notifyQueueService.getNotifyQueueRetry(notifyQueueId);
			APIResponse<NotifyQueueRetryDTO> responseObj = new APIResponse<>(
					Arrays.asList(CommonConstants.RETRIVED_SUCCESS_MESSAGE),
					NotifyQueueRetryMapper.INSTANCE.notifyQueueRetryToNotifyQueueRetryDTO(queueRetry),
					ResponseStatusCode.SUCCESS);
			logger.info(":::getNotifyQueue Method Ends:::");
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);

		} catch (NoRecordsFoundException e) {
			APIResponse<NotifyQueueRetryDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (NullPointerException e) {
			APIResponse<NotifyQueueRetryDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);

		} catch (Exception e) {
			APIResponse<NotifyQueueRetryDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
}
