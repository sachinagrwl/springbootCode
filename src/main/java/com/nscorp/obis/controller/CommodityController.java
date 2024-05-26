package com.nscorp.obis.controller;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.common.NullOrNotBlank;
import com.nscorp.obis.domain.Commodity;
import com.nscorp.obis.dto.CommodityDTO;
import com.nscorp.obis.dto.mapper.CommodityMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.PaginatedResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.CommodityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RequestMapping("/")
@Validated
@RestController
@CrossOrigin
@Slf4j
public class CommodityController {

    @Autowired
    CommodityService commodityService;

    @GetMapping(value = ControllerConstants.COMMODITY)
    ResponseEntity<APIResponse<PaginatedResponse<CommodityDTO>>> searchCommodity(
            @NullOrNotBlank(max = 60, message = "Commodity Code Long Name cannot be more than 60 ")
            @RequestParam(name = "stcc-longname", required = false) String longName,

            @NullOrNotBlank(max = 1, message = "Hazard Indicator should be length of 1")
            @Pattern(regexp = "^(Y|N|D|)$", message = "Only Y, N, D & Null is allowed")
            @RequestParam(name = "stcc-hazind", required = false) String hazardIndicator,

            @Digits(integer = 5, fraction = 0, message = "Commodity Code 5 length cannot be more than 5")
            @Min(value = 0, message = "Commodity Code 5 value must be greater than or equal to 0")
            @RequestParam(name = "stcc-cd5", required = false) Integer commodityCode5,

            @Digits(integer = 2, fraction = 0, message = "Commodity Code 2 length cannot be more than 2")
            @Min(value = 0, message = "Commodity Code 2 value must be greater than or equal to 0")
            @RequestParam(name = "stcc-cd2", required = false) Integer commodityCode2,

            @Digits(integer = 2, fraction = 0, message = "Commodity Sub Code length cannot be more than 2")
            @Min(value = 0, message = "Commodity Sub Code value must be greater than or equal to 0")
            @RequestParam(name = "stcc-subcode", required = false) Integer commoditySubCode,

            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "20") Integer pageSize,
            @RequestParam(name = "sort", required = false, defaultValue = "commodityCodeLongName, asc") String[] sort

    ) {
        try {
            log.info("searchCommodity - Method Starts");
            if (longName == null && hazardIndicator == null && commodityCode5 == null
                    && commodityCode2 == null && commoditySubCode == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new APIResponse<>(Arrays.asList("Parameters are missing!"),
                                ResponseStatusCode.INFORMATION.getStatusCode(),
                                ResponseStatusCode.INFORMATION.toString()));
            }

            PaginatedResponse<CommodityDTO> response = commodityService.fetchCommodity(longName,
                    hazardIndicator, commodityCode5, commodityCode2, commoditySubCode, pageSize, pageNumber, sort);

            APIResponse<PaginatedResponse<CommodityDTO>> apiResponse = new APIResponse<>(
                    Arrays.asList("Successfully retrieve data!"), response, ResponseStatusCode.SUCCESS.getStatusCode(),
                    ResponseStatusCode.SUCCESS.toString());
            log.info("searchCommodity : Method Ends");
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

        } catch (NoRecordsFoundException e) {
            APIResponse<PaginatedResponse<CommodityDTO>> responseObj = new APIResponse<>(
                    Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION.getStatusCode(),
                    ResponseStatusCode.INFORMATION.toString());
            log.error("searchCommodity : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (InvalidDataException e) {
            APIResponse<PaginatedResponse<CommodityDTO>> responseObj = new APIResponse<>(
                    Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION.getStatusCode(),
                    ResponseStatusCode.INFORMATION.toString());
            log.error("searchCommodity : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
        } catch (Exception e) {
            APIResponse<PaginatedResponse<CommodityDTO>> responseObj = new APIResponse<>(
                    Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION.getStatusCode(),
                    ResponseStatusCode.INFORMATION.toString());
            log.error("searchCommodity : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }

    @PostMapping(value = ControllerConstants.COMMODITY)
    ResponseEntity<APIResponse<CommodityDTO>> addCommodity(
            @Valid @RequestBody CommodityDTO commodityDTO, @RequestHeader Map<String, String> headers)
            throws SQLException {
        try {
            log.info("addCommodity : Method Starts");
            APIResponse<CommodityDTO> responseObj = new APIResponse<>(
                    Arrays.asList("Successfully added data!"),
                    commodityService.addCommodity(commodityDTO, headers),
                    ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
            log.info("addCommodity : Method Ends");
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);

        } catch (InvalidDataException e) {
            APIResponse<CommodityDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
        } catch (Exception e) {
            APIResponse<CommodityDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
            log.error("addCommodity : Error " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }

    @PutMapping(value = ControllerConstants.COMMODITY)
    ResponseEntity<APIResponse<CommodityDTO>> updateCommodity(
            @Valid @RequestBody CommodityDTO commodityDTO, @RequestHeader Map<String, String> headers)
            throws SQLException {
        try {
            log.info("updateCommodity : Method Starts");
            APIResponse<CommodityDTO> responseObj = new APIResponse<>(
                    Arrays.asList("Successfully updated data!"),
                    commodityService.updateCommodity(commodityDTO, headers),
                    ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
            log.info("updateCommodity : Method Ends");
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);

        } catch (NoRecordsFoundException | InvalidDataException e) {
            APIResponse<CommodityDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
            HttpStatus status = HttpStatus.NOT_FOUND;
            if (e.getClass() == InvalidDataException.class)
                status = HttpStatus.BAD_REQUEST;
            log.error("updateCommodity : Error " + e.getMessage());
            return ResponseEntity.status(status).body(responseObj);
        } catch (Exception e) {
            APIResponse<CommodityDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
            log.error("updateCommodity : Error " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }

    @PutMapping(value = ControllerConstants.COMMODITY + "/delete")
    public ResponseEntity<List<APIResponse<CommodityDTO>>> deleteCommodity(@Valid @RequestBody List<CommodityDTO> dtoListToBeDeleted
            , @RequestHeader Map<String, String> headers) {
        List<APIResponse<CommodityDTO>> responseDTOList;
        AtomicInteger errorCount = new AtomicInteger();
        if (!CollectionUtils.isEmpty(dtoListToBeDeleted)) {
            responseDTOList = dtoListToBeDeleted.stream().map(dto -> {
                APIResponse<CommodityDTO> singleDtoDelResponse;
                try {
                    Commodity deleteCommodity = commodityService.deleteCommodity(CommodityMapper.INSTANCE.CommodityDTOToCommodity(dto), headers);
                    singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully updated data!"),
                            CommodityMapper.INSTANCE.CommodityToCommodityDTO(deleteCommodity),
                            ResponseStatusCode.SUCCESS);
                } catch (InvalidDataException e) {
                    errorCount.incrementAndGet();
                    singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
                } catch (NoRecordsFoundException e) {
                    errorCount.incrementAndGet();
                    singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
                } catch (Exception e) {
                    errorCount.incrementAndGet();
                    singleDtoDelResponse = new APIResponse<>(
                            Arrays.asList(e.getMessage()
                                    + " For Commodity Code 5: " + dto.getCommodityCode5()
                                    + " For Commodity Code 2: " + dto.getCommodityCode2()
                                    + " For Commodity Sub Code: " + dto.getCommoditySubCode()
                            ), ResponseStatusCode.FAILURE);
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

    @PutMapping(value = ControllerConstants.COMMODITY + "/restore")
    public ResponseEntity<List<APIResponse<CommodityDTO>>> restoreCommodity(@Valid @RequestBody List<CommodityDTO> dtoListToBeRestore
            , @RequestHeader Map<String, String> headers) {
        List<APIResponse<CommodityDTO>> responseDTOList;
        AtomicInteger errorCount = new AtomicInteger();
        if (!CollectionUtils.isEmpty(dtoListToBeRestore)) {
            responseDTOList = dtoListToBeRestore.stream().map(dto -> {
                APIResponse<CommodityDTO> singleDtoDelResponse;
                try {
                    Commodity deleteCommodity = commodityService.restoreCommodity(CommodityMapper.INSTANCE.CommodityDTOToCommodity(dto), headers);
                    singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully updated data!"),
                            CommodityMapper.INSTANCE.CommodityToCommodityDTO(deleteCommodity),
                            ResponseStatusCode.SUCCESS);
                } catch (InvalidDataException e) {
                    errorCount.incrementAndGet();
                    singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
                } catch (NoRecordsFoundException e) {
                    errorCount.incrementAndGet();
                    singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
                } catch (Exception e) {
                    errorCount.incrementAndGet();
                    singleDtoDelResponse = new APIResponse<>(
                            Arrays.asList(e.getMessage()
                                    + " For Commodity Code 5: " + dto.getCommodityCode5()
                                    + " For Commodity Code 2: " + dto.getCommodityCode2()
                                    + " For Commodity Sub Code: " + dto.getCommoditySubCode()
                            ), ResponseStatusCode.FAILURE);
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
