package com.nscorp.obis.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.nscorp.obis.dto.mapper.StorageRatesListMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.StorageRates;
import com.nscorp.obis.dto.EquipmentCustomerRangeDTO;
import com.nscorp.obis.dto.StorageRatesDTO;
import com.nscorp.obis.dto.StorageRatesListDTO;
import com.nscorp.obis.dto.mapper.StorageRateDetailMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.PaginatedResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.StorageRatesService;

@Validated
@RestController
@RequestMapping("/")
@Slf4j
public class StorageRatesController {

	@Autowired
	StorageRatesService service;

	@Autowired
	StorageRatesListMapper storageRatesListMapper;

	@GetMapping(value = ControllerConstants.EQUIPMENT_CUST_RANGE)
	public ResponseEntity<APIResponse<PaginatedResponse<EquipmentCustomerRangeDTO>>> getEquipmentCustomerRange(
			@RequestParam(name = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
			@RequestParam(name = "pageSize", required = false, defaultValue = "20") Integer pageSize)
			throws SQLException {

		try {
			PaginatedResponse<EquipmentCustomerRangeDTO> equipmentCustomerRange = service
					.fetchEquipmentCustomerRange(pageSize, pageNumber);

			APIResponse<PaginatedResponse<EquipmentCustomerRangeDTO>> responseObj = new APIResponse<>(
					Arrays.asList("Successfully retrieved data!"), equipmentCustomerRange,
					ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);

		} catch (NoRecordsFoundException e) {
			APIResponse<PaginatedResponse<EquipmentCustomerRangeDTO>> responseObj = new APIResponse<>(
					Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION.getStatusCode(),
					ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<PaginatedResponse<EquipmentCustomerRangeDTO>> responseObj = new APIResponse<>(
					Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION.getStatusCode(),
					ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@GetMapping(value = ControllerConstants.STORAGE_RATE)
	public ResponseEntity<APIResponse<PaginatedResponse<StorageRatesDTO>>> getStorageRates(
			@RequestParam(name = "selectRateType", required = false) String selectRateType,
			@RequestParam(name = "incExpDate", required = false) String incExpDate,
			@RequestParam(name = "shipPrimSix", required = false) String shipPrimSix,
			@RequestParam(name = "customerPrimSix", required = false) String customerPrimSix,
			@RequestParam(name = "bnfPrimSix", required = false) String bnfPrimSix,
			@RequestParam(name = "termId", required = false) String[] termId,
			@RequestParam(name = "equipInit", required = false) String[] equipInit,
			@RequestParam(name = "equipLgth", required = false) String equipLgth,
			@RequestParam(name = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
			@RequestParam(name = "pageSize", required = false, defaultValue = "20") Integer pageSize,
			@RequestParam(name = "sort", required = false, defaultValue = "termId, asc") String[] sort,
			@RequestParam(name = "filter", required = false) String[] filter)
			throws SQLException {

		try {
			PaginatedResponse<StorageRatesDTO> storageRates = service.fetchStorageRates(selectRateType, incExpDate,
					shipPrimSix, customerPrimSix, bnfPrimSix, termId, equipInit, equipLgth, pageSize, pageNumber, sort, filter);

			APIResponse<PaginatedResponse<StorageRatesDTO>> responseObj = new APIResponse<>(
					Arrays.asList("Successfully retrieved data!"), storageRates,
					ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);

		} catch (NoRecordsFoundException e) {
			APIResponse<PaginatedResponse<StorageRatesDTO>> responseObj = new APIResponse<>(
					Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION.getStatusCode(),
					ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<PaginatedResponse<StorageRatesDTO>> responseObj = new APIResponse<>(
					Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION.getStatusCode(),
					ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@PutMapping(value = ControllerConstants.STORAGE_RATE)
	ResponseEntity<APIResponse<StorageRatesDTO>> updateStorageRate(@Valid @RequestBody StorageRatesDTO storageRatesDTO,
			@RequestHeader Map<String, String> headers) throws SQLException {
		try {
			log.info("updateStorageRate : Method Starts");
			APIResponse<StorageRatesDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),
					service.updateStorageRate(storageRatesDTO, headers), ResponseStatusCode.SUCCESS.getStatusCode(),
					ResponseStatusCode.SUCCESS.toString());
			log.info("updateStorageRate : Method Ends");
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);

		} catch (NoRecordsFoundException | InvalidDataException e) {
			APIResponse<StorageRatesDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			HttpStatus status = HttpStatus.NOT_FOUND;
			if (e.getClass() == InvalidDataException.class)
				status = HttpStatus.BAD_REQUEST;
			log.error("updateStorageRate : Error " + e.getMessage());
			return ResponseEntity.status(status).body(responseObj);
		} catch (Exception e) {
			APIResponse<StorageRatesDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			log.error("updateStorageRate : Error " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@PostMapping(value = ControllerConstants.STORAGE_RATE)
	public ResponseEntity<List<APIResponse<StorageRatesDTO>>> addStorageRates(
			@RequestParam(name = "selectRateType", required = false, defaultValue = "Default") String selectRateType,
			@RequestParam(name = "forceadd", required = false, defaultValue = "N") String forceAdd,
			@Valid @NotNull @RequestBody StorageRatesListDTO storageRatesListDTO,
			@RequestHeader Map<String, String> headers) {
		List<APIResponse<StorageRatesDTO>> response = new ArrayList<>();
		AtomicInteger errorCount = new AtomicInteger();
		StorageRatesDTO storageRatesDTO;
		storageRatesDTO = storageRatesListMapper.StorageRatesListDTOToStorageRatesDTO(storageRatesListDTO);
		Integer dummyTermId = 0;
		if (storageRatesListDTO.getTermIds() == null || storageRatesListDTO.getTermIds().isEmpty()) {
			storageRatesListDTO.setTermIds(new ArrayList<>());
			storageRatesListDTO.getTermIds().add(0, 12334L);
			dummyTermId++;
		}
		if (storageRatesListDTO.getEquipInits() == null || storageRatesListDTO.getEquipInits().isEmpty()) {
			storageRatesListDTO.setEquipInits(new ArrayList<>());
			storageRatesListDTO.getEquipInits().add("null");
		}
		for (String LorE : storageRatesListDTO.getLdEmptyCds()) {
			for (String LorI : storageRatesListDTO.getLclInterInds()) {
				for (Long termId : storageRatesListDTO.getTermIds()) {
					storageRatesDTO.setTermId(termId);
					if (dummyTermId > 0) {
						storageRatesDTO.setTermId(null);
					}
					for (String equipInit : storageRatesListDTO.getEquipInits()) {
						storageRatesDTO.setEquipInit(equipInit);
						if (equipInit.equals("null")) {
							storageRatesDTO.setEquipInit(null);
						}
						storageRatesDTO.setLclInterInd(LorI);
						storageRatesDTO.setLdEmptyCd(LorE);
						APIResponse<StorageRatesDTO> responseObj;
						try {
							StorageRatesDTO data = service.addStorageRates(selectRateType, forceAdd, storageRatesDTO,
									headers);
							responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"), data,
									ResponseStatusCode.SUCCESS);
						} catch (Exception e) {
							errorCount.incrementAndGet();
							responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
									ResponseStatusCode.INFORMATION);
						}
						response.add(responseObj);
					}
				}
			}
		}
		if (errorCount.get() == 0 && response.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else if (response.size() > errorCount.get()) {
			return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(response);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

}
