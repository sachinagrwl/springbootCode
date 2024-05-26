package com.nscorp.obis.controller;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.NotifyCustomerInitView;
import com.nscorp.obis.dto.NotifyCustomerInitViewDTO;
import com.nscorp.obis.dto.mapper.NotifyCustomerInitViewMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.NotifyCustomerInitViewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class NotifyCustomerInitViewController {

    @Autowired
    NotifyCustomerInitViewService notifyCustomerInitViewService;

    private static final Logger log = LoggerFactory.getLogger(NotifyCustomerInitViewController.class);

    @GetMapping(value= ControllerConstants.NOTIFY_CUST_INIT_VIEW)
    public ResponseEntity<APIResponse<List<NotifyCustomerInitViewDTO>>> getAllCustomerInitialsView(){

        log.info("'NotifyCustomerInitViewController' method starts!");
        try {
            List<NotifyCustomerInitViewDTO> custInitialsViewDtoList = Collections.emptyList();
            List<NotifyCustomerInitView> custInitialsViewList = notifyCustomerInitViewService.getAllCustomerInitialsView();
            if (custInitialsViewList != null && !custInitialsViewList.isEmpty()) {
                custInitialsViewDtoList = custInitialsViewList.stream()
                        .map(NotifyCustomerInitViewMapper.INSTANCE::notifyCustomerInitViewToNotifyCustomerInitViewDTO)
                        .collect(Collectors.toList());
            }
            APIResponse<List<NotifyCustomerInitViewDTO>> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"),custInitialsViewDtoList, ResponseStatusCode.SUCCESS);
            log.info("'NotifyCustomerInitViewController' method ends!");
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (NoRecordsFoundException e){
            log.debug("NotifyCustomerInitViewController' NoRecordsFoundException encountered!");
            APIResponse<List<NotifyCustomerInitViewDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (Exception e){
            log.debug("NotifyCustomerInitViewController' Exception encountered!");
            APIResponse<List<NotifyCustomerInitViewDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }

    }
}
