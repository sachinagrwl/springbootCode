package com.nscorp.obis.services;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


import com.nscorp.obis.repository.CustomerRepository;
import com.nscorp.obis.repository.TerminalRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.domain.CustomerPerDiemRateSelection;
import com.nscorp.obis.dto.CustomerPerDiemRateSelectionDTO;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.CustomerInfoRepository;
import com.nscorp.obis.repository.CustomerPerDiemRateSelectionRepository;

public class CustomerPerDiemRateSelectionServiceTest {

	@Mock
	CustomerPerDiemRateSelectionRepository repository;

	@Mock
	CustomerInfoRepository custInfoRepo;

	@Mock
	CustomerRepository custRepo;

	@Mock
	TerminalRepository terminalRepo;

	@InjectMocks
	CustomerPerDiemRateSelectionServiceImpl serviceImpl;

	CustomerPerDiemRateSelectionDTO customerPerDiemRateSelectionDTO;

	CustomerPerDiemRateSelection customerPerDiemRateSelection;
	List<CustomerPerDiemRateSelection> customerPerDiemRateSelectionList;
	List<CustomerPerDiemRateSelectionDTO> customerPerDiemRateSelectionDTOList;
	Optional<CustomerPerDiemRateSelection> optional;
	String custPrimSix;
	Map<String, String> headers;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		custPrimSix = "109957";
		customerPerDiemRateSelectionList = new ArrayList<>();
		customerPerDiemRateSelectionDTO = new CustomerPerDiemRateSelectionDTO();
		customerPerDiemRateSelection = new CustomerPerDiemRateSelection();
		customerPerDiemRateSelectionDTO.setPerDiemId(1234L);
		customerPerDiemRateSelection.setPerDiemId(1234L);
		customerPerDiemRateSelectionDTO.setCustomerName("Test Cust");
		customerPerDiemRateSelectionDTO.setCustPrimSix("123456");
		customerPerDiemRateSelection.setCustomerName("Test Cust");
		customerPerDiemRateSelection.setCustPrimSix("123456");

		customerPerDiemRateSelectionDTO.setEffectiveDate(new Date(02-20-2023));
		customerPerDiemRateSelection.setEffectiveDate(new Date(02-20-2023));
		customerPerDiemRateSelectionList.add(customerPerDiemRateSelection);
		optional = Optional.of(customerPerDiemRateSelection);
		headers = new HashMap<>();
		headers.put("userid", "test");
		headers.put("extensionschema", "test");
	}

	@AfterEach
	void tearDown() {
		customerPerDiemRateSelectionDTO = null;
		customerPerDiemRateSelection = null;
		headers = null;
	}

	@Test
	void testFetchCustomerPerDiemRate() throws SQLException {
		when(repository.findByCustPrimSix(custPrimSix)).thenReturn(customerPerDiemRateSelectionList);
		List<CustomerPerDiemRateSelection> customerPerDiemRateSelection = serviceImpl.fetchCustomerPerDiemRate(custPrimSix);
		assertNotNull(customerPerDiemRateSelection);

	}

	@Test
	void testFetchCustomerPerDiemRateException() {

		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(serviceImpl.fetchCustomerPerDiemRate(custPrimSix)));
		assertEquals("No Records found for this Customer Primsix.", exception.getMessage());
	}

	@Test
	void testUpdateCustomerPerDiemRate() throws SQLException {
		customerPerDiemRateSelectionDTO.setPerDiemId(null);
		customerPerDiemRateSelectionDTO.setFreeDayLimit(12);
		customerPerDiemRateSelectionDTO.setRateDayLimit(13);

		assertThrows(InvalidDataException.class,
				() -> serviceImpl.updateCustomerPerDiemRate(customerPerDiemRateSelectionDTO, headers));
		when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		customerPerDiemRateSelectionDTO.setPerDiemId(1234L);
		assertThrows(NoRecordsFoundException.class,
				() -> serviceImpl.updateCustomerPerDiemRate(customerPerDiemRateSelectionDTO, headers));
		when(repository.findById(Mockito.anyLong())).thenReturn(optional);
		when(repository.save(Mockito.any())).thenReturn(customerPerDiemRateSelection);
		customerPerDiemRateSelectionDTO.setBillCustomerId(1234L);
		when(custInfoRepo.existsById(Mockito.anyLong())).thenReturn(true);
		CustomerPerDiemRateSelectionDTO temp = serviceImpl.updateCustomerPerDiemRate(customerPerDiemRateSelectionDTO,
				headers);
		assertEquals(customerPerDiemRateSelection.getPerDiemId(), temp.getPerDiemId());
	}

	@Test
	void testAddCustomerPerDiemRate() throws SQLException {
		customerPerDiemRateSelection.setEquipTp("C");
		customerPerDiemRateSelectionDTO.setEquipTp("C");
		customerPerDiemRateSelection.setIngateLoadEmptyStatus("L");
		customerPerDiemRateSelectionDTO.setIngateLoadEmptyStatus("L");
		customerPerDiemRateSelection.setOutgateLoadEmptyStatus("L");
		customerPerDiemRateSelectionDTO.setOutgateLoadEmptyStatus("L");
		customerPerDiemRateSelectionDTO.setUversion("1");
		customerPerDiemRateSelectionDTO.setBeneficialCustomerName("Test");
		customerPerDiemRateSelectionDTO.setBeneficialPrimSix("001234");
		customerPerDiemRateSelectionDTO.setShipCustomerName("Test");
		customerPerDiemRateSelectionDTO.setShipPrimSix("001234");
		customerPerDiemRateSelectionDTO.setEffectiveDate(new Date(02-20-2023));
		customerPerDiemRateSelection.setEffectiveDate(new Date(02-20-2023));
		customerPerDiemRateSelectionDTO.setEndDate(new Date(02-19-2023));
		customerPerDiemRateSelection.setEndDate(new Date(02-19-2023));

		customerPerDiemRateSelection.setFreeDayLimit(7);
		customerPerDiemRateSelectionDTO.setFreeDayLimit(7);
		customerPerDiemRateSelection.setRateDayLimit(8);
		customerPerDiemRateSelectionDTO.setRateDayLimit(8);

		customerPerDiemRateSelection.setRateType("H");
		customerPerDiemRateSelectionDTO.setRateType("H");

		customerPerDiemRateSelection.setCountWeekend("Y");
		customerPerDiemRateSelectionDTO.setCountWeekend("Y");

		customerPerDiemRateSelection.setInitialRate(BigDecimal.valueOf(3.14));
		customerPerDiemRateSelectionDTO.setInitialRate(BigDecimal.valueOf(3.14));

		customerPerDiemRateSelection.setSecondaryRate(BigDecimal.valueOf(3.14));
		customerPerDiemRateSelectionDTO.setSecondaryRate(BigDecimal.valueOf(3.14));

		headers.put("extensionschema", null);
		List<String> customerList = new ArrayList<>();
		customerList.add("AMERICAN FIBRE SUPPLY");
		when(repository.existsByCustPrimSixAndTerminalIdAndBeneficialPrimSixAndShipPrimSixAndOutgateLoadEmptyStatusAndIngateLoadEmptyStatusAndEquipTp(Mockito.anyString(),Mockito.anyLong(),Mockito.anyString(),
				Mockito.anyString(),Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(false);
		when(custInfoRepo.checkByCustomerNameAndCustomerPrimarySix(Mockito.anyString(),Mockito.anyString())).thenReturn(customerList);
		customerPerDiemRateSelectionDTO.setTerminalId(1234L);
		when(terminalRepo.existsById(customerPerDiemRateSelectionDTO.getTerminalId())).thenReturn(true);

		when(repository.save(Mockito.any())).thenReturn(customerPerDiemRateSelection);
		customerPerDiemRateSelectionDTO.setTerminalId(null);
		CustomerPerDiemRateSelectionDTO added = serviceImpl.addCustomerPerDiemRate(customerPerDiemRateSelectionDTO, headers);
		assertEquals(customerPerDiemRateSelection.getPerDiemId(), added.getPerDiemId());
	}


	@SuppressWarnings("deprecation")
	@Test
	void testUpdateCustomerPerDiemRateException() throws SQLException {
		when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		when(repository.findById(Mockito.anyLong())).thenReturn(optional);
		customerPerDiemRateSelectionDTO.setCustomerName("test");
		assertThrows(InvalidDataException.class,
				() -> serviceImpl.updateCustomerPerDiemRate(customerPerDiemRateSelectionDTO, headers));
		customerPerDiemRateSelectionDTO.setCustomerName("Test Cust");
		customerPerDiemRateSelectionDTO.setCustPrimSix("1234");
		assertThrows(InvalidDataException.class,
				() -> serviceImpl.updateCustomerPerDiemRate(customerPerDiemRateSelectionDTO, headers));
		customerPerDiemRateSelectionDTO.setCustPrimSix("123456");
		customerPerDiemRateSelectionDTO.setBeneficialPrimSix("123");
		assertThrows(InvalidDataException.class,
				() -> serviceImpl.updateCustomerPerDiemRate(customerPerDiemRateSelectionDTO, headers));
		customerPerDiemRateSelectionDTO.setBeneficialPrimSix("123456");
		customerPerDiemRateSelectionDTO.setBeneficialCustomerName("test");
		assertThrows(InvalidDataException.class,
				() -> serviceImpl.updateCustomerPerDiemRate(customerPerDiemRateSelectionDTO, headers));
		customerPerDiemRateSelectionDTO.setBeneficialCustomerName(null);
		customerPerDiemRateSelectionDTO.setBeneficialPrimSix("12345");
		assertThrows(InvalidDataException.class,
				() -> serviceImpl.updateCustomerPerDiemRate(customerPerDiemRateSelectionDTO, headers));
		customerPerDiemRateSelectionDTO.setBeneficialPrimSix(null);
		customerPerDiemRateSelectionDTO.setShipCustomerName("Test");
		assertThrows(InvalidDataException.class,
				() -> serviceImpl.updateCustomerPerDiemRate(customerPerDiemRateSelectionDTO, headers));
		customerPerDiemRateSelectionDTO.setShipCustomerName(null);
		customerPerDiemRateSelectionDTO.setShipPrimSix("123456");
		assertThrows(InvalidDataException.class,
				() -> serviceImpl.updateCustomerPerDiemRate(customerPerDiemRateSelectionDTO, headers));
		customerPerDiemRateSelectionDTO.setShipPrimSix(null);
		customerPerDiemRateSelectionDTO.setEquipTp("M");
		assertThrows(InvalidDataException.class,
				() -> serviceImpl.updateCustomerPerDiemRate(customerPerDiemRateSelectionDTO, headers));
		customerPerDiemRateSelectionDTO.setEquipTp(null);
		customerPerDiemRateSelectionDTO.setIngateLoadEmptyStatus("Y");
		assertThrows(InvalidDataException.class,
				() -> serviceImpl.updateCustomerPerDiemRate(customerPerDiemRateSelectionDTO, headers));
		customerPerDiemRateSelectionDTO.setIngateLoadEmptyStatus(null);
		customerPerDiemRateSelectionDTO.setOutgateLoadEmptyStatus("Y");
		assertThrows(InvalidDataException.class,
				() -> serviceImpl.updateCustomerPerDiemRate(customerPerDiemRateSelectionDTO, headers));
		customerPerDiemRateSelectionDTO.setOutgateLoadEmptyStatus(null);
		customerPerDiemRateSelectionDTO.setTerminalId(123L);
		assertThrows(InvalidDataException.class,
				() -> serviceImpl.updateCustomerPerDiemRate(customerPerDiemRateSelectionDTO, headers));
		customerPerDiemRateSelectionDTO.setTerminalId(null);
		customerPerDiemRateSelectionDTO.setEffectiveDate(new Date(02-21-2023));
		assertThrows(InvalidDataException.class,
				() -> serviceImpl.updateCustomerPerDiemRate(customerPerDiemRateSelectionDTO, headers));
		customerPerDiemRateSelectionDTO.setEffectiveDate(new Date(02-20-2023));
		customerPerDiemRateSelectionDTO.setEndDate(new Date(02-20-2023));
		assertThrows(InvalidDataException.class,
				() -> serviceImpl.updateCustomerPerDiemRate(customerPerDiemRateSelectionDTO, headers));
		customerPerDiemRateSelectionDTO.setEndDate(new Date(02-19-2023));
		customerPerDiemRateSelectionDTO.setUversion("1");
		headers.put("extensionschema", null);
		customerPerDiemRateSelectionDTO.setBillCustomerId(1234L);
		when(custInfoRepo.existsById(Mockito.anyLong())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class,
				() -> serviceImpl.updateCustomerPerDiemRate(customerPerDiemRateSelectionDTO, headers));

		when(custInfoRepo.existsById(Mockito.anyLong())).thenReturn(true);
		customerPerDiemRateSelectionDTO.setFreeDayLimit(7);
		customerPerDiemRateSelectionDTO.setEffectiveDate(null);
		assertThrows(InvalidDataException.class,
				() -> serviceImpl.updateCustomerPerDiemRate(customerPerDiemRateSelectionDTO, headers));

		customerPerDiemRateSelectionDTO.setFreeDayLimit(128);
		customerPerDiemRateSelectionDTO.setEffectiveDate(new Date(02-20-2023));
		assertThrows(InvalidDataException.class,
				() -> serviceImpl.updateCustomerPerDiemRate(customerPerDiemRateSelectionDTO, headers));

		customerPerDiemRateSelectionDTO.setFreeDayLimit(12);
		customerPerDiemRateSelectionDTO.setRateDayLimit(128);
		assertThrows(InvalidDataException.class,
				() -> serviceImpl.updateCustomerPerDiemRate(customerPerDiemRateSelectionDTO, headers));

		customerPerDiemRateSelectionDTO.setRateDayLimit(11);
		assertThrows(InvalidDataException.class,
				() -> serviceImpl.updateCustomerPerDiemRate(customerPerDiemRateSelectionDTO, headers));




	}



	@Test
	void testAddCustomerPerDiemRateException() throws SQLException{
		List<String> list = new ArrayList<>();
		when(custInfoRepo.checkByCustomerNameAndCustomerPrimarySix(Mockito.any(),Mockito.any())).thenReturn(list);
		InvalidDataException exception = assertThrows(InvalidDataException.class,
				() -> when(serviceImpl.addCustomerPerDiemRate(customerPerDiemRateSelectionDTO,headers)));
		assertEquals(exception.getMessage(),"No Customer  Found with this customer name  :" + customerPerDiemRateSelectionDTO.getCustomerName() + " And customer primary six : " + customerPerDiemRateSelectionDTO.getCustPrimSix());

		when(repository.existsByCustPrimSixAndTerminalIdAndBeneficialPrimSixAndShipPrimSixAndOutgateLoadEmptyStatusAndIngateLoadEmptyStatusAndEquipTp(
				Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(true);
		InvalidDataException exception2 = assertThrows(InvalidDataException.class,
				() -> when(serviceImpl.addCustomerPerDiemRate(customerPerDiemRateSelectionDTO,headers)));
		assertEquals(exception2.getMessage(),"Association already exist for given combination");

		when(repository.existsByCustPrimSixAndTerminalIdAndBeneficialPrimSixAndShipPrimSixAndOutgateLoadEmptyStatusAndIngateLoadEmptyStatusAndEquipTp(
				Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(false);
		list.add("AMERICAN FIBRE SUPPLY");
		when(custInfoRepo.checkByCustomerNameAndCustomerPrimarySix(Mockito.any(),Mockito.any())).thenReturn(list);

		customerPerDiemRateSelectionDTO.setEquipTp("Y");
		InvalidDataException exception3 = assertThrows(InvalidDataException.class,
				() -> when(serviceImpl.addCustomerPerDiemRate(customerPerDiemRateSelectionDTO,headers)));
		assertEquals(exception3.getMessage(),"Only accepted EQ_TP values for Per Diem Rate are C= Container, T=Trailer, Z = Chassis");

		customerPerDiemRateSelectionDTO.setEquipTp("C");
		customerPerDiemRateSelectionDTO.setIngateLoadEmptyStatus("R");
		InvalidDataException exception4 = assertThrows(InvalidDataException.class,
				() -> when(serviceImpl.addCustomerPerDiemRate(customerPerDiemRateSelectionDTO,headers)));
		assertEquals(exception4.getMessage(),"Only accepted IngateLoadEmptyStatus values for Per Diem Rate are L= Load, E=Empty");

		customerPerDiemRateSelectionDTO.setIngateLoadEmptyStatus("E");
		customerPerDiemRateSelectionDTO.setOutgateLoadEmptyStatus("O");
		InvalidDataException exception5 = assertThrows(InvalidDataException.class,
				() -> when(serviceImpl.addCustomerPerDiemRate(customerPerDiemRateSelectionDTO,headers)));
		assertEquals(exception5.getMessage(),"Only accepted OutgateLoadEmptyStatus values for Per Diem Rate are L= Load, E=Empty");
//
		customerPerDiemRateSelectionDTO.setOutgateLoadEmptyStatus("L");
		customerPerDiemRateSelectionDTO.setTerminalId(123L);
		when(terminalRepo.existsById(Mockito.anyLong())).thenReturn(false);
		NoRecordsFoundException exception6 = assertThrows(NoRecordsFoundException.class,
				() -> when(serviceImpl.addCustomerPerDiemRate(customerPerDiemRateSelectionDTO,headers)));
		assertEquals(exception6.getMessage(),"No Terminal Found with this terminal id :" + customerPerDiemRateSelectionDTO.getTerminalId());

		customerPerDiemRateSelectionDTO.setTerminalId(null);
		customerPerDiemRateSelectionDTO.setCustomerName(null);
		customerPerDiemRateSelectionDTO.setBeneficialCustomerName("Test");
		customerPerDiemRateSelectionDTO.setBeneficialPrimSix("001234");
		list = new ArrayList<>();
		when(custInfoRepo.checkByCustomerNameAndCustomerPrimarySix(Mockito.any(),Mockito.any())).thenReturn(list);
		NoRecordsFoundException exception7 = assertThrows(NoRecordsFoundException.class,
				() -> when(serviceImpl.addCustomerPerDiemRate(customerPerDiemRateSelectionDTO,headers)));
		assertEquals(exception7.getMessage(),"No Beneficial Customer  Found with this Beneficial customer name  :" + customerPerDiemRateSelectionDTO.getBeneficialCustomerName() + " And Beneficial customer primary six : " + customerPerDiemRateSelectionDTO.getBeneficialPrimSix());


		customerPerDiemRateSelectionDTO.setBeneficialCustomerName(null);
		customerPerDiemRateSelectionDTO.setShipCustomerName("Test");
		customerPerDiemRateSelectionDTO.setShipPrimSix("001234");

		when(custInfoRepo.checkByCustomerNameAndCustomerPrimarySix(Mockito.any(),Mockito.any())).thenReturn(list);
		NoRecordsFoundException exception8 = assertThrows(NoRecordsFoundException.class,
				() -> when(serviceImpl.addCustomerPerDiemRate(customerPerDiemRateSelectionDTO,headers)));
		assertEquals(exception8.getMessage(),"No Ship Customer  Found with this Ship customer name  :" + customerPerDiemRateSelectionDTO.getShipCustomerName() + " And Ship customer primary six : " + customerPerDiemRateSelectionDTO.getShipPrimSix());


		customerPerDiemRateSelectionDTO.setShipCustomerName(null);
		customerPerDiemRateSelectionDTO.setBillCustomerId(1234L);
		when(custInfoRepo.existsById(Mockito.anyLong())).thenReturn(false);
		NoRecordsFoundException exception9 = assertThrows(NoRecordsFoundException.class,
				() -> serviceImpl.addCustomerPerDiemRate(customerPerDiemRateSelectionDTO, headers));
		assertEquals(exception9.getMessage(),"No Record found for given customer id " + customerPerDiemRateSelectionDTO.getBillCustomerId());

		when(custInfoRepo.existsById(Mockito.anyLong())).thenReturn(true);

		customerPerDiemRateSelectionDTO.setFreeDayLimit(7);

		customerPerDiemRateSelectionDTO.setEffectiveDate(null);
		InvalidDataException exception10 = assertThrows(InvalidDataException.class,
				() -> serviceImpl.addCustomerPerDiemRate(customerPerDiemRateSelectionDTO, headers));
		assertEquals(exception10.getMessage(),"Effective Date field is required");

		customerPerDiemRateSelectionDTO.setEffectiveDate(new Date(02-20-2023));
		customerPerDiemRateSelectionDTO.setEndDate(new Date(02-20-2023));
		customerPerDiemRateSelectionDTO.setUversion("!");
		headers.put("extensionschema", null);
		InvalidDataException exception11 = assertThrows(InvalidDataException.class,
				() -> serviceImpl.addCustomerPerDiemRate(customerPerDiemRateSelectionDTO, headers));
		assertEquals(exception11.getMessage(),"End date should be later than effective date");


		customerPerDiemRateSelectionDTO.setFreeDayLimit(128);
		customerPerDiemRateSelectionDTO.setEffectiveDate(new Date(02-20-2023));
		customerPerDiemRateSelectionDTO.setEndDate(new Date(02-19-2023));
		assertThrows(InvalidDataException.class,
				() -> serviceImpl.addCustomerPerDiemRate(customerPerDiemRateSelectionDTO, headers));

		customerPerDiemRateSelectionDTO.setFreeDayLimit(12);
		customerPerDiemRateSelectionDTO.setRateDayLimit(128);
		assertThrows(InvalidDataException.class,
				() -> serviceImpl.addCustomerPerDiemRate(customerPerDiemRateSelectionDTO, headers));

		customerPerDiemRateSelectionDTO.setRateDayLimit(11);
		assertThrows(InvalidDataException.class,
				() -> serviceImpl.addCustomerPerDiemRate(customerPerDiemRateSelectionDTO, headers));

	}

}
