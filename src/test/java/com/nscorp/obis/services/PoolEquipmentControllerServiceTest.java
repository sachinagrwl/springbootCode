package com.nscorp.obis.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.domain.Customer;
import com.nscorp.obis.domain.Pool;
import com.nscorp.obis.domain.PoolEquipmentConflictController;
import com.nscorp.obis.domain.PoolEquipmentController;
import com.nscorp.obis.domain.Terminal;
import com.nscorp.obis.dto.PoolEquipmentControllerDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.CustomerRepository;
import com.nscorp.obis.repository.PoolEquipmentConflictControllerRepository;
import com.nscorp.obis.repository.PoolEquipmentControllerRepository;
import com.nscorp.obis.repository.PoolRepository;

class PoolEquipmentControllerServiceTest {
	
	@InjectMocks
	PoolEquipmentControllerServiceImpl poolEqService;
	
	@Mock
	PoolEquipmentControllerRepository controllerRepo;
	
	@Mock 
	CustomerRepository customerRepo;
	
	@Mock
	PoolRepository poolRepo;
	
	@Mock
	PoolEquipmentConflictControllerRepository conflictRepository;
	
	PoolEquipmentController poolEqCtrl;
	PoolEquipmentControllerDTO poolEqCtrlDto;
	List<PoolEquipmentController> poolEqCtrlList;
	List<PoolEquipmentControllerDTO> poolEqCtrlDtoList;
	PoolEquipmentConflictController conflictController;
	List<PoolEquipmentConflictController> conflictControllerList;
	Customer customer;
	Pool pool1;
	List<Pool> poolList;
	Terminal terminalObj;
	Long poolId;
	Long customerId = 100L;
	
	Map<String,String> header;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		poolId = 1000L;
		poolEqCtrl = new PoolEquipmentController();
		poolEqCtrlDto = new PoolEquipmentControllerDTO();
		customer = new Customer();
		conflictController = new PoolEquipmentConflictController();
		poolEqCtrlList = new ArrayList<>();
		poolEqCtrlDtoList = new ArrayList<>();
		
		poolEqCtrl.setPoolControllerId(Long.valueOf(123));
		poolEqCtrl.setPoolId(Long.valueOf(123));
		poolEqCtrl.setCustomerPrimary6(null);
		poolEqCtrl.setEquipmentType("C");
		poolEqCtrl.setCustomer(customer);
		
		poolEqCtrlList.add(poolEqCtrl);
		
		poolEqCtrlDto.setPoolControllerId(Long.valueOf(123));
		poolEqCtrlDto.setPoolId(Long.valueOf(123));
		poolEqCtrlDto.setCustomerPrimary6("123000");
		poolEqCtrlDto.setEquipmentType("C");
		poolEqCtrlDto.setCustomer(customer);
		
		poolEqCtrlDtoList.add(poolEqCtrlDto);
		
		customer.setTeamAudCd("A10");
		customer.setPrimeContact("TEST");
		customer.setDeliveryDetail(null);
		customer.setCustomerZipCode("TEST");
		customer.setCustomerState("TEST");
		customer.setCustomerNumber("123000");
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
		
		poolList = new ArrayList<>();
		conflictControllerList = new ArrayList<>();
		terminalObj = new Terminal();
		terminalObj.setTerminalId(Long.valueOf(123));
		pool1 = new Pool();
		pool1.setPoolId(Long.valueOf(123));
		pool1.setPoolName("TEST");
		terminalObj.setTerminalName("TEST");
		
		conflictController.setControllerType("TEST");
		conflictController.setCustomerPrimary6("TEST");
		conflictController.setPoolControllerId(Long.valueOf(123));
		conflictController.setPool(pool1);
		conflictController.setTerminal(terminalObj);
		
		conflictControllerList.add(conflictController);
//		conflictControllerList.add(conflictController);
//		conflictControllerList.add(conflictController);
	
		header = new HashMap<String, String>();
        header.put("userid", "Test");
        header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetAllPool() {
		when(controllerRepo.findAll()).thenReturn(poolEqCtrlList);
		List<PoolEquipmentController> allPools = poolEqService.getAllPool();
		assertEquals(allPools, poolEqCtrlList);
	}
	
	@Test
	void testGetAllPoolNoRecordsFoundException() {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(poolEqService.getAllPool()));
		assertEquals("No Records Found!", exception.getMessage());		
	}
	
	@Test
	void testInsertPoolNoRecordFoundException() {
		poolEqCtrl.setPoolId(null);
		NoRecordsFoundException validateConflictException = assertThrows(NoRecordsFoundException.class,
				() -> when(poolEqService.insertPoolCtrl(poolEqCtrl, header)));
		assertEquals("Pool Id should not be null!", validateConflictException.getMessage());

		poolEqCtrl.setPoolId(poolId);
		when(controllerRepo.existsByPoolId(Mockito.any())).thenReturn(false);
		NoRecordsFoundException validateConflictException1 = assertThrows(NoRecordsFoundException.class,
				() -> when(poolEqService.insertPoolCtrl(poolEqCtrl, header)));
		assertEquals("PoolId: 1000 is invalid as it doesn't exists in Pool",
				validateConflictException1.getMessage());
	}
	
	@Test
	void testInsertNoRecordFoundException() {
		poolEqCtrl.setCustomer(null);
		NoRecordsFoundException validateConflictException = assertThrows(NoRecordsFoundException.class,
				() -> when(poolEqService.insertPoolCtrl(poolEqCtrl, header)));
		assertEquals("Customer should not be null!", validateConflictException.getMessage());
		
		
	}
	
	@Test
	void testInsertCustNoRecordFoundException() {
		poolEqCtrl.setCustomer(null);;
		NoRecordsFoundException validateConflictException = assertThrows(NoRecordsFoundException.class,
				() -> when(poolEqService.insertPoolCtrl(poolEqCtrl, header)));
		assertEquals("Customer should not be null!", validateConflictException.getMessage());
		
		
	}
	
	@Test
	void testInvalidNoRecordFoundException() {
		poolEqCtrl.getCustomer().setCustomerId(customerId);
		when(poolRepo.existsByPoolId(Mockito.any())).thenReturn(true);
		when(customerRepo.existsByCustomerId(Mockito.any())).thenReturn(false);
		NoRecordsFoundException validateConflictException2 = assertThrows(NoRecordsFoundException.class,
				() -> when(poolEqService.insertPoolCtrl(poolEqCtrl, header)));
		assertEquals("Customer Id: 100 is invalid as it doesn't exists in Customer",
				validateConflictException2.getMessage());
	}
	
	@Test
	void testInvalidRecordFoundException() {
		poolEqCtrl.getCustomer().setCustomerId(null);
		NoRecordsFoundException validateConflictException1 = assertThrows(NoRecordsFoundException.class,
				() -> when(poolEqService.insertPoolCtrl(poolEqCtrl, header)));
		assertEquals("Customer Id should not be null!", validateConflictException1.getMessage());
	}
	
	@Test
	void testConflictNoRecordFoundException() {
		
		poolEqCtrl.setPoolId(123000L);
		poolEqCtrl.setCustomerPrimary6(customer.getCustomerNumber().substring(0, 6));
		
		when(customerRepo.existsByCustomerId(Mockito.any())).thenReturn(true);
		when(poolRepo.existsByPoolId(Mockito.any())).thenReturn(true);
		when(customerRepo.findById(Mockito.any())).thenReturn(Optional.of(customer));
		when(customerRepo.findByCustomerId(Mockito.any())).thenReturn(customer);
		when(conflictRepository.existsByControllerTypeAndCustomerPrimary6AndPoolControllerId(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
		when(conflictRepository.findByControllerTypeAndCustomerPrimary6AndPoolControllerId(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(conflictControllerList);
		RecordNotAddedException exception2 = assertThrows(RecordNotAddedException.class,
				() -> when(poolEqService.insertPoolCtrl(poolEqCtrl, header)));
		assertEquals("The Controller Conflict Identified on Pool Name - Equipment Type - Customer Primary 6 - Terminal : TEST - TEST - TEST - TEST", exception2.getMessage());
	}
	
	@Test
	void testAlreadyExistsException() {
		
		poolEqCtrl.setPoolId(123000L);
		poolEqCtrl.setCustomerPrimary6(customer.getCustomerNumber().substring(0, 6));
		
		when(customerRepo.existsByCustomerId(Mockito.any())).thenReturn(true);
		when(poolRepo.existsByPoolId(Mockito.any())).thenReturn(true);
		when(customerRepo.findById(Mockito.any())).thenReturn(Optional.of(customer));
		when(customerRepo.findByCustomerId(Mockito.any())).thenReturn(customer);
		when(conflictRepository.existsByControllerTypeAndCustomerPrimary6AndPoolControllerId(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(false);
		when(conflictRepository.findByControllerTypeAndCustomerPrimary6AndPoolControllerId(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(conflictControllerList);
		when(controllerRepo.existsByPoolIdAndEquipmentTypeAndCustomerPrimary6(Mockito.any(),Mockito.any(), Mockito.any())).thenReturn(true);
		RecordAlreadyExistsException exception2 = assertThrows(RecordAlreadyExistsException.class,
				() -> when(poolEqService.insertPoolCtrl(poolEqCtrl, header)));
		assertEquals("Record Already Exists under Pool Id: 123000 ,Equipment Type: C and Customer Primary 6: 123000", exception2.getMessage());
	}
	
	@Test
	void testNullPointerException() {
		header.put("extensionschema", null);
		assertNull(header.get(CommonConstants.EXTENSION_SCHEMA));
		poolEqCtrl.setPoolId(123000L);
		poolEqCtrl.setCustomerPrimary6(customer.getCustomerNumber().substring(0, 6));
		
		when(customerRepo.existsByCustomerId(Mockito.any())).thenReturn(true);
		when(poolRepo.existsByPoolId(Mockito.any())).thenReturn(true);
		when(customerRepo.findById(Mockito.any())).thenReturn(Optional.of(customer));
		when(customerRepo.findByCustomerId(Mockito.any())).thenReturn(customer);
		when(conflictRepository.existsByControllerTypeAndCustomerPrimary6AndPoolControllerId(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(false);
		when(conflictRepository.findByControllerTypeAndCustomerPrimary6AndPoolControllerId(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(conflictControllerList);
		when(controllerRepo.existsByEquipmentTypeAndCustomerPrimary6(Mockito.any(), Mockito.any())).thenReturn(false);
		NullPointerException exception = assertThrows(NullPointerException.class,
				() -> poolEqService.insertPoolCtrl(poolEqCtrl, header));
		assertEquals("Extension Schema should not be null, empty or blank", exception.getMessage());
	}

	@Test
	void testInsertPoolCtrl() {
		when(customerRepo.existsByCustomerId(Mockito.any())).thenReturn(true);
		when(poolRepo.existsByPoolId(Mockito.any())).thenReturn(true);
		when(customerRepo.findById(Mockito.any())).thenReturn(Optional.of(customer));
		when(customerRepo.findByCustomerId(Mockito.any())).thenReturn(customer);
		when(conflictRepository.existsByControllerTypeAndCustomerPrimary6AndPoolControllerId(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(false);
		when(conflictRepository.findByControllerTypeAndCustomerPrimary6AndPoolControllerId(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(conflictControllerList);
		when(controllerRepo.existsByEquipmentTypeAndCustomerPrimary6(Mockito.any(), Mockito.any())).thenReturn(false);
		when(controllerRepo.save(Mockito.any())).thenReturn(poolEqCtrl);
		PoolEquipmentController poolEq = poolEqService.insertPoolCtrl(poolEqCtrl, header);
		assertNotNull(poolEq);	
	}
	
	@Test
	void testUpdateConflictNoRecordFoundException() {
		poolEqCtrl.setPoolId(123000L);
		poolEqCtrl.setCustomerPrimary6(customer.getCustomerNumber().substring(0, 6));
		
		when(controllerRepo.findById(Mockito.any())).thenReturn(Optional.of(poolEqCtrl));
		when(controllerRepo.existsByPoolControllerIdAndUversion(Mockito.any(), Mockito.any())).thenReturn(true);
		when(poolRepo.existsByPoolId(Mockito.any())).thenReturn(true);
		when(conflictRepository.existsByControllerTypeAndCustomerPrimary6AndPoolControllerId(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
		when(conflictRepository.findByControllerTypeAndCustomerPrimary6AndPoolControllerId(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(conflictControllerList);
		RecordNotAddedException exception2 = assertThrows(RecordNotAddedException.class,
				() -> when(poolEqService.updatePoolCtrl(poolEqCtrl, header)));
		assertEquals("The Controller Conflict Identified on Pool Name - Equipment Type - Customer Primary 6 - Terminal : TEST - TEST - TEST - TEST", exception2.getMessage());
	}
	
	@Test
	void testUpdateConflictRecordAlreadyExistsException() {
		poolEqCtrl.setPoolId(123000L);
		poolEqCtrl.setCustomerPrimary6(customer.getCustomerNumber().substring(0, 6));
		
		when(controllerRepo.findById(Mockito.any())).thenReturn(Optional.of(poolEqCtrl));
		when(controllerRepo.existsByPoolControllerIdAndUversion(Mockito.any(), Mockito.any())).thenReturn(true);
		when(poolRepo.existsByPoolId(Mockito.any())).thenReturn(true);
		when(conflictRepository.existsByControllerTypeAndCustomerPrimary6AndPoolControllerId(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(false);
		when(conflictRepository.findByControllerTypeAndCustomerPrimary6AndPoolControllerId(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(conflictControllerList);
		when(controllerRepo.existsByPoolIdAndEquipmentTypeAndCustomerPrimary6(Mockito.any(),Mockito.any(), Mockito.any())).thenReturn(true);
		RecordAlreadyExistsException exception2 = assertThrows(RecordAlreadyExistsException.class,
				() -> when(poolEqService.updatePoolCtrl(poolEqCtrl, header)));
		assertEquals("Record Already Exists under Pool Id: 123000 ,Equipment Type: C and Customer Primary 6: 123000", exception2.getMessage());
	}

	@Test
	void testUpdatePoolCtrl() {
		when(controllerRepo.existsByPoolControllerIdAndUversion(Mockito.any(), Mockito.any())).thenReturn(true);
		when(poolRepo.existsByPoolId(Mockito.any())).thenReturn(true);
		when(conflictRepository.existsByControllerTypeAndCustomerPrimary6AndPoolControllerId(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(false);
		when(conflictRepository.findByControllerTypeAndCustomerPrimary6AndPoolControllerId(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(conflictControllerList);
		when(controllerRepo.existsByEquipmentTypeAndCustomerPrimary6(Mockito.any(), Mockito.any())).thenReturn(false);
		when(controllerRepo.findById(Mockito.any())).thenReturn(Optional.of(poolEqCtrl));
		when(controllerRepo.save(Mockito.any())).thenReturn(poolEqCtrl);
		PoolEquipmentController poolEq = poolEqService.updatePoolCtrl(poolEqCtrl, header);
		assertNotNull(poolEq);	
	}
	
	@Test
	void testUpdateNoRecordsFoundException() {
		
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> poolEqService.updatePoolCtrl(poolEqCtrl, header));
		assertEquals("No record Found Under this Pool Controller Id:123 and Uversion:null", exception.getMessage());
		
	}

	@Test
	void testDeletePoolCtrl() {
		when(controllerRepo.existsByPoolControllerIdAndUversion(Mockito.any(), Mockito.any())).thenReturn(true);
		when(controllerRepo.findById(Mockito.any())).thenReturn(Optional.of(poolEqCtrl));
		PoolEquipmentController poolEq = poolEqService.deletePoolCtrl(poolEqCtrl);
		assertNotNull(poolEq);
	}
	
	@Test
	void testDeleteRecordNotDeletedException() {
		RecordNotDeletedException exception = assertThrows(RecordNotDeletedException.class,
				() -> poolEqService.deletePoolCtrl(poolEqCtrl));
		assertEquals("No record Found Under this Pool Controller Id:123 and Uversion:null", exception.getMessage());
	}

}
