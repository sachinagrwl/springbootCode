package com.nscorp.obis.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
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

import com.nscorp.obis.domain.Station;
import com.nscorp.obis.domain.StationTermHandle;
import com.nscorp.obis.dto.StationTermHandleDTO;
import com.nscorp.obis.dto.mapper.StationTermHandleMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.repository.StationRepository;
import com.nscorp.obis.repository.StationTermHandleRepository;
import com.nscorp.obis.repository.TerminalRepository;

public class StationTermHandleServiceTest {

	@Mock
	StationTermHandleRepository handleRepository;

	@Mock
	StationRepository repository;

	@Mock
	StationTermHandleMapper mapper;

	@Mock
	TerminalRepository terminalRepo;

	@InjectMocks
	StationTermHandleServiceImpl handleServiceImpl;

	StationTermHandle stationTermHandle;
	List<StationTermHandle> stationTermHandles;
	StationTermHandleDTO stationTermhandleDTO;
	List<StationTermHandleDTO> stationTermHandleDTOS;
	Long terminalId = 219974351921L;
	Map<String, String> headers;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		stationTermHandle = new StationTermHandle();
		stationTermhandleDTO = new StationTermHandleDTO();
		stationTermHandles = new ArrayList<>();
		stationTermHandleDTOS = new ArrayList<>();
		stationTermHandleDTOS.add(stationTermhandleDTO);
		stationTermHandles.add(stationTermHandle);
		headers = new HashMap<>();
		headers.put("userid", "test");

	}

	@AfterEach
	void tearDown() {
		stationTermhandleDTO = null;
		stationTermHandleDTOS = null;
	}

	@Test
	void testGetTermHandleDetails() {
		when(terminalRepo.existsByTerminalId(Mockito.anyLong())).thenReturn(true);
		when(handleRepository.findByHandleTermId(Mockito.anyLong())).thenReturn(stationTermHandles);
		when(mapper.stationTermHandleToStationTermHandleDTO(Mockito.any())).thenReturn(stationTermhandleDTO);
		List<StationTermHandleDTO> response = handleServiceImpl.getTermHandleDetails(terminalId);
		assertEquals(1, response.size());
		when(terminalRepo.existsByTerminalId(Mockito.anyLong())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class, () -> handleServiceImpl.getTermHandleDetails(terminalId));
		when(terminalRepo.existsByTerminalId(Mockito.anyLong())).thenReturn(true);
		when(handleRepository.findByHandleTermId(Mockito.anyLong())).thenReturn(new ArrayList<>());
		assertThrows(NoRecordsFoundException.class, () -> handleServiceImpl.getTermHandleDetails(terminalId));
	}

	@Test
	void testDeleteStationTermHandle() {
		stationTermhandleDTO.setHandleTermId(1234L);
		stationTermhandleDTO.setStationId(1234L);
		when(handleRepository.existsByHandleTermIdAndStationId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(true);
		when(handleRepository.findByHandleTermIdAndStationId(Mockito.anyLong(), Mockito.anyLong()))
				.thenReturn(stationTermHandle);
		doNothing().when(handleRepository).delete(Mockito.any());
		handleServiceImpl.deleteStationTermHandle(stationTermhandleDTO);
		when(handleRepository.existsByHandleTermIdAndStationId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class, () -> handleServiceImpl.deleteStationTermHandle(stationTermhandleDTO));
	}
	
	@Test
	void testInsertStationTermHandle() {
		stationTermhandleDTO.setHandleTermId(1234L);
		stationTermhandleDTO.setStationId(1234L);
		when(terminalRepo.existsByTerminalId(Mockito.anyLong())).thenReturn(true);
		when(repository.findByTermId(Mockito.anyLong())).thenReturn(new Station());
		when(handleRepository.existsByHandleTermIdAndStationId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(false);
		when(handleRepository.save(Mockito.any())).thenReturn(stationTermHandle);
		when(mapper.stationTermHandleDTOToStationTermHandle(Mockito.any())).thenReturn(stationTermHandle);
		when(mapper.stationTermHandleToStationTermHandleDTO(Mockito.any())).thenReturn(stationTermhandleDTO);
		assertNotNull(handleServiceImpl.insertStationTermHandle(stationTermhandleDTO, headers));
		when(terminalRepo.existsByTerminalId(Mockito.anyLong())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class, () -> handleServiceImpl.insertStationTermHandle(stationTermhandleDTO, headers));
		when(terminalRepo.existsByTerminalId(Mockito.anyLong())).thenReturn(true);
		when(repository.findByTermId(Mockito.anyLong())).thenReturn(null);
		assertThrows(NoRecordsFoundException.class, () -> handleServiceImpl.insertStationTermHandle(stationTermhandleDTO, headers));
		when(handleRepository.existsByHandleTermIdAndStationId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(true);
		when(repository.findByTermId(Mockito.anyLong())).thenReturn(new Station());
		assertThrows(RecordAlreadyExistsException.class, () -> handleServiceImpl.insertStationTermHandle(stationTermhandleDTO, headers));
	}
	
	
	

}
