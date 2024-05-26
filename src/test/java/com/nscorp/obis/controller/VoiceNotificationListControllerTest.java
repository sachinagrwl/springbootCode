package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.VoiceNotify;
import com.nscorp.obis.dto.VoiceNotifyDTO;
import com.nscorp.obis.dto.mapper.VoiceNotifyMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.VoiceNotifyService;

class VoiceNotificationListControllerTest {
	
	@Mock
    VoiceNotifyService voiceNotifyService;

    @Mock
    VoiceNotifyMapper voiceNotifyMapper;

    @InjectMocks
    VoiceNotificationListController voiceNotifyController;

    VoiceNotify voiceNotify;
    List<VoiceNotify> voiceNotifyList;
    VoiceNotifyDTO voiceNotifyDTO;
    List<VoiceNotifyDTO> voiceNotifyDTOList;

    Map<String, String> headers;
    String notifyStat;
    Long termId;
    String notifyMethod;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
        voiceNotify = new VoiceNotify();
        voiceNotifyDTO = new VoiceNotifyDTO();
        voiceNotifyList = new ArrayList<>();
        voiceNotifyDTOList = new ArrayList<>();

        voiceNotify.setEquipId("asd");
        voiceNotify.setEquipInit("qwe");
        voiceNotify.setEquipNbr(BigDecimal.valueOf(123456));
        voiceNotify.setEquipTp("qwe");
        voiceNotify.setChasId("qwe");
        voiceNotify.setChasNbr(BigDecimal.valueOf(123456));
        voiceNotify.setDmgInd("qwe");
        voiceNotify.setEventCd("RMFC");
        voiceNotify.setEvtDesc("GROUNDED-REMOVED FROM FLATCAR");
        
        voiceNotifyList.add(voiceNotify);
        
        voiceNotifyDTO.setEquipId("asd");
        voiceNotifyDTO.setEquipInit("qwe");
        voiceNotifyDTO.setEquipNbr(BigDecimal.valueOf(123456));
        voiceNotifyDTO.setEquipTp("qwe");
        voiceNotifyDTO.setChasId("qwe");
        voiceNotifyDTO.setChasNbr(BigDecimal.valueOf(123456));
        voiceNotifyDTO.setDmgInd("qwe");
        voiceNotifyDTO.setEventCd("RMFC");
        voiceNotifyDTO.setEvtDesc("GROUNDED-REMOVED FROM FLATCAR");
        
        voiceNotifyDTOList.add(voiceNotifyDTO);

        headers = new HashMap<>();
        headers.put("userid", "test");
        headers.put("extensionschema", "test");
	}

	@AfterEach
	void tearDown() throws Exception {
		voiceNotify = null;
        voiceNotifyDTO = null;
	}

	@Test
	void testGetAllvoiceNotify() {
		when(voiceNotifyService.getVoiceNtfyList(notifyStat, termId, notifyMethod)).thenReturn(voiceNotifyList);
		ResponseEntity<APIResponse<List<VoiceNotifyDTO>>> getVoiceNotify = voiceNotifyController.getAllvoiceNotify(notifyStat, termId, notifyMethod);
		assertEquals(getVoiceNotify.getStatusCodeValue(), 200);
	}
	
	@Test
	void testGetAllvoiceNotifyNoRecordsException() {
		when(voiceNotifyService.getVoiceNtfyList(notifyStat, termId, notifyMethod)).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<VoiceNotifyDTO>>> getVoiceNotify = voiceNotifyController.getAllvoiceNotify(notifyStat, termId, notifyMethod);
		assertEquals(getVoiceNotify.getStatusCodeValue(), 404);
	}
	
	@Test
	void testGetAllvoiceNotifyException() {
		when(voiceNotifyService.getVoiceNtfyList(notifyStat, termId, notifyMethod)).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<VoiceNotifyDTO>>> getVoiceNotify = voiceNotifyController.getAllvoiceNotify(notifyStat, termId, notifyMethod);
		assertEquals(getVoiceNotify.getStatusCodeValue(), 500);
	}

}
