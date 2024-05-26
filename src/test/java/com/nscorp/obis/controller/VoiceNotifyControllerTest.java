package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.nscorp.obis.exception.RecordNotAddedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.VoiceNotify;
import com.nscorp.obis.dto.VoiceNotifyDTO;
import com.nscorp.obis.dto.mapper.VoiceNotifyMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.VoiceNotifyService;

public class VoiceNotifyControllerTest {

    @Mock
    VoiceNotifyService voiceNotifyService;

    @Mock
    VoiceNotifyMapper voiceNotifyMapper;

    @InjectMocks
    VoiceNotifyController voiceNotifyController;

    VoiceNotify voiceNotify;
    VoiceNotifyDTO voiceNotifyDTO;

    Map<String, String> headers;

    Long notifyQueueId;

    @SuppressWarnings("deprecation")
	@BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        voiceNotify = new VoiceNotify();
        voiceNotifyDTO = new VoiceNotifyDTO();

        notifyQueueId = 619919702687L;
        voiceNotify.setEquipId("asd");
        voiceNotify.setEquipInit("qwe");
        voiceNotify.setEquipNbr(BigDecimal.valueOf(123456));
        voiceNotify.setEquipTp("qwe");
        voiceNotify.setChasId("qwe");
        voiceNotify.setChasNbr(BigDecimal.valueOf(123456));
        voiceNotify.setDmgInd("qwe");
        voiceNotify.setEventCd("RMFC");
        voiceNotify.setEvtDesc("GROUNDED-REMOVED FROM FLATCAR");

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
    void testGetVoiceNotify() {
        when(voiceNotifyService.getVoiceNotify(notifyQueueId)).thenReturn(voiceNotify);
        when(voiceNotifyMapper.voiceNotifyToVoiceNotifyDTO(voiceNotify)).thenReturn(voiceNotifyDTO);
        ResponseEntity<APIResponse<VoiceNotifyDTO>> getData = voiceNotifyController.getAllvoiceNotify(notifyQueueId);
        assertEquals(getData.getStatusCodeValue(), 200);
        when(voiceNotifyService.getVoiceNotify(any())).thenReturn(null);
        getData = voiceNotifyController.getAllvoiceNotify(any());
        assertEquals(getData.getStatusCodeValue(), 200);
    }

    @Test
    void testUpdateVoiceNotify() {
        when(voiceNotifyMapper.voiceNotifyDTOToVoiceNotify(Mockito.any())).thenReturn(voiceNotify);
        when(voiceNotifyService.updateVoiceNotify(Mockito.any(), Mockito.any())).thenReturn(voiceNotify);
        when(voiceNotifyMapper.voiceNotifyToVoiceNotifyDTO(Mockito.any())).thenReturn(voiceNotifyDTO);
        ResponseEntity<APIResponse<VoiceNotifyDTO>> update = voiceNotifyController.updateNotifyQueue(voiceNotifyDTO,
                headers);
        assertNotNull(update.getBody());
    }

    @Test
    void testRuntimeException() {
        when(voiceNotifyService.getVoiceNotify(any())).thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<VoiceNotifyDTO>> getData = voiceNotifyController.getAllvoiceNotify(any());
        assertEquals(getData.getStatusCodeValue(), 500);

        when(voiceNotifyService.updateVoiceNotify(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<VoiceNotifyDTO>> updateData = voiceNotifyController.updateNotifyQueue(voiceNotifyDTO,
                headers);
        assertEquals(updateData.getStatusCodeValue(), 500);
    }
    @Test
    void testNoRecordsFoundException() {
        when(voiceNotifyService.getVoiceNotify(any())).thenThrow(new NoRecordsFoundException());
        ResponseEntity<APIResponse<VoiceNotifyDTO>> getData = voiceNotifyController.getAllvoiceNotify(any());
        assertEquals(getData.getStatusCodeValue(), 404);

        when(voiceNotifyService.updateVoiceNotify(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
        ResponseEntity<APIResponse<VoiceNotifyDTO>> updateDate = voiceNotifyController.updateNotifyQueue(voiceNotifyDTO,
                headers);
        assertEquals(updateDate.getStatusCodeValue(), 404);
    }

    @Test
    void testVoiceNotifyNullPointerException() {
        when(voiceNotifyService.updateVoiceNotify(Mockito.any(), Mockito.any())).thenThrow(new NullPointerException());
        ResponseEntity<APIResponse<VoiceNotifyDTO>> updateData = voiceNotifyController.updateNotifyQueue(voiceNotifyDTO,
                headers);
        assertEquals(updateData.getStatusCodeValue(),400);
    }

    @Test
    void testVoiceNotifyRecordNotAddedException() {
        when(voiceNotifyService.updateVoiceNotify(Mockito.any(), Mockito.any())).thenThrow(new RecordNotAddedException());
        ResponseEntity<APIResponse<VoiceNotifyDTO>> updateData = voiceNotifyController.updateNotifyQueue(voiceNotifyDTO,
                headers);
        assertEquals(updateData.getStatusCodeValue(),404);
    }
}