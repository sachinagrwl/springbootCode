package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.PayGuarantee;
import com.nscorp.obis.dto.PayGuaranteeDTO;
import com.nscorp.obis.dto.mapper.PayGuaranteeMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.PayGuaranteeService;

@Validated
@RestController
@CrossOrigin
@RequestMapping("/")
public class PayGuaranteeController {

	@Autowired
	PayGuaranteeService payGuaranteeService;

	@GetMapping(value = ControllerConstants.PAY_GUARANTEE)
	public ResponseEntity<APIResponse<PayGuaranteeDTO>> getAllTables(@RequestParam(name = "chrg-id") Long chrgId) {

		try {
			PayGuaranteeDTO payInfoDTO = new PayGuaranteeDTO();
			PayGuarantee pay = payGuaranteeService.getPayGuarantee(chrgId);
			if (pay != null) {
				payInfoDTO = PayGuaranteeMapper.INSTANCE
						.payGuaranteeToPayGuaranteeDTO(pay);
			}
			APIResponse<PayGuaranteeDTO> responseObj = new APIResponse<PayGuaranteeDTO>(
					Arrays.asList("Successfully retrieve data!"), payInfoDTO,
					ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);

		} catch (NoRecordsFoundException e) {
			APIResponse<PayGuaranteeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<PayGuaranteeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@PostMapping(value = ControllerConstants.PAY_GUARANTEE)
	public ResponseEntity<APIResponse<PayGuaranteeDTO>> addPayGuarantee(@Valid @RequestBody PayGuaranteeDTO payGuaranteeDTO,
																		   @RequestHeader Map<String, String> headers) {
		try {
			PayGuarantee payGuarantee = PayGuaranteeMapper.INSTANCE.payGuaranteeDTOToPayGuarantee(payGuaranteeDTO);
			PayGuarantee addPay = payGuaranteeService.addPayGuarantee(payGuarantee, headers);
			PayGuaranteeDTO payInfoDTO = PayGuaranteeMapper.INSTANCE.payGuaranteeToPayGuaranteeDTO(addPay);
			APIResponse<PayGuaranteeDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),
					payInfoDTO, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<PayGuaranteeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (RecordAlreadyExistsException e) {
			APIResponse<PayGuaranteeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (Exception e) {
			APIResponse<PayGuaranteeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@PutMapping(value = ControllerConstants.PAY_GUARANTEE)
	public ResponseEntity<APIResponse<PayGuaranteeDTO>> updatePayGuarantee(@Valid @RequestBody PayGuaranteeDTO payGuaranteeDTO,
																		@RequestHeader Map<String, String> headers) {
		try {
			PayGuarantee payGuarantee = PayGuaranteeMapper.INSTANCE.payGuaranteeDTOToPayGuarantee(payGuaranteeDTO);
			PayGuarantee addPay = payGuaranteeService.updatePayGuarantee(payGuarantee, headers);
			PayGuaranteeDTO payInfoDTO = PayGuaranteeMapper.INSTANCE.payGuaranteeToPayGuaranteeDTO(addPay);
			APIResponse<PayGuaranteeDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),
					payInfoDTO, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<PayGuaranteeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (RecordAlreadyExistsException e) {
			APIResponse<PayGuaranteeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (Exception e) {
			APIResponse<PayGuaranteeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

}
