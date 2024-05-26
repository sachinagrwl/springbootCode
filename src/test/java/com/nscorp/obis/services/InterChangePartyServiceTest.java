package com.nscorp.obis.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.domain.InterChangeParty;
import com.nscorp.obis.dto.InterChangePartyDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.InterChangePartyRepository;

public class InterChangePartyServiceTest {
	@InjectMocks
	InterChangePartyServiceImpl inteChangePartyService;
	@Mock
	InterChangePartyRepository inteChangePartyRepo;
	
	InterChangeParty interChangeParty;
	InterChangeParty interChangePartyAdded;
	InterChangePartyDTO interChangePartyDto;
	List<InterChangeParty> interChangePartyList;
	List<InterChangePartyDTO> interChangePartyDTOList;
	Map<String, String> header;

	String ichgCode;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		interChangeParty = new InterChangeParty();
		interChangeParty.setIchgCode("BAR");
		interChangeParty.setIchgCdDesc("Valid");
		interChangeParty.setRoadOtherInd("DC");
		interChangeParty.setCreateUserId("use");
		interChangeParty.setUpdateExtensionSchema("ext");
		interChangeParty.setCreateDateTime(null);
		interChangeParty.setUpdateDateTime(null);
		interChangeParty.setUversion("uver");
		interChangeParty.setRoadOtherInd("D");
		interChangeParty.setCreateUserId("use");
		interChangePartyList = new ArrayList<>();
		interChangePartyList.add(interChangeParty);
		
		ichgCode = "BAR";
		
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}
	@AfterEach
	void tearDown() throws Exception {
		interChangeParty=null;
	}

	@Test
	void testDeleteInterChangeParty() {
		when(inteChangePartyRepo.existsById(Mockito.anyString())).thenReturn(true);
		inteChangePartyRepo.deleteById(Mockito.anyString());
		inteChangePartyService.deleteInterChangeParty(interChangeParty);
	}
	@Test
	void testRecordNotFoundExceptionDeleteInterChangeParty() {
		when(inteChangePartyRepo.existsById(Mockito.anyString())).thenReturn(false);
		
		RecordNotDeletedException exception = assertThrows(RecordNotDeletedException.class, 
				() -> inteChangePartyService.deleteInterChangeParty(interChangeParty));
		assertEquals("Record Not Found!", exception.getMessage());
		interChangePartyList=null;
	}

	@Test
	void testGetInterChangeParty() {
		when(inteChangePartyRepo.getByIchgCode(ichgCode)).thenReturn(interChangePartyList);
		List<InterChangeParty> interchange = inteChangePartyService.getAllTables(ichgCode);
		assertEquals(interchange,interChangePartyList);
	}
	
	@Test
	void testGetNoRecordsFoundException() {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(inteChangePartyService.getAllTables(Mockito.anyString())));
		Assertions.assertEquals("No Records found", exception.getMessage());
	}
	
//	@Test
//	void testAddNoRecordsFoundException() {
//		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
//				() -> when(inteChangePartyService.insertInterChangeParty(interChangeParty, header)));
//		assertEquals("Interchange party is not defined in Road Table", exception.getMessage());
//	}
	
	@Test
	void testAddInterChangeParty() {
		when(inteChangePartyRepo.existsById(Mockito.any())).thenReturn(false);
		when(inteChangePartyRepo.save(Mockito.any())).thenReturn(interChangeParty);			
		interChangePartyAdded = inteChangePartyService.insertInterChangeParty(interChangeParty, header);
		assertEquals(interChangePartyAdded,interChangeParty);
	}
	
	@Test
	void testInterChangePartyRecordAlreadyExistsException() {
		when(inteChangePartyRepo.existsById(Mockito.any())).thenReturn(true);
		RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
				() -> when(inteChangePartyService.insertInterChangeParty(interChangeParty, header)));
		assertEquals("Record with IchgCode Already Exists!", exception.getMessage());		

	}
	
	@Test
    void testUpdateInterChangeParty() {

 

        when(inteChangePartyRepo.existsByIchgCode(Mockito.any())).thenReturn(true);
        when(inteChangePartyRepo.findByIchgCode(Mockito.any())).thenReturn(interChangeParty);
        when(inteChangePartyRepo.save(Mockito.any())).thenReturn(interChangeParty);
        InterChangeParty interChangePartyUpdated = inteChangePartyService.updateInterChangeParty(interChangeParty, header);        

        assertEquals(interChangePartyUpdated,interChangeParty);
    }

    @Test
     void testUpdateNoRecordsFoundException() {
         NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
                    () -> when(inteChangePartyService.updateInterChangeParty(interChangeParty, header)));
         assertEquals("Record with IchgCode BAR Not Found!",exception.getMessage());

 

        }
}
