package com.nscorp.obis.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.CorporateCustomer;
import com.nscorp.obis.dto.CorporateCustomerDTO;
import com.nscorp.obis.dto.mapper.CorporateCustomerMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.CorporateCustomerMaintenanceService;

class CorporateCustomerMaintenanceControllerTest {
	@Mock
	CorporateCustomerMapper corporateCustomerMapper;
	@Mock
	CorporateCustomerMaintenanceService corporateCustomerMaintenanceService;
	@InjectMocks
	CorporateCustomerMaintenanceController corporateCustomerMaintenanceController;
	CorporateCustomer corporateCustomerMaintenance;
	CorporateCustomerDTO corporateCustomerDto;
	List<CorporateCustomerDTO> corporateCustomerDtoList;
	List<CorporateCustomer> corporateCustomerMaintenances;
	Map<String, String> header;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);

		corporateCustomerMaintenance = new CorporateCustomer();
		corporateCustomerMaintenance.setCorporateCustomerId(Long.valueOf(1));
		corporateCustomerMaintenances = new ArrayList<>();
		corporateCustomerMaintenances.add(corporateCustomerMaintenance);
		corporateCustomerMaintenance.setCorporateCustomerId(Long.valueOf(2));
		corporateCustomerMaintenances.add(corporateCustomerMaintenance);
		corporateCustomerDto = new CorporateCustomerDTO();
		corporateCustomerDto.setCorporateCustomerId(Long.valueOf(1));
		corporateCustomerDtoList = new ArrayList<>();
		corporateCustomerDtoList.add(corporateCustomerDto);
		corporateCustomerDto.setCorporateCustomerId(Long.valueOf(2));
		corporateCustomerDtoList.add(corporateCustomerDto);

	}

	@Test
	void testGetCorporateCustomers() {
		when(corporateCustomerMaintenanceService.getCorporateCustomerData()).thenReturn(corporateCustomerMaintenances);
		when(corporateCustomerMapper.corporateCustomerToCorporateCustomerDTO(new CorporateCustomer()))
				.thenReturn(new CorporateCustomerDTO());
		ResponseEntity<APIResponse<List<CorporateCustomerDTO>>> result = corporateCustomerMaintenanceController
				.getCorporateCustomers();
		Assertions.assertEquals(200, result.getStatusCodeValue());
		when(corporateCustomerMaintenanceService.getCorporateCustomerData()).thenReturn(null);
		result = corporateCustomerMaintenanceController.getCorporateCustomers();
		Assertions.assertEquals(200, result.getStatusCodeValue());
		when(corporateCustomerMaintenanceService.getCorporateCustomerData()).thenReturn(Collections.EMPTY_LIST);
		result = corporateCustomerMaintenanceController.getCorporateCustomers();
		Assertions.assertEquals(200, result.getStatusCodeValue());
	}

	@Test
	void testGetCorporateCustomersNoRecordFound() {
		when(corporateCustomerMaintenanceService.getCorporateCustomerData()).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<CorporateCustomerDTO>>> result = corporateCustomerMaintenanceController
				.getCorporateCustomers();
		Assertions.assertEquals(404, result.getStatusCodeValue());

		when(corporateCustomerMaintenanceService.updateCorporateCustomer(Mockito.any(), Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<CorporateCustomerDTO>> corporateCustomerListGet1 = corporateCustomerMaintenanceController
				.updateCorporateCustomer(corporateCustomerDto, header);
		assertEquals(corporateCustomerListGet1.getStatusCodeValue(), 404);
		
		when(corporateCustomerMaintenanceService.addCorporateCustomer(Mockito.any(), Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<CorporateCustomerDTO>> corporateCustomerListAdd = corporateCustomerMaintenanceController
				.addCustomer(corporateCustomerDto, header);
		assertEquals(corporateCustomerListAdd.getStatusCodeValue(), 404);
	}

	@Test
	void testGetCorporateCustomersException() {
		when(corporateCustomerMaintenanceService.getCorporateCustomerData()).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<CorporateCustomerDTO>>> result = corporateCustomerMaintenanceController
				.getCorporateCustomers();
		Assertions.assertEquals(500, result.getStatusCodeValue());

		when(corporateCustomerMaintenanceService.updateCorporateCustomer(Mockito.any(), Mockito.any()))
				.thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<CorporateCustomerDTO>> corporateCustomerListGet1 = corporateCustomerMaintenanceController
				.updateCorporateCustomer(corporateCustomerDto, header);
		assertEquals(corporateCustomerListGet1.getStatusCodeValue(), 500);
		
		when(corporateCustomerMaintenanceService.addCorporateCustomer(Mockito.any(), Mockito.any()))
			.thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<CorporateCustomerDTO>> corporateCustomerListAdd = corporateCustomerMaintenanceController
				.addCustomer(corporateCustomerDto, header);
		assertEquals(corporateCustomerListAdd.getStatusCodeValue(), 500);
	}

	@Test
	@DisplayName("Update Corporate Customers")
	void testUpdateCorporateCustomers() {
		when(corporateCustomerMaintenanceService.updateCorporateCustomer(corporateCustomerMaintenance, header))
				.thenReturn(corporateCustomerMaintenance);
		ResponseEntity<APIResponse<CorporateCustomerDTO>> updateCorporateCustomers = corporateCustomerMaintenanceController
				.updateCorporateCustomer(corporateCustomerDto, header);
		assertEquals(updateCorporateCustomers.getStatusCodeValue(), 200);
	}
	
	@Test
	void testAddCorporateCustomers() {
		when(corporateCustomerMaintenanceService.addCorporateCustomer(corporateCustomerMaintenance, header))
				.thenReturn(corporateCustomerMaintenance);
		ResponseEntity<APIResponse<CorporateCustomerDTO>> addCorporateCustomers = corporateCustomerMaintenanceController
				.addCustomer(corporateCustomerDto, header);
		assertEquals(addCorporateCustomers.getStatusCodeValue(), 200);
	}

	@Test
	@DisplayName("Record Already Exists")
	void testCorporateCustomersRecordAlreadyExistsException() {
		when(corporateCustomerMaintenanceService.updateCorporateCustomer(Mockito.any(), Mockito.any()))
				.thenThrow(new RecordAlreadyExistsException());
		when(corporateCustomerMaintenanceService.addCorporateCustomer(Mockito.any(), Mockito.any()))
		.thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<CorporateCustomerDTO>> corporateCustomerListGet = corporateCustomerMaintenanceController
				.updateCorporateCustomer(corporateCustomerDto, header);
		assertEquals(corporateCustomerListGet.getStatusCodeValue(), 208);
		ResponseEntity<APIResponse<CorporateCustomerDTO>> corporateCustomerListAdd = corporateCustomerMaintenanceController
				.addCustomer(corporateCustomerDto, header);
		assertEquals(corporateCustomerListAdd.getStatusCodeValue(), 208);
	}

	@Test
	void testDeleteCorporateCustomerMaintenance() {
		when(corporateCustomerMapper.corporateCustomerDTOToCorporateCustomer(Mockito.any()))
				.thenReturn(new CorporateCustomer());
		when(corporateCustomerMaintenanceService.deleteCorporateCustomerData(new CorporateCustomer()))
				.thenReturn(new CorporateCustomer());
		when(corporateCustomerMapper.corporateCustomerDTOToCorporateCustomer(Mockito.any()))
				.thenReturn(corporateCustomerMaintenance);
		ResponseEntity<List<APIResponse<CorporateCustomerDTO>>> deleteList = corporateCustomerMaintenanceController
				.deleteCorporateCustomerData(corporateCustomerDtoList);
		assertEquals(deleteList.getStatusCodeValue(), 200);
		when(corporateCustomerMaintenanceService.deleteCorporateCustomerData(new CorporateCustomer())).thenReturn(null);
		deleteList = corporateCustomerMaintenanceController.deleteCorporateCustomerData(corporateCustomerDtoList);
		Assertions.assertEquals(200, deleteList.getStatusCodeValue());
		when(corporateCustomerMaintenanceService.getCorporateCustomerData()).thenReturn(Collections.EMPTY_LIST);
		deleteList = corporateCustomerMaintenanceController.deleteCorporateCustomerData(corporateCustomerDtoList);
		Assertions.assertEquals(200, deleteList.getStatusCodeValue());

	}

	@Test
	void testDeleteCorporateCustomerMaintenanceException() {
		when(corporateCustomerMaintenanceService.deleteCorporateCustomerData(Mockito.any()))
				.thenThrow(new RuntimeException());
		ResponseEntity<List<APIResponse<CorporateCustomerDTO>>> deleteList = corporateCustomerMaintenanceController
				.deleteCorporateCustomerData(corporateCustomerDtoList);
		assertNotNull(deleteList.getStatusCodeValue());
	}

	@Test
	void testDeleteCorporateCustomerMaintenanceNoRecordFoundException() {
		when(corporateCustomerMaintenanceService.deleteCorporateCustomerData(Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<List<APIResponse<CorporateCustomerDTO>>> deleteList = corporateCustomerMaintenanceController
				.deleteCorporateCustomerData(corporateCustomerDtoList);
		assertNotNull(deleteList.getStatusCodeValue());
	}

}
