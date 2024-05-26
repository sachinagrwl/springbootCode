package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.VoiceNotify;
import com.nscorp.obis.dto.VoiceNotifyDTO;
import com.nscorp.obis.dto.mapper.VoiceNotifyMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.VoiceNotifyService;

@Validated
@RestController
@CrossOrigin
@RequestMapping("/")
public class VoiceNotificationListController {
	
	@Autowired
	VoiceNotifyService voiceNotifyService;
	
	@GetMapping(value = ControllerConstants.VOICE_NOTIFICATION_LIST)
	public ResponseEntity<APIResponse<List<VoiceNotifyDTO>>> getAllvoiceNotify(@RequestParam(required = true, name = "notifyStat") String notifyStat,
			@RequestParam(required = true, name = "termId") Long termId,
			@RequestParam(required = true, name = "notifyMethod") String notifyMethod) {
		
		try {
			List<VoiceNotifyDTO> voiceNotifyDtoList = Collections.emptyList();
			List<VoiceNotify> voiceNotifyList = voiceNotifyService.getVoiceNtfyList(notifyStat, termId, notifyMethod);
			if (voiceNotifyList != null && !voiceNotifyList.isEmpty()) {
				voiceNotifyDtoList = voiceNotifyList.stream().map(VoiceNotifyMapper.INSTANCE::voiceNotifyToVoiceNotifyDTO)
                        .collect(Collectors.toList());
            }
			APIResponse<List<VoiceNotifyDTO>> responseObj = new APIResponse<>(Arrays.asList(CommonConstants.RETRIVED_SUCCESS_MESSAGE),
					voiceNotifyDtoList, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);

        } catch (NoRecordsFoundException e) {
            APIResponse<List<VoiceNotifyDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (Exception e) {
            APIResponse<List<VoiceNotifyDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
		
	}

}
