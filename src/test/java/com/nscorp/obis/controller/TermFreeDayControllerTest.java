package com.nscorp.obis.controller;

import com.nscorp.obis.domain.TermFreeDay;
import com.nscorp.obis.domain.Terminal;
import com.nscorp.obis.dto.TermFreeDayDTO;
import com.nscorp.obis.dto.mapper.TermFreeDayMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.repository.TerminalRepository;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.TermFreeDayService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class TermFreeDayControllerTest {

    @InjectMocks
    TermFreeDayController termFreeDayController;
    
    @Mock
    TermFreeDayMapper termFreeMapper;

    @Mock
    TermFreeDayService termFreeDayService;

    @Mock
    TerminalRepository terminalRepo;

    TermFreeDay termFreeDay;
    TermFreeDayDTO termFreeDayDTO;
    List<TermFreeDay> termFreeDayList;
    List<TermFreeDayDTO> termFreeDayDTOList;
    List<String> allDescList;
    Map<String, String> header;
    List<Long> termId = new ArrayList<>();
    LocalDate closeDate;
    String closeCode;
    LocalTime closeFromTime;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        allDescList = new ArrayList<>();
        termFreeDay = new TermFreeDay();
        termFreeDay.setTermId(1234L);
        termFreeDay.setCloseDate(LocalDate.of(2022, 01, 01));
        termFreeDay.setCloseFromTime(LocalTime.of(00,01));
        termFreeDay.setCloseRsnCd("SNO");
        termFreeDay.setCloseRsnDesc("SNOW");
        termFreeDay.setCloseToTime(LocalTime.of(23,59));
        termFreeDayList = new ArrayList<>();
        termFreeDayList.add(termFreeDay);

        termFreeDayDTO = new TermFreeDayDTO();
        termFreeDayDTO.setTermId(1234L);
        termFreeDayDTO.setCloseDate(LocalDate.of(2022, 01, 01));
        termFreeDayDTO.setCloseFromTime(LocalTime.of(00,01));
        termFreeDayDTO.setCloseRsnCd("SNO");
        termFreeDayDTO.setCloseRsnDesc("SNOW");
        termFreeDayDTO.setCloseToTime(LocalTime.of(23,59));
        termFreeDayDTO.setFreeDay("Y");
        termFreeDayDTOList = new ArrayList<>();
        termFreeDayDTOList.add(termFreeDayDTO);
        termId.add(1234L);
        closeDate = LocalDate.of(2022,01,01);
        closeCode = "SNO";
    }

    @AfterEach
    void tearDown() {
        termFreeDay = null;
        termFreeDayDTO = null;
        termFreeDayList = null;
        termFreeDayDTOList = null;
    }

    @Test
    void getAllFreeDays() {
        when(termFreeDayService.getAllFreeDays(termId,closeDate,closeCode, closeFromTime)).thenReturn(termFreeDayList);
        ResponseEntity<APIResponse<List<TermFreeDayDTO>>> getTermFreeDay = termFreeDayController.getAllFreeDays(termId,closeDate,closeCode, closeFromTime);
        assertEquals(getTermFreeDay.getStatusCodeValue(),200);
    }

    @Test
    void getAllReasonDesc() {
        when(termFreeDayService.getAllReasonDesc()).thenReturn(allDescList);
        ResponseEntity<APIResponse<List<String>>> getAllReasonDesc = termFreeDayController.getAllReasonDesc();
        assertEquals(getAllReasonDesc.getStatusCodeValue(),200);
    }

    @Test
    void testGetAllAARTypesNoRecordsFoundException() {
        when(termFreeDayService.getAllFreeDays(termId,closeDate,closeCode, closeFromTime)).thenThrow(new NoRecordsFoundException());
        ResponseEntity<APIResponse<List<TermFreeDayDTO>>> getTermFreeDay = termFreeDayController.getAllFreeDays(termId,closeDate,closeCode, closeFromTime);
        assertEquals(getTermFreeDay.getStatusCodeValue(),404);
    }

    @Test
    void testGetException() {
        when(termFreeDayService.getAllFreeDays(termId,closeDate,closeCode, closeFromTime)).thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<List<TermFreeDayDTO>>> getTermFreeDay = termFreeDayController.getAllFreeDays(termId,closeDate,closeCode, closeFromTime);
        assertEquals(getTermFreeDay.getStatusCodeValue(), 500);

        when(termFreeDayService.getAllReasonDesc()).thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<List<String>>> getAllReasonDesc = termFreeDayController.getAllReasonDesc();
        assertEquals(getAllReasonDesc.getStatusCodeValue(),500);
    }
    
    @Test
	void testTermFreeNoRecordsFoundException() {
		when(termFreeDayService.updateTermDay(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<TermFreeDayDTO>> updateTerm =  termFreeDayController.updateTermDay(Mockito.any(),Mockito.any());
        assertEquals(updateTerm.getStatusCodeValue(),404);
	}
    
    @Test
	void testTermFreeRuntimeException() {
		when(termFreeDayService.getAllFreeDays(Mockito.any(),Mockito.any(), Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		when(termFreeDayService.updateTermDay(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
		
		ResponseEntity<APIResponse<List<TermFreeDayDTO>>> getTerm = termFreeDayController.getAllFreeDays(Mockito.any(), Mockito.any(),Mockito.any(), Mockito.any());
		ResponseEntity<APIResponse<TermFreeDayDTO>> updateTerm =  termFreeDayController.updateTermDay(Mockito.any(),Mockito.any());

		assertEquals(getTerm.getStatusCodeValue(), 500);
		assertEquals(updateTerm.getStatusCodeValue(),500);
	}
    
    @Test
	void testTermFreeNullPointerException() {
		when(termFreeDayService.updateTermDay(Mockito.any(),Mockito.any())).thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<TermFreeDayDTO>> updateTerm =  termFreeDayController.updateTermDay(Mockito.any(),Mockito.any());
		assertEquals(updateTerm.getStatusCodeValue(),400);
	}
    
    @Test
	void testTermFreeSizeExceedException() {
		when(termFreeDayService.updateTermDay(Mockito.any(),Mockito.any())).thenThrow(new SizeExceedException());
        ResponseEntity<APIResponse<TermFreeDayDTO>> updateTerm =  termFreeDayController.updateTermDay(Mockito.any(),Mockito.any());
		assertEquals(updateTerm.getStatusCodeValue(),411);
	}
    
//    @Test
//	void testTermFreeRecordAlreadyExistsException() {
//		when(termFreeDayService.addTermDay(Mockito.any(),Mockito.any())).thenThrow(new RecordAlreadyExistsException());
//		ResponseEntity<List<APIResponse<TermFreeDayDTO>>> addTerm = termFreeDayController.addTermDay(Mockito.any(),Mockito.any());
//		assertEquals(addTerm.getStatusCodeValue(),208);
//	}

//	@Test
//	void testTermFreeRecordNotAddedException() {
//		when(termFreeDayService.addTermDay(Mockito.any(),Mockito.any())).thenThrow(new RecordNotAddedException());
//		ResponseEntity<List<APIResponse<TermFreeDayDTO>>> addTerm = termFreeDayController.addTermDay(Mockito.any(),Mockito.any());
//		assertEquals(addTerm.getStatusCodeValue(),406);
//	}

//    @Test
//    void addTermDay() {
////        termFreeDayDTO.setTerminalName("terminalName");
////        Terminal terminal = new Terminal();
////    	when(termFreeMapper.TermFreeDayDTOToTermFreeDay(Mockito.any())).thenReturn(termFreeDay);
////		when(termFreeDayService.addTermDay(Mockito.any(), Mockito.any())).thenReturn(termFreeDay);
////		when(termFreeMapper.TermFreeDayToTermFreeDayDTO(Mockito.any())).thenReturn(termFreeDayDTO);
////        when(terminalRepo.findByTerminalId(termFreeDay.getTermId())).thenReturn(terminal);
////		ResponseEntity<List<APIResponse<TermFreeDayDTO>>> addedTerm = termFreeDayController.addTermDay(termFreeDayDTO,
////				header);
////		assertNotNull(addedTerm.getBody());
//        when(termFreeMapper.TermFreeDayDTOToTermFreeDay(Mockito.any())).thenReturn(termFreeDay);
//        termFreeDayService.addTermDay(Mockito.any(),Mockito.any());
//        when(termFreeMapper.TermFreeDayToTermFreeDayDTO(Mockito.any())).thenReturn(termFreeDayDTO);
//        ResponseEntity<List<APIResponse<TermFreeDayDTO>>> addedTerm = termFreeDayController.addTermDay(termFreeDayDTO,
//				header);
//        assertEquals(addedTerm.getStatusCodeValue(),500);
//    }

    @Test
    void updateTermDay() {
        termFreeDayDTO.setTerminalName("terminalName");
        Terminal terminal = new Terminal();
    	when(termFreeMapper.TermFreeDayDTOToTermFreeDay(Mockito.any())).thenReturn(termFreeDay);
    	when(termFreeDayService.updateTermDay(Mockito.any(), Mockito.any())).thenReturn(termFreeDay);
    	when(termFreeMapper.TermFreeDayToTermFreeDayDTO(Mockito.any())).thenReturn(termFreeDayDTO);
        when(terminalRepo.findByTerminalId(termFreeDay.getTermId())).thenReturn(terminal);

        ResponseEntity<APIResponse<TermFreeDayDTO>> updatedTerm = termFreeDayController.updateTermDay(termFreeDayDTO,
    					header);
    	assertNotNull(updatedTerm.getBody());
    }

    @Test
    void addWeight() {
        List<Terminal> terminalList = new ArrayList<>();
        terminalList.add(new Terminal());
        terminalList.add(new Terminal());
        termFreeDayDTO.setTerminalList(terminalList);
        when(termFreeMapper.TermFreeDayDTOToTermFreeDay(Mockito.any())).thenReturn(termFreeDay);
        termFreeDayService.addTermDay(Mockito.any(), Mockito.any());
        when(termFreeMapper.TermFreeDayToTermFreeDayDTO(Mockito.any())).thenReturn(termFreeDayDTO);
        ResponseEntity<List<APIResponse<TermFreeDayDTO>>> addData = termFreeDayController.addTermDay(termFreeDayDTO, header);
        assertEquals(addData.getStatusCodeValue(),200);
    }

    @Test
    void addError() {
        TermFreeDay termList = new TermFreeDay();
        List<TermFreeDay> termFreeList = new ArrayList<>();
        termFreeList.add(termList);

        TermFreeDayDTO termFreeDto = new TermFreeDayDTO();

        when(termFreeMapper.TermFreeDayDTOToTermFreeDay(Mockito.any())).thenReturn(termList);
        termFreeDayService.addTermDay(Mockito.any(), Mockito.any());
        when(termFreeMapper.TermFreeDayToTermFreeDayDTO(Mockito.any())).thenReturn(termFreeDto);
        ResponseEntity<List<APIResponse<TermFreeDayDTO>>> addList = termFreeDayController.addTermDay(termFreeDto, header);
        assertEquals(addList.getStatusCodeValue(),500);

    }

    @Test
    void deleteWeight() {
    	when(termFreeMapper.TermFreeDayDTOToTermFreeDay(Mockito.any())).thenReturn(termFreeDay);
    	termFreeDayService.deleteTermFreeDay(Mockito.any());
		when(termFreeMapper.TermFreeDayToTermFreeDayDTO(Mockito.any())).thenReturn(termFreeDayDTO);
		ResponseEntity<List<APIResponse<TermFreeDayDTO>>> deleteList = termFreeDayController.deleteWeight(termFreeDayDTOList);
		assertEquals(deleteList.getStatusCodeValue(),200);
    }
    
    @Test
    void deleteErrorWeight() {
    	TermFreeDay termList = new TermFreeDay();
    	List<TermFreeDay> termFreeList = new ArrayList<>();
    	termFreeList.add(termList);

    	TermFreeDayDTO termFreeDto = new TermFreeDayDTO();
    	List<TermFreeDayDTO> termFreeDtoList = new ArrayList<>();

    	when(termFreeMapper.TermFreeDayDTOToTermFreeDay(Mockito.any())).thenReturn(termList);
    	termFreeDayService.deleteTermFreeDay(Mockito.any());
		when(termFreeMapper.TermFreeDayToTermFreeDayDTO(Mockito.any())).thenReturn(termFreeDto);
		ResponseEntity<List<APIResponse<TermFreeDayDTO>>> deleteList = termFreeDayController.deleteWeight(termFreeDtoList);
    	assertEquals(deleteList.getStatusCodeValue(),500);

    }
    
}