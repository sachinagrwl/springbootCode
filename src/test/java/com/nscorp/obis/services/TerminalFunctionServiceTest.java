package com.nscorp.obis.services;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.Date;
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

import com.nscorp.obis.domain.Terminal;
import com.nscorp.obis.domain.TerminalFunction;
import com.nscorp.obis.dto.TerminalFunctionDTO;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.TerminalFunctionRepository;
import com.nscorp.obis.repository.TerminalRepository;

public class TerminalFunctionServiceTest {
	
	@InjectMocks
	TerminalFunctionServiceImpl terminalFunctionService;
	
	@Mock
	TerminalFunctionRepository terminalFunctionRepo;

	@Mock
	TerminalRepository terminalRepo;
	Terminal terminal;
	TerminalFunction terminalFunction;
    List<TerminalFunction> terminalFunctionList;
    TerminalFunctionDTO terminalFunctionDto;
    List<TerminalFunctionDTO> terminalFunctionDtoList;
    Map<String, String> header;
    List<String> functionName;
    
    @BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		terminal= new Terminal();
		terminal.setTerminalId(1L);
		terminal.setTerminalName("ABCD");
		terminalFunctionDto = new TerminalFunctionDTO();
		terminalFunction = new TerminalFunction();
		terminalFunctionDtoList = new ArrayList<>();
		terminalFunctionList = new ArrayList<>();
		
		terminalFunction.setTerminalId(1234L);
		terminalFunction.setFunctionName("ABCD");
		terminalFunction.setStatusFlag(null);
		terminalFunction.setEffectiveDate(null);
		terminalFunction.setEndDate(null);
		
		terminalFunctionList.add(terminalFunction);
	    
		terminalFunctionDto.setTerminalId(1234L);
		terminalFunctionDto.setFunctionName("ABCD");
		terminalFunctionDto.setStatusFlag(null);
		terminalFunctionDto.setEffectiveDate(null);
		terminalFunctionDto.setEndDate(null);
	    
		terminalFunctionDtoList.add(terminalFunctionDto);
	    
	    header = new HashMap<String, String>();
        header.put("userid", "Test");
        header.put("extensionschema", "Test");
	}
    
    @AfterEach
	void tearDown() throws Exception {
    	terminalFunction = null;
    	terminalFunctionList = null;
    	terminalFunctionDto = null;
    	terminalFunctionDtoList = null;
	}

	@Test
	void testGetTerminalFunction() {

		when(terminalFunctionRepo.existsByTerminalId(Mockito.any())).thenReturn(true);
		when(terminalFunctionRepo.existsByFunctionName(Mockito.any())).thenReturn(true);
		when(terminalFunctionRepo.findByTerminalIdAndFunctionName(Mockito.any(),Mockito.any())).thenReturn(terminalFunction);
		when(terminalFunctionRepo.findByTerminalIdOrFunctionName(Mockito.any(),Mockito.any())).thenReturn(terminalFunctionList);
		when(terminalFunctionRepo.findAll()).thenReturn(terminalFunctionList);
		when(terminalRepo.findByTerminalId(Mockito.any())).thenReturn(terminal);
		List<TerminalFunction> termFun = terminalFunctionService.getTerminalFunctionList(1234L, "ABCD");
		termFun = terminalFunctionService.getTerminalFunctionList(null, "ABCD");
		termFun = terminalFunctionService.getTerminalFunctionList(1234L, null);

//		when(terminalFunctionRepo.findByTerminalIdAndFunctionName(Mockito.any(),Mockito.any())).thenReturn(Collections.emptyList());
//		List<TerminalFunction> termFun1 = terminalFunctionService.getTerminalFunctionList(1234L, Collections.singletonList("ABCD"));

//		when(terminalFunctionRepo.findByTerminalIdAndFunctionName(Mockito.any(),Mockito.any())).thenReturn(null);
//		List<TerminalFunction> termFun1 = terminalFunctionService.getTerminalFunctionList(1234L, "ABCD");

	}

	@Test
	void testGetTerminalFunctionException() {
    	when(terminalFunctionRepo.existsByTerminalId(Mockito.any())).thenReturn(false);
    	when(terminalFunctionRepo.existsByFunctionName(Mockito.any())).thenReturn(false);
//		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
//                () -> when(terminalFunctionService.getTerminalFunctionList(null,null)));

		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
                () -> when(terminalFunctionService.getTerminalFunctionList(567L, null)));

		exception = assertThrows(NoRecordsFoundException.class,
                () -> when(terminalFunctionService.getTerminalFunctionList(null, "EFGH")));
	}

	@Test
	void testUpdateTerminalFunction() {
		terminalFunction.setFunctionName("EXPRESS_NS");
		terminalFunction.setStatusFlag("Y");
		terminalFunction.setEndDate(new Date(System.currentTimeMillis()));
		terminalFunction.setEffectiveDate(new Date(System.currentTimeMillis()-8640000));
		when(terminalFunctionRepo.existsByTerminalIdAndFunctionName(Mockito.any(), (Mockito.any()))).thenReturn(true);
		when(terminalFunctionRepo.findByTerminalIdAndFunctionName(Mockito.any(),Mockito.any())).thenReturn(terminalFunction);
		when(terminalFunctionRepo.save(Mockito.any())).thenReturn(terminalFunction);
		TerminalFunction addedRoad = terminalFunctionService.updateTerminalFunction(terminalFunction, header);
		
		terminalFunction.setEndDate(new Date(System.currentTimeMillis()));
		terminalFunction.setEffectiveDate(terminalFunction.getEndDate());
		addedRoad = terminalFunctionService.updateTerminalFunction(terminalFunction, header);
		

	}
	
	@Test
	void testUpdateTerminalFunctionInvalidDataException(){
		terminalFunction.setEndDate(new Date(System.currentTimeMillis()-8640000));
		terminalFunction.setEffectiveDate(new Date(System.currentTimeMillis()));
		when(terminalFunctionRepo.existsByTerminalIdAndFunctionName(Mockito.any(), (Mockito.any()))).thenReturn(true);
		when(terminalFunctionRepo.findByTerminalIdAndFunctionName(Mockito.any(),Mockito.any())).thenReturn(terminalFunction);
		InvalidDataException exception= assertThrows(InvalidDataException.class,
				() -> when(terminalFunctionService.updateTerminalFunction(terminalFunction, header)));
	}

	@Test
	void testUpdateTerminalFunctionNoRecordsFoundException() {
		when(terminalFunctionRepo.existsByTerminalIdAndFunctionName(Mockito.any(), (Mockito.any()))).thenReturn(false);
		when(terminalFunctionRepo.findByTerminalIdAndFunctionName(Mockito.any(),Mockito.any())).thenReturn(terminalFunction);
		when(terminalFunctionRepo.save(Mockito.any())).thenReturn(terminalFunction);
		NoRecordsFoundException exception1 = assertThrows(NoRecordsFoundException.class,
				() -> when(terminalFunctionService.updateTerminalFunction(terminalFunction, header)));
		assertEquals("Record with Terminal Function Not Found!", exception1.getMessage());
	}

}
