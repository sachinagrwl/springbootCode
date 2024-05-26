package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.AARHitch;
import com.nscorp.obis.dto.AARHitchDTO;
import com.nscorp.obis.dto.mapper.AARHitchMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.AARHitchService;

@Validated
@RestController
@RequestMapping(ControllerConstants.AAR_HITCH)
public class AARHitchController {
	
	@Autowired
	AARHitchService aarHitchService;
	
	@GetMapping
    public ResponseEntity<APIResponse<List<AARHitchDTO>>> getAllHitch(@Size(max = 4, message = "'aarType' size should not be greater than {max}") @RequestParam(required = false, name = "aarType") String aarType,
    		@Size(max = 2, message = "'hitchLocation' size should not be greater than {max}") @RequestParam(required = false, name = "hitchLocation") String hitchLocation) {
		
		try {
			
			List<AARHitchDTO> aarHitchDTOList = Collections.emptyList();
			List<AARHitch> aarHitchList = aarHitchService.getAllHitch(aarType, hitchLocation);
			if(aarHitchList != null && !aarHitchList.isEmpty()){
				aarHitchDTOList = aarHitchList.stream()
                        .map(AARHitchMapper.INSTANCE::AARHitchToAARHitchDTO)
                        .collect(Collectors.toList());
            }
			APIResponse<List<AARHitchDTO>> listAPIResponse = new APIResponse<>(Arrays.asList("Successfully retrieved data!"),aarHitchDTOList, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(listAPIResponse);
		} catch (NoRecordsFoundException e){
            APIResponse<List<AARHitchDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (Exception e){
            APIResponse<List<AARHitchDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
		
	}

}
