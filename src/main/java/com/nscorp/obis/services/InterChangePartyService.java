package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.nscorp.obis.domain.InterChangeParty;
import com.nscorp.obis.domain.Road;

public interface InterChangePartyService {

	public List<InterChangeParty> getAllTables(String ichgCode);
	
	InterChangeParty updateInterChangeParty(InterChangeParty interChangeParty, Map<String,String> headers);

	public void deleteInterChangeParty(InterChangeParty interChangePartyDTOTointerChangeParty);

	InterChangeParty insertInterChangeParty(@Valid InterChangeParty interChangePartyObj, Map<String, String> headers);


}
