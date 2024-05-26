package com.nscorp.obis.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Array;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.obis.domain.DamageReason;
import com.nscorp.obis.dto.BeneficialOwnerDTO;
import com.nscorp.obis.dto.mapper.DamageReasonMapper;
import com.nscorp.obis.exception.InvalidDataException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.dto.DamageReasonDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.DamageReasonService;

public class DamageReasonControllerTest {

	@Mock
	DamageReasonService damageReasonService;
	@Mock
	DamageReasonMapper mapper;

	@InjectMocks
	DamageReasonController damageReasonController;

	DamageReasonDTO damageReasonDTO;
	List<DamageReasonDTO> damageReasonDTOList;
	DamageReason damageReason;
	List<DamageReason> damageReasonList;
	Integer catCd;

	Map<String, String> headers;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		catCd=3;
		damageReasonDTO = new DamageReasonDTO();
		headers = new HashMap<>();
		damageReasonDTOList= Arrays.asList(damageReasonDTO);
		damageReason=new DamageReason();
	}

	@AfterEach
	void tearDown() {
		damageReasonDTO = null;
		headers = null;

	}

	@Test
	void testAddDamageReason() {
		when(damageReasonService.addDamageReason(Mockito.any(), Mockito.any())).thenReturn(damageReasonDTO);
		ResponseEntity<APIResponse<DamageReasonDTO>> response = damageReasonController.addDamageReason(damageReasonDTO,
				headers);
		assertEquals(200, response.getStatusCodeValue());
	}

	@Test
	void testUpdateDamageReason() {
		when(damageReasonService.updateDamageReason(Mockito.any(), Mockito.any())).thenReturn(damageReasonDTO);
		ResponseEntity<APIResponse<DamageReasonDTO>> response = damageReasonController
				.updateDamageReason(damageReasonDTO, headers);
		assertEquals(200, response.getStatusCodeValue());
	}

	@Test
	void testNoRecordsException() {
//		when(damageReasonService.addDamageReason(Mockito.any(), Mockito.any()))
//				.thenThrow(NoRecordsFoundException.class);
//		ResponseEntity<APIResponse<DamageReasonDTO>> response = damageReasonController.addDamageReason(damageReasonDTO,
//				headers);
//		assertEquals(404, response.getStatusCodeValue());
//
//		when(damageReasonService.updateDamageReason(Mockito.any(), Mockito.any()))
//				.thenThrow(NoRecordsFoundException.class);
//		ResponseEntity<APIResponse<DamageReasonDTO>> response2 = damageReasonController
//				.updateDamageReason(damageReasonDTO, headers);
//		assertEquals(404, response2.getStatusCodeValue());

		when(damageReasonService.deleteDamageReasons(Mockito.any()))
				.thenThrow(NoRecordsFoundException.class);
		ResponseEntity<List<APIResponse<DamageReasonDTO>>> response3 = damageReasonController
				.deleteDamageReason(damageReasonDTOList);
		assertEquals(404, response3.getStatusCodeValue());
		//500 error
		when(damageReasonService.getDamageReason(Mockito.any()))
				.thenThrow(NoRecordsFoundException.class);
		ResponseEntity<APIResponse<List<DamageReason>>> response4 = damageReasonController
				.getDamageReason(catCd);
		assertEquals(404, response4.getStatusCodeValue());
	}

	@Test
	void testRecordAlreadyException() {
		when(damageReasonService.addDamageReason(Mockito.any(), Mockito.any()))
				.thenThrow(RecordAlreadyExistsException.class);
		ResponseEntity<APIResponse<DamageReasonDTO>> response = damageReasonController.addDamageReason(damageReasonDTO,
				headers);
		assertEquals(400, response.getStatusCodeValue());

		when(damageReasonService.updateDamageReason(Mockito.any(), Mockito.any()))
				.thenThrow(RecordAlreadyExistsException.class);
		ResponseEntity<APIResponse<DamageReasonDTO>> response2 = damageReasonController
				.updateDamageReason(damageReasonDTO, headers);
		assertEquals(400, response2.getStatusCodeValue());

	}
	@Test
	void testNoRecordsFoundException() throws SQLException {

		when(damageReasonService.getDamageReason(Mockito.any())).thenThrow(NoRecordsFoundException.class);
		ResponseEntity<APIResponse<List<DamageReason>>> getData = damageReasonController.getDamageReason(catCd);
		assertEquals(getData.getStatusCodeValue(), 404);
		//500
	}

	@Test
	void testException() {
		when(damageReasonService.addDamageReason(Mockito.any(), Mockito.any())).thenThrow(RuntimeException.class);
		ResponseEntity<APIResponse<DamageReasonDTO>> response = damageReasonController.addDamageReason(damageReasonDTO,
				headers);
		assertEquals(500, response.getStatusCodeValue());
		when(damageReasonService.updateDamageReason(Mockito.any(), Mockito.any())).thenThrow(RuntimeException.class);
		ResponseEntity<APIResponse<DamageReasonDTO>> response2 = damageReasonController
				.updateDamageReason(damageReasonDTO, headers);
		assertEquals(500, response2.getStatusCodeValue());
		when(damageReasonService.deleteDamageReasons(Mockito.any())).thenThrow(RuntimeException.class);
		ResponseEntity<List<APIResponse<DamageReasonDTO>>> response3 = damageReasonController
				.deleteDamageReason(damageReasonDTOList);
		assertEquals(500, response3.getStatusCodeValue());
		////works

		when(damageReasonService.getDamageReason(Mockito.any())).thenThrow(RuntimeException.class);
		ResponseEntity<APIResponse<List<DamageReason>>> response4 = damageReasonController
				.getDamageReason(catCd);
		assertEquals(500, response4.getStatusCodeValue());




	}
	@Test
	void testDeleteDamageReason() {

		when(mapper.damageReasonDTOToDamageReason(Mockito.any())).thenReturn(damageReason);
		when(damageReasonService.deleteDamageReasons(Mockito.any())).thenReturn(damageReason);
		when(mapper.damageReasonToDamageReasonDTO(Mockito.any())).thenReturn(damageReasonDTO);
		ResponseEntity<List<APIResponse<DamageReasonDTO>>> deleteData = damageReasonController.deleteDamageReason(damageReasonDTOList);
		assertEquals(deleteData.getStatusCodeValue(), 200);
		//500 error
		//works
	}
	@Test
	void testGetDamageReasons() throws SQLException{
		when(damageReasonService.getDamageReason(Mockito.any())).thenReturn(damageReasonList);
		ResponseEntity<APIResponse<List<DamageReason>>> getData = damageReasonController.getDamageReason(catCd);
		assertEquals(getData.getStatusCodeValue(), 200);
		//works
	}
}
