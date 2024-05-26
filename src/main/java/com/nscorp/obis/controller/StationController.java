package com.nscorp.obis.controller;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.SortFilter;
import com.nscorp.obis.domain.Station;
import com.nscorp.obis.dto.StationDTO;
import com.nscorp.obis.dto.mapper.StationMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.PaginationWrapper;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.StationService;
import org.hibernate.QueryParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ControllerConstants.URI + "/stations")
@Validated
public class StationController {

    @Autowired
    StationService stationCrossReferenceService;

	@GetMapping
    public ResponseEntity<APIResponse<PaginationWrapper>> searchStations(@Size(max = 19, message = "Station Name size should be less than 19") @RequestParam(value = "stationName", required = false) String stationName,
												 @Size(max = 2, message = "State size should be less than 2") @RequestParam(value = "state", required = false) String state,
												 @Size(max = 4, message = "Road Number size should be less than 4") @RequestParam(value = "roadNumber", required = false) String roadNumber,
												 @Size(max = 6, message = "FSAC size should be less than 6") @RequestParam(value = "FSAC", required = false) String FSAC,
												 @Size(max = 6, message = "BillAtFSAC size should be less than 6") @RequestParam(value = "billAtFsac", required = false) String billAtFsac,
												 @Size(max = 4, message = "Road Name size should be less than 4") @RequestParam(value = "roadName", required = false) String roadName,
												 @Size(max = 5, message = "Operation Station size should be less than 5") @RequestParam(value = "operationStation", required = false) String operationStation,
												 @Size(max = 9, message = "SPLC size should be less than 9") @RequestParam(value = "splc", required = false) String splc,
												 @Size(max = 5, message = "Rule260Station size should be less than 5") @RequestParam(value = "rule260Station", required = false) String rule260Station,
												 @Size(max = 1, message = "Intermodal Indicator size should be less than 1") @RequestParam(value = "intermodalIndicator", required = false) String intermodalIndicator,
												 @Size(max = 5, message = "Char5Spell size should be less than 5") @RequestParam(value = "char5Spell", required = false) String char5Spell,
												 @Size(max = 5, message = "Char5Alias size should be less than 5") @RequestParam(value = "char5Alias", required = false) String char5Alias,
												 @Size(max = 8, message = "Char8Spell size should be less than 8") @RequestParam(value = "char8Spell", required = false) String char8Spell,
												 @Size(max = 2, message = "Division size should be less than 2") @RequestParam(value = "division", required = false) String division,
												 @RequestParam(value = "expirationDate", required = false) Date expirationDate,
												 @RequestParam int pageNumber, //Page number
												 @RequestParam int pageSize,
												 @RequestParam(defaultValue = "stationName,asc") String[] sort){ // How many records per page

		try {

			Page<Station> stations = stationCrossReferenceService.searchStations(stationName, roadNumber,FSAC, state ,billAtFsac,roadName,operationStation,
					splc,rule260Station,intermodalIndicator,char5Spell,char5Alias,char8Spell,division,expirationDate,PageRequest.of(pageNumber-1,pageSize, Sort.by(SortFilter.sortOrder(sort))));


			List<StationDTO> searchResponseList = Collections.emptyList();
			List<Station> stations1 = stations.getContent();

			if(stations1 != null && !stations1.isEmpty()){
				searchResponseList = stations1.stream()
						.map(StationMapper.INSTANCE::stationToStationDTO)
						.collect(Collectors.toList());
			}

			PaginationWrapper paginationWrapper = new PaginationWrapper(searchResponseList, stations.getNumber()+1, stations.getTotalPages(),stations.getTotalElements());
			APIResponse<PaginationWrapper> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"), paginationWrapper, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		}
		catch (DataAccessException | QueryParameterException exception){
			APIResponse<PaginationWrapper> responseObj = new APIResponse<>(Arrays.asList("Request Parameter is incorrect!"), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		}
		catch (SizeExceedException e){
			APIResponse<PaginationWrapper> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		}
		catch (NoRecordsFoundException e){
			APIResponse<PaginationWrapper> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		}
		catch (Exception e){
			APIResponse<PaginationWrapper> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
    }



	@PutMapping
	public ResponseEntity<APIResponse<StationDTO>> updateStation(@Valid @RequestBody StationDTO stationObjDto, @RequestHeader Map<String,String> headers) {
		try {
			Station stationObj = StationMapper.INSTANCE.stationDTOToStation(stationObjDto);
			Station updatedStation = stationCrossReferenceService.updateStation(stationObj, headers);
			StationDTO updatedStationDto = StationMapper.INSTANCE.stationToStationDTO(updatedStation);
			APIResponse<StationDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),updatedStationDto, ResponseStatusCode.SUCCESS);

			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<StationDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (NullPointerException e){
			APIResponse<StationDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (SizeExceedException e){
			APIResponse<StationDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (RecordNotAddedException e){
			APIResponse<StationDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (Exception e){
			APIResponse<StationDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);			
		}
	}

}

