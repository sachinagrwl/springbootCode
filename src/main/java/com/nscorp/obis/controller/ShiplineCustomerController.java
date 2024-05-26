package com.nscorp.obis.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.ShiplineCustomer;
import com.nscorp.obis.dto.ShiplineCustomerDTO;
import com.nscorp.obis.dto.mapper.ShiplineCustomerMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.ShiplineCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class ShiplineCustomerController {
	@Autowired
	ShiplineCustomerService steamshipCustomerService;
	
	@GetMapping(value= ControllerConstants.STEAMSHIP_CUSTOMERS)
	public ResponseEntity<APIResponse<List<ShiplineCustomerDTO>>> getAllSteamshipCustomer(){
		
		try {
			List<ShiplineCustomerDTO> steamshipCustomerDtoList = Collections.emptyList();
			List<ShiplineCustomer> steamshipCustomerList = steamshipCustomerService.getAllSteamshipCustomers();
			if (steamshipCustomerList != null && !steamshipCustomerList.isEmpty()) {
				steamshipCustomerDtoList = steamshipCustomerList.stream()
						.map(ShiplineCustomerMapper.INSTANCE::steamshipCustomerToSteamshipCustomerDTO)
						.collect(Collectors.toList());
			}
			APIResponse<List<ShiplineCustomerDTO>> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"),steamshipCustomerDtoList, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<List<ShiplineCustomerDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e){
			APIResponse<List<ShiplineCustomerDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	@PostMapping(value= ControllerConstants.STEAMSHIP_CUSTOMERS)
	public ResponseEntity<APIResponse<ShiplineCustomerDTO>> addSteamshipCustomer(@Valid @NotNull @RequestBody ShiplineCustomerDTO steamshipCustomerDto, @RequestHeader Map<String,String> headers) {
		try {
			ShiplineCustomer steamshipCustomer = ShiplineCustomerMapper.INSTANCE.steamshipCustomerDTOToSteamshipCustomer(steamshipCustomerDto);
			ShiplineCustomer steamshipCustomerAdded = steamshipCustomerService.addSteamshipCustomer(steamshipCustomer, headers);
			ShiplineCustomerDTO addedsteamshipCustomer = ShiplineCustomerMapper.INSTANCE.steamshipCustomerToSteamshipCustomerDTO(steamshipCustomerAdded);
			APIResponse<ShiplineCustomerDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),addedsteamshipCustomer, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<ShiplineCustomerDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (SizeExceedException e){
			APIResponse<ShiplineCustomerDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (RecordAlreadyExistsException e){
			APIResponse<ShiplineCustomerDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (RecordNotAddedException e){
			APIResponse<ShiplineCustomerDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (NullPointerException e){
			APIResponse<ShiplineCustomerDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (Exception e){
			APIResponse<ShiplineCustomerDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	
	@DeleteMapping(value= ControllerConstants.STEAMSHIP_CUSTOMERS)
	public ResponseEntity<List<APIResponse<ShiplineCustomerDTO>>> deleteSteamshipCustomer(@Valid @RequestBody List<ShiplineCustomerDTO> steamshipCustomerObjList){
		List<APIResponse<ShiplineCustomerDTO>> response;
		AtomicInteger errorCount = new AtomicInteger();
		if (steamshipCustomerObjList != null && !steamshipCustomerObjList.isEmpty()) {
				response = steamshipCustomerObjList.stream().map( steamshipCustomerObjDto -> {
					APIResponse<ShiplineCustomerDTO> singleDtoDelResponse;
					try {
						steamshipCustomerService.deleteCustomer(ShiplineCustomerMapper.INSTANCE.steamshipCustomerDTOToSteamshipCustomer(steamshipCustomerObjDto));
						singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"),steamshipCustomerObjDto, ResponseStatusCode.SUCCESS);
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
