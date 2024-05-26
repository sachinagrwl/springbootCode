package com.nscorp.obis.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.common.SpecificationGenerator;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.CustomerTerm;
import com.nscorp.obis.domain.CustomerTerminal;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.CustomerRepository;
import com.nscorp.obis.repository.CustomerTermRepository;
import com.nscorp.obis.repository.CustomerTerminalRepository;
import com.nscorp.obis.repository.TerminalRepository;

@Service
public class CustomerTermServiceImpl implements CustomerTermService {
	
	@Autowired
	TerminalRepository terminalRepository;

	@Autowired
	CustomerRepository CustomerRepository;

	@Autowired
	CustomerTermRepository customerTermRepository;
	
	@Autowired
	CustomerTerminalRepository customerTerminalRepository;
	
	@Autowired
	SpecificationGenerator specificationGenerator;

	@Override
	public List<CustomerTerm> fetchCustomerNotifyProfiles(@Valid Long terminalId, String customerName)
			throws SQLException {
		Specification<CustomerTerm> specification = specificationGenerator.customerTermSpecification(terminalId, customerName);
		List<CustomerTerm> customerTerms =customerTermRepository.findAll(specification);	
		System.out.println(customerTerms.isEmpty());
		if (customerTerms.isEmpty()) {
			throw new NoRecordsFoundException("No Records found for this Combination.");
		}
		return customerTerms;
	}
	@Override
	public List<CustomerTerminal> fetchCustomerTerminal(@Valid Long customerId)
			throws SQLException {
		List<CustomerTerminal> customerTerminals =customerTerminalRepository.findByCustomerId(customerId);		
		if (customerTerminals.isEmpty()) {
			throw new NoRecordsFoundException("No Records found for this Customer.");
		}
		return customerTerminals;
	}

	@Override
	public CustomerTerminal updateCustomerTerminals(CustomerTerminal customerTerminal, Map<String, String> headers) {

		Long customerId = customerTerminal.getCustomerId();
		Long terminalId = customerTerminal.getTerminalId();

		if (customerId != null) {
			if (!CustomerRepository.existsByCustomerId(customerId)) {
				throw new NoRecordsFoundException("No Customer Found with this customer id : " + customerId);
			}
		} else {
			throw new NoRecordsFoundException("customerId cant be null. Enter Valid customerId");
		}

		if (terminalId != null) {
			if (!terminalRepository.existsByTerminalId(terminalId)) {
				throw new NoRecordsFoundException("No Terminal Found with this terminal id : " + terminalId);
			}
		
		} else {
			throw new NoRecordsFoundException("terminalId cant be null. Enter Valid terminalId");
		}

		UserId.headerUserID(headers);
		customerTerminal.setCreateUserId(headers.get("userid"));
		customerTerminal.setUpdateUserId(headers.get("userid"));

		customerTerminal.setUpdateExtensionSchema(headers.get("extensionschema"));
		customerTerminal.setUversion("!");
		

		CustomerTerminal customerterm = customerTerminalRepository.save(customerTerminal);
		if (customerterm == null) {
			throw new RecordNotAddedException("Record Not added to Database");
		}
		return customerterm;

	}

	@Override
	@Transactional
	public Long deleteByCustomerId(Long customerId)
	{

		customerTerminalRepository.deleteByCustomerId(customerId);
		return customerId;
	}

	


}
