package com.nscorp.obis.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.EMSIngateAllocation;
import com.nscorp.obis.domain.SortFilter;
import com.nscorp.obis.domain.Station;
import com.nscorp.obis.dto.EMSIngateAllocationDTO;
import com.nscorp.obis.dto.mapper.EMSIngateAllocationMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.PaginationWrapper;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.EMSIngateAllocationService;
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
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ControllerConstants.EMS_INGATE_ALLOCATION)
@Validated
public class EMSIngateAllocationController {

	@Autowired
	EMSIngateAllocationService emsIngateAllocationService;
	
	@GetMapping
	public ResponseEntity<APIResponse<PaginationWrapper>> searchAllocations(
			@Digits(integer = 15, fraction = 0, message = "Ingate Terminal Id should be less than 15") @RequestParam(value = "ingateTerminalId", required = false) Long ingateTerminalId,
			@RequestParam(value = "onlineOriginStationTermId", required = false) Station onlineOriginStation,
			@RequestParam(value = "onlineDestinationStationTermId", required = false) Station onlineDestinationStation,
			@RequestParam(value = "offlineDestinationStationTermId", required = false) Station offlineDestinationStation,
			@Digits(integer = 15, fraction = 0, message = "Cooperate Customer Id should be less than 15") @RequestParam(value = "corporateCustomerId", required = false) Long corporateCustomerId,
			@RequestParam(value = "lineOfBusinesses", required = false) List<String> lineOfBusinesses,
			@Size(max = 110, message = "WayBill Route should be less than 110") @RequestParam(value = "wayBillRoute", required = false) String wayBillRoute,
			@RequestParam(value = "trafficTypes", required = false) List<String> trafficTypes,
			@RequestParam(value = "startDate", required = false) Date startDate,
			@RequestParam(value = "endDate", required = false) Date endDate,
			@RequestParam(value = "include-inactive", required = false) Boolean includeInactive,
			@RequestParam(value = "include-expired", required = false) Boolean includeExpired,
			@RequestParam(value = "include-permanent", required = false) Boolean includePermanent,
			@RequestParam int pageNumber, // Page number
			@RequestParam int pageSize,
			@RequestParam(defaultValue = "ingateTerminalId,asc") String[] sort) { // How many records per page

		try {
			List<String> list = Arrays.asList(sort);
			if(list.contains("onlineOriginStationTermId") && sort[1].equals("asc")){
				sort = new String[1];
				sort[0] = "onlineOriginStation.stationName,asc";
			} else if(list.contains("onlineOriginStationTermId") && sort[1].equals("desc")){
				sort = new String[1];
				sort[0] = "onlineOriginStation.stationName,desc";
			}

			if(list.contains("ingateTerminalId") && sort[1].equals("asc")){
				sort = new String[1];
				sort[0] = "terminal.terminalName,asc";
			} else if(list.contains("ingateTerminalId") && sort[1].equals("desc")){
				sort = new String[1];
				sort[0] = "terminal.terminalName,desc";
			}

			if(list.contains("onlineDestinationStationTermId") && sort[1].equals("asc")){
				sort = new String[1];
				sort[0] = "station.stationName,asc";
			} else if(list.contains("onlineDestinationStationTermId") && sort[1].equals("desc")){
				sort = new String[1];
				sort[0] = "station.stationName,desc";
			}

			if(list.contains("offlineDestinationStationTermId") && sort[1].equals("asc")){
				sort = new String[1];
				sort[0] = "station1.stationName,asc";
			} else if(list.contains("offlineDestinationStationTermId") && sort[1].equals("desc")){
				sort = new String[1];
				sort[0] = "station1.stationName,desc";
			}

			if(list.contains("corporateCustomerId") && sort[1].equals("asc")){
				sort = new String[1];
				sort[0] = "corporateCustomer.corporateLongName,asc";
			} else if(list.contains("corporateCustomerId") && sort[1].equals("desc")){
				sort = new String[1];
				sort[0] = "corporateCustomer.corporateLongName,desc";
			}

			if(list.contains("lineOfBusinesses")){
				for(int i=0;i<sort.length;i++){
					if(sort[i].equals("lineOfBusinesses") && sort[i+1].equals("desc")){
						sort = new String[3];
						sort[i] = "premium,desc";
						sort[i+1] = "international,desc";
						sort[i+2] = "domestic,desc";
						break;
					}
					else if(sort[i].equals("lineOfBusinesses") && sort[i+1].equals("asc")){
						sort = new String[3];
						sort[i] = "premium,asc";
						sort[i+1] = "international,asc";
						sort[i+2] = "domestic,asc";
						break;
					}
				}
			}

			if(list.contains("trafficTypes")){
				for(int i=0;i<sort.length;i++){
					if(sort[i].equals("trafficTypes") && sort[i+1].equals("desc")){
						sort = new String[3];
						sort[i] = "steelWheel,desc";
						sort[i+1] = "rubberTire,desc";
						sort[i+2] = "local,desc";
						break;
					}
					else if(sort[i].equals("trafficTypes") && sort[i+1].equals("asc")){
						sort = new String[3];
						sort[i] = "steelWheel,asc";
						sort[i+1] = "rubberTire,asc";
						sort[i+2] = "local,asc";
						break;
					}
				}
			}

//			Date currentDate = null;
			LocalDate currentDate = null;
			LocalDate st_date = null;
			LocalDate end_date = null;
			if(startDate != null) {
				st_date = startDate.toLocalDate();
			}
			if(endDate != null) {
				end_date = endDate.toLocalDate();
			}
			
			Page<EMSIngateAllocation> allocations = emsIngateAllocationService.searchIngateAllocation(ingateTerminalId,
					onlineOriginStation, onlineDestinationStation, offlineDestinationStation,
					corporateCustomerId, lineOfBusinesses, wayBillRoute, trafficTypes, st_date, end_date, currentDate,
					includeInactive, includeExpired, includePermanent,
					PageRequest.of(pageNumber - 1, pageSize, Sort.by(SortFilter.sortOrder(sort))));

			List<EMSIngateAllocationDTO> searchResponseList = Collections.emptyList();
			List<EMSIngateAllocation> allocations1 = allocations.getContent();

			if (allocations1 != null && !allocations1.isEmpty()) {
				searchResponseList = allocations1.stream()
						.map(EMSIngateAllocationMapper.INSTANCE::emsIngateAllocationToEMSIngateAllocationDTO)
						.collect(Collectors.toList());
			}
//			if(list.contains("corporateCustomerId") && list.contains("desc")) {
//				Collections.reverse(searchResponseList);
//			}
			PaginationWrapper paginationWrapper = new PaginationWrapper(searchResponseList, allocations.getNumber() + 1,
					allocations.getTotalPages(), allocations.getTotalElements());
			APIResponse<PaginationWrapper> responseObj = new APIResponse<>(
					Arrays.asList("Successfully retrieved data!"), paginationWrapper, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (DataAccessException | QueryParameterException exception) {
			APIResponse<PaginationWrapper> responseObj = new APIResponse<>(
					Arrays.asList("Request Parameter is incorrect!"), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (SizeExceedException e) {
			APIResponse<PaginationWrapper> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<PaginationWrapper> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<PaginationWrapper> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	@PostMapping
	public ResponseEntity<APIResponse<EMSIngateAllocationDTO>> addAllocation(@Valid @RequestBody EMSIngateAllocationDTO emsObjDto, @RequestHeader Map<String,String> headers){

    	try {
			EMSIngateAllocation allocations = EMSIngateAllocationMapper.INSTANCE.emsIngateAllocationDTOToEMSIngateAllocation(emsObjDto);
			EMSIngateAllocation addedAllocation = emsIngateAllocationService.insertAllocation(allocations, headers);
			EMSIngateAllocationDTO addedAllocationDto = EMSIngateAllocationMapper.INSTANCE.emsIngateAllocationToEMSIngateAllocationDTO(addedAllocation);
			APIResponse<EMSIngateAllocationDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),addedAllocationDto, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<EMSIngateAllocationDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (SizeExceedException e){
			APIResponse<EMSIngateAllocationDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (RecordAlreadyExistsException e){
			APIResponse<EMSIngateAllocationDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (RecordNotAddedException e){
			APIResponse<EMSIngateAllocationDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (NullPointerException e){
			APIResponse<EMSIngateAllocationDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (Exception e){
			APIResponse<EMSIngateAllocationDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@PutMapping
	public ResponseEntity<APIResponse<EMSIngateAllocationDTO>> updateRestriction(@Valid @RequestBody EMSIngateAllocationDTO emsIngateAllocationDTO, @RequestHeader Map<String,String> headers) {
		try {
			EMSIngateAllocation allocation = EMSIngateAllocationMapper.INSTANCE.emsIngateAllocationDTOToEMSIngateAllocation(emsIngateAllocationDTO);
			EMSIngateAllocation updateAllocation = emsIngateAllocationService.updateAllocation(allocation,headers);
			EMSIngateAllocationDTO updatedAllocationDTO = EMSIngateAllocationMapper.INSTANCE.emsIngateAllocationToEMSIngateAllocationDTO(updateAllocation);
			APIResponse<EMSIngateAllocationDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),updatedAllocationDTO, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<EMSIngateAllocationDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (RecordAlreadyExistsException e){
			APIResponse<EMSIngateAllocationDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (RecordNotAddedException e){
			APIResponse<EMSIngateAllocationDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (NullPointerException e){
			APIResponse<EMSIngateAllocationDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (SizeExceedException e){
			APIResponse<EMSIngateAllocationDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (Exception e){
			if (e.getCause() instanceof javax.persistence.OptimisticLockException){
				APIResponse<EMSIngateAllocationDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE_OPTIMISTICLOCK);
				return ResponseEntity.status(HttpStatus.CONFLICT).body(responseObj);
			}
			APIResponse<EMSIngateAllocationDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

}
