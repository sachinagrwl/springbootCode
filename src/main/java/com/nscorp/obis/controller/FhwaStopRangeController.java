package com.nscorp.obis.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.FhwaStopRange;
import com.nscorp.obis.dto.FhwaStopRangeDTO;
import com.nscorp.obis.dto.mapper.FhwaStopRangeMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.FhwaStopRangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = ControllerConstants.FMCSA_EXPIRATION)
@Validated
public class FhwaStopRangeController {

    @Autowired
    FhwaStopRangeService fhwaStopRangeService;

    @GetMapping
    public ResponseEntity<APIResponse<List<FhwaStopRangeDTO>>> getAllRanges(@RequestParam(required = false, name = "equipmentInit") String equipmentInit,
                                                                            @RequestParam(required = false, name = "equipmentType") String equipmentType,
                                                                            @RequestParam(required = false, name = "equipmentNumberLow") BigDecimal equipmentNumberLow,
                                                                            @RequestParam(required = false, name = "equipmentNumberHigh") BigDecimal equipmentNumberHigh) {
        try {
            List<FhwaStopRangeDTO> fhwaStopRangeDTOList = Collections.emptyList();
            List<FhwaStopRange> fhwaStopRanges = fhwaStopRangeService.getAllFhwaStopRanges(equipmentInit, equipmentType, equipmentNumberLow, equipmentNumberHigh);
            if (fhwaStopRanges != null && !fhwaStopRanges.isEmpty()) {
                fhwaStopRangeDTOList = fhwaStopRanges.stream()
                        .map(FhwaStopRangeMapper.INSTANCE::fhwaStopRangeToFhwaStopRangeDTO)
                        .collect(Collectors.toList());
            }
            APIResponse<List<FhwaStopRangeDTO>> listAPIResponse = new APIResponse<>(Arrays.asList("Successfully retrieved data!"), fhwaStopRangeDTOList, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(listAPIResponse);
        } catch (NoRecordsFoundException e) {
            APIResponse<List<FhwaStopRangeDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (Exception e) {
            APIResponse<List<FhwaStopRangeDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<FhwaStopRangeDTO>> addRange(@Valid @NotNull @RequestBody FhwaStopRangeDTO fhwaStopRangeDTO, @RequestHeader Map<String, String> headers) {

        try {
            FhwaStopRange fhwaStopRange = FhwaStopRangeMapper.INSTANCE.fhwaStopRangeDTOToFhwaStopRange(fhwaStopRangeDTO);
            FhwaStopRange fhwaStopRangeAdded = fhwaStopRangeService.addFhwaStopRange(fhwaStopRange, headers);
            FhwaStopRangeDTO addedFhwaStopRange = FhwaStopRangeMapper.INSTANCE.fhwaStopRangeToFhwaStopRangeDTO(fhwaStopRangeAdded);
            APIResponse<FhwaStopRangeDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),addedFhwaStopRange, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (NoRecordsFoundException e){
            APIResponse<FhwaStopRangeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (SizeExceedException e){
            APIResponse<FhwaStopRangeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
        } catch (RecordAlreadyExistsException e){
            APIResponse<FhwaStopRangeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
        } catch (RecordNotAddedException e){
            APIResponse<FhwaStopRangeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
        } catch (NullPointerException e){
            APIResponse<FhwaStopRangeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
        } catch (Exception e){
            APIResponse<FhwaStopRangeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }

    @PutMapping
    public ResponseEntity<APIResponse<FhwaStopRangeDTO>> updateRange(@Valid @NotNull @RequestBody FhwaStopRangeDTO fhwaStopRangeDTO, @RequestHeader Map<String,String> headers) {
        try {
            FhwaStopRange fhwaStopRange = FhwaStopRangeMapper.INSTANCE.fhwaStopRangeDTOToFhwaStopRange(fhwaStopRangeDTO);
            FhwaStopRange fhwaStopRangeUpdate = fhwaStopRangeService.updateFhwaStopRange(fhwaStopRange, headers);
            FhwaStopRangeDTO updateFhwaStopRange = FhwaStopRangeMapper.INSTANCE.fhwaStopRangeToFhwaStopRangeDTO(fhwaStopRangeUpdate);
            APIResponse<FhwaStopRangeDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),updateFhwaStopRange, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (NoRecordsFoundException e){
            APIResponse<FhwaStopRangeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (RecordNotAddedException e){
            APIResponse<FhwaStopRangeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
        } catch (NullPointerException e){
            APIResponse<FhwaStopRangeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
        } catch (SizeExceedException e){
            APIResponse<FhwaStopRangeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
        } catch (Exception e){
            APIResponse<FhwaStopRangeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }

    @DeleteMapping
    public ResponseEntity<List<APIResponse<FhwaStopRangeDTO>>> deleteRanges(@Valid @NotNull @RequestBody List<FhwaStopRangeDTO> rangesObjList){
        List<APIResponse<FhwaStopRangeDTO>> response;
        AtomicInteger errorCount = new AtomicInteger();
        if (rangesObjList != null && !rangesObjList.isEmpty()) {
            response = rangesObjList.stream().map(rangesObjDto -> {
                        APIResponse<FhwaStopRangeDTO> singleDtoDelResponse;
                        try {
                            fhwaStopRangeService.deleteFhwaStopRange(FhwaStopRangeMapper.INSTANCE.fhwaStopRangeDTOToFhwaStopRange(rangesObjDto));
                            singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"),rangesObjDto, ResponseStatusCode.SUCCESS);
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
