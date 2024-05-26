package com.nscorp.obis.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import com.nscorp.obis.domain.UnCd;
import com.nscorp.obis.dto.UnCdDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.UnCdRepository;

public class UnCdServiceTest {

	@Mock
	UnCdRepository unCdRepository;

	@InjectMocks
	UnCdServiceImpl unCdServiceImpl;

	UnCdDTO unCdDTO;
	UnCd uncd;
	List<UnCd> unCdList;
	List<UnCdDTO> unCdDTOList;
	Map<String, String> header;

	String unCd;
	String unDsc;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		unCdList = new ArrayList<>();
		uncd = new UnCd();
		unCdDTO = new UnCdDTO();
		unCdDTOList = new ArrayList<>();
		uncd.setUnCd("QW235");
		uncd.setUnDsc("Hello");
		uncd.setUversion("!");
		unCdList.add(uncd);
		unCdDTO.setUnCd("QW235");
		unCdDTO.setUnDsc("Hello");
		unCdDTO.setUversion("!");
		unCdDTOList.add(unCdDTO);
		unCd = "D3";
		unDsc = "Hello";

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		uncd = null;
		unCdDTO = null;
		unCdList = null;
		unCdDTOList = null;
	}

	@Test
	void testGetAllUnCd() {
		when(unCdRepository.searchAll(Mockito.any())).thenReturn(unCdList);
		List<UnCd> enCode = unCdServiceImpl.getAllTables(unCd);
		assertNotNull(enCode);
	}

	@Test
	void testDeleteUnCode() {
		when(unCdRepository.existsByUnCdAndUversion(Mockito.any(), Mockito.any())).thenReturn(true);
		when(unCdRepository.findByUnCdAndUversion(Mockito.any(), Mockito.any())).thenReturn(uncd);
		unCdRepository.delete(Mockito.any());
		UnCd del = unCdServiceImpl.deleteUnCode(uncd);
	}

	@Test
	void testNoRecordFoundException() {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(unCdServiceImpl.getAllTables(unCd)));
		assertEquals("No Records found!", exception.getMessage());

		when(unCdRepository.existsByUnCdAndUversion(Mockito.any(), Mockito.any())).thenReturn(false);
		RecordNotDeletedException exception1 = assertThrows(RecordNotDeletedException.class,
				() -> when(unCdServiceImpl.deleteUnCode(uncd)));
		assertEquals("Record Not Found!", exception1.getMessage());

		when(unCdRepository.existsByUnCdAndUversion(Mockito.any(), Mockito.any())).thenReturn(false);
		exception = assertThrows(NoRecordsFoundException.class, () -> when(unCdServiceImpl.updateUnDesc(uncd, header)));
		assertEquals("No record found for this 'Un Code': QW235", exception.getMessage());

	}

	@Test
	void testAddRecordAlreadyExistsException() {

		when(unCdRepository.existsByUnCd(Mockito.any())).thenReturn(true);
		RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
				() -> when(unCdServiceImpl.addUnCode(uncd, header)));
		assertEquals("Record Already Exists", exception.getMessage());
	}

	@Test
	void testUpdateUnCode() {

		when(unCdRepository.existsByUnCdAndUversion(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		when(unCdRepository.findByUnCd(Mockito.anyString())).thenReturn((uncd));
		when(unCdRepository.save(Mockito.any())).thenReturn(uncd);
		UnCd updatedUnCode = unCdServiceImpl.updateUnDesc(uncd, header);
		assertNotNull(updatedUnCode);

	}

	@Test
	void testAddUnCode() {

		when(unCdRepository.existsByUnCdAndUversion(Mockito.anyString(), Mockito.anyString())).thenReturn(false);
		when(unCdRepository.save(Mockito.any())).thenReturn(uncd);
		UnCd updatedUnCode = unCdServiceImpl.addUnCode(uncd, header);
		assertNotNull(updatedUnCode);

	}
}
