package com.nscorp.obis.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.TermFreeDay;
import com.nscorp.obis.domain.Terminal;
import com.nscorp.obis.dto.TermFreeDayDTO;
import com.nscorp.obis.dto.mapper.TermFreeDayMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.repository.TerminalRepository;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.TermFreeDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ControllerConstants.TERM_FREE_DAY)
public class TermFreeDayController {

    @Autowired
    TermFreeDayService termFreeDayService;

    @Autowired
    private TerminalRepository terminalRepo;

    @GetMapping
    public ResponseEntity<APIResponse<List<TermFreeDayDTO>>> getAllFreeDays(@RequestParam(required = false, name = "termId") List<Long> termId,
                                                                            @RequestParam(required = false, name = "closed-date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate closedDate,
                                                                            @RequestParam(required = false, name = "close-code") String closeRsnCd,
                                                                            @RequestParam(required = false, name = "close-from-time") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime closeFromTime){

        try {
            List<TermFreeDayDTO> termFreeDayDTOList = Collections.emptyList();
            List<TermFreeDay> termFreeDayList = termFreeDayService.getAllFreeDays(termId,closedDate,closeRsnCd,closeFromTime);
            if(termFreeDayList != null && !termFreeDayList.isEmpty()){
                termFreeDayDTOList = termFreeDayList.stream()
                        .map(TermFreeDayMapper.INSTANCE::TermFreeDayToTermFreeDayDTO)
                        .collect(Collectors.toList());
            }
            APIResponse<List<TermFreeDayDTO>> listAPIResponse = new APIResponse<>(Arrays.asList("Successfully retrieved data!"),termFreeDayDTOList, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(listAPIResponse);
        } catch (NoRecordsFoundException e){
            APIResponse<List<TermFreeDayDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (Exception e){
            APIResponse<List<TermFreeDayDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<APIResponse<TermFreeDayDTO>>> addTermDay(
            @Valid @NotNull @NotBlank @RequestBody TermFreeDayDTO termObj, @RequestHeader Map<String, String> headers) {
        List<APIResponse<TermFreeDayDTO>> response;
        AtomicInteger errorCount = new AtomicInteger();
        if (!CollectionUtils.isEmpty(termObj.getTerminalList())) {
            response = termObj.getTerminalList().stream().map(terminal -> {
                APIResponse<TermFreeDayDTO> singleDtoDelResponse;
                try {
                    termObj.setTermId(terminal.getTerminalId());
                    TermFreeDay termDay = TermFreeDayMapper.INSTANCE.TermFreeDayDTOToTermFreeDay(termObj);
                    TermFreeDay updateTermDay = termFreeDayService.addTermDay(termDay, headers);
                    TermFreeDayDTO termDayDto = TermFreeDayMapper.INSTANCE.TermFreeDayToTermFreeDayDTO(updateTermDay);
                    singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully Added data!"), termDayDto,
                            ResponseStatusCode.SUCCESS);
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
    
    @PutMapping
    public ResponseEntity<APIResponse<TermFreeDayDTO>> updateTermDay(@Valid @NotNull @RequestBody TermFreeDayDTO termObj, @RequestHeader Map<String, String> headers) {
    	
    	try {
    		TermFreeDay termDay = TermFreeDayMapper.INSTANCE.TermFreeDayDTOToTermFreeDay(termObj);
    		TermFreeDay updateTermDay = termFreeDayService.updateTermDay(termDay, headers);
    		TermFreeDayDTO termDayDto = TermFreeDayMapper.INSTANCE.TermFreeDayToTermFreeDayDTO(updateTermDay);
            Terminal terminal = terminalRepo.findByTerminalId(termObj.getTermId());
            termDayDto.setTerminalName(terminal.getTerminalName());
    		APIResponse<TermFreeDayDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),termDayDto, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (SizeExceedException e){
			APIResponse<TermFreeDayDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (NullPointerException e){
			APIResponse<TermFreeDayDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<TermFreeDayDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e){
			APIResponse<TermFreeDayDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
    	}
    	
    }

    @DeleteMapping
    public ResponseEntity<List<APIResponse<TermFreeDayDTO>>> deleteWeight(@Valid @NotNull @RequestBody List<TermFreeDayDTO> termFreeDayObjList){
        List<APIResponse<TermFreeDayDTO>> response;
        AtomicInteger errorCount = new AtomicInteger();
        if (termFreeDayObjList != null && !termFreeDayObjList.isEmpty()) {
            response = termFreeDayObjList.stream().map(termFreeDayObjDto -> {
                        APIResponse<TermFreeDayDTO> singleDtoDelResponse;
                        try {
                            termFreeDayService.deleteTermFreeDay(TermFreeDayMapper.INSTANCE.TermFreeDayDTOToTermFreeDay(termFreeDayObjDto));
                            singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"),termFreeDayObjDto, ResponseStatusCode.SUCCESS);
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
    @GetMapping(value = ControllerConstants.TERM_FREE_DAY_REASON_DESC)
    public ResponseEntity<APIResponse<List<String>>> getAllReasonDesc() {
        try {
            List<String> reasonDescList = termFreeDayService.getAllReasonDesc();
            APIResponse<List<String>> listAPIResponse = new APIResponse<>(Arrays.asList("Successfully retrieved data!"),reasonDescList, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(listAPIResponse);
        } catch (Exception e){
            APIResponse<List<String>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }
    
}
