package com.nscorp.obis.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.nscorp.obis.domain.CustomerPerDiemRateSelection;
import com.nscorp.obis.dto.CustomerPerDiemRateSelectionDTO;

public interface CustomerPerDiemRateSelectionService {
	CustomerPerDiemRateSelectionDTO addCustomerPerDiemRate(
			@Valid CustomerPerDiemRateSelectionDTO customerPerDiemRateSelectionDTO, Map<String, String> headers) throws SQLException;


	List<CustomerPerDiemRateSelection> fetchCustomerPerDiemRate(@Valid String custPrimSix) throws SQLException;

	CustomerPerDiemRateSelectionDTO updateCustomerPerDiemRate(
			@Valid CustomerPerDiemRateSelectionDTO customerPerDiemRateSelectionDTO, Map<String, String> headers) throws SQLException;
}
