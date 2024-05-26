package com.nscorp.obis.services;

import java.math.BigDecimal;
import java.util.List;

import com.nscorp.obis.domain.ShipmentActiveView;

public interface ShipmentActiveViewService {


	List<ShipmentActiveView> getShipment(String equipInit, BigDecimal equipNbr, String equipTp, String equipId);

}
