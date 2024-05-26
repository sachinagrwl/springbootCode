package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.ShipmentActiveView;
import com.nscorp.obis.dto.ShipmentActiveViewDTO;
import com.nscorp.obis.dto.mapper.ShipmentActiveViewMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.ShipmentActiveViewService;

public class ShipmentActiveViewControllerTest {
	
	@Mock
	ShipmentActiveViewService shipmentActiveViewService;

	@Mock
	ShipmentActiveViewMapper shipmentActiveViewMapper;

	@InjectMocks
	ShipmentActiveViewController shipmentActiveViewController;
	
	ShipmentActiveViewDTO shipmentActiveViewDto;
	ShipmentActiveView shipmentActiveView;
	List<ShipmentActiveView> shipmentActiveViewList;
	List<ShipmentActiveViewDTO> shipmentActiveViewDtoList;
	Map<String, String> header;
	
	String equipTp;
	String equipInit;
	String equipId;
	BigDecimal equipNbr = new BigDecimal(1000);

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		shipmentActiveView = new ShipmentActiveView();
		shipmentActiveViewDto = new ShipmentActiveViewDTO();
		shipmentActiveViewDtoList = new ArrayList<>();
		shipmentActiveViewList = new ArrayList<>();
		shipmentActiveViewDto.setEquipId("0");
		shipmentActiveViewDto.setEquipInit("EMHU");
		shipmentActiveViewDto.setEquipNbr(equipNbr);
		shipmentActiveViewDto.setEquipTp("C");
		shipmentActiveViewDto.setSvcId(14244120811052L);
		
		shipmentActiveViewDtoList.add(shipmentActiveViewDto);
		shipmentActiveViewList.add(shipmentActiveView);
		header = new HashMap<String,String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		shipmentActiveView = null;
		shipmentActiveViewDto = null;
		shipmentActiveViewDtoList = null;
		shipmentActiveViewList = null;
	}

	@Test
	void testGetShipmentActiveView() {
		when(shipmentActiveViewService.getShipment(equipInit, equipNbr, equipTp, equipId)).thenReturn(shipmentActiveViewList);
		ResponseEntity<APIResponse<List<ShipmentActiveViewDTO>>> getShipmentActiveViewList = shipmentActiveViewController.getShipment(equipInit, equipNbr, equipTp, equipId);
		assertEquals(getShipmentActiveViewList.getStatusCodeValue(),200);
	}
	
	@Test
	void testShipmentActiveViewNoRecordsFoundException() {
		when(shipmentActiveViewService.getShipment(equipInit, equipNbr, equipTp, equipId)).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<ShipmentActiveViewDTO>>> shipmentList = shipmentActiveViewController.getShipment(equipInit, equipNbr, equipTp, equipId);
		assertEquals(shipmentList.getStatusCodeValue(), 200);
		}
	
	@Test
	void testShipmentActiveViewException() {
		when(shipmentActiveViewService.getShipment(equipInit, equipNbr, equipTp, equipId)).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<ShipmentActiveViewDTO>>> shipmentList = shipmentActiveViewController.getShipment(equipInit, equipNbr, equipTp, equipId);
		assertEquals(shipmentList.getStatusCodeValue(), 500);
	}

}
