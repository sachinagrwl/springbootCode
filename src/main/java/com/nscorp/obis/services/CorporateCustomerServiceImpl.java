package com.nscorp.obis.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nscorp.obis.domain.CorporateCustomer;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.CorporateCustomerRepository;

@Service
public class CorporateCustomerServiceImpl implements CorporateCustomerService {

	@Autowired
	private CorporateCustomerRepository corporateCustomerRepo;

	@Override
	public List<CorporateCustomer> getAllCorporateCustomers() {
		List<CorporateCustomer> corporateCustomers = corporateCustomerRepo.findAllByOrderByCorporateLongName();
		if (corporateCustomers.isEmpty()) {
			throw new NoRecordsFoundException("No Records found");
		}
		return corporateCustomers;
	}

}
