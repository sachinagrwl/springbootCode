package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.Equipment;
import com.nscorp.obis.dto.EquipmentDTO;
import com.nscorp.obis.dto.mapper.EquipmentMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.EquipmentService;


@Validated
@RestController
@CrossOrigin
@RequestMapping("/")
public class EquipmentController {
	
	@Autowired
	EquipmentService equipmentService;

	private static final Logger logger = LoggerFactory.getLogger(EquipmentController.class);
		
	@GetMapping(value = ControllerConstants.EQUIPMENT)
	public ResponseEntity<APIResponse<List<EquipmentDTO>>> getAllEquiList(
			
			@NotEmpty(message = "equip int should not be NULL.") 
			@Size( max=4, message="equip int less than 4 Char")
			@RequestParam("equip-int") String equipInit,
			
			@NotNull(message = "equip nbr should not be NULL.")
			@Digits(integer = 6, fraction = 0, message = "equip nbr should have 6 digits")
			@RequestParam("equip-nbr") Integer equipNbr) {

		try {
			List<EquipmentDTO> equipmentDtoList = Collections.emptyList();
			List<Equipment> equipmentList = equipmentService.getAllEquiList(equipInit, equipNbr);
			if (equipmentList != null && !equipmentList.isEmpty()) {
				equipmentDtoList = equipmentList.stream().map(EquipmentMapper.INSTANCE::equipmentToEquipmentDTO)
						.collect(Collectors.toList());
			}
			APIResponse<List<EquipmentDTO>> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"),
					equipmentDtoList, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<List<EquipmentDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<EquipmentDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
			}

		}
	
}


