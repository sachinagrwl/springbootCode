package com.nscorp.obis.controller;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.AARType;
import com.nscorp.obis.domain.CIFExcpView;
import com.nscorp.obis.dto.AARTypeDTO;
import com.nscorp.obis.dto.CIFExcpViewDTO;
import com.nscorp.obis.dto.mapper.AARTypeMapper;
import com.nscorp.obis.dto.mapper.CashInFistMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.CashInFistServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/")
public class CashInFistController {

    @Autowired
    private CashInFistServiceImpl cashInFistServiceImpl;

    @GetMapping(value= ControllerConstants.CASH_IN_FIST)
    public ResponseEntity<APIResponse<List<CIFExcpViewDTO>>> getAllCashData()
    {
        try{
            List<CIFExcpView> cashList = cashInFistServiceImpl.getCashData();
            List<CIFExcpViewDTO> cifExcpViewDtoList = Collections.emptyList();
            if (cashList != null && !cashList.isEmpty()) {
                cifExcpViewDtoList = cashList.stream().map(CashInFistMapper.INSTANCE::CIFExcpViewToCIFExcpViewDTO)
                        .collect(Collectors.toList());
            }
            APIResponse<List<CIFExcpViewDTO>> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"),cifExcpViewDtoList, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        }  catch (NoRecordsFoundException e) {
            APIResponse<List<CIFExcpViewDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (Exception e) {
            APIResponse<List<CIFExcpViewDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }

    }
}
