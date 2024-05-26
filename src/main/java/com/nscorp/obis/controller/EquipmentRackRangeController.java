package com.nscorp.obis.controller;

import java.util.Arrays;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
import com.nscorp.obis.domain.EquipmentRackRange;
import com.nscorp.obis.dto.EquipmentRackRangeDTO;
import com.nscorp.obis.dto.mapper.EquipmentRackRangeMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.EquipmentRackRangeService;

@Validated
@RestController
@CrossOrigin
@RequestMapping("/")
public class EquipmentRackRangeController {

	
	@Autowired
	EquipmentRackRangeService equipmentRackRangeService;
	
	@GetMapping(value = ControllerConstants.EQ_RACK_RANGE)
	public ResponseEntity<APIResponse<List<EquipmentRackRangeDTO>>> getAllTables() {

		try {
			List<EquipmentRackRangeDTO> equiDtoList = Collections.emptyList();
			List<EquipmentRackRange> tablesList = equipmentRackRangeService.getAllTables();
			if (tablesList != null && !tablesList.isEmpty()) {
				equiDtoList = tablesList.stream()
						.map(EquipmentRackRangeMapper.INSTANCE::equipmentRackRangeToEquipmentRackRangeDTO)
						.collect(Collectors.toList());
			}
			APIResponse<List<EquipmentRackRangeDTO>> responseObj = new APIResponse<>(
					Arrays.asList("Successfully retrieved data!"), equiDtoList, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<List<EquipmentRackRangeDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<EquipmentRackRangeDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}

	}
	
	@PostMapping(value = ControllerConstants.EQ_RACK_RANGE)
	public ResponseEntity<APIResponse<EquipmentRackRangeDTO>> addEquipmentRackRange(@Valid @RequestBody EquipmentRackRangeDTO equipmentRackRangeObjDto,
			@RequestHeader Map<String, String> headers) {
		try {
			EquipmentRackRange equipmentRackRange = EquipmentRackRangeMapper.INSTANCE.equipmentRackRangeDTOToEquipmentRackRange(equipmentRackRangeObjDto);
			EquipmentRackRange addequipmentRackRange = equipmentRackRangeService.addEquipmentRackRange(equipmentRackRange, headers);
			EquipmentRackRangeDTO addequipmentRackRangeDto = EquipmentRackRangeMapper.INSTANCE.equipmentRackRangeToEquipmentRackRangeDTO(addequipmentRackRange);
			APIResponse<EquipmentRackRangeDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),
					addequipmentRackRangeDto, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<EquipmentRackRangeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (RecordAlreadyExistsException e) {
			APIResponse<EquipmentRackRangeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (Exception e) {
			APIResponse<EquipmentRackRangeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
	
	@PutMapping(value = ControllerConstants.EQ_RACK_RANGE)
	public ResponseEntity<APIResponse<EquipmentRackRangeDTO>> putEquipmentRackRange(@Valid @RequestBody EquipmentRackRangeDTO equipmentRackRangeObjDto,
			@RequestHeader Map<String, String> headers) {
		try {
			if(equipmentRackRangeObjDto.getEquipLowNbr()!=null &&
					equipmentRackRangeObjDto.getEquipHighNbr()!=null && 
					StringUtils.isEmpty(equipmentRackRangeObjDto.getEquipType())) {
			throw new Exception("please provide manditory fields");
			}
			if(!equipmentRackRangeObjDto.getEquipType().equals("C")) {
				throw new Exception("Enter valid Equip");
			}
			EquipmentRackRange equipmentRackRange = EquipmentRackRangeMapper.INSTANCE.equipmentRackRangeDTOToEquipmentRackRange(equipmentRackRangeObjDto);
			EquipmentRackRange addequipmentRackRange = equipmentRackRangeService.updateEquipmentRackRange(equipmentRackRange, headers);
			EquipmentRackRangeDTO addequipmentRackRangeDto = EquipmentRackRangeMapper.INSTANCE.equipmentRackRangeToEquipmentRackRangeDTO(addequipmentRackRange);
			APIResponse<EquipmentRackRangeDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),
					addequipmentRackRangeDto, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<EquipmentRackRangeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<EquipmentRackRangeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
	
	@DeleteMapping(value = ControllerConstants.EQ_RACK_RANGE)
	public ResponseEntity<List<APIResponse<EquipmentRackRangeDTO>>> deleteEquipmentRackRangeDto(@RequestBody List<EquipmentRackRangeDTO> equipmentRackRangeDtoList) {
		List<APIResponse<EquipmentRackRangeDTO>> response;
		AtomicInteger errorCount = new AtomicInteger();
		
		if (equipmentRackRangeDtoList != null && !equipmentRackRangeDtoList.isEmpty()) {
			response = equipmentRackRangeDtoList.stream().map(tableObjDto -> {
				APIResponse<EquipmentRackRangeDTO> singleDtoDelResponse;
				try {
					equipmentRackRangeService.deleteEquipmentRackRange(EquipmentRackRangeMapper.INSTANCE.equipmentRackRangeDTOToEquipmentRackRange(tableObjDto));
					singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"),tableObjDto, ResponseStatusCode.SUCCESS);
				} catch (Exception e) {
					errorCount.incrementAndGet();
					singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
				}
				return singleDtoDelResponse;
			})
			.collect(Collectors.toList());
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
