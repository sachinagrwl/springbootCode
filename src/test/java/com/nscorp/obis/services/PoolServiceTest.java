package com.nscorp.obis.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.nscorp.obis.domain.*;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.*;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.SpecificationGenerator;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;

public class PoolServiceTest {
	@Mock
	PoolRepository repository;

	@Mock
	PoolTerminalRepository poolTermRepository;

	@Mock
	PoolTypeRepository poolTypeRepository;

	@Mock
	TruckerGroupRepository truckerGroupRepository;

	@Mock
	PoolEquipmentControllerRepository poolEqControllerRepo;

	@Mock
	PoolEquipmentRangeRepository poolEqRangeRepo;

	@InjectMocks
	PoolServiceImpl service;

	@Mock
	SpecificationGenerator specificationGenerator;

	Specification<Pool> specs;
	Long poolId;
	String poolName;
	List<Pool> pools;

	TruckerGroup truckerGroup;
	PoolEquipmentRange poolEquipmentRange;

	List<PoolEquipmentRange> poolEquipmentRangeList;
	PoolEquipmentController poolEqCtrl;
	List<PoolEquipmentController> poolEqCtrlList;
	Customer customer;
	Pool pool;
	Pool existingPool;
	PoolTerminal poolTerminal;
	List<PoolTerminal> poolTerminalList;
	Set<Terminal> terminal;
	Terminal terminalObj;

	Map<String, String> header;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		poolTerminalList = new ArrayList<>();
		terminal = new HashSet<>();

		truckerGroup = new TruckerGroup();
		truckerGroup.setTruckerGroupCode("TEST");
		truckerGroup.setTruckerGroupDesc("TEST");
		truckerGroup.setSetupSchema("TEST");

		poolEquipmentRange = new PoolEquipmentRange();
		poolEquipmentRange.setPoolRangeId(123456789L);
		poolEquipmentRange.setPoolId(12345678L);
		poolEquipmentRange.setEquipmentInit("TEST");
		poolEquipmentRange.setEquipmentLowNumber(new BigDecimal(1000));
		poolEquipmentRange.setEquipmentHighNumber(new BigDecimal(1000));
		poolEquipmentRange.setEquipmentType("C");

		poolEquipmentRangeList = new ArrayList<>();
		poolEquipmentRangeList.add(poolEquipmentRange);

		customer = new Customer();
		customer.setTeamAudCd("A10");
		customer.setPrimeContact("TEST");
		customer.setDeliveryDetail(null);
		customer.setCustomerZipCode("TEST");
		customer.setCustomerState("TEST");
		customer.setCustomerNumber("Test");
		customer.setCustomerName("TEST");
		customer.setCustomerId(100L);
		customer.setCustomerExt(123000);
		customer.setCustomerExchange(123000);
		customer.setCustomerCountry("TEST");
		customer.setCustomerCity("TEST");
		customer.setCustomerBase("TEST");
		customer.setCustomerArea(123000);
		customer.setCustomerAdd2("TEST");
		customer.setCustomerAdd1("TEST");
		customer.setCorporateCustomerId(Long.valueOf(123));

		poolEqCtrl = new PoolEquipmentController();
		poolEqCtrl.setPoolControllerId(Long.valueOf(123));
		poolEqCtrl.setPoolId(Long.valueOf(123));
		poolEqCtrl.setCustomerPrimary6("TEST");
		poolEqCtrl.setEquipmentType("C");
		poolEqCtrl.setCustomer(customer);

		poolEqCtrlList = new ArrayList<>();
		poolEqCtrlList.add(poolEqCtrl);

		pools = new ArrayList<>();
		terminalObj = new Terminal();
		pool = new Pool();
		existingPool = new Pool();
		poolTerminal = new PoolTerminal();
		poolId = 1234L;
		poolName = "VIP";
		terminalObj.setTerminalId(100L);
		terminal.add(terminalObj);
		pool.setPoolId(poolId);
		pool.setPoolName(poolName);
		pool.setDescription("description");
		pool.setTerminals(terminal);
		pool.setPoolReservationType("HJ");
		pool.setTruckerGroup(truckerGroup);
		pool.setAgreementRequired("T");
		pool.setCheckTrucker("B");
		pools.add(pool);

		existingPool.setPoolId(100L);
		existingPool.setPoolName("Pool");
		existingPool.setDescription("Pool description");
		existingPool.setTerminals(terminal);
		existingPool.setPoolReservationType("PV");
		existingPool.setTruckerGroup(truckerGroup);
		existingPool.setAgreementRequired("T");
		existingPool.setCheckTrucker("B");

		poolTerminal.setPoolId(poolId);
		poolTerminal.setTerminalId(poolId);
		poolTerminalList.add(poolTerminal);

		header = new HashMap<>();
		header.put("userid", "test");
		header.put("extensionschema", "test");

	}

	@AfterEach
	void tearDown() throws Exception {
		pool = null;
		existingPool = null;
		pools = null;
		customer = null;
		poolEqCtrl = null;
		poolEqCtrlList = null;
		poolEquipmentRange = null;
		poolEquipmentRangeList = null;
	}

	@Test
	void testGetPools() {
		when(repository.existsByPoolId(Mockito.any())).thenReturn(true);
		when(specificationGenerator.poolSpecification(Mockito.any(), Mockito.any())).thenReturn(specs);
		when(repository.findAll(specs)).thenReturn(pools);
		List<Pool> poolList = service.getPools(poolId, poolName);
		assertEquals(pools, poolList);
	}

	@SuppressWarnings("unchecked")
	@Test
	void testInvalidDataException() {
		when(repository.existsByPoolId(Mockito.any())).thenReturn(false);
		when(specificationGenerator.poolSpecification(Mockito.any(), Mockito.any())).thenReturn(specs);
		when(repository.findAll(Mockito.any(Specification.class))).thenReturn(pools);
		InvalidDataException exception = assertThrows(InvalidDataException.class,
				() -> when(service.getPools(poolId, poolName)));
	}

	@SuppressWarnings("unchecked")
	@Test
	void testNoRecordsFoundException() {
		when(repository.existsByPoolId(Mockito.any())).thenReturn(true);
		when(specificationGenerator.poolSpecification(Mockito.any(), Mockito.any())).thenReturn(specs);
		when(repository.findAll(Mockito.any(Specification.class)))
				.thenThrow(new NoRecordsFoundException("No records found."));
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(service.getPools(poolId, poolName)));

	}

	@Test
	void testUpdatePoolTerminal() {
		when(repository.existsByPoolId(Mockito.any())).thenReturn(true);
		when(poolTermRepository.existsByPoolIdAndTerminalId(Mockito.any(), Mockito.any())).thenReturn(false);
		when(poolTermRepository.findByPoolId(Mockito.any())).thenReturn(poolTerminalList);
		when(repository.findById(Mockito.any())).thenReturn(Optional.of(pool));
		Pool updatedPool = service.updatePoolTerminal(pool, header);
		assertEquals(updatedPool, pool);
	}

//    @Test
//    void testLambdaUpdatePool() {
//    	when(repository.existsByPoolIdAndUversion(Mockito.any(), Mockito.any())).thenReturn(true);
//    	when(poolTermRepository.existsByPoolIdAndTerminalId(Mockito.any(), Mockito.any())).thenReturn(false);
//    	when(poolTermRepository.findByPoolId(Mockito.any())).thenReturn(poolTerminalList);
//    	when(repository.findById(Mockito.any())).thenReturn(Optional.of(pool));
//    	Pool updatedPool = service.updatePool(pool, header);
//    	assertEquals(updatedPool, pool);
//    }

	@Test
	void testUpdatePoolTerminalNoRecordsFoundException() {
		when(repository.existsByPoolId(Mockito.any())).thenReturn(false);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(service.updatePoolTerminal(pool, header)));
		assertEquals("Record Not Found!", exception.getMessage());
	}

	@Test
	void testUpdatePoolTerminalNullPointerException() {
		header.put("extensionschema", null);
		assertNull(header.get(CommonConstants.EXTENSION_SCHEMA));
		NullPointerException exception = assertThrows(NullPointerException.class,
				() -> service.updatePoolTerminal(pool, header));
		assertEquals("Extension Schema should not be null, empty or blank", exception.getMessage());
	}

	@Test
	void testAddPool() {
		when(repository.findById(Mockito.any())).thenReturn(Optional.of(existingPool));
		when(poolTypeRepository.existsById(Mockito.any())).thenReturn(true);
		when(truckerGroupRepository.existsByTruckerGroupCode(Mockito.any())).thenReturn(true);
		when(truckerGroupRepository.findByTruckerGroupCode(Mockito.any())).thenReturn(truckerGroup);
		when(repository.save(Mockito.any())).thenReturn(pool);
		Pool addedPool = service.addPool(pool, header);
		assertNotNull(addedPool);
	}

	@Test
	void testAddPoolNameDescRecordNotAddedException() {
		when(repository.existsByPoolName(pool.getPoolName())).thenReturn(true);
		when(repository.existsByDescription(pool.getDescription())).thenReturn(true);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> service.addPool(pool, header));
		assertEquals("Pool Name & Pool Description should be unique from other records!", exception.getMessage());
	}

	@Test
	void testAddPoolNameRecordNotAddedException() {
		when(repository.existsByPoolName(pool.getPoolName())).thenReturn(true);
		when(repository.existsByDescription(pool.getDescription())).thenReturn(false);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> service.addPool(pool, header));
		assertEquals("Pool Name should be unique from other records!", exception.getMessage());
	}

	@Test
	void testAddPoolDescRecordNotAddedException() {
		when(repository.existsByPoolName(pool.getPoolName())).thenReturn(false);
		when(repository.existsByDescription(pool.getDescription())).thenReturn(true);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> service.addPool(pool, header));
		assertEquals("Pool Description should be unique from other records!", exception.getMessage());
	}

	@Test
	void testAddPoolTypeRecordNotAddedException() {
		when(repository.existsByPoolName(pool.getPoolName())).thenReturn(false);
		when(repository.existsByDescription(pool.getDescription())).thenReturn(false);
		when(poolTypeRepository.existsById(Mockito.any())).thenReturn(false);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> service.addPool(pool, header));
		assertEquals("Reservation Type is not valid", exception.getMessage());
	}

	@Test
	void testAddPoolTruckerGroupRecordNotAddedException() {
		when(repository.existsByPoolName(pool.getPoolName())).thenReturn(false);
		when(repository.existsByDescription(pool.getDescription())).thenReturn(false);
		when(poolTypeRepository.existsById(Mockito.any())).thenReturn(true);
		when(truckerGroupRepository.existsByTruckerGroupCode(Mockito.any())).thenReturn(false);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> service.addPool(pool, header));
		assertEquals("Trucker Group Code is not valid", exception.getMessage());
	}

	@Test
	void testUpdatePool() {
		pool.setUversion("!");
		when(repository.existsByPoolId(Mockito.any())).thenReturn(true);
		when(repository.findById(Mockito.any())).thenReturn(Optional.of(existingPool));
		when(poolTypeRepository.existsById(Mockito.any())).thenReturn(true);
		when(truckerGroupRepository.existsByTruckerGroupCode(Mockito.any())).thenReturn(true);
		when(truckerGroupRepository.findByTruckerGroupCode(Mockito.any())).thenReturn(truckerGroup);

		when(repository.save(Mockito.any())).thenReturn(pool);
		Pool updatedPool = service.updatePool(pool, header);
		assertEquals(updatedPool.getPoolName(), pool.getPoolName());
	}

	@Test
	void testUpdatePoolNameRecordNotAddedException() {
//		existingPool.setPoolName(poolName);
		existingPool.setDescription("description");
		when(repository.existsByPoolId(Mockito.any())).thenReturn(true);
		when(repository.findById(Mockito.any())).thenReturn(Optional.of(existingPool));
		when(repository.existsByPoolName(Mockito.any())).thenReturn(true);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> service.updatePool(pool, header));
		assertEquals("Pool Name should be unique from other records!", exception.getMessage());
	}

	@Test
	void testUpdatePoolNameDescRecordNotAddedException() {
//		existingPool.setPoolName(poolName);
//		existingPool.setDescription("description");
		when(repository.existsByPoolId(Mockito.any())).thenReturn(true);
		when(repository.findById(Mockito.any())).thenReturn(Optional.of(existingPool));
		when(repository.existsByPoolName(Mockito.any())).thenReturn(true);
		when(repository.existsByDescription(Mockito.any())).thenReturn(true);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> service.updatePool(pool, header));
		assertEquals("Pool Name & Pool Description should be unique from other records!", exception.getMessage());
	}

	@Test
	void testUpdatePoolDescRecordNotAddedException() {
//		existingPool.setDescription("description");
		existingPool.setPoolName(poolName);
		when(repository.existsByPoolId(Mockito.any())).thenReturn(true);
		when(repository.findById(Mockito.any())).thenReturn(Optional.of(existingPool));
//		when(repository.existsByPoolName(pool.getPoolName())).thenReturn(false);
		when(repository.existsByDescription(Mockito.any())).thenReturn(true);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> service.updatePool(pool, header));
		assertEquals("Pool Description should be unique from other records!", exception.getMessage());
	}

	@Test
	void testUpdatePoolNoRecordsFoundException() {
		when(repository.existsByPoolId(pool.getPoolId())).thenReturn(false);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> service.updatePool(pool, header));
		assertEquals("No record found for this 'poolId': "+pool.getPoolId(), exception.getMessage());
	}

	@Test
	void testDeletePool() {
		when(repository.existsByPoolId(Mockito.any())).thenReturn(true);
		when(repository.findById(Mockito.any())).thenReturn(Optional.of(pool));
		when(poolEqControllerRepo.findByPoolId(pool.getPoolId())).thenReturn(poolEqCtrlList);
		when(poolEqRangeRepo.findByPoolId(pool.getPoolId())).thenReturn(poolEquipmentRangeList);
		Pool poolObj = service.deletePool(pool);
		assertNotNull(poolObj);
	}

	@Test
	void testDeletePoolNoRecordsFoundException() {
		when(repository.existsByPoolId(Mockito.any())).thenReturn(false);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> service.deletePool(pool));
		assertEquals("No record Found to delete Under this Pool Id: " + pool.getPoolId(), exception.getMessage());
	}

	@Test
	void testDeletePoolRecordNotDeletedException() {
		when(poolTermRepository.existsByPoolId(Mockito.any())).thenReturn(true);
		RecordNotDeletedException exception = assertThrows(RecordNotDeletedException.class,
				() -> service.deletePool(pool));
		assertEquals("Pool with Pool Id: "+pool.getPoolId()+" has some active terminals", exception.getMessage());
	}

}
