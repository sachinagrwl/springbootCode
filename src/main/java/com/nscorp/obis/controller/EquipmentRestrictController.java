package com.nscorp.obis.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.EquipmentRestrict;
import com.nscorp.obis.dto.EquipmentRestrictDTO;
import com.nscorp.obis.dto.mapper.EquipmentRestrictMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.EquipmentRestrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class EquipmentRestrictController {

    @Autowired
    EquipmentRestrictService equipRestrictService;

    @GetMapping(value= ControllerConstants.EQUIPMENT_RESTRICT)
    public ResponseEntity<APIResponse<List<EquipmentRestrictDTO>>> getAllEquipmentRestrictions(){

        try {
            List<EquipmentRestrictDTO> equipRestrictDtoList = Collections.emptyList();
            List<EquipmentRestrict> equipRestrictList = equipRestrictService.getAllEquipRestrictions();
            if (equipRestrictList != null && !equipRestrictList.isEmpty()) {
                equipRestrictDtoList = equipRestrictList.stream()
                        .map(EquipmentRestrictMapper.INSTANCE::equipmentRestrictToEquipmentRestrictDTO)
                        .collect(Collectors.toList());
            }
            APIResponse<List<EquipmentRestrictDTO>> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"),equipRestrictDtoList, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (NoRecordsFoundException e){
            APIResponse<List<EquipmentRestrictDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (Exception e){
            APIResponse<List<EquipmentRestrictDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @PostMapping(value= ControllerConstants.EQUIPMENT_RESTRICT)
    public ResponseEntity<APIResponse<EquipmentRestrictDTO>> addEquipmentRestriction(@Valid @RequestBody EquipmentRestrictDTO eqRestrictObjDto, @RequestHeader Map<String,String> headers){
        try {
            EquipmentRestrict equipRestrict = EquipmentRestrictMapper.INSTANCE.equipmentRestrictDTOToEquipmentRestrict(eqRestrictObjDto);

            EquipmentRestrict addedEquipRestrict = equipRestrictService.addEquipRestrictions(equipRestrict, headers);
            EquipmentRestrictDTO addedEquipRestrictDto = EquipmentRestrictMapper.INSTANCE.equipmentRestrictToEquipmentRestrictDTO(addedEquipRestrict);
            APIResponse<EquipmentRestrictDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),addedEquipRestrictDto, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (NoRecordsFoundException e){
            APIResponse<EquipmentRestrictDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (SizeExceedException e){
            APIResponse<EquipmentRestrictDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
        } catch (RecordAlreadyExistsException e){
            APIResponse<EquipmentRestrictDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
        } catch (RecordNotAddedException e){
            APIResponse<EquipmentRestrictDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
        } catch (NullPointerException e){
            APIResponse<EquipmentRestrictDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
        } catch (Exception e){
            APIResponse<EquipmentRestrictDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }

    @PutMapping(value = ControllerConstants.EQUIPMENT_RESTRICT)
    public ResponseEntity<APIResponse<EquipmentRestrictDTO>> updateEquipmentRestriction(@Valid @RequestBody EquipmentRestrictDTO eqRestrictObjDto, @RequestHeader Map<String,String> headers) {

        try {
            EquipmentRestrict equipRestrict = EquipmentRestrictMapper.INSTANCE.equipmentRestrictDTOToEquipmentRestrict(eqRestrictObjDto);
            EquipmentRestrict updatedEquipRestrict = equipRestrictService.updateEquipRestriction(equipRestrict, headers);
            EquipmentRestrictDTO updatedEquipRestrictDto = EquipmentRestrictMapper.INSTANCE.equipmentRestrictToEquipmentRestrictDTO(updatedEquipRestrict);
            APIResponse<EquipmentRestrictDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),updatedEquipRestrictDto, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (NoRecordsFoundException e){
            APIResponse<EquipmentRestrictDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (NullPointerException e){
            APIResponse<EquipmentRestrictDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
        } catch (SizeExceedException e){
            APIResponse<EquipmentRestrictDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
        } catch (Exception e){
            APIResponse<EquipmentRestrictDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }

    @DeleteMapping(value = ControllerConstants.EQUIPMENT_RESTRICT)
    public ResponseEntity<List<APIResponse<EquipmentRestrictDTO>>> deleteEquipmentRestriction(@RequestBody List<EquipmentRestrictDTO> eqRestrictObjDtoList) {
        List<APIResponse<EquipmentRestrictDTO>> responseDTOList;
        AtomicInteger errorCount = new AtomicInteger();
        if (!CollectionUtils.isEmpty(eqRestrictObjDtoList)) {
            responseDTOList = eqRestrictObjDtoList.stream().map(eqRestrictObjDto -> {
                APIResponse<EquipmentRestrictDTO> singleDtoDelResponse;
                try {
                    EquipmentRestrict eqOverrideTareWeight = equipRestrictService.deleteEquipRestriction(EquipmentRestrictMapper.INSTANCE.equipmentRestrictDTOToEquipmentRestrict(eqRestrictObjDto));
                    singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"),
                            EquipmentRestrictMapper.INSTANCE.equipmentRestrictToEquipmentRestrictDTO(eqOverrideTareWeight),
                            ResponseStatusCode.SUCCESS);
                } catch (NoRecordsFoundException e) {
                    errorCount.incrementAndGet();
                    singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
                } catch (Exception e) {
                    errorCount.incrementAndGet();
                    singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage() + " For Restriction Id : " + eqRestrictObjDto.getRestrictionId()), ResponseStatusCode.FAILURE);
                }
                return singleDtoDelResponse;
            }).collect(Collectors.toList());
        } else {
            responseDTOList = Collections.emptyList();
        }

        if (errorCount.get() == 0 && responseDTOList.size() > 0) // No errors and at least 1 success
            return ResponseEntity.status(HttpStatus.OK).body(responseDTOList);
        else if (responseDTOList.size() > errorCount.get()) // Partial success
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(responseDTOList);
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTOList);
    }

}
