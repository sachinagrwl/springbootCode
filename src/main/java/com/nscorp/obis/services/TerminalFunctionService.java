package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import com.nscorp.obis.domain.TerminalFunction;

public interface TerminalFunctionService {
	
	List<TerminalFunction> getTerminalFunctionList(Long terminalId, String functionName);
	
	TerminalFunction updateTerminalFunction(TerminalFunction terminalFunction, Map<String, String> headers);
}
