package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.EquipmentInitialSpeedCodeMaintenance;
import com.nscorp.obis.dto.EquipmentInitialSpeedCodeMaintenanceDTO;
import com.nscorp.obis.dto.mapper.EquipmentInitialSpeedCodeMaintenanceMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.EquipmentInitialSpeedCodeMaintenanceService;

@Validated
@RestController
@RequestMapping(ControllerConstants.EQ_INIT_SPEED_CODE)
public class EquipmentInitialSpeedCodeMaintenanceController {
	
	@Autowired
	EquipmentInitialSpeedCodeMaintenanceService eqInitCodeService;
	
	@GetMapping
    public ResponseEntity<APIResponse<List<EquipmentInitialSpeedCodeMaintenanceDTO>>> getAllInitSpeedCode(@Size(max = 4, message = "'eqInitShort' size should not be greater than {max}") @RequestParam(required = false, name = "eqInitShort") String eqInitShort) {
		
try {
			
			List<EquipmentInitialSpeedCodeMaintenanceDTO> eqSpeedDTOList = Collections.emptyList();
			List<EquipmentInitialSpeedCodeMaintenance> eqSpeedList = eqInitCodeService.getAllInitSpeedCode(eqInitShort);
			if(eqSpeedList != null && !eqSpeedList.isEmpty()){
				eqSpeedDTOList = eqSpeedList.stream()
                        .map(EquipmentInitialSpeedCodeMaintenanceMapper.INSTANCE::EquipmentInitialSpeedCodeMaintenanceToEquipmentInitialSpeedCodeMaintenanceDTO)
                        .collect(Collectors.toList());
            }
			APIResponse<List<EquipmentInitialSpeedCodeMaintenanceDTO>> listAPIResponse = new APIResponse<>(Arrays.asList("Successfully retrieved data!"),eqSpeedDTOList, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(listAPIResponse);
		} catch (NoRecordsFoundException e){
            APIResponse<List<EquipmentInitialSpeedCodeMaintenanceDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (Exception e){
            APIResponse<List<EquipmentInitialSpeedCodeMaintenanceDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }

	}

}
