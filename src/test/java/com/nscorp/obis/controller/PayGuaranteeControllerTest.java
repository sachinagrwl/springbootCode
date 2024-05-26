package com.nscorp.obis.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.PayGuarantee;
import com.nscorp.obis.dto.PayGuaranteeDTO;
import com.nscorp.obis.dto.mapper.PayGuaranteeMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.PayGuaranteeServiceImpl;

public class PayGuaranteeControllerTest {

	@Mock
	PayGuaranteeServiceImpl payGuaranteeService;

	@Mock
	PayGuaranteeMapper payGuaranteeMapper;

	@InjectMocks
	PayGuaranteeController payGuaranteeController;

	PayGuarantee payGuarantee;
	PayGuaranteeDTO payGuaranteeDTO;

	Map<String, String> headers;

	Long chrgId;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		payGuarantee = new PayGuarantee();
		payGuaranteeDTO = new PayGuaranteeDTO();
		payGuarantee.setChrgId(1234L);
		headers = new HashMap<>();
		headers.put("userid", "test");
		headers.put("extensionschema", "test");

		chrgId = 1234L;
	}

	@AfterEach
	void tearDown() throws Exception {
		payGuarantee = null;
		payGuaranteeDTO = null;
	}

	@Test
	void testGetCustomerLocalInfo() throws SQLException {
		when(payGuaranteeService.getPayGuarantee(Mockito.any())).thenReturn(payGuarantee);
		when(payGuaranteeMapper.payGuaranteeDTOToPayGuarantee(Mockito.any())).thenReturn(payGuarantee);
		when(payGuaranteeMapper.payGuaranteeToPayGuaranteeDTO(Mockito.any())).thenReturn(payGuaranteeDTO);
		ResponseEntity<APIResponse<PayGuaranteeDTO>> getData = payGuaranteeController.getAllTables(chrgId);
		assertEquals(getData.getStatusCodeValue(), 200);
	}
 
	@Test
	@DisplayName("NoRecordsFoundException")
	void testNoRecordsFoundException() throws SQLException {
		when(payGuaranteeService.getPayGuarantee(Mockito.any())).thenThrow(NoRecordsFoundException.class);
		ResponseEntity<APIResponse<PayGuaranteeDTO>> getData = payGuaranteeController.getAllTables(chrgId);
		assertEquals(getData.getStatusCodeValue(), 404);

	}
	
	@Test
	@DisplayName("Exception")
	void testException() throws SQLException {
		when(payGuaranteeService.getPayGuarantee(Mockito.any())).thenThrow(RuntimeException.class);
		ResponseEntity<APIResponse<PayGuaranteeDTO>> getData = payGuaranteeController.getAllTables(chrgId);
		assertEquals(getData.getStatusCodeValue(), 500);

	}

	@Test
	void testAddPayGuarantee() {
		when(payGuaranteeService.addPayGuarantee(Mockito.any(PayGuarantee.class), Mockito.any()))
				.thenReturn(payGuarantee);
		ResponseEntity<APIResponse<PayGuaranteeDTO>> payGuarantee = payGuaranteeController
				.addPayGuarantee(payGuaranteeDTO, headers);
		assertNotNull(payGuarantee);
	}

	@Test
	void testAddPayGuaranteeException() {
		when(payGuaranteeService.addPayGuarantee(Mockito.any(), Mockito.any()))
				.thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<PayGuaranteeDTO>> payGuarantee = payGuaranteeController
				.addPayGuarantee(payGuaranteeDTO,headers);
		assertEquals(payGuarantee.getStatusCodeValue(), 500);
	}

	@Test
	void testAddPayGuaranteeNoRecordsFoundException() {
		when(payGuaranteeService.addPayGuarantee(Mockito.any(), Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<PayGuaranteeDTO>> payGuarantee = payGuaranteeController
				.addPayGuarantee(payGuaranteeDTO,headers);
		assertEquals(payGuarantee.getStatusCodeValue(), 404);
	}

	@Test
	void testAddPayGuaranteeRecordAlreadyExistsException() {
		when(payGuaranteeService.addPayGuarantee(Mockito.any(), Mockito.any()))
				.thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<PayGuaranteeDTO>> payGuarantee = payGuaranteeController
				.addPayGuarantee(payGuaranteeDTO,headers);
		assertEquals(payGuarantee.getStatusCodeValue(), 208);
	}

	@Test
	void testUpdatePayGuarantee() {
		when(payGuaranteeService.updatePayGuarantee(Mockito.any(PayGuarantee.class), Mockito.any()))
				.thenReturn(payGuarantee);
		ResponseEntity<APIResponse<PayGuaranteeDTO>> payGuarantee = payGuaranteeController
				.updatePayGuarantee(payGuaranteeDTO, headers);
		assertNotNull(payGuarantee);
	}

	@Test
	void testUpdatePayGuaranteeException() {
		when(payGuaranteeService.updatePayGuarantee(Mockito.any(), Mockito.any()))
				.thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<PayGuaranteeDTO>> payGuarantee = payGuaranteeController
				.updatePayGuarantee(payGuaranteeDTO,headers);
		assertEquals(payGuarantee.getStatusCodeValue(), 500);
	}

	@Test
	void testUpdatePayGuaranteeNoRecordsFoundException() {
		when(payGuaranteeService.updatePayGuarantee(Mockito.any(), Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<PayGuaranteeDTO>> payGuarantee = payGuaranteeController
				.updatePayGuarantee(payGuaranteeDTO,headers);
		assertEquals(payGuarantee.getStatusCodeValue(), 404);
	}

	@Test
	void testUpdatePayGuaranteeRecordAlreadyExistsException() {
		when(payGuaranteeService.updatePayGuarantee(Mockito.any(), Mockito.any()))
				.thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<PayGuaranteeDTO>> payGuarantee = payGuaranteeController
				.updatePayGuarantee(payGuaranteeDTO,headers);
		assertEquals(payGuarantee.getStatusCodeValue(), 208);
	}

}
