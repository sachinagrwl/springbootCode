package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.nscorp.obis.domain.Terminal;


public interface TerminalService {
	Terminal updateTerminal(@Valid Terminal terminalObj, Map<String ,String> headers);

	 Terminal insertTerminal(@Valid Terminal terminalObj, Map<String, String> headers);
	 
	 public Terminal getTerminal(Long termId);


		
	


}
