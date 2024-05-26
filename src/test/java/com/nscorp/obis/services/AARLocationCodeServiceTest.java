package com.nscorp.obis.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.obis.domain.DamageComponent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.domain.AARLocationCode;
import com.nscorp.obis.domain.AARType;
import com.nscorp.obis.dto.AARLocationCodeDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.repository.AARLocationCodeRepository;

public class AARLocationCodeServiceTest {

	@InjectMocks
	AARLocationCodeServiceImpl aarLocationCodeService;

	@Mock
	AARLocationCodeRepository aarLocationCodeRepo;

	AARLocationCode aarLocationCode;
	AARLocationCodeDTO aarLocationCodeDTO;
	List<AARLocationCode> aarLocationCodeList;
	List<AARLocationCodeDTO> aarLocationCodeDTOList;
	AARType addedTable;
	AARType tableUpdated;
	AARLocationCode aarLocationCodeAdded;
	Map<String, String> header;

	List<String> type1;
	List<String> description1;
	List<Integer> capacity1;
	List<AARType> aarTypes;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		aarLocationCode = new AARLocationCode();
		aarLocationCode.setLocCd("QWE");
		aarLocationCode.setLocDesc("Hello");
		aarLocationCodeList = new ArrayList<>();
		aarLocationCodeList.add(aarLocationCode);
		aarLocationCodeDTO = new AARLocationCodeDTO();
		aarLocationCodeDTO.setLocCd("QWE");
		aarLocationCodeDTO.setLocDesc("HELLO");
		aarLocationCodeDTOList = new ArrayList<>();
		aarLocationCodeDTOList.add(aarLocationCodeDTO);

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		aarLocationCode = null;
		aarLocationCodeDTO = null;
		aarLocationCodeDTOList = null;
		aarLocationCodeList = null;
	}

	@Test
	public void testGetAllAARLocationCodes() throws Exception {

		//AARLocationCode aARLocationCodeList = new ArrayList<>();
		//aARLocationCodeList.add(new AARLocationCode());
		Mockito.when(aarLocationCodeRepo.findAll()).thenReturn(aarLocationCodeList);
		List<AARLocationCode> result = aarLocationCodeService.getAllAARLocationCodes();
		assertNotNull(result);

	}

	@Test
	public void testGetByAARLocationCodes() throws Exception {

		//AARLocationCode aARLocationCodeList = new ArrayList<>();
		//aARLocationCodeList.add(new AARLocationCode());
		Mockito.when(aarLocationCodeRepo.findByLocCd(Mockito.anyString())).thenReturn(aarLocationCode);
		AARLocationCode result = aarLocationCodeService.getAARLocationCodesByLocCode("C");
		assertNotNull(result);

	}

	@Test
	public void testGetAARLocationCodesWhenNoRecordsFoundExceptionIsThrown() {

		Mockito.when(aarLocationCodeRepo.findByLocCd(Mockito.anyString())).thenReturn(null);

		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class, () -> {
			aarLocationCodeService.getAARLocationCodesByLocCode("V");
		});

		assertEquals("No AAR Code List Found!", exception.getMessage());
		verify(aarLocationCodeRepo).findByLocCd("V");
	}


	@Test
	void testAddAARLocationCode() {
		when(aarLocationCodeRepo.existsByLocCd(Mockito.any())).thenReturn(false);
		when(aarLocationCodeRepo.save(Mockito.any())).thenReturn(aarLocationCode);
		aarLocationCodeAdded = aarLocationCodeService.addAARLocationCode(aarLocationCode, header);
		assertEquals(aarLocationCodeAdded, aarLocationCode);
	}

	@Test
	void testNoRecordsFoundException() {
		when(aarLocationCodeRepo.existsByLocCd(Mockito.any())).thenReturn(true);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(aarLocationCodeService.addAARLocationCode(aarLocationCode, header)));
	}

	@Test
	void testUpdateAARLocationCode() {
		when(aarLocationCodeRepo.existsByLocCd(Mockito.any())).thenReturn(true);
		when(aarLocationCodeRepo.findByLocCd(Mockito.any())).thenReturn(aarLocationCode);
		when(aarLocationCodeRepo.save(Mockito.any())).thenReturn(aarLocationCode);
		AARLocationCode aarLocationCodeUpdate = aarLocationCodeService.updateAARLocationCode(aarLocationCode, header);
		assertEquals(aarLocationCodeUpdate, aarLocationCode);
	}

	@Test
	void testNoRecordFoundException() {
		when(aarLocationCodeRepo.existsByLocCd(Mockito.any())).thenReturn(false);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(aarLocationCodeService.updateAARLocationCode(aarLocationCode, header)));
		assertEquals("Record with AARLocation Code " + aarLocationCode.getLocCd() + " Not Found!", exception.getMessage());
	}

}
