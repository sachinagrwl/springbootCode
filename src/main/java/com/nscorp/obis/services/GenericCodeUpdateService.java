package com.nscorp.obis.services;

import com.nscorp.obis.domain.GenericCodeUpdate;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

public interface GenericCodeUpdateService {
	
	List<GenericCodeUpdate> getByTableName(String tableName);

	GenericCodeUpdate insertCode(@Valid GenericCodeUpdate genericcodeupdate, Map<String, String> headers);

	GenericCodeUpdate updateCode(@Valid GenericCodeUpdate codeUpdate, Map<String, String> headers);

	void deleteCode(@Valid GenericCodeUpdate codeDelete);

}

