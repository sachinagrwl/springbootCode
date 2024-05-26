package com.nscorp.obis.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.nscorp.obis.dto.CashExceptionDTO;

import javax.validation.Valid;

public interface CashExceptionService {

	List<CashExceptionDTO> getCashException(String customerName, String customerPrimSix);

	CashExceptionDTO addCashException(
			@Valid CashExceptionDTO cashExceptionDTO, Map<String, String> headers) throws SQLException;

	CashExceptionDTO updateCashException(
			@Valid CashExceptionDTO cashExceptionDTO, Map<String, String> headers) throws SQLException;


}
