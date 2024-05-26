package com.nscorp.obis.services;

import com.nscorp.obis.domain.AARType;
import com.nscorp.obis.domain.Block;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

public interface AARTypeService {

	List<AARType> getAllAARTypes(String type);
	
	List<AARType> getAllAARTypesList(List<String> type,List<String> description,List<Integer> capacity);

	AARType insertAARType(AARType aarType, Map<String, String> headers);

	AARType updateAARType(@Valid AARType aarTypeObj, Map<String, String> headers);

	
	public void deleteAARType(AARType aarType);
}
