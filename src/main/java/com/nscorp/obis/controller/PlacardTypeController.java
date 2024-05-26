package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.nscorp.obis.domain.PlacardType;
import com.nscorp.obis.dto.PlacardTypeDTO;
import com.nscorp.obis.dto.mapper.PlacardTypeMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.PlacardTypeService;

@RestController
@CrossOrigin
@RequestMapping("/")
public class PlacardTypeController {
	
	@Autowired
	PlacardTypeService placardTypeService;

	@Autowired
	PlacardTypeMapper placardTypeMapper;
	
	@GetMapping(value = ControllerConstants.PLACARD_TP)
	public ResponseEntity<APIResponse<List<PlacardTypeDTO>>> getPlacardType(@RequestParam(name = "placard-cd", required=false) String placardCd){
		try {
			List<PlacardTypeDTO> placardTypeDto = Collections.emptyList();
			List<PlacardType> placardType = placardTypeService.getAllPlacard(placardCd);
			if (placardType != null && !placardType.isEmpty()) {
				placardTypeDto = placardType.stream()
						.map(PlacardTypeMapper.INSTANCE::placardTypeToPlacardTypeDTO)
						.collect(Collectors.toList());
			}
			APIResponse<List<PlacardTypeDTO>> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"), placardTypeDto, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		}catch (NoRecordsFoundException e ){
			APIResponse<List<PlacardTypeDTO>> responseObj = new APIResponse<>(Arrays.asList("No Records Found"), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		}catch (Exception e){
			APIResponse<List<PlacardTypeDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@PostMapping(value = ControllerConstants.PLACARD_TP)
	public ResponseEntity<APIResponse<PlacardTypeDTO>> addPlacardType(@Valid @RequestBody PlacardTypeDTO placardTypeDTOObj, @RequestHeader Map<String,String> headers) {

		try {
			PlacardType placardType = PlacardTypeMapper.INSTANCE.placardTypeDtoToPlacardType(placardTypeDTOObj);
			PlacardType AddplacardType = placardTypeService.addPlacard(placardType, headers);
			PlacardTypeDTO AddplacardTypeDTO = PlacardTypeMapper.INSTANCE.placardTypeToPlacardTypeDTO(AddplacardType);
			APIResponse<PlacardTypeDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully Added data!"), AddplacardTypeDTO, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<PlacardTypeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (NullPointerException e){
			APIResponse<PlacardTypeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (SizeExceedException e){
			APIResponse<PlacardTypeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (RecordNotAddedException e){
			APIResponse<PlacardTypeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (Exception e){
			APIResponse<PlacardTypeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
	
	@PutMapping(value = ControllerConstants.PLACARD_TP)
	public ResponseEntity<APIResponse<PlacardTypeDTO>> updatePlacardType(@Valid @RequestBody PlacardTypeDTO placardTypeDTOObj, @RequestHeader Map<String,String> headers) {

		try {
			PlacardType placardType = PlacardTypeMapper.INSTANCE.placardTypeDtoToPlacardType(placardTypeDTOObj);
			PlacardType updateplacardType = placardTypeService.updatePlacard(placardType, headers);
			PlacardTypeDTO UpdateplacardTypeDTO = PlacardTypeMapper.INSTANCE.placardTypeToPlacardTypeDTO(updateplacardType);
			APIResponse<PlacardTypeDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully Updated data!"), UpdateplacardTypeDTO, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<PlacardTypeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (NullPointerException e){
			APIResponse<PlacardTypeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (SizeExceedException e){
			APIResponse<PlacardTypeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (RecordNotAddedException e){
			APIResponse<PlacardTypeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (Exception e){
			APIResponse<PlacardTypeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
	
	@DeleteMapping(value = ControllerConstants.PLACARD_TP)
	public ResponseEntity<List<APIResponse<PlacardTypeDTO>>> deletePlacardType(
			@Valid @RequestBody List<PlacardTypeDTO> PlacardTypeDto) {
		List<APIResponse<PlacardTypeDTO>> response;
		AtomicInteger errorCount = new AtomicInteger();

		if (PlacardTypeDto != null && !PlacardTypeDto.isEmpty()) {
			response = PlacardTypeDto.stream().map(tableObjDto -> {
				APIResponse<PlacardTypeDTO> singleDtoDelResponse;
				try {
					placardTypeService.deletePlacardType(PlacardTypeMapper.INSTANCE.placardTypeDtoToPlacardType(tableObjDto));
					singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"), tableObjDto,
							ResponseStatusCode.SUCCESS);
				} catch (ConstraintViolationException e) {	       
					errorCount.incrementAndGet();
					singleDtoDelResponse = new APIResponse<>((Arrays.asList("\"OPTCS.MISSION_PLACARD.FK_MISSION_PLACARD_PLACARD_TP\" restricts the deletion. SQLSTATE=23504")),
							ResponseStatusCode.FAILURE);
				} catch (Exception e) {
					errorCount.incrementAndGet();
					//singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
					singleDtoDelResponse = new APIResponse<>(Arrays.asList("\"OPTCS.MISSION_PLACARD.FK_MISSION_PLACARD_PLACARD_TP\" restricts the deletion. SQLSTATE=23504"), ResponseStatusCode.FAILURE);
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
