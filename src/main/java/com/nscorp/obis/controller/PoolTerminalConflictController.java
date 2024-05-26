package com.nscorp.obis.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.nscorp.obis.common.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.Pool;
import com.nscorp.obis.dto.PoolDTO;
import com.nscorp.obis.dto.mapper.PoolMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.PoolTerminalConflictService;

@RestController
@RequestMapping("/")
@Slf4j
public class PoolTerminalConflictController {
	@Autowired
	PoolTerminalConflictService poolTerminalConflictService;

	@PutMapping(value = ControllerConstants.POOL_TERMINAL_CONFLICT)
	public ResponseEntity<APIResponse<List<PoolDTO>>> validatePoolTerminalConflict(
			@Valid @NotNull @RequestBody PoolDTO poolDTO, @RequestHeader Map<String, String> headers) {
		try {
			log.info("validatePoolTerminalConflict : Method Starts");
			List<PoolDTO> conflictedPoolDTOList = new ArrayList<>();
			Set<Pool> validatedPool = poolTerminalConflictService
					.validatePoolTerminalConflict(PoolMapper.INSTANCE.poolDtoToPool(poolDTO), headers);
			if (!CollectionUtils.isEmpty(validatedPool)) {
				conflictedPoolDTOList = validatedPool.stream().map(PoolMapper.INSTANCE::poolToPoolDto)
						.collect(Collectors.toList());
			}
			log.info("validatePoolTerminalConflict : Method Ends");
			APIResponse<List<PoolDTO>> responseObj = new APIResponse<>(Arrays.asList(CommonConstants.VALIDATED_SUCCESS_MESSAGE),
					conflictedPoolDTOList, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<List<PoolDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<PoolDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

}
