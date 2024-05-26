package com.nscorp.obis.services;


import java.util.Map;

import com.nscorp.obis.domain.CustomerInfo;
import com.nscorp.obis.dto.CustomerInfoDTO;
import com.nscorp.obis.response.data.PaginatedResponse;

public interface CustomerInfoService {

	PaginatedResponse<CustomerInfoDTO> fetchCustomers(Long customerId, String customerName, String customerNumber,
			Integer pageSize, Integer pageNumber, String[] sort, String[] filter,String fetchExpired);

	CustomerInfoDTO updateCustomer(CustomerInfoDTO customerInfoDTO, Map<String, String> headers);

	CustomerInfo addCustomer(CustomerInfo customerInfo, Map<String, String> headers);

}
