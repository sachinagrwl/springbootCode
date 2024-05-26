package com.nscorp.obis.controller;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.AARWhyMadeCodes;
import com.nscorp.obis.dto.AARWhyMadeCodesDTO;
import com.nscorp.obis.dto.mapper.AARWhyMadeCodesMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.AARWhyMadeCodesService;
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

@RestController
@RequestMapping("/")
@Validated
public class AARWhyMadeCodesController {

	@Autowired
	AARWhyMadeCodesService aarWhyMadeCodesService;

	@GetMapping(value = ControllerConstants.AAR_WHY_MADE_CODE)
	public ResponseEntity<APIResponse<List<AARWhyMadeCodesDTO>>> getAllAARWhyMadeCodes() {

		try {
			List<AARWhyMadeCodesDTO> aarWhyMadeCodesDTOList = Collections.emptyList();
			List<AARWhyMadeCodes> aarWhyMadeCodes = aarWhyMadeCodesService.getAARWhyMadeCodes();
			if (aarWhyMadeCodes != null && !aarWhyMadeCodes.isEmpty()) {
				aarWhyMadeCodesDTOList = aarWhyMadeCodes.stream()
						.map(AARWhyMadeCodesMapper.INSTANCE::aarWhyMadeCodesToAARWhyMadeCodesDTO)
						.collect(Collectors.toList());
			}
			APIResponse<List<AARWhyMadeCodesDTO>> responseObj = new APIResponse<>(
					Arrays.asList("Successfully retrieved data!"), aarWhyMadeCodesDTOList, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<List<AARWhyMadeCodesDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<AARWhyMadeCodesDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}

	}
	
	@PutMapping(value = ControllerConstants.AAR_WHY_MADE_CODE)
	public ResponseEntity<APIResponse<AARWhyMadeCodesDTO>> updateAARWhyMadeCodes(@Valid @RequestBody AARWhyMadeCodesDTO aarWhyMadeCodesDto,
			@RequestHeader Map<String, String> headers) {
		try {
			AARWhyMadeCodes aarWhyMadeCodes = AARWhyMadeCodesMapper.INSTANCE.aarWhyMadeCodesDTOToAARWhyMadeCodes(aarWhyMadeCodesDto);
			AARWhyMadeCodes updateAARWhyMadeCodes = aarWhyMadeCodesService.updateAARWhyMadeCodes(aarWhyMadeCodes, headers);
			AARWhyMadeCodesDTO updatedAARWhyMadeCodesDto = AARWhyMadeCodesMapper.INSTANCE.aarWhyMadeCodesToAARWhyMadeCodesDTO(updateAARWhyMadeCodes);
			APIResponse<AARWhyMadeCodesDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),
					updatedAARWhyMadeCodesDto, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<AARWhyMadeCodesDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (RecordAlreadyExistsException e) {
			APIResponse<AARWhyMadeCodesDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (Exception e) {
			APIResponse<AARWhyMadeCodesDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
	
	@DeleteMapping(value= ControllerConstants.AAR_WHY_MADE_CODE) 
	  public ResponseEntity<List<APIResponse<AARWhyMadeCodesDTO>>> deleteAARWhyMadeCodes(@Valid @RequestBody List<AARWhyMadeCodesDTO> aarWhyMadeCodesDTO){
		List<APIResponse<AARWhyMadeCodesDTO>> response;
		AtomicInteger errorCount = new AtomicInteger();
		
		if (aarWhyMadeCodesDTO != null && !aarWhyMadeCodesDTO.isEmpty()) {
			response = aarWhyMadeCodesDTO.stream().map(tableObjDto -> {
				APIResponse<AARWhyMadeCodesDTO> singleDtoDelResponse;
				try {
					aarWhyMadeCodesService.deleteAARWhyMadeCodes(AARWhyMadeCodesMapper.INSTANCE.aarWhyMadeCodesDTOToAARWhyMadeCodes(tableObjDto));
					singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"),tableObjDto, ResponseStatusCode.SUCCESS);
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

	@PostMapping(value = ControllerConstants.AAR_WHY_MADE_CODE)
	public ResponseEntity<APIResponse<AARWhyMadeCodesDTO>> addAARWhyMadeCodes(@Valid @NotNull @RequestBody AARWhyMadeCodesDTO aarWhyMadeCodesDTOObj,
														  @RequestHeader Map<String, String> headers) {
		try {

			AARWhyMadeCodes aarWhyMadeCodes = AARWhyMadeCodesMapper.INSTANCE.aarWhyMadeCodesDTOToAARWhyMadeCodes(aarWhyMadeCodesDTOObj);
			AARWhyMadeCodes addAARWhyMadeCodes = aarWhyMadeCodesService.addAARWhyMadeCodes(aarWhyMadeCodes, headers);
			AARWhyMadeCodesDTO addedAARWhyMadeCodesDto = AARWhyMadeCodesMapper.INSTANCE.aarWhyMadeCodesToAARWhyMadeCodesDTO(addAARWhyMadeCodes);

			APIResponse<AARWhyMadeCodesDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"), addedAARWhyMadeCodesDto,
					ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<AARWhyMadeCodesDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (RecordNotAddedException e) {
			APIResponse<AARWhyMadeCodesDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (RecordAlreadyExistsException e) {
			APIResponse<AARWhyMadeCodesDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (Exception e) {
			APIResponse<AARWhyMadeCodesDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}

	}
}
