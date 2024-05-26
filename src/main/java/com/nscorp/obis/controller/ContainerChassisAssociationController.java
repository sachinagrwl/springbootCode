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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.ContainerChassisAssociation;
import com.nscorp.obis.dto.ContainerChassisAssociationDTO;
import com.nscorp.obis.dto.mapper.ContainerChassisAssociationMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.ContainerChassisAssociationService;

@RestController
@RequestMapping("/")
public class ContainerChassisAssociationController {

    @Autowired
    ContainerChassisAssociationService containerChassisAssociationService;

    @GetMapping(value= ControllerConstants.CONTAINER_CHASSIS_ASSOC)
    public ResponseEntity<APIResponse<List<ContainerChassisAssociationDTO>>> getAllControllerChassisAssociations(){

        try {
            List<ContainerChassisAssociationDTO> contChassDtoList = Collections.emptyList();
            List<ContainerChassisAssociation> contChassList = containerChassisAssociationService.getAllControllerChassisAssociations();
            if (contChassList != null && !contChassList.isEmpty()) {
                contChassDtoList = contChassList.stream()
                        .map(ContainerChassisAssociationMapper.INSTANCE::containerChassisAssociationToContainerChassisAssociationDTO)
                        .collect(Collectors.toList());
            }
            APIResponse<List<ContainerChassisAssociationDTO>> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"),contChassDtoList, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (NoRecordsFoundException e){
            APIResponse<List<ContainerChassisAssociationDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (Exception e){
            APIResponse<List<ContainerChassisAssociationDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @PostMapping(value= ControllerConstants.CONTAINER_CHASSIS_ASSOC)
    public ResponseEntity<APIResponse<ContainerChassisAssociationDTO>> addContainerChassisAssociation(@Valid @RequestBody ContainerChassisAssociationDTO associationObjDto, @RequestHeader Map<String,String> headers){
        try {
            ContainerChassisAssociation association = ContainerChassisAssociationMapper.INSTANCE.containerChassisAssociationDTOToContainerChassisAssociation(associationObjDto);

            ContainerChassisAssociation addedAssociation = containerChassisAssociationService.addContainerChassisAssociation(association, headers);
            ContainerChassisAssociationDTO addedTableDto = ContainerChassisAssociationMapper.INSTANCE.containerChassisAssociationToContainerChassisAssociationDTO(addedAssociation);
            APIResponse<ContainerChassisAssociationDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),addedTableDto, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (NoRecordsFoundException e){
            APIResponse<ContainerChassisAssociationDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (SizeExceedException e){
            APIResponse<ContainerChassisAssociationDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
        } catch (RecordAlreadyExistsException e){
            APIResponse<ContainerChassisAssociationDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
        } catch (RecordNotAddedException e){
            APIResponse<ContainerChassisAssociationDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
        } catch (NullPointerException e){
            APIResponse<ContainerChassisAssociationDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
        } catch (Exception e){
            APIResponse<ContainerChassisAssociationDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }
    
    @PutMapping(value= ControllerConstants.CONTAINER_CHASSIS_ASSOC)
	 public ResponseEntity<APIResponse<ContainerChassisAssociationDTO>> updateContainerChassisAssociation(@Valid @NotNull @RequestBody ContainerChassisAssociationDTO conChassisAssociationDto, @RequestHeader Map<String,String> headers) {
	        try {
	        	ContainerChassisAssociation chassisAssociation = containerChassisAssociationService.updateContainerChassisAssociation(
	        			ContainerChassisAssociationMapper.INSTANCE.containerChassisAssociationDTOToContainerChassisAssociation(conChassisAssociationDto), headers);
	            APIResponse<ContainerChassisAssociationDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),
	            		ContainerChassisAssociationMapper.INSTANCE.containerChassisAssociationToContainerChassisAssociationDTO(chassisAssociation), ResponseStatusCode.SUCCESS);
	            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
	        }catch (NoRecordsFoundException e){
	            APIResponse<ContainerChassisAssociationDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
	        }catch (RecordNotAddedException e) {
	        	APIResponse<ContainerChassisAssociationDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
	            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
			}catch (Exception e){
	            APIResponse<ContainerChassisAssociationDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
	        }
	 }
	
	  @DeleteMapping(value= ControllerConstants.CONTAINER_CHASSIS_ASSOC)
	    public ResponseEntity<List<APIResponse<ContainerChassisAssociationDTO>>> expireContainerChassisAssociation(@RequestBody List<ContainerChassisAssociationDTO> associationDTOList, @RequestHeader Map<String,String> headers) {
	        List<APIResponse<ContainerChassisAssociationDTO>> responseDTOList;
	        AtomicInteger errorCount = new AtomicInteger();
	        if (!CollectionUtils.isEmpty(associationDTOList)) {
	            responseDTOList = associationDTOList.stream().map(associationDTO -> {
	                APIResponse<ContainerChassisAssociationDTO> singleDtoDelResponse;
	                try {
	                	ContainerChassisAssociation chassisAssociation = containerChassisAssociationService.expireContainerChassisAssociation(
	    	        			ContainerChassisAssociationMapper.INSTANCE.containerChassisAssociationDTOToContainerChassisAssociation(associationDTO), headers);
	                    singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"),
	                    		ContainerChassisAssociationMapper.INSTANCE.containerChassisAssociationToContainerChassisAssociationDTO(chassisAssociation),
	                            ResponseStatusCode.SUCCESS);
	                } catch (NoRecordsFoundException e) {
	                    errorCount.incrementAndGet();
	                    singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
	                } catch (RecordAlreadyExistsException e) {
	                    errorCount.incrementAndGet();
	                    singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
	                } catch (Exception e) {
	                    errorCount.incrementAndGet();
	                    singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage() + " For Association Id : " + associationDTO.getAssociationId()), ResponseStatusCode.FAILURE);
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
