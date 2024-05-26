package com.nscorp.obis.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.EquipmentCar;
import com.nscorp.obis.dto.EquipmentCarDTO;
import com.nscorp.obis.dto.mapper.EquipmentCarMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.EquipmentCarService;

@RestController
@RequestMapping(value = ControllerConstants.CAR)
@Validated
public class EquipmentCarController {

	@Autowired
	EquipmentCarService equipmentCarService;

	@GetMapping
	public ResponseEntity<APIResponse<List<EquipmentCarDTO>>> getEquipmentCar(
			@RequestParam(required = true, name = "carInit") String carInit,
			@RequestParam(required = true, name = "carNbr") BigDecimal carNbr,
			@RequestParam(required = true, name = "carEquipType") String carEquipType) {
		try {
			List<EquipmentCarDTO> equipmentCarDTOList = new ArrayList<>();
			EquipmentCar equipmentCar = equipmentCarService.getEquipmentCar(carInit, carNbr, carEquipType);
			EquipmentCarDTO retrivedCarDTO = EquipmentCarMapper.INSTANCE
					.EquipmentCarToEquipmentCarDTO(articulateConversionFromAlphaToNum(equipmentCar));
			equipmentCarDTOList.add(retrivedCarDTO);
			APIResponse<List<EquipmentCarDTO>> responseObj = new APIResponse<>(
					Arrays.asList("Successfully retrived data!"), equipmentCarDTOList, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<List<EquipmentCarDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<EquipmentCarDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@PostMapping
	public ResponseEntity<APIResponse<EquipmentCarDTO>> addEquipmentCar(@Valid @RequestBody EquipmentCarDTO eqCarObjDto,
			@RequestHeader Map<String, String> headers) {
		try {
			EquipmentCar eqCar = EquipmentCarMapper.INSTANCE
					.EquipmentCarDTOToEquipmentCar(articulateConversionFromNumToAlpha(eqCarObjDto));
			EquipmentCar addedEqCar = equipmentCarService.addEquipmentCar(eqCar, headers);
			EquipmentCarDTO addedEqCarDto = EquipmentCarMapper.INSTANCE
					.EquipmentCarToEquipmentCarDTO(articulateConversionFromAlphaToNum(addedEqCar));
			APIResponse<EquipmentCarDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),
					addedEqCarDto, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<EquipmentCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (SizeExceedException e) {
			APIResponse<EquipmentCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (RecordAlreadyExistsException e) {
			APIResponse<EquipmentCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (RecordNotAddedException e) {
			APIResponse<EquipmentCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (NullPointerException e) {
			APIResponse<EquipmentCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (Exception e) {
			APIResponse<EquipmentCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<APIResponse<EquipmentCarDTO>> updateEquipmentCar(
			@Valid @NotNull @RequestBody EquipmentCarDTO equipmentCarDTO, @RequestHeader Map<String, String> headers) {
		try {
			EquipmentCar updateCar = EquipmentCarMapper.INSTANCE
					.EquipmentCarDTOToEquipmentCar(articulateConversionFromNumToAlpha(equipmentCarDTO));
			EquipmentCar updatedEqCar = equipmentCarService.updateEquipmentCar(updateCar, headers);
			EquipmentCarDTO updatedEqCarDto = EquipmentCarMapper.INSTANCE
					.EquipmentCarToEquipmentCarDTO(articulateConversionFromAlphaToNum(updatedEqCar));
			APIResponse<EquipmentCarDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),
					updatedEqCarDto, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<EquipmentCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (RecordNotAddedException e) {
			APIResponse<EquipmentCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (Exception e) {
			APIResponse<EquipmentCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	private EquipmentCar articulateConversionFromAlphaToNum(EquipmentCar existingCar) {
		char charArticulate = StringUtils.isNotBlank(existingCar.getArticulate())
				? existingCar.getArticulate().toCharArray()[0]
				: '0';
		if ((int) charArticulate > 49 && (int) charArticulate < 58)
			existingCar.setArticulate(String.valueOf(charArticulate - 48));
		else if ((int) charArticulate > 64 && (int) charArticulate < 91)
			existingCar.setArticulate(String.valueOf(charArticulate - 55));
		else
			existingCar.setArticulate(null);
		return existingCar;
	}

	private EquipmentCarDTO articulateConversionFromNumToAlpha(EquipmentCarDTO existingCar) {
		try {
			String articulateReq = existingCar.getArticulate();
			int intArticulate = StringUtils.isNotBlank(articulateReq) ? Integer.parseInt(articulateReq) : 0;
			if (intArticulate > 1 && intArticulate < 10)
				existingCar.setArticulate(articulateReq);
			else if (intArticulate > 9 && intArticulate < 36)
				existingCar.setArticulate(String.valueOf((char) (intArticulate + 55)));
			else
				existingCar.setArticulate(null);
		} catch (NumberFormatException e) {
			existingCar.setArticulate(null);
		}
		return existingCar;
	}

}
