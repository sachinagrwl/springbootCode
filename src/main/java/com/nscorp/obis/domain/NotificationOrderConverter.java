package com.nscorp.obis.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply =true)
public class NotificationOrderConverter implements AttributeConverter<NotificationOrder, String>{

	@Override
	public String convertToDatabaseColumn(NotificationOrder attribute) {
		if(attribute==null)return null;
		return attribute.getCode();
	}

	@Override
	public NotificationOrder convertToEntityAttribute(String dbData) {
		if(dbData!=null) 
			return NotificationOrder.of(dbData);
		else
			return null;
	}

}
