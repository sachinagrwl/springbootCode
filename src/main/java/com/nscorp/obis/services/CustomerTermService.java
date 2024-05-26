package com.nscorp.obis.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.nscorp.obis.domain.CustomerTerm;
import com.nscorp.obis.domain.CustomerTerminal;

public interface CustomerTermService {

	List<CustomerTerm> fetchCustomerNotifyProfiles(@Valid Long terminalId, @Valid String customerName) throws SQLException;

	List<CustomerTerminal> fetchCustomerTerminal(@Valid Long customerId) throws SQLException;
	
	CustomerTerminal updateCustomerTerminals(CustomerTerminal customerTerminal, Map<String, String> headers);

	Long deleteByCustomerId(Long customerId);

	
}
