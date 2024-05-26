package com.nscorp.obis.services;

import com.nscorp.obis.domain.CodeTableSelection;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

public interface CodeTableSelectionService {

	/* This Method Is Used To Fetch All Values */
	List<CodeTableSelection> getAllTables();
	
	CodeTableSelection insertTable(@Valid CodeTableSelection codeObj, Map<String, String> headers);
	
	CodeTableSelection updateCodeTableSelection(CodeTableSelection tableObj, Map<String, String> headers);

	void deleteTable(@Valid CodeTableSelection tableObj);
	
}
