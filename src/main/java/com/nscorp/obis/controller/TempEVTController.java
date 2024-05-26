package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.TempEVT;
import com.nscorp.obis.dto.TempEVTDTO;
import com.nscorp.obis.dto.mapper.TempEVTMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.TempEVTService;

@RestController
@RequestMapping("/")
public class TempEVTController {

	@Autowired
	TempEVTService tempEVTService;
	
	 @PostMapping(value = ControllerConstants.NOTIFY_ACTIVE_SHIPMENT)
		public ResponseEntity<APIResponse<TempEVTDTO>> addTempEVT(@Valid @RequestBody TempEVTDTO tempEVTDTOObj,
				@RequestHeader Map<String, String> headers) {
			try {
				TempEVT tempEVT = TempEVTMapper.INSTANCE.tempEVTDTOToTempEVT(tempEVTDTOObj);
				TempEVT addtempEVT = tempEVTService.addTempEVT(tempEVT, headers);
				TempEVTDTO addtempEVTDto = TempEVTMapper.INSTANCE.tempEVTToTempEVTDTO(addtempEVT);
				APIResponse<TempEVTDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),
						addtempEVTDto, ResponseStatusCode.SUCCESS);
				return ResponseEntity.status(HttpStatus.OK).body(responseObj);
			} catch (NoRecordsFoundException e) {
				APIResponse<TempEVTDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
						ResponseStatusCode.INFORMATION);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
			} catch (RecordAlreadyExistsException e) {
				APIResponse<TempEVTDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
						ResponseStatusCode.INFORMATION);
				return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
			} catch (Exception e) {
				
				APIResponse<TempEVTDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
						ResponseStatusCode.FAILURE);
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
			}
		}
}
