package com.nscorp.obis.controller;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RestController;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.CorporateCustomer;
import com.nscorp.obis.dto.CorporateCustomerDTO;
import com.nscorp.obis.dto.mapper.CorporateCustomerMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.CorporateCustomerMaintenanceService;

@RestController
@CrossOrigin
@RequestMapping("/")
public class CorporateCustomerMaintenanceController {
    @Autowired
    CorporateCustomerMaintenanceService corporateCustomerMaintenanceService;

    @GetMapping(value = ControllerConstants.CORPORATE_CUSTOMER_MAINTENANCE)
    public ResponseEntity<APIResponse<List<CorporateCustomerDTO>>> getCorporateCustomers(){
        try {
            List<CorporateCustomerDTO> corporateCustomerDtoList = Collections.emptyList();
            List<CorporateCustomer> corporateCustomerList = corporateCustomerMaintenanceService.getCorporateCustomerData();
            if (corporateCustomerList != null && !corporateCustomerList.isEmpty()) {
                corporateCustomerDtoList = corporateCustomerList.stream()
                        .map(CorporateCustomerMapper.INSTANCE::corporateCustomerToCorporateCustomerDTO)
                        .collect(Collectors.toList());
            }
            APIResponse<List<CorporateCustomerDTO>> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"),corporateCustomerDtoList, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (NoRecordsFoundException e){
            APIResponse<List<CorporateCustomerDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        }
        catch (Exception e){
            APIResponse<List<CorporateCustomerDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }
    
    @PutMapping(value = ControllerConstants.CORPORATE_CUSTOMER_MAINTENANCE)
	public ResponseEntity<APIResponse<CorporateCustomerDTO>> updateCorporateCustomer(@Valid @RequestBody CorporateCustomerDTO corporateCustomerDTO,
			@RequestHeader Map<String, String> headers) {
		try {
			CorporateCustomer corporateCustomer = CorporateCustomerMapper.INSTANCE.corporateCustomerDTOToCorporateCustomer(corporateCustomerDTO);
			CorporateCustomer addcorporateCustomer = corporateCustomerMaintenanceService.updateCorporateCustomer(corporateCustomer, headers);
			CorporateCustomerDTO addedcorporateCustomerDto = CorporateCustomerMapper.INSTANCE.corporateCustomerToCorporateCustomerDTO(addcorporateCustomer);
			APIResponse<CorporateCustomerDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),
					addedcorporateCustomerDto, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<CorporateCustomerDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (RecordAlreadyExistsException e) {
			APIResponse<CorporateCustomerDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (Exception e) {
			APIResponse<CorporateCustomerDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
    @DeleteMapping(value = ControllerConstants.CORPORATE_CUSTOMER_MAINTENANCE)
	public ResponseEntity<List<APIResponse<CorporateCustomerDTO>>> deleteCorporateCustomerData(
			@RequestBody List<CorporateCustomerDTO> dtoListToBeDeleted) {
    	List<APIResponse<CorporateCustomerDTO>> response;
		AtomicInteger errorCount = new AtomicInteger();
		if (dtoListToBeDeleted != null && !dtoListToBeDeleted.isEmpty()) {
			response = dtoListToBeDeleted.stream().map(dto -> {
				APIResponse<CorporateCustomerDTO> singleDtoDelResponse;
				try {
					
					corporateCustomerMaintenanceService.deleteCorporateCustomerData(
							CorporateCustomerMapper.INSTANCE.corporateCustomerDTOToCorporateCustomer(dto));
					singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"), dto,
							ResponseStatusCode.SUCCESS);
				} catch (Exception e) {
					errorCount.incrementAndGet();
					singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
				}
				return singleDtoDelResponse;
			}).collect(Collectors.toList());
		} else {
			response = Collections.emptyList();
		}
		if (errorCount.get() == 0 && response.size() > 0) { // No errors and at least 1 success
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else if (response.size() > errorCount.get()) { // Partial success
			return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(response);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
    
    

    @PostMapping(value= ControllerConstants.CORPORATE_CUSTOMER_MAINTENANCE)
    public ResponseEntity<APIResponse<CorporateCustomerDTO>> addCustomer(@Valid @RequestBody CorporateCustomerDTO CorporateCustomerDto, @RequestHeader Map<String,String> headers) {
            try {
            	CorporateCustomer corporateCustomer = CorporateCustomerMapper.INSTANCE.corporateCustomerDTOToCorporateCustomer(CorporateCustomerDto);
            	System.out.println(corporateCustomer.toString());
            	CorporateCustomer corporateCustomerAdded = corporateCustomerMaintenanceService.addCorporateCustomer(corporateCustomer, headers);
            	CorporateCustomerDTO addCustomer = CorporateCustomerMapper.INSTANCE.corporateCustomerToCorporateCustomerDTO(corporateCustomerAdded);
                APIResponse<CorporateCustomerDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),addCustomer, ResponseStatusCode.SUCCESS);
                return ResponseEntity.status(HttpStatus.OK).body(responseObj);
                
            }catch (NoRecordsFoundException e){
            		APIResponse<CorporateCustomerDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            		return ResponseEntity.status(HttpStatus.OK).body(responseObj);
            }catch (RecordAlreadyExistsException e) {
           
            		APIResponse<CorporateCustomerDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
            		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
            }catch (Exception e){
            	
            		APIResponse<CorporateCustomerDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }

    
    
    
}

