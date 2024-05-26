package com.nscorp.obis.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.nscorp.obis.common.SpecificationGenerator;
import com.nscorp.obis.domain.CustomerInfo;
import com.nscorp.obis.domain.Train;
import com.nscorp.obis.dto.TrainDTO;
import com.nscorp.obis.dto.mapper.TrainMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.TerminalTrainRepository;
import com.nscorp.obis.repository.TrainRepository;
import com.nscorp.obis.repository.TrainScheduleRepository;

public class TrainServiceTest {
	
	@Mock
	TrainRepository trainRepo;
	
	@Mock
	TerminalTrainRepository terminalTrainRepo;

	@Mock
	TrainScheduleRepository trainScheduleRepo;

	@Mock
	SpecificationGenerator specificationGenerator;

	@Mock
	TrainMapper mapper;
	
	@InjectMocks
	TrainServiceImpl trainServiceImpl;
	
	TrainDTO trainDTO;
	
	Specification<Train> specification;

	List<TrainDTO> trainDTOs;
	
	Train train;
	List<Train> trains;

	Map<String, String> headers;

	String trainNumber = "123";
	String trainDesc = "test";
	
	Sort sort=Sort.by("trainNumber");

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		trainDTO = new TrainDTO();
		train=new Train();
		trainDTO.setTrainNumber(trainNumber);
		train.setTrainNumber(trainNumber);
		trainDTOs = new ArrayList<>();
		trains=new ArrayList<>();
		trains.add(train);
		trainDTOs.add(trainDTO);
		headers = new HashMap<>();
		headers.put("userid", "testApi");
		specification=(Root<Train> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		};
	}

	@AfterEach
	void tearDown() {
		trainDTO = null;
		trainDTOs = null;
		headers = null;
		specification=null;
	}
	
	@Test
	void testFetchTrainDetails() {
		when(specificationGenerator.trainSpecification(Mockito.anyString(), Mockito.anyString())).thenReturn(specification);
		when(trainRepo.findAll(Mockito.eq(specification),Mockito.eq(sort))).thenReturn(trains);
		when(mapper.trainToTrainDTO(Mockito.any())).thenReturn(trainDTO);
		List<TrainDTO> response=trainServiceImpl.fetchTrainDetails(trainNumber, trainDesc);
		assertNotNull(response);
		when(trainRepo.findAll(Mockito.eq(specification),Mockito.eq(sort))).thenReturn(new ArrayList<>());
		assertThrows(NoRecordsFoundException.class,()->trainServiceImpl.fetchTrainDetails(trainNumber, trainDesc));
	}
	
	@Test
	void testAddTrain() {
		when(trainRepo.existsById(Mockito.anyString())).thenReturn(false);
		when(mapper.trainToTrainDTO(Mockito.any())).thenReturn(trainDTO);
		when(mapper.trainDTOToTrain(Mockito.any())).thenReturn(train);
		when(trainRepo.save(Mockito.any())).thenReturn(train);
		TrainDTO response=trainServiceImpl.addTrain(trainDTO, headers);
		assertNotNull(response);
		when(trainRepo.existsById(Mockito.anyString())).thenReturn(true);
		assertThrows(RecordAlreadyExistsException.class, ()->trainServiceImpl.addTrain(response, headers));
	}
	
	@Test
	void testUpdateTrain() {
		when(trainRepo.existsById(Mockito.anyString())).thenReturn(true);
		when(trainRepo.findById(Mockito.anyString())).thenReturn(Optional.of(train));
		when(mapper.trainToTrainDTO(Mockito.any())).thenReturn(trainDTO);
		when(mapper.trainDTOToTrain(Mockito.any())).thenReturn(train);
		when(trainRepo.save(Mockito.any())).thenReturn(train);
		TrainDTO response=trainServiceImpl.updateTrain(trainDTO, headers);
		assertNotNull(response);
		when(trainRepo.existsById(Mockito.anyString())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class, ()->trainServiceImpl.updateTrain(response, headers));
	}
	
	@Test
	void testDeleteTrain() {
		when(trainRepo.findById(Mockito.anyString())).thenReturn(Optional.of(train));
		when(terminalTrainRepo.existsByTrainNr(Mockito.anyString())).thenReturn(false);
		when(trainScheduleRepo.existsByTrainNumber(Mockito.anyString())).thenReturn(false);
		when(mapper.trainToTrainDTO(Mockito.any())).thenReturn(trainDTO);
		doNothing().when(trainRepo).delete(Mockito.any());
		TrainDTO response=trainServiceImpl.deleteTrain(trainDTO);
		assertNotNull(response);
		when(trainRepo.findById(Mockito.anyString())).thenReturn(Optional.empty());
		assertThrows(NoRecordsFoundException.class, ()->trainServiceImpl.deleteTrain(trainDTO));
		when(terminalTrainRepo.existsByTrainNr(Mockito.anyString())).thenReturn(true);
		when(trainScheduleRepo.existsByTrainNumber(Mockito.anyString())).thenReturn(true);
		when(trainRepo.findById(Mockito.anyString())).thenReturn(Optional.of(train));
		assertThrows(RecordNotDeletedException.class, ()->trainServiceImpl.deleteTrain(trainDTO));
		
	}

}
