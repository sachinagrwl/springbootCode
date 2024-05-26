package com.nscorp.obis.services;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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

import com.nscorp.obis.domain.Pool;
import com.nscorp.obis.domain.PoolCarStorageExempt;
import com.nscorp.obis.domain.Terminal;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.PoolRepository;

class PoolTerminalConflictServiceTest {

	@InjectMocks
	PoolTerminalConflictServiceImpl poolTerminalConflictService;

	@Mock
	PoolRepository poolRepository;

	PoolCarStorageExempt carStorageExempt;

	Pool pool;
	Pool dbPool;
	Terminal terminalA;
	Terminal terminalB;
	Set<Terminal> terminalSet;
	Set<Terminal> dbTerminalSet;
	List<PoolCarStorageExempt> carStorageExemptList;

	Set<PoolCarStorageExempt> CarStorageExemptSet;

	Map<String, String> header;

	Long poolId;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		poolId = 1000L;
		carStorageExempt = new PoolCarStorageExempt();
		pool = new Pool();
		dbPool = new Pool();
		terminalA = new Terminal();
		terminalB = new Terminal();
		carStorageExemptList = new ArrayList<>();
		CarStorageExemptSet = new HashSet<>();
		terminalSet = new HashSet<>();
		dbTerminalSet = new HashSet<>();
		header = new HashMap<>();

		terminalA.setTerminalId(100L);
		terminalB.setTerminalId(200L);
		terminalSet.add(terminalA);
		terminalSet.add(terminalB);
		pool.setTerminals(terminalSet);
		pool.setPoolId(poolId);
		carStorageExempt.setPool(pool);
		carStorageExempt.setPoolId(poolId);

		dbTerminalSet.add(terminalA);
		dbPool.setTerminals(dbTerminalSet);
		dbPool.setPoolId(poolId);

		header.put("userid", "test");
		header.put("extensionschema", "test");

	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testvalidatePoolTerminalConflict() {
		when(poolRepository.existsById(Mockito.any())).thenReturn(true);
		when(poolRepository.findById(Mockito.any())).thenReturn(Optional.of(dbPool));
		when(poolRepository.poolTerminalConflict(Mockito.any(), Mockito.any())).thenReturn(poolId);
		Set<Pool> validatedPools = poolTerminalConflictService.validatePoolTerminalConflict(pool, header);
		assertTrue(!validatedPools.isEmpty());
	}

	@Test
	void testvalidatePoolTerminalConflictNoConflict() {
		when(poolRepository.existsById(Mockito.any())).thenReturn(true);
		when(poolRepository.findById(Mockito.any())).thenReturn(Optional.of(pool));
		when(poolRepository.poolTerminalConflict(Mockito.any(), Mockito.any())).thenReturn(poolId);
		Set<Pool> validatedPools = poolTerminalConflictService.validatePoolTerminalConflict(pool, header);
		assertTrue(validatedPools.isEmpty());

		when(poolRepository.findById(Mockito.any())).thenReturn(Optional.of(dbPool));
		when(poolRepository.poolTerminalConflict(Mockito.any(), Mockito.any())).thenReturn(null);
		Set<Pool> validatedPools2 = poolTerminalConflictService.validatePoolTerminalConflict(pool, header);
		assertTrue(validatedPools2.isEmpty());
	}

	@Test
	void testNoRecordFoundException() {
		pool.setPoolId(null);
		NoRecordsFoundException validateConflictException = assertThrows(NoRecordsFoundException.class,
				() -> when(poolTerminalConflictService.validatePoolTerminalConflict(pool, header)));
		assertEquals("Pool Id should not be null!", validateConflictException.getMessage());

		pool.setPoolId(poolId);
		when(poolRepository.existsById(Mockito.any())).thenReturn(false);
		NoRecordsFoundException validateConflictException1 = assertThrows(NoRecordsFoundException.class,
				() -> when(poolTerminalConflictService.validatePoolTerminalConflict(pool, header)));
		assertEquals("PoolId: " + pool.getPoolId() + " is invalid as it doesn't exists in Pool",
				validateConflictException1.getMessage());
	}
}
