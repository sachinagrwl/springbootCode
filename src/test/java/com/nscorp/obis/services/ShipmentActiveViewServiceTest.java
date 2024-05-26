package com.nscorp.obis.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import com.nscorp.obis.domain.ShipmentActiveView;
import com.nscorp.obis.dto.ShipmentActiveViewDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.ShipmentActiveViewRepository;

public class ShipmentActiveViewServiceTest {
	
	@InjectMocks
	ShipmentActiveViewServiceImpl shipmentActiveViewService;

	@Mock
	ShipmentActiveViewRepository shipmentActiveViewRepository;
	
	ShipmentActiveViewDTO shipmentActiveViewDto;
	ShipmentActiveView shipmentActiveView;
	List<ShipmentActiveView> shipmentActiveViewList;
	List<ShipmentActiveViewDTO> shipmentActiveViewDtoList;
	
	Map<String, String> header;
	String equipInit;
	String equipId;
	String equipTp;
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
		shipmentActiveViewList.add(shipmentActiveView);
		shipmentActiveViewDtoList.add(shipmentActiveViewDto);
		
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
		when(shipmentActiveViewRepository.findByEquipInitAndEquipNbrAndEquipTpAndEquipId(equipInit, equipNbr, equipTp, equipId)).thenReturn(shipmentActiveViewList);
		List<ShipmentActiveView> shipment = shipmentActiveViewService.getShipment(equipInit, equipNbr, equipTp, equipId);
		assertEquals(shipment,shipmentActiveViewList);
	}
	
	@Test
	void testGetAllShipmentActiveViewException() {		
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(shipmentActiveViewService.getShipment(equipInit, equipNbr, equipTp, equipId)));
		assertEquals("No Active Shipment!", exception.getMessage());
		
	}

}
