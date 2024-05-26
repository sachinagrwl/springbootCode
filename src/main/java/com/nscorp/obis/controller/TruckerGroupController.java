package com.nscorp.obis.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.TruckerGroup;
import com.nscorp.obis.dto.TruckerGroupDTO;
import com.nscorp.obis.dto.mapper.TruckerGroupMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.TruckerGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
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
@RequestMapping("/")
public class TruckerGroupController {

    @Autowired
    TruckerGroupService truckerGroupService;

    @GetMapping(value= ControllerConstants.TRUCKER_GROUP)
    public ResponseEntity<APIResponse<List<TruckerGroupDTO>>> getAllTruckerGroups(){

        try {
            List<TruckerGroupDTO> truckerGroupDtoList = Collections.emptyList();
            List<TruckerGroup> truckerGroupList = truckerGroupService.getAllTruckerGroups();
            if (truckerGroupList != null && !truckerGroupList.isEmpty()) {
                truckerGroupDtoList = truckerGroupList.stream()
                        .map(TruckerGroupMapper.INSTANCE::truckerGroupToTruckerGroupDTO)
                        .collect(Collectors.toList());
            }
            APIResponse<List<TruckerGroupDTO>> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"),truckerGroupDtoList, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (NoRecordsFoundException e){
            APIResponse<List<TruckerGroupDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (Exception e){
            APIResponse<List<TruckerGroupDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @PostMapping(value= ControllerConstants.TRUCKER_GROUP)
    public ResponseEntity<APIResponse<TruckerGroupDTO>> addTruckerGroup(@Valid @RequestBody TruckerGroupDTO truckerGroupObjDto, @RequestHeader Map<String,String> headers){
        try {
            TruckerGroup truckerGroupObj = TruckerGroupMapper.INSTANCE.truckerGroupDTOToTruckerGroup(truckerGroupObjDto);

            TruckerGroup addedTruckerGroup = truckerGroupService.addTruckerGroup(truckerGroupObj, headers);
            TruckerGroupDTO addedTruckerGroupDto = TruckerGroupMapper.INSTANCE.truckerGroupToTruckerGroupDTO(addedTruckerGroup);
            APIResponse<TruckerGroupDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),addedTruckerGroupDto, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (NoRecordsFoundException e){
            APIResponse<TruckerGroupDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (SizeExceedException e){
            APIResponse<TruckerGroupDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
        } catch (RecordAlreadyExistsException e){
            APIResponse<TruckerGroupDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
        } catch (RecordNotAddedException e){
            APIResponse<TruckerGroupDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
        } catch (NullPointerException e){
            APIResponse<TruckerGroupDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
        } catch (Exception e){
            APIResponse<TruckerGroupDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }

    @PutMapping(value= ControllerConstants.TRUCKER_GROUP)
    public ResponseEntity<APIResponse<TruckerGroupDTO>> updateTruckerGroup(@Valid @NotNull @RequestBody TruckerGroupDTO truckerGroupDto, @RequestHeader Map<String,String> headers) {
        try {
            TruckerGroup truckerGroupObj = truckerGroupService.updateTruckerGroup(
                    TruckerGroupMapper.INSTANCE.truckerGroupDTOToTruckerGroup(truckerGroupDto), headers);
            APIResponse<TruckerGroupDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),
                    TruckerGroupMapper.INSTANCE.truckerGroupToTruckerGroupDTO(truckerGroupObj), ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        }catch (NoRecordsFoundException e){
            APIResponse<TruckerGroupDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        }catch (RecordNotAddedException e) {
            APIResponse<TruckerGroupDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
        }catch (Exception e){
            APIResponse<TruckerGroupDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }

    @DeleteMapping(value = ControllerConstants.TRUCKER_GROUP)
    public ResponseEntity<List<APIResponse<TruckerGroupDTO>>> deleteTruckerGroup(@RequestBody List<TruckerGroupDTO> truckerGroupObjDtoList) {
        List<APIResponse<TruckerGroupDTO>> responseDTOList;
        AtomicInteger errorCount = new AtomicInteger();
        if (!CollectionUtils.isEmpty(truckerGroupObjDtoList)) {
            responseDTOList = truckerGroupObjDtoList.stream().map(truckerGroupObjDto -> {
                APIResponse<TruckerGroupDTO> singleDtoDelResponse;
                try {
                    TruckerGroup truckerGroupObj = truckerGroupService.deleteTruckerGroup(TruckerGroupMapper.INSTANCE.truckerGroupDTOToTruckerGroup(truckerGroupObjDto));
                    singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"),
                            TruckerGroupMapper.INSTANCE.truckerGroupToTruckerGroupDTO(truckerGroupObj),
                            ResponseStatusCode.SUCCESS);
                } catch (NoRecordsFoundException e) {
                    errorCount.incrementAndGet();
                    singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
                } catch (RecordNotAddedException e) {
                    errorCount.incrementAndGet();
                    singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
                }
                catch (Exception e) {
                    errorCount.incrementAndGet();
                    singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage() + " For Trucker Group Code : " + truckerGroupObjDto.getTruckerGroupCode()), ResponseStatusCode.FAILURE);
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
