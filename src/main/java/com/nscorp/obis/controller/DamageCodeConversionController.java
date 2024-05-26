package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.DamageCodeConversion;
import com.nscorp.obis.dto.DamageCodeConversionDTO;
import com.nscorp.obis.dto.mapper.DamageCodeConversionMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.DamageCodeConversionService;

@RestController
@RequestMapping("/")
@CrossOrigin
@Validated
public class DamageCodeConversionController {


    @Autowired
    private DamageCodeConversionService damageCodeConversionService;


    @GetMapping(value = ControllerConstants.DAMAGE_CODE_CONVERSION)
    public ResponseEntity<APIResponse<List<DamageCodeConversionDTO>>> getAllDamageCodeConversions(@RequestParam(name = "catCd",required = false) Integer catCd,
                                                                                                  @RequestParam(name = "reasonCd",required = false) String reasonCd) {

        try {
            List<DamageCodeConversionDTO> damageCodeConversionDTOList = Collections.emptyList();
            if (catCd != null && reasonCd!=null) {
                DamageCodeConversion damageEntity = damageCodeConversionService.getDamageCodeConversionByCatCode(catCd, reasonCd);
                damageCodeConversionDTOList = Arrays.asList(DamageCodeConversionMapper.INSTANCE.damageCodeConversionToDamageCodeConversionDTO(damageEntity));
            } else {
                List<DamageCodeConversion> damageConversionList = damageCodeConversionService.getAllDamageCodeConversions();
                if (damageConversionList != null && !damageConversionList.isEmpty()) {
                    damageCodeConversionDTOList = damageConversionList.stream().map(DamageCodeConversionMapper.INSTANCE::damageCodeConversionToDamageCodeConversionDTO)
                            .collect(Collectors.toList());
                }
            }

            APIResponse<List<DamageCodeConversionDTO>> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"),
                    damageCodeConversionDTOList, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);

        } catch (NoRecordsFoundException e) {
            APIResponse<List<DamageCodeConversionDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (Exception e) {
            APIResponse<List<DamageCodeConversionDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }

    }

    @DeleteMapping(value = ControllerConstants.DAMAGE_CODE_CONVERSION)
    public ResponseEntity<List<APIResponse<DamageCodeConversionDTO>>> deleteDamageCodeConversion(@NotNull @RequestBody List<DamageCodeConversionDTO> damageComponentDTOList) {
        List<APIResponse<DamageCodeConversionDTO>> response;
        AtomicInteger errorCount = new AtomicInteger();
        if (damageComponentDTOList != null && !damageComponentDTOList.isEmpty()) {
            response = damageComponentDTOList.stream().map(damageComponentDTOObj -> {
                        APIResponse<DamageCodeConversionDTO> singleDtoDelResponse;
                        try {
                            damageCodeConversionService.deleteCodeConversion(DamageCodeConversionMapper.INSTANCE.damageCodeConversionDtoToDamageCodeConversion(damageComponentDTOObj));
                            singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"), damageComponentDTOObj, ResponseStatusCode.SUCCESS);
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
    
    @PostMapping(value = ControllerConstants.DAMAGE_CODE_CONVERSION)
	public ResponseEntity<APIResponse<DamageCodeConversionDTO>> addDamageCodeConversion(@Valid @RequestBody DamageCodeConversionDTO damageCodeConversionDTO,
																		   @RequestHeader Map<String, String> headers) {
		try {
			DamageCodeConversion damageCodeConversion = DamageCodeConversionMapper.INSTANCE.damageCodeConversionDtoToDamageCodeConversion(damageCodeConversionDTO);
			DamageCodeConversion addDamageCode = damageCodeConversionService.addDamageCodeConversion(damageCodeConversion, headers);
			DamageCodeConversionDTO damageCodeDTO = DamageCodeConversionMapper.INSTANCE.damageCodeConversionToDamageCodeConversionDTO(addDamageCode);
			APIResponse<DamageCodeConversionDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),
					damageCodeDTO, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<DamageCodeConversionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (RecordAlreadyExistsException e) {
			APIResponse<DamageCodeConversionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (Exception e) {
			APIResponse<DamageCodeConversionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
    
    @PutMapping(value = ControllerConstants.DAMAGE_CODE_CONVERSION)
	public ResponseEntity<APIResponse<DamageCodeConversionDTO>> updateDamageCodeConversion(
			@Valid @RequestBody DamageCodeConversionDTO damageCodeConversionDTO, @RequestHeader Map<String, String> headers) {
		try{
			DamageCodeConversion damageCodeReq = DamageCodeConversionMapper.INSTANCE.damageCodeConversionDtoToDamageCodeConversion(damageCodeConversionDTO);
			DamageCodeConversion addDamageCodeRes = damageCodeConversionService.updateDamageCodeConversion(damageCodeReq, headers);
			DamageCodeConversionDTO damageCodeDtoRes = DamageCodeConversionMapper.INSTANCE.damageCodeConversionToDamageCodeConversionDTO(addDamageCodeRes);
			APIResponse<DamageCodeConversionDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),
					damageCodeDtoRes, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		}
		catch (NoRecordsFoundException e) {
			APIResponse<DamageCodeConversionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (RecordAlreadyExistsException e) {
			APIResponse<DamageCodeConversionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (Exception e) {
			APIResponse<DamageCodeConversionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
}
