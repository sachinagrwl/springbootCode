package com.nscorp.obis.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.domain.Pool;
import com.nscorp.obis.domain.PoolCarStorageExempt;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.PoolCarStorageExemptRepository;
import com.nscorp.obis.repository.PoolRepository;
import com.nscorp.obis.repository.PoolTypeRepository;

class PoolCarStorageExemptServiceTest {

	@InjectMocks
	PoolCarStorageExemptServiceImpl carStorageExemptService;

	@Mock
	PoolCarStorageExemptRepository carStorageExemptRepository;

	@Mock
	PoolRepository poolRepository;

	@Mock
	PoolTypeRepository poolTypeRepository;

	List<PoolCarStorageExempt> carStorageExemptsList;

	PoolCarStorageExempt poolCarStorageExempt;

	Pool pool;

	Map<String, String> headers;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		poolCarStorageExempt = new PoolCarStorageExempt();
		pool = new Pool();
		pool.setPoolId(10000L);
		pool.setPoolReservationType("PT");
		poolCarStorageExempt.setPoolId(10000L);
		poolCarStorageExempt.setPool(pool);

		carStorageExemptsList = new ArrayList<>();

		carStorageExemptsList.add(poolCarStorageExempt);

		headers = new HashMap<String, String>();
		headers.put("userid", "Test");
		headers.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		carStorageExemptsList = null;
		poolCarStorageExempt = null;
		headers = null;
	}

	@Test
	void testGetAllPoolCarStorageExempts() {
		when(carStorageExemptRepository.findAll(Sort.by("pool.description").and(Sort.by("pool.poolReservationType"))))
				.thenReturn(carStorageExemptsList);
		List<PoolCarStorageExempt> allPoolCarStorageExempts = carStorageExemptService.getAllPoolCarStorageExempts();
		assertEquals(allPoolCarStorageExempts, carStorageExemptsList);
	}

	@Test
	void testAddPoolCarStorageExempt() {
		when(carStorageExemptRepository.existsById(Mockito.any())).thenReturn(false);
		when(poolRepository.existsById(Mockito.any())).thenReturn(true);
		when(poolTypeRepository.existsById(Mockito.any())).thenReturn(true);
		when(poolRepository.findById(Mockito.any())).thenReturn(Optional.of(pool));
		when(carStorageExemptRepository.save(Mockito.any())).thenReturn(poolCarStorageExempt);
		PoolCarStorageExempt addPoolCarStorageExempt = carStorageExemptService
				.addPoolCarStorageExempt(poolCarStorageExempt, headers);
		assertEquals(addPoolCarStorageExempt, poolCarStorageExempt);
	}

	@Test
	void testDeletePoolCarStorageExempt() {
		when(carStorageExemptRepository.existsByPoolIdAndUversion(Mockito.any(), Mockito.any())).thenReturn(true);
		when(carStorageExemptRepository.findById(Mockito.any())).thenReturn(Optional.of(poolCarStorageExempt));
		PoolCarStorageExempt deletePoolCarStorageExempt = carStorageExemptService
				.deletePoolCarStorageExempt(poolCarStorageExempt);
		assertEquals(deletePoolCarStorageExempt, poolCarStorageExempt);
	}

	@Test
	void testNoRecordFoundException() {
		when(carStorageExemptRepository.findAll(Sort.by("pool.description").and(Sort.by("pool.poolReservationType"))))
				.thenReturn(Collections.emptyList());
		NoRecordsFoundException getException = assertThrows(NoRecordsFoundException.class,
				() -> when(carStorageExemptService.getAllPoolCarStorageExempts()));
		assertEquals("No Records are found for Pool Car Storage Exempts!", getException.getMessage());

		when(carStorageExemptRepository.existsByPoolIdAndUversion(Mockito.any(), Mockito.any())).thenReturn(false);
		NoRecordsFoundException deleteException = assertThrows(NoRecordsFoundException.class,
				() -> when(carStorageExemptService.deletePoolCarStorageExempt(poolCarStorageExempt)));
		assertEquals("No record Found to delete Under this Pool Id: " + poolCarStorageExempt.getPoolId()
				+ " and U_Version: " + poolCarStorageExempt.getUversion(), deleteException.getMessage());

	}

	@Test
	void testRecordAlreadyExistsException() {
		when(carStorageExemptRepository.existsById(Mockito.any())).thenReturn(true);
		RecordAlreadyExistsException addException = assertThrows(RecordAlreadyExistsException.class,
				() -> when(carStorageExemptService.addPoolCarStorageExempt(poolCarStorageExempt, headers)));
		assertEquals("Record under this Pool Id: " + poolCarStorageExempt.getPool().getPoolId()
				+ " is already exists on Car Exempt", addException.getMessage());
	}

	@Test
	void testRecordNotAddedException() {
		when(carStorageExemptRepository.existsById(Mockito.any())).thenReturn(false);
		when(poolRepository.existsById(Mockito.any())).thenReturn(false);
		RecordNotAddedException addException = assertThrows(RecordNotAddedException.class,
				() -> when(carStorageExemptService.addPoolCarStorageExempt(poolCarStorageExempt, headers)));
		assertEquals("Record under this Pool Id: " + poolCarStorageExempt.getPool().getPoolId()
				+ " is not valid as it doesn't exists on Pool", addException.getMessage());

		when(carStorageExemptRepository.existsById(Mockito.any())).thenReturn(false);
		when(poolRepository.existsById(Mockito.any())).thenReturn(true);
		headers.put(CommonConstants.EXTENSION_SCHEMA, null);
		RecordNotAddedException addException1 = assertThrows(RecordNotAddedException.class,
				() -> when(carStorageExemptService.addPoolCarStorageExempt(poolCarStorageExempt, headers)));
		assertEquals("Extension Schema should not be null, empty or blank", addException1.getMessage());

		poolCarStorageExempt.setPoolId(null);
		RecordNotAddedException addException2 = assertThrows(RecordNotAddedException.class,
				() -> when(carStorageExemptService.addPoolCarStorageExempt(poolCarStorageExempt, headers)));
		assertEquals("Pool Id cannot be null!", addException2.getMessage());

	}
}
