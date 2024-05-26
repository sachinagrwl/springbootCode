package com.nscorp.obis.services;

import com.nscorp.obis.common.SpecificationGenerator;
import com.nscorp.obis.domain.NotepadEntry;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.CustomerRepository;
import com.nscorp.obis.repository.NotepadEntryRepository;
import com.nscorp.obis.repository.TerminalRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class NotepadEntryServiceTest {

    @InjectMocks
    NotepadEntryServiceImpl notepadEntryService;

    @Mock
    NotepadEntryRepository notepadEntryRepository;

    @Mock
	SpecificationGenerator specificationGenerator;
    
    @Mock
    CustomerRepository customerRepository;
    NotepadEntry notepadEntry;

    @Mock
	TerminalRepository terminalRepository;

    List<NotepadEntry> notepadEntrys;
    Map<String, String> header;
    Specification<NotepadEntry> specification;
    Long customerId;
	Long terminalId;

    @SuppressWarnings("deprecation")
    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        customerId = 68744832863098L;
		terminalId = 46544102182938L;
        notepadEntry = new NotepadEntry();
        notepadEntry.setCustomerId(customerId);
        notepadEntry.setTerminalId(terminalId);
        notepadEntry.setNotepadText("test");

        
        notepadEntrys = new ArrayList<NotepadEntry>();
        notepadEntrys.add(notepadEntry);
        specification = specificationGenerator.notepadEntrySpecification(terminalId, customerId);

        header = new HashMap<String, String>();
        header.put("userid", "Test");
        header.put("extensionschema", "Test");
    }

    @AfterEach
    void tearDown() throws Exception{
        notepadEntry = null;
    }

    @Test
	void testFetchNotepadEntry() throws SQLException {
		when(terminalRepository.existsByTerminalId(notepadEntry.getTerminalId())).thenReturn(true);
		when(customerRepository.existsByCustomerId(notepadEntry.getCustomerId())).thenReturn(true);
		when(notepadEntryRepository.findAll(specification)).thenReturn(notepadEntrys);
		when(notepadEntryService.fetchNotepadEntry(customerId,terminalId))
				.thenReturn(notepadEntrys);
	}

    @Test
	void testNoRecordFoundException() throws SQLException {
		
        NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(notepadEntryService.fetchNotepadEntry(customerId,terminalId)));
        assertEquals("No Terminal Found with this terminal id : " + terminalId, exception.getMessage());

        when(terminalRepository.existsByTerminalId(notepadEntry.getTerminalId())).thenReturn(true);
		NoRecordsFoundException exception2 = assertThrows(NoRecordsFoundException.class,
				() -> when(notepadEntryService.fetchNotepadEntry(customerId,terminalId)));
        assertEquals("No Customer Found with this customer id : " + customerId, exception2.getMessage());
		customerId = null;
		terminalId = null;
		
		NoRecordsFoundException exception3 = assertThrows(NoRecordsFoundException.class,
            () -> when(notepadEntryService.fetchNotepadEntry(customerId,terminalId)));
		assertEquals("Pass any parameter", exception3.getMessage());

		customerId = 68744832863098L;
		terminalId = 46544102182938L;
		
		when(notepadEntrys.isEmpty()).thenReturn(true);
		NoRecordsFoundException exception4 = assertThrows(NoRecordsFoundException.class,
				() -> when(notepadEntryService.fetchNotepadEntry(customerId,terminalId)));
		assertEquals("No Record found for this combination", exception4.getMessage());

    }
    
    @Test
    void testAddNotepadEntry() {
        when(customerRepository.existsByCustomerId(notepadEntry.getCustomerId())).thenReturn(true);
        when(notepadEntryRepository.save(Mockito.any())).thenReturn(notepadEntry);
        NotepadEntry addedNotepadEntry = notepadEntryService.addNotepadEntry(notepadEntry, header);
        assertNotNull(addedNotepadEntry);

    }


    @Test
    void testAddRulesCircularException() {
        NotepadEntry obj = new NotepadEntry();
        obj.setCustomerId(7503L);
        RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
                () -> when(notepadEntryService
                        .addNotepadEntry(obj, header)));
        assertEquals("No Customer Found with this customer id : "+obj.getCustomerId(), exception.getMessage());


        RecordNotAddedException exception2 = assertThrows(RecordNotAddedException.class,
                () -> when(notepadEntryService
                        .addNotepadEntry(obj, header)));
        assertEquals("Record Not added to Database", exception2.getMessage());
    }



}