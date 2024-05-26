package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.UmlerConventionalCar;
import com.nscorp.obis.dto.UmlerConventionalCarDTO;
import com.nscorp.obis.dto.mapper.UmlerConventionalCarMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.ConventionalLoadCapabilitiesService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = ControllerConstants.CONV_LOAD_CPBLTS)
@Validated
public class ConventionalLoadCapabilitiesController {
	
	
	@Autowired
	ConventionalLoadCapabilitiesService convLoadService;
	
	@GetMapping
	public ResponseEntity<APIResponse<List<UmlerConventionalCarDTO>>> getAllConvLoad(
			@RequestParam(required = false, name = "aarType") String aarType,
			@RequestParam(required = false, name = "carInit") String carInit) {
		
		try {
			List<UmlerConventionalCarDTO> convLoadDtoList = Collections.emptyList();
            List<UmlerConventionalCar> convLoadList = convLoadService.getConvLoad(aarType, carInit);
            if (convLoadList != null && !convLoadList.isEmpty()) {
            	convLoadDtoList = convLoadList.stream().map(UmlerConventionalCarMapper.INSTANCE::UmlerConventionalCarToUmlerConventionalCarDTO)
                        .collect(Collectors.toList());
		}
            APIResponse<List<UmlerConventionalCarDTO>> responseObj = new APIResponse<>(Arrays.asList(CommonConstants.RETRIVED_SUCCESS_MESSAGE),
            		convLoadDtoList, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);

        } catch (NoRecordsFoundException e) {
            APIResponse<List<UmlerConventionalCarDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (Exception e) {
            APIResponse<List<UmlerConventionalCarDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
		
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@PostMapping
	public ResponseEntity<APIResponse<UmlerConventionalCarDTO>> addConventionalCar(@Valid @RequestBody UmlerConventionalCarDTO convCarDto, @RequestHeader Map<String,String> headers){
		try {
			UmlerConventionalCar convCar = UmlerConventionalCarMapper.INSTANCE.UmlerConventionalCarDTOToUmlerConventionalCar(convCarDto);

			UmlerConventionalCar addConvCar = convLoadService.addConventionalCar(convCar, headers);
			UmlerConventionalCarDTO addConvCarDto = UmlerConventionalCarMapper.INSTANCE.UmlerConventionalCarToUmlerConventionalCarDTO(addConvCar);
			APIResponse<UmlerConventionalCarDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),addConvCarDto, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<UmlerConventionalCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (SizeExceedException e){
			APIResponse<UmlerConventionalCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (RecordAlreadyExistsException e){
			APIResponse<UmlerConventionalCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (RecordNotAddedException e){
			APIResponse<UmlerConventionalCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (NullPointerException e){
			APIResponse<UmlerConventionalCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (Exception e){
			APIResponse<UmlerConventionalCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@PutMapping
	public ResponseEntity<APIResponse<UmlerConventionalCarDTO>> updateConventionalCar(@Valid @RequestBody UmlerConventionalCarDTO convCarDto, @RequestHeader Map<String,String> headers) {

		try {
			UmlerConventionalCar convCar = UmlerConventionalCarMapper.INSTANCE.UmlerConventionalCarDTOToUmlerConventionalCar(convCarDto);
			UmlerConventionalCar updatedConvCar = convLoadService.updateConventionalCar(convCar, headers);
			UmlerConventionalCarDTO updatedConvCarDto = UmlerConventionalCarMapper.INSTANCE.UmlerConventionalCarToUmlerConventionalCarDTO(updatedConvCar);
			APIResponse<UmlerConventionalCarDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),updatedConvCarDto, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<UmlerConventionalCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (RecordNotAddedException e){
			APIResponse<UmlerConventionalCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (NullPointerException e){
			APIResponse<UmlerConventionalCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (SizeExceedException e){
			APIResponse<UmlerConventionalCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (Exception e){
			APIResponse<UmlerConventionalCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}


	@DeleteMapping
	public ResponseEntity<List<APIResponse<UmlerConventionalCarDTO>>> deleteConventionalCar(
			@RequestBody List<UmlerConventionalCarDTO> umlerConventionalCarDTOList) {
		List<APIResponse<UmlerConventionalCarDTO>> responseDTOList;
		AtomicInteger errorCount = new AtomicInteger();
		if (!CollectionUtils.isEmpty(umlerConventionalCarDTOList)) {
			responseDTOList = umlerConventionalCarDTOList.stream().map(umlerConventionalCarDTO -> {
				APIResponse<UmlerConventionalCarDTO> singleDtoDelResponse;
				try {
					UmlerConventionalCar umlerConventionalCar = convLoadService
							.deleteConvLoad(UmlerConventionalCarMapper.INSTANCE
									.UmlerConventionalCarDTOToUmlerConventionalCar(umlerConventionalCarDTO));
					singleDtoDelResponse = new APIResponse<>(
							Arrays.asList(CommonConstants.DELETE_SUCCESS_MESSAGE), UmlerConventionalCarMapper.INSTANCE
							.UmlerConventionalCarToUmlerConventionalCarDTO(umlerConventionalCar),
							ResponseStatusCode.SUCCESS);
				} catch (NoRecordsFoundException e) {
					errorCount.incrementAndGet();
					singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
				} catch (Exception e) {
					errorCount.incrementAndGet();
					singleDtoDelResponse = new APIResponse<>(
							Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
				}
				return singleDtoDelResponse;
			}).collect(Collectors.toList());
		} else {
			responseDTOList = Collections.emptyList();
		}

		if (errorCount.get() == 0 && responseDTOList.size() > 0) // No errors and at least 1 success
			return ResponseEntity.status(HttpStatus.OK).body(responseDTOList);
		else if (responseDTOList.size() > errorCount.get()) // Partial success
			return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(responseDTOList);
		else
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTOList);
	}

}
