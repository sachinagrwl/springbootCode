package com.nscorp.obis.services;

import com.nscorp.obis.domain.DestinationTerminalNotifyProfile;
import com.nscorp.obis.dto.DestinationTerminalNotifyProfileDTO;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface DestinationTerminalNotifyProfileService {
	
		List<DestinationTerminalNotifyProfile> fetchDestinationTerminalProfilesByTermId( Long terminalId) throws SQLException;

		DestinationTerminalNotifyProfile updateDestinationTerminalProfile(
				@Valid DestinationTerminalNotifyProfileDTO destinationTerminalNotifyProfileDTO,
				Map<String, String> headers) throws SQLException;;
	
		void deleteDestinationTerminalProfile(@Valid DestinationTerminalNotifyProfile destinationTerminalNotifyProfile);

		DestinationTerminalNotifyProfile insertDestinationTerminalNotifyProfile(
				DestinationTerminalNotifyProfile destinationTerminalNotifyProfile, Map<String, String> headers,
				Long terminalId);
}
