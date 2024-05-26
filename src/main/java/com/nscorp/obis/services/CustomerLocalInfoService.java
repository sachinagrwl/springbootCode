package com.nscorp.obis.services;

import java.sql.SQLException;
import java.util.Map;

import com.nscorp.obis.dto.CustomerLocalInfoDTO;

import javax.validation.Valid;

import com.nscorp.obis.domain.CustomerLocalInfo;

public interface CustomerLocalInfoService {

	CustomerLocalInfo fetchCustomerLocalInfo(@Valid Long customerId, @Valid Long terminalId) throws SQLException;

	CustomerLocalInfoDTO deleteCustomerLocalInfo(CustomerLocalInfoDTO customerLocalInfoDTO);
	
	CustomerLocalInfoDTO addCustomerLocalInfo(CustomerLocalInfoDTO customerLocalInfoDTO, Map<String, String> headers);

	CustomerLocalInfo updateCustomerLocalInfo(@Valid CustomerLocalInfoDTO customerLocalInfoDTO,
			Map<String, String> headers);

}
