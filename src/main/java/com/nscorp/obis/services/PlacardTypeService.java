package com.nscorp.obis.services;

import java.util.List;
import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;

import com.nscorp.obis.domain.PlacardType;

public interface PlacardTypeService {

	List<PlacardType> getAllPlacard(String placardCd);

	PlacardType addPlacard(PlacardType placardType, Map<String, String> headers);
	
	PlacardType updatePlacard(PlacardType placardType, Map<String, String> headers);

	List<PlacardType> deletePlacardType(PlacardType placardType) throws ConstraintViolationException;
}
