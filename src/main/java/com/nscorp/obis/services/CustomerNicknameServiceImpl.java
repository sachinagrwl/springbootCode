package com.nscorp.obis.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.common.SpecificationGenerator;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.CustomerNickname;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.CustomerNicknameRepository;
import com.nscorp.obis.repository.CustomerRepository;
import com.nscorp.obis.repository.TerminalRepository;

@Service
public class CustomerNicknameServiceImpl implements CustomerNicknameService {

	@Autowired
	CustomerNicknameRepository customerNicknameRepository;

	@Autowired
	TerminalRepository terminalRepository;

	@Autowired
	CustomerRepository CustomerRepository;

	@Autowired
	SpecificationGenerator specificationGenerator;

	@Override
	public List<CustomerNickname> fetchCustomerNickname(@Valid Long customerId, @Valid Long terminalId,
			@Valid String customerNickname) throws SQLException {
		if (terminalId != null) {
			if (!terminalRepository.existsByTerminalId(terminalId)) {
				throw new NoRecordsFoundException("No Terminal Found with this terminal id : " + terminalId);
			}
		}
		if (customerId != null) {
			if (!CustomerRepository.existsByCustomerId(customerId)) {
				throw new NoRecordsFoundException("No Customer Found with this customer id : " + customerId);
			}
		}
		if (customerId == null && terminalId == null && customerNickname == null) {
			throw new NoRecordsFoundException("Pass any parameter");
		}
		Specification<CustomerNickname> specification = specificationGenerator.customerNicknameSpecification(customerId,
				terminalId, customerNickname);
		List<CustomerNickname> customerNicknames = customerNicknameRepository.findAll(specification);
		if (customerNicknames.isEmpty()) {
			throw new NoRecordsFoundException("No Record found for this combination");
		}
		return customerNicknames;
	}

	/* This method is used to add values */
	@Override
	public CustomerNickname addCustomerNickname(CustomerNickname customerNickname,Map<String, String> headers) {

		Long terminalId = customerNickname.getTerminalId();
		Long customerId = customerNickname.getCustomerId();
		String Nickname = customerNickname.getCustomerNickname();

		if (terminalId != null) {
			if (!terminalRepository.existsByTerminalId(terminalId)) {
				throw new NoRecordsFoundException("No Terminal Found with this terminal id : " + terminalId);
			}
		} else {
			throw new NoRecordsFoundException("terminalId  cant be empty. Enter Valid terminalId");
		}

		if (customerId != null) {
			if (!CustomerRepository.existsByCustomerId(customerId)) {
				throw new NoRecordsFoundException("No Customer Found with this customer id : " + customerId);
			}
		} else {
			throw new NoRecordsFoundException("customerId cant be empty. Enter Valid customerId");
		}

		if (!Nickname.isEmpty()) {
			if (customerNicknameRepository.existsByCustomerNicknameAndTerminalId(Nickname,terminalId)) {
				throw new RecordAlreadyExistsException(
						"A customer with this alias already exists for this terminal. Change the alias to make it unique for this terminal.");
			}
		} else {
			throw new NoRecordsFoundException("Nickname cant be empty. Enter Valid Nickname");
		}

		UserId.headerUserID(headers);
		customerNickname.setCreateUserId(headers.get("userid"));
		customerNickname.setUpdateUserId(headers.get("userid"));

		customerNickname.setUpdateExtensionSchema(headers.get("extensionschema"));
		customerNickname.setUversion("!");

		CustomerNickname customerProfile = customerNicknameRepository.save(customerNickname);
		if (customerProfile == null) {
			throw new RecordNotAddedException("Record Not added to Database");
		}
		return customerProfile;

	}


	@Transactional
	public CustomerNickname deleteCustomerNickname(@Valid @NotNull CustomerNickname customerNickname) {

		Long terminalId = customerNickname.getTerminalId();
		Long customerId = customerNickname.getCustomerId();
		String Nickname = customerNickname.getCustomerNickname();

		if (terminalId != null) {
			if (!terminalRepository.existsByTerminalId(terminalId)) {
				throw new NoRecordsFoundException("No Terminal Found with this terminal id : " + terminalId);
			}
		} else {
			throw new NoRecordsFoundException("terminalId  cant be empty. Enter Valid terminalId");
		}

		if (customerId != null) {
			if (!CustomerRepository.existsByCustomerId(customerId)) {
				throw new NoRecordsFoundException("No Customer Found with this customer id : " + customerId);
			}
		} else {
			throw new NoRecordsFoundException("customerId cant be empty. Enter Valid customerId");
		}

		if (!Nickname.isEmpty()) {
			if (!customerNicknameRepository.existsByCustomerNickname(Nickname)) {
				throw new NoRecordsFoundException("Nickname does not exist. Change the nickname");
			}
		} else {
			throw new NoRecordsFoundException("Nickname cant be empty. Enter Valid Nickname");
		}

		customerNicknameRepository.deleteByCustomerNickname(Nickname);
		return customerNickname;
	
	}


}
