package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.nscorp.obis.domain.UnCd;
import com.nscorp.obis.dto.UnCdDTO;
import com.nscorp.obis.dto.mapper.UnCdMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.DamageCategory;
import com.nscorp.obis.dto.DamageCategoryDTO;
import com.nscorp.obis.dto.mapper.DamageCategoryMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.DamageCategoryService;

@RestController
@CrossOrigin
@RequestMapping("/")
public class DamageCategoryController {

    @Autowired
    DamageCategoryService damageCategoryService;

    @Autowired
    DamageCategoryMapper damageCategoryMapper;

	@GetMapping(value = ControllerConstants.DAMAGE_CATEGORY)
	public ResponseEntity<APIResponse<List<DamageCategoryDTO>>> getDamageCategory(
			@RequestParam(name = "cat-cd", required = false) Integer catCd) {
		try {
			List<DamageCategoryDTO> damageCategoryDto = Collections.emptyList();
			List<DamageCategory> damageCategory = damageCategoryService.getAllDamageCategory(catCd);
			if (damageCategory != null && !damageCategory.isEmpty()) {
				damageCategoryDto = damageCategory.stream()
						.map(DamageCategoryMapper.INSTANCE::damageCategoryToDamageCategoryDTO)
						.collect(Collectors.toList());
			}
			APIResponse<List<DamageCategoryDTO>> responseObj = new APIResponse<>(
					Arrays.asList("Successfully retrieved data!"), damageCategoryDto, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<List<DamageCategoryDTO>> responseObj = new APIResponse<>(Arrays.asList("No Records Found"),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<DamageCategoryDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@DeleteMapping(value = ControllerConstants.DAMAGE_CATEGORY)
	public ResponseEntity<List<APIResponse<DamageCategoryDTO>>> deleteDamageCategory(
			@RequestBody List<DamageCategoryDTO> dtoListToBeDeleted) {
		List<APIResponse<DamageCategoryDTO>> response;
		AtomicInteger errorCount = new AtomicInteger();
		if (dtoListToBeDeleted != null && !dtoListToBeDeleted.isEmpty()) {
			response = dtoListToBeDeleted.stream().map(dto -> {
				APIResponse<DamageCategoryDTO> singleDtoDelResponse;
				try {
					damageCategoryService.deleteDamageCategory(DamageCategoryMapper.INSTANCE.damageCategoryDtoToDamageCategory(dto));
					singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"), dto,
							ResponseStatusCode.SUCCESS);
				} catch (Exception e) {
					errorCount.incrementAndGet();
					singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
				}
				return singleDtoDelResponse;
			}).collect(Collectors.toList());
		} else {
			response = Collections.emptyList();
		}
		if (errorCount.get() == 0 && response.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else if (response.size() > errorCount.get()) {
			return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(response);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
	
	@PostMapping(value = ControllerConstants.DAMAGE_CATEGORY)
	public ResponseEntity<APIResponse<DamageCategoryDTO>> addDamageCategory(@Valid @RequestBody DamageCategoryDTO damageCategoryDTOObj, @RequestHeader Map<String,String> headers) {

		try {
			DamageCategory damageCategory = DamageCategoryMapper.INSTANCE.damageCategoryDtoToDamageCategory(damageCategoryDTOObj);
			DamageCategory addDamageCategory = damageCategoryService.addDamageCategory(damageCategory, headers);
			DamageCategoryDTO addDamageCategoryDTO = DamageCategoryMapper.INSTANCE.damageCategoryToDamageCategoryDTO(addDamageCategory);
			APIResponse<DamageCategoryDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully Added data!"), addDamageCategoryDTO, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<DamageCategoryDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (NullPointerException e){
			APIResponse<DamageCategoryDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (SizeExceedException e){
			APIResponse<DamageCategoryDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (RecordNotAddedException e){
			APIResponse<DamageCategoryDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (Exception e){
			APIResponse<DamageCategoryDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}


	@PutMapping(value= ControllerConstants.DAMAGE_CATEGORY)
	public ResponseEntity<APIResponse<DamageCategoryDTO>> updateDamageCategory(@Valid @NotNull @RequestBody DamageCategoryDTO damageCategoryDTOObj, @RequestHeader Map<String,String> headers) {
		try {
			DamageCategory addDamageCategory = damageCategoryService.updateDamageCategory(DamageCategoryMapper.INSTANCE.damageCategoryDtoToDamageCategory(damageCategoryDTOObj), headers);

			APIResponse<DamageCategoryDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),
					DamageCategoryMapper.INSTANCE.damageCategoryToDamageCategoryDTO(addDamageCategory), ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		}catch (NoRecordsFoundException e){
			APIResponse<DamageCategoryDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		}catch (RecordNotAddedException e) {
			APIResponse<DamageCategoryDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		}catch (Exception e){
			APIResponse<DamageCategoryDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
}