package com.nscorp.obis.services;

import java.util.Map;

import com.nscorp.obis.domain.Pool;

public interface DisplayPoolCustomerService {

	Pool addPoolCustomer(Pool pool, Map<String, String> headers);

	Pool deletePool(Pool poolDtoToPool);

//	Pool deletePool(Pool poolObj);

}
