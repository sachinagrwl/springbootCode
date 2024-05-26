package com.nscorp.obis.controller;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.nscorp.obis.dto.MoneyReceivedResponseDTO;
import com.nscorp.obis.response.data.PaginationWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.MoneyReceived;
import com.nscorp.obis.domain.SortFilter;
import com.nscorp.obis.dto.MoneyReceivedDTO;
import com.nscorp.obis.dto.mapper.MoneyReceivedMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.MoneyReceivedService;

@Validated
@RestController
@CrossOrigin
@RequestMapping(ControllerConstants.MONEY_RCD)
public class MoneyReceivedController {

	@Autowired
	MoneyReceivedService moneyReceivedService;

	@GetMapping
	public ResponseEntity<APIResponse<MoneyReceivedResponseDTO>> getAllTables(
			@RequestParam(name = "term-id", required = true) Long termId,
			@RequestParam(name = "cust-id", required = false) Long customerId,
			@RequestParam(name = "equip-init",required = false) String equipInit,
			@RequestParam(name = "equip-nbr",required = false) Integer equipNbr,
			@RequestParam(name = "supressTerm",required = false) String  termChkInd,
			@RequestParam(name = "supressFinal",required = false) String moneyChkInd,
			@RequestParam int pageNumber,
			@RequestParam int pageSize,
			@RequestParam(required = false,defaultValue = "equipInit:asc,equipNbr:asc,paidDtTm:desc") String[] sort) {

		try {
			Pageable pageable=PageRequest.of(pageNumber==0?pageNumber:pageNumber-1, pageSize, Sort.by(SortFilter.sortOrder(sort)));
			Page<MoneyReceived> moneyViews = moneyReceivedService.getMoneyReceived(termId, customerId,equipInit,equipNbr,termChkInd,moneyChkInd,
					pageable);

			List<MoneyReceivedDTO> moneyReceivedDTOList = Collections.emptyList();
			List<MoneyReceived> moneyReceived = moneyViews.getContent();
			List<MoneyReceived> moneyReceivedFinalList = new ArrayList<>();
			for(MoneyReceived moneyReceived1:moneyReceived){
				moneyReceived1.setPaidDtTmStr(moneyReceived1.getPaidDtTm().toString());
				moneyReceivedFinalList.add(moneyReceived1);
			}

			if (moneyReceivedFinalList != null && !moneyReceivedFinalList.isEmpty()) {
				moneyReceivedDTOList = moneyReceivedFinalList.stream()
						.map(MoneyReceivedMapper.INSTANCE::moneyReceivedTomoneyReceivedDTO)
						.collect(Collectors.toList());
			}
			PaginationWrapper paginationWrapper = new PaginationWrapper(moneyReceivedDTOList, moneyViews.getNumber() + 1,
					moneyViews.getTotalPages(), moneyViews.getTotalElements());
			MoneyReceivedResponseDTO response = new MoneyReceivedResponseDTO();
			response.setMoneyReceivedList(paginationWrapper);
			APIResponse <MoneyReceivedResponseDTO> responseObj = new APIResponse<>(
					Arrays.asList("Successfully retrieved data!"), response, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<MoneyReceivedResponseDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (Exception e) {
			APIResponse<MoneyReceivedResponseDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}

	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<APIResponse<MoneyReceivedDTO>> addPayment(
			@Valid @NotNull @RequestBody MoneyReceivedDTO moneyReceivedDTO,
			@RequestHeader Map<String, String> headers) {
		try {
			MoneyReceived moneyReceived = moneyReceivedService.addPayment(
					MoneyReceivedMapper.INSTANCE.moneyReceivedDTOTomoneyReceived(moneyReceivedDTO), headers);
			APIResponse<MoneyReceivedDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),
					MoneyReceivedMapper.INSTANCE.moneyReceivedTomoneyReceivedDTO(moneyReceived),
					ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (InvalidDataException e) {
			APIResponse<MoneyReceivedDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (RecordAlreadyExistsException e) {
			APIResponse<MoneyReceivedDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (NullPointerException e) {
			APIResponse<MoneyReceivedDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (Exception e) {
			APIResponse<MoneyReceivedDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<APIResponse<MoneyReceivedDTO>> updatePayment(
			@Valid @NotNull @RequestBody MoneyReceivedDTO moneyReceivedDTO,
			@RequestHeader Map<String, String> headers) {
		try {
			MoneyReceived moneyReceived = moneyReceivedService.updatePayment(
					MoneyReceivedMapper.INSTANCE.moneyReceivedDTOTomoneyReceived(moneyReceivedDTO), headers);
			APIResponse<MoneyReceivedDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),
					MoneyReceivedMapper.INSTANCE.moneyReceivedTomoneyReceivedDTO(moneyReceived),
					ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (InvalidDataException e) {
			APIResponse<MoneyReceivedDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<MoneyReceivedDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (NullPointerException e) {
			APIResponse<MoneyReceivedDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (Exception e) {
			APIResponse<MoneyReceivedDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
}
