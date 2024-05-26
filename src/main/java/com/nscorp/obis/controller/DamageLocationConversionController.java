package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.nscorp.obis.domain.DamageLocationConversion;
import com.nscorp.obis.dto.DamageLocationConversionDTO;
import com.nscorp.obis.dto.mapper.DamageLocationConversionMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.DamageLocationConversionService;

@RestController
@CrossOrigin
@RequestMapping("/")
public class DamageLocationConversionController {
    @Autowired
    DamageLocationConversionService damageLocationConversionService;

    @GetMapping(value = ControllerConstants.DAMAGE_LOCATION_CONVERSIOND)
    public ResponseEntity<APIResponse<List<DamageLocationConversionDTO>>> getDamageLocationConversion() {
        try {
            List<DamageLocationConversionDTO> damageLocationConversionDTOList=Collections.emptyList();
            List<DamageLocationConversion> damageLocationConversionList = damageLocationConversionService.getAllDamageLocationConversion();
            if (damageLocationConversionList != null && !damageLocationConversionList.isEmpty()) {
                damageLocationConversionDTOList = damageLocationConversionList.stream().map(DamageLocationConversionMapper.INSTANCE::damageLocationConversionToDamageLocationConversionDTO)
                        .collect(Collectors.toList());
            }
            APIResponse<List<DamageLocationConversionDTO>> responseObj = new APIResponse<>(
                    Arrays.asList("Successfully retrieved data!"), damageLocationConversionDTOList, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (NoRecordsFoundException e) {
            APIResponse<List<DamageLocationConversionDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (Exception e) {
            APIResponse<List<DamageLocationConversionDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }

    @DeleteMapping(value = ControllerConstants.DAMAGE_LOCATION_CONVERSIOND)
    public ResponseEntity<List<APIResponse<DamageLocationConversionDTO>>> deleteDamageReason(
            @Valid @RequestBody List<DamageLocationConversionDTO> damageLocationConversionDTOList) {
        List<APIResponse<DamageLocationConversionDTO>> response;
        AtomicInteger errorCount = new AtomicInteger();
        if (damageLocationConversionDTOList != null && !damageLocationConversionDTOList.isEmpty()) {
            response = damageLocationConversionDTOList.stream().map(tableObjDto -> {
                APIResponse<DamageLocationConversionDTO> singleDtoDelResponse;
                try {
                    damageLocationConversionService.deleteDamageLocationConversion(DamageLocationConversionMapper.INSTANCE.damageLocationConversionDtoToDamageLocationConversion(tableObjDto));
                    singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"),tableObjDto, ResponseStatusCode.SUCCESS);
                } catch (Exception e) {
                    errorCount.incrementAndGet();
                    singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
                }
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
    
    @PostMapping(value = ControllerConstants.DAMAGE_LOCATION_CONVERSIOND)
    public ResponseEntity<APIResponse<DamageLocationConversionDTO>> addDamageLocationConversion(@Valid @RequestBody DamageLocationConversionDTO damageLocationConversionObj,
                                                                              @RequestHeader Map<String, String> headers) {
        try {
        	DamageLocationConversion damageLocationConversion = DamageLocationConversionMapper.INSTANCE.damageLocationConversionDtoToDamageLocationConversion(damageLocationConversionObj);
        	DamageLocationConversion addDamageLocationConversion = damageLocationConversionService.insertDamageLocationConversion(damageLocationConversion, headers);
        	DamageLocationConversionDTO addedDamageLocationConversionDto = DamageLocationConversionMapper.INSTANCE.damageLocationConversionToDamageLocationConversionDTO(addDamageLocationConversion);
            APIResponse<DamageLocationConversionDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),
            		addedDamageLocationConversionDto, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (NoRecordsFoundException e) {
            APIResponse<DamageLocationConversionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (RecordAlreadyExistsException e) {
            APIResponse<DamageLocationConversionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
        } catch (Exception e) {
            APIResponse<DamageLocationConversionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }


    @PutMapping(value = ControllerConstants.DAMAGE_LOCATION_CONVERSIOND)
    public ResponseEntity<APIResponse<DamageLocationConversionDTO>> updateDamageLocationConversion(@Valid @RequestBody DamageLocationConversionDTO damageLocationConversionObj,
                                                                 @RequestHeader Map<String, String> headers) {
    	 try {
         	DamageLocationConversion damageLocationConversion = DamageLocationConversionMapper.INSTANCE.damageLocationConversionDtoToDamageLocationConversion(damageLocationConversionObj);
         	DamageLocationConversion addDamageLocationConversion = damageLocationConversionService.updateDamageLocationConversion(damageLocationConversion, headers);
         	DamageLocationConversionDTO addedDamageLocationConversionDto = DamageLocationConversionMapper.INSTANCE.damageLocationConversionToDamageLocationConversionDTO(addDamageLocationConversion);
             APIResponse<DamageLocationConversionDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),
             		addedDamageLocationConversionDto, ResponseStatusCode.SUCCESS);
             return ResponseEntity.status(HttpStatus.OK).body(responseObj);
         } catch (NoRecordsFoundException e) {
             APIResponse<DamageLocationConversionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                     ResponseStatusCode.INFORMATION);
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
         } catch (RecordAlreadyExistsException e) {
             APIResponse<DamageLocationConversionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                     ResponseStatusCode.INFORMATION);
             return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
         } catch (Exception e) {
             APIResponse<DamageLocationConversionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                     ResponseStatusCode.FAILURE);
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
         }
     }
}
