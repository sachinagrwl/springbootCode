package com.nscorp.obis.controller;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import com.nscorp.obis.exception.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.dto.CashExceptionDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.CashExceptionService;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/")
@Validated
@RestController
@CrossOrigin
@Slf4j
public class CashExceptionController {

    @Autowired
    CashExceptionService cashExceptionService;

    @GetMapping(value = ControllerConstants.CASH_EXCEPTION)
    public ResponseEntity<APIResponse<List<CashExceptionDTO>>> fetchCashExceptions(
            @Size(min = 1, max = 35, message = "customer name length should be between 1 and 35") @RequestParam(name = "customer-name", required = false) String customerName,
            @Size(min = 6, max = 6, message = "customer primary six length should be equal to 6") @RequestParam(name = "cust_primarysix", required = false) String customerPrimarySix) {
        try {
            log.info("fetchCashExceptions - Method Starts");
            List<CashExceptionDTO> cashExceptions = cashExceptionService.getCashException(customerName,
                    customerPrimarySix);
            APIResponse<List<CashExceptionDTO>> responseObj;
            List<String> message = Arrays.asList("Successfully retrieved data!");
            responseObj = new APIResponse<>(message, cashExceptions, ResponseStatusCode.SUCCESS.getStatusCode(),
                    ResponseStatusCode.SUCCESS.toString());
            log.info("fetchCashExceptions - Method Ends");
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (NoRecordsFoundException e) {
            APIResponse<List<CashExceptionDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
            log.error("fetchCashExceptions - Error :" + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (Exception e) {
            APIResponse<List<CashExceptionDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
            log.error("fetchCashExceptions - Error :" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }

    @PostMapping(value = ControllerConstants.CASH_EXCEPTION)
    ResponseEntity<APIResponse<CashExceptionDTO>> addCashExceptions(
            @Valid @RequestBody CashExceptionDTO cashExceptionDTO, @RequestHeader Map<String, String> headers)
            throws SQLException {
        try {
            log.info("addCashExceptions : Method Starts");
            APIResponse<CashExceptionDTO> responseObj = new APIResponse<>(
                    Arrays.asList("Successfully added data!"),
                    cashExceptionService.
                            addCashException(cashExceptionDTO, headers),
                    ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
            log.info("addCashExceptions : Method Ends");
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);

        } catch (NoRecordsFoundException | InvalidDataException e) {
            APIResponse<CashExceptionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
            HttpStatus status = HttpStatus.NOT_FOUND;
            if (e.getClass() == InvalidDataException.class)
                status = HttpStatus.BAD_REQUEST;
            log.error("addCashExceptions : Error " + e.getMessage());
            return ResponseEntity.status(status).body(responseObj);
        } catch (Exception e) {
            APIResponse<CashExceptionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
            log.error("addCashExceptions : Error " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }

    @PutMapping(value = ControllerConstants.CASH_EXCEPTION)
	ResponseEntity<APIResponse<CashExceptionDTO>> updateCashExceptions(
			@Valid @RequestBody CashExceptionDTO cashExceptionDTO, @RequestHeader Map<String, String> headers) throws SQLException {
			
		try {
            APIResponse<CashExceptionDTO> responseObj = new APIResponse<>(
                    Arrays.asList("Successfully updated data!"),cashExceptionService.updateCashException(cashExceptionDTO, headers),
                    ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
			
		} catch (NoRecordsFoundException | InvalidDataException e) {
            APIResponse<CashExceptionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
            HttpStatus status = HttpStatus.NOT_FOUND;
            if (e.getClass() == InvalidDataException.class)
                status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body(responseObj);
        } catch (Exception e) {
            APIResponse<CashExceptionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
	}

}
