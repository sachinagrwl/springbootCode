package com.nscorp.obis.services;

import java.util.List;

import com.nscorp.obis.domain.CustomerIndex;
import com.nscorp.obis.dto.CustomerIndexDTO;

public interface CustomerIndexService {
	
	List<CustomerIndexDTO> getCustomers(String customerName, String customerNumber, String city, String state, String uniqueGroup,Long corporateId, String latest, String[] sort, String fetchExpired);

	CustomerIndex getCustIndex(Long notifyQueueId);
}
