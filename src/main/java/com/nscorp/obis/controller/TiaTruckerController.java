package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.nscorp.obis.domain.Trucker;
import com.nscorp.obis.dto.mapper.TruckerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.dto.TruckerDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.TiaTruckerService;

@Validated
@RestController
@RequestMapping("/")
public class TiaTruckerController {

	@Autowired
	TiaTruckerService tiaTruckerService;
	
	@GetMapping(ControllerConstants.GET_TIA_TKR)
	public ResponseEntity<APIResponse<List<TruckerDTO>>> getTruckerNameByTruckerCode(
		@RequestParam(name="tkr-cd") String truckerCode){
		try {
			List<TruckerDTO> truckerDtoList = Collections.emptyList();
			List<Trucker> truckerList = tiaTruckerService.getTruckerNameByTruckerCode(truckerCode.toUpperCase());
			if (truckerList != null && !truckerList.isEmpty()) {
				truckerDtoList = truckerList.stream().map(TruckerMapper.INSTANCE::TruckerToTruckerDTO).collect(Collectors.toList());
			}
			APIResponse<List<TruckerDTO>> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"), truckerDtoList, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		}catch (NoRecordsFoundException e ){
			APIResponse<List<TruckerDTO>> responseObj = new APIResponse<>(Arrays.asList("No Records Found"), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		}catch (Exception e){
			APIResponse<List<TruckerDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
}
