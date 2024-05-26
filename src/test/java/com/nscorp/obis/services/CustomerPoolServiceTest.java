package com.nscorp.obis.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import com.nscorp.obis.common.SpecificationGenerator;
import com.nscorp.obis.domain.CustomerPool;
import com.nscorp.obis.domain.CustomerTerm;
import com.nscorp.obis.domain.CustomerTerminal;
import com.nscorp.obis.dto.CustomerPoolDTO;
import com.nscorp.obis.dto.CustomerTermDTO;
import com.nscorp.obis.dto.CustomerTerminalDTO;
import com.nscorp.obis.dto.PoolListDTO;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.CustomerInfoRepository;
import com.nscorp.obis.repository.CustomerPoolRepository;
import com.nscorp.obis.repository.CustomerRepository;
import com.nscorp.obis.repository.CustomerTermRepository;
import com.nscorp.obis.repository.CustomerTerminalRepository;
import com.nscorp.obis.repository.PoolRepository;
import com.nscorp.obis.repository.TerminalRepository;

public class CustomerPoolServiceTest {

	@Mock
	CustomerPoolRepository customerPoolRepository;
	@Mock
	SpecificationGenerator specificationGenerator;
	@Mock
	CustomerInfoRepository customerInfoRepo;
	@Mock
	PoolRepository poolRepo;

	@InjectMocks
	CustomerPoolServiceImpl customerPoolService;

	@Mock
	CustomerRepository customerRepository;

	PoolListDTO poolListDTO;

	CustomerPool customerPool;
	List<CustomerPool> customerPools;
	Map<String, String> header;
	Specification<CustomerTerm> specification;

	Long customerId;
	Long poolId;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		customerPool = new CustomerPool();
		customerPools = new ArrayList<>();
		poolId = 1002004L;
		customerId = 1138L;
		customerPool.setCustomerId(customerId);
		customerPool.setPoolId(poolId);
		customerPools.add(customerPool);
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
		poolListDTO = new PoolListDTO();
	}

	@AfterEach
	void tearDown() throws Exception {
		customerPool = null;
		customerPools = null;
	}

	@Test
	void testFetchCustomerPool() throws SQLException {
		when(customerPoolRepository.findByCustomerId(Mockito.any())).thenReturn(customerPools);
		List<CustomerPool> response = customerPoolService.fetchCustomerPool(customerId);
		assertEquals(response, customerPools);
	}

	@Test
	void testUpdateCustomerPools() throws SQLException {
		poolListDTO = new PoolListDTO();
		poolListDTO.setCustomerId(1111L);
		poolListDTO.setPoolIds(Arrays.asList(111L, 222L));
		customerPools = new ArrayList<>();
		customerPool = new CustomerPool();
		customerPool.setCustomerId(1111L);
		customerPool.setPoolId(333L);
		customerPools.add(customerPool);
		when(customerInfoRepo.existsById(Mockito.anyLong())).thenReturn(true);
		when(poolRepo.existsById(Mockito.anyLong())).thenReturn(true);
		when(customerPoolRepository.findByCustomerId(Mockito.anyLong())).thenReturn(customerPools);
		when(customerPoolRepository.findByCustomerIdAndPoolId(Mockito.anyLong(), Mockito.anyLong()))
				.thenReturn(customerPool);
		List<CustomerPoolDTO> response = customerPoolService.updateCustomerPools(poolListDTO, header);
		assertEquals(response.size(), customerPools.size());
		when(customerInfoRepo.existsById(Mockito.anyLong())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class, () -> customerPoolService.updateCustomerPools(poolListDTO, header));
		when(customerInfoRepo.existsById(Mockito.anyLong())).thenReturn(true);
		when(poolRepo.existsById(Mockito.anyLong())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class, () -> customerPoolService.updateCustomerPools(poolListDTO, header));
		poolListDTO.setPoolIds(Arrays.asList(null, 222L));
		assertThrows(InvalidDataException.class, () -> customerPoolService.updateCustomerPools(poolListDTO, header));
		when(customerInfoRepo.existsById(Mockito.anyLong())).thenReturn(true);
		when(poolRepo.existsById(Mockito.anyLong())).thenReturn(true);
		poolListDTO.setPoolIds(Arrays.asList(111L, 222L));
		header.put("extensionschema", null);
		response = customerPoolService.updateCustomerPools(poolListDTO, header);
		assertEquals(response.size(), customerPools.size());
	}

	@Test
	void testNoRecordsFoundException() {
		when(customerPoolRepository.findByCustomerId(Mockito.any())).thenReturn(new ArrayList<>());
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(customerPoolService.fetchCustomerPool(customerId)));
		assertEquals(exception.getMessage(), "No Records found for this Customer.");
	}

}
