package com.nscorp.obis.controller;

import java.sql.SQLException;
import java.util.ArrayList;
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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
import com.nscorp.obis.domain.SpecialActivityNotifyProfile;
import com.nscorp.obis.dto.ActivityNotifyProfileDTO;
import com.nscorp.obis.dto.SpecialActivityNotifyDTO;
import com.nscorp.obis.dto.SpecialActivityNotifyProfileDTO;
import com.nscorp.obis.dto.mapper.ActivityNotifyProfileMapper;
import com.nscorp.obis.dto.mapper.CustomerNotifyProfileMapper;
import com.nscorp.obis.dto.mapper.SpecialActivityNotifyProfileMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.SpecialActivityNotifyProfileService;

import lombok.extern.slf4j.Slf4j;

@Validated
@RestController
@RequestMapping("/")
@Slf4j
public class SpecialActivityNotifyProfileController {

	@Autowired
	SpecialActivityNotifyProfileService specialActivityNotifyProfileService;

	@GetMapping(value = ControllerConstants.SPECIAL_ACTIVITY)
	public ResponseEntity<APIResponse<List<SpecialActivityNotifyDTO>>> getActivityDetails() throws SQLException {
		try {

			List<SpecialActivityNotifyDTO> activityDto = new ArrayList<>();

			List<SpecialActivityNotifyProfile> data = specialActivityNotifyProfileService.fetchActivityDetails();
			if (data != null && !data.isEmpty()) {
				activityDto = data.stream().map(
						SpecialActivityNotifyProfileMapper.INSTANCE::specialActivityNotifyProfileToSpecialActivityNotifyDto)
						.collect(Collectors.toList());
			}

			APIResponse<List<SpecialActivityNotifyDTO>> responseObj = new APIResponse<>(
					Arrays.asList("Successfully retrieved data!"), activityDto,
					ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);

		} catch (NoRecordsFoundException e) {
			APIResponse<List<SpecialActivityNotifyDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<SpecialActivityNotifyDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@GetMapping(value = ControllerConstants.SPECIAL_ACTIVITY_PROFILE)
	public ResponseEntity<APIResponse<List<SpecialActivityNotifyProfileDTO>>> getActivityProfiles(
			@Valid @RequestParam(name = "activityId", required = false) Integer activityId) throws SQLException {
		try {

			List<SpecialActivityNotifyProfileDTO> activityDto = new ArrayList<>();

			List<SpecialActivityNotifyProfile> data = specialActivityNotifyProfileService
					.fetchActivityProfiles(activityId);
			if (data != null && !data.isEmpty()) {
				activityDto = data.stream().map(
						SpecialActivityNotifyProfileMapper.INSTANCE::specialActivityNotifyProfileToSpecialActivityNotifyProfileDto)
						.collect(Collectors.toList());
			}
			APIResponse<List<SpecialActivityNotifyProfileDTO>> responseObj = new APIResponse<>(
					Arrays.asList("Successfully retrieved data!"), activityDto,
					ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);

		} catch (NoRecordsFoundException e) {
			APIResponse<List<SpecialActivityNotifyProfileDTO>> responseObj = new APIResponse<>(
					Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION.getStatusCode(),
					ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<SpecialActivityNotifyProfileDTO>> responseObj = new APIResponse<>(
					Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION.getStatusCode(),
					ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@PostMapping(value = ControllerConstants.SPECIAL_ACTIVITY_PROFILE)
	public ResponseEntity<APIResponse<ActivityNotifyProfileDTO>> addActivityProfile(
			@Valid @RequestBody ActivityNotifyProfileDTO activityNotifyProfileDTO,
			@RequestHeader Map<String, String> headers) throws SQLException {
		try {
			log.info("addActivityProfile : Method Starts");
			ActivityNotifyProfileDTO activityDto = specialActivityNotifyProfileService
					.addActivityNotifyProfile(activityNotifyProfileDTO, headers);
			APIResponse<ActivityNotifyProfileDTO> responseObj = new APIResponse<>(
					Arrays.asList("Successfully added data!"), activityDto, ResponseStatusCode.SUCCESS.getStatusCode(),
					ResponseStatusCode.SUCCESS.toString());
			log.info("addActivityProfile : Method Ends");
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);

		} catch (NoRecordsFoundException e) {
			APIResponse<ActivityNotifyProfileDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			log.error("addActivityProfile : Error :" + e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<ActivityNotifyProfileDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			log.error("addActivityProfile : Error :" + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@PutMapping(value = ControllerConstants.SPECIAL_ACTIVITY_PROFILE)
	public ResponseEntity<APIResponse<SpecialActivityNotifyProfileDTO>> updateActivityProfile(
			@Valid @RequestBody SpecialActivityNotifyProfileDTO specialActivityNotifyProfileDTO,
			@RequestHeader Map<String, String> headers) throws SQLException {
		try {

			SpecialActivityNotifyProfile specialActivityNotifyProfile = specialActivityNotifyProfileService.updateActivityNotifyProfile(specialActivityNotifyProfileDTO, headers);
			SpecialActivityNotifyProfileDTO activityNotifyProfileDTO = SpecialActivityNotifyProfileMapper.INSTANCE.specialActivityNotifyProfileToSpecialActivityNotifyProfileDto(specialActivityNotifyProfile);
			APIResponse<SpecialActivityNotifyProfileDTO> responseObj = new APIResponse<>(
					Arrays.asList("Successfully updated data!"), activityNotifyProfileDTO,
					ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);

		} catch (NoRecordsFoundException e) {
			APIResponse<SpecialActivityNotifyProfileDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<SpecialActivityNotifyProfileDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@DeleteMapping(value = ControllerConstants.SPECIAL_ACTIVITY_NOTIFY_PROFILE)
	public ResponseEntity<List<APIResponse<ActivityNotifyProfileDTO>>> deleteActivityProfile(
			@Valid @NotNull @RequestBody List<ActivityNotifyProfileDTO> activityNotifyProfiles) {

		List<APIResponse<ActivityNotifyProfileDTO>> response;
		AtomicInteger errorCount = new AtomicInteger();
		if (activityNotifyProfiles != null && !activityNotifyProfiles.isEmpty()) {
			response = activityNotifyProfiles.stream().map(activityNotifyProfileDTO -> {
				APIResponse<ActivityNotifyProfileDTO> singleDtoDelResponse;
				try {
					specialActivityNotifyProfileService.deleteActivityNotifyProfile(ActivityNotifyProfileMapper.INSTANCE
							.activityNotifyProfileDTOtoActivityNotifyProfile(activityNotifyProfileDTO));
					singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"),
							activityNotifyProfileDTO, ResponseStatusCode.SUCCESS);
				} catch (Exception e) {
					errorCount.incrementAndGet();
					singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
				}
				return singleDtoDelResponse;
			}).collect(Collectors.toList());
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
