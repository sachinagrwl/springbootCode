package com.nscorp.obis.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply =true)
public class NotificationTypeConverter implements AttributeConverter<NotificationType, String>{

	@Override
	public String convertToDatabaseColumn(NotificationType attribute) {
		if(attribute==null)return null;
		return attribute.getCode();
	}

	@Override
	public NotificationType convertToEntityAttribute(String dbData) {
		if(dbData!= null)
			return NotificationType.of(dbData.trim());
		else
			return null;
	}
}