package com.nscorp.obis.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.domain.EndorsementCode;
import com.nscorp.obis.dto.EndorsementCodeDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.EndorsementCodeRepository;

public class EndorsementCodeServiceTest {

	@Mock
	EndorsementCodeRepository endorsementCodeRepo;

	@InjectMocks
	EndorsementCodeServiceImpl endorsementCodeServiceImpl;

	EndorsementCodeDTO endorsementCodeDTO;
	EndorsementCode endorsementCode;
	List<EndorsementCode> endorsementCodeList;
	String endorsementCd;
	String endorseCdDesc;
	Map<String, String> header;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		endorsementCodeList = new ArrayList<>();
		endorsementCode = new EndorsementCode();
		endorsementCodeDTO = new EndorsementCodeDTO();
		endorsementCode.setEndorsementCd("D5");
		endorsementCode.setEndorseCdDesc("Hello");
		endorsementCd = "D3";
		endorseCdDesc = "Hello";

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@Test
	void testGetEndorsementCd() {
		when(endorsementCodeRepo.searchAll(Mockito.any(), Mockito.any())).thenReturn(endorsementCodeList);
		List<EndorsementCode> enCode = endorsementCodeRepo.searchAll(endorsementCd, endorseCdDesc);
		assertEquals(enCode, endorsementCodeList);
	}

	@Test
	void testAddEndorsementCd() {
		when(endorsementCodeRepo.existsById(Mockito.any())).thenReturn(false);
		when(endorsementCodeRepo.save(Mockito.any())).thenReturn(endorsementCode);
		endorsementCode = endorsementCodeServiceImpl.addEndorsementCode(endorsementCode, header);
		assertEquals(endorsementCode, endorsementCode);
	}
	
	@Test
	void testUpdateEndorsementCd() {
		endorsementCode.setEndorsementCd("D4");
		endorsementCode.setEndorseCdDesc("Hwllo");
		//when(endorsementCodeRepo.existsById(Mockito.any())).thenReturn(true);
		//when(endorsementCodeRepo.findByEndorsementCd(Mockito.any())).thenReturn(endorsementCode);
		when(endorsementCodeRepo.save(Mockito.any())).thenReturn(endorsementCode);
		EndorsementCode endCode = endorsementCodeServiceImpl.updateEndorsementCode(endorsementCode, header);
		assertEquals(endCode, endorsementCode);
		
		endorsementCode.setEndorsementCd("D4");
		endorsementCode.setEndorseCdDesc(null);
		endCode = endorsementCodeServiceImpl.updateEndorsementCode(endorsementCode, header);
		assertEquals(endCode, endorsementCode);
		
		endorsementCode.setEndorsementCd(null);
		endCode = endorsementCodeServiceImpl.updateEndorsementCode(endorsementCode, header);
		assertEquals(endCode, endorsementCode);
	}

	@Test
	void testNoRecordFoundException() {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(endorsementCodeServiceImpl.getAllTables(endorsementCd, endorseCdDesc)));
		assertEquals("No Records found!", exception.getMessage());

		when(endorsementCodeRepo.existsById(endorsementCd)).thenReturn(false);
		endorsementCodeRepo.deleteById(endorsementCd);

		RecordNotDeletedException exception1 = assertThrows(RecordNotDeletedException.class,
				() -> endorsementCodeServiceImpl.deleteEndorsementCode(endorsementCode));
		assertEquals("Record Not Found!", exception1.getMessage());

		endorsementCode.setEndorsementCd(null);
		when(endorsementCodeRepo.existsById(Mockito.any())).thenReturn(true);
		NoRecordsFoundException exception2 = assertThrows(NoRecordsFoundException.class,
				() -> when(endorsementCodeServiceImpl.addEndorsementCode(endorsementCode, header)));
		assertEquals("W-ENDORSEMENT_CD Required", exception2.getMessage());
		
		endorsementCode.setEndorsementCd("D4");
		endorsementCode.setEndorseCdDesc(null);
		when(endorsementCodeRepo.existsById(Mockito.any())).thenReturn(false);
		NoRecordsFoundException exception3 = assertThrows(NoRecordsFoundException.class,
				() -> when(endorsementCodeServiceImpl.addEndorsementCode(endorsementCode, header)));
		assertEquals("W-DESC_DATA required", exception3.getMessage());
		
		endorsementCode.setEndorsementCd(null);
		NoRecordsFoundException exception4 = assertThrows(NoRecordsFoundException.class,
				() -> when(endorsementCodeServiceImpl.updateEndorsementCode(endorsementCode, header)));
		assertEquals("W-ENDORSEMENT_CD Required", exception4.getMessage());

		endorsementCode.setEndorsementCd("D4");
		endorsementCode.setEndorseCdDesc(null);
		exception4 = assertThrows(NoRecordsFoundException.class,
				() -> when(endorsementCodeServiceImpl.updateEndorsementCode(endorsementCode, header)));
		assertEquals("W-DESC_DATA required", exception4.getMessage());

	}

	@Test
	void testRecordAlreadyExistsException() {
		when(endorsementCodeRepo.existsById(Mockito.any())).thenReturn(true);
		RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
				() -> when(endorsementCodeServiceImpl.addEndorsementCode(endorsementCode, header)));

		endorsementCode.setEndorsementCd("D3");
		when(endorsementCodeRepo.existsById(Mockito.any())).thenReturn(true);
		RecordAlreadyExistsException exception1 = assertThrows(RecordAlreadyExistsException.class,
				() -> when(endorsementCodeServiceImpl.updateEndorsementCode(endorsementCode, header)));
	}

	@Test
	void testDeleteEndorsementCode() {
		when(endorsementCodeRepo.existsById(Mockito.any())).thenReturn(true);
		endorsementCodeRepo.deleteById(Mockito.any());
		endorsementCodeServiceImpl.deleteEndorsementCode(endorsementCode);
	}
	
	@Test
	void testRecordNotDeletedException() {
		when(endorsementCodeRepo.existsById(Mockito.any())).thenReturn(false);
		RecordNotDeletedException exception = assertThrows(RecordNotDeletedException.class,
				() -> when(endorsementCodeServiceImpl.addEndorsementCode(endorsementCode, header)));
		assertEquals("Record Not Found!", exception.getMessage());
	}
}
