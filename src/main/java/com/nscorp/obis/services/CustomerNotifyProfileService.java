package com.nscorp.obis.services;

import com.nscorp.obis.domain.CustomerNotifyProfile;
import com.nscorp.obis.dto.CustomerNotifyProfileDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface CustomerNotifyProfileService {

	List<CustomerNotifyProfile> fetchCustomerNotifyProfiles(@Valid Long customerId) throws SQLException;

	CustomerNotifyProfile updateCustomerNotifyProfile(@Valid CustomerNotifyProfileDTO customerNotifyProfileDTO,
			Map<String,String> headers);
	
	void deleteCustomerNotifyProfiles(@Valid @NotNull CustomerNotifyProfile customerNotifyProfiles);

	CustomerNotifyProfile insertCustomerNotifyProfile(CustomerNotifyProfile customerNotifyProfile,
			Map<String, String> headers, Long notifyTerminalId, Long customerId);

}
