package com.nscorp.obis.controller;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.ResourceList;
import com.nscorp.obis.dto.ResourceListDTO;
import com.nscorp.obis.dto.mapper.ResourceListMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.ResourceListService;
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
public class ResourceListController {
	
	@Autowired
	ResourceListService resourceListService;
	
	private static final Logger logger = LoggerFactory.getLogger(ResourceListController.class);
	
	@GetMapping(value= ControllerConstants.GET_SEC_RESOURCE)
	public ResponseEntity<APIResponse<List<ResourceListDTO>>> getAllResourceList() {
		try {
			logger.info("Starts Here");
			List<ResourceListDTO> resourceDtoList = Collections.emptyList();
			List<ResourceList> resourceList = resourceListService.getAllResourceList();
			if (resourceList != null && !resourceList.isEmpty()) {
				resourceDtoList = resourceList.stream()
						.map(ResourceListMapper.INSTANCE::resourceListToResourceListDTO)
						.collect(Collectors.toList());
			}
			APIResponse<List<ResourceListDTO>> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"),resourceDtoList, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<List<ResourceListDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
//			return generateResponse(e.getMessage(),HttpStatus.MULTI_STATUS, ResponseStatusCode.FAILURE,null);
		} catch (Exception e){
			APIResponse<List<ResourceListDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
//			return generateResponse(e.getMessage(),HttpStatus.MULTI_STATUS, ResponseStatusCode.FAILURE,null);
		}
	}
}
