package com.nscorp.obis.controller;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.StationRestriction;
import com.nscorp.obis.dto.StationRestrictionDTO;
import com.nscorp.obis.dto.mapper.StationRestrictionMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.StationRestrictionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
public class StationRestrictionController {

	@Autowired
	StationRestrictionService stationRestrictionService;

	@GetMapping(value= ControllerConstants.STATION_RESTRICTIONS, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Fetch all the values")
    public ResponseEntity<APIResponse<List<StationRestrictionDTO>>> getStationRestrictions(@PathVariable("term-id") Long termId){

        try {
			List<StationRestrictionDTO> stationRestrictionDTOList = Collections.emptyList();
			List<StationRestriction> stationRestrictionList = stationRestrictionService.getStationRestriction(termId);

			if(stationRestrictionList != null && !stationRestrictionList.isEmpty()){
				stationRestrictionDTOList = stationRestrictionList.stream()
			            .map(StationRestrictionMapper.INSTANCE::stationRestrictionToStationRestrictionDTO)
			            .collect(Collectors.toList());
			}

			APIResponse<List<StationRestrictionDTO>> responseObject = new APIResponse<>(Arrays.asList("Successfully Retrived Data"),stationRestrictionDTOList, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObject);
		} catch (NoRecordsFoundException e){
			APIResponse<List<StationRestrictionDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		}
		catch (Exception e){
			APIResponse<List<StationRestrictionDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
    }
    
    @PostMapping(value = ControllerConstants.STATION_RESTRICTIONS, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <APIResponse<StationRestrictionDTO>> addStationRestrictions(@PathVariable("term-id") Long termId, @Valid @NotNull @RequestBody StationRestrictionDTO stationRestrictionsObj, @RequestHeader Map<String,String> headers){
    	try {
			StationRestriction stationRestrictions = StationRestrictionMapper.INSTANCE.stationRestrictionDTOToStationRestriction(stationRestrictionsObj);
			StationRestriction addedStationRestrictions = stationRestrictionService.addStationRestriction(termId, stationRestrictions, headers);
			StationRestrictionDTO addedStationRestrictionsDTO = StationRestrictionMapper.INSTANCE.stationRestrictionToStationRestrictionDTO(addedStationRestrictions);
			APIResponse<StationRestrictionDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),addedStationRestrictionsDTO, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<StationRestrictionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (SizeExceedException e){
			APIResponse<StationRestrictionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (RecordAlreadyExistsException e){
			APIResponse<StationRestrictionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (RecordNotAddedException e){
			APIResponse<StationRestrictionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (NullPointerException e){
			APIResponse<StationRestrictionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (Exception e){
			APIResponse<StationRestrictionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
		
	}
    
    @DeleteMapping(value = ControllerConstants.STATION_RESTRICTIONS)
	public ResponseEntity<List<APIResponse<StationRestrictionDTO>>> deleteStationRestrictions(@PathVariable("term-id") Long termId, @Valid @RequestBody List<StationRestrictionDTO> stationRestrictionsObj){
    	List<APIResponse<StationRestrictionDTO>> response;
		AtomicInteger errorCount = new AtomicInteger();
		if (stationRestrictionsObj != null && !stationRestrictionsObj.isEmpty()) {
				response = stationRestrictionsObj.stream().map( stationRestrictionsObjDto -> {
					APIResponse<StationRestrictionDTO> singleDtoDelResponse;
					try {
						stationRestrictionService.deleteStationRestriction(termId, StationRestrictionMapper.INSTANCE.stationRestrictionDTOToStationRestriction(stationRestrictionsObjDto));
						singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"),stationRestrictionsObjDto, ResponseStatusCode.SUCCESS);
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
