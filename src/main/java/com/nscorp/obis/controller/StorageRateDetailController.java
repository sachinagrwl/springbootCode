package com.nscorp.obis.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.StorageRates;
import com.nscorp.obis.dto.StorageRatesDTO;
import com.nscorp.obis.dto.mapper.StorageRateDetailMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.StorageRateDetailService;

@RestController
@CrossOrigin
@RequestMapping("/")
public class StorageRateDetailController {

	@Autowired
	StorageRateDetailService storageRateDetailService;
	
	@Autowired
	StorageRateDetailMapper storageRateDetailMapper;
	
	@GetMapping(ControllerConstants.STORAGE_RATES)
	public ResponseEntity<APIResponse<StorageRatesDTO>> getStorageRates(
			@RequestParam(name = "charge-id") Long chrgId){
		try {
			StorageRatesDTO storageRatesDto = new StorageRatesDTO();
			StorageRates storageRates = storageRateDetailService.getStorageRateDetail(chrgId);
			if (storageRatesDto != null) {
				storageRatesDto = StorageRateDetailMapper.INSTANCE.storageRatesToStorageRatesDTO(storageRates);
			}
			APIResponse<StorageRatesDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"), storageRatesDto, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		}catch (NoRecordsFoundException e ){
			APIResponse<StorageRatesDTO> responseObj = new APIResponse<>(Arrays.asList("No Records Found"), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		}catch (Exception e){
			APIResponse<StorageRatesDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
}
