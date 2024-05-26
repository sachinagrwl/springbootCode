package com.nscorp.obis.controller;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.ReNotifyView;
import com.nscorp.obis.domain.SortFilter;
import com.nscorp.obis.dto.ReNotifyViewDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.PaginationWrapper;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.ReNotifyViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/")
@Validated
public class ReNotifyViewController {
    @Autowired
    ReNotifyViewService reNotifyViewService;
    @GetMapping(ControllerConstants.GET_RENOTIFY)
    public ResponseEntity<APIResponse<ReNotifyViewDTO>> getReNotifyViewByTermId(
            @NotNull(message = "term-id should not be NULL.") @RequestParam(name="term-id") double termId, @RequestParam(name="ntfy-stat", required = false) String notifyState, @RequestParam(name="equipInit", required = false) String equipInit, @RequestParam(name="equipNbr", required = false) Integer equipNbr, @RequestParam(name="notifyCustomerName", required = false) String notifyCustomerName, @RequestParam(required = false,defaultValue = "0") int pageNumber, @RequestParam(required = false,defaultValue = "20") int pageSize, @RequestParam(required = false,defaultValue = "notifyCustomerName,equipInit,equipNbr") String[] sort){
        try {
            Pageable pageable=PageRequest.of(pageNumber==0?pageNumber:pageNumber-1, pageSize, Sort.by(SortFilter.sortOrder(sort)));
            Page<ReNotifyView> reNotifyViews = reNotifyViewService.getNotifications(termId, pageable,equipInit!=null?equipInit.replace("%20"," "):null,equipNbr,notifyCustomerName!=null?notifyCustomerName.replace("%20"," "):null);
            int count = reNotifyViewService.getCount(termId,notifyState);
            int delayCount = reNotifyViewService.getDelayCount(termId,notifyCustomerName);
            List<ReNotifyView> reNotifyViewList = reNotifyViews.getContent();
            PaginationWrapper paginationWrapper = new PaginationWrapper(reNotifyViewList, reNotifyViews.getNumber() + 1,
                    reNotifyViews.getTotalPages(), reNotifyViews.getTotalElements());
            ReNotifyViewDTO reNotifyViewDTO = new ReNotifyViewDTO();
            reNotifyViewDTO.setReNotifyViewList(paginationWrapper);
            reNotifyViewDTO.setCountNotifyState(count);
            reNotifyViewDTO.setCountDelyStat(delayCount);
            APIResponse<ReNotifyViewDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"), reNotifyViewDTO, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        }catch (NoRecordsFoundException e ){
            APIResponse<ReNotifyViewDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        }catch (Exception e){
            APIResponse<ReNotifyViewDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }
}
