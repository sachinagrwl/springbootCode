package com.nscorp.obis.domain;

import javax.persistence.AttributeConverter;

public class TerminalTypeConverter implements AttributeConverter<TerminalType, String>{

	@Override
	public String convertToDatabaseColumn(TerminalType attribute) {
		if(attribute==null)return null;
		return attribute.getCode();
	}

	@Override
	public TerminalType convertToEntityAttribute(String dbData) {
		if(dbData!= null)
			return TerminalType.of(dbData.trim());
		else
			return null;
	}
} 

