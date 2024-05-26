package com.nscorp.obis.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.domain.EquipLoc;
import com.nscorp.obis.domain.Shipment;
import com.nscorp.obis.domain.ShipmentActiveView;
import com.nscorp.obis.domain.TempEVT;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.EquipLocRepository;
import com.nscorp.obis.repository.ShipmentActiveViewRepository;
import com.nscorp.obis.repository.ShipmentRepository;
import com.nscorp.obis.repository.TempEVTRepository;
import com.nscorp.obis.repository.TerminalRepository;

@Service
@Transactional
public class ShipmentActiveViewServiceImpl implements ShipmentActiveViewService {

	@Autowired
	ShipmentActiveViewRepository shipmentActiveViewRepo;

	@Autowired
	ShipmentRepository shipmentRepo;

	@Autowired
	EquipLocRepository equipLocRepo;

	@Autowired
	TerminalRepository terminalRepo;
	
	@Autowired
	TempEVTRepository tempEVTRepo;
	
	EquipLoc equipLoc;
	Shipment shipment;
	TempEVT tempEVT;

	@Override
	public List<ShipmentActiveView> getShipment(String equipInit, BigDecimal equipNbr, String equipTp, String equipId) {
		List<ShipmentActiveView> shipmentActive = shipmentActiveViewRepo
				.findByEquipInitAndEquipNbrAndEquipTpAndEquipId(equipInit, equipNbr, equipTp, equipId);
		if (shipmentActive.isEmpty()) {
			throw new NoRecordsFoundException("No Active Shipment!");
		}
		return shipmentActive;
	}
}
