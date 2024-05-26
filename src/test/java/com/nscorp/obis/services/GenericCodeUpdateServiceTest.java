package com.nscorp.obis.services;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.DomainValueConstants;
import com.nscorp.obis.domain.CodeTableSelection;
import com.nscorp.obis.domain.GenericCodeUpdate;
import com.nscorp.obis.dto.GenericCodeUpdateDTO;
import com.nscorp.obis.exception.*;
import com.nscorp.obis.repository.CodeTableSelectionRepository;
import com.nscorp.obis.repository.GenericCodeUpdateRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class GenericCodeUpdateServiceTest {

	@InjectMocks
	GenericCodeUpdateServiceImpl genericCodeUpdateService;

	@Mock
	GenericCodeUpdateRepository genericCodeUpdateRepository;

	@Mock
	CodeTableSelectionRepository codeTableSelectionRepository;
	GenericCodeUpdate genericCodeUpdate;
	CodeTableSelection codeTableSelection;
	GenericCodeUpdate genericCodeUpdateResource;
	GenericCodeUpdateDTO genericCodeUpdateDto;
	List<GenericCodeUpdate> genericCodeUpdateList;
	List<GenericCodeUpdateDTO> genericCodeUpdateDTOList;
	GenericCodeUpdate addedTableCode;
	GenericCodeUpdate tableCodeUpdated;
	Map<String, String> header;
	String tableName = "CAN";
	
	@BeforeEach
	void setUp() throws Exception {
		
		MockitoAnnotations.openMocks(this);
		
		genericCodeUpdate = new GenericCodeUpdate();
		genericCodeUpdate.setGenericTable("Test6");
		genericCodeUpdate.setGenericTableCode("p1");
		genericCodeUpdate.setGenericLongDescription("Test");
		genericCodeUpdate.setGenericShortDescription("Test");
		
		genericCodeUpdateList = new ArrayList<>();
		genericCodeUpdateList.add(genericCodeUpdate);

		genericCodeUpdateDto = new GenericCodeUpdateDTO();
		genericCodeUpdateDto.setGenericTable("Test6");
		genericCodeUpdateDto.setGenericTableCode("p1");
		genericCodeUpdateDto.setGenericLongDescription("Test");
		genericCodeUpdateDto.setGenericShortDescription("Test");
		genericCodeUpdateDTOList = new ArrayList<>();
		genericCodeUpdateDTOList.add(genericCodeUpdateDto);
		codeTableSelection = new CodeTableSelection();
		codeTableSelection.setGenericTable("Test6");
		codeTableSelection.setGenCdFldSize((short) 2);

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		genericCodeUpdateList = null;
		genericCodeUpdateDto = null;
		genericCodeUpdate = null;
		genericCodeUpdateDTOList = null;
	}

	@Test
	void testGetByTableName() {
		when(codeTableSelectionRepository.existsByGenericTableIgnoreCase(tableName)).thenReturn(true);
		when(genericCodeUpdateRepository.findByGenericTableIgnoreCase(Mockito.any())).thenReturn(genericCodeUpdateList);
		List<GenericCodeUpdate> allTableCode = genericCodeUpdateService.getByTableName(tableName);
		assertEquals(allTableCode, genericCodeUpdateList);
	}
	@Test
	void testGetByTableNameException() {
		when(codeTableSelectionRepository.existsByGenericTableIgnoreCase(tableName)).thenReturn(false);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(genericCodeUpdateService.getByTableName(Mockito.any())));
		assertEquals("No record found under the TableName: null", exception.getMessage());
	}

	@Test
	void testInsertCode() {
		when(codeTableSelectionRepository.existsByGenericTableIgnoreCase(Mockito.any())).thenReturn(true);
		when(codeTableSelectionRepository.findBySize(Mockito.any())).thenReturn(codeTableSelection.getGenCdFldSize());
		when(genericCodeUpdateRepository.existsByGenericTableAndGenericTableCodeIgnoreCase(Mockito.any(),Mockito.any())).thenReturn(false);
		when(genericCodeUpdateRepository.save(Mockito.any())).thenReturn(genericCodeUpdate);
		addedTableCode = genericCodeUpdateService.insertCode(genericCodeUpdate, header);
		assertNotNull(addedTableCode);
	}

	@Test
	void testInsertCodeNoRecordsFoundException() {
		when(codeTableSelectionRepository.existsByGenericTableIgnoreCase(Mockito.any())).thenReturn(false);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(genericCodeUpdateService.insertCode(genericCodeUpdate,header)));
		assertEquals("No record found under this TableName: TEST6", exception.getMessage());
	}

	@Test
	void testInsertCodeSizeExceedException() {
		when(codeTableSelectionRepository.existsByGenericTableIgnoreCase(Mockito.any())).thenReturn(true);
		assertTrue(genericCodeUpdate.getGenericTableCode().length() > codeTableSelectionRepository.findBySize(tableName));

		SizeExceedException exception = assertThrows(SizeExceedException.class,
				() -> when(genericCodeUpdateService.insertCode(genericCodeUpdate,header)));
		assertEquals("Maximum Length for Table code is 0", exception.getMessage());
	}

	@Test
	void testInsertCodeRecordAlreadyExistsException() {
		when(codeTableSelectionRepository.existsByGenericTableIgnoreCase(Mockito.any())).thenReturn(true);
		when(codeTableSelectionRepository.findBySize(Mockito.any())).thenReturn(codeTableSelection.getGenCdFldSize());
		when(genericCodeUpdateRepository.existsByGenericTableAndGenericTableCodeIgnoreCase(Mockito.any(),Mockito.any())).thenReturn(true);
		RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
				() -> when(genericCodeUpdateService.insertCode(genericCodeUpdate,header)));
		assertEquals("Table Code is already exists under TEST6", exception.getMessage());
	}

	@Test
	void testInsertCodeRecordNotAddedException() {
		when(codeTableSelectionRepository.existsByGenericTableIgnoreCase(Mockito.any())).thenReturn(true);
		when(codeTableSelectionRepository.findBySize(Mockito.any())).thenReturn(codeTableSelection.getGenCdFldSize());
		when(genericCodeUpdateRepository.existsByGenericTableAndGenericTableCodeIgnoreCase(Mockito.any(),Mockito.any())).thenReturn(false);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(genericCodeUpdateService.insertCode(genericCodeUpdate,header)));
		assertEquals("Record Not added to Database", exception.getMessage());
	}

	@Test
	void testUpdateCode() {
		when(genericCodeUpdateRepository.existsByGenericTableAndGenericTableCodeIgnoreCase(Mockito.any(),Mockito.any())).thenReturn(true);
		when(genericCodeUpdateRepository.findByGenericTableAndGenericTableCodeIgnoreCase(Mockito.any(),Mockito.any())).thenReturn(genericCodeUpdate);
		when(genericCodeUpdateRepository.save(any(GenericCodeUpdate.class))).thenReturn(genericCodeUpdateResource);
		tableCodeUpdated = genericCodeUpdateService.updateCode(genericCodeUpdate, header);
		assertEquals(tableCodeUpdated, genericCodeUpdate);
	}

	@Test
	void testUpdateCodeValidFlagEDI() {
		tableName = "EDI_RSN";
		genericCodeUpdate.setGenericTable(tableName);
		String genFlag = "D";
		genericCodeUpdate.setGenericFlag(genFlag);
		when(genericCodeUpdateRepository.existsByGenericTableAndGenericTableCodeIgnoreCase(Mockito.any(),Mockito.any())).thenReturn(true);
		when(genericCodeUpdateRepository.findByGenericTableAndGenericTableCodeIgnoreCase(Mockito.any(),Mockito.any())).thenReturn(genericCodeUpdate);
		assertTrue(tableName.equalsIgnoreCase(CommonConstants.EDI_RSN_TABLE));
		assertFalse(Arrays.asList(DomainValueConstants.EDI_RSN_VALUES).contains(genFlag));

		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(genericCodeUpdateService.updateCode(genericCodeUpdate,header)));
		assertEquals("'EDI_RSN' table can have 'GEN_FLAG' values of: 'Y', 'N' & null only", exception.getMessage());
	}

	@Test
	void testUpdateCodeValidFlagINREJECT() {
		tableName = "INREJECT";
		genericCodeUpdate.setGenericTable(tableName);
		String genFlag = "D";
		genericCodeUpdate.setGenericFlag(genFlag);
		when(genericCodeUpdateRepository.existsByGenericTableAndGenericTableCodeIgnoreCase(Mockito.any(),Mockito.any())).thenReturn(true);
		when(genericCodeUpdateRepository.findByGenericTableAndGenericTableCodeIgnoreCase(Mockito.any(),Mockito.any())).thenReturn(genericCodeUpdate);
		assertTrue(tableName.equalsIgnoreCase(CommonConstants.INREJECT_TABLE));
		assertFalse(Arrays.asList(DomainValueConstants.ALLOWED_GEN_FLAG_VALUES).contains(genFlag));
		assertTrue(tableName.equalsIgnoreCase(CommonConstants.INREJECT_TABLE));

		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(genericCodeUpdateService.updateCode(genericCodeUpdate,header)));
		assertEquals(" 'INREJECT' table can have 'GEN_FLAG' values of: 'B', 'C', 'T' & null only", exception.getMessage());
	}

	@Test
	void testUpdateCodeValidFlagINRTRJCT() {
		tableName = "INRTRJCT";
		genericCodeUpdate.setGenericTable(tableName);
		String genFlag = "D";
		genericCodeUpdate.setGenericFlag(genFlag);
		when(genericCodeUpdateRepository.existsByGenericTableAndGenericTableCodeIgnoreCase(Mockito.any(),Mockito.any())).thenReturn(true);
		when(genericCodeUpdateRepository.findByGenericTableAndGenericTableCodeIgnoreCase(Mockito.any(),Mockito.any())).thenReturn(genericCodeUpdate);
		assertTrue(tableName.equalsIgnoreCase(CommonConstants.INRTRJCT_TABLE));
		assertFalse(Arrays.asList(DomainValueConstants.ALLOWED_GEN_FLAG_VALUES).contains(genFlag));
		assertTrue(tableName.equalsIgnoreCase(CommonConstants.INRTRJCT_TABLE));

		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(genericCodeUpdateService.updateCode(genericCodeUpdate,header)));
		assertEquals(" 'INRTRJCT' table can have 'GEN_FLAG' values of: 'B', 'C', 'T' & null only", exception.getMessage());
	}

	@Test
	void testUpdateCodeValidFlagLP_CFG_TABLE() {
		tableName = "LP_CFG";
		genericCodeUpdate.setGenericTable(tableName);
		String genFlag = "D";
		genericCodeUpdate.setGenericFlag(genFlag);
		when(genericCodeUpdateRepository.existsByGenericTableAndGenericTableCodeIgnoreCase(Mockito.any(),Mockito.any())).thenReturn(true);
		when(genericCodeUpdateRepository.findByGenericTableAndGenericTableCodeIgnoreCase(Mockito.any(),Mockito.any())).thenReturn(genericCodeUpdate);
		assertTrue(tableName.equalsIgnoreCase(CommonConstants.LP_CFG_TABLE));
		assertFalse(Arrays.asList(DomainValueConstants.ALLOWED_GEN_FLAG_VALUES).contains(genFlag));
		assertTrue(tableName.equalsIgnoreCase(CommonConstants.LP_CFG_TABLE));

		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(genericCodeUpdateService.updateCode(genericCodeUpdate,header)));
		assertEquals(" 'LP_CFG' table can have 'GEN_FLAG' values of: 'B', 'C', 'T' & null only", exception.getMessage());
	}


	@Test
	void testUpdateCodeNoRecordsFoundException() {
		when(genericCodeUpdateRepository.existsByGenericTableAndGenericTableCodeIgnoreCase(genericCodeUpdate.getGenericTable(),genericCodeUpdate.getGenericTableCode())).thenReturn(false);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(genericCodeUpdateService.updateCode(genericCodeUpdate,header)));
		assertEquals("No record Found Under this TableName and Table Code", exception.getMessage());
	}

	@Test
	void testDeleteCode() {
		when(genericCodeUpdateRepository.existsByGenericTableAndGenericTableCodeIgnoreCase(Mockito.any(),Mockito.any())).thenReturn(true);
		genericCodeUpdateService.deleteCode(genericCodeUpdate);
	}

	@Test
	void testDeleteCodeRecordNotDeletedException() {
		RecordNotDeletedException exception = assertThrows(RecordNotDeletedException.class,
				() -> genericCodeUpdateService.deleteCode(genericCodeUpdate));
		assertEquals("TEST6 and P1 Record Not Found!", exception.getMessage());
	}
}
