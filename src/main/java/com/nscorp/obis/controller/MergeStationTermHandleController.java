package com.nscorp.obis.controller;


import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.dto.MergeStationTermHandleDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.MergeStationTermHandleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@RequestMapping("/")
@Validated
@RestController
@CrossOrigin
@Slf4j
public class MergeStationTermHandleController {

	@Autowired
	MergeStationTermHandleService service;

	@GetMapping(value = ControllerConstants.MERGE_STATION_TERMINAL_HANDLE)
	public ResponseEntity<APIResponse<List<MergeStationTermHandleDTO>>> fetchMergeStationTermHandle(
			@RequestParam(name = "terminalId", required = true) Long terminalId) {
		try {
			log.info("fetchMergeStationTermHandle - Method Starts");
			List<MergeStationTermHandleDTO> mergeStationTermHandle = service.getMergeStationTermHandleDetails(terminalId);
			APIResponse<List<MergeStationTermHandleDTO>> responseObj;
			List<String> message = Arrays.asList("Successfully retrieved data!");
			responseObj = new APIResponse<>(message, mergeStationTermHandle, ResponseStatusCode.SUCCESS.getStatusCode(),
					ResponseStatusCode.SUCCESS.toString());
			log.info("fetchMergeStationTermHandle - Method Ends");
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<List<MergeStationTermHandleDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			log.error("fetchMergeStationTermHandle - Error :" + e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<MergeStationTermHandleDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			log.error("fetchMergeStationTermHandle - Error :" + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	};

	@PostMapping(value = ControllerConstants.MERGE_STATION_TERMINAL_HANDLE)
	public ResponseEntity<APIResponse<MergeStationTermHandleDTO>> insertMergeStationTermHandle(
			@RequestBody @Valid MergeStationTermHandleDTO mergeStationTermHandleDTO, @RequestHeader Map<String, String> headers) {
		try {
			log.info("insertMergeStationTermHandle - Method Starts");
			UserId.headerUserID(headers);
			MergeStationTermHandleDTO addedStationTermHandle = service.insertMergeStationTermHandle(mergeStationTermHandleDTO, headers);
			APIResponse<MergeStationTermHandleDTO> responseObj;
			List<String> message = Arrays.asList("Successfully added data!");
			responseObj = new APIResponse<>(message, addedStationTermHandle, ResponseStatusCode.SUCCESS.getStatusCode(),
					ResponseStatusCode.SUCCESS.toString());
			log.info("insertMergeStationTermHandle - Method Ends");
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException | RecordAlreadyExistsException e) {
			APIResponse<MergeStationTermHandleDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			HttpStatus httpStatus = HttpStatus.NOT_FOUND;
			if (e.getClass() == RecordAlreadyExistsException.class)
				httpStatus = HttpStatus.ALREADY_REPORTED;
			log.error("insertMergeStationTermHandle - Error :" + e.getMessage());
			return ResponseEntity.status(httpStatus).body(responseObj);
		} catch (Exception e) {
			APIResponse<MergeStationTermHandleDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			log.error("insertMergeStationTermHandle - Error :" + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	};

	@DeleteMapping(value = ControllerConstants.MERGE_STATION_TERMINAL_HANDLE)
	ResponseEntity<List<APIResponse<MergeStationTermHandleDTO>>> deleteMergeStationTerminalHandle(
			@NotEmpty(message = "request body can't be empty") @RequestBody @Valid List<MergeStationTermHandleDTO> dtos) {
		log.info("deleteMergeStationTerminalHandle - Method Starts");
		List<APIResponse<MergeStationTermHandleDTO>> responses;
		AtomicInteger errorCount = new AtomicInteger();
		responses=dtos.stream().map(DTO -> {
			APIResponse<MergeStationTermHandleDTO> response;
			try {
				response = new APIResponse<>(Arrays.asList("Successfully deleted data!"),
						service.deleteMergeStationTermHandle(DTO), ResponseStatusCode.SUCCESS);

			} catch (Exception err) {
				errorCount.incrementAndGet();
				log.error("deleteMergeStationTerminalHandle - Error :" + err.getMessage());
				response = new APIResponse<>(Arrays.asList(err.getMessage()), ResponseStatusCode.FAILURE);
			}
			return response;
		}).collect(Collectors.toList());
		if (errorCount.get() == 0) {
			return ResponseEntity.status(HttpStatus.OK).body(responses);
		}
		if (errorCount.get() == dtos.size()) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responses);
		} else {
			return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(responses);
		}

	}

}
