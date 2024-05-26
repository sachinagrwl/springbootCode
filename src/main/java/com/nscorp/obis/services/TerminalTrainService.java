package com.nscorp.obis.services;

import com.nscorp.obis.domain.TerminalTrain;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

public interface TerminalTrainService {

	
	/* This Method Is Used To Fetch All Values */
	
	public List<TerminalTrain> getAllTerminalTrains();
	
	/*This method is used to Update existing train desc*/
	TerminalTrain updateTrainDesc(@Valid TerminalTrain trainDescObj, Map<String ,String> headers);
	public void deleteTrain(TerminalTrain terminalTrain);
		
	
}
