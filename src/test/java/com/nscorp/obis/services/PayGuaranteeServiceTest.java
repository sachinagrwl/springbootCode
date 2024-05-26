package com.nscorp.obis.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.domain.CustomerIndex;
import com.nscorp.obis.domain.PayGuarantee;
import com.nscorp.obis.dto.PayGuaranteeDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.CustomerIndexRepository;
import com.nscorp.obis.repository.PayGuaranteeRepository;

public class PayGuaranteeServiceTest {

	
	@Mock
	PayGuaranteeRepository payGuaranteeRepo;
	
	@Mock
	CustomerIndexRepository customerIndexRepo;
	
	@InjectMocks
	PayGuaranteeServiceImpl payGuaranteeService;
	
	CustomerIndex customerIndex;
	PayGuarantee payGuarantee;
	PayGuaranteeDTO payGuaranteeDTO;


	Map<String, String> headers;
	Long chrgId;
	Long custId;


	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		payGuarantee = new PayGuarantee();
		customerIndex = new CustomerIndex();
		payGuaranteeDTO = new PayGuaranteeDTO();
		chrgId = 619919702687L;
		custId = 125135L;
		payGuarantee.setPayGuarId(1L);
		payGuarantee.setChrgId(1234L);
		payGuarantee.setGuarCustId(134L);
		customerIndex.setCustomerId(1234L);
		customerIndex.setCustomerName("ABCD");
		customerIndex.setCustomerNumber("12345");
		
		headers = new HashMap<>();
		headers.put("userid", "test");
		headers.put("extensionschema", "test");
		
	}

	@AfterEach
	void tearDown() throws Exception {
		payGuarantee = null;
		payGuaranteeDTO = null;
		customerIndex = null;

	}

	@Test
	void testFetchCustomerLocalInfo() throws SQLException {
		when(payGuaranteeRepo.existsByChrgId(Mockito.any())).thenReturn(true);
		when(payGuaranteeRepo.findByChrgId(Mockito.any())).thenReturn(payGuarantee);
		when(customerIndexRepo.findByCustomerId(custId)).thenReturn(customerIndex);
		PayGuarantee equipCar = payGuaranteeService.getPayGuarantee(chrgId);
		assertEquals(equipCar, payGuarantee);
	}
	
	@Test
	void testEquipmentCarNoRecordsFoundException() {
		when(payGuaranteeRepo.existsByChrgId(Mockito.any())).thenReturn(false);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(payGuaranteeService.getPayGuarantee(chrgId)));
		assertEquals("No Payment Guarantee found for this Equipment/Shipment Id combination", exception.getMessage());
	}

	
	@Test
	void testAddPayGuarantee() {
		payGuarantee.setCustomer(customerIndex);
		when(customerIndexRepo.findByCustomerId(Mockito.any())).thenReturn(customerIndex);
		when(payGuaranteeRepo.existsByGuarCustId(Mockito.any())).thenReturn(false);
		when(payGuaranteeRepo.save(Mockito.any())).thenReturn(payGuarantee);
		PayGuarantee payAdded = payGuaranteeService.addPayGuarantee(payGuarantee,headers);
		assertEquals(payAdded, payGuarantee);
	}
	
	@Test
	void testAddPayGuaranteeRecordNotAddedException() {
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(payGuaranteeService.addPayGuarantee(payGuarantee,headers)));
		assertEquals("You must select the customer by detailing on Name or Number", exception.getMessage());
	}

	@Test
	void testAddPayGuaranteeRecordAlreadyExistsException() {
		payGuarantee.setCustomer(customerIndex);
		when(customerIndexRepo.findByCustomerId(Mockito.any())).thenReturn(customerIndex);
		when(payGuaranteeRepo.existsByGuarCustId(Mockito.any())).thenReturn(true);
		RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
                () -> when(payGuaranteeService.addPayGuarantee(payGuarantee,headers)));
     assertEquals("Duplicate Data !",exception.getMessage());
	}

	@Test
	void testUpdatePayGuarantee() {
		payGuarantee.setCustomer(customerIndex);
		payGuarantee.setGuarCustId(123L);
		when(payGuaranteeRepo.existsByChrgId(Mockito.any())).thenReturn(true);
		when(customerIndexRepo.findByCustomerId(Mockito.any())).thenReturn(customerIndex);
//		when(payGuaranteeRepo.existsByGuarCustId(Mockito.any())).thenReturn(false);
		when(payGuaranteeRepo.save(Mockito.any())).thenReturn(payGuarantee);
		PayGuarantee payAdded = payGuaranteeService.updatePayGuarantee(payGuarantee,headers);
		assertEquals(payAdded, payGuarantee);
	}

	@Test
	void testUpdatePayGuaranteeNoRecordFoundException() {
		when(payGuaranteeRepo.existsByChrgId(Mockito.any())).thenReturn(false);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(payGuaranteeService.updatePayGuarantee(payGuarantee,headers)));
		assertEquals("No Record Found!", exception.getMessage());
	}

	@Test
	void testUpdatePayGuaranteeRecordNotAddedException() {
		when(payGuaranteeRepo.existsByChrgId(Mockito.any())).thenReturn(true);
		payGuarantee.setGuarCustId(null);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(payGuaranteeService.updatePayGuarantee(payGuarantee,headers)));
		

		when(customerIndexRepo.findByCustomerId(Mockito.any())).thenReturn(null);
		payGuarantee.setGuarCustId(123L);
		exception = assertThrows(RecordNotAddedException.class,
				() -> when(payGuaranteeService.updatePayGuarantee(payGuarantee,headers)));
	}

//	@Test
//	void testUpdatePayGuaranteeRecordAlreadyExistsException() {
//		payGuarantee.setCustomer(customerIndex);
//		payGuarantee.setGuarCustId(123L);
//		when(payGuaranteeRepo.existsByChrgId(Mockito.any())).thenReturn(true);
//		when(customerIndexRepo.findByCustomerId(Mockito.any())).thenReturn(customerIndex);
//		when(payGuaranteeRepo.existsByGuarCustId(Mockito.any())).thenReturn(true);
//		RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
//				() -> when(payGuaranteeService.updatePayGuarantee(payGuarantee,headers)));
//		assertEquals("Duplicate Data !",exception.getMessage());
//	}

}
