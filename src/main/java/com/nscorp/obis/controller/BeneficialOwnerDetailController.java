package com.nscorp.obis.controller;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.nscorp.obis.dto.mapper.CustomerMapper;
import com.nscorp.obis.exception.InvalidDataException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.BeneficialOwnerDetail;
import com.nscorp.obis.dto.BeneficialOwnerDetailDTO;
import com.nscorp.obis.dto.mapper.BeneficialOwnerDetailMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.BeneficialOwnerDetailService;

@Validated
@RestController
@RequestMapping("/")
@Slf4j
public class BeneficialOwnerDetailController {

	@Autowired
	BeneficialOwnerDetailService beneficialOwnerDetailService;

	@GetMapping(value = ControllerConstants.BENEFICIAL_CUSTOMER_DETAILS_MAINTENANCE)
	public ResponseEntity<APIResponse<List<BeneficialOwnerDetailDTO>>> getBeneficialOwnerDetails(
			@Valid @RequestParam(name = "bnfCustId", required = false) Long bnfCustId,
			@Valid @RequestParam(name = "bnfOwnerNumber", required = false) String bnfOwnerNumber) throws SQLException {

		try {
			List<BeneficialOwnerDetailDTO> beneficialOwnerDetailDTOList = new ArrayList<>();

			List<BeneficialOwnerDetail> beneficialOwnerDetail = beneficialOwnerDetailService
					.fetchBeneficialOwnerDetails(bnfCustId, bnfOwnerNumber);

			if (beneficialOwnerDetail != null && !beneficialOwnerDetail.isEmpty()) {
				beneficialOwnerDetailDTOList = beneficialOwnerDetail.stream()
						.map(BeneficialOwnerDetailMapper.INSTANCE::beneficialOwnerDetailToBeneficialOwnerDetailDTO)
						.collect(Collectors.toList());
			}

			APIResponse<List<BeneficialOwnerDetailDTO>> responseObj = new APIResponse<>(
					Arrays.asList("Successfully retrieved data!"), beneficialOwnerDetailDTOList,
					ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);

		} catch (NoRecordsFoundException e) {
			APIResponse<List<BeneficialOwnerDetailDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<BeneficialOwnerDetailDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@DeleteMapping(value = ControllerConstants.BENEFICIAL_CUSTOMER_DETAILS_MAINTENANCE)
	public ResponseEntity<List<APIResponse<BeneficialOwnerDetailDTO>>> deleteBnfCustDtl(
			@RequestBody List<BeneficialOwnerDetailDTO> bnfCustDtlDtoList, @RequestHeader Map<String, String> headers) {
		log.info("deleteBnfCustDtl : Method Starts");
		List<APIResponse<BeneficialOwnerDetailDTO>> response;
		AtomicInteger errorCount = new AtomicInteger();

		if (bnfCustDtlDtoList != null && !bnfCustDtlDtoList.isEmpty()) {
			response = bnfCustDtlDtoList.stream().map(tableObjDto -> {
				APIResponse<BeneficialOwnerDetailDTO> singleDtoDelResponse;
				try {
					BeneficialOwnerDetail deletedData = beneficialOwnerDetailService
							.deleteBeneficialDetails(BeneficialOwnerDetailMapper.INSTANCE
									.beneficialOwnerDetailDTOToBeneficialOwnerDetail(tableObjDto), headers);
					BeneficialOwnerDetailDTO deletedDataDTO = BeneficialOwnerDetailMapper.INSTANCE
							.beneficialOwnerDetailToBeneficialOwnerDetailDTO(deletedData);

					singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"),
							deletedDataDTO, ResponseStatusCode.SUCCESS);
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
				log.info("deleteBnfCustDtl : Method Ends");
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

	@PostMapping(value = ControllerConstants.BENEFICIAL_CUSTOMER_DETAILS_MAINTENANCE)
	public ResponseEntity<APIResponse<BeneficialOwnerDetailDTO>> addBeneficialOwnerDetail(
			@Valid @RequestBody BeneficialOwnerDetailDTO beneficialOwnerDetail, @RequestHeader Map<String, String> headers)
			throws SQLException {
		try {
			APIResponse<BeneficialOwnerDetailDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),
					beneficialOwnerDetailService.addBeneficialOwnerDetail(beneficialOwnerDetail, headers),
					ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());// 200
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException | RecordAlreadyExistsException e) {

			APIResponse<BeneficialOwnerDetailDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());

			HttpStatus status = HttpStatus.NOT_FOUND; // 404
			if (e.getClass() == RecordAlreadyExistsException.class || e.getClass() == InvalidDataException.class) {
				status = HttpStatus.BAD_REQUEST; // 400
			}
			return ResponseEntity.status(status).body(responseObj);

		} catch (Exception e) {
			APIResponse<BeneficialOwnerDetailDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);// 500

		}

	}

}