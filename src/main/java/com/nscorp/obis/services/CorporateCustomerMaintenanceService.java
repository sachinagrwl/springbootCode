package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import com.nscorp.obis.domain.CorporateCustomer;


public interface CorporateCustomerMaintenanceService {
    List<CorporateCustomer> getCorporateCustomerData();

    CorporateCustomer deleteCorporateCustomerData(CorporateCustomer object);

	CorporateCustomer updateCorporateCustomer(CorporateCustomer corporateCustomer, Map<String, String> headers);

	CorporateCustomer addCorporateCustomer(CorporateCustomer corporateCustomer, Map<String, String> headers);

}
