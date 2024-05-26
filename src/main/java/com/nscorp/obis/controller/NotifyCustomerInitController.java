package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
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
import com.nscorp.obis.domain.NotifyCustomerInit;
import com.nscorp.obis.dto.NotifyCustomerInitDTO;
import com.nscorp.obis.dto.mapper.NotifyCustomerInitMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.NotifyCustomerInitService;

@Validated
@RestController
@RequestMapping("/")
public class NotifyCustomerInitController {

	@Autowired
	NotifyCustomerInitService notifyCustomerInitService;

	@GetMapping(value = ControllerConstants.NOTIFY_CUST_INIT)
	public ResponseEntity<APIResponse<List<NotifyCustomerInitDTO>>> getAllCustomerInitials(
			@RequestParam(required = false, name = "custId") Long custId) {

		try {
			List<NotifyCustomerInitDTO> cusInitDtoList = Collections.emptyList();
			List<NotifyCustomerInit> custInitList = notifyCustomerInitService.getCustomerInitials(custId);
			if (custInitList != null && !custInitList.isEmpty()) {
				cusInitDtoList = custInitList.stream()
						.map(NotifyCustomerInitMapper.INSTANCE::notifyCustomerInitToNotifyCustomerInitDTO)
						.collect(Collectors.toList());
			}
			APIResponse<List<NotifyCustomerInitDTO>> responseObj = new APIResponse<>(
					Arrays.asList(CommonConstants.RETRIVED_SUCCESS_MESSAGE), cusInitDtoList,
					ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<List<NotifyCustomerInitDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<NotifyCustomerInitDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}

	}

	@PostMapping(value = ControllerConstants.NOTIFY_CUST_INIT)
	public ResponseEntity<APIResponse<NotifyCustomerInitDTO>> addNotifyCustomerInit(
			@Valid @NotNull @RequestBody NotifyCustomerInitDTO notifyCustomerInitDTO,
			@RequestHeader Map<String, String> headers) {
		try {
			NotifyCustomerInit notifyCustomerInit = notifyCustomerInitService.addNotifyCustomerInit(
					NotifyCustomerInitMapper.INSTANCE.notifyCustomerInitDTOToNotifyCustomerInit(notifyCustomerInitDTO),
					headers);

			APIResponse<NotifyCustomerInitDTO> responseObj = new APIResponse<>(
					Arrays.asList(CommonConstants.ADD_SUCCESS_MESSAGE),
					NotifyCustomerInitMapper.INSTANCE.notifyCustomerInitToNotifyCustomerInitDTO(notifyCustomerInit),
					ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (RecordNotAddedException e) {
			APIResponse<NotifyCustomerInitDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (NullPointerException e) {
			APIResponse<NotifyCustomerInitDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (Exception e) {
			APIResponse<NotifyCustomerInitDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@DeleteMapping(value = ControllerConstants.NOTIFY_CUST_INIT)
	public ResponseEntity<APIResponse<NotifyCustomerInitDTO>> deleteNotifyCustomerInit(
			@Valid @NotNull @RequestBody NotifyCustomerInitDTO notifyCustomerInitDTO) {
		try {
			NotifyCustomerInit notifyCustomerInit = notifyCustomerInitService.deleteNotifyCustomerInit(
					NotifyCustomerInitMapper.INSTANCE.notifyCustomerInitDTOToNotifyCustomerInit(notifyCustomerInitDTO));

			APIResponse<NotifyCustomerInitDTO> responseObj = new APIResponse<>(
					Arrays.asList(CommonConstants.DELETE_SUCCESS_MESSAGE),
					NotifyCustomerInitMapper.INSTANCE.notifyCustomerInitToNotifyCustomerInitDTO(notifyCustomerInit),
					ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<NotifyCustomerInitDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (NullPointerException e) {
			APIResponse<NotifyCustomerInitDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (Exception e) {
			APIResponse<NotifyCustomerInitDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@PutMapping(value = ControllerConstants.NOTIFY_CUST_INIT)
	public ResponseEntity<APIResponse<List<NotifyCustomerInitDTO>>> updateNotifyCustomerInit(
			@Valid @NotNull @RequestBody NotifyCustomerInitDTO notifyCustomerInitDTO,
			@RequestHeader Map<String, String> headers) {
		try {
			List<NotifyCustomerInitDTO> cusInitDtoList = Collections.emptyList();
			List<NotifyCustomerInit> notifyCustomerInit = notifyCustomerInitService.updateNotifyCustomerInit(
					NotifyCustomerInitMapper.INSTANCE.notifyCustomerInitDTOToNotifyCustomerInit(notifyCustomerInitDTO),
					headers);

			if (!CollectionUtils.isEmpty(notifyCustomerInit)) {
				cusInitDtoList = notifyCustomerInit.stream()
						.map(NotifyCustomerInitMapper.INSTANCE::notifyCustomerInitToNotifyCustomerInitDTO)
						.collect(Collectors.toList());
			}
			APIResponse<List<NotifyCustomerInitDTO>> responseObj = new APIResponse<>(
					Arrays.asList(CommonConstants.UPDATE_SUCCESS_MESSAGE), cusInitDtoList, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<List<NotifyCustomerInitDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (RecordNotAddedException e) {
			APIResponse<List<NotifyCustomerInitDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (NullPointerException e) {
			APIResponse<List<NotifyCustomerInitDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<NotifyCustomerInitDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
}
