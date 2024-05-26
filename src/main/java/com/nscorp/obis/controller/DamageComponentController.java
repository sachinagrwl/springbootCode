package com.nscorp.obis.controller;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.DamageComponent;
import com.nscorp.obis.dto.DamageComponentDTO;
import com.nscorp.obis.dto.mapper.DamageComponentMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.DamageComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
@CrossOrigin
@Validated
public class DamageComponentController {

    @Autowired
    DamageComponentService damageComponentService;


    @GetMapping(value = ControllerConstants.DAMAGE_COMPONENT)
    public ResponseEntity<APIResponse<List<DamageComponentDTO>>> getAllDamageComponents(@RequestParam(required = false) Integer jobCode) {

        try {
            List<DamageComponentDTO> damageComponentDTOList = Collections.emptyList();
            if (jobCode != null) {
                DamageComponent damageEntity = damageComponentService.getDamageComponentsByJobCode(jobCode);
                damageComponentDTOList = Arrays.asList(DamageComponentMapper.INSTANCE.damageComponentToDamageComponentDTO(damageEntity));
            } else {
                List<DamageComponent> damageComponentList = damageComponentService.getAllDamageComponents();
                if (damageComponentList != null && !damageComponentList.isEmpty()) {
                    damageComponentDTOList = damageComponentList.stream().map(DamageComponentMapper.INSTANCE::damageComponentToDamageComponentDTO)
                            .collect(Collectors.toList());
                }
            }

            APIResponse<List<DamageComponentDTO>> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"),
                    damageComponentDTOList, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);

        } catch (NoRecordsFoundException e) {
            APIResponse<List<DamageComponentDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (Exception e) {
            APIResponse<List<DamageComponentDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }

    }

    @DeleteMapping(value = ControllerConstants.DAMAGE_COMPONENT)
    public ResponseEntity<List<APIResponse<DamageComponentDTO>>> deleteDamageComponent(@NotNull @RequestBody List<DamageComponentDTO> damageComponentDTOList) {
        List<APIResponse<DamageComponentDTO>> response;
        AtomicInteger errorCount = new AtomicInteger();
        if (damageComponentDTOList != null && !damageComponentDTOList.isEmpty()) {
            response = damageComponentDTOList.stream().map(damageComponentDTOObj -> {
                        APIResponse<DamageComponentDTO> singleDtoDelResponse;
                        try {
                            damageComponentService.deleteDamageComponent(DamageComponentMapper.INSTANCE.damageComponentDTOToDamageComponent(damageComponentDTOObj));
                            singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"), damageComponentDTOObj, ResponseStatusCode.SUCCESS);
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

    @PostMapping(value = ControllerConstants.DAMAGE_COMPONENT)
    public ResponseEntity<APIResponse<DamageComponentDTO>> addDamageComponent(@Valid @RequestBody DamageComponentDTO damageComponentObj,
                                                                              @RequestHeader Map<String, String> headers) {
        try {
            DamageComponent damageComponent = DamageComponentMapper.INSTANCE.damageComponentDTOToDamageComponent(damageComponentObj);
            DamageComponent addDamageComponent = damageComponentService.insertDamageComponent(damageComponent, headers);
            DamageComponentDTO addedDamageComponentDto = DamageComponentMapper.INSTANCE.damageComponentToDamageComponentDTO(addDamageComponent);
            APIResponse<DamageComponentDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),
                    addedDamageComponentDto, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (NoRecordsFoundException e) {
            APIResponse<DamageComponentDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (RecordAlreadyExistsException e) {
            APIResponse<DamageComponentDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
        } catch (Exception e) {
            APIResponse<DamageComponentDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }


    @PutMapping(value = ControllerConstants.DAMAGE_COMPONENT)
    public ResponseEntity<APIResponse<DamageComponentDTO>> updateDamageComponent(@Valid @RequestBody DamageComponentDTO damageComponentObjDTO,
                                                                 @RequestHeader Map<String, String> headers) {
        try {
            DamageComponent damageComponent = DamageComponentMapper.INSTANCE.damageComponentDTOToDamageComponent(damageComponentObjDTO);
            DamageComponent addDamageComponent = damageComponentService.UpdateDamageComponent(damageComponent, headers);
            DamageComponentDTO addedDamageComponentDto = DamageComponentMapper.INSTANCE.damageComponentToDamageComponentDTO(addDamageComponent);
            APIResponse<DamageComponentDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),
                    addedDamageComponentDto, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (NoRecordsFoundException e) {
            APIResponse<DamageComponentDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        }catch (Exception e) {
            APIResponse<DamageComponentDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }
}
