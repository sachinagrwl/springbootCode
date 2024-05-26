package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.Map;

import com.nscorp.obis.exception.RecordNotAddedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.VoiceNotify;
import com.nscorp.obis.dto.VoiceNotifyDTO;
import com.nscorp.obis.dto.mapper.VoiceNotifyMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.VoiceNotifyService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
@RestController
@CrossOrigin
@RequestMapping(value = ControllerConstants.NOTIFY_DETAILS)
public class VoiceNotifyController {

	@Autowired
	VoiceNotifyService voiceNotifyService;

	@GetMapping
	public ResponseEntity<APIResponse<VoiceNotifyDTO>> getAllvoiceNotify(
			@RequestParam(required = true, name = "ntfy-queue-id") Long notifyQueueId) {

		try {
			VoiceNotifyDTO voiceNotifyDto = new VoiceNotifyDTO();
			VoiceNotify voiceNotify = voiceNotifyService.getVoiceNotify(notifyQueueId);
			if (voiceNotify != null) {
				voiceNotifyDto = VoiceNotifyMapper.INSTANCE.voiceNotifyToVoiceNotifyDTO(voiceNotify);

			}
			APIResponse<VoiceNotifyDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"),
					voiceNotifyDto, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<VoiceNotifyDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<VoiceNotifyDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}

	}

	@PutMapping
	public ResponseEntity<APIResponse<VoiceNotifyDTO>> updateNotifyQueue(@Valid @NotNull @RequestBody VoiceNotifyDTO voiceNotifyDTO,
																					   @RequestHeader Map<String, String> headers) {

		try {
			VoiceNotify rangeNotifyQueue = voiceNotifyService.updateVoiceNotify(VoiceNotifyMapper.INSTANCE.voiceNotifyDTOToVoiceNotify(voiceNotifyDTO), headers);
			APIResponse<VoiceNotifyDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),
					VoiceNotifyMapper.INSTANCE.voiceNotifyToVoiceNotifyDTO(rangeNotifyQueue), ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (RecordNotAddedException e){
			APIResponse<VoiceNotifyDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<VoiceNotifyDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (NullPointerException e) {
			APIResponse<VoiceNotifyDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (Exception e) {
			APIResponse<VoiceNotifyDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
}
