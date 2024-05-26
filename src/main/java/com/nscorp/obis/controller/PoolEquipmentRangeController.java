package com.nscorp.obis.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.PoolEquipmentRange;
import com.nscorp.obis.dto.PoolEquipmentRangeDTO;
import com.nscorp.obis.dto.mapper.PoolEquipmentRangeMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.PoolEquipmentRangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = ControllerConstants.POOL_EQUIPMENT_RANGE)
@Validated
public class PoolEquipmentRangeController {

    @Autowired
    PoolEquipmentRangeService poolEquipmentRangeService;

    @GetMapping
    public ResponseEntity<APIResponse<List<PoolEquipmentRangeDTO>>> getAllEquipmentRanges() {
        try {
            List<PoolEquipmentRangeDTO> poolEquipmentRangeDTOList = Collections.emptyList();
            List<PoolEquipmentRange> poolEquipmentRanges = poolEquipmentRangeService.getAllPoolEquipmentRanges();
            if (poolEquipmentRanges != null && !poolEquipmentRanges.isEmpty()) {
                poolEquipmentRangeDTOList = poolEquipmentRanges.stream()
                        .map(PoolEquipmentRangeMapper.INSTANCE::poolEquipmentRangeToPoolEquipmentRangeDto)
                        .collect(Collectors.toList());
            }
            APIResponse<List<PoolEquipmentRangeDTO>> listAPIResponse = new APIResponse<>(Arrays.asList("Successfully retrieved data!"), poolEquipmentRangeDTOList, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(listAPIResponse);
        } catch (NoRecordsFoundException e) {
            APIResponse<List<PoolEquipmentRangeDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (Exception e) {
            APIResponse<List<PoolEquipmentRangeDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<PoolEquipmentRangeDTO>> addPoolEquipmentRange(@Valid @NotNull @RequestBody PoolEquipmentRangeDTO poolEquipmentRangeDTO,
                                                          @RequestHeader Map<String, String> headers) {

        try {
            PoolEquipmentRange rangeAdded = poolEquipmentRangeService.addPoolEquipmentRange(PoolEquipmentRangeMapper.INSTANCE.poolEquipmentRangeDtoToPoolEquipmentRange(poolEquipmentRangeDTO), headers);
            APIResponse<PoolEquipmentRangeDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),
                    PoolEquipmentRangeMapper.INSTANCE.poolEquipmentRangeToPoolEquipmentRangeDto(rangeAdded), ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (RecordNotAddedException e){
            APIResponse<PoolEquipmentRangeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (NoRecordsFoundException e){
            APIResponse<PoolEquipmentRangeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (NullPointerException e) {
            APIResponse<PoolEquipmentRangeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
        } catch (Exception e) {
            APIResponse<PoolEquipmentRangeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }

    @PutMapping
    public ResponseEntity<APIResponse<PoolEquipmentRangeDTO>> updatePoolEquipmentRange(@Valid @NotNull @RequestBody PoolEquipmentRangeDTO poolEquipmentRangeDTO,
                                                                                    @RequestHeader Map<String, String> headers) {

        try {
            PoolEquipmentRange rangeUpdated = poolEquipmentRangeService.updatePoolEquipmentRange(PoolEquipmentRangeMapper.INSTANCE.poolEquipmentRangeDtoToPoolEquipmentRange(poolEquipmentRangeDTO), headers);
            APIResponse<PoolEquipmentRangeDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),
                    PoolEquipmentRangeMapper.INSTANCE.poolEquipmentRangeToPoolEquipmentRangeDto(rangeUpdated), ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (RecordNotAddedException e){
            APIResponse<PoolEquipmentRangeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (NoRecordsFoundException e){
            APIResponse<PoolEquipmentRangeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (NullPointerException e) {
            APIResponse<PoolEquipmentRangeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
        } catch (Exception e) {
            APIResponse<PoolEquipmentRangeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }

    @DeleteMapping
    public ResponseEntity<List<APIResponse<PoolEquipmentRangeDTO>>> deleteRange(@Valid @NotNull @RequestBody List<PoolEquipmentRangeDTO> rangeDTOList){
        List<APIResponse<PoolEquipmentRangeDTO>> response;
        AtomicInteger errorCount = new AtomicInteger();
        if (rangeDTOList != null && !rangeDTOList.isEmpty()) {
            response = rangeDTOList.stream().map(rangeObjDto -> {
                        APIResponse<PoolEquipmentRangeDTO> singleDtoDelResponse;
                        try {
                            poolEquipmentRangeService.deletePoolEquipmentRange(PoolEquipmentRangeMapper.INSTANCE.poolEquipmentRangeDtoToPoolEquipmentRange(rangeObjDto));
                            singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"),rangeObjDto, ResponseStatusCode.SUCCESS);
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
