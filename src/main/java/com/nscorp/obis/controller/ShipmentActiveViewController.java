package com.nscorp.obis.controller;

import java.math.BigDecimal;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.ShipmentActiveView;
import com.nscorp.obis.dto.ShipmentActiveViewDTO;
import com.nscorp.obis.dto.mapper.ShipmentActiveViewMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.ShipmentActiveViewService;

@RestController
@RequestMapping("/")
public class ShipmentActiveViewController {
	
	@Autowired
	ShipmentActiveViewService shipService;

	private static final Logger logger = LoggerFactory.getLogger(ShipmentActiveViewController.class);
	
	  @GetMapping(value= ControllerConstants.NOTIFY_ACTIVE_SHIPMENT)
	  public ResponseEntity<APIResponse<List<ShipmentActiveViewDTO>>> getShipment(@RequestParam(required = true, name = "equip-init") String equipInit,
	                                                                               @RequestParam(required = true, name = "equip-nbr") BigDecimal equipNbr,
	                                                                               @RequestParam(required = true, name = "equip-tp") String equipTp,
	                                                                               @RequestParam(required = true, name = "equip-id") String equipId) {
	        try {
	            List<ShipmentActiveViewDTO> shipmentActiveDtoList = Collections.emptyList();
	            List<ShipmentActiveView> shipmentActiveList = shipService.getShipment(equipInit, equipNbr, equipTp, equipId);
	            if (shipmentActiveList != null && !shipmentActiveList.isEmpty()) {
	            	shipmentActiveDtoList = shipmentActiveList.stream()
	                        .map(ShipmentActiveViewMapper.INSTANCE::shipmentActiveViewToShipmentActiveViewDTO)
	                        .collect(Collectors.toList());
	            }
	            APIResponse<List<ShipmentActiveViewDTO>> listAPIResponse = new APIResponse<>(Arrays.asList("Successfully retrieved data!"), shipmentActiveDtoList, ResponseStatusCode.SUCCESS);
	            return ResponseEntity.status(HttpStatus.OK).body(listAPIResponse);
	        } catch (NoRecordsFoundException e) {
	            APIResponse<List<ShipmentActiveViewDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
	            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
	        } catch (Exception e) {
	            APIResponse<List<ShipmentActiveViewDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
	        }
	    }
	  
	
}
