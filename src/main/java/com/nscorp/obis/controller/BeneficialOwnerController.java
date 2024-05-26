package com.nscorp.obis.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.nscorp.obis.exception.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.BeneficialOwner;
import com.nscorp.obis.dto.BeneficialOwnerDTO;
import com.nscorp.obis.dto.mapper.BeneficialOwnerMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.BeneficialOwnerService;

import lombok.extern.slf4j.Slf4j;

@Validated
@RestController
@RequestMapping("/")
@Slf4j
public class BeneficialOwnerController {

	@Autowired
	BeneficialOwnerService beneficialOwnerService;

	@PutMapping(value = ControllerConstants.BENEFICIAL_OWNER)
	ResponseEntity<APIResponse<BeneficialOwnerDTO>> updateBeneficialOwner(
			@Valid @RequestBody BeneficialOwnerDTO beneficialOwnerDTO, @RequestHeader Map<String, String> headers) throws SQLException {

		try {
			APIResponse<BeneficialOwnerDTO> responseObj = new APIResponse<>(
					Arrays.asList("Successfully updated data!"), beneficialOwnerService.updateBeneficialOwner(beneficialOwnerDTO, headers),
					ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);

		} catch (NoRecordsFoundException | InvalidDataException e) {
			APIResponse<BeneficialOwnerDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			HttpStatus status = HttpStatus.NOT_FOUND;
			if (e.getClass() == InvalidDataException.class)
				status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status).body(responseObj);
		} catch (Exception e) {
			APIResponse<BeneficialOwnerDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}


	@GetMapping(value = ControllerConstants.BENEFICIAL_OWNER)
	public ResponseEntity<APIResponse<List<BeneficialOwnerDTO>>> getBeneficialCustomer(
			@Valid @RequestParam(name = "bnfLongName", required = false) String bnfLongName,
			@Valid @RequestParam(name = "bnfShortName", required = false) String bnfShortName) throws SQLException {

		try {
			List<BeneficialOwnerDTO> beneficialOwnerDtoList = new ArrayList<>();

			List<BeneficialOwner> beneficialOwner = beneficialOwnerService.fetchBeneficialCustomer(bnfLongName,
					bnfShortName);
			if (beneficialOwner != null && !beneficialOwner.isEmpty()) {
				beneficialOwnerDtoList = beneficialOwner.stream()
						.map(BeneficialOwnerMapper.INSTANCE::beneficialOwnerToBeneficialOwnerDTO)
						.collect(Collectors.toList());
			}

			APIResponse<List<BeneficialOwnerDTO>> responseObj = new APIResponse<>(
					Arrays.asList("Successfully retrieved data!"), beneficialOwnerDtoList,
					ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);

		} catch (NoRecordsFoundException e) {
			APIResponse<List<BeneficialOwnerDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<BeneficialOwnerDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@PostMapping(value = ControllerConstants.BENEFICIAL_OWNER)
	public ResponseEntity<APIResponse<BeneficialOwnerDTO>> addBeneficialCustomer(
			@Valid @RequestBody BeneficialOwnerDTO beneficialOwnerDTO, @RequestHeader Map<String, String> headers)
			throws SQLException {

		try {
			log.info("addBeneficialCustomer : Method Starts");
			APIResponse<BeneficialOwnerDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),
					beneficialOwnerService.addBeneficialCustomer(beneficialOwnerDTO, headers),
					ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			log.info("addBeneficialCustomer : Method Ends");
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);

		} catch (NoRecordsFoundException | RecordAlreadyExistsException e) {
			APIResponse<BeneficialOwnerDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			HttpStatus status=HttpStatus.NOT_FOUND;
			if(e.getClass()==RecordAlreadyExistsException.class || e.getClass()==InvalidDataException.class) {
				status=HttpStatus.BAD_REQUEST;
			}
			log.error("addBeneficialCustomer : Error" + e.getMessage());
			return ResponseEntity.status(status).body(responseObj);
		} catch (Exception e) {
			APIResponse<BeneficialOwnerDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			log.error("addBeneficialCustomer : Error" + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@DeleteMapping(value = ControllerConstants.BENEFICIAL_OWNER)
    public ResponseEntity<List<APIResponse<BeneficialOwnerDTO>>> deleteBeneficialCustomer(
			@Valid @RequestBody List<BeneficialOwnerDTO> beneficialOwnerDTOList) {
        log.info("deleteBeneficialCustomer : Method Starts");
        List<APIResponse<BeneficialOwnerDTO>> response;
        AtomicInteger errorCount = new AtomicInteger();

        if (beneficialOwnerDTOList != null && !beneficialOwnerDTOList.isEmpty()) {
            response = beneficialOwnerDTOList.stream().map(tableObjDto -> {
                APIResponse<BeneficialOwnerDTO> singleDtoDelResponse;
                try {
                    BeneficialOwner deletedData = beneficialOwnerService.
                            deleteBeneficialCustomers(
                                    BeneficialOwnerMapper.INSTANCE.beneficialOwnerDTOToBeneficialOwner(tableObjDto));
                    BeneficialOwnerDTO deletedDataDTO = BeneficialOwnerMapper.INSTANCE.beneficialOwnerToBeneficialOwnerDTO(deletedData);

                    singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"), deletedDataDTO,
                            ResponseStatusCode.SUCCESS);
                } catch (InvalidDataException e) {
                    errorCount.incrementAndGet();
                    singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
                } catch (NoRecordsFoundException e) {
                    errorCount.incrementAndGet();
                    singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
                } catch (Exception e) {
                    errorCount.incrementAndGet();
                    singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
                }
                log.info("deleteBeneficialCustomer : Method Ends");
                return singleDtoDelResponse;
            }).collect(Collectors.toList());
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
