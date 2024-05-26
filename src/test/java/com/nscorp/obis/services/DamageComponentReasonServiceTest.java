package com.nscorp.obis.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import com.nscorp.obis.common.SpecificationGenerator;
import com.nscorp.obis.domain.CustomerInfo;
import com.nscorp.obis.domain.DamageComponentReason;
import com.nscorp.obis.dto.DamageComponentReasonDTO;
import com.nscorp.obis.dto.mapper.DamageComponentReasonMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.repository.AARWhyMadeCodesRepository;
import com.nscorp.obis.repository.DamageComponentReasonRepository;
import com.nscorp.obis.repository.DamageComponentRepository;

public class DamageComponentReasonServiceTest {

	@InjectMocks
	DamageComponentReasonServiceImpl service;

	@Mock
	DamageComponentReasonRepository repository;

	@Mock
	SpecificationGenerator specificationGenerator;

	@Mock
	DamageComponentReasonMapper mapper;

	@Mock
	private DamageComponentRepository damageComponentRepo;

	@Mock
	private AARWhyMadeCodesRepository aarwhyMadeCodeRepo;

	DamageComponentReasonDTO dto;

	List<DamageComponentReasonDTO> dtos;

	DamageComponentReason damageComponentReason;

	List<DamageComponentReason> damageComponentReasons;

	Integer jobCode = 1103;
	Integer aarWhyMadeCode = 11;
	String orderCode = "A";
	String sizeRequired = "Y";
	Map<String, String> headers;

	Specification<DamageComponentReason> specification;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		dto = new DamageComponentReasonDTO();
		damageComponentReason = new DamageComponentReason();
		damageComponentReasons = new ArrayList<>();
		dto.setOrderCode("A");
		damageComponentReason.setOrderCode("A");
		damageComponentReasons.add(damageComponentReason);
		dtos = new ArrayList<>();
		dtos.add(dto);
		specification = (Root<DamageComponentReason> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		};
		headers=new HashMap<>();
		headers.put("userid", "test");
		headers.put("extensionschema", "test");
	}

	@AfterEach
	void tearDown() {
		dto = null;
		dtos = null;
		headers = null;
	}

	@Test
	void testGetDamageComponentReasons() {
		when(specificationGenerator.damageComponentReasonSpecification(Mockito.eq(jobCode), Mockito.eq(aarWhyMadeCode),
				Mockito.eq(orderCode), Mockito.eq(sizeRequired))).thenReturn(specification);
		when(repository.findAll(specification)).thenReturn(damageComponentReasons);
		when(mapper.damageComponentReasonToDamageComponentReasonDTO(Mockito.any())).thenReturn(dto);
		List<DamageComponentReasonDTO> response = service.getDamageComponentReasons(jobCode, aarWhyMadeCode, orderCode,
				sizeRequired);
		assertEquals(dtos, response);
		damageComponentReasons = new ArrayList<>();
		when(repository.findAll(specification)).thenReturn(damageComponentReasons);
		assertThrows(NoRecordsFoundException.class,
				() -> service.getDamageComponentReasons(jobCode, aarWhyMadeCode, orderCode, sizeRequired));
	}

	@Test
	void testDeleteDamageComponentReason() {
		when(repository.findByJobCodeAndAarWhyMadeCode(Mockito.any(), Mockito.any())).thenReturn(damageComponentReason);
		doNothing().when(repository).delete(Mockito.any());
		when(mapper.damageComponentReasonToDamageComponentReasonDTO(Mockito.any())).thenReturn(dto);
		DamageComponentReasonDTO response = service.deleteDamageComponentReason(dto);
		assertEquals(dto, response);
		when(repository.findByJobCodeAndAarWhyMadeCode(Mockito.any(), Mockito.any())).thenReturn(null);
		assertThrows(NoRecordsFoundException.class, () -> service.deleteDamageComponentReason(dto));
		dto.setJobCode(1111111);
		assertThrows(InvalidDataException.class, () -> service.deleteDamageComponentReason(dto));
		dto.setJobCode(11111);
		dto.setAarWhyMadeCode(11111111);
		assertThrows(InvalidDataException.class, () -> service.deleteDamageComponentReason(dto));
	}

	@Test
	void testCreateDamageComponentReason() {
		when(damageComponentRepo.existsByJobCode(Mockito.any())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class, () -> service.createDamageComponentReason(dto, headers));
		when(damageComponentRepo.existsByJobCode(Mockito.any())).thenReturn(true);
		when(aarwhyMadeCodeRepo.existsByAarWhyMadeCd(Mockito.any())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class, () -> service.createDamageComponentReason(dto, headers));
		when(aarwhyMadeCodeRepo.existsByAarWhyMadeCd(Mockito.any())).thenReturn(true);
		when(repository.existsByJobCodeAndAarWhyMadeCode(Mockito.any(), Mockito.any())).thenReturn(true);
		assertThrows(RecordAlreadyExistsException.class, () -> service.createDamageComponentReason(dto, headers));
		when(repository.existsByJobCodeAndAarWhyMadeCode(Mockito.any(), Mockito.any())).thenReturn(false);
		when(repository.existsByJobCodeAndOrderCode(Mockito.any(), Mockito.any())).thenReturn(true);
		assertThrows(RecordAlreadyExistsException.class, () -> service.createDamageComponentReason(dto, headers));
		when(repository.existsByJobCodeAndOrderCode(Mockito.any(), Mockito.any())).thenReturn(false);
		when(mapper.damageComponentReasonDTOToDamageComponentReason(Mockito.any())).thenReturn(damageComponentReason);
		when(repository.save(Mockito.any())).thenReturn(damageComponentReason);
		when(mapper.damageComponentReasonToDamageComponentReasonDTO(Mockito.any())).thenReturn(dto);
		DamageComponentReasonDTO response = service.createDamageComponentReason(dto, headers);
		assertNotNull(response);
	}
	
	@Test
	void testUpdateDamageComponentReason() {
		when(repository.findByJobCodeAndAarWhyMadeCode(Mockito.any(), Mockito.any())).thenReturn(null);
		assertThrows(NoRecordsFoundException.class, () -> service.updateDamageComponentReason(dto, headers));
		when(repository.findByJobCodeAndAarWhyMadeCode(Mockito.any(), Mockito.any())).thenReturn(damageComponentReason);
		dto.setOrderCode("B");
		when(repository.existsByJobCodeAndOrderCode(Mockito.any(), Mockito.any())).thenReturn(true);
		assertThrows(RecordAlreadyExistsException.class, () -> service.updateDamageComponentReason(dto, headers));
		when(repository.existsByJobCodeAndOrderCode(Mockito.any(), Mockito.any())).thenReturn(false);
		when(repository.save(Mockito.any())).thenReturn(damageComponentReason);
		when(mapper.damageComponentReasonToDamageComponentReasonDTO(Mockito.any())).thenReturn(dto);
		DamageComponentReasonDTO response = service.updateDamageComponentReason(dto, headers);
		assertNotNull(response);
	}

}
