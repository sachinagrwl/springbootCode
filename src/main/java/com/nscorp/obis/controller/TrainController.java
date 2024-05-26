package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.common.NullOrNotBlank;
import com.nscorp.obis.dto.TrainDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.TrainService;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/")
@Validated
@RestController
@CrossOrigin
@Slf4j
public class TrainController {

	@Autowired
	TrainService trainService;

	@GetMapping(value = ControllerConstants.TRAIN)
	public ResponseEntity<APIResponse<List<TrainDTO>>> fetchTrains(
			@NullOrNotBlank(min = 1, max = 4, message = "train number length should be between 1 and 4") @RequestParam(name = "train-number", required = false) String trainNumber,
			@NullOrNotBlank(min = 1, max = 24, message = "train desc length should be between 1 and 24") @RequestParam(name = "train-desc", required = false) String trainDesc) {
		try {
			log.info("fetchTrains - Method Starts");
			List<TrainDTO> trains = trainService.fetchTrainDetails(trainNumber, trainDesc);
			APIResponse<List<TrainDTO>> responseObj;
			List<String> message = Arrays.asList("Successfully retrieved data!");
			responseObj = new APIResponse<>(message, trains, ResponseStatusCode.SUCCESS.getStatusCode(),
					ResponseStatusCode.SUCCESS.toString());
			log.info("fetchTrains - Method Ends");
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<List<TrainDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			log.error("fetchTrains - Error :" + e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<TrainDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			log.error("fetchTrains - Error :" + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@PostMapping(value = ControllerConstants.TRAIN)
	public ResponseEntity<APIResponse<TrainDTO>> addTrain(@Valid @RequestBody TrainDTO trainDTO,
			@RequestHeader Map<String, String> headers) {
		try {
			log.info("addTrain - Method Starts");
			TrainDTO train = trainService.addTrain(trainDTO, headers);
			APIResponse<TrainDTO> responseObj;
			List<String> message = Arrays.asList("Successfully added data!");
			responseObj = new APIResponse<>(message, train, ResponseStatusCode.SUCCESS.getStatusCode(),
					ResponseStatusCode.SUCCESS.toString());
			log.info("addTrain - Method Ends");
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<TrainDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			log.error("addTrain - Error :" + e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<TrainDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			log.error("addTrain - Error :" + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@PutMapping(value = ControllerConstants.TRAIN)
	public ResponseEntity<APIResponse<TrainDTO>> updateTrain(@Valid @RequestBody TrainDTO trainDTO,
			@RequestHeader Map<String, String> headers) {
		try {
			log.info("updateTrain - Method Starts");
			TrainDTO train = trainService.updateTrain(trainDTO, headers);
			APIResponse<TrainDTO> responseObj;
			List<String> message = Arrays.asList("Successfully updated data!");
			responseObj = new APIResponse<>(message, train, ResponseStatusCode.SUCCESS.getStatusCode(),
					ResponseStatusCode.SUCCESS.toString());
			log.info("updateTrain - Method Ends");
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<TrainDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			log.error("updateTrain - Error :" + e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<TrainDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			log.error("updateTrain - Error :" + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@DeleteMapping(value = ControllerConstants.TRAIN)
	public ResponseEntity<APIResponse<TrainDTO>> deleteTrain(@Valid @RequestBody TrainDTO trainDTO) {
		try {
			log.info("deleteTrain - Method Starts");
			TrainDTO train = trainService.deleteTrain(trainDTO);
			APIResponse<TrainDTO> responseObj;
			List<String> message = Arrays.asList("Successfully deleted data!");
			responseObj = new APIResponse<>(message, train, ResponseStatusCode.SUCCESS.getStatusCode(),
					ResponseStatusCode.SUCCESS.toString());
			log.info("deleteTrain - Method Ends");
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException | RecordNotDeletedException e) {
			APIResponse<TrainDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			HttpStatus status = HttpStatus.BAD_REQUEST;
			if (e.getClass() == NoRecordsFoundException.class) {
				status = HttpStatus.NOT_FOUND;
			}
			log.error("deleteTrain - Error :" + e.getMessage());
			return ResponseEntity.status(status).body(responseObj);
		} catch (Exception e) {
			APIResponse<TrainDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			log.error("deleteTrain - Error :" + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

}
