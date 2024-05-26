package com.nscorp.obis.controller;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.AARDamage;
import com.nscorp.obis.dto.AARDamageDTO;
import com.nscorp.obis.dto.mapper.AARDamageMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.AARDamageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping(ControllerConstants.AAR_DAMAGE)
public class AARDamageController {

    @Autowired
    AARDamageService aarDamageService;
    @GetMapping
    public ResponseEntity<APIResponse<List<AARDamageDTO>>> getAarDamage(@Size(max = 4, message = "'aarDamage' size should not be greater than {max}") @RequestParam(required = false, name = "aarDamage") String aarDamage) {

        try {

            List<AARDamageDTO> aarDamageDTOList = Collections.emptyList();
            List<AARDamage> aarDamageList = aarDamageService.getAllAarDamageCodes(aarDamage);
            if(aarDamageList != null && !aarDamageList.isEmpty()){
                aarDamageDTOList = aarDamageList.stream()
                        .map(AARDamageMapper.INSTANCE::AARDamageToAARDamageDTO)
                        .collect(Collectors.toList());
            }
            APIResponse<List<AARDamageDTO>> listAPIResponse = new APIResponse<>(Arrays.asList("Successfully retrieved data!"),aarDamageDTOList, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(listAPIResponse);
        } catch (NoRecordsFoundException e){
            APIResponse<List<AARDamageDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (Exception e){
            APIResponse<List<AARDamageDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }

    }
}
