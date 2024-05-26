package com.nscorp.obis.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.nscorp.obis.domain.CustomerNickname;

public interface CustomerNicknameService {

	List<CustomerNickname> fetchCustomerNickname(@Valid Long customerId, @Valid Long terminalId,
			@Valid String customerNickname) throws SQLException;

	CustomerNickname addCustomerNickname(CustomerNickname customerNickname,Map<String, String> headers);

	CustomerNickname deleteCustomerNickname(@Valid @NotNull CustomerNickname customerNickname);

}
