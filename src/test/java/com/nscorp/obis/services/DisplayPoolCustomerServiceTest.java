package com.nscorp.obis.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.domain.Customer;
import com.nscorp.obis.domain.CustomerInfo;
import com.nscorp.obis.domain.CustomerPool;
import com.nscorp.obis.domain.Pool;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.CustomerInfoRepository;
import com.nscorp.obis.repository.CustomerPoolRepository;
import com.nscorp.obis.repository.PoolRepository;

class DisplayPoolCustomerServiceTest {

	@InjectMocks
	DisplayPoolCustomerServiceImpl displayPoolCustomerServiceImpl;

	@Mock
	PoolRepository poolRepository;

	@Mock
	CustomerPoolRepository customerPoolRepository;

	@Mock
	CustomerInfoRepository customerInfoRepository;

	Pool pool;

	Customer customer;

	CustomerPool customerPool;

	Set<Customer> customerSet;

	Map<String, String> headers;

	CustomerInfo customerInfo;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		pool = new Pool();
		customer = new Customer();
		customerPool = new CustomerPool();
		customerSet = new HashSet<>();
		customerInfo = new CustomerInfo();

		customer.setCustomerId(1000L);
		customerSet.add(customer);
		pool.setCustomers(customerSet);
		pool.setPoolId(1000000L);
		pool.setPoolReservationType("PV");

		headers = new HashMap<>();
		headers.put("userid", "test");
		headers.put("extensionschema", "test");
	}

	@AfterEach
	void tearDown() throws Exception {
		pool = null;
		customer = null;
		customerSet = null;
		headers = null;
	}

	@Test
	void testAddPoolCustomer() {
		when(poolRepository.existsByPoolId(Mockito.any())).thenReturn(true);
		when(customerInfoRepository.existsById(Mockito.any())).thenReturn(true);
		when(customerInfoRepository.findById(Mockito.any())).thenReturn(Optional.of(customerInfo));
		when(customerPoolRepository.existsByCustomerIdAndPoolId(Mockito.any(), Mockito.any())).thenReturn(false);
		when(poolRepository.findById(Mockito.any())).thenReturn(Optional.of(pool));
		Pool addPoolCustomer = displayPoolCustomerServiceImpl.addPoolCustomer(pool, headers);
		assertNotNull(addPoolCustomer);
	}

	@Test
	void testAddPoolCustomerNoRecordFoundException() {
		when(poolRepository.existsByPoolId(Mockito.any())).thenReturn(true);
		when(customerInfoRepository.existsById(Mockito.any())).thenReturn(false);
		when(customerInfoRepository.findById(Mockito.any())).thenReturn(Optional.of(customerInfo));
		NoRecordsFoundException addException2 = assertThrows(NoRecordsFoundException.class,
				() -> when(displayPoolCustomerServiceImpl.addPoolCustomer(pool, headers)));
		assertEquals("Customer Id: 1000 is not valid as it doesn't exists in CUSTOMER", addException2.getMessage());

		when(poolRepository.existsByPoolId(Mockito.any())).thenReturn(false);
		NoRecordsFoundException addException = assertThrows(NoRecordsFoundException.class,
				() -> when(displayPoolCustomerServiceImpl.addPoolCustomer(pool, headers)));
		assertEquals("Pool Id: " + pool.getPoolId() + " is not valid as it doesn't exists in Pool",
				addException.getMessage());

		pool.setPoolId(null);
		NoRecordsFoundException addException1 = assertThrows(NoRecordsFoundException.class,
				() -> when(displayPoolCustomerServiceImpl.addPoolCustomer(pool, headers)));
		assertEquals("Pool Id: " + pool.getPoolId() + " is not valid as it doesn't exists in Pool",
				addException1.getMessage());

	}

	@Test
	void testAddPoolCustomerNullPointerException() {
		customer.setCustomerId(null);
		customerSet.add(customer);
		pool.setCustomers(customerSet);
		when(poolRepository.existsByPoolId(Mockito.any())).thenReturn(true);
		when(customerInfoRepository.findById(Mockito.any())).thenReturn(Optional.of(customerInfo));
		NullPointerException addException = assertThrows(NullPointerException.class,
				() -> when(displayPoolCustomerServiceImpl.addPoolCustomer(pool, headers)));
		assertEquals("Customer Id should not be null!", addException.getMessage());

		headers.put("extensionschema", null);
		NullPointerException addException1 = assertThrows(NullPointerException.class,
				() -> when(displayPoolCustomerServiceImpl.addPoolCustomer(pool, headers)));
		assertEquals("Extension Schema should not be null, empty or blank", addException1.getMessage());
	}

	@Test
	void testAddPoolCustomerRecordNotAddedException() {
		customerInfo.setTeamAudCd("TO1");
		pool.setPoolReservationType("RR");
		when(poolRepository.existsByPoolId(Mockito.any())).thenReturn(true);
		when(customerInfoRepository.existsById(Mockito.any())).thenReturn(true);
		when(customerInfoRepository.findById(Mockito.any())).thenReturn(Optional.of(customerInfo));
		when(customerPoolRepository.existsByCustomerIdAndPoolId(Mockito.any(), Mockito.any())).thenReturn(false);
		when(poolRepository.findById(Mockito.any())).thenReturn(Optional.of(pool));
		RecordNotAddedException addException = assertThrows(RecordNotAddedException.class,
				() -> when(displayPoolCustomerServiceImpl.addPoolCustomer(pool, headers)));
		assertEquals(
				"Record not added as Customer : null having Team Audit code as (TO1 or TO2) are invalid for RR Trailer Pool",
				addException.getMessage());
	}

	@Test
	void testAddPoolCustomerRecordAlreadyExistsException() {
		when(poolRepository.existsByPoolId(Mockito.any())).thenReturn(true);
		when(customerInfoRepository.existsById(Mockito.any())).thenReturn(true);
		when(customerInfoRepository.findById(Mockito.any())).thenReturn(Optional.of(customerInfo));
		when(customerPoolRepository.existsByCustomerIdAndPoolId(Mockito.any(), Mockito.any())).thenReturn(true);
		when(poolRepository.findById(Mockito.any())).thenReturn(Optional.of(pool));
		RecordAlreadyExistsException addException = assertThrows(RecordAlreadyExistsException.class,
				() -> when(displayPoolCustomerServiceImpl.addPoolCustomer(pool, headers)));
		assertEquals("Record with Pool Id: " + pool.getPoolId() + " and Customer Name: " + customer.getCustomerName()
				+ "is already exists!", addException.getMessage());
	}

	@Test
	void testDeletePool() {
		when(poolRepository.findById(Mockito.any())).thenReturn(Optional.of(pool));
		when(customerPoolRepository.existsByCustomerIdAndPoolIdAndUversion(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(true);
		when(customerPoolRepository.findByCustomerIdAndPoolId(Mockito.any(), Mockito.any())).thenReturn(customerPool);
		displayPoolCustomerServiceImpl.deletePool(pool);
	}

	@Test
	void testDeletePoolRecordNotDeleted() {
		when(poolRepository.findById(Mockito.any())).thenReturn(Optional.of(pool));
		when(customerPoolRepository.existsByCustomerIdAndPoolIdAndUversion(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(false);
		assertThrows(RecordNotDeletedException.class, () -> displayPoolCustomerServiceImpl.deletePool(pool));
	}
}
