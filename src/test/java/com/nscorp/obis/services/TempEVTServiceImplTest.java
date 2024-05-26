package com.nscorp.obis.services;

import com.nscorp.obis.domain.EquipLoc;
import com.nscorp.obis.domain.Shipment;
import com.nscorp.obis.domain.ShipmentActiveView;
import com.nscorp.obis.domain.TempEVT;
import com.nscorp.obis.dto.TempEVTDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TempEVTServiceImplTest {
	@Mock
	ShipmentActiveViewRepository shipmentActiveViewRepo;
	@Mock
	ShipmentRepository shipmentRepo;
	@Mock
	EquipLocRepository equipLocRepo;
	@Mock
	TerminalRepository terminalRepo;
	@Mock
	TempEVTRepository tempEVTRepo;
	@Mock
	EquipLoc equipLoc;
	@Mock
	Shipment shipment;
	@Mock
	ShipmentActiveView shipmentActiveView;
	@InjectMocks
	TempEVTServiceImpl tempEVTServiceImpl;
	List<ShipmentActiveView> shipmentActiveViewList;
	TempEVT tempEvt;
	TempEVTDTO tempEvtDTO;
	Map<String, String> header;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		shipmentActiveView = new ShipmentActiveView();
		equipLoc = new EquipLoc();
		shipment= new Shipment();
		equipLoc.setCurrLoc("TERMINAL LOT");
		shipmentActiveView.setEquipId("0");
		shipmentActiveView.setShipment(shipment);
		shipmentActiveView.setEquipInit("HDMU");
		shipmentActiveView.setEquipTp("C");
		shipmentActiveView.setEquipNbr(BigDecimal.valueOf(100000));
		shipmentActiveView.setSvcId(Long.valueOf(10000));
		shipmentActiveViewList = new ArrayList<>();
		shipmentActiveViewList.add(shipmentActiveView);
		tempEvt = new TempEVT();
		tempEvtDTO = new TempEVTDTO();
		tempEvt.setEvtlogId(Long.valueOf(10000));
		tempEvt.setEvtCd("NTFY");
		tempEvt.setQueStat("R");
		tempEvt.setTermId(Long.valueOf(10000));
		tempEvt.setSvcId(Long.valueOf(10000));
		tempEvt.setEquipId("0");
		tempEvt.setEquipNbr(BigDecimal.valueOf(100000));
		tempEvt.setEquipTp("C");
		tempEvt.setEquipInit("HDMU");

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		tempEvt = null;
		tempEvtDTO = null;
		shipmentActiveView = null;
		shipmentActiveViewList = null;
	}

	@Test
	void testAddTempEVT() throws Exception {
		tempEvt.setTermId(123L);
		equipLoc.setTerminalId(123L);

		// when(tempEVTRepo.existsBySvcId(tempEvt.getSvcId())).thenReturn(false);
		when(shipmentActiveViewRepo.findByEquipInitAndEquipNbrAndEquipTpAndEquipId(Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any())).thenReturn(shipmentActiveViewList);
		when(shipmentActiveViewRepo.existsByEquipInitAndEquipNbrAndEquipTpAndEquipId(Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any())).thenReturn(true);

		when(shipmentActiveViewRepo.inOnlyTest(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn((short) 1);
		when(shipmentRepo.existsByOnlDest(Mockito.any())).thenReturn(true);
		when(shipmentRepo.findBySvcId(Mockito.any())).thenReturn(shipment);
		when(equipLocRepo.findByEquipInitAndEquipNbrAndEquipTpAndEquipId(Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any())).thenReturn(equipLoc);
		when(terminalRepo.existsByTerminalId(Mockito.any())).thenReturn(true);
		when(tempEVTRepo.SGK()).thenReturn(Long.valueOf(1));
		when(tempEVTRepo.existsBySvcId(Mockito.any())).thenReturn(false);
		when(tempEVTRepo.save(tempEvt)).thenReturn(tempEvt);
		TempEVT result = tempEVTServiceImpl.addTempEVT(tempEvt, header);

		when(shipmentRepo.existsByOnlDest(Mockito.any())).thenReturn(false);
		result = tempEVTServiceImpl.addTempEVT(tempEvt, header);
		when(shipmentActiveViewRepo.inOnlyTest(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn((short) 2);
		result = tempEVTServiceImpl.addTempEVT(tempEvt, header);



	}

//	@Test
//	void AddNoRecordFoundException() throws Exception {
//		when(shipmentActiveViewRepo.findByEquipInitAndEquipNbrAndEquipTpAndEquipId(tempEvt.getEquipInit(),
//				tempEvt.getEquipNbr(), tempEvt.getEquipTp(), tempEvt.getEquipId())).thenReturn(shipmentActiveViewList);
//		when(shipmentActiveViewRepo.existsByEquipInitAndEquipNbrAndEquipTpAndEquipId(tempEvt.getEquipInit(),
//				tempEvt.getEquipNbr(), tempEvt.getEquipTp(), tempEvt.getEquipId())).thenReturn(true);
//		when(shipmentRepo.findBySvcId(tempEvt.getSvcId())).thenReturn(null);
//		TempEVT result = tempEVTServiceImpl.addTempEVT(tempEvt, header);
//		assertNotNull(result);
//
//	}

	@Test
	void testAddTempEVTNoRecordFoundException() throws Exception {
		// when(tempEVTRepo.existsBySvcId(tempEvt.getSvcId())).thenReturn(false);
		// when(shipmentRepo.existsByOnlDest(shipment.getOnlDest())).thenReturn(false);
		when(shipmentActiveViewRepo.findByEquipInitAndEquipNbrAndEquipTpAndEquipId(tempEvt.getEquipInit(),
				tempEvt.getEquipNbr(), tempEvt.getEquipTp(), tempEvt.getEquipId())).thenReturn(shipmentActiveViewList);
		when(shipmentActiveViewRepo.existsByEquipInitAndEquipNbrAndEquipTpAndEquipId(tempEvt.getEquipInit(),
				tempEvt.getEquipNbr(), tempEvt.getEquipTp(), tempEvt.getEquipId())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class, () -> tempEVTServiceImpl.addTempEVT(tempEvt, header));
		shipmentActiveViewList.add(shipmentActiveView);
		when(shipmentActiveViewRepo.inOnlyTest(Long.valueOf(1), Long.valueOf(1), Long.valueOf(1)))
				.thenReturn((short) 0);
		when(shipmentActiveViewRepo.findByEquipInitAndEquipNbrAndEquipTpAndEquipId(tempEvt.getEquipInit(),
				tempEvt.getEquipNbr(), tempEvt.getEquipTp(), tempEvt.getEquipId())).thenReturn(null);
		when(tempEVTRepo.SGK()).thenReturn(Long.valueOf(1));
		when(equipLoc.getTerminalId()).thenReturn(Long.valueOf(1));
		when(shipment.getOnlDest()).thenReturn(Long.valueOf(1));
		when(shipment.getOfflDest()).thenReturn(Long.valueOf(1));
		when(shipmentActiveViewRepo.existsByEquipInitAndEquipNbrAndEquipTpAndEquipId(tempEvt.getEquipInit(),
				tempEvt.getEquipNbr(), tempEvt.getEquipTp(), tempEvt.getEquipId()))
						.thenThrow(new NoRecordsFoundException());
		when(shipmentActiveViewRepo.inOnlyTest(Long.valueOf(1), Long.valueOf(1), Long.valueOf(1)))
				.thenThrow(new NoRecordsFoundException());
		when(shipmentRepo.findBySvcId(null)).thenThrow(new NoRecordsFoundException());
		when(terminalRepo.existsByTerminalId(Long.valueOf(1))).thenThrow(new NoRecordsFoundException());
		when(equipLoc.getCurrLoc()).thenThrow(new NoRecordsFoundException());
		when(tempEVTRepo.save(tempEvt)).thenThrow(new NoRecordsFoundException());
		assertThrows(NoRecordsFoundException.class, () -> tempEVTServiceImpl.addTempEVT(tempEvt, header));
	}

	@Test
	void testAddTempEVTRecordAlreadyExistsException() {
		when(tempEVTRepo.existsBySvcId(tempEvt.getSvcId())).thenThrow(new RecordAlreadyExistsException());
		assertThrows(RecordAlreadyExistsException.class, () -> tempEVTServiceImpl.addTempEVT(tempEvt, header));
	}
}
