package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.Customer;
import com.nscorp.obis.domain.PoolEquipmentController;
import com.nscorp.obis.dto.PoolEquipmentControllerDTO;
import com.nscorp.obis.dto.mapper.PoolEquipmentControllerMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.PoolEquipmentControllerService;

class EquipmentPoolControllerTest {
	
	@Mock
	PoolEquipmentControllerService poolEqCtrlService;
	
	@Mock
	PoolEquipmentControllerMapper poolEqCtrlMapper;
	
	@InjectMocks
	EquipmentPoolController poolEqController;
	
	PoolEquipmentController poolEqCtrl;
	PoolEquipmentControllerDTO poolEqCtrlDto;
	List<PoolEquipmentController> poolEqCtrlList;
	List<PoolEquipmentControllerDTO> poolEqCtrlDtoList;
	Customer customer;
	Map<String,String> header;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		poolEqCtrl = new PoolEquipmentController();
		poolEqCtrlDto = new PoolEquipmentControllerDTO();
		customer = new Customer();
		poolEqCtrlList = new ArrayList<>();
		poolEqCtrlDtoList = new ArrayList<>();
		
		poolEqCtrl.setPoolControllerId(Long.valueOf(123));
		poolEqCtrl.setPoolId(Long.valueOf(123));
		poolEqCtrl.setCustomerPrimary6("TEST");
		poolEqCtrl.setEquipmentType("C");
		poolEqCtrl.setCustomer(customer);
		
		poolEqCtrlList.add(poolEqCtrl);
		
		poolEqCtrlDto.setPoolControllerId(Long.valueOf(123));
		poolEqCtrlDto.setPoolId(Long.valueOf(123));
		poolEqCtrlDto.setCustomerPrimary6("TEST");
		poolEqCtrlDto.setEquipmentType("C");
		poolEqCtrlDto.setCustomer(customer);
		
		poolEqCtrlDtoList.add(poolEqCtrlDto);
		
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
	
		header = new HashMap<String, String>();
        header.put("userid", "Test");
        header.put("extensionschema", "Test");
        
	}

	@AfterEach
	void tearDown() throws Exception {
		poolEqCtrlDtoList = null;
		poolEqCtrlList = null;
		poolEqCtrlDto = null;
		poolEqCtrl = null;
	}

	@Test
	void testGetAllPoolController() {
		when(poolEqCtrlService.getAllPool()).thenReturn(poolEqCtrlList);
		ResponseEntity<APIResponse<List<PoolEquipmentControllerDTO>>> getPoolCtrl = poolEqController.getAllPoolController();
        assertEquals(getPoolCtrl.getStatusCodeValue(),200);
	}
	
	@Test
    void poolRuntimeException() {
		when(poolEqCtrlService.getAllPool()).thenThrow(new RuntimeException());
        when(poolEqCtrlService.insertPoolCtrl(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
        when(poolEqCtrlService.updatePoolCtrl(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());

        ResponseEntity<APIResponse<List<PoolEquipmentControllerDTO>>> getPoolCtrl = poolEqController.getAllPoolController();
        ResponseEntity<APIResponse<PoolEquipmentControllerDTO>> addPoolCtrl = poolEqController.insertPoolController(Mockito.any(),Mockito.any());
        ResponseEntity<APIResponse<PoolEquipmentControllerDTO>> updatePoolCtrl = poolEqController.updatePoolController(Mockito.any(),Mockito.any());

        assertEquals(getPoolCtrl.getStatusCodeValue(),500);
        assertEquals(addPoolCtrl.getStatusCodeValue(),500);
        assertEquals(updatePoolCtrl.getStatusCodeValue(),500);
    }
	
	@Test
    void poolNoRecordFoundException() {
		when(poolEqCtrlService.getAllPool()).thenThrow(new NoRecordsFoundException());
        when(poolEqCtrlService.insertPoolCtrl(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
        when(poolEqCtrlService.updatePoolCtrl(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());

        ResponseEntity<APIResponse<List<PoolEquipmentControllerDTO>>> getPoolCtrl = poolEqController.getAllPoolController();
        ResponseEntity<APIResponse<PoolEquipmentControllerDTO>> addPoolCtrl = poolEqController.insertPoolController(Mockito.any(),Mockito.any());
        ResponseEntity<APIResponse<PoolEquipmentControllerDTO>> updatePoolCtrl = poolEqController.updatePoolController(Mockito.any(),Mockito.any());

        assertEquals(getPoolCtrl.getStatusCodeValue(),404);
        assertEquals(addPoolCtrl.getStatusCodeValue(),404);
        assertEquals(updatePoolCtrl.getStatusCodeValue(),404);
    }
	
	 @Test
     void getAllPoolControllerRecordAlreadyExistsException() {
		 when(poolEqCtrlService.insertPoolCtrl(Mockito.any(), Mockito.any())).thenThrow(new RecordAlreadyExistsException());
		 ResponseEntity<APIResponse<PoolEquipmentControllerDTO>> addPoolCtrl = poolEqController.insertPoolController(Mockito.any(),Mockito.any());
		 assertEquals(addPoolCtrl.getStatusCodeValue(),208); 
	 }
	 
	 @Test
	 void testPoolControllerSizeExceedException() {
		when(poolEqCtrlService.insertPoolCtrl(Mockito.any(), Mockito.any())).thenThrow(new SizeExceedException());
		ResponseEntity<APIResponse<PoolEquipmentControllerDTO>> addedPoolController = poolEqController.insertPoolController(poolEqCtrlDto, header);
		assertEquals(addedPoolController.getStatusCodeValue(),411);
	}
	 
	 @Test
	 void testPoolControllerRecordNotAddedException() {
		when(poolEqCtrlService.insertPoolCtrl(Mockito.any(), Mockito.any())).thenThrow(new RecordNotAddedException ());
		ResponseEntity<APIResponse<PoolEquipmentControllerDTO>> addedPoolController = poolEqController.insertPoolController(poolEqCtrlDto, header);
		assertEquals(addedPoolController.getStatusCodeValue(),406);
	}
	 
	 @Test
	 void testPoolControllerNullPointerException() {
		when(poolEqCtrlService.insertPoolCtrl(Mockito.any(), Mockito.any())).thenThrow(new NullPointerException  ());
		ResponseEntity<APIResponse<PoolEquipmentControllerDTO>> addedPoolController = poolEqController.insertPoolController(poolEqCtrlDto, header);
		assertEquals(addedPoolController.getStatusCodeValue(),400);
	}

	 @Test
     void getAllPoolControllerInvalidDataException() {
		 when(poolEqCtrlService.updatePoolCtrl(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
		 ResponseEntity<APIResponse<PoolEquipmentControllerDTO>> updatePoolCtrl = poolEqController.updatePoolController(Mockito.any(),Mockito.any());
		 assertEquals(updatePoolCtrl.getStatusCodeValue(),404);
	 }
	 
	 @Test
     void getAllPoolControllerRecordNotAddedException() {
		 when(poolEqCtrlService.updatePoolCtrl(Mockito.any(), Mockito.any())).thenThrow(new RecordNotAddedException());
		 ResponseEntity<APIResponse<PoolEquipmentControllerDTO>> updatePoolCtrl = poolEqController.updatePoolController(Mockito.any(),Mockito.any());
		 assertEquals(updatePoolCtrl.getStatusCodeValue(),406);
	 }
	 
	 @Test
     void getAllPoolControllerNullPointerException() {
		 when(poolEqCtrlService.updatePoolCtrl(Mockito.any(), Mockito.any())).thenThrow(new NullPointerException());
		 ResponseEntity<APIResponse<PoolEquipmentControllerDTO>> updatePoolCtrl = poolEqController.updatePoolController(Mockito.any(),Mockito.any());
		 assertEquals(updatePoolCtrl.getStatusCodeValue(),400);
	 }
	 
	@Test
	void testInsertPoolController() {
		when(poolEqCtrlMapper.poolEquipmentControllerDTOToPoolEquipmentController(Mockito.any())).thenReturn(poolEqCtrl);
		when(poolEqCtrlService.insertPoolCtrl(Mockito.any(), Mockito.any())).thenReturn(poolEqCtrl);
		when(poolEqCtrlMapper.poolEquipmentControllerToPoolEquipmentControllerDTO(Mockito.any())).thenReturn(poolEqCtrlDto);
		ResponseEntity<APIResponse<PoolEquipmentControllerDTO>> addPoolCtrl = poolEqController.insertPoolController(poolEqCtrlDto, header);
        assertNotNull(addPoolCtrl.getBody());
	}

	@Test
	void testUpdatePoolController() {
		when(poolEqCtrlMapper.poolEquipmentControllerDTOToPoolEquipmentController(Mockito.any())).thenReturn(poolEqCtrl);
		when(poolEqCtrlService.updatePoolCtrl(Mockito.any(), Mockito.any())).thenReturn(poolEqCtrl);
		when(poolEqCtrlMapper.poolEquipmentControllerToPoolEquipmentControllerDTO(Mockito.any())).thenReturn(poolEqCtrlDto);
		ResponseEntity<APIResponse<PoolEquipmentControllerDTO>> updatePoolCtrl = poolEqController.updatePoolController(poolEqCtrlDto, header);
		assertNotNull(updatePoolCtrl.getBody());
		
	}

	@Test
	void testDeletePoolController() {
		when(poolEqCtrlMapper.poolEquipmentControllerDTOToPoolEquipmentController(Mockito.any())).thenReturn(poolEqCtrl);
		when(poolEqCtrlService.deletePoolCtrl(Mockito.any())).thenReturn(poolEqCtrl);
		when(poolEqCtrlMapper.poolEquipmentControllerToPoolEquipmentControllerDTO(Mockito.any())).thenReturn(poolEqCtrlDto);
		ResponseEntity<List<APIResponse<PoolEquipmentControllerDTO>>> deletePoolCtrl = poolEqController.deletePoolController(poolEqCtrlDtoList);
		assertEquals(deletePoolCtrl.getStatusCodeValue(),200);
	}
	
	@SuppressWarnings("unchecked")
	@Test
    void testDeleteCarEmbargoEmptyDTOList(){
        ResponseEntity<List<APIResponse<PoolEquipmentControllerDTO>>> responseEntity = poolEqController.deletePoolController(Collections.EMPTY_LIST);
        assertEquals(responseEntity.getStatusCodeValue(),500);
    }

}
