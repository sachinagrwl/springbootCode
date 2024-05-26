package com.nscorp.obis.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.nscorp.obis.domain.CustomerPool;
import com.nscorp.obis.dto.CustomerPoolDTO;
import com.nscorp.obis.dto.PoolListDTO;

public interface CustomerPoolService {

	List<CustomerPool> fetchCustomerPool(@Valid Long customerId) throws SQLException;

	List<CustomerPoolDTO> updateCustomerPools(PoolListDTO poolListDTO, Map<String, String> headers) throws SQLException;

}
