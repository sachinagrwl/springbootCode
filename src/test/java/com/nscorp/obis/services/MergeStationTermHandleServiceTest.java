package com.nscorp.obis.services;

import com.nscorp.obis.domain.MergeStationTermHandle;
import com.nscorp.obis.domain.Station;
import com.nscorp.obis.dto.MergeStationTermHandleDTO;
import com.nscorp.obis.dto.mapper.MergeStationTermHandleMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.repository.MergeStationTermHandleRepository;
import com.nscorp.obis.repository.StationRepository;
import com.nscorp.obis.repository.TerminalRepository;
import org.junit.jupiter.api.AfterEach;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class MergeStationTermHandleServiceTest {

	@Mock
	MergeStationTermHandleRepository handleRepository;

	@Mock
	StationRepository repository;

	@Mock
	MergeStationTermHandleMapper mapper;

	@Mock
	TerminalRepository terminalRepo;

	@InjectMocks
	MergeStationTermHandleServiceImpl handleServiceImpl;

	MergeStationTermHandle mergeStationTermHandle;
	List<MergeStationTermHandle> mergeStationTermHandles;
	MergeStationTermHandleDTO mergeStationTermHandleDTO;
	List<MergeStationTermHandleDTO> mergeStationTermHandleDTOS;
	Long terminalId = 219974351921L;
	Map<String, String> headers;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mergeStationTermHandle = new MergeStationTermHandle();
		mergeStationTermHandleDTO = new MergeStationTermHandleDTO();
		mergeStationTermHandles = new ArrayList<>();
		mergeStationTermHandleDTOS = new ArrayList<>();
		mergeStationTermHandleDTOS.add(mergeStationTermHandleDTO);
		mergeStationTermHandles.add(mergeStationTermHandle);
		headers = new HashMap<>();
		headers.put("userid", "test");

	}

	@AfterEach
	void tearDown() {
		mergeStationTermHandleDTO = null;
		mergeStationTermHandleDTOS = null;
	}

	@Test
	void testGetTermHandleDetails() {
		when(terminalRepo.existsByTerminalId(Mockito.anyLong())).thenReturn(true);
		when(handleRepository.findByHandleTermId(Mockito.anyLong())).thenReturn(mergeStationTermHandles);
		when(mapper.mergeStationTermHandleToMergeStationTermHandleDTO(Mockito.any())).thenReturn(mergeStationTermHandleDTO);
		List<MergeStationTermHandleDTO> response = handleServiceImpl.getMergeStationTermHandleDetails(terminalId);
		assertEquals(1, response.size());
		when(terminalRepo.existsByTerminalId(Mockito.anyLong())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class, () -> handleServiceImpl.getMergeStationTermHandleDetails(terminalId));
		when(terminalRepo.existsByTerminalId(Mockito.anyLong())).thenReturn(true);
		when(handleRepository.findByHandleTermId(Mockito.anyLong())).thenReturn(new ArrayList<>());
		assertThrows(NoRecordsFoundException.class, () -> handleServiceImpl.getMergeStationTermHandleDetails(terminalId));
	}

	@Test
	void testDeleteStationTermHandle() {
		mergeStationTermHandleDTO.setHandleTermId(1234L);
		mergeStationTermHandleDTO.setStationId(1234L);
		when(handleRepository.existsByHandleTermIdAndStationId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(true);
		when(handleRepository.findByHandleTermIdAndStationId(Mockito.anyLong(), Mockito.anyLong()))
				.thenReturn(mergeStationTermHandle);
		doNothing().when(handleRepository).delete(Mockito.any());
		handleServiceImpl.deleteMergeStationTermHandle(mergeStationTermHandleDTO);
		when(handleRepository.existsByHandleTermIdAndStationId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class, () -> handleServiceImpl.deleteMergeStationTermHandle(mergeStationTermHandleDTO));
	}
	
	@Test
	void testInsertStationTermHandle() {
		mergeStationTermHandleDTO.setHandleTermId(1234L);
		mergeStationTermHandleDTO.setStationId(1234L);
		when(terminalRepo.existsByTerminalId(Mockito.anyLong())).thenReturn(true);
		when(repository.findByTermId(Mockito.anyLong())).thenReturn(new Station());
		when(handleRepository.existsByHandleTermIdAndStationId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(false);
		when(handleRepository.save(Mockito.any())).thenReturn(mergeStationTermHandle);
		when(mapper.mergeStationTermHandleDTOToMergeStationTermHandle(Mockito.any())).thenReturn(mergeStationTermHandle);
		when(mapper.mergeStationTermHandleToMergeStationTermHandleDTO(Mockito.any())).thenReturn(mergeStationTermHandleDTO);
		assertNotNull(handleServiceImpl.insertMergeStationTermHandle(mergeStationTermHandleDTO, headers));
		when(terminalRepo.existsByTerminalId(Mockito.anyLong())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class, () -> handleServiceImpl.insertMergeStationTermHandle(mergeStationTermHandleDTO, headers));
		when(terminalRepo.existsByTerminalId(Mockito.anyLong())).thenReturn(true);
		when(repository.findByTermId(Mockito.anyLong())).thenReturn(null);
		assertThrows(NoRecordsFoundException.class, () -> handleServiceImpl.insertMergeStationTermHandle(mergeStationTermHandleDTO, headers));
		when(handleRepository.existsByHandleTermIdAndStationId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(true);
		when(repository.findByTermId(Mockito.anyLong())).thenReturn(new Station());
		assertThrows(RecordAlreadyExistsException.class, () -> handleServiceImpl.insertMergeStationTermHandle(mergeStationTermHandleDTO, headers));
	}
	
	
	

}
