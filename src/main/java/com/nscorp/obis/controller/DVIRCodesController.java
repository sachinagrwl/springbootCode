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
import org.springframework.web.bind.annotation.*;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.DVIRCodes;
import com.nscorp.obis.dto.DVIRCodesDTO;
import com.nscorp.obis.dto.mapper.DVIRCodesMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.DVIRCodesService;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/")
@Validated
@RestController
@CrossOrigin
@Slf4j
public class DVIRCodesController {

    @Autowired
    private DVIRCodesService dvirCodesService;

    @GetMapping(value = ControllerConstants.DVIR_CODES)
    public ResponseEntity<APIResponse<List<DVIRCodesDTO>>> getDVIRCodes() {
        try {
            List<DVIRCodesDTO> dvirCodesDTOList= Collections.emptyList();
            List<DVIRCodes> dvirCodesList = dvirCodesService.getAllDVIRCodes();
            if (dvirCodesList != null && !dvirCodesList.isEmpty()) {
                dvirCodesDTOList = dvirCodesList.stream().map(DVIRCodesMapper.INSTANCE::DvirCodesToDvirCodesDTO)
                        .collect(Collectors.toList());
            }
            APIResponse<List<DVIRCodesDTO>> responseObj = new APIResponse<>(
                    Arrays.asList("Successfully retrieved data!"), dvirCodesDTOList, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (NoRecordsFoundException e) {
            APIResponse<List<DVIRCodesDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (Exception e) {
            APIResponse<List<DVIRCodesDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }

    @DeleteMapping(value = ControllerConstants.DVIR_CODES)
    public ResponseEntity<List<APIResponse<DVIRCodesDTO>>> deleteDVIRCodes(
            @Valid @RequestBody List<DVIRCodesDTO> dvirCodesDTOList) {
        List<APIResponse<DVIRCodesDTO>> response;
        AtomicInteger errorCount = new AtomicInteger();
        if (dvirCodesDTOList != null && !dvirCodesDTOList.isEmpty()) {
            response = dvirCodesDTOList.stream().map(tableObjDto -> {
                APIResponse<DVIRCodesDTO> singleDtoDelResponse;
                try {
                    dvirCodesService.deleteDVIRCodes(DVIRCodesMapper.INSTANCE.DvirCodesDtoToDvirCodes(tableObjDto));
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


    @PostMapping(value = ControllerConstants.DVIR_CODES)
    public ResponseEntity<APIResponse<DVIRCodesDTO>> addDVIRCodes(@Valid @NotNull @RequestBody DVIRCodesDTO dvirCodesDTOObj,
                                                                  @RequestHeader Map<String, String> headers) {
        try {

            DVIRCodes dvirCodes = DVIRCodesMapper.INSTANCE.DvirCodesDtoToDvirCodes(dvirCodesDTOObj);
            DVIRCodes dvirCodesAdded = dvirCodesService.addDvirCodes(dvirCodes, headers);
            DVIRCodesDTO dvirCodesAddedDTO = DVIRCodesMapper.INSTANCE.DvirCodesToDvirCodesDTO(dvirCodesAdded);


            APIResponse<DVIRCodesDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"), dvirCodesAddedDTO,
                    ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (NoRecordsFoundException e) {
            APIResponse<DVIRCodesDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (RecordNotAddedException e) {
            APIResponse<DVIRCodesDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
        } catch (RecordAlreadyExistsException e) {
            APIResponse<DVIRCodesDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
        } catch (Exception e) {
            APIResponse<DVIRCodesDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }

    }
    
    @PutMapping(value = ControllerConstants.DVIR_CODES)
    public ResponseEntity<APIResponse<DVIRCodesDTO>> updateDVIRCodes(@Valid @NotNull @RequestBody DVIRCodesDTO dvirCodesDTOObj,
                                                                  @RequestHeader Map<String, String> headers) {
        try {

            DVIRCodes dvirCodes = DVIRCodesMapper.INSTANCE.DvirCodesDtoToDvirCodes(dvirCodesDTOObj);
            DVIRCodes dvirCodesUpdated = dvirCodesService.updateDvirCodes(dvirCodes, headers);
            DVIRCodesDTO dvirCodesAddedDTO = DVIRCodesMapper.INSTANCE.DvirCodesToDvirCodesDTO(dvirCodesUpdated);


            APIResponse<DVIRCodesDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"), dvirCodesAddedDTO,
                    ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (NoRecordsFoundException e) {
            APIResponse<DVIRCodesDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (RecordNotAddedException e) {
            APIResponse<DVIRCodesDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
        } catch (RecordAlreadyExistsException e) {
            APIResponse<DVIRCodesDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
        } catch (Exception e) {
            APIResponse<DVIRCodesDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }

    }
}
