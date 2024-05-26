package com.nscorp.obis.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.obis.domain.*;
import com.nscorp.obis.dto.mapper.TerminalMapper;
import com.nscorp.obis.repository.StationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.dto.TerminalDTO;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.repository.TerminalRepository;


public class TerminalServiceTest {

	@InjectMocks
	TerminalServiceImpl terminalService;

	@Mock
	TerminalRepository terminalRepo;

	@Mock
	StationRepository stationRepo;
	
	Terminal terminal;
	Station station;
	Terminal terminalResource;
	Terminal terminalUpdated;
	Terminal terminalAdded;
	TerminalInd terminalInd;

	TerminalDTO terminalDto;
	List<Terminal> terminalList;
	List<TerminalDTO> terminalDtoList;
	List<DayOfWeek> days;
	TerminalType terminalType;
	Date endDate =new Date(12);
	NSTimeZone zone;
	NSCountry country;
	Time endTime = null;
	
	Map<String, String> header;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		terminal = new Terminal();
		terminalDto = new TerminalDTO();
		terminalDtoList = new ArrayList<>();
		terminalList = new ArrayList<>();
		days = new ArrayList<>();

		terminal.setTerminalId(1234L);
		terminal.setNsTerminalId(123);
		terminal.setTerminalCountry(country);
		terminal.setTerminalZnOffset(zone);
		terminal.setDayLightSaveIndicator("Y");
		terminal.setExpiredDate(endDate);
		
		terminal.setTerminalType(terminalType);
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
		days.add(DayOfWeek.SUN);
		days.add(DayOfWeek.MON);
		days.add(DayOfWeek.TUE);
		days.add(DayOfWeek.WED);
		days.add(DayOfWeek.THU);
		days.add(DayOfWeek.FRI);
		days.add(DayOfWeek.SAT);
		terminal.setRenotifyDays(days);
		terminal.setRenotifyTime("21");
		terminal.setTerminalCloseOutTime(endTime);
		TerminalInd terminalIndObj = new TerminalInd();
		terminalIndObj.setAgsIndicator("Y");
		terminalIndObj.setLastMovNSNotOK("Y");
		terminalIndObj.setPrivateInd("Y");
		terminal.setUversion("!");
		terminal.setTerminalInd(terminalIndObj);
		terminal.setStnXrfId(1234L);
		//station.setTermId(1234L);
		terminalDto = TerminalMapper.INSTANCE.terminalToTerminalDTO(terminal);
		terminalList.add(terminal);
		terminalDtoList.add(terminalDto);
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
	void testUpdateTerminal() {
		terminal.setTerminalName("abc");
		when(terminalRepo.findByTerminalId(Mockito.any())).thenReturn(terminal);
		when(terminalRepo.existsByTerminalId(Mockito.any())).thenReturn(true);
		when(stationRepo.findByTermId(Mockito.any())).thenReturn(station);

		when(terminalRepo.save(Mockito.any())).thenReturn(terminal);
		terminalUpdated = terminalService.updateTerminal(terminal, header);		
		terminalUpdated.setTerminalId(123L);
		assertEquals(terminalUpdated,terminal);
	}
	@Test
	void testUpdateTerminalAlreadyExistTerminalNameExceptio() {
		terminal.setTerminalName("test");
		when(terminalRepo.existsByTerminalName(Mockito.any())).thenReturn(true);
		RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
				() -> when(terminalService.updateTerminal(terminal, header)));
		assertEquals("Terminal Name already exists:test",exception.getMessage());

	}
	@Test
	void testUpdateTerminalNullRenotify() {
		terminal.setRenotifyTime(null);
		when(terminalRepo.existsByTerminalId(Mockito.any())).thenReturn(true);
		when(terminalRepo.findByTerminalId(Mockito.any())).thenReturn(terminal);
		when(stationRepo.findByTermId(Mockito.any())).thenReturn(station);

		when(terminalRepo.save(Mockito.any())).thenReturn(terminal);
		terminalUpdated = terminalService.updateTerminal(terminal, header);
		terminalUpdated.setTerminalId(123L);
		assertEquals(terminalUpdated,terminal);
	}
	
	
	
	@Test
	void testAddTerminal() {
		when(terminalRepo.existsByTerminalId(Mockito.any())).thenReturn(false);
		when(terminalRepo.save(Mockito.any())).thenReturn(terminal);
		when(stationRepo.findByTermId(Mockito.any())).thenReturn(station);
		terminalAdded = terminalService.insertTerminal(terminal, header);
		assertEquals(terminalAdded,terminal);
		
	}
	@Test
	void testAddTerminalForNullTime() {
		terminal.setDeferredTime(null);
		terminal.setRenotifyTime(null);
		when(terminalRepo.existsByTerminalId(Mockito.any())).thenReturn(false);
		when(terminalRepo.save(Mockito.any())).thenReturn(terminal);
		when(stationRepo.findByTermId(Mockito.any())).thenReturn(station);
		terminalAdded = terminalService.insertTerminal(terminal, header);
		assertEquals(terminalAdded,terminal);

	}
	@Test
	void testAddTerminalForGreaterDeferredTime() {
		terminal.setDeferredTime("89");
		terminal.setRenotifyTime("26");
		when(terminalRepo.existsByTerminalId(Mockito.any())).thenReturn(false);
		when(terminalRepo.save(Mockito.any())).thenReturn(terminal);
		when(stationRepo.findByTermId(Mockito.any())).thenReturn(station);
		InvalidDataException exception = assertThrows(InvalidDataException.class,
				() -> when(terminalService.insertTerminal(terminal, header)));
		assertEquals("Deferred Time can't be more than 23 ",exception.getMessage());

	}
	@Test
	void testAddTerminalForGreaterRenotifyTime() {
		terminal.setRenotifyTime("26");
		when(terminalRepo.existsByTerminalId(Mockito.any())).thenReturn(false);
		when(terminalRepo.save(Mockito.any())).thenReturn(terminal);
		when(stationRepo.findByTermId(Mockito.any())).thenReturn(station);
		InvalidDataException exception = assertThrows(InvalidDataException.class,
				() -> when(terminalService.insertTerminal(terminal, header)));
		assertEquals("Renotify Time can't be more than 23 ",exception.getMessage());

	}
	@Test
	void testGetTerminal() {
		terminal = new Terminal();
		terminal.setTerminalId(1234L);
		when(terminalRepo.findByTerminalId(Mockito.any())).thenReturn(terminal);
		terminalAdded = terminalService.getTerminal(1234L);
		assertEquals(terminalAdded,terminal);
	}

	@Test
	void testGetTerminalNoRecord() {
		when(terminalRepo.findByTerminalId(Mockito.any())).thenReturn(null);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(terminalService.getTerminal(Mockito.any())));
		assertEquals("No Records Found",exception.getMessage());
	}

	 @Test
	 void testUpdateNoRecordsFoundException() {
		 NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
	                () -> when(terminalService.updateTerminal(terminal, header)));
	     assertEquals("Record with TerminalId 1234 Not Found!",exception.getMessage());
	    }
	 
	 @Test
	    void testAddRecordAlreadyExistsException() {
		 	terminal.setTerminalId(1234L);
			when(terminalRepo.existsByTerminalId(Mockito.any())).thenReturn(true);
			RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
					() -> when(terminalService.insertTerminal(terminal, header)));
			assertEquals("Terminal Id already exists:1234",exception.getMessage());
	    }
	@Test
	void testAddRecordAlreadyExistTerminalNameException() {
		terminal.setTerminalName("test");
		when(terminalRepo.existsByTerminalName(Mockito.any())).thenReturn(true);
		RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
				() -> when(terminalService.insertTerminal(terminal, header)));
		assertEquals("Terminal Name already exists:test",exception.getMessage());
	}
	 
	 @Test
		void testUpdateTerminalNoRecordsFoundException() {	
			NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
					() -> terminalService.updateTerminal(terminal, header));
			assertEquals("Record with TerminalId " + terminal.getTerminalId() + " Not Found!", exception.getMessage());
		}
	

		
	    @Test
	    void testTerminalInvalidDataException(){
			terminal.setDeferredTime("26");
			when(terminalRepo.existsByTerminalId(Mockito.any())).thenReturn(true);
			InvalidDataException exception = assertThrows(InvalidDataException.class,
					() -> when(terminalService.updateTerminal(terminal, header)));
			assertEquals("Deferred Time can't be more than 23 ", exception.getMessage());
			terminal.setRenotifyTime("26");
			terminal.setDeferredTime(null);
			exception = assertThrows(InvalidDataException.class,
					() -> when(terminalService.updateTerminal(terminal, header)));
			assertEquals("Renotify Time can't be more than 23 ", exception.getMessage());
	    }

}