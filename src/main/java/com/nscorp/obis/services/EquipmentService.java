package com.nscorp.obis.services;

import java.util.List;

import com.nscorp.obis.domain.Equipment;

public interface EquipmentService  {
	
	public List<Equipment> getAllEquiList( String equipInit, Integer equipNbr );

}
