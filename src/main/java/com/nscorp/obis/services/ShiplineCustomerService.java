package com.nscorp.obis.services;

import com.nscorp.obis.domain.ShiplineCustomer;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

public interface ShiplineCustomerService {

	List<ShiplineCustomer> getAllSteamshipCustomers();
	
	ShiplineCustomer addSteamshipCustomer(@Valid ShiplineCustomer steamshipCustomerObj, Map<String,String> headers);
	void deleteCustomer(@Valid ShiplineCustomer steamshipCustomerObj);
}
