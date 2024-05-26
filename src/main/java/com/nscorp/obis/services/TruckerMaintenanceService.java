package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import com.nscorp.obis.domain.CustomerScac;
import com.nscorp.obis.domain.DrayageCompany;
import com.nscorp.obis.domain.DrayageCustomer;
import com.nscorp.obis.domain.DrayageCustomerInfo;
import com.nscorp.obis.dto.CustomerScacDTO;
import com.nscorp.obis.dto.DrayageCustomerInfoDTO;

public interface TruckerMaintenanceService {

	List<DrayageCustomer> fetchDrayageCustomers(Long customerId, String drayageId);
	List<CustomerScacDTO> fetchDrayageCustomersByPrimarySix(String customerPrimarySix);

	List<DrayageCompany> fetchDrayageCompany(String drayageId);
	
	DrayageCustomerInfo addDrayageCustomer(DrayageCustomerInfo drayageCustomerInfo, Map<String, String> headers, String override);
	DrayageCustomerInfo addDrayageCustomerAndRemoveLink(DrayageCustomerInfo drayageCustomerInfo,String drayageId, Map<String, String> headers);
	List<DrayageCustomerInfoDTO> deleteDrayageCustomerInfo(List<DrayageCustomerInfoDTO> drayageCustomerInfoDTOS);

	DrayageCompany addDrayageCompany(DrayageCompany drayageCompany, Map<String, String> headers);

	DrayageCompany updateDrayageCompany(DrayageCompany drayageCompany, Map<String, String> headers);
}
