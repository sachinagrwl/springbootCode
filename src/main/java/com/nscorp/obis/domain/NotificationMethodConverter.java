package com.nscorp.obis.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply =true)
public class NotificationMethodConverter implements AttributeConverter<NotificationMethod, String>{
	@Override
	public String convertToDatabaseColumn(NotificationMethod attribute) {
		if(attribute==null)return null;
		return attribute.getCode();
	}

	@Override
	public NotificationMethod convertToEntityAttribute(String dbData) {
		if(dbData!=null)
			return NotificationMethod.of(dbData.trim());
		else
			return null;
	}
}