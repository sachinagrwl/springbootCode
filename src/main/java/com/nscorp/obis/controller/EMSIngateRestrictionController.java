package com.nscorp.obis.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.EMSIngateRestriction;
import com.nscorp.obis.dto.EMSIngateRestrictionDTO;
import com.nscorp.obis.dto.mapper.EMSIngateRestrictionMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.repository.EMSIngateRestrictionRepository;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.EMSIngateRestrictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class EMSIngateRestrictionController {

    @Autowired
    EMSIngateRestrictionService emsIngateRestrictionService;

    @Autowired
	EMSIngateRestrictionRepository emsIngateRestrictionRepository;

    @GetMapping(value= ControllerConstants.EMS_INGATE_RESTRICTION)
    public ResponseEntity<APIResponse<List<EMSIngateRestrictionDTO>>> getAllRestrictions() {

        try{
            List<EMSIngateRestrictionDTO> emsIngateRestrictionDTOList = Collections.emptyList();
            List<EMSIngateRestriction> emsIngateRestrictions = emsIngateRestrictionService.getAllRestrictions();
            if(emsIngateRestrictions != null && !emsIngateRestrictions.isEmpty()){
                emsIngateRestrictionDTOList = emsIngateRestrictions.stream()
                        .map(EMSIngateRestrictionMapper.INSTANCE::emsIngateRestrictionToEMSIngateRestrictionDTO)
                        .collect(Collectors.toList());
            }
			APIResponse<List<EMSIngateRestrictionDTO>> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"),emsIngateRestrictionDTOList, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (NoRecordsFoundException e){
			APIResponse<List<EMSIngateRestrictionDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (NullPointerException e){
			APIResponse<List<EMSIngateRestrictionDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (Exception e){
			APIResponse<List<EMSIngateRestrictionDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
	@PostMapping(value= ControllerConstants.EMS_INGATE_RESTRICTION)
	public ResponseEntity<APIResponse<EMSIngateRestrictionDTO>> addRestriction(@Valid @RequestBody EMSIngateRestrictionDTO emsObjDto, @RequestHeader Map<String,String> headers){

    	try {
			emsObjDto.setRestrictId(emsIngateRestrictionRepository.SGK());
			EMSIngateRestriction restrictions = EMSIngateRestrictionMapper.INSTANCE.emsIngateRestrictionDTOToEMSIngateRestriction(emsObjDto);
			EMSIngateRestriction addedRestriction = emsIngateRestrictionService.insertRestriction(restrictions,headers);
			EMSIngateRestrictionDTO addedRestrictionDto = EMSIngateRestrictionMapper.INSTANCE.emsIngateRestrictionToEMSIngateRestrictionDTO(addedRestriction);

			APIResponse<EMSIngateRestrictionDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),addedRestrictionDto, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<EMSIngateRestrictionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (SizeExceedException e){
			APIResponse<EMSIngateRestrictionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (RecordAlreadyExistsException e){
			APIResponse<EMSIngateRestrictionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (RecordNotAddedException e){
			APIResponse<EMSIngateRestrictionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (NullPointerException e){
			APIResponse<EMSIngateRestrictionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (Exception e){
			APIResponse<EMSIngateRestrictionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@PutMapping(value = ControllerConstants.EMS_INGATE_RESTRICTION)
	public ResponseEntity<APIResponse<EMSIngateRestrictionDTO>> updateRestriction(@Valid @RequestBody EMSIngateRestrictionDTO emsObjDto, @RequestHeader Map<String,String> headers) {

		try {
			EMSIngateRestriction restrictions = EMSIngateRestrictionMapper.INSTANCE.emsIngateRestrictionDTOToEMSIngateRestriction(emsObjDto);
			EMSIngateRestriction updatedRestriction = emsIngateRestrictionService.updateRestriction(restrictions, headers);
			EMSIngateRestrictionDTO updatedRestrictionDto = EMSIngateRestrictionMapper.INSTANCE.emsIngateRestrictionToEMSIngateRestrictionDTO(updatedRestriction);

			APIResponse<EMSIngateRestrictionDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),updatedRestrictionDto, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<EMSIngateRestrictionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (RecordAlreadyExistsException e){
			APIResponse<EMSIngateRestrictionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (RecordNotAddedException e){
			APIResponse<EMSIngateRestrictionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (NullPointerException e){
			APIResponse<EMSIngateRestrictionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (SizeExceedException e){
			APIResponse<EMSIngateRestrictionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (Exception e){
			if (e.getCause() instanceof javax.persistence.OptimisticLockException){
				APIResponse<EMSIngateRestrictionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE_OPTIMISTICLOCK);
				return ResponseEntity.status(HttpStatus.CONFLICT).body(responseObj);
			}
			APIResponse<EMSIngateRestrictionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

}
