package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.nscorp.obis.domain.CarEmbargo;
import com.nscorp.obis.dto.CarEmbargoDTO;
import com.nscorp.obis.dto.mapper.CarEmbargoMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.RecordNotAddedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.HazRestriction;
import com.nscorp.obis.dto.HazRestrictionDTO;
import com.nscorp.obis.dto.mapper.HazRestrictionMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.HazRestrictionService;

@RestController
@RequestMapping(value = ControllerConstants.HAZ_RESTRICTION)
@Validated
public class HazRestrictionController {

    @Autowired
    private HazRestrictionService hazRestrictionService;


    @PostMapping
    public ResponseEntity<APIResponse<HazRestrictionDTO>> insertHazRestriction(@Valid @RequestBody HazRestrictionDTO hazRestrictionDTO, @RequestHeader Map<String, String> headers) {
		try {
			HazRestriction hazRestriction = HazRestrictionMapper.INSTANCE.hazRestrictionDTOToHazRestriction(hazRestrictionDTO);
			HazRestriction addHazRestriction = hazRestrictionService.insertHazRestriction(hazRestriction, headers);
			HazRestrictionDTO addedhazRestrictionDTO = HazRestrictionMapper.INSTANCE.hazRestrictionToHazRestrictionDTO(addHazRestriction);

			APIResponse<HazRestrictionDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),
					addedhazRestrictionDTO, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<HazRestrictionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (RecordAlreadyExistsException e) {
			APIResponse<HazRestrictionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (Exception e) {
			APIResponse<HazRestrictionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

		@GetMapping
		public ResponseEntity<APIResponse<List<HazRestrictionDTO>>> getHazRestriction(

			@RequestParam(value = "un-cd", required = false) String unCd) {
			try {
			List<HazRestrictionDTO> hazRestrictionDtoList = Collections.emptyList();
			List<HazRestriction> hazRestrictionList = hazRestrictionService.getHazRestriction(unCd);
			if (hazRestrictionList != null && ! hazRestrictionList.isEmpty()) {
				hazRestrictionDtoList = hazRestrictionList.stream()
						.map(HazRestrictionMapper.INSTANCE::hazRestrictionToHazRestrictionDTO)
						.collect(Collectors.toList());
			}
			APIResponse<List<HazRestrictionDTO>> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"),hazRestrictionDtoList, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e ){
			APIResponse<List<HazRestrictionDTO>> responseObj = new APIResponse<>(Arrays.asList("No Records Found"), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		}catch (Exception e){
			APIResponse<List<HazRestrictionDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@DeleteMapping
	public ResponseEntity<List<APIResponse<HazRestrictionDTO>>> deleteHazRestricition(@Valid @RequestBody List<HazRestrictionDTO> hazRestrictionDTO){
		List<APIResponse<HazRestrictionDTO>> response;
		AtomicInteger errorCount = new AtomicInteger();

		if (hazRestrictionDTO != null && !hazRestrictionDTO.isEmpty()) {
			response = hazRestrictionDTO.stream().map(tableObjDto -> {
						APIResponse<HazRestrictionDTO> singleDtoDelResponse;
						try {
							hazRestrictionService.deleteHazRestriction(HazRestrictionMapper.INSTANCE.hazRestrictionDTOToHazRestriction(tableObjDto));
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

	@PutMapping
	public ResponseEntity<APIResponse<HazRestrictionDTO>> updateHazRestriction(@Valid @NotNull @RequestBody HazRestrictionDTO hazRestrictionDTO, @RequestHeader Map<String,String> headers) {
		try {
			HazRestriction hazRestriction = HazRestrictionMapper.INSTANCE.hazRestrictionDTOToHazRestriction(hazRestrictionDTO);
			HazRestriction hazRestrictionUpdate = hazRestrictionService.updateHazRestriction(hazRestriction, headers);
			HazRestrictionDTO updateHazRestriction = HazRestrictionMapper.INSTANCE.hazRestrictionToHazRestrictionDTO(hazRestrictionUpdate);
			APIResponse<HazRestrictionDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),updateHazRestriction, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<HazRestrictionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (InvalidDataException | RecordNotAddedException e) {
			APIResponse<HazRestrictionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		}  catch (NullPointerException e){
			APIResponse<HazRestrictionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (Exception e){
			APIResponse<HazRestrictionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

}

