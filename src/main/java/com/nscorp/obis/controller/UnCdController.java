package com.nscorp.obis.controller;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.UnCd;
import com.nscorp.obis.dto.UnCdDTO;
import com.nscorp.obis.dto.mapper.UnCdMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.UnCdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Validated
@RestController
@CrossOrigin
@RequestMapping("/")
public class UnCdController {

	@Autowired(required = true)
	UnCdService unCdService;

	@GetMapping(value = ControllerConstants.Un_Cd)
	public ResponseEntity<APIResponse<List<UnCdDTO>>> getAllTables(
			@RequestParam(name = "un-cd", required = false) String unCd) {

		try {
			List<UnCdDTO> unCdDtoList = Collections.emptyList();
			List<UnCd> unCdList = unCdService.getAllTables(unCd);
			if (unCdList != null && !unCdList.isEmpty()) {
				unCdDtoList = unCdList.stream().map(UnCdMapper.INSTANCE::UnCdToUnCdDTO).collect(Collectors.toList());
			}
			APIResponse<List<UnCdDTO>> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"),
					unCdDtoList, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<List<UnCdDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<UnCdDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}

	}

	@DeleteMapping(value = ControllerConstants.Un_Cd)
	public ResponseEntity<List<APIResponse<UnCdDTO>>> deleteUnCode(@RequestBody List<UnCdDTO> dtoListToBeDeleted) {
		List<APIResponse<UnCdDTO>> response;
		AtomicInteger errorCount = new AtomicInteger();
		if (dtoListToBeDeleted != null && !dtoListToBeDeleted.isEmpty()) {
			response = dtoListToBeDeleted.stream().map(dto -> {
				APIResponse<UnCdDTO> singleDtoDelResponse;
				try {
					UnCd unCd = unCdService.deleteUnCode(UnCdMapper.INSTANCE.UnCdDTOToUnCd(dto));
					singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"),
							UnCdMapper.INSTANCE.UnCdToUnCdDTO(unCd), ResponseStatusCode.SUCCESS);
				} catch (InvalidDataException e) {
					errorCount.incrementAndGet();
					singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
				} catch (Exception e) {
					errorCount.incrementAndGet();
					singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
				}
				return singleDtoDelResponse;
			}).collect(Collectors.toList());
		} else {
			response = Collections.emptyList();
		}
		if (errorCount.get() == 0 && response.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else if (response.size() > errorCount.get()) {
			return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(response);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PutMapping(value = ControllerConstants.Un_Cd)
	public ResponseEntity<APIResponse<UnCdDTO>> updateUnCdDesc(@Valid @NotNull @RequestBody UnCdDTO unCdDTO,
			@RequestHeader Map<String, String> headers) {
		try {
			UnCd unCdObj = unCdService.updateUnDesc(UnCdMapper.INSTANCE.UnCdDTOToUnCd(unCdDTO), headers);
			APIResponse<UnCdDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),
					UnCdMapper.INSTANCE.UnCdToUnCdDTO(unCdObj), ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<UnCdDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (RecordNotAddedException e) {
			APIResponse<UnCdDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (Exception e) {
			APIResponse<UnCdDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@PostMapping(value = ControllerConstants.Un_Cd)
	public ResponseEntity<APIResponse<UnCdDTO>> addUnCode(@Valid @NotNull @RequestBody UnCdDTO unCdObj,
			@RequestHeader Map<String, String> headers) {
		try {
			UnCd unCd = UnCdMapper.INSTANCE.UnCdDTOToUnCd(unCdObj);
			UnCd unCdAdd = unCdService.addUnCode(unCd, headers);
			UnCdDTO termDayDto = UnCdMapper.INSTANCE.UnCdToUnCdDTO(unCdAdd);
			APIResponse<UnCdDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"), termDayDto,
					ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<UnCdDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (RecordNotAddedException e) {
			APIResponse<UnCdDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (RecordAlreadyExistsException e) {
			APIResponse<UnCdDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (Exception e) {
			APIResponse<UnCdDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}

	}
}
