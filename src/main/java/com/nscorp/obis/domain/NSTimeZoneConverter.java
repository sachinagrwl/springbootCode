package com.nscorp.obis.domain;

import javax.persistence.AttributeConverter;

public class NSTimeZoneConverter implements AttributeConverter<NSTimeZone, Integer>{

	@Override
	public Integer convertToDatabaseColumn(NSTimeZone attribute) {
		if(attribute==null)return null;
		return attribute.getCode();
	}

	@Override
	public NSTimeZone convertToEntityAttribute(Integer dbData) {
		if(dbData!= null)
			return NSTimeZone.of(dbData);
		else
			return null;
	}
} 



