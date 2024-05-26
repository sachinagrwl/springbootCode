package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nscorp.obis.dto.mapper.DrayageCompanyMapper;
import com.nscorp.obis.exception.InvalidDataException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.CustomerInfo;
import com.nscorp.obis.domain.DrayageCompany;
import com.nscorp.obis.domain.DrayageCustomer;
import com.nscorp.obis.domain.DrayageCustomerInfo;
import com.nscorp.obis.dto.CustomerScacDTO;
import com.nscorp.obis.dto.DrayageCompanyDTO;
import com.nscorp.obis.dto.DrayageCustomerDTO;
import com.nscorp.obis.dto.DrayageCustomerInfoDTO;
import com.nscorp.obis.dto.DrayageCustomerListDTO;
import com.nscorp.obis.dto.mapper.DrayageCustomerInfoMapper;
import com.nscorp.obis.dto.mapper.DrayageCustomerMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.DrayageCompanyRepository;
import com.nscorp.obis.repository.DrayageCustomerRepository;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.TruckerMaintenanceServiceImpl;

public class TruckerMaintenanceControllerTest {

	@Mock
	DrayageCustomerMapper drayageCustomerMapper;

	@Mock
	DrayageCompanyMapper drayageCompanyMapper;
	@Mock
	DrayageCustomerInfoMapper drayageCustomerInfoMapper;

	@Mock
	DrayageCustomerRepository drayageCustomerRepository;
	
	@Mock
	DrayageCompanyRepository drayageCompanyRepository;

	@Mock
	TruckerMaintenanceServiceImpl truckerMaintenanceService;
    
	@Mock
	DrayageCustomerMapper mapper;
	@InjectMocks
	TruckerMaintenanceController truckerMaintenanceController;

	DrayageCustomer drayageCustomer;
	CustomerInfo customer;
	DrayageCustomerDTO drayageCustomerDto;
	DrayageCustomerInfo drayageCustomerInfo;
	DrayageCustomerInfoDTO drayageCustomerInfoDTO;
	List<DrayageCustomer> drayageCustomerList;
	List<DrayageCustomerDTO> drayageCustomerDtoList;
	DrayageCompany drayageCompany;
	List<DrayageCompany> drayageCompanyList;
	DrayageCompanyDTO drayageCompanyDto;
	List<DrayageCompanyDTO> drayageCompanyDtoList;
	List<DrayageCustomerInfoDTO> drayageCustomerInfoDTOs;
	DrayageCustomerListDTO drayageCustomerListDto;
	Map<String, String> header;
	List<CustomerScacDTO> customerScacDTOs;

	String drayageId;
	Long customerId;
	String customerName;
	String customerNumber;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		drayageCustomer = new DrayageCustomer();
		drayageCompany = new DrayageCompany();
		drayageCompanyDto = new DrayageCompanyDTO();
		drayageCustomerInfo = new DrayageCustomerInfo();
		drayageCustomerInfoDTO=new DrayageCustomerInfoDTO();
		customer = new CustomerInfo();
		drayageCustomerDto = new DrayageCustomerDTO();
		drayageCustomerListDto = new DrayageCustomerListDTO();
		drayageCustomerList = new ArrayList<>();
		drayageCustomerDtoList = new ArrayList<>();
		drayageCompanyList = new ArrayList<>();
		drayageCompanyDtoList = new ArrayList<>();
		drayageCustomerInfoDTOs = new ArrayList<>();
		customerName = "CASCO SERVICES INC";
		customerId = 1138L;
		drayageId = "AAGC";
		customer.setCustomerId(customerId);
		customerNumber = "1328610018";
		drayageCustomer.setDrayageId(drayageId);
		drayageCustomer.setCustomerName("TEST");
		drayageCustomer.setCustomer(customer);
		drayageCustomerDto.setDrayageId(drayageId);
		drayageCustomerDto.setCustomerName("TEST");
		drayageCustomerList.add(drayageCustomer);
		drayageCustomerDtoList.add(drayageCustomerDto);
		drayageCompany.setDrayageId(drayageId);
		drayageCompany.setTiaInd("S");
		drayageCompanyDto.setDrayageId(drayageId);
		drayageCompanyDto.setTiaInd("S");
		drayageCompanyList.add(drayageCompany);
		drayageCompanyDtoList.add(drayageCompanyDto);
		drayageCustomerInfo.setCustomerId(customerId);
		drayageCustomerInfo.setDrayageId(drayageId);
		drayageCustomerInfoDTO.setCustomerId(customerId);
		drayageCustomerInfoDTO.setDrayageId(drayageId);
		drayageCustomerInfoDTOs.add(drayageCustomerInfoDTO);
		drayageCustomerListDto.setDrayageId(drayageId);
		drayageCustomerListDto.setDrayageCustomerList(drayageCustomerInfoDTOs);
	}

	@AfterEach
	void tearDown() throws Exception {
		drayageCustomerInfoDTO=null;
	}

	@Test
	void testGetDrayageCustomers() throws SQLException {
		when(truckerMaintenanceService.fetchDrayageCustomers(Mockito.any(), Mockito.any())).thenReturn(drayageCustomerList);
		ResponseEntity<APIResponse<List<DrayageCustomerDTO>>> result = truckerMaintenanceController.getDrayageCustomers(customerId,drayageId);
		assertEquals(result.getStatusCodeValue(), 200);
	}
	
	@Test
	void testGetDrayageCustomersByPrimarySix() throws SQLException {
		when(truckerMaintenanceService.fetchDrayageCustomersByPrimarySix(Mockito.any())).thenReturn(customerScacDTOs);
		ResponseEntity<APIResponse<List<CustomerScacDTO>>> result = truckerMaintenanceController.getDrayageCustomersByPrimarySix(customerNumber);
		assertEquals(result.getStatusCodeValue(), 200);
	}

	@Test
	void testGetDrayageCompany() throws SQLException {
		when(truckerMaintenanceService.fetchDrayageCompany(Mockito.any())).thenReturn(drayageCompanyList);
		ResponseEntity<APIResponse<List<DrayageCompanyDTO>>> result = truckerMaintenanceController.getDrayageCompany(drayageId);
		assertEquals(result.getStatusCodeValue(), 200);
	}
	
	@Test
	void testAddDrayageCustomer() {
		when(drayageCustomerInfoMapper.drayageCustomerInfoToDrayageCustomerInfoDTO(Mockito.any()))
				.thenReturn(drayageCustomerInfoDTO);
		when(drayageCustomerInfoMapper.drayageCustomerInfoDtoToDrayageCustomerInfo(Mockito.any())).thenReturn(drayageCustomerInfo);
		when(truckerMaintenanceService.addDrayageCustomer(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(drayageCustomerInfo);
		ResponseEntity<APIResponse<DrayageCustomerInfoDTO>> added = truckerMaintenanceController
				.addDrayageCustomerInfo("N",drayageCustomerInfoDTO, header);
		assertEquals(added.getStatusCodeValue(), 200);
	}
	@Test
	void testAddDrayageCustomerList() {
		when(drayageCustomerInfoMapper.drayageCustomerInfoToDrayageCustomerInfoDTO(Mockito.any()))
				.thenReturn(drayageCustomerInfoDTO);
		when(drayageCustomerInfoMapper.drayageCustomerInfoDtoToDrayageCustomerInfo(Mockito.any())).thenReturn(drayageCustomerInfo);
		when(truckerMaintenanceService.addDrayageCustomerAndRemoveLink(Mockito.any(),Mockito.any(), Mockito.any()))
				.thenReturn(drayageCustomerInfo);
		ResponseEntity<List<APIResponse<DrayageCustomerInfoDTO>>> added = truckerMaintenanceController
				.addCustomersToDrayage(drayageCustomerListDto, header);
		assertEquals(added.getStatusCodeValue(), 200);
	}

	@Test
	void testDeleteDrayageCustomer() throws SQLException {
		when(truckerMaintenanceService.deleteDrayageCustomerInfo(Mockito.any())).thenReturn(drayageCustomerInfoDTOs);
		ResponseEntity<APIResponse<List<DrayageCustomerInfoDTO>>> result = truckerMaintenanceController.deleteDrayageCustomer(drayageCustomerInfoDTOs);
		assertEquals(result.getStatusCodeValue(), 200);
	}

	@Test
	void testAddDrayageCompany() {
		when(drayageCompanyMapper.drayageCompanyToDrayageCompanyDTO(Mockito.any())).thenReturn(drayageCompanyDto);
		when(drayageCompanyMapper.drayageCompanyDtoToDrayageCompany(Mockito.any())).thenReturn(drayageCompany);
		when(truckerMaintenanceService.addDrayageCompany(Mockito.any(),Mockito.any())).thenReturn(drayageCompany);
		ResponseEntity<APIResponse<DrayageCompanyDTO>> data = truckerMaintenanceController.addDrayageCompany(drayageCompanyDto, header);
		assertEquals(data.getStatusCodeValue(),200);
	}
	@Test
	void testUpdateDrayageCompany() {
		when(drayageCompanyMapper.drayageCompanyToDrayageCompanyDTO(Mockito.any())).thenReturn(drayageCompanyDto);
		when(drayageCompanyMapper.drayageCompanyDtoToDrayageCompany(Mockito.any())).thenReturn(drayageCompany);
		when(truckerMaintenanceService.updateDrayageCompany(Mockito.any(),Mockito.any())).thenReturn(drayageCompany);
		ResponseEntity<APIResponse<DrayageCompanyDTO>> data = truckerMaintenanceController.putDrayageCompany(drayageCompanyDto, header);
		assertEquals(data.getStatusCodeValue(),200);
	}

	@Test
	@DisplayName("NoRecordsFoundException")
	void testNoRecordsFoundException() throws SQLException {
		when(truckerMaintenanceService.fetchDrayageCustomers(Mockito.any(), Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<DrayageCustomerDTO>>> exception = truckerMaintenanceController.getDrayageCustomers(customerId,drayageId);
		assertEquals(exception.getStatusCodeValue(), 404);
		when(truckerMaintenanceService.fetchDrayageCompany(Mockito.any()))
		        .thenThrow(new NoRecordsFoundException());
        ResponseEntity<APIResponse<List<DrayageCompanyDTO>>> exception1 = truckerMaintenanceController.getDrayageCompany(drayageId);
		assertEquals(exception1.getStatusCodeValue(), 404);
		when(truckerMaintenanceService.deleteDrayageCustomerInfo(Mockito.any())).thenThrow(NoRecordsFoundException.class);
		ResponseEntity<APIResponse<List<DrayageCustomerInfoDTO>>> result = truckerMaintenanceController.deleteDrayageCustomer(drayageCustomerInfoDTOs);
		assertEquals(result.getStatusCodeValue(), 404);

		when(truckerMaintenanceService.addDrayageCompany(Mockito.any(), Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<DrayageCompanyDTO>> addException = truckerMaintenanceController.addDrayageCompany(drayageCompanyDto,header);
		assertEquals(addException.getStatusCodeValue(), 404);
		when(truckerMaintenanceService.updateDrayageCompany(Mockito.any(), Mockito.any()))
		.thenThrow(new NoRecordsFoundException());
        ResponseEntity<APIResponse<DrayageCompanyDTO>> updateException = truckerMaintenanceController.putDrayageCompany(drayageCompanyDto,header);
           assertEquals(updateException.getStatusCodeValue(), 404);
		when(truckerMaintenanceService.addDrayageCustomer(Mockito.any(), Mockito.any(),Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<DrayageCustomerInfoDTO>> exception2 = truckerMaintenanceController.addDrayageCustomerInfo("N",drayageCustomerInfoDTO,header);
		assertEquals(exception2.getStatusCodeValue(), 404);
		when(truckerMaintenanceService.fetchDrayageCustomersByPrimarySix(Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<CustomerScacDTO>>> result2 = truckerMaintenanceController.getDrayageCustomersByPrimarySix(customerNumber);
		assertEquals(result2.getStatusCodeValue(), 404);
		
	}

	@Test
	@DisplayName("Internal Server Error")
	void testException() throws SQLException {
		when(truckerMaintenanceService.fetchDrayageCustomers(Mockito.any(), Mockito.any()))
				.thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<DrayageCustomerDTO>>> exception = truckerMaintenanceController.getDrayageCustomers(customerId,drayageId);
		assertEquals(exception.getStatusCodeValue(), 500);
		when(truckerMaintenanceService.fetchDrayageCompany(Mockito.any()))
                .thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<List<DrayageCompanyDTO>>> exception1 = truckerMaintenanceController.getDrayageCompany(drayageId);
        assertEquals(exception1.getStatusCodeValue(), 500);
        when(truckerMaintenanceService.deleteDrayageCustomerInfo(Mockito.any())).thenThrow(RuntimeException.class);
		ResponseEntity<APIResponse<List<DrayageCustomerInfoDTO>>> result = truckerMaintenanceController.deleteDrayageCustomer(drayageCustomerInfoDTOs);
		assertEquals(result.getStatusCodeValue(), 500);
		when(truckerMaintenanceService.addDrayageCompany(Mockito.any(),Mockito.any()))
				.thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<DrayageCompanyDTO>> addException = truckerMaintenanceController.addDrayageCompany(drayageCompanyDto,header);
		assertEquals(addException.getStatusCodeValue(), 500);
		when(truckerMaintenanceService.updateDrayageCompany(Mockito.any(),Mockito.any()))
		.thenThrow(new RuntimeException());
         ResponseEntity<APIResponse<DrayageCompanyDTO>> ex = truckerMaintenanceController.putDrayageCompany(drayageCompanyDto,header);
         assertEquals(ex.getStatusCodeValue(), 500);
         when(truckerMaintenanceService.fetchDrayageCustomersByPrimarySix(Mockito.any())).thenThrow(new RuntimeException());
 		ResponseEntity<APIResponse<List<CustomerScacDTO>>> result2 = truckerMaintenanceController.getDrayageCustomersByPrimarySix(customerNumber);
 		assertEquals(result2.getStatusCodeValue(), 500);
	}

	@Test
	void testInvalidDataException() {
		when(truckerMaintenanceService.addDrayageCustomer(Mockito.any(), Mockito.any(),Mockito.any()))
				.thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<DrayageCustomerInfoDTO>> exception2 = truckerMaintenanceController.addDrayageCustomerInfo("N",drayageCustomerInfoDTO,header);
		assertEquals(exception2.getStatusCodeValue(), 500);
		
	}

	@Test
	@DisplayName("RecordAlreadyExistsException")
	void testRecordAlreadyExistsException() throws SQLException {
		when(truckerMaintenanceService.addDrayageCustomer(Mockito.any(), Mockito.any(),Mockito.any()))
				.thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<DrayageCustomerInfoDTO>> exception = truckerMaintenanceController.addDrayageCustomerInfo("N",drayageCustomerInfoDTO,header);
		assertEquals(exception.getStatusCodeValue(), 404);
		
	}

	@Test
	@DisplayName("RecordNotAddedException")
	void testRecordNotAddedException() throws SQLException {
		when(truckerMaintenanceService.addDrayageCustomer(Mockito.any(), Mockito.any(),Mockito.any()))
				.thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<DrayageCustomerInfoDTO>> exception = truckerMaintenanceController.addDrayageCustomerInfo("N",drayageCustomerInfoDTO,header);
		assertEquals(exception.getStatusCodeValue(), 404);
		when(truckerMaintenanceService.addDrayageCustomerAndRemoveLink(Mockito.any(),Mockito.any(), Mockito.any()))
		.thenThrow(new RecordNotAddedException());
        ResponseEntity<List<APIResponse<DrayageCustomerInfoDTO>>> exception2 = truckerMaintenanceController.addCustomersToDrayage(drayageCustomerListDto,header);
        assertEquals(exception2.getStatusCodeValue(), 404);
	}

	@Test
	@DisplayName("RecordNotAddedException")
	void testRecordNotAddedExceptionForCompany() throws SQLException {
		when(truckerMaintenanceService.addDrayageCompany(Mockito.any(), Mockito.any()))
				.thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<DrayageCompanyDTO>> exception = truckerMaintenanceController.addDrayageCompany(drayageCompanyDto,header);
		assertEquals(exception.getStatusCodeValue(), 406);

	}
	@Test
	@DisplayName("InvalidDataException")
	void testInvalidException() throws SQLException {
		when(truckerMaintenanceService.updateDrayageCompany(Mockito.any(), Mockito.any()))
		.thenThrow(new InvalidDataException());
        ResponseEntity<APIResponse<DrayageCompanyDTO>> exception2 = truckerMaintenanceController.putDrayageCompany(drayageCompanyDto,header);
         assertEquals(exception2.getStatusCodeValue(), 406);
	}

	@Test
	@DisplayName("InvalidDataException")
	void testInvalidExceptionForAddCompany() throws SQLException {
		when(truckerMaintenanceService.addDrayageCompany(Mockito.any(), Mockito.any()))
				.thenThrow(new InvalidDataException(Mockito.any()));
		ResponseEntity<APIResponse<DrayageCompanyDTO>> exception1 = truckerMaintenanceController.addDrayageCompany(drayageCompanyDto,header);
		assertEquals(exception1.getStatusCodeValue(), 500);

	}
}
