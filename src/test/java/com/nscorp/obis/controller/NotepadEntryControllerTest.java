package com.nscorp.obis.controller;

import com.nscorp.obis.domain.NotepadEntry;
import com.nscorp.obis.dto.NotepadEntryDTO;
import com.nscorp.obis.dto.mapper.NotepadEntryMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.NotepadEntryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class NotepadEntryControllerTest {
	
	@InjectMocks
	NotepadEntryController notepadEntryController;
	
	@Mock
	NotepadEntryMapper notepadEntryMapper;
	
	@Mock
	NotepadEntryService notepadEntryService;
	
	NotepadEntry notepadEntry;
	List<NotepadEntry> notepadEntrys;
	NotepadEntryDTO notepadEntryDTO;

	Long customerId;
	Long terminalId;
	
	Map<String, String> header;
	


	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		notepadEntry = new NotepadEntry();
		notepadEntry.setCustomerId(10024L);
		notepadEntry.setNotepadText("test");

		customerId = 68744832863098L;
		terminalId = 46544102182938L;

		notepadEntrys = new ArrayList<NotepadEntry>();
		notepadEntrys.add(notepadEntry);

		notepadEntryDTO = new NotepadEntryDTO();
		notepadEntryDTO.setCustomerId(10024L);
		notepadEntryDTO.setNotepadText("test");

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		notepadEntryDTO = null;
		notepadEntry = null;
	}

	@Test
	void testGetNotepadEntry() throws SQLException {
		when(notepadEntryService.fetchNotepadEntry(Mockito.any(), Mockito.any()))
				.thenReturn(notepadEntrys);
		when(notepadEntryMapper.NotepadEntryDTOToNotepadEntry(Mockito.any())).thenReturn(notepadEntry);
		when(notepadEntryMapper.NotepadEntryToNotepadEntryDTO(Mockito.any()))
				.thenReturn(notepadEntryDTO);
		ResponseEntity<APIResponse<List<NotepadEntryDTO>>> getData = notepadEntryController
				.getNotepadEntry(customerId, terminalId);
		assertEquals(getData.getStatusCodeValue(), 200);
	}

	@Test
	@DisplayName("NoRecordsFoundException")
	void testGetNoRecordsFoundException() throws SQLException {
		when(notepadEntryService.fetchNotepadEntry(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<NotepadEntryDTO>>> getData = notepadEntryController
				.getNotepadEntry(customerId, terminalId);
		assertEquals(getData.getStatusCodeValue(), 404);

	}

	@Test
	@DisplayName("Internal Server Error")
	void testException() throws SQLException {
		when(notepadEntryService.fetchNotepadEntry(Mockito.any(), Mockito.any()))
				.thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<NotepadEntryDTO>>> getData = notepadEntryController
				.getNotepadEntry(customerId, terminalId);
		assertEquals(getData.getStatusCodeValue(), 500);
	}

	@Test
	void testAddNotepadEntry() {
		when(notepadEntryMapper.NotepadEntryDTOToNotepadEntry(Mockito.any())).thenReturn(notepadEntry);
		when(notepadEntryService.addNotepadEntry(Mockito.any(), Mockito.any())).thenReturn(notepadEntry);
		when(notepadEntryMapper.NotepadEntryToNotepadEntryDTO(Mockito.any())).thenReturn(notepadEntryDTO);
		ResponseEntity<APIResponse<NotepadEntryDTO>> responseEntity = notepadEntryController.addNotepadEntry(notepadEntryDTO,
				header);
		assertNotNull(responseEntity.getBody());
	}


	@Test
	void testNotepadEntryNoRecordsFoundException() throws SQLException {
		when(notepadEntryService.addNotepadEntry(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<NotepadEntryDTO>> addedNotepadEntry = notepadEntryController.addNotepadEntry(notepadEntryDTO,
				header);
		assertEquals(addedNotepadEntry.getStatusCodeValue(),404);

		
	}

	@Test
	void testNotepadEntryRecordNotAddedException() {
		when(notepadEntryService.addNotepadEntry(Mockito.any(), Mockito.any())).thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<NotepadEntryDTO>> addedNotepadEntry = notepadEntryController.addNotepadEntry(notepadEntryDTO,
				header);
		assertEquals(addedNotepadEntry.getStatusCodeValue(),406);

	}

	@Test
	void testNotepadEntryRecordNullPointerException() {
		when(notepadEntryService.addNotepadEntry(Mockito.any(), Mockito.any())).thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<NotepadEntryDTO>> addedNotepadEntry = notepadEntryController.addNotepadEntry(notepadEntryDTO,
				header);
		assertEquals(addedNotepadEntry.getStatusCodeValue(),500);

	}

	@Test
	void testOverrideWeightsException() {
		when(notepadEntryService.addNotepadEntry(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<NotepadEntryDTO>> addedNotepadEntry = notepadEntryController.addNotepadEntry(notepadEntryDTO,
				header);
		assertEquals(addedNotepadEntry.getStatusCodeValue(), 500);
	}


}
