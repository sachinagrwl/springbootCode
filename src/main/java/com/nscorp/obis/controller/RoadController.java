package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.Road;
import com.nscorp.obis.dto.RoadDTO;
import com.nscorp.obis.dto.mapper.RoadMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.RoadService;

@RestController
@RequestMapping(value = ControllerConstants.ROAD)
@Validated
public class RoadController {

	@Autowired
	RoadService roadService;

	@GetMapping
	public ResponseEntity<APIResponse<List<RoadDTO>>> getAllRoads(
			@RequestParam(required = false, name = "roadNumber") String roadNumber,
			@RequestParam(required = false, name = "roadName") String roadName,
			@RequestParam(required = false, name = "roadFullName") String roadFullName,
			@RequestParam(required = false, name = "roadType") String roadType) {
		try {
			List<RoadDTO> roadDTOList = Collections.emptyList();
			List<Road> roadList = roadService.getAllRoads(roadNumber, roadName, roadFullName, roadType);
			if (!CollectionUtils.isEmpty(roadList)) {
				roadDTOList = roadList.stream().map(RoadMapper.INSTANCE::RoadToRoadDTO).collect(Collectors.toList());
			}
			APIResponse<List<RoadDTO>> listAPIResponse = new APIResponse<>(
					Arrays.asList("Successfully retrieved data!"), roadDTOList, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(listAPIResponse);
		} catch (NoRecordsFoundException e) {
			APIResponse<List<RoadDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<RoadDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<APIResponse<RoadDTO>> addRoad(@Valid @NotNull @RequestBody RoadDTO roadDTO,
			@RequestHeader Map<String, String> headers) {

		try {
			Road roadAdded = roadService.addRoad(RoadMapper.INSTANCE.RoadDTOToRoad(roadDTO), headers);
			APIResponse<RoadDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),
					RoadMapper.INSTANCE.RoadToRoadDTO(roadAdded), ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<RoadDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (RecordAlreadyExistsException e) {
			APIResponse<RoadDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (NullPointerException e) {
			APIResponse<RoadDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (Exception e) {
			APIResponse<RoadDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
	@PutMapping
	public ResponseEntity<APIResponse<RoadDTO>> updateRoad(@Valid @NotNull @RequestBody RoadDTO roadDTO, @RequestHeader Map<String,String> headers) {
		try {
			Road road = RoadMapper.INSTANCE.RoadDTOToRoad(roadDTO);
			Road roadUpdate = roadService.updateRoad(road, headers);
			RoadDTO updateRoad = RoadMapper.INSTANCE.RoadToRoadDTO(roadUpdate);
			APIResponse<RoadDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),updateRoad, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<RoadDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (RecordAlreadyExistsException e) {
			APIResponse<RoadDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (NullPointerException e){
			APIResponse<RoadDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (Exception e){
			APIResponse<RoadDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@DeleteMapping
	public ResponseEntity<List<APIResponse<RoadDTO>>> deleteRoad(@Valid @NotNull @RequestBody List<RoadDTO> roadDTOList){
		List<APIResponse<RoadDTO>> response;
		AtomicInteger errorCount = new AtomicInteger();
		if (roadDTOList != null && !roadDTOList.isEmpty()) {
			response = roadDTOList.stream().map(roadObjDto -> {
						APIResponse<RoadDTO> singleDtoDelResponse;
						try {
							roadService.deleteRoad(RoadMapper.INSTANCE.RoadDTOToRoad(roadObjDto));
							singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"),roadObjDto, ResponseStatusCode.SUCCESS);
						} catch (Exception e) {
							errorCount.incrementAndGet();
							singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
						}
						return singleDtoDelResponse;
					})
					.collect(Collectors.toList());
		} else {
			response = Collections.emptyList();
		}

		if (errorCount.get() == 0 && response.size() > 0) { // No errors and atleast 1 success
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else if (response.size() > errorCount.get()) { // Partial success
			return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(response);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
}
