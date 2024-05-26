package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.UserId;
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
public class TempEVTServiceImpl implements TempEVTService {

	
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
	ShipmentActiveView shipmentActiveView;

	public TempEVT addTempEVT(TempEVT tempEVT, Map<String, String> headers) throws Exception {
		// TODO Auto-generated method stub
		
		UserId.headerUserID(headers);
		
//		if(tempEVTRepo.existsBySvcId(tempEVT.getSvcId()))
//			throw new RecordAlreadyExistsException("SVC ID Already Exists");

		List<ShipmentActiveView> shipmentActive1 = shipmentActiveViewRepo
				.findByEquipInitAndEquipNbrAndEquipTpAndEquipId(tempEVT.getEquipInit(), tempEVT.getEquipNbr(),
						tempEVT.getEquipTp(), tempEVT.getEquipId());

		if (!shipmentActiveViewRepo.existsByEquipInitAndEquipNbrAndEquipTpAndEquipId(tempEVT.getEquipInit(), tempEVT.getEquipNbr(),
				tempEVT.getEquipTp(), tempEVT.getEquipId())) {
			throw new NoRecordsFoundException("No active shipment");
		
		}
		else if (shipmentActive1.size() > 1)
			throw new NoRecordsFoundException("Cannot generate notification with multiple shipments");

		shipment = shipmentRepo.findBySvcId(tempEVT.getSvcId());
		if (shipment != null) {

			EquipLoc equipLoc = equipLocRepo.findByEquipInitAndEquipNbrAndEquipTpAndEquipId(tempEVT.getEquipInit(),
					tempEVT.getEquipNbr(), tempEVT.getEquipTp(), tempEVT.getEquipId());
			if(equipLoc.getTerminalId().equals(tempEVT.getTermId())) {
			if (terminalRepo.existsByTerminalId(equipLoc.getTerminalId())) {

				if (!equipLoc.getCurrLoc().trim().equalsIgnoreCase("TERMINAL LOT")) {

					throw new NoRecordsFoundException("Equipment not in terminal lot");
				}

				if (shipmentRepo.existsByOnlDest(equipLoc.getTerminalId())) {

					// Add data to DB

					tempEVT.setCreateUserId(headers.get(CommonConstants.USER_ID));
					tempEVT.setUpdateUserId(headers.get(CommonConstants.USER_ID));
					tempEVT.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
					tempEVT.setUversion("!");
					Long evtlogId = tempEVTRepo.SGK();
					tempEVT.setEvtlogId(evtlogId);
					tempEVT.setEvtCd("NTFY");
					tempEVT.setQueStat("R");
					tempEVT.setEvtdtTm(tempEVT.getEvtdtTm());
					TempEVT code = tempEVTRepo.save(tempEVT);
					System.out.print(code);
					return code;
				} else {
					
				
					short procOutput;
					 procOutput = shipmentActiveViewRepo.inOnlyTest(equipLoc.getTerminalId(),
								shipment.getOnlDest(),
								shipment.getOfflDest());
					 
					if (procOutput == 1) {

						tempEVT.setCreateUserId(headers.get(CommonConstants.USER_ID));
						tempEVT.setUpdateUserId(headers.get(CommonConstants.USER_ID));
						tempEVT.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
						tempEVT.setUversion("!");
						Long evtlogId = tempEVTRepo.SGK();
						tempEVT.setEvtlogId(evtlogId);
						tempEVT.setEvtCd("NTFY");
						tempEVT.setQueStat("R");
						tempEVT.setEvtdtTm(tempEVT.getEvtdtTm());
						TempEVT code = tempEVTRepo.save(tempEVT);
						return code;
					} else {

						throw new NoRecordsFoundException("Equipment not at online destination");
					}
				}
			}
			else
				throw new NoRecordsFoundException("Equipment at wrong terminal");
			} else
				throw new NoRecordsFoundException("Equipment at wrong terminal");
		} else
			throw new NoRecordsFoundException("Cannot generate notification without shipment");

	}
}