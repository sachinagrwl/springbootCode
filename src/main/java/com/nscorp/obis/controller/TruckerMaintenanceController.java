package com.nscorp.obis.controller;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.RecordNotAddedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.common.NullOrNotBlank;
import com.nscorp.obis.domain.CustomerNickname;
import com.nscorp.obis.domain.DrayageCompany;
import com.nscorp.obis.domain.DrayageCustomer;
import com.nscorp.obis.domain.DrayageCustomerInfo;
import com.nscorp.obis.dto.CustomerScacDTO;
import com.nscorp.obis.dto.DrayageCompanyDTO;
import com.nscorp.obis.dto.DrayageCustomerDTO;
import com.nscorp.obis.dto.DrayageCustomerInfoDTO;
import com.nscorp.obis.dto.DrayageCustomerListDTO;
import com.nscorp.obis.dto.mapper.CustomerNicknameMapper;
import com.nscorp.obis.dto.mapper.DrayageCompanyMapper;
import com.nscorp.obis.dto.mapper.DrayageCustomerInfoMapper;
import com.nscorp.obis.dto.mapper.DrayageCustomerMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.TruckerMaintenanceService;

@RestController
@Validated
@RequestMapping("/")
public class TruckerMaintenanceController {
    @Autowired
    TruckerMaintenanceService service;

    @Autowired
    DrayageCustomerMapper drayageCustomerMapper;


	@Autowired
	DrayageCustomerInfoMapper drayageCustomerInfoMapper;

	@Autowired
	DrayageCompanyMapper drayageCompanyMapper;

    @GetMapping(value = ControllerConstants.DRAYAGE_CUSTOMER)
    public ResponseEntity<APIResponse<List<DrayageCustomerDTO>>> getDrayageCustomers(
            @Digits(integer = CommonConstants.CUST_ID_MAX_SIZE, fraction = 0, message = "Customer id length cannot be more than 15") @Min(value = 1, message = "Customer id must be greater than 0") @RequestParam(name = "customerId", required = false) Long customerId,
            @NullOrNotBlank(min = 1, max = 4, message = "Drayage Id should not be empty and length should be between 1 and 4.") @RequestParam(name = "drayageId", required = false) String drayageId)
            throws SQLException {
        try {
            List<DrayageCustomerDTO> drayageCustomerDTO = Collections.emptyList();
            List<DrayageCustomer> drayageCustomers = service.fetchDrayageCustomers(customerId, drayageId);
            if (drayageCustomers != null && !drayageCustomers.isEmpty()) {
                drayageCustomerDTO = drayageCustomers.stream()
                        .map(drayageCustomerMapper.INSTANCE::drayageCustomerToDrayageCustomerDTO)
                        .collect(Collectors.toList());
            }
            APIResponse<List<DrayageCustomerDTO>> responseObj = new APIResponse<>(
                    Arrays.asList("Successfully retrieved data!"), drayageCustomerDTO, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (NoRecordsFoundException e) {
            APIResponse<List<DrayageCustomerDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (Exception e) {
            APIResponse<List<DrayageCustomerDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }

    @GetMapping(value = ControllerConstants.DRAYAGE_COMPANY)
    public ResponseEntity<APIResponse<List<DrayageCompanyDTO>>> getDrayageCompany(
            @NullOrNotBlank(min = 1, max = 4, message = "Drayage Id should not be empty and length should be between 1 and 4.") @RequestParam(name = "drayageId", required = true) String drayageId)
            throws SQLException {
        try {
            List<DrayageCompanyDTO> drayageCompanyDTO = Collections.emptyList();
            List<DrayageCompany> drayageCompany = service.fetchDrayageCompany(drayageId);
            if (drayageCompany != null && !drayageCompany.isEmpty()) {
                drayageCompanyDTO = drayageCompany.stream()
                        .map(drayageCompanyMapper.INSTANCE::drayageCompanyToDrayageCompanyDTO)
                        .collect(Collectors.toList());
            }
            APIResponse<List<DrayageCompanyDTO>> responseObj = new APIResponse<>(
                    Arrays.asList("Successfully retrieved data!"), drayageCompanyDTO, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (NoRecordsFoundException e) {
            APIResponse<List<DrayageCompanyDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (Exception e) {
            APIResponse<List<DrayageCompanyDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }

    @DeleteMapping(value = ControllerConstants.DRAYAGE_CUSTOMER)
    public ResponseEntity<APIResponse<List<DrayageCustomerInfoDTO>>> deleteDrayageCustomer(
            @NotEmpty(message = "request body should not be empty") @Valid @RequestBody List<DrayageCustomerInfoDTO> drayageCustomerInfoDTO) throws SQLException {
        try {
            APIResponse<List<DrayageCustomerInfoDTO>> responseObj = new APIResponse<>(
                    Arrays.asList("The SCAC - Customer link is removed successfully!"),
                    service.deleteDrayageCustomerInfo(drayageCustomerInfoDTO), ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (NoRecordsFoundException e) {
            APIResponse<List<DrayageCustomerInfoDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (Exception e) {
            APIResponse<List<DrayageCustomerInfoDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }

	@PostMapping(value = ControllerConstants.DRAYAGE_CUSTOMER)
	public ResponseEntity<APIResponse<DrayageCustomerInfoDTO>> addDrayageCustomerInfo(
			@RequestParam(name = "override", required = false, defaultValue ="N") String override,
		@Valid @RequestBody DrayageCustomerInfoDTO DrayageCustomerInfoDTO,@RequestHeader Map<String,String> headers) {

		try {
			DrayageCustomerInfo drayageCustomerInfo = drayageCustomerInfoMapper.INSTANCE.drayageCustomerInfoDtoToDrayageCustomerInfo(DrayageCustomerInfoDTO);
			DrayageCustomerInfo added = service.addDrayageCustomer(drayageCustomerInfo,headers,override);
			DrayageCustomerInfoDTO addedDTO = drayageCustomerInfoMapper.INSTANCE.drayageCustomerInfoToDrayageCustomerInfoDTO(added);

			APIResponse<DrayageCustomerInfoDTO> response = new APIResponse<>(Arrays.asList("Sucessfully added data !"),
					addedDTO, ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			return ResponseEntity.status(HttpStatus.OK).body(response);

		} catch (RecordAlreadyExistsException e) {
			APIResponse<DrayageCustomerInfoDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<DrayageCustomerInfoDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (RecordNotAddedException e) {
			APIResponse<DrayageCustomerInfoDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<DrayageCustomerInfoDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}

	}
    @JsonIgnoreProperties(ignoreUnknown = true)
    @PostMapping(value = ControllerConstants.DRAYAGE_COMPANY, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<DrayageCompanyDTO>> addDrayageCompany(@Valid @NotNull @RequestBody DrayageCompanyDTO drayageCompanyDTO, @RequestHeader Map<String, String> headers) {

        try {
            DrayageCompany drayageCompany = DrayageCompanyMapper.INSTANCE.drayageCompanyDtoToDrayageCompany(drayageCompanyDTO);
            DrayageCompany drayageCompanyAdded = service.addDrayageCompany(drayageCompany, headers);
            DrayageCompanyDTO addedDrayageCompanyDTO = DrayageCompanyMapper.INSTANCE.drayageCompanyToDrayageCompanyDTO(drayageCompanyAdded);
            APIResponse<DrayageCompanyDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"), addedDrayageCompanyDTO, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (NoRecordsFoundException e) {
            APIResponse<DrayageCompanyDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (RecordNotAddedException e) {
            APIResponse<DrayageCompanyDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
        } catch (InvalidDataException e) {
            APIResponse<DrayageCompanyDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
        } catch (Exception e) {
            APIResponse<DrayageCompanyDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    @PutMapping(value = ControllerConstants.DRAYAGE_COMPANY, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<DrayageCompanyDTO>> putDrayageCompany(@Valid @NotNull @RequestBody DrayageCompanyDTO drayageCompanyDTO, @RequestHeader Map<String, String> headers) {

        try {
            DrayageCompany drayageCompany = DrayageCompanyMapper.INSTANCE.drayageCompanyDtoToDrayageCompany(drayageCompanyDTO);
            DrayageCompany updatedDrayage = service.updateDrayageCompany(drayageCompany, headers);
            DrayageCompanyDTO updatedDrayageCompanyDTO = DrayageCompanyMapper.INSTANCE.drayageCompanyToDrayageCompanyDTO(updatedDrayage);
            APIResponse<DrayageCompanyDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"), updatedDrayageCompanyDTO, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (NoRecordsFoundException e) {
            APIResponse<DrayageCompanyDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (RecordNotAddedException e) {
            APIResponse<DrayageCompanyDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
        } catch (InvalidDataException e) {
            APIResponse<DrayageCompanyDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
        } catch (Exception e) {
            APIResponse<DrayageCompanyDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    @PutMapping(value = ControllerConstants.DRAYAGE_CUSTOMER, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<APIResponse<DrayageCustomerInfoDTO>>> addCustomersToDrayage(
    		@Valid @NotNull @RequestBody DrayageCustomerListDTO drayageCustomerListDTO,
    		@RequestHeader Map<String, String> headers ) {
    		
    			List<APIResponse<DrayageCustomerInfoDTO>> response;
    			AtomicInteger errorCount = new AtomicInteger();
    			String drayageId = drayageCustomerListDTO.getDrayageId();
    			if (drayageCustomerListDTO.getDrayageCustomerList() != null && !drayageCustomerListDTO.getDrayageCustomerList().isEmpty()) {

    				response = drayageCustomerListDTO.getDrayageCustomerList().stream().map(drayageCustomer -> {
    					
    					APIResponse<DrayageCustomerInfoDTO> responseObj;
    					try{
    						DrayageCustomerInfo drayageCustomerInfo = DrayageCustomerInfoMapper.INSTANCE
    							.drayageCustomerInfoDtoToDrayageCustomerInfo(drayageCustomer);
    						
    						DrayageCustomerInfo addedDrayageCustomer = service.addDrayageCustomerAndRemoveLink(drayageCustomerInfo, drayageId, headers);
    							
    						DrayageCustomerInfoDTO addedDrayageCustomerDto = DrayageCustomerInfoMapper.INSTANCE
    								.drayageCustomerInfoToDrayageCustomerInfoDTO(addedDrayageCustomer);
    						
    						responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"), addedDrayageCustomerDto,ResponseStatusCode.SUCCESS);
    					
    					}catch (Exception e) {
    						errorCount.incrementAndGet();
    						responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
    					}
    					return responseObj;

    				}).collect(Collectors.toList());

    			} 
    			else {
    				throw new InvalidDataException("Drayage Customer List can't be empty.");
    				// response = Collections.emptyList();
    			}

    			if (errorCount.get() == 0 && response.size() > 0) { // No errors and atleast 1 success
    				return ResponseEntity.status(HttpStatus.OK).body(response);
    			} else if (response.size() > errorCount.get()) { // Partial success
    				return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(response);
    			} else {
    				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    			}

    	}

    
	@GetMapping(value = ControllerConstants.DRAYAGE_CUSTOMER_PRIMARY_SIX)
	public ResponseEntity<APIResponse<List<CustomerScacDTO>>> getDrayageCustomersByPrimarySix(
			@Size(min = 6, max = 6, message = "primary six length should be equal to 6.") @RequestParam(name = "primarysix", required = true) String customerPrimarySix)
			throws SQLException {
		try {

			APIResponse<List<CustomerScacDTO>> responseObj = new APIResponse<>(
					Arrays.asList("Successfully retrieved data!"),
					service.fetchDrayageCustomersByPrimarySix(customerPrimarySix), ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<List<CustomerScacDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<CustomerScacDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

}
