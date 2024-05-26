package com.nscorp.obis.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.obis.domain.UnCd;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.domain.AARWhyMadeCodes;
import com.nscorp.obis.dto.AARWhyMadeCodesDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.AARWhyMadeCodesRepository;

public class AARWhyMadeCodesServiceTest {

	@InjectMocks
	AARWhyMadeCodesServiceImpl aarWhyMadeCodesService;

	@Mock
	AARWhyMadeCodesRepository aarWhyMadeCodesRepo;
	AARWhyMadeCodes aarWhyMadeCodes;
	AARWhyMadeCodesDTO aarWhyMadeCodesDto;
	List<AARWhyMadeCodes> aarWhyMadeCodesList;
	List<AARWhyMadeCodesDTO> aarWhyMadeCodesDtoList;
	AARWhyMadeCodes addedTable;
	AARWhyMadeCodes tableUpdated;
	AARWhyMadeCodes aarWhyMadeCodesadded;
	Map<String, String> header;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		aarWhyMadeCodesList=new ArrayList<>();
		aarWhyMadeCodes = new AARWhyMadeCodes();
		aarWhyMadeCodes.setAarWhyMadeCd(12);
		aarWhyMadeCodes.setAarDesc("TEST");
		aarWhyMadeCodesList.add(aarWhyMadeCodes);
		aarWhyMadeCodesDtoList = new ArrayList<>();
		aarWhyMadeCodesDto = new AARWhyMadeCodesDTO();
		aarWhyMadeCodesDto.setAarWhyMadeCd(12);
		aarWhyMadeCodesDto.setAarDesc("TEST");
		aarWhyMadeCodesDtoList.add(aarWhyMadeCodesDto);
		
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		aarWhyMadeCodes = null;
		aarWhyMadeCodesDto = null;
		aarWhyMadeCodesDtoList = null;
		aarWhyMadeCodesList = null;
	}

	@Test
	void testAddAARWhycodes() {
		//aarWhyMadeCodes.setAarWhyMadeCd(122);
		when(aarWhyMadeCodesRepo.existsByAarWhyMadeCd(Mockito.any())).thenReturn(false);
		when(aarWhyMadeCodesRepo.save(Mockito.any())).thenReturn(aarWhyMadeCodes);
		AARWhyMadeCodes addWhyMadeCodes = aarWhyMadeCodesService.addAARWhyMadeCodes(aarWhyMadeCodes, header);
		assertNotNull(addWhyMadeCodes);

	}

	@Test
	void testAddAARWhycodesRecordAlreadyFoundException() {
		//aarWhyMadeCodes.setAarWhyMadeCd(122);
		when(aarWhyMadeCodesRepo.existsByAarWhyMadeCd(Mockito.any())).thenReturn(true);
		when(aarWhyMadeCodesRepo.save(Mockito.any())).thenReturn(aarWhyMadeCodes);

		RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
				() -> aarWhyMadeCodesService.addAARWhyMadeCodes(aarWhyMadeCodes, header));
		assertEquals("Record Already Exists", exception.getMessage());


	}
	
	@Test
	void testGetAllAARWhyMadeCodes() {
		when(aarWhyMadeCodesRepo.findAll()).thenReturn(aarWhyMadeCodesList);
		List<AARWhyMadeCodes> aarTypes = aarWhyMadeCodesService.getAARWhyMadeCodes();
		assertEquals(aarTypes,aarWhyMadeCodesList);
	}
	
	@Test
	void testGetAllAARWhyMadeCodesException(){
		when(aarWhyMadeCodesRepo.findAll()).thenReturn(null);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(aarWhyMadeCodesService.getAARWhyMadeCodes()));
		Assertions.assertEquals("No AAR Why Made Codes List Found!", exception.getMessage());

		when(aarWhyMadeCodesRepo.existsById(Mockito.any())).thenReturn(false);
		 exception = assertThrows(NoRecordsFoundException.class,
				() -> when(aarWhyMadeCodesService.updateAARWhyMadeCodes(aarWhyMadeCodes, header)));
		assertEquals(exception,exception);
	}
	
	@Test
	void testUpdateAARWhyMadeCodes() {
		when(aarWhyMadeCodesRepo.existsById(Mockito.any())).thenReturn(true);
		when(aarWhyMadeCodesRepo.findByAarWhyMadeCd(Mockito.any())).thenReturn(aarWhyMadeCodes);
		when(aarWhyMadeCodesRepo.save(Mockito.any())).thenReturn(aarWhyMadeCodes);
		AARWhyMadeCodes aarWhyCodeAdded = aarWhyMadeCodesService.updateAARWhyMadeCodes(aarWhyMadeCodes, header);
		assertEquals(aarWhyCodeAdded,aarWhyMadeCodes);
	}
	
	@Test
	void testDeleteAARWhyMadeCodes() {
		when(aarWhyMadeCodesRepo.existsById(Mockito.any())).thenReturn(true);
		aarWhyMadeCodesRepo.deleteById(Mockito.any());
		aarWhyMadeCodesService.deleteAARWhyMadeCodes(aarWhyMadeCodes);
	}
	
	@Test
	void testDeleteAARWhyMadeCodesException() {
		when(aarWhyMadeCodesRepo.existsById(Mockito.any())).thenReturn(false);
		RecordNotDeletedException exception = assertThrows(RecordNotDeletedException.class,
				() -> aarWhyMadeCodesService.deleteAARWhyMadeCodes(aarWhyMadeCodes));
		assertEquals("Record Not Found!", exception.getMessage());
	}


}


