package com.nscorp.obis.services;

import com.nscorp.obis.domain.TerminalNotifyProfile;
import com.nscorp.obis.dto.TerminalNotifyProfileDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface TerminalNotifyProfileService {
	
	List<TerminalNotifyProfile> fetchTerminalProfilesByTermId(@Valid Long termId) throws SQLException;

	TerminalNotifyProfile updateTerminalProfileByProfileId(@Valid TerminalNotifyProfileDTO terminalNotifyProfileDTO, Map<String,String> headers) throws SQLException;

	
	TerminalNotifyProfile insertTerminalNotifyProfile(TerminalNotifyProfile terminalnotifyprofile,Map<String, String> headers, Long termId);

	void deleteTerminalProfile(@Valid @NotNull TerminalNotifyProfile terminalNotifyProfile);

}
