package com.nscorp.obis.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.Terminal;
import com.nscorp.obis.dto.TerminalDTO;
import com.nscorp.obis.dto.mapper.TerminalMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.TerminalService;

public class TerminalControllerTest {

	@Mock
	TerminalService terminalService;

	@Mock
	TerminalMapper terminalMapper;

	@InjectMocks
	TerminalController terminalController;

	TerminalDTO terminalDto;
//		TerminalTrainList terminalTrainDtoList;
	Terminal terminal;
	List<Terminal> terminalList;
	List<TerminalDTO> terminalDtoList;
	List<Long> termId;


	Map<String, String> header;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		terminal = new Terminal();
		terminalDto = new TerminalDTO();
		terminalDtoList = new ArrayList<>();
		terminalList = new ArrayList<>();
		termId = new ArrayList<>();
		terminal.setNsTerminalId(null);
		terminal.setTerminalCountry(null);
		terminal.setTerminalZnOffset(null);
		terminal.setDayLightSaveIndicator("Y");
		terminal.setStnXrfId(null);
		terminal.setExpiredDate(null);
		terminal.setTerminalType(null);
		terminal.setHitchCheckIndicator("Y");
		terminal.setHaulageIndicator("Y");
		terminal.setIdcsTerminalIndicator("Y");
		terminal.setSswTerminalIndicator("Y");
		terminal.setTerminalAddress1("12");
		terminal.setTerminalAddress2("23");
		terminal.setTerminalCity1("12");
		terminal.setTerminalCity2("34");
		terminal.setTerminalZipCode1("4567");
		terminal.setTerminalZipCode2("6543");
		terminal.setTerminalState1("HP");
		terminal.setTerminalState2("MP");

		terminal.setExternalAreaCd1(12);
		terminal.setExternalAreaCd2(21);
		terminal.setExternalAreaCd3(222);
		terminal.setExternalExchange1(23);
		terminal.setExternalExchange2(345);
		terminal.setExternalExchange3(345);
		terminal.setExternalExtension1(123);
		terminal.setExternalExtension2(324);
		terminal.setExternalExtension3(456);

		terminal.setInternalAreaCd1(234);
		terminal.setInternalAreaCd2(321);
		terminal.setInternalAreaCd3(234);
		terminal.setInternalExchange1(432);
		terminal.setInternalExchange2(345);
		terminal.setInternalExchange3(456);
		terminal.setInternalExtension1(5143);
		terminal.setInternalExtension2(4516);
		terminal.setInternalExtension3(4567);

		terminal.setExternalFaxArea1(21);
		terminal.setExternalFaxArea2(23);
		terminal.setExternalFaxArea3(45);
		terminal.setExternalFaxExchange1(345);
		terminal.setExternalFaxExchange2(234);
		terminal.setExternalFaxExchange3(345);
		terminal.setExternalFaxExtension1(3221);
		terminal.setExternalFaxExtension2(4256);
		terminal.setExternalFaxExtension3(5432);

		terminal.setInternalFaxArea1(345);
		terminal.setInternalFaxArea2(345);
		terminal.setInternalFaxArea3(345);
		terminal.setInternalFaxExchange1(345);
		terminal.setInternalFaxExchange2(345);
		terminal.setInternalFaxExchange3(123);
		terminal.setInternalFaxExtension1(432);
		terminal.setInternalFaxExtension2(234);
		terminal.setInternalFaxExtension3(456);

		terminal.setDeferredTime("06");
		terminal.setRenotifyDays(null);
		terminal.setRenotifyTime(null);
		terminal.setTerminalCloseOutTime(null);
		
		termId.add(123000L);

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {

		terminal = null;
		terminalDto = null;
		terminalDtoList = null;
		terminalList = null;

	}
	
	@Test
	void testGetTerminal() {
		when(terminalService.getTerminal(Mockito.any())).thenReturn(terminal);
		ResponseEntity<APIResponse<List<TerminalDTO>>> terminalList = terminalController.getTerminal(termId);
		assertEquals(terminalList.getStatusCodeValue(), 200);
	}

	@Test
	void testAddTerminal() {
		when(terminalMapper.terminalDTOToTerminal(Mockito.any())).thenReturn(terminal);
		when(terminalService.insertTerminal(Mockito.any(), Mockito.any())).thenReturn(terminal);
		when(terminalMapper.terminalToTerminalDTO(Mockito.any())).thenReturn(terminalDto);
		ResponseEntity<APIResponse<TerminalDTO>> added = terminalController.addTerminal(terminalDto, header);
		assertNotNull(added.getBody().getData());
	}

	@Test
	void testUpdateTerminal() {
		when(terminalMapper.terminalDTOToTerminal(Mockito.any())).thenReturn(terminal);
		when(terminalService.updateTerminal(Mockito.any(), Mockito.any())).thenReturn(terminal);
		when(terminalMapper.terminalToTerminalDTO(Mockito.any())).thenReturn(terminalDto);
		ResponseEntity<APIResponse<TerminalDTO>> update = terminalController.updateTerminal(terminalDto, header);
		assertNotNull(update.getBody().getData());
	}

	@Test
	void testTerminalNullPointerException() {

		when(terminalService.updateTerminal(Mockito.any(), Mockito.any()))
				.thenThrow(new NullPointerException("Null pointer"));

		when(terminalService.insertTerminal(Mockito.any(), Mockito.any())).thenThrow(new NullPointerException());

		ResponseEntity<APIResponse<TerminalDTO>> blockUpdate = terminalController.updateTerminal(Mockito.any(), Mockito.any());

		ResponseEntity<APIResponse<TerminalDTO>> addBlock = terminalController.addTerminal(Mockito.any(), Mockito.any());

		assertEquals(blockUpdate.getStatusCodeValue(), 400);

		assertEquals(addBlock.getStatusCodeValue(), 400);

	}
	
	@Test
	void testTerminalException() {

		when(terminalService.updateTerminal(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		when(terminalService.insertTerminal(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		when(terminalService.getTerminal(Mockito.any())).thenThrow(new RuntimeException());
		
		ResponseEntity<APIResponse<TerminalDTO>> blockUpdate = terminalController.updateTerminal(Mockito.any(),	Mockito.any());
		ResponseEntity<APIResponse<TerminalDTO>> addBlock = terminalController.addTerminal(Mockito.any(), Mockito.any());
		ResponseEntity<APIResponse<List<TerminalDTO>>> terminalList = terminalController.getTerminal(null);

		assertEquals(blockUpdate.getStatusCodeValue(), 500);
		assertEquals(addBlock.getStatusCodeValue(), 500);
		assertEquals(terminalList.getStatusCodeValue(), 500);

	}
	
	@Test
	void testTerminalRecordAlreadyExistsException() {

		when(terminalService.updateTerminal(Mockito.any(), Mockito.any())).thenThrow(new RecordAlreadyExistsException());
		when(terminalService.insertTerminal(Mockito.any(), Mockito.any())).thenThrow(new RecordAlreadyExistsException());
		
		ResponseEntity<APIResponse<TerminalDTO>> blockUpdate = terminalController.updateTerminal(Mockito.any(),	Mockito.any());
		ResponseEntity<APIResponse<TerminalDTO>> addBlock = terminalController.addTerminal(Mockito.any(), Mockito.any());
		

		assertEquals(blockUpdate.getStatusCodeValue(), 208);
		assertEquals(addBlock.getStatusCodeValue(), 208);

	}
	
	@Test
	void testTerminalNoRecordsFoundException() {

		when(terminalService.updateTerminal(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
		when(terminalService.insertTerminal(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
		when(terminalService.getTerminal(Mockito.any())).thenThrow(new NoRecordsFoundException());
		
		ResponseEntity<APIResponse<TerminalDTO>> blockUpdate = terminalController.updateTerminal(Mockito.any(),	Mockito.any());
		ResponseEntity<APIResponse<TerminalDTO>> addBlock = terminalController.addTerminal(Mockito.any(), Mockito.any());
		ResponseEntity<APIResponse<List<TerminalDTO>>> getTerminal = terminalController.getTerminal(termId);

		assertEquals(blockUpdate.getStatusCodeValue(), 404);
		assertEquals(addBlock.getStatusCodeValue(), 404);
		assertEquals(getTerminal.getStatusCodeValue(), 404);

	}
	
	@Test
	void testTerminalRecordNotAddedException() {

		when(terminalService.updateTerminal(Mockito.any(), Mockito.any())).thenThrow(new RecordNotAddedException());
		when(terminalService.insertTerminal(Mockito.any(), Mockito.any())).thenThrow(new RecordNotAddedException());
		
		ResponseEntity<APIResponse<TerminalDTO>> blockUpdate = terminalController.updateTerminal(Mockito.any(),	Mockito.any());
		ResponseEntity<APIResponse<TerminalDTO>> addBlock = terminalController.addTerminal(Mockito.any(), Mockito.any());

		assertEquals(blockUpdate.getStatusCodeValue(), 406);
		assertEquals(addBlock.getStatusCodeValue(), 406);

	}
	
	

}
