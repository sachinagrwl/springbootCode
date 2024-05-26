package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import com.nscorp.obis.domain.CustomerCharge;
import com.nscorp.obis.dto.CustomerChargeDTO;


public interface CustomerChargeService {

	List<CustomerChargeDTO> getStorageCharge(String equipInit, Integer equipNbr);
	
	 CustomerCharge updateCustomerCharge(CustomerChargeDTO customerChargeDTO, Map<String, String> headers);
}
