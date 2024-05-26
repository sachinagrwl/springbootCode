package com.nscorp.obis.services;

import com.nscorp.obis.domain.CodeTableSelection;
import com.nscorp.obis.dto.CodeTableSelectionDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.CodeTableSelectionRepository;
import com.nscorp.obis.repository.GenericCodeUpdateRepository;
import com.nscorp.obis.repository.ResourceListRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class CodeTableSelectionServiceTest {
	
	@InjectMocks
	CodeTableSelectionServiceImpl codeTableSelectionService;

	@Mock
	CodeTableSelectionRepository codeTableSelectionRepository;

	@Mock
	ResourceListRepository resourceListRepository;

	@Mock
	GenericCodeUpdateRepository genericCodeRepo;

	CodeTableSelection codeTableSelection;
	CodeTableSelectionDTO codeTableSelectionDto;
	List<CodeTableSelection> codeTableSelectionList;
	List<CodeTableSelectionDTO> codeTableSelectionDTOList;
	CodeTableSelection addedTable;
	CodeTableSelection tableUpdated;
	Map<String, String> header;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		codeTableSelection = new CodeTableSelection();
		codeTableSelection.setGenericTable("Test6");
		codeTableSelection.setGenericTableDesc("Test6");
		codeTableSelection.setGenCdFldSize((short) 1);
		codeTableSelection.setResourceNm("Testing");
		
		codeTableSelectionList = new ArrayList<>();
		
		codeTableSelectionDto = new CodeTableSelectionDTO();
		codeTableSelectionDto.setGenericTable("Test6");
		codeTableSelectionDto.setGenericTableDesc("Test6");
		codeTableSelectionDto.setGenCdFldSize((short) 1);
		codeTableSelectionDto.setResourceNm("Testing");

		codeTableSelectionDTOList = new ArrayList<>();
		codeTableSelectionDTOList.add(codeTableSelectionDto);
		codeTableSelectionList.add(codeTableSelection);
		
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		codeTableSelectionList = null;
		codeTableSelectionDTOList = null;
		codeTableSelection = null;
		codeTableSelectionDto = null;
	}

	@Test
	void testGetAllTables() {
		when(codeTableSelectionRepository.findAllByOrderByGenericTableAsc()).thenReturn(codeTableSelectionList);
		List<CodeTableSelection> allTable = codeTableSelectionService.getAllTables();
		assertEquals(allTable, codeTableSelectionList);
	}

	@Test
	void testGetAllTablesException() {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(codeTableSelectionService.getAllTables()));
		Assertions.assertEquals("No Records are found for Generic Table", exception.getMessage());
	}

	@Test
	void testInsertTable() {
		when(codeTableSelectionRepository.existsById(Mockito.any())).thenReturn(false);
		when(resourceListRepository.existsByResourceNameIgnoreCase(codeTableSelection.getResourceNm())).thenReturn(true);
		when(codeTableSelectionRepository.save(Mockito.any())).thenReturn(codeTableSelection);
		addedTable = codeTableSelectionService.insertTable(codeTableSelection, header);
		assertNotNull(addedTable);
	}

	@Test
	void testAddGenericTableException() {
		when(codeTableSelectionRepository.existsById(Mockito.any())).thenReturn(true);

		RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
				() -> when(codeTableSelectionService.insertTable(codeTableSelection, header)));
		assertEquals("Record with Table Name Test6 Already Exists!", exception.getMessage());
	}

	@Test
	void testAddGenericTableRecordNotAddedException() {
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(codeTableSelectionService.insertTable(codeTableSelection, header)));
		assertEquals("Record with Table Name Test6 cannot be Added!", exception.getMessage());
	}

	@Test
	void testResourceNmNoRecordsFoundException() {
		codeTableSelection.setResourceNm(null);
		assertNull(codeTableSelection.getResourceNm());
		when(resourceListRepository.existsByResourceNameIgnoreCase(codeTableSelection.getResourceNm())).thenReturn(false);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(codeTableSelectionService.insertTable(codeTableSelection, header)));
		assertEquals("Resource NM null is not available ", exception.getMessage());

	}

	@Test
	void testUpdateCodeTableSelection() {
		when(codeTableSelectionRepository.existsByGenericTable(Mockito.any())).thenReturn(true);
		when(codeTableSelectionRepository.findByGenericTable(Mockito.any())).thenReturn(codeTableSelection);
		when(resourceListRepository.existsByResourceNameIgnoreCase(codeTableSelection.getResourceNm())).thenReturn(true);
		when(codeTableSelectionRepository.save(Mockito.any())).thenReturn(codeTableSelection);
		tableUpdated = codeTableSelectionService.updateCodeTableSelection(codeTableSelection, header);
		assertEquals(tableUpdated.getGenericTable(), codeTableSelection.getGenericTable());
	}
	@Test
	void testUpdateGenericTableNoRecordsFoundException() {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(codeTableSelectionService.updateCodeTableSelection(codeTableSelection, header)));
		assertEquals("Record with Table Name Test6 Not Found!", exception.getMessage());
	}

	@Test
	void testDeleteTable() {
		when(codeTableSelectionRepository.existsByGenericTable("Test6")).thenReturn(true);
		when(genericCodeRepo.existsByGenericTableIgnoreCase(Mockito.any())).thenReturn(false);
		codeTableSelectionRepository.deleteByGenericTable(Mockito.any());
		codeTableSelectionService.deleteTable(codeTableSelection);
	}

	@Test
	void testDeleteTableException() {
		when(codeTableSelectionRepository.existsByGenericTable("Test6")).thenReturn(true);
		when(genericCodeRepo.existsByGenericTableIgnoreCase(Mockito.any())).thenReturn(true);
		RecordNotDeletedException exception = assertThrows(RecordNotDeletedException.class,
				() -> codeTableSelectionService.deleteTable(codeTableSelection));
		assertEquals("Test6 Table has records and cannot be deleted!", exception.getMessage());
	}

	@Test
	void testDeleteTabletRecordNotAddedException() {
		RecordNotDeletedException exception = assertThrows(RecordNotDeletedException.class,
				() -> codeTableSelectionService.deleteTable(codeTableSelection));
		assertEquals("Test6 Table Not Found!", exception.getMessage());
	}

}
