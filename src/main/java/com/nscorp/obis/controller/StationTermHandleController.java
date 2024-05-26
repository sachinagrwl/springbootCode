package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.dto.StationTermHandleDTO;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.StationTermHandleService;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/")
@Validated
@RestController
@CrossOrigin
@Slf4j
public class StationTermHandleController {

	@Autowired
	StationTermHandleService service;

	@GetMapping(value = ControllerConstants.STATION_TERMINAL_HANDLE)
	public ResponseEntity<APIResponse<List<StationTermHandleDTO>>> fetchStationTermHandle(
			@RequestParam(name = "terminalId", required = true) Long terminalId) {
		try {
			log.info("fetchStationTermHandle - Method Starts");
			List<StationTermHandleDTO> stationTermHandle = service.getTermHandleDetails(terminalId);
			APIResponse<List<StationTermHandleDTO>> responseObj;
			List<String> message = Arrays.asList("Successfully retrieved data!");
			responseObj = new APIResponse<>(message, stationTermHandle, ResponseStatusCode.SUCCESS.getStatusCode(),
					ResponseStatusCode.SUCCESS.toString());
			log.info("fetchStationTermHandle - Method Ends");
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<List<StationTermHandleDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			log.error("fetchStationTermHandle - Error :" + e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<StationTermHandleDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			log.error("fetchStationTermHandle - Error :" + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	};

	@PostMapping(value = ControllerConstants.STATION_TERMINAL_HANDLE)
	public ResponseEntity<APIResponse<StationTermHandleDTO>> insertStationTermHandle(
			@RequestBody @Valid StationTermHandleDTO stationTermHandleDTO, @RequestHeader Map<String, String> headers) {
		try {
			log.info("insertStationTermHandle - Method Starts");
			UserId.headerUserID(headers);
			StationTermHandleDTO stationTermHandle = service.insertStationTermHandle(stationTermHandleDTO, headers);
			APIResponse<StationTermHandleDTO> responseObj;
			List<String> message = Arrays.asList("Successfully added data!");
			responseObj = new APIResponse<>(message, stationTermHandle, ResponseStatusCode.SUCCESS.getStatusCode(),
					ResponseStatusCode.SUCCESS.toString());
			log.info("insertStationTermHandle - Method Ends");
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException | RecordAlreadyExistsException e) {
			APIResponse<StationTermHandleDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			HttpStatus httpStatus = HttpStatus.NOT_FOUND;
			if (e.getClass() == RecordAlreadyExistsException.class)
				httpStatus = HttpStatus.ALREADY_REPORTED;
			log.error("insertStationTermHandle - Error :" + e.getMessage());
			return ResponseEntity.status(httpStatus).body(responseObj);
		} catch (Exception e) {
			APIResponse<StationTermHandleDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			log.error("insertStationTermHandle - Error :" + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	};

	@DeleteMapping(value = ControllerConstants.STATION_TERMINAL_HANDLE)
	ResponseEntity<List<APIResponse<StationTermHandleDTO>>> deleteStationTerminalHandle(
			@NotEmpty(message = "request body can't be empty") @RequestBody @Valid List<StationTermHandleDTO> dtos) {
		log.info("deleteStationTerminalHandle - Method Starts");
		List<APIResponse<StationTermHandleDTO>> responses;
		AtomicInteger errorCount = new AtomicInteger();
		responses=dtos.stream().map(DTO -> {
			APIResponse<StationTermHandleDTO> response;
			try {
				response = new APIResponse<>(Arrays.asList("Successfully deleted data!"),
						service.deleteStationTermHandle(DTO), ResponseStatusCode.SUCCESS);

			} catch (Exception err) {
				errorCount.incrementAndGet();
				log.error("deleteStationTerminalHandle - Error :" + err.getMessage());
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
